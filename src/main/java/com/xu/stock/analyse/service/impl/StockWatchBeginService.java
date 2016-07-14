package com.xu.stock.analyse.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xu.stock.analyse.dao.IStockHighestDao;
import com.xu.stock.analyse.dao.IStockWatchBeginDao;
import com.xu.stock.analyse.model.StockHighest;
import com.xu.stock.analyse.model.StockWatchBegin;
import com.xu.stock.analyse.service.IStockWatchBeginService;
import com.xu.stock.analyse.service.StockAnalyseConstants.HighestAnalyseStatus;
import com.xu.stock.analyse.service.StockAnalyseConstants.StrategyType;
import com.xu.stock.analyse.service.StockAnalyseConstants.WatchBeginStatus;
import com.xu.stock.analyse.service.uitl.StockAnalyseUtil;
import com.xu.stock.data.model.StockDaily;
import com.xu.util.DateUtil;

/**
 * 盯盘开始点
 * 
 * @version
 * 
 * <pre>
 * Author	Version		Date		Changes
 * lunan.xu 	1.0  		2016年4月26日 	Created
 * </pre>
 * 
 * @since 1.
 */
@SuppressWarnings("restriction")
@Service("stockWatchBeginService")
public class StockWatchBeginService implements IStockWatchBeginService {

    protected Logger               log    = LoggerFactory.getLogger(this.getClass());
    public static final BigDecimal BD_100 = BigDecimal.valueOf(100);
    public static final BigDecimal BD_10  = BigDecimal.valueOf(10);
    public static final BigDecimal BD_1   = BigDecimal.valueOf(1);
    @Resource
    private IStockHighestDao       stockHighestDao;
    @Resource
    private IStockWatchBeginDao    stockWatchBeginDao;

