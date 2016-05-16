package com.xu.stock.analyse.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    @Resource
    private IStockHighestDao       stockHighestDao;
    @Resource
    private IStockWatchBeginDao    stockWatchBeginDao;

    public void analyseBatchBeginByHighest(List<StockDaily> dailys, String parameters, BigDecimal thisFallRate, BigDecimal warnRateLow, BigDecimal buyRateLow, BigDecimal buyRateHigh) {
        List<StockHighest> highestPoints = stockHighestDao.getHighests(dailys.get(0).getStockCode(), parameters);
        for (StockHighest highest : highestPoints) {
            if (HighestAnalyseStatus.ANALYZING.equals(highest.getAnalyseStatus())) {
                Integer index = StockAnalyseUtil.dailyIndex(dailys, highest.getAnalyseDate());
                StockDaily highestStockDaily = dailys.get(index);
                BigDecimal highestCloseExr = highestStockDaily.getClose().multiply(highestStockDaily.getExrights());
                BigDecimal lowestCloseExr = highestCloseExr.subtract(highestCloseExr.multiply(thisFallRate).divide(BD_100, 2, BigDecimal.ROUND_HALF_UP));
                BigDecimal warnLowExr = highestCloseExr.subtract(highestCloseExr.multiply(warnRateLow).divide(BD_100, 2, BigDecimal.ROUND_HALF_UP));
                BigDecimal buyLowExr = highestCloseExr.subtract(highestCloseExr.multiply(buyRateLow).divide(BD_100, 2, BigDecimal.ROUND_HALF_UP));
                BigDecimal buyHighExr = highestCloseExr.subtract(highestCloseExr.multiply(buyRateHigh).divide(BD_100, 2, BigDecimal.ROUND_HALF_UP));
                BigDecimal lowestExr = BigDecimal.valueOf(Integer.MAX_VALUE);
                highestCloseExr = highestCloseExr.compareTo(buyHighExr) == 1 ? highestCloseExr : buyHighExr;
                List<StockWatchBegin> watchBegins = new ArrayList<StockWatchBegin>();
                for (int i = index + 1; i < dailys.size(); i++) {// 从指定点开始遍历
                    StockDaily thisDaliy = dailys.get(i);
                    highest.setAnalyseDate(thisDaliy.getDate());
                    if (highest.getWatchBegins() == null) {
                        highest.setWatchBegins(new ArrayList<StockWatchBegin>());
                    }
                    BigDecimal thisCloseExr = thisDaliy.getClose().multiply(thisDaliy.getExrights());
                    BigDecimal currentHighestExr = thisDaliy.getHigh().multiply(thisDaliy.getExrights());

                    // 如果当前价小于最低价
                    if (thisCloseExr.compareTo(lowestExr) == -1) {
                        lowestExr = thisDaliy.getClose().multiply(thisDaliy.getExrights());
                    }
                    // 当前价高于设定范围
                    if (thisCloseExr.compareTo(buyHighExr) == 1) {
                        if (highest.getWatchBegins().isEmpty() && watchBegins.isEmpty()) {
                            highest.setAnalyseStatus(HighestAnalyseStatus.UNBUYABLE);
                        } else {
                            highest.setAnalyseStatus(HighestAnalyseStatus.BUYABLE);
                        }
                        break;
                    }

                    // 本次跌幅超设定幅度，大于最低预警值
                    if (lowestCloseExr.compareTo(lowestExr) >= 0 && thisCloseExr.compareTo(warnLowExr) >= 0) {
                        if (thisCloseExr.compareTo(buyLowExr) >= 0 && thisCloseExr.compareTo(buyHighExr) <= 0 && currentHighestExr.compareTo(highestCloseExr) <= 0) {
                            // 当天是最佳时机，后面跳过
                            break;
                        } else if (thisCloseExr.compareTo(buyHighExr) <= 0) {
                            StockWatchBegin watchBegin = buildWatchBegin(highest, thisDaliy, parameters, buyLowExr, buyHighExr, highestCloseExr);
                            watchBegins.add(watchBegin);
                        }
                    }

                    // // 过滤昨天已成交
                    // StockDaily lastDaliy = dailys.get(i - 1);
                    // BigDecimal lastCloseExr =
                    // lastDaliy.getClose().multiply(lastDaliy.getExrights());
                    // BigDecimal lastHighExr =
                    // lastDaliy.getHigh().multiply(lastDaliy.getExrights());
                    // if (lastCloseExr.compareTo(buyLowExr) == 1 &&
                    // lastCloseExr.compareTo(buyHighExr) == -1 &&
                    // lastHighExr.compareTo(highestCloseExr) <= 0) {
                    // break;
                    // }
                    //
                    // // 本次跌幅超设定幅度，与最高点相差比例介于设定的报警范围内，当前最高价小于历史最高价
                    // if (lowestCloseExr.compareTo(lowestExr) == 1 &&
                    // thisCloseExr.compareTo(warnLowExr) >= 0 &&
                    // thisCloseExr.compareTo(buyHighExr) <= 0 &&
                    // currentHighestExr.compareTo(highestCloseExr) <= 0) {
                    // StockWatchBegin watchBegin = buildWatchBegin(highest,
                    // thisDaliy, parameters, buyLowExr, buyHighExr);
                    // watchBegins.add(watchBegin);
                    // }
                }
                stockWatchBeginDao.saveWatchBegins(watchBegins);
                stockHighestDao.updateHighestAnalyse(highest);
            }
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

        log.info("watch begin\t" + highest.getStockCode() + "\t" + DateUtil.date2String(highest.getDate()) + "\t" + DateUtil.date2String(thisDaliy.getDate()));
        return watchBegin;
    }
}
