package com.xu.stock.data.download;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xu.stock.data.model.StockMinute;
import com.xu.util.DateUtil;
import com.xu.util.HttpClientHandle;

/**
 * 新浪实时数据下载
 * 
 * @version
 * 
 * <pre>
 * Author	Version		Date		Changes
 * lunan.xu 	1.0  		2016年4月29日 	Created
 * </pre>
 * 
 * @since 1.
 */
public class SinaStockMinuteRealtimeDownloador {

    protected static Logger log = LoggerFactory.getLogger(SinaStockMinuteRealtimeDownloador.class);

    /**
     * 下载股票指数数据
     * 
     * @param watch
     * @return
     */
    public static StockMinute download(String stockCode, BigDecimal lastClose, BigDecimal lastExrights) {
        String fullStockCode = stockCode.startsWith("6") ? "sh" + stockCode : "sz" + stockCode;
        String url = StockApiConstants.Sina.API_URL_STOCK_REALTIME_DETAL + fullStockCode;
        String result = HttpClientHandle.get(url, "gb2312");
        if (result.length() > 30) {
            String[] infos = result.split(",");
            String[] times = infos[31].split(":");
            if (!infos[1].startsWith("0.00")) {// 表示停排
                BigDecimal newClose = BigDecimal.valueOf(Double.valueOf(infos[2]));
                BigDecimal exrights = lastExrights.multiply(lastClose).divide(newClose, 4, BigDecimal.ROUND_HALF_UP);

                StockMinute stockMinute = new StockMinute();
                stockMinute.setStockCode(stockCode);
                stockMinute.setDate(DateUtil.stringToDate(infos[30]));
                stockMinute.setHour(Integer.valueOf(times[0]));
                stockMinute.setMinute(Integer.valueOf(times[1]));
                stockMinute.setPrice(BigDecimal.valueOf(Double.valueOf(infos[3])));
                stockMinute.setHigh(BigDecimal.valueOf(Double.valueOf(infos[4])));
                stockMinute.setLow(BigDecimal.valueOf(Double.valueOf(infos[5])));
                stockMinute.setVolume(null);
                stockMinute.setAmount(null);
                stockMinute.setExrights(exrights);
                return stockMinute;
            } else {
                log.info("股票停牌\t" + stockCode + "\t" + result);
            }

        } else {
            log.error("fetchExrights下载数据不成功");
        }

        return null;
    }
}
