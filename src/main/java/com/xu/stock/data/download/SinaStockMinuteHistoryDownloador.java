package com.xu.stock.data.download;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xu.stock.data.model.StockDaily;
import com.xu.stock.data.model.StockMinute;
import com.xu.util.DateUtil;
import com.xu.util.HttpClientHandle;

/**
 * 新浪分时指数下载
 * 
 * @version
 * 
 * <pre>
 * Author	Version		Date		Changes
 * lunan.xu 	1.0  		2016年4月9日 	Created
 * </pre>
 * 
 * @since 1.
 */
public class SinaStockMinuteHistoryDownloador {

    protected static Logger log = LoggerFactory.getLogger(SinaStockMinuteHistoryDownloador.class);

    /**
     * 下载股票指数数据
     * 
     * @param daily
     * @return
     */
    public static List<StockMinute> download(StockDaily daily) {
        String url = buildRepairUrl(daily.getStockCode(), daily.getDate());

        String result = HttpClientHandle.get(url, "gb2312");

        return parseStockMinutes(result, daily);
    }

    /**
     * 解析分时信息
     * 
     * @param path
     * @param stockDaily
     * @param date
     * @param exrights
     * @return
     */
    private static List<StockMinute> parseStockMinutes(String result, StockDaily stockDaily) {
        List<StockMinute> minutes = new ArrayList<StockMinute>();

        if (result.startsWith("成交时间")) {
            String[] tradeStr = result.split("\n");
            Double high = Double.MIN_VALUE;
            for (int i = tradeStr.length - 2; i > 0; i--) {
                String[] infos = tradeStr[i].split("\t");
                if (!updateStockMinute(infos, minutes)) {
                    StockMinute minuteInfo = newStockMinute(infos, stockDaily, high);
                    minutes.add(minuteInfo);
                }
            }
        } else {
            log.error("下载数据不成功\t" + stockDaily.getStockCode() + "\t" + DateUtil.date2String(stockDaily.getDate()) + "\t" + result);
        }

        return minutes;
    }

    private static boolean updateStockMinute(String[] tradeInfos, List<StockMinute> minutes) {
        if (minutes.isEmpty() || tradeInfos[0].lastIndexOf(":") < 4) {
            return false;
        }
        Integer hour = Integer.valueOf(tradeInfos[0].substring(0, 2));
        Integer minute = Integer.valueOf(tradeInfos[0].substring(3, 5));
        for (StockMinute stockMinute : minutes) {
            if (stockMinute.getHour() == hour && stockMinute.getMinute() == minute) {
                Double volume = Double.valueOf(tradeInfos[3]);
                Double amount = Double.valueOf(tradeInfos[4]) / 10000;
                stockMinute.setVolume(volume + stockMinute.getVolume());
                stockMinute.setAmount(amount + stockMinute.getAmount());
                return true;
            }
        }
        return false;
    }

    private static StockMinute newStockMinute(String[] tradeInfos, StockDaily stockDaily, Double high) {
        if (tradeInfos[0].lastIndexOf(":") > 4) {

            Integer hour = Integer.valueOf(tradeInfos[0].substring(0, 2));
            Integer minute = Integer.valueOf(tradeInfos[0].substring(3, 5));
            Double price = Double.valueOf(tradeInfos[1]);
            Double volume = Double.valueOf(tradeInfos[3]);
            Double amount = Double.valueOf(tradeInfos[4]) / 10000;

            if (price > high) {
                high = price;
            }

            StockMinute stockMinute = new StockMinute();
            stockMinute.setStockCode(stockDaily.getStockCode());
            stockMinute.setDate(stockDaily.getDate());
            stockMinute.setHour(hour);
            stockMinute.setMinute(minute);
            stockMinute.setPrice(BigDecimal.valueOf(price));
            stockMinute.setHigh(BigDecimal.valueOf(high));
            stockMinute.setVolume(volume);
            stockMinute.setAmount(amount);
            stockMinute.setExrights(stockDaily.getExrights());
            return stockMinute;
        }
        return null;
    }

    /**
     * 修复股票数据库地址
     * "http://market.finance.sina.com.cn/downxls.php?date=2011-07-08&symbol=sh600900"
     * 
     * @param stockCode
     * @param nextDate
     * @return
     */
    private static String buildRepairUrl(String stockCode, Date nextDate) {
        String fullStockCode = stockCode.startsWith("6") ? "sh" + stockCode : "sz" + stockCode;

        String date = DateUtil.getDate(nextDate, "yyyy-MM-dd");

        StringBuffer url = new StringBuffer();
        url.append(StockApiConstants.Sina.API_URL_REPAIR_STOCK_MINUTE_INDEX).append("?symbol=").append(fullStockCode);
        url.append("&date=").append(date);
        return url.toString();
    }
}
