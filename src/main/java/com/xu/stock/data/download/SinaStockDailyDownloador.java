package com.xu.stock.data.download;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.xml.xpath.XPathConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.xu.stock.data.model.Stock;
import com.xu.stock.data.model.StockDaily;
import com.xu.util.DateUtil;
import com.xu.util.DocumentUtil;
import com.xu.util.HttpClientHandle;
import com.xu.util.StringUtil;
import com.xu.util.XPathUtil;

/**
 * 新浪日数据下载
 * 
 * @version
 * 
 * <pre>
 * Author	Version		Date		Changes
 * lunan.xu 	1.0  		2016年4月11日 	Created
 * </pre>
 * 
 * @since 1.
 */
public class SinaStockDailyDownloador {

    protected static Logger        log    = LoggerFactory.getLogger(SinaStockDailyDownloador.class);

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
            try {
                String[] indesUrl = url.split("##");
                List<StockDaily> dailys = parseHtml(stock, HttpClientHandle.get(indesUrl[0], "gb2312"));// 解析返回的html
                resolveExrights(stock, dailys, indesUrl[1]);// 解析返回的html
                resolveLastDataExrights(dailys, indesUrl[2]);// 解析返回的html
                stockDailyes.addAll(dailys);
            } catch (Exception e) {
                log.error("", e);
                stock.setHasException(true);
                break;
            }
        }
        return stockDailyes;
    }

    private static void resolveLastDataExrights(List<StockDaily> dailys, String url) {
        if (dailys.size() > 1) {
            StockDaily lastDaily = dailys.get(dailys.size() - 1);
            StockDaily lastLastDaily = dailys.get(dailys.size() - 2);
            if (lastDaily.getExrightsObj() == null && lastLastDaily.getExrightsObj() != null) {
                String result = HttpClientHandle.get(url, "gb2312");
                if (result.length() > 30) {
                    String[] infos = result.split(",");
                    if (!infos[1].startsWith("0.00") && infos[30].equals(DateUtil.date2String(lastDaily.getDate()))) {
                        BigDecimal newClose = BigDecimal.valueOf(Double.valueOf(infos[2]));
                        BigDecimal exrights = lastLastDaily.getExrights().multiply(lastLastDaily.getLastClose()).divide(newClose, 4, BigDecimal.ROUND_HALF_UP);
                        Double rate = exrights.divide(lastLastDaily.getExrights(), 4, BigDecimal.ROUND_HALF_UP).doubleValue();
                        if (rate > 0.98 && rate < 1.02) {
                            lastDaily.setExrights(lastLastDaily.getExrights());
                            lastDaily.setThisExrights(BigDecimal.valueOf(1));
                        } else {
                            BigDecimal thisExrights = exrights.divide(lastLastDaily.getExrights(), 6, BigDecimal.ROUND_HALF_UP);
                            lastDaily.setExrights(exrights);
                            lastDaily.setThisExrights(thisExrights);
                        }
                    }
                }
            }
        }


    }

    /**
     * 构建URL
     * 
     * @param stock
     * @return "http://money.finance.sina.com.cn/corp/go.php/vMS_MarketHistory/stockid/601006.phtml?year=2015&jidu=2"
     * "http://vip.stock.finance.sina.com.cn/corp/go.php/vMS_FuQuanMarketHistory/stockid/000001.phtml?year=2016&jidu=2"
     */
    public static List<String> buildUrls(Stock stock) {
        List<String> urls = new ArrayList<String>();
        List<String> seasons = new ArrayList<String>();
        Date today = new Date();
        int beginYear = 2005;
        int beginJidu = 1;
        if (stock.getLastDate() != null) {
            Date nextWorkDay = DateUtil.getNextWorkDay(stock.getLastDate());
            beginYear = DateUtil.getYear(nextWorkDay);
            beginJidu = DateUtil.getSeason(nextWorkDay);
        }

        for (; beginYear <= DateUtil.getYear(today); beginYear++) {
            if (beginYear < DateUtil.getYear(today)) {
                for (; beginJidu <= 4; beginJidu++) {
                    seasons.add("year=" + beginYear + "&jidu=" + beginJidu);
                    if (beginJidu == 4) {
                        beginJidu = 1;
                        break;
                    }
                }
            } else {
                for (; beginJidu <= DateUtil.getSeason(today); beginJidu++) {
                    if (StockDailyDownloadWorker.hasValidDay(stock.getLastDate())) {
                        seasons.add("year=" + beginYear + "&jidu=" + beginJidu);
                    }
                }
            }
        }

        for (String season : seasons) {
            String fullStockCode = stock.getStockCode().startsWith("6") ? "sh" + stock.getStockCode() : "sz" + stock.getStockCode();
            StringBuilder url = new StringBuilder(StockApiConstants.Sina.API_URL_GET_STOCK_INDEX).append(stock.getStockCode()).append(".phtml?").append(season);
            url.append("##").append(StockApiConstants.Sina.API_URL_GET_FUQUAN_STOCK_INDEX).append(stock.getStockCode()).append(".phtml?").append(season);
            url.append("##").append(StockApiConstants.Sina.API_URL_STOCK_REALTIME_DETAL).append(fullStockCode);
            urls.add(url.toString());
        }

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
            String tempString = html.replaceAll("&nbsp;", "").replaceAll("&", "");
            int begin = tempString.indexOf("<table id=\"FundHoldSharesTable");
            if (begin > 0) {
                tempString = tempString.substring(begin);
                tempString = tempString.substring(0, tempString.indexOf("</table>") + 8);
                Document doc = DocumentUtil.string2Doc(tempString);

                NodeList daylyNodes = (NodeList) XPathUtil.parse(doc, "//table/tr[position()>1]", XPathConstants.NODESET);
                for (int i = daylyNodes.getLength() - 1; i >= 0; i--) {
                    StockDaily stockDaily = buildStockDaily(stock, daylyNodes, i);

                    dailys.add(stockDaily);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return dailys;
    }

    private static StockDaily buildStockDaily(Stock stock, NodeList daylyNodes, int i) {
        NodeList dailyNodes = daylyNodes.item(i).getChildNodes();
        NodeList dateNodes = dailyNodes.item(1).getChildNodes().item(0).getChildNodes();
        Node dateNode = dateNodes.getLength() > 1 ? dateNodes.item(1) : dateNodes.item(0);
        String date = StringUtil.replaceBlank(dateNode.getTextContent());

        BigDecimal open = BigDecimal.valueOf(Double.valueOf(dailyNodes.item(3).getTextContent()));
        BigDecimal high = BigDecimal.valueOf(Double.valueOf(dailyNodes.item(5).getTextContent()));
        BigDecimal close = BigDecimal.valueOf(Double.valueOf(dailyNodes.item(7).getTextContent()));
        BigDecimal low = BigDecimal.valueOf(Double.valueOf(dailyNodes.item(9).getTextContent()));
        String volume = dailyNodes.item(11).getTextContent();
        String amount = dailyNodes.item(13).getTextContent();

        BigDecimal lastClose = stock.getLastClose() == null ? open : stock.getLastClose();

        StockDaily stockDaily = new StockDaily();
        stockDaily.setStockCode(stock.getStockCode());
        stockDaily.setStockName(stock.getStockName());
        stockDaily.setDate(DateUtil.stringToDate(date));
        stockDaily.setLastClose(lastClose);
        stockDaily.setOpen(open);
        stockDaily.setClose(close);
        stockDaily.setHigh(high);
        stockDaily.setLow(low);
        stockDaily.setAmount(BigDecimal.valueOf(Double.valueOf(amount)));
        stockDaily.setVolume(BigDecimal.valueOf(Double.valueOf(volume)));
        stockDaily.setAsset(null);// TODO

        setGapRate(stockDaily);

        stock.setLastClose(stockDaily.getClose());

        return stockDaily;
    }

    private static void resolveExrights(Stock stock, List<StockDaily> dailys, String url) {
        if (!dailys.isEmpty()) {
            try {
                String html = HttpClientHandle.get(url, "gb2312");
                String tempString = html.replaceAll("&nbsp;", "").replaceAll("&", "");
                int begin = tempString.indexOf("<table id=\"FundHoldSharesTable");
                if (begin > 0) {
                    tempString = tempString.substring(begin);
                    tempString = tempString.substring(0, tempString.indexOf("</table>") + 8);
                    Document doc = DocumentUtil.string2Doc(tempString);

                    NodeList daylyNodes = (NodeList) XPathUtil.parse(doc, "//table/tr[position()>1]", XPathConstants.NODESET);
                    int nodeLength = daylyNodes.getLength();
                    int dailyIndex = 0;
                    for (int i = nodeLength - 1; i >= 0; i--) {
                        NodeList dailyNodes = daylyNodes.item(i).getChildNodes();
                        NodeList dateNodes = dailyNodes.item(1).getChildNodes().item(0).getChildNodes();
                        Node dateNode = nodeLength > 1 ? dateNodes.item(1) : dateNodes.item(0);
                        dateNode = dateNode == null ? dateNodes.item(0) : dateNode;
                        String date = StringUtil.replaceBlank(dateNode.getTextContent());
                        date = "".equals(date) ? StringUtil.replaceBlank(daylyNodes.item(i).getChildNodes().item(1).getChildNodes().item(0).getChildNodes().item(1).getTextContent()) : date;
                        BigDecimal exrights = BigDecimal.valueOf(Double.valueOf(dailyNodes.item(15).getTextContent()));
                        BigDecimal thisExrights = BigDecimal.valueOf(1);

                        StockDaily stockDaily = dailys.get(dailyIndex);
                        while (stock.getLastClose() != null && !DateUtil.date2String(stockDaily.getDate()).equals(date)) {
                            stockDaily.setThisExrights(thisExrights);
                            stockDaily.setExrights(stock.getExrights());
                            dailyIndex = dailyIndex + 1;
                            stockDaily = dailys.get(dailyIndex);
                        }
                        if (DateUtil.date2String(stockDaily.getDate()).equals(date)) {
                            if (stock.getLastClose() != null && stock.getExrights().compareTo(exrights) != 0) {
                                thisExrights = exrights.divide(stock.getExrights(), 6, BigDecimal.ROUND_HALF_UP);
                                if (!"2005-01-01".equals(DateUtil.date2String(stock.getLastDate())) || stock.getDailySize() > 0) {
                                    stockDaily.setLastClose(stockDaily.getLastClose().multiply(stock.getExrights()).divide(exrights, 2, BigDecimal.ROUND_HALF_UP));
                                    setGapRate(stockDaily);
                                }
                            }
                            stockDaily.setThisExrights(thisExrights);
                            stockDaily.setExrights(exrights);
                            stock.setExrights(exrights);
                            stock.setAsset(stockDaily.getAsset());
                            stock.setDailySize(stock.getDailySize() + 1);
                            dailyIndex = dailyIndex + 1;
                        } else {
                            throw new RuntimeException();
                        }
                    }
                    if (nodeLength == 0) {
                        throw new RuntimeException();
                    }
                } else {
                    throw new RuntimeException();
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void setGapRate(StockDaily stockDaily) {
        stockDaily.setCloseGap(stockDaily.getClose().subtract(stockDaily.getLastClose()));
        BigDecimal closeGapRate = stockDaily.getCloseGap().multiply(BD_100).divide(stockDaily.getLastClose(), 2, BigDecimal.ROUND_HALF_UP);
        stockDaily.setCloseGapRate(closeGapRate);
        stockDaily.setHighGap(stockDaily.getHigh().subtract(stockDaily.getLastClose()));
        BigDecimal highGapRate = stockDaily.getHighGap().multiply(BD_100).divide(stockDaily.getLastClose(), 2, BigDecimal.ROUND_HALF_UP);
        stockDaily.setHighGapRate(highGapRate);
        stockDaily.setLowGap(stockDaily.getLow().subtract(stockDaily.getLastClose()));
        BigDecimal lowGapRate = stockDaily.getLowGap().multiply(BD_100).divide(stockDaily.getLastClose(), 2, BigDecimal.ROUND_HALF_UP);
        stockDaily.setLowGapRate(lowGapRate);
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
