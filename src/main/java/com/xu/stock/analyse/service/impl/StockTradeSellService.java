package com.xu.stock.analyse.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Double                 expectRateBegin   = 0d;
    private Double                 expectRateEnd     = 10d;
    private Double                 stopLossRateBegin = 10d;
    private Double                 stopLossRateEnd   = 10d;

    @Resource
    private IStockMinuteService    stockMinuteService;
    @Resource
    private IStockTradeBuyDao      stockTradeBuyDao;
    @Resource
    private IStockTradeSellDao     stockTradeSellDao;

    @Override
    public void analyseStockTradeSell(List<StockDaily> dailys) {
        Map<String, List<StockMinute>> minuteCache = new HashMap<String, List<StockMinute>>();
        initStrategyParameters();
        List<StockTradeBuy> buys = stockTradeBuyDao.getBoughtStockTradeBuys(dailys.get(0).getStockCode(), StrategyType.HIGHEST_PROBE_BUY.toString());
        for (StockTradeBuy buy : buys) {
            for (int holdDay = holdDayBegin; holdDay <= holdDayEnd; holdDay++) {
                if (StockAnalyseUtil.dailyIndex(dailys, buy.getDate()) != null) {
                    if (StockAnalyseUtil.dailyIndex(dailys, buy.getDate()) < dailys.size() - holdDay
                        || (StockAnalyseUtil.dailyIndex(dailys, buy.getDate()) == dailys.size() - holdDay && StockAnalyseUtil.hasNewSellData(dailys, dailys.get(dailys.size() - 1).getDate()))) {
                        List<StockMinute> minutes = fetchStockMinute(dailys, buy, holdDay, minuteCache);
                        for (int stopLossRate = stopLossRateBegin.intValue(); stopLossRate <= stopLossRateEnd.intValue(); stopLossRate++) {
                            for (int expectRate = expectRateBegin.intValue(); expectRate <= expectRateEnd.intValue(); expectRate++) {
                                StockMinute stockMinute = analyseSellMinute(dailys, minutes, buy, holdDay, stopLossRate, expectRate);
                                if (stockMinute != null) {
                                    String parameters = StockAnalyseUtil.buildParameter(buy.getParameters(), holdDay, expectRate, stopLossRate);
                                    StockTradeSell sellTrade = buildStockTradeSell(stockMinute, buy, parameters);
                                    stockTradeSellDao.saveTradeSell(sellTrade);
                                    buy.setStatus(StockTradeBuyStatus.SELLED);
                                    stockTradeBuyDao.updateStatus(buy);
                                    log.info("卖出\t" + buy.getStockCode() + "\t" + DateUtil.date2String(buy.getDate()) + "\t" + DateUtil.date2String(sellTrade.getDate()));
                                } else {
                                    log.info("等待卖出\t" + buy.getStockCode() + "\t" + DateUtil.date2String(buy.getDate()));
                                }
                            }
                        }
                    } else {
                        log.info("未到卖出日期\t" + buy.getStockCode() + "\t" + DateUtil.date2String(buy.getDate()));
                    }
                } else {
                    log.info("未抓取买入日期\t" + buy.getStockCode() + "\t" + DateUtil.date2String(buy.getDate()));
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
        StockMinute result = null; 
        if (!minutes.isEmpty()) {
            BigDecimal expectRate = BigDecimal.valueOf(expectRateInt).divide(BD_100).setScale(4, BigDecimal.ROUND_HALF_UP);// 期望收益
            BigDecimal thisStopLoss_100 = BigDecimal.valueOf(stopLossRate).divide(BD_100).setScale(4, BigDecimal.ROUND_HALF_UP);
            StockDaily buyDay = StockAnalyseUtil.getSellDaily(dailys, buy.getDate(), 0);
            for (int i = 0; i < minutes.size(); i++) {
                StockMinute stockMinute = minutes.get(i);
                BigDecimal expectSellPrice = buy.getPrice().add(buy.getPrice().multiply(expectRate)).multiply(buy.getExrights()).divide(stockMinute.getExrights(), 4, BigDecimal.ROUND_HALF_UP);
                BigDecimal stopLossPrice = buyDay.getClose().subtract(buyDay.getClose().multiply(thisStopLoss_100)).multiply(buyDay.getExrights()).divide(stockMinute.getExrights(), 4, BigDecimal.ROUND_HALF_UP);
                Boolean isStopLoss = stockMinute.getPrice().compareTo(stopLossPrice) == -1;
                Boolean isExpectSell = expectSellPrice.compareTo(stockMinute.getPrice()) <= 0;
                Boolean isNearClose = (stockMinute.getHour() >= StockSellTime.HOUR && stockMinute.getMinute() >= StockSellTime.MINUTE);

                if (isStopLoss) {// 止跌、达到预期、到了接近收盘时间
                    result = stockMinute;
                    break;
                }
                if (isExpectSell) {// 止跌、达到预期、到了接近收盘时间
                    result = stockMinute;
                    break;
                }
                if (isNearClose) {// 止跌、达到预期、到了接近收盘时间
                    result = stockMinute;
                    break;
                }
            }
        }
        if (result == null) {
            if (StockAnalyseUtil.dailyIndex(dailys, buy.getDate()) < dailys.size() - holdDay) {// 历史日期，只是没获取到分时信息。以收盘价成交
                log.info("没有分时信息\t" + buy.getStockCode() + "\t" + DateUtil.date2String(buy.getDate()));
                StockDaily daily = dailys.get(StockAnalyseUtil.dailyIndex(dailys, buy.getDate()) + holdDay);
                StockMinute stockMinute = new StockMinute();
                stockMinute.setDate(daily.getDate());
                stockMinute.setHour(15);
                stockMinute.setMinute(0);
                stockMinute.setPrice(daily.getClose());
                stockMinute.setHigh(daily.getHigh());
                stockMinute.setLow(daily.getLow());
                stockMinute.setExrights(daily.getExrights());
                result = stockMinute;
            }
        }
        return result;
    }

    /**
     * 获取分时信息
     * 
     * @param dailys
     * @param buy
     * @param minuteCache
     * @return
     */
    private List<StockMinute> fetchStockMinute(List<StockDaily> dailys, StockTradeBuy buy, int holdDay, Map<String, List<StockMinute>> minuteCache) {
        List<StockMinute> stockMinutes = null;
        int dailyIndex = StockAnalyseUtil.dailyIndex(dailys, buy.getDate());
        if (dailyIndex > dailys.size() - holdDay) {
            return null;
        } else if (dailyIndex == dailys.size() - holdDay) {
            String cacheKey = buy.getStockCode() + DateUtil.getDate(new Date(), "yyyy-MM-dd HH:mm");
            if (minuteCache.containsKey(cacheKey)) {
                return minuteCache.get(cacheKey);
            } else {
                stockMinutes = stockMinuteService.fetchRealtimeMinute(dailys.get(dailys.size() - holdDay));
                minuteCache.put(cacheKey, stockMinutes);
            }
        } else {
            StockDaily daily = dailys.get(dailyIndex + holdDay);
            if (minuteCache.containsKey(daily.getStockCode() + daily.getDate())) {
                return minuteCache.get(daily.getStockCode() + daily.getDate());
            } else {
                stockMinutes = stockMinuteService.fetchHistoryMinutes(dailys.get(dailyIndex + holdDay));
                minuteCache.put(daily.getStockCode() + daily.getDate(), stockMinutes);
            }
        }
        return stockMinutes;
    }

    /**
     * 初始化策略参数
     */
    private void initStrategyParameters() {

    }

}
