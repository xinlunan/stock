package com.xu.stock.analyse.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        if (lastRiseRate.compareTo(BigDecimal.valueOf(Double.valueOf(4.9))) > 0 && lastRiseRate.compareTo(BigDecimal.valueOf(Double.valueOf(5.1))) < 0
            && thisDaily.getHigh().compareTo(thisDaily.getClose()) == 0) {
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
     * @return
     */
    public static boolean isHighest(List<StockDaily> stockDailys, int index, int date1, int date2) {
        for (int i = index - date1; i < index + date2; i++) {
            if (i < index) {
                if (stockDailys.get(i).getClose().compareTo(stockDailys.get(index).getClose()) > 0) {
                    return false;
                }
            }
            if (i > index) {
                if (stockDailys.get(i).getClose().compareTo(stockDailys.get(index).getClose()) >= 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 获取卖出日期的股票信息
     * 
     * @param dailys
     * @param date
     * @param holdDay
     * @return
     */
    public static StockDaily getSellStockDaily(List<StockDaily> dailys, Date date, int holdDay) {
        for (int i = 0; i < dailys.size() - 1; i++) {
            StockDaily daily = dailys.get(i);
            if (DateUtil.dateToString(daily.getDate()).equals(DateUtil.dateToString(date))) {
                return dailys.get(i + holdDay);
            }
        }
        return null;
    }

}
