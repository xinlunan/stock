package com.xu.stock.analyse.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xu.stock.analyse.dao.IStockTradeBuyDao;
import com.xu.stock.analyse.dao.IStockWatchBeginDao;
import com.xu.stock.analyse.model.StockHighest;
import com.xu.stock.analyse.model.StockTrade;
import com.xu.stock.analyse.model.StockTradeBuy;
import com.xu.stock.analyse.model.StockWatchBegin;
import com.xu.stock.analyse.service.IStockTradeBuyService;
import com.xu.stock.analyse.service.StockAnalyseConstants.StockTradeBuyAnalyseType;
import com.xu.stock.analyse.service.StockAnalyseConstants.StockTradeBuyStatus;
import com.xu.stock.analyse.service.StockAnalyseConstants.TradeNature;
import com.xu.stock.analyse.service.StockAnalyseConstants.WatchBeginStatus;
import com.xu.stock.analyse.service.uitl.StockAnalyseUtil;
import com.xu.stock.data.model.StockDaily;
import com.xu.stock.data.model.StockMinute;
import com.xu.stock.data.service.IStockMinuteService;

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
@Service("stockTradeBuyService")
public class StockTradeBuyService implements IStockTradeBuyService {

    protected Logger            log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private IStockMinuteService stockMinuteService;
    @Resource
    private IStockTradeBuyDao        stockTradeBuyDao;
    @Resource
    private IStockWatchBeginDao stockWatchBeginDao;

    public void analyseStockTradeBuy(List<StockDaily> dailys, List<StockWatchBegin> watchBegins) {
        List<StockTradeBuy> buys = new ArrayList<StockTradeBuy>();
        for (StockWatchBegin watchBegin : watchBegins) {
            if (WatchBeginStatus.ANALYZING.equals(watchBegin.getAnalyseStatus())) {
                StockMinute stockMinute = fetchStockMinute(dailys, watchBegin);
                if (stockMinute != null) {
                    BigDecimal priceExr = stockMinute.getPrice().multiply(stockMinute.getExrights());
                    if (priceExr.compareTo(watchBegin.getBuyRefLowExr()) == 1 && priceExr.compareTo(watchBegin.getBuyRefHighExr()) == -1) {
                        buys.add(buildStockTradeBuy(watchBegin, stockMinute, TradeNature.VIRTUAL));
                        watchBegin.setAnalyseStatus(WatchBeginStatus.TRADED);
                    } else {
                        watchBegin.setAnalyseStatus(WatchBeginStatus.UNTRADED);
                    }
                }
                stockWatchBeginDao.updateStatus(watchBegin);
            }
        }
        stockTradeBuyDao.saveStockTradeBuys(buys);
    }

    /**
     * 获取分时信息
     * 
     * @param dailys
     * @param watchBegin
     * @return
     */
    private StockMinute fetchStockMinute(List<StockDaily> dailys, StockWatchBegin watchBegin) {
        StockMinute stockMinute;
        int dailyIndex = StockAnalyseUtil.dailyIndex(dailys, watchBegin.getDate());
        if (dailyIndex < dailys.size() - 1) {// 下一天也在历史中
            StockDaily nextDaily = dailys.get(dailyIndex + 1);
            stockMinute = stockMinuteService.fetchHistoryNearCloseBuyMinute(nextDaily);
            if (stockMinute == null) {
                stockMinute = buildStockMinute(dailys.get(dailyIndex));
            }
        } else {
            stockMinute = stockMinuteService.fetchRealNearCloseBuyMinute(watchBegin);
        }
        return stockMinute;
    }

    private StockTradeBuy buildStockTradeBuy(StockWatchBegin watchBegin, StockMinute stockMinute, String nature) {
        StockTradeBuy stockTradeBuy = new StockTradeBuy();
        stockTradeBuy.setStockCode(watchBegin.getStockCode());
        stockTradeBuy.setStockName(watchBegin.getStockName());
        stockTradeBuy.setStrategy(watchBegin.getStrategy());
        stockTradeBuy.setParameters(watchBegin.getParameters());
        stockTradeBuy.setNature(nature);
        stockTradeBuy.setStatus(StockTradeBuyStatus.BOUGHT);
        stockTradeBuy.setLastDate(watchBegin.getDate());
        stockTradeBuy.setDate(stockMinute.getDate());
        stockTradeBuy.setHour(stockMinute.getHour());
        stockTradeBuy.setMinute(stockMinute.getMinute());
        stockTradeBuy.setPrice(stockMinute.getPrice());
        stockTradeBuy.setExrights(stockMinute.getExrights());
        stockTradeBuy.setAnalyseType(stockMinute.getHour() == 15 ? StockTradeBuyAnalyseType.CLOSE : StockTradeBuyAnalyseType.REALTIME);
        return stockTradeBuy;
    }

