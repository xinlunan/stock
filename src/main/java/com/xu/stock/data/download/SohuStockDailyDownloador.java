package com.xu.stock.data.download;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xu.stock.data.model.Stock;
import com.xu.stock.data.model.StockDaily;
import com.xu.util.DateUtil;
import com.xu.util.HttpClientHandle;

import net.sf.json.JSONObject;

/**
 * 股票指数控制层辅助类
 * 
 * @version Revision History
 * 
 * <pre>
 * Author     Version       Date        Changes
 * lunan.xu    1.0           2015-5-29     Created
 * </pre>
 * 
 * @since 1.
 */
public class SohuStockDailyDownloador {

    protected Logger               log    = LoggerFactory.getLogger(this.getClass());

    public static final BigDecimal BD_100 = BigDecimal.valueOf(100);

    /**
     * 下载股票指数数据
     * 
     * @param stock
     * @return
     */
    public static Stock download(Stock stock) {
        // 下载数据
        List<StockDaily> stockDailyes = downloadStockDaily(stock);

        // 检查有效性
        reviewStockDaily(stockDailyes);

        stock.setStockDailys(stockDailyes);

        return stock;
    }

    /**
     * 下载历史
     * 
     * @param stock
     * @return
     */
    private static List<StockDaily> downloadStockDaily(Stock stock) {
        List<StockDaily> stockDailyes = new LinkedList<StockDaily>();

        List<String> urls = buildUrls(stock);// 时间周期大时，要按季度拆分成小的请求

        for (String url : urls) {
            String html = HttpClientHandle.get(url, "gb2312");

            List<StockDaily> dailys = parseHtml(stock, html);// 解析返回的html

            stockDailyes.addAll(dailys);
        }
        return stockDailyes;
    }

    /**
     * 构建URL
     * 
     * @param stock
     * @return "http://q.stock.sohu.com/hisHq?code=cn_600011&start=20050101&end=20500101"
     */
    public static List<String> buildUrls(Stock stock) {
        List<String> urls = new ArrayList<String>();
        StringBuilder url = new StringBuilder(StockApiConstants.Sohu.API_URL_GET_STOCK_INDEX).append(stock.getStockCode());
        Date fromDate = stock.getLastDate();
        Date endDay = StockDownloadHelper.getLastDate();
        if (fromDate == null) {
            fromDate = DateUtil.stringToDate("2005-01-01");
        } else {
            fromDate = DateUtil.addDay(fromDate, 1);
        }

        url.append("&start=").append(DateUtil.date2String(fromDate, "yyyyMMdd")).append("&end=").append(DateUtil.date2String(endDay, "yyyyMMdd"));
        urls.add(url.toString());

        return urls;
    }

    /**
     * 解析返回的html
     * 
     * @param stock
     * @param html
     * @return
     */
    private static List<StockDaily> parseHtml(Stock stock, String html) {
        List<StockDaily> dailys = new ArrayList<StockDaily>();
        try {
            if (html != null && !html.startsWith("{}")) {
                String json = html.substring(1, html.length() - 2);
                JSONObject obj = JSONObject.fromObject(json);
                Map<String, List<List<String>>> map = (HashMap<String, List<List<String>>>) JSONObject.toBean(obj, HashMap.class);
                List<List<String>> indexs = map.get("hq");
                if (indexs != null) {
                    for (int i = indexs.size() - 1; i >= 0; i--) {

                        // 日期 开盘 收盘 涨跌额 涨跌幅 最低 最高 成交量(手) 成交金额(万) 换手率
                        List<String> daily = indexs.get(i);
                        String date = daily.get(0);
                        BigDecimal open = BigDecimal.valueOf(Double.valueOf(daily.get(1)));
                        BigDecimal close = BigDecimal.valueOf(Double.valueOf(daily.get(2)));
                        BigDecimal closeGap = BigDecimal.valueOf(Double.valueOf(daily.get(3)));
                        BigDecimal low = BigDecimal.valueOf(Double.valueOf(daily.get(5)));
                        BigDecimal high = BigDecimal.valueOf(Double.valueOf(daily.get(6)));
                        BigDecimal amount = BigDecimal.valueOf(Double.valueOf(daily.get(7)));
                        BigDecimal volume = BigDecimal.valueOf(Double.valueOf(daily.get(8)));

                        StockDaily stockDaily = new StockDaily();
                        stockDaily.setStockCode(stock.getStockCode());
                        stockDaily.setStockName(stock.getStockName());
                        stockDaily.setDate(DateUtil.stringToDate(date));
                        BigDecimal lastExrightsClose = close.subtract(closeGap);// 除权后昨收=今收-涨跌额
                        stockDaily.setLastClose(lastExrightsClose);
                        BigDecimal lastClose = stock.getLastClose() == null ? stockDaily.getLastClose() : stock.getLastClose();
                        stockDaily.setOpen(open);
                        stockDaily.setClose(close);
                        stockDaily.setCloseGap(stockDaily.getClose().subtract(stockDaily.getLastClose()));
                        BigDecimal closeGapRate = stockDaily.getCloseGap().multiply(BD_100).divide(stockDaily.getLastClose(), 2, BigDecimal.ROUND_HALF_UP);
                        stockDaily.setCloseGapRate(closeGapRate);
                        stockDaily.setHigh(high);
                        stockDaily.setHighGap(stockDaily.getHigh().subtract(stockDaily.getLastClose()));
                        BigDecimal highGapRate = stockDaily.getHighGap().multiply(BD_100).divide(stockDaily.getLastClose(), 2, BigDecimal.ROUND_HALF_UP);
                        stockDaily.setHighGapRate(highGapRate);
                        stockDaily.setLow(low);
                        stockDaily.setLowGap(stockDaily.getLow().subtract(stockDaily.getLastClose()));
                        BigDecimal lowGapRate = stockDaily.getLowGap().multiply(BD_100).divide(stockDaily.getLastClose(), 2, BigDecimal.ROUND_HALF_UP);
                        stockDaily.setLowGapRate(lowGapRate);

                        BigDecimal thisExrights = lastClose.divide(lastExrightsClose, 10, BigDecimal.ROUND_HALF_UP);
                        if (thisExrights.compareTo(BigDecimal.valueOf(1.09)) == 1 || thisExrights.compareTo(BigDecimal.valueOf(0.92)) == -1) {
                            stockDaily.setThisExrights(thisExrights);
                            BigDecimal exrights = lastClose.divide(lastExrightsClose, 10, BigDecimal.ROUND_HALF_UP).multiply(stock.getExrights());
                            stockDaily.setExrights(exrights);
                        } else {
                            stockDaily.setThisExrights(BigDecimal.valueOf(1));
                            stockDaily.setExrights(stock.getExrights());
                        }

                        stockDaily.setAmount(amount);
                        stockDaily.setVolume(volume);
                        stockDaily.setMarketValue(null);// TODO

                        dailys.add(stockDaily);

                        stock.setLastClose(stockDaily.getClose());
                        stock.setMarketValue(stockDaily.getMarketValue());
                        stock.setExrights(stockDaily.getExrights());
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return dailys;
    }

    /**
     * 检查下载的数据是否正常，如有异常会进行处理
     * 
     * @param stockDailyes
     */
    private static void reviewStockDaily(List<StockDaily> stockDailyes) {
        // 检查数据合理性
    }

}