    @Override
    public void analyseBatchBeginByHighest(List<StockDaily> dailys, Integer lastWaveCycle, Integer thisWaveCycle, BigDecimal thisFallRate) {
        Map<String, StockDaily> lastHigherCache = new HashMap<String, StockDaily>();
        String parameters = StockAnalyseUtil.buildParameter(lastWaveCycle, thisWaveCycle, thisFallRate);
        List<StockHighest> highestPoints = stockHighestDao.getHighests(dailys.get(dailys.size() - 1), parameters);
        for (StockHighest highest : highestPoints) {// 遍历所有最高点
            int i = Integer.valueOf(highest.getParameters().substring(highest.getParameters().lastIndexOf(",") + 1));
            BigDecimal highestCloseExr = highest.getClose().multiply(highest.getExrights());
            Integer index = StockAnalyseUtil.dailyIndex(dailys, highest.getAnalyseDate());
            for (int j = index + 1; j < dailys.size(); j++) {// 从指定日期开始遍历
                StockDaily thisDaily = dailys.get(j);
                if (highest.getParameters().equals("20,20,30.0,0") && "2015-11-25".equals(DateUtil.date2String(highest.getDate())) && "2016-07-04".equals(DateUtil.date2String(thisDaily.getDate()))) {
                    log.info("");
                }
                highest.setAnalyseDate(thisDaily.getDate());
                StockDaily higherDate = StockAnalyseUtil.getLastHigher(dailys, thisDaily, lastHigherCache);
                if (higherDate.getDate().after(highest.getDate())) {
                    continue;
                }

                BigDecimal thisCloseExr = thisDaily.getClose().multiply(thisDaily.getExrights());
                BigDecimal currentHighestExr = thisDaily.getHigh().multiply(thisDaily.getExrights());
                BigDecimal buyRateHigh = BigDecimal.valueOf(0 - i);
                BigDecimal high = BD_100.subtract(buyRateHigh);
                if (i > 0) {
                    high = BD_100.multiply(BigDecimal.valueOf(1.01).pow(i)).setScale(0, BigDecimal.ROUND_HALF_UP);
                    buyRateHigh = BD_100.subtract(high);
                }
                BigDecimal buyRateLow = BD_100.subtract(high.multiply(BD_100.subtract(BD_1)).divide(BD_100, 1, BigDecimal.ROUND_HALF_UP));
                BigDecimal warnRateLow = BD_100.subtract(high.multiply(BD_100.subtract(BD_10)).divide(BD_100, 1, BigDecimal.ROUND_HALF_UP));
                BigDecimal warnLowExr = highestCloseExr.subtract(highestCloseExr.multiply(warnRateLow).divide(BD_100, 2, BigDecimal.ROUND_HALF_UP));
                BigDecimal buyLowExr = highestCloseExr.subtract(highestCloseExr.multiply(buyRateLow).divide(BD_100, 2, BigDecimal.ROUND_HALF_UP));
                BigDecimal buyHighExr = highestCloseExr.subtract(highestCloseExr.multiply(buyRateHigh).divide(BD_100, 2, BigDecimal.ROUND_HALF_UP));
                BigDecimal highestExr = highestCloseExr.compareTo(buyHighExr) == 1 ? highestCloseExr : buyHighExr;
                List<StockWatchBegin> watchBegins = new ArrayList<StockWatchBegin>();
                if (highest.getWatchBegins() == null) {
                    highest.setWatchBegins(new ArrayList<StockWatchBegin>());
                }

                // 当前价高于设定范围
                if (thisCloseExr.compareTo(buyHighExr) == 1) {
                    highest.setAnalyseStatus(HighestAnalyseStatus.UNBUYABLE);
                    break;
                }

                // 本次跌幅超设定幅度，大于最低预警值
                if (thisCloseExr.compareTo(warnLowExr) >= 0) {
                    if (thisCloseExr.compareTo(buyLowExr) >= 0 && thisCloseExr.compareTo(buyHighExr) <= 0 && currentHighestExr.compareTo(highestExr) <= 0) {
                        highest.setAnalyseStatus(HighestAnalyseStatus.UNBUYABLE);
                        break;// 当天是最佳时机，后面跳过
                    } else if (thisCloseExr.compareTo(buyHighExr) <= 0) {
                        String thisParameters = StockAnalyseUtil.buildParameter(parameters, warnRateLow, buyRateLow, buyRateHigh);
                        StockWatchBegin watchBegin = buildWatchBegin(highest, thisDaily, thisParameters, buyLowExr, buyHighExr, highestExr);
                        watchBegins.add(watchBegin);
                        highest.setAnalyseStatus(HighestAnalyseStatus.BUYABLE);
                    }
                }
                stockWatchBeginDao.saveWatchBegins(watchBegins);
            }
            if (HighestAnalyseStatus.ANALYZING.equals(highest.getAnalyseStatus())) {
                highest.setAnalyseDate(dailys.get(dailys.size() - 1).getDate());
            }
            stockHighestDao.updateHighestAnalyse(highest);
        }
    }

    private StockWatchBegin buildWatchBegin(StockHighest highest, StockDaily thisDaliy, String parameters, BigDecimal buyLowExr, BigDecimal buyHighExr, BigDecimal highestCloseExr) {
        StockWatchBegin watchBegin = new StockWatchBegin();
        watchBegin.setStockCode(highest.getStockCode());
        watchBegin.setStockName(highest.getStockName());
        watchBegin.setStrategy(StrategyType.HIGHEST_PROBE_BUY.toString());
        watchBegin.setParameters(parameters);
        watchBegin.setRefDate(highest.getDate());
        watchBegin.setRefClose(highest.getClose());
        watchBegin.setRefExrights(highest.getExrights());
        watchBegin.setDate(thisDaliy.getDate());
        watchBegin.setClose(thisDaliy.getClose());
        watchBegin.setExrights(thisDaliy.getExrights());
        watchBegin.setBuyRefLowExr(buyLowExr);
        watchBegin.setBuyRefHighExr(buyHighExr);
        watchBegin.setBuyRefCloseExr(highestCloseExr);
        watchBegin.setAnalyseStatus(WatchBeginStatus.ANALYZING);

        log.info("watch begin\t" + highest.getStockCode() + "\t" + DateUtil.date2String(highest.getDate()) + "\t" + DateUtil.date2String(thisDaliy.getDate()) + "\t" + parameters);
        return watchBegin;
    }
}
