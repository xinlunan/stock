package com.xu.stock.analyse.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xu.stock.analyse.dao.IStockTradeDao;
import com.xu.stock.analyse.model.StockAnalyseStrategy;
import com.xu.stock.analyse.model.StockTrade;
import com.xu.stock.analyse.service.StockAnalyseConstants.HighestProbeBuyArgs;
import com.xu.stock.analyse.service.StockAnalyseConstants.StrategyType;
import com.xu.stock.analyse.service.StockAnalyseConstants.TradeNature;
import com.xu.stock.analyse.service.StockAnalyseConstants.TradeType;
import com.xu.stock.analyse.service.uitl.StockAnalyseUtil;
import com.xu.stock.data.model.StockDaily;
import com.xu.util.DateUtil;

/**
 * 最高点探测分析
 * 
 * @version
 * 
 * <pre>
 * Author	Version		Date		Changes
 * lunan.xu 	1.0  		2016年3月19日 	Created
 * </pre>
 * 
 * @since 1.
 */
@SuppressWarnings("restriction")
@Service("volumeRatioAnalyseService")
public class VolumeRatioAnalystService extends BaseStockAnalyseService {

    private Integer        lastWaveCycle;
    private Integer        thisWaveCycle;
    private BigDecimal     thisFallRate;
    private BigDecimal     stopLoss = BigDecimal.valueOf(Float.valueOf(10f));
    @Resource
    private IStockTradeDao stockTradeDao;

    @Override
    public List<StockAnalyseStrategy> getAnalyseStrategys() {
        return stockAnalyseStrategyDao.getAnalyseStrategys(StrategyType.HIGHEST_PROBE_BUY);
    }

    @Override
    public void setAnalyseStrategy(StockAnalyseStrategy strategy) {
        super.setAnalyseStrategy(strategy);
        this.lastWaveCycle = strategy.getIntValue(HighestProbeBuyArgs.D1_LAST_WAVE_CYCLE);
        this.thisWaveCycle = strategy.getIntValue(HighestProbeBuyArgs.D2_THIS_WAVE_CYCLE);
        this.thisFallRate = BigDecimal.valueOf(strategy.getDoubleValue(HighestProbeBuyArgs.F1_THIS_FALL_RATE));
    }

    @Override
    public void doAnalyse(List<StockDaily> dailys) {
        if ("000033".equals(dailys.get(0).getStockCode())) {
            log.info("debug异常" + dailys.get(0).getStockCode());
        }
        analyseStockTradeBuy(dailys);
        analyseStockTradeSell(dailys);

        log.info("analyse stock code:" + dailys.get(0).getStockCode());
    }

    /**
     * 是否是购买点
     * 
     * @param dailys
     * @param index
     * @param riseDay2
     * @return
     */
    protected boolean isBuyDaily(List<StockDaily> dailys, int index) {
        StockDaily daily = dailys.get(index);
        if (daily.getVolumeRatio().compareTo(BigDecimal.valueOf(Integer.valueOf(5))) >= 0) {
            // if (daily.getMa5().compareTo(daily.getMa10()) > 0 &&
            // daily.getMa10().compareTo(daily.getMa20()) > 0) {
            if (daily.getMa5().compareTo(daily.getMa10()) > 0 && daily.getMa10().compareTo(daily.getMa20()) > 0) {
                for (int i = index - 1; i < index; i++) {
                    StockDaily lastDaily = dailys.get(i);
                    if (lastDaily.getMa5().compareTo(lastDaily.getMa10()) < 0 && lastDaily.getMa10().compareTo(lastDaily.getMa20()) < 0) {
                        return false;
                    }
                }
            }
            return true;
            // }
        }
        return false;
    }

    protected void analyseStockTradeBuy(List<StockDaily> dailys) {
        int endIndex = dailys.size();

        List<StockDaily> buyPoints = new ArrayList<StockDaily>();
        for (int index = 60; index < endIndex; index++) {
            if (isBuyDaily(dailys, index)) {
                buyPoints.add(dailys.get(index));
            }
        }

        List<StockTrade> trades = new ArrayList<StockTrade>();
        for (StockDaily stockDaily : buyPoints) {
            StockTrade trade = new StockTrade();

            trade.setStockCode(stockDaily.getStockCode());
            trade.setStockName(stockDaily.getStockName());

            trade.setBuyDate(stockDaily.getDate());
            trade.setBuyHour(15);
            trade.setBuyMinute(0);
            trade.setBuyTradePrice(stockDaily.getClose());
            trade.setBuyHighPrice(stockDaily.getHigh());
            trade.setBuyClosePrice(stockDaily.getClose());

            trade.setSellDate(stockDaily.getDate());
            trade.setSellHour(15);
            trade.setSellMinute(0);
            trade.setSellTradePrice(BigDecimal.valueOf(0));
            trade.setSellHighPrice(BigDecimal.valueOf(0));
            trade.setSellClosePrice(BigDecimal.valueOf(0));

            trade.setProfit(BigDecimal.valueOf(0));
            trade.setProfitRate(BigDecimal.valueOf(0));
            trade.setHighProfitRate(BigDecimal.valueOf(0));
            trade.setCloseProfitRate(BigDecimal.valueOf(0));
            trade.setExrights(stockDaily.getExrights());

            trade.setTradeType(TradeType.BUY);
            trade.setTradeNature(TradeNature.VIRTUAL);
            trade.setStrategy(StrategyType.SERIAL_RISE_BUY.toString());
            trade.setVersion(strategy.getVersion());
            trade.setParameters(strategy.getParameters());
            log.info(trade.toString());

            trades.add(trade);
        }

        stockTradeDao.saveStockTrades(trades);
    }

