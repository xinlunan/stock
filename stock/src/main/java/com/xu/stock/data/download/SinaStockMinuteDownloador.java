package com.xu.stock.data.download;

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
public class SinaStockMinuteDownloador {

    protected static Logger log = LoggerFactory.getLogger(SinaStockMinuteDownloador.class);

    /**
     * 下载股票指数数据
     * 
     * @param stockDaily
     * @param date
     * @return
     */
    public static List<StockMinute> download(StockDaily stockDaily, Date date) {
        String url = buildRepairUrl(stockDaily.getStockCode(), date);

        String result = HttpClientHandle.get(url, "gb2312");

        return parseStockMinutes(result, stockDaily, date);
    }

    /**
     * 解析分时信息
     * 
     * @param path
     * @param stockDaily
     * @param date
     * @return
     */
    private static List<StockMinute> parseStockMinutes(String result, StockDaily stockDaily, Date date) {
        List<StockMinute> minutes = new ArrayList<StockMinute>();

        if (result.startsWith("成交时间")) {
            String[] tradeStr = result.split("\n");
            for (int i = tradeStr.length - 2; i > 0; i--) {
                String[] infos = tradeStr[i].split("\t");
                if (!updateStockMinute(infos, minutes)) {
                    StockMinute minuteInfo = newStockMinute(infos, stockDaily);
                    minutes.add(minuteInfo);
                }
            }
        } else {
            log.error("下载数据不成功");
        }

        return minutes;
    }

    private static boolean updateStockMinute(String[] tradeInfos, List<StockMinute> minutes) {
        if (minutes.isEmpty()) {
            return false;
        }
        Integer hour = Integer.valueOf(tradeInfos[0].substring(0, 2));
        Integer minute = Integer.valueOf(tradeInfos[0].substring(3, 5));
        StockMinute stockMinute = minutes.get(minutes.size() - 1);

        if (stockMinute.getHour() == hour && stockMinute.getMinute() == minute) {
            Double volume = Double.valueOf(tradeInfos[3]);
            Double amount = Double.valueOf(tradeInfos[4]) / 10000;
            stockMinute.setVolume(volume + stockMinute.getVolume());
            stockMinute.setAmount(amount + stockMinute.getAmount());
            return true;
        }
        return false;
    }

    private static StockMinute newStockMinute(String[] tradeInfos, StockDaily stockDaily) {
        Integer hour = Integer.valueOf(tradeInfos[0].substring(0, 2));
        Integer minute = Integer.valueOf(tradeInfos[0].substring(3, 5));
        Double price = Double.valueOf(tradeInfos[1]);
        Double volume = Double.valueOf(tradeInfos[3]);
        Double amount = Double.valueOf(tradeInfos[4]) / 10000;

        StockMinute stockMinute = new StockMinute();
        stockMinute.setStockCode(stockDaily.getStockCode());
        stockMinute.setDate(stockDaily.getDate());
        stockMinute.setHour(hour);
        stockMinute.setMinute(minute);
        stockMinute.setPrice(price);
        stockMinute.setVolume(volume);
        stockMinute.setAmount(amount);
        stockMinute.setExrights(stockDaily.getExrights().doubleValue());
        return stockMinute;
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
        url.append(StockApiConstants.Sina.API_URL_REPAIR_STOCK_INDEX).append("?symbol=").append(fullStockCode);
        url.append("&date=").append(date);
        return url.toString();
    }
}
