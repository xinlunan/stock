package com.xu.stock.data.download;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.xml.xpath.XPathConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.xu.stock.analyse.service.uitl.StockAnalyseUtil;
import com.xu.stock.data.model.Stock;
import com.xu.stock.data.model.StockDaily;
import com.xu.util.DateDiffUtil;
import com.xu.util.DateUtil;
import com.xu.util.DocumentUtil;
import com.xu.util.HttpClientHandle;
import com.xu.util.StringUtil;
import com.xu.util.XPathUtil;

/**
 * 股票指数控制层辅助类
 * 
 * @version
 * 
 * <pre>
 * Author	Version		Date		Changes
 * lunan.xu 	1.0  		2016年8月16日 	Created
 * </pre>
 * 
 * @since 1.
 */
public class NeteaseStockDailyDownloador {
    protected static Logger        log    = LoggerFactory.getLogger(NeteaseStockDailyDownloador.class);

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
        List<StockDaily> dailys = new LinkedList<StockDaily>();
        try {

            String neteaseUrl = buileNeteaseDailyUrl(stock);
            dailys = parseHtml(stock, HttpClientHandle.get(neteaseUrl, "gb2312"));// 解析返回的html

            for (int i = 0; i < dailys.size(); i++) {
                StockDaily daily = dailys.get(i);
                if (daily.getExrights().compareTo(BigDecimal.ZERO) == -1) {
                    resolveExrights(stock, dailys, buildFuquanUrl(daily));
                    resolveLastDataExrights(dailys, buildDailyFuquanUrl(daily));
                }
            }

        } catch (Exception e) {
            log.error("", e);
            stock.setHasException(true);
        }
        return dailys;
    }


    private static void resolveLastDataExrights(List<StockDaily> dailys, String url) {
        if (dailys.size() > 1) {
            StockDaily lastDaily = dailys.get(dailys.size() - 1);
            StockDaily lastLastDaily = dailys.get(dailys.size() - 2);
            if (lastDaily.getExrights().compareTo(BigDecimal.ZERO) == -1 && lastLastDaily.getExrights().compareTo(BigDecimal.ZERO) == 1) {
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
                            BigDecimal thisExrights = exrights.divide(lastLastDaily.getExrights(), 4, BigDecimal.ROUND_HALF_UP);
                            lastDaily.setExrights(exrights);
                            lastDaily.setThisExrights(thisExrights);
                        }
                    }
                }
            }
        }


    }

    private static String buileNeteaseDailyUrl(Stock stock) {
        String url = StockApiConstants.Netease.API_URL_GET_STOCK_INDEX;
        String prefix = stock.getStockCode().startsWith("6") ? "0" : "1";
        String start = DateUtil.date2String(DateDiffUtil.getNextWorkDate(stock.getLastDate())).replaceAll("-", "");
        url = url.replace("#{prefix}", prefix).replace("#{stockCode}", stock.getStockCode()).replace("#{start}", start);
        return url;
    }

    private static String buildFuquanUrl(StockDaily daily) {
        String year = DateUtil.date2String(daily.getDate()).split("-")[0];
        @SuppressWarnings("deprecation")
        int season = daily.getDate().getMonth() / 3 + 1;
        String url = StockApiConstants.Sina.API_URL_GET_FUQUAN_STOCK_INDEX;
        url = url + daily.getStockCode() + ".phtml?year=" + year + "&jidu=" + season;
        return url;
    }

    private static String buildDailyFuquanUrl(StockDaily daily) {
        String fullStockCode = daily.getStockCode().startsWith("6") ? "sh" + daily.getStockCode() : "sz" + daily.getStockCode();
        return StockApiConstants.Sina.API_URL_STOCK_REALTIME_DETAL + fullStockCode;
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
            String dailyInfosStr[] = html.split("\r\n");
            // 2005-03-31,'000001,深发展Ａ,5.21,5.23,5.06,5.09,5.1,0.11,2.1569,0.2418,3408518,17553914.0,10137733396.3,7342775837.65
            for (int i = dailyInfosStr.length - 1; i > 0; i--) {
                StockDaily stockDaily = buildStockDaily(stock, dailyInfosStr[i]);
                if (stockDaily != null) {
                    dailys.add(stockDaily);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return dailys;
    }

    private static StockDaily buildStockDaily(Stock stock, String dailyInfoStr) {
        String[] dailyInfos = dailyInfoStr.split(",");

        if (Double.valueOf(0).equals(Double.valueOf(dailyInfos[3]))) {
            return null;
        }

        BigDecimal lastClose = BigDecimal.valueOf(Double.valueOf(dailyInfos[7]));
        BigDecimal open = BigDecimal.valueOf(Double.valueOf(dailyInfos[6]));
        BigDecimal high = BigDecimal.valueOf(Double.valueOf(dailyInfos[4]));
        BigDecimal close = BigDecimal.valueOf(Double.valueOf(dailyInfos[3]));
        BigDecimal low = BigDecimal.valueOf(Double.valueOf(dailyInfos[5]));
        BigDecimal volume = BigDecimal.valueOf(Double.valueOf(dailyInfos[11]));
        BigDecimal turnoverRate = BigDecimal.valueOf(Double.valueOf(dailyInfos[10]));
        BigDecimal amount = BigDecimal.valueOf(Double.valueOf(dailyInfos[12]));
        Long marketValue = Double.valueOf(dailyInfos[13]).longValue();
        Long circulationMarketValue = Double.valueOf(dailyInfos[14]).longValue();


        StockDaily stockDaily = new StockDaily();
        stockDaily.setStockCode(stock.getStockCode());
        stockDaily.setStockName(dailyInfos[2]);
        stockDaily.setDate(DateUtil.stringToDate(dailyInfos[0]));
        stockDaily.setLastClose(lastClose);
        stockDaily.setOpen(open);
        stockDaily.setClose(close);
        stockDaily.setHigh(high);
        stockDaily.setLow(low);
        stockDaily.setVolume(volume);
        stockDaily.setTurnoverRate(turnoverRate);
        stockDaily.setAmount(amount);
        stockDaily.setMarketValue(marketValue);
        stockDaily.setCirculationMarketValue(circulationMarketValue);

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

                    for (int i = nodeLength - 1; i >= 0; i--) {
                        NodeList dailyNodes = daylyNodes.item(i).getChildNodes();
                        NodeList dateNodes = dailyNodes.item(1).getChildNodes().item(0).getChildNodes();
                        Node dateNode = nodeLength > 1 ? dateNodes.item(1) : dateNodes.item(0);
                        dateNode = dateNode == null ? dateNodes.item(0) : dateNode;
                        String date = StringUtil.replaceBlank(dateNode.getTextContent());

                        date = "".equals(date) ? StringUtil.replaceBlank(daylyNodes.item(i).getChildNodes().item(1).getChildNodes().item(0).getChildNodes().item(1).getTextContent()) : date;
                        BigDecimal exrights = BigDecimal.valueOf(Double.valueOf(dailyNodes.item(15).getTextContent()));
                        BigDecimal thisExrights = BigDecimal.valueOf(1);

                        Integer dailyIndex = StockAnalyseUtil.dailyIndex(dailys, DateUtil.stringToDate(date));
                        if (dailyIndex != null) {
                            StockDaily stockDaily = dailys.get(dailyIndex);
                            while (stock.getLastClose() != null && !DateUtil.date2String(stockDaily.getDate()).equals(date)) {
                                stockDaily.setThisExrights(thisExrights);
                                stockDaily.setExrights(stock.getExrights());
                                dailyIndex = dailyIndex + 1;
                                stockDaily = dailys.get(dailyIndex);
                            }
                            if (DateUtil.date2String(stockDaily.getDate()).equals(date)) {
                                if (stock.getLastClose() != null && stock.getExrights().compareTo(exrights) != 0) {
                                    thisExrights = exrights.divide(stock.getExrights(), 4, BigDecimal.ROUND_HALF_UP);
                                    if (!"2005-01-01".equals(DateUtil.date2String(stock.getLastDate())) || stock.getDailySize() > 0) {
                                        stockDaily.setLastClose(stockDaily.getLastClose().multiply(stock.getExrights()).divide(exrights, 2, BigDecimal.ROUND_HALF_UP));
                                        setGapRate(stockDaily);
                                    }
                                }
                                stockDaily.setThisExrights(thisExrights);
                                stockDaily.setExrights(exrights);
                                stock.setExrights(exrights);
                                stock.setMarketValue(stockDaily.getMarketValue());
                                stock.setDailySize(stock.getDailySize() + 1);
                                dailyIndex = dailyIndex + 1;
                            } else {
                                throw new RuntimeException();
                            }
                        }
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
