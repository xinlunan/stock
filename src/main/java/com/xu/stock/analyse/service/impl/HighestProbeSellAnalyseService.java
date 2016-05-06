package com.xu.stock.analyse.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xu.stock.analyse.model.StockAnalyseStrategy;
import com.xu.stock.analyse.model.StockTrade;
import com.xu.stock.analyse.service.StockAnalyseConstants.HighestProbeSellArgs;
import com.xu.stock.analyse.service.StockAnalyseConstants.StockSellTime;
import com.xu.stock.analyse.service.StockAnalyseConstants.StrategyType;
import com.xu.stock.analyse.service.StockAnalyseConstants.TradeNature;
import com.xu.stock.analyse.service.StockAnalyseConstants.TradeType;
import com.xu.stock.analyse.service.uitl.StockAnalyseUtil;
import com.xu.stock.data.model.StockDaily;
import com.xu.stock.data.model.StockMinute;
import com.xu.stock.data.service.IStockMinuteService;
import com.xu.stock.data.service.IStockService;
import com.xu.util.DateUtil;

@SuppressWarnings("restriction")
@Service("highestProbeSellAnalyseService")
public class HighestProbeSellAnalyseService extends BaseStockAnalyseService {

    private Integer             holdDay;
    private BigDecimal          stopLoss;
    @Resource
    private IStockMinuteService stockMinuteService;
    @Resource
    private IStockService       stockService;

    @Override
    public void setAnalyseStrategy(StockAnalyseStrategy strategy) {
        super.setAnalyseStrategy(strategy);
        this.holdDay = strategy.getIntValue(HighestProbeSellArgs.HOLD_DAY);
        this.stopLoss = BigDecimal.valueOf(strategy.getDoubleValue(HighestProbeSellArgs.STOP_LOSS));
    }

    @Override
    public List<StockTrade> doAnalyse(List<StockDaily> dailys) {
        log.info("analyse stock code:" + dailys.get(0).getStockCode());

        // 获取买入点
        List<StockTrade> buys = stockSimulateTradeDao.getBuyTrades(StrategyType.HIGHEST_PROBE_BUY, dailys.get(0).getStockCode());

        // 分析卖出点
        return analyseSellPoints(dailys, buys);
    }

