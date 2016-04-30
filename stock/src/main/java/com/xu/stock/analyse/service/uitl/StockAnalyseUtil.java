package com.xu.stock.analyse.service.uitl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xu.stock.analyse.model.StockWatchBegin;
import com.xu.stock.data.model.StockDaily;
import com.xu.util.DateUtil;

/**
 * 股票分析工具
 * 
 * @version
 * 
 * <pre>
 * Author	Version		Date		Changes
 * lunan.xu 	1.0  		2016年4月4日 	Created
 * </pre>
 * 
 * @since 1.
 */
public class StockAnalyseUtil {

    protected static Logger        log    = LoggerFactory.getLogger(StockAnalyseUtil.class);

    public static final BigDecimal BD_100 = BigDecimal.valueOf(100);

    /**
     * 是否整体上涨
     * 
     * @param dailys
     * @param index
     * @return
     */
    public static boolean isGeneralRise(List<StockDaily> dailys, int index, int riseDay, int serialDays) {
        for (int i = index + riseDay - serialDays; i < index + riseDay; i++) {
            if (dailys.get(i).getClose().compareTo(dailys.get(i + 1).getClose()) == 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否连接上涨
     * 
     * @param dailys
     * @param index
     * @return
     */
    public static boolean isSerialRise(List<StockDaily> dailys, int index, int riseDay) {
        for (int i = index; i < index + riseDay; i++) {
            if (dailys.get(i).getClose().compareTo(dailys.get(i + 1).getClose()) == 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * 增长是否达到预期
     * 
     * @param dailys
     * @param index
     * @return
     */
    public static boolean isRistExpected(List<StockDaily> dailys, int index, int riseDay, BigDecimal riseRate) {
        // 增长未达到预期
        BigDecimal fromPrice = dailys.get(index).getClose();
        BigDecimal allRise = dailys.get(index + riseDay).getClose().subtract(fromPrice);
        BigDecimal allRiseRate = allRise.multiply(BD_100).divide(fromPrice, 2, BigDecimal.ROUND_HALF_UP);
        return allRiseRate.compareTo(riseRate) >= 0;
    }

    /**
     * 前一天是否涨停
     * 
     * @param dailys
     * @param index
     * @return
     */
    public static boolean isLastDayRiseLimit(List<StockDaily> dailys, int index) {
        BigDecimal lastPrice = dailys.get(index - 1).getClose();
        StockDaily thisDaily = dailys.get(index);
        BigDecimal lastRise = thisDaily.getClose().subtract(lastPrice);
        BigDecimal lastRiseRate = lastRise.multiply(BD_100).divide(lastPrice, 2, BigDecimal.ROUND_HALF_UP);

        if (lastRiseRate.compareTo(BigDecimal.valueOf(Double.valueOf(9.8))) > 0 && thisDaily.getHigh().compareTo(thisDaily.getClose()) == 0) {
            return true;
        }

        if (lastRiseRate.compareTo(BigDecimal.valueOf(Double.valueOf(4.9))) > 0 && lastRiseRate.compareTo(BigDecimal.valueOf(Double.valueOf(5.1))) < 0 && thisDaily.getHigh().compareTo(thisDaily.getClose()) == 0) {
            return true;
        }

        return false;
    }

    public static Boolean isLimitUp(BigDecimal close, BigDecimal closeGapRate, BigDecimal low) {
        if (close.compareTo(low) != 0) {
            return false;
        }

        if (closeGapRate.compareTo(BigDecimal.valueOf(Double.valueOf(9.8))) > 0 || (closeGapRate.compareTo(BigDecimal.valueOf(Double.valueOf(4.9))) > 0 && closeGapRate.compareTo(BigDecimal.valueOf(Double.valueOf(5.1))) < 0)) {
            return true;
        }

        return false;
    }

    public static Boolean isLimitDown(BigDecimal close, BigDecimal closeGapRate, BigDecimal high) {
        if (close.compareTo(high) != 0) {
            return false;
        }
        if (closeGapRate.compareTo(BigDecimal.valueOf(Double.valueOf(-9.8))) < 0 || (closeGapRate.compareTo(BigDecimal.valueOf(Double.valueOf(-4.9))) < 0 && closeGapRate.compareTo(BigDecimal.valueOf(Double.valueOf(-5.1))) > 0)) {
            return true;
        }
        return false;
    }

    /**
     * 当前点是不是区间内最高点
     * 
     * @param stockDailys
     * @param index
     * @param date1
     * @param date2
     * @param thisFallRate
     * @return
     */
    public static boolean isHighest(List<StockDaily> stockDailys, int index, int date1, int date2) {

        for (int i = index - date1; i < index + date2; i++) {
            if (i < index) {
                if (stockDailys.get(i).getClose().multiply(stockDailys.get(i).getExrights()).compareTo(stockDailys.get(index).getClose().multiply(stockDailys.get(index).getExrights())) > 0) {
                    return false;
                }
            }
            if (i > index) {
                if (stockDailys.get(i).getClose().multiply(stockDailys.get(i).getExrights()).compareTo(stockDailys.get(index).getClose().multiply(stockDailys.get(index).getExrights())) >= 0) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * 是否是卖出日期
     * 
     * @param dailys
     * @param date
     * @param holdDay
     * @return
     */
    public static Boolean hasSellDate(List<StockDaily> dailys, Date date, int holdDay) {
        for (int i = 0; i < dailys.size(); i++) {
            StockDaily daily = dailys.get(i);
            if (DateUtil.dateToString(daily.getDate()).equals(DateUtil.dateToString(date))) {
                return i + holdDay < dailys.size() - 1;
            }
        }
        return false;
    }

    /**
     * 获取卖出日期的股票信息
     * 
     * @param dailys
     * @param date
     * @param holdDay
     * @return
     */
    public static StockDaily getSellDaily(List<StockDaily> dailys, Date date, int holdDay) {
        for (int i = 0; i < dailys.size(); i++) {
            StockDaily daily = dailys.get(i);
            if (DateUtil.dateToString(daily.getDate()).equals(DateUtil.dateToString(date))) {
                if (i + holdDay < dailys.size() - 1) {
                    return dailys.get(i + holdDay);
                } else if (i + holdDay == dailys.size() - 1) {
                    StockDaily lastDaily = dailys.get(i + holdDay);
                    StockDaily nextDaily = buildNextStockDaily(lastDaily);
                    return nextDaily;
                } else {
                    return null;
                }

            }
        }
        return null;
    }

    public static StockDaily buildNextStockDaily(StockDaily lastDaily) {
        StockDaily nextDaily = new StockDaily();
        nextDaily.setStockCode(lastDaily.getStockCode());
        nextDaily.setStockName(lastDaily.getStockName());
        nextDaily.setLastClose(lastDaily.getClose().multiply(lastDaily.getExrights()));
        nextDaily.setDate(DateUtil.stringToDate(DateUtil.date2String(new Date())));
        if (DateUtil.getSrvDate().equals(DateUtil.date2String(lastDaily.getDate()))) {
            nextDaily.setDate(DateUtil.addDay(nextDaily.getDate(), 1));
        }
        return nextDaily;
    }

    public static boolean isReachFallRate(List<StockDaily> stockDailys, int index, BigDecimal thisFallRate) {
        StockDaily highestStockDaily = stockDailys.get(index);
        BigDecimal highestCloseExr = highestStockDaily.getClose().multiply(highestStockDaily.getExrights());
        BigDecimal lowestCloseExr = highestCloseExr.subtract(highestCloseExr.multiply(thisFallRate).divide(BD_100, 2, BigDecimal.ROUND_HALF_UP));
        for (int i = index; i < stockDailys.size(); i++) {// 从指定点开始遍历
            StockDaily thisDaliy = stockDailys.get(i);
            BigDecimal thisCloseExr = thisDaliy.getClose().multiply(thisDaliy.getExrights());

            // 如果当前已大于历史高点
            if (thisCloseExr.compareTo(highestCloseExr) == 1) {
                return false;
            }
            // 本次跌幅超设定幅度，与最高点相差比例介于设定的报警范围内，当前最高价小于历史最高价
            if (thisCloseExr.compareTo(lowestCloseExr) == -1) {
                return true;
            }
        }
        return false;
    }

    public static Integer dailyIndex(List<StockDaily> dailys, Date date) {
        if (dailys != null) {
            int low = 0;
            int high = dailys.size() - 1;

            while ((low <= high) && (low <= dailys.size() - 1) && (high <= dailys.size() - 1)) {
                int middle = (high + low) >> 1;
                StockDaily daily = dailys.get(middle);
                int compare = DateUtil.dateToString(daily.getDate()).compareTo(DateUtil.dateToString(date));
                if (compare == 0) {
                    return middle;
                } else if (compare > 0) {
                    high = middle - 1;
                } else {
                    low = middle + 1;
                }
            }
        }
        return null;
    }

    public static boolean hasNextDaily(List<StockDaily> dailys, StockWatchBegin watchBegin) {
        if (dailys != null && dailys.size() > 1) {
            StockDaily lastDaily = dailys.get(dailys.size() - 1);
            int compare = DateUtil.dateToString(lastDaily.getDate()).compareTo(DateUtil.dateToString(watchBegin.getDate()));
            return compare > 0;
        }
        return false;
    }
}
