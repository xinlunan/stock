package com.xu.stock.analyse.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xu.stock.analyse.dao.IStockTradeBuyDao;
import com.xu.stock.analyse.dao.IStockTradeSellDao;
import com.xu.stock.analyse.model.StockTradeBuy;
import com.xu.stock.analyse.model.StockTradeSell;
import com.xu.stock.analyse.service.IStockTradeSellService;
import com.xu.stock.analyse.service.StockAnalyseConstants.StockSellTime;
import com.xu.stock.analyse.service.StockAnalyseConstants.StockTradeBuyStatus;
import com.xu.stock.analyse.service.StockAnalyseConstants.StockTradeSellAnalyseType;
import com.xu.stock.analyse.service.StockAnalyseConstants.StrategyType;
import com.xu.stock.analyse.service.StockAnalyseConstants.TradeNature;
import com.xu.stock.analyse.service.uitl.StockAnalyseUtil;
import com.xu.stock.data.model.StockDaily;
import com.xu.stock.data.model.StockMinute;
import com.xu.stock.data.service.IStockMinuteService;
import com.xu.util.DateUtil;

/**
 * 历史最高点
 * 
 * @version
 * 
 * <pre>
 * Author	Version		Date		Changes
 * lunan.xu 	1.0  		2016年4月28日 	Created
 * </pre>
 * 
 * @since 1.
 */
@SuppressWarnings("restriction")
@Service("stockTradeSellService")
public class StockTradeSellService implements IStockTradeSellService {

    protected Logger               log               = LoggerFactory.getLogger(this.getClass());
    public static final BigDecimal BD_100            = BigDecimal.valueOf(100);

    private Integer                holdDayBegin      = 1;
    private Integer                holdDayEnd        = 1;
    private Double                 expectRateBegin   = 10d;
    private Double                 expectRateEnd     = 10d;
    private Double                 stopLossRateBegin = 10d;
    private Double                 stopLossRateEnd   = 10d;

    @Resource
    private IStockMinuteService    stockMinuteService;
    @Resource
    private IStockTradeBuyDao      stockTradeBuyDao;
    @Resource
    private IStockTradeSellDao     stockTradeSellDao;

    public void analyseStockTradeSell(List<StockDaily> dailys, String parameters) {
        initStrategyParameters();
        List<StockTradeBuy> buys = stockTradeBuyDao.getBoughtStockTradeBuys(dailys.get(0).getStockCode(), StrategyType.HIGHEST_PROBE_BUY.toString(), parameters);
        for (StockTradeBuy buy : buys) {
            for (int holdDay = holdDayBegin; holdDay <= holdDayEnd; holdDay++) {
                if (StockAnalyseUtil.dailyIndex(dailys, buy.getDate()) <= dailys.size() - holdDay) {
                    List<StockMinute> minutes = fetchStockMinute(dailys, buy, holdDay);
                    for (int stopLossRate = stopLossRateBegin.intValue(); stopLossRate <= stopLossRateEnd.intValue(); stopLossRate++) {
                        for (int expectRate = expectRateBegin.intValue(); expectRate <= expectRateEnd.intValue(); expectRate++) {
                            StockMinute stockMinute = analyseSellMinute(dailys, minutes, buy, holdDay, stopLossRate, expectRate);
                            if (stockMinute != null) {
                                StockTradeSell sellTrade = buildStockTradeSell(stockMinute, buy, parameters + "," + holdDay + "," + expectRate + "," + stopLossRate);
                                stockTradeSellDao.saveTradeSell(sellTrade);
                                buy.setStatus(StockTradeBuyStatus.SELLED);
                                stockTradeBuyDao.updateStatus(buy);
                            } else {
                                log.info("停牌，无法卖出\t" + buy.getStockCode() + "\t" + DateUtil.date2String(buy.getDate()));
                            }
                        }
                    }
                    if (StockTradeBuyStatus.BOUGHT.equals(buy.getStatus())) {
                        log.error("惨了没卖出去");
                    }
                } else {
                    log.info("未到卖出日期\t" + buy.getStockCode() + "\t" + DateUtil.date2String(buy.getDate()));
                }
            }

        }
    }