    private StockMinute buildStockMinute(StockDaily daily) {
        StockMinute stockMinute = new StockMinute();
        stockMinute.setDate(daily.getDate());
        stockMinute.setHour(15);
        stockMinute.setMinute(0);
        stockMinute.setPrice(daily.getClose());
        stockMinute.setHigh(daily.getHigh());
        stockMinute.setExrights(daily.getExrights());
        return stockMinute;
    }

    /**
     * 分析购买的时间点
     * 
     * @param dailys
     * @param watchBegins
     * @return
     */
    private void scanBuyMinutePoints(List<StockDaily> dailys, List<StockWatchBegin> watchBegins) {

        for (StockWatchBegin highestPoint : watchBegins) {
            // List<StockDaily> watchBegins = highestPoint.getWatchBegins();
            //
            // for (StockDaily daily : watchBegins) {
            // BigDecimal buyLowExr =
            // highestPoint.getClose().subtract(highestPoint.getClose().multiply(buyRateLow).divide(BD_100)).multiply(highestPoint.getExrights());
            // BigDecimal buyHighExr =
            // highestPoint.getClose().subtract(highestPoint.getClose().multiply(buyRateHigh).divide(BD_100)).multiply(highestPoint.getExrights());
            //
            // StockMinute buyMinute =
            // stockMinuteService.getNearCloseBuyMinute(daily);
            // if (buyMinute != null &&
            // buyLowExr.compareTo(BigDecimal.valueOf(buyMinute.getPrice() *
            // buyMinute.getExrights())) < 0 &&
            // buyHighExr.compareTo(BigDecimal.valueOf(buyMinute.getPrice() *
            // buyMinute.getExrights())) < 0) {
            // buyMinute.setStockDaily(daily);
            // daily.getMinutes().add(buyMinute);
            // }
            // }
        }
    }

    /**
     * 分析购买的时间点
     * 
     * @param buyPoints
     * @param buyDailyPoints
     * @return
     */
    private List<StockTrade> buildStockSimulateTrades(List<StockHighest> highestPoints) {
        List<StockTrade> trades = new ArrayList<StockTrade>();
        for (StockHighest highest : highestPoints) {
            // for (StockDaily stockDaily : highest.getWatchBegins()) {
            // StockTrade trade = new StockTrade();
            //
            // List<StockMinute> minutes = stockDaily.getMinutes();
            // if (!minutes.isEmpty()) {
            // StockMinute stockMinute = minutes.get(0);
            //
            // trade.setStockCode(stockDaily.getStockCode());
            // trade.setStockName(stockDaily.getStockName());
            //
            // trade.setBuyDate(stockDaily.getDate());
            // trade.setExrights(BigDecimal.valueOf(stockMinute.getExrights()).setScale(4,
            // BigDecimal.ROUND_HALF_UP));
            // trade.setBuyHour(15);
            // trade.setBuyMinute(0);
            // trade.setBuyTradePrice(stockDaily.getClose());
            // trade.setBuyHighPrice(stockDaily.getHigh());
            // trade.setBuyClosePrice(stockDaily.getClose());
            //
            // trade.setSellDate(stockMinute.getDate());
            // trade.setSellHour(stockMinute.getHour());
            // trade.setSellMinute(stockMinute.getMinute());
            // trade.setSellTradePrice(BigDecimal.valueOf(0));
            // trade.setSellHighPrice(BigDecimal.valueOf(0));
            // trade.setSellClosePrice(BigDecimal.valueOf(0));
            //
            // trade.setProfit(BigDecimal.valueOf(0));
            // trade.setProfitRate(BigDecimal.valueOf(0));
            // trade.setHighProfitRate(BigDecimal.valueOf(0));
            // trade.setCloseProfitRate(BigDecimal.valueOf(0));
            //
            // trade.setTradeType(TradeType.BUY);
            // trade.setTradeNature(TradeNature.VIRTUAL);
            // trade.setStrategy(StrategyType.HIGHEST_PROBE_BUY.toString());
            // trade.setVersion(strategy.getVersion());
            // trade.setParameters(strategy.getParameters());
            // log.info(trade.toString());
            //
            // trades.add(trade);
            // }
            // }
        }
        return trades;
    }

}
