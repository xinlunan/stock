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
@SuppressWarnings({ "restriction", "unused", "hiding" })
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
        // 流通股本、量比、换手率、收阳、现价
        if (daily.getCirculationMarketValue() < 10000000000l && daily.getClose().doubleValue() < 25 && daily.getVolumeRatio().doubleValue() > 3 && daily.getTurnoverRate().doubleValue() > 8 && daily.getCloseGapRate().doubleValue() > 0
            && daily.getCloseGapRate().doubleValue() < 9.9) {
            // 找双底
            StockDaily before100 = dailys.get(index - 100);
            StockDaily before1 = dailys.get(index - 1);
            if (before100.getMa60().doubleValue() > before1.getMa60().doubleValue()) {// 大趋势下跌
                StockDaily _60lowestDaily = dailys.get(index - 60);
                for (int i = index - 60; i < index; i++) {// 60天内最低点找出来
                    StockDaily lastDaily = dailys.get(i);
                    if (_60lowestDaily.getClose().multiply(_60lowestDaily.getExrights()).doubleValue() > lastDaily.getClose().multiply(lastDaily.getExrights()).doubleValue()) {
                        _60lowestDaily = lastDaily;
                    }
                }
                Integer _60lowestIndex = StockAnalyseUtil.dailyIndex(dailys, _60lowestDaily.getDate());
                if (_60lowestIndex < index - 20) {// 最低点在20天以前
                    StockDaily _20lowestDaily = dailys.get(index - 20);// 20天内最低点
                    for (int i = index - 20; i < index; i++) {// 60天内最低点找出来
                        StockDaily lastDaily = dailys.get(i);
                        if (_20lowestDaily.getClose().multiply(_20lowestDaily.getExrights()).doubleValue() > lastDaily.getClose().multiply(lastDaily.getExrights()).doubleValue()) {
                            _20lowestDaily = lastDaily;
                        }
                    }
                    Integer _20lowestIndex = StockAnalyseUtil.dailyIndex(dailys, _20lowestDaily.getDate());
                    if (_20lowestIndex > index - 10) {
                        if (_20lowestDaily.getClose().multiply(_20lowestDaily.getExrights()).multiply(BD_100).divide(_60lowestDaily.getClose().multiply(_60lowestDaily.getExrights()), 4, BigDecimal.ROUND_HALF_UP).doubleValue() < 105) {
                            BigDecimal sumBefor60 = BigDecimal.ZERO;
                            BigDecimal sum60 = BigDecimal.ZERO;
                            BigDecimal sum20 = BigDecimal.ZERO;
                            for (int j = index - 90; j < index - 70; j++) {
                                StockDaily lastDaily = dailys.get(j);
                                sumBefor60 = sumBefor60.add(lastDaily.getTurnoverRate());
                            }
                            for (int j = _60lowestIndex - 5; j < _60lowestIndex + 5; j++) {
                                StockDaily lastDaily = dailys.get(j);
                                sum60 = sum60.add(lastDaily.getTurnoverRate());
                            }
                            for (int j = _20lowestIndex - 4; j <= _20lowestIndex; j++) {
                                StockDaily lastDaily = dailys.get(j);
                                sum20 = sum20.add(lastDaily.getTurnoverRate());
                            }
                            Double avgBefor60 = sumBefor60.divide(BigDecimal.valueOf(20), 4, BigDecimal.ROUND_HALF_UP).doubleValue();
                            Double avg60 = sum60.divide(BigDecimal.valueOf(10), 4, BigDecimal.ROUND_HALF_UP).doubleValue();
                            Double avg20 = sum20.divide(BigDecimal.valueOf(5), 4, BigDecimal.ROUND_HALF_UP).doubleValue();
                            if (avgBefor60 > avg60 && avgBefor60 > avg20) {
                            }
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    protected void analyseStockTradeBuy(List<StockDaily> dailys) {
        int endIndex = dailys.size();

        List<StockDaily> buyPoints = new ArrayList<StockDaily>();
        if (dailys.size() > 100) {
            for (int index = 100; index < endIndex; index++) {
                if (isBuyDaily(dailys, index)) {
                    buyPoints.add(dailys.get(index));
                }
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