    private StockTradeSell buildStockTradeSell(StockMinute stockMinute, StockTradeBuy buy, String parameters) {
        StockTradeSell sellTrade = new StockTradeSell();

        sellTrade.setStockCode(buy.getStockCode());
        sellTrade.setStockName(buy.getStockName());
        sellTrade.setStrategy(buy.getStrategy());
        sellTrade.setParameters(parameters);
        sellTrade.setNature(TradeNature.VIRTUAL);
        sellTrade.setLastDate(buy.getDate());
        sellTrade.setDate(stockMinute.getDate());
        sellTrade.setHour(stockMinute.getHour());
        sellTrade.setMinute(stockMinute.getMinute());
        sellTrade.setPrice(stockMinute.getPrice());
        sellTrade.setExrights(stockMinute.getExrights());
        sellTrade.setProfitRate(sellTrade.getPrice().multiply(stockMinute.getExrights()).subtract(buy.getPrice().multiply(buy.getExrights())).multiply(BD_100).divide(buy.getPrice().multiply(buy.getExrights()), 4, BigDecimal.ROUND_HALF_UP));
        sellTrade.setAnalyseType(StockTradeSellAnalyseType.REALTIME);

        sellTrade.setBuyDate(buy.getDate());
        sellTrade.setExrights(stockMinute.getExrights());
        return sellTrade;
    }

    private StockMinute analyseSellMinute(List<StockDaily> dailys, List<StockMinute> minutes, StockTradeBuy buy, int holdDay, int stopLossRate, int expectRateInt) {
        if (!minutes.isEmpty()) {
            BigDecimal expectRate = BigDecimal.valueOf(expectRateInt).divide(BD_100).setScale(4, BigDecimal.ROUND_HALF_UP);// 期望收益
            BigDecimal thisStopLoss_100 = BigDecimal.valueOf(stopLossRate).divide(BD_100).setScale(4, BigDecimal.ROUND_HALF_UP);
            StockDaily buyDaily = StockAnalyseUtil.getSellDaily(dailys, buy.getDate(), 0);
            for (int i = 0; i < minutes.size(); i++) {
                StockMinute stockMinute = minutes.get(i);
                BigDecimal expectSellPrice = buy.getPrice().add(buy.getPrice().multiply(expectRate)).multiply(buy.getExrights()).divide(stockMinute.getExrights(), 4, BigDecimal.ROUND_HALF_UP);
                BigDecimal stopLossPrice = buyDaily.getClose().subtract(buyDaily.getClose().multiply(thisStopLoss_100)).multiply(buyDaily.getExrights()).divide(stockMinute.getExrights(), 4, BigDecimal.ROUND_HALF_UP);
                Boolean isStopLoss = stockMinute.getPrice().compareTo(stopLossPrice) == -1;
                Boolean isExpectSell = expectSellPrice.compareTo(stockMinute.getPrice()) <= 0;
                Boolean isNearClose = (stockMinute.getHour() >= StockSellTime.HOUR && stockMinute.getMinute() >= StockSellTime.MINUTE);

                if (isStopLoss || isExpectSell || isNearClose) {// 止跌、达到预期、到了接近收盘时间
                    return stockMinute;
                }
            }
            log.error("到收盘都没生成卖出交易 ");
            throw new RuntimeException("到收盘都没生成卖出交易 ");
        } else {
            log.info("没有分时信息\t" + buy.getStockCode() + "\t" + DateUtil.date2String(buy.getDate()));
            if (StockAnalyseUtil.dailyIndex(dailys, buy.getDate()) < dailys.size() - holdDay) {// 历史日期，只是没获取到分时信息。以收盘价成交
                StockDaily daily = dailys.get(StockAnalyseUtil.dailyIndex(dailys, buy.getDate()) + holdDay);
                StockMinute stockMinute = new StockMinute();
                stockMinute.setDate(daily.getDate());
                stockMinute.setHour(15);
                stockMinute.setMinute(0);
                stockMinute.setPrice(daily.getClose());
                stockMinute.setHigh(daily.getHigh());
                stockMinute.setExrights(daily.getExrights());
                return stockMinute;
            } else {
                return null;
            }
        }
    }

    /**
     * 获取分时信息
     * 
     * @param dailys
     * @param buy
     * @return
     */
    private List<StockMinute> fetchStockMinute(List<StockDaily> dailys, StockTradeBuy buy, int holdDay) {
        List<StockMinute> stockMinutes = null;
        int dailyIndex = StockAnalyseUtil.dailyIndex(dailys, buy.getDate());
        if (dailyIndex > dailys.size() - holdDay) {
            return null;
        } else if (dailyIndex == dailys.size() - holdDay) {
            stockMinutes = stockMinuteService.fetchRealtimeMinute(dailys.get(dailys.size() - holdDay));
        } else {
            stockMinutes = stockMinuteService.fetchHistoryMinutes(dailys.get(dailyIndex + holdDay));
        }
        return stockMinutes;
    }

    /**
     * 初始化策略参数
     */
    private void initStrategyParameters() {

    }

}
