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
import com.xu.stock.analyse.model.StockTradeBuy;
import com.xu.stock.analyse.model.StockWatchBegin;
import com.xu.stock.analyse.service.IStockTradeBuyService;
import com.xu.stock.analyse.service.StockAnalyseConstants.StockTradeBuyAnalyseType;
import com.xu.stock.analyse.service.StockAnalyseConstants.StockTradeBuyStatus;
import com.xu.stock.analyse.service.StockAnalyseConstants.StrategyType;
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
    private IStockTradeBuyDao   stockTradeBuyDao;
    @Resource
    private IStockWatchBeginDao stockWatchBeginDao;

    public void analyseStockTradeBuy(List<StockDaily> dailys, String parameters) {
        List<StockWatchBegin> watchBegins = stockWatchBeginDao.getUnAnalyseWatchBegins(StrategyType.HIGHEST_PROBE_BUY, parameters, dailys.get(0).getStockCode());
        List<StockTradeBuy> buys = new ArrayList<StockTradeBuy>();
        for (StockWatchBegin watchBegin : watchBegins) {
            if (WatchBeginStatus.ANALYZING.equals(watchBegin.getAnalyseStatus())) {
                StockMinute stockMinute = fetchStockMinute(dailys, watchBegin);
                if (stockMinute != null) {
                    BigDecimal priceExr = stockMinute.getPrice().multiply(stockMinute.getExrights());
                    BigDecimal highPriceExr = stockMinute.getHigh().multiply(stockMinute.getExrights());
                    if (priceExr.compareTo(watchBegin.getBuyRefLowExr()) >= 0 && priceExr.compareTo(watchBegin.getBuyRefHighExr()) <= 0 && highPriceExr.compareTo(watchBegin.getBuyRefCloseExr()) <= 0) {
                        buys.add(buildStockTradeBuy(watchBegin, stockMinute, TradeNature.VIRTUAL));
                        watchBegin.setAnalyseStatus(WatchBeginStatus.TRADED);
                    } else {
                        watchBegin.setAnalyseStatus(WatchBeginStatus.UNTRADED);
                    }
                } else {
                    // 如果停牌，则不关注，避免每次下载数据，浪费网络
                    watchBegin.setAnalyseStatus(WatchBeginStatus.UNTRADED);
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

        StockMinute stockMinute = null;
        int dailyIndex = StockAnalyseUtil.dailyIndex(dailys, watchBegin.getDate());
        if (dailyIndex < dailys.size() - 1) {// 下一天也在历史中
            StockDaily nextDaily = dailys.get(dailyIndex + 1);
            stockMinute = stockMinuteService.fetchHistoryNearCloseBuyMinute(nextDaily);
            if (stockMinute == null) {
                stockMinute = StockAnalyseUtil.buildStockMinute(nextDaily);
            }
            if (stockMinute.getHigh().compareTo(stockMinute.getPrice()) == 0) {
                BigDecimal thisPriceExr = stockMinute.getPrice().multiply(stockMinute.getExrights());
                BigDecimal thisHighPriceExr = stockMinute.getHigh().multiply(stockMinute.getExrights());
                BigDecimal lastPriceExr = nextDaily.getLastClose().multiply(nextDaily.getExrights());
                BigDecimal rate = thisPriceExr.subtract(lastPriceExr).multiply(BigDecimal.valueOf(100)).divide(lastPriceExr, 4, BigDecimal.ROUND_HALF_UP);
                if (StockAnalyseUtil.isLimitUp(thisPriceExr, rate, thisHighPriceExr)) {
                    stockMinute = null;
                }
            }
        } else {
            stockMinute = stockMinuteService.fetchRealtimeBuyMinute(watchBegin);
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

}