    /**
     * 分析卖出点
     * 
     * @param dailys
     * @param buys
     * @return
     */
    private List<StockTrade> analyseSellPoints(List<StockDaily> dailys, List<StockTrade> buys) {
        List<StockTrade> sells = new ArrayList<StockTrade>();
        for (StockTrade buy : buys) {
            StockDaily stockDaily = StockAnalyseUtil.getSellDaily(dailys, buy.getBuyDate(), holdDay);
            Boolean canSell = false;
            List<StockMinute> minutes = stockMinuteService.fetchStockMinutes(stockDaily);
            if (minutes.size() > 0) {
                for (int i = 0; i < minutes.size(); i++) {
                    StockMinute stockMinute = minutes.get(i);
                    BigDecimal expectRate = BigDecimal.valueOf(strategy.getDoubleValue(HighestProbeSellArgs.EXPECT_RATE)).divide(BD_100).setScale(4, BigDecimal.ROUND_HALF_UP);// 期望收益
                    BigDecimal thisStopLoss_100 = stopLoss.divide(BD_100).setScale(4, BigDecimal.ROUND_HALF_UP);// 期望收益
                    BigDecimal expectSellPrice = buy.getBuyTradePrice().add(buy.getBuyTradePrice().multiply(expectRate)).multiply(buy.getExrights()).divide(stockMinute.getExrights(), 4, BigDecimal.ROUND_HALF_UP);
                    BigDecimal stopLossPrice = buy.getBuyTradePrice().subtract(buy.getBuyTradePrice().multiply(thisStopLoss_100)).multiply(buy.getExrights()).divide(stockMinute.getExrights(), 4, BigDecimal.ROUND_HALF_UP);
                    Boolean isStopLoss = stockMinute.getPrice().compareTo(stopLossPrice) == -1;
                    Boolean isExpectSell = expectSellPrice.compareTo(stockMinute.getPrice()) <= 0;
                    Boolean isOnTime = (stockMinute.getHour() >= StockSellTime.HOUR && stockMinute.getMinute() >= StockSellTime.MINUTE);

                    if (isStopLoss || isExpectSell || isOnTime) {
                        BigDecimal sellPrice = null;
                        if (isStopLoss) {
                            sellPrice = stockMinute.getPrice();
                        } else if (isExpectSell) {
                            sellPrice = stockMinute.getPrice();
                        } else {
                            sellPrice = stockMinute.getPrice();
                        }

                        StockTrade sellTrade = new StockTrade();

                        sellTrade.setStockCode(buy.getStockCode());
                        sellTrade.setStockName(buy.getStockName());

                        sellTrade.setBuyDate(buy.getBuyDate());
                        sellTrade.setBuyHour(buy.getBuyHour());
                        sellTrade.setBuyMinute(buy.getBuyMinute());
                        sellTrade.setBuyTradePrice(buy.getBuyTradePrice());
                        sellTrade.setBuyHighPrice(buy.getBuyHighPrice());
                        sellTrade.setBuyClosePrice(buy.getBuyClosePrice());
                        sellTrade.setExrights(stockMinute.getExrights());

                        sellTrade.setSellDate(stockMinute.getDate());
                        sellTrade.setSellHour(stockMinute.getHour());
                        sellTrade.setSellMinute(stockMinute.getMinute());
                        sellTrade.setSellTradePrice(sellPrice);
                        sellTrade.setSellHighPrice(stockMinute.getHigh());
                        sellTrade.setSellClosePrice(sellPrice);

                        sellTrade.setProfit(sellTrade.getSellTradePrice().subtract(sellTrade.getBuyTradePrice()));
                        sellTrade.setProfitRate(sellTrade.getProfit().multiply(BD_100).divide(sellTrade.getBuyTradePrice(), 4, BigDecimal.ROUND_HALF_UP));
                        sellTrade.setHighProfitRate(sellTrade.getSellHighPrice().subtract(sellTrade.getBuyTradePrice()).multiply(BD_100).divide(sellTrade.getBuyTradePrice(), 4, BigDecimal.ROUND_HALF_UP));
                        sellTrade.setCloseProfitRate(sellTrade.getSellClosePrice().subtract(sellTrade.getBuyTradePrice()).multiply(BD_100).divide(sellTrade.getBuyTradePrice(), 4, BigDecimal.ROUND_HALF_UP));

                        sellTrade.setTradeType(TradeType.SELL);
                        sellTrade.setTradeNature(TradeNature.VIRTUAL);
                        sellTrade.setStrategy(StrategyType.HIGHEST_PROBE_SELL.toString());
                        sellTrade.setVersion(strategy.getVersion());
                        sellTrade.setParameters(strategy.getParameters());
                        log.info(sellTrade.toString());

                        sells.add(sellTrade);
                        canSell = true;
                        break;
                    }
                }
            } else {
                log.info("没有分时信息");
            }

            if (!canSell) {
                // TODO 不合理的代码，迟早要删除
                log.info("惨了，卖不出去\t" + buy.getStockCode() + DateUtil.date2String(buy.getBuyDate()));

                StockDaily daily = stockService.getNextDaily(buy.getStockCode(), buy.getBuyDate());
                if (daily != null) {
                    StockTrade sellTrade = new StockTrade();

                    sellTrade.setStockCode(buy.getStockCode());
                    sellTrade.setStockName(buy.getStockName());

                    sellTrade.setBuyDate(buy.getBuyDate());
                    sellTrade.setBuyHour(buy.getBuyHour());
                    sellTrade.setBuyMinute(buy.getBuyMinute());
                    sellTrade.setBuyTradePrice(buy.getBuyTradePrice());
                    sellTrade.setBuyHighPrice(buy.getBuyHighPrice());
                    sellTrade.setBuyClosePrice(buy.getBuyClosePrice());
                    sellTrade.setExrights(buy.getExrights());

                    sellTrade.setSellDate(daily.getDate());
                    sellTrade.setSellHour(15);
                    sellTrade.setSellMinute(0);
                    sellTrade.setSellTradePrice(daily.getClose());
                    sellTrade.setSellHighPrice(daily.getHigh());
                    sellTrade.setSellClosePrice(daily.getClose());

                    sellTrade.setProfit(sellTrade.getSellTradePrice().subtract(sellTrade.getBuyTradePrice()));
                    sellTrade.setProfitRate(sellTrade.getProfit().multiply(BD_100).divide(sellTrade.getBuyTradePrice(), 4, BigDecimal.ROUND_HALF_UP));
                    sellTrade.setHighProfitRate(sellTrade.getSellHighPrice().subtract(sellTrade.getBuyTradePrice()).multiply(BD_100).divide(sellTrade.getBuyTradePrice(), 4, BigDecimal.ROUND_HALF_UP));
                    sellTrade.setCloseProfitRate(sellTrade.getSellClosePrice().subtract(sellTrade.getBuyTradePrice()).multiply(BD_100).divide(sellTrade.getBuyTradePrice(), 4, BigDecimal.ROUND_HALF_UP));

                    sellTrade.setTradeType(TradeType.SELL);
                    sellTrade.setTradeNature(TradeNature.VIRTUAL);
                    sellTrade.setStrategy(StrategyType.HIGHEST_PROBE_SELL.toString());
                    sellTrade.setVersion(strategy.getVersion());
                    sellTrade.setParameters(strategy.getParameters());
                    log.info(sellTrade.toString());

                    sells.add(sellTrade);
                } else {
                    log.info("惨了惨了惨了，真卖不出去，停牌\t" + buy.getStockCode() + DateUtil.date2String(buy.getBuyDate()));

                }

            }
        }

        return sells;
    }

    @Override
    public List<StockAnalyseStrategy> getAnalyseStrategys() {
        return stockAnalyseStrategyDao.getAnalyseStrategys(StrategyType.HIGHEST_PROBE_SELL);
    }

}