    protected void analyseStockTradeSell(List<StockDaily> dailys) {
        List<StockTrade> buys = stockTradeDao.getBuyTrades(StrategyType.SERIAL_RISE_BUY, dailys.get(0).getStockCode());
        List<StockTrade> sells = new ArrayList<StockTrade>();
        for (StockTrade buy : buys) {
            if (DateUtil.date2String(buy.getBuyDate()).equals(DateUtil.date2String(dailys.get(dailys.size() - 1).getDate()))) {
                continue;
            }
            StockDaily sellDaily = StockAnalyseUtil.getSellDaily(dailys, buy.getBuyDate(), 1);
            if (sellDaily != null) {
                StockTrade sell = new StockTrade();

                sell.setStockCode(sellDaily.getStockCode());
                sell.setStockName(sellDaily.getStockName());

                sell.setBuyDate(buy.getBuyDate());
                sell.setBuyHour(buy.getBuyHour());
                sell.setBuyMinute(buy.getBuyMinute());
                sell.setBuyTradePrice(buy.getBuyTradePrice());
                sell.setBuyHighPrice(buy.getBuyHighPrice());
                sell.setBuyClosePrice(buy.getBuyClosePrice());
                sell.setExrights(sellDaily.getExrights());

                sell.setSellDate(sellDaily.getDate());
                sell.setSellHour(15);
                sell.setSellMinute(0);
                BigDecimal expectRate = BigDecimal.valueOf(Integer.valueOf(7)).divide(BD_100, 4, BigDecimal.ROUND_HALF_UP);// 期望收益
                BigDecimal expectSellPrice = buy.getBuyTradePrice().add(buy.getBuyTradePrice().multiply(expectRate)).multiply(buy.getExrights()).divide(sell.getExrights(), 4, BigDecimal.ROUND_HALF_UP);
                BigDecimal stopLossPrice = buy.getBuyTradePrice().subtract(buy.getBuyTradePrice().multiply(stopLoss)).multiply(buy.getExrights()).divide(sell.getExrights(), 4, BigDecimal.ROUND_HALF_UP);
                if (sellDaily.getLow().compareTo(stopLossPrice) == -1) {
                    sell.setSellTradePrice(stopLossPrice);
                } else if (expectSellPrice.compareTo(sellDaily.getHigh()) <= 0) {
                    sell.setSellTradePrice(expectSellPrice);
                } else {
                    sell.setSellTradePrice(sellDaily.getClose());
                }
                sell.setSellHighPrice(sellDaily.getHigh());
                sell.setSellClosePrice(sellDaily.getClose());

                sell.setProfit(sell.getSellTradePrice().subtract(sell.getBuyTradePrice()));
                sell.setProfitRate(sell.getProfit().multiply(BD_100).divide(sell.getBuyTradePrice(), 2, BigDecimal.ROUND_HALF_UP));
                sell.setHighProfitRate(sell.getSellHighPrice().subtract(sell.getBuyTradePrice()).multiply(BD_100).divide(sell.getBuyTradePrice(), 4, BigDecimal.ROUND_HALF_UP));
                sell.setCloseProfitRate(sell.getSellClosePrice().subtract(sell.getBuyTradePrice()).multiply(BD_100).divide(sell.getBuyTradePrice(), 4, BigDecimal.ROUND_HALF_UP));

                sell.setTradeType(TradeType.SELL);
                sell.setTradeNature(TradeNature.VIRTUAL);
                sell.setStrategy(StrategyType.SERIAL_RISE_SELL.toString());
                sell.setVersion(strategy.getVersion());
                sell.setParameters(strategy.getParameters());
                log.info(sell.toString());

                sells.add(sell);
            }
        }
        stockTradeDao.saveStockTrades(sells);
    }

}
