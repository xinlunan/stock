package com.xu.stock.download.downloador;

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
import com.xu.stock.download.downloador.StockApiConstant.Sina;
import com.xu.util.DateUtil;
import com.xu.util.DocumentUtil;
import com.xu.util.HttpClientHandle;
import com.xu.util.NumberUtil;
import com.xu.util.StringUtil;
import com.xu.util.XPathUtil;

/**
 * 股票指数控制层辅助类
 * 
 * @version Revision History
 * 
 *          <pre>
 * Author     Version       Date        Changes
 * lunan.xu    1.0           2015-5-29     Created
 * 
 *          </pre>
 * 
 * @since 1.
 */
public class SinaStockDailyDownloador {
	static Logger log = LoggerFactory.getLogger(SinaStockDailyDownloador.class);

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
	 * @return "http://money.finance.sina.com.cn/corp/go.php/vMS_MarketHistory/stockid/601006.phtml?year=2015&jidu=2"
	 */
	public static List<String> buildUrls(Stock stock) {
		List<String> urls = new ArrayList<String>();
		List<String> seasons = new ArrayList<String>();
		Date today = new Date();
		int beginYear = 2005;
		int beginJidu = 1;
		if (stock.getLastDate() != null) {
			beginYear = DateUtil.getYear(stock.getLastDate());
			beginJidu = DateUtil.getSeason(stock.getLastDate());
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
					seasons.add("year=" + beginYear + "&jidu=" + beginJidu);
				}
			}
		}

		for (String season : seasons) {
			StringBuilder url = new StringBuilder(StockApiConstant.Sina.API_URL_GET_STOCK_INDEX)
					.append(stock.getStockCode()).append(".phtml?").append(season);
			urls.add(url.toString());
		}

		return urls;
	}

	/**
	 * 解析返回的html
	 * 
	 * @param stock
	 * 
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

				NodeList daylyNodes = (NodeList) XPathUtil.parse(doc, "//table/tr[position()>1]",
						XPathConstants.NODESET);
				for (int i = daylyNodes.getLength() - 1; i >= 0; i--) {
					NodeList dailyNodes = daylyNodes.item(i).getChildNodes();
					NodeList dateNodes = dailyNodes.item(1).getChildNodes().item(0).getChildNodes();
					Node dateNode = dateNodes.getLength() > 1 ? dateNodes.item(1) : dateNodes.item(0);
					String date = StringUtil.replaceBlank(dateNode.getTextContent());

					Integer open = NumberUtil.mul(dailyNodes.item(3).getTextContent(), StockDownloadHelper.STR_100);
					Integer high = NumberUtil.mul(dailyNodes.item(5).getTextContent(), StockDownloadHelper.STR_100);
					Integer close = NumberUtil.mul(dailyNodes.item(7).getTextContent(), StockDownloadHelper.STR_100);
					Integer low = NumberUtil.mul(dailyNodes.item(9).getTextContent(), StockDownloadHelper.STR_100);
					String volume = dailyNodes.item(11).getTextContent();
					String amount = dailyNodes.item(13).getTextContent();

					// log.debug(date + "\t" + open + "\t" + high + "\t" + close
					// + "\t" + low + "\t" + volume + "\t" + amount);

					StockDaily stockDaily = new StockDaily();
					stockDaily.setStockId(stock.getStockId());
					stockDaily.setStockCode(stock.getStockCode());
					stockDaily.setStockName(stock.getStockName());
					stockDaily.setDate(DateUtil.stringToDate(date));
					Integer lastCloseInt = stock.getLastClose() == null ? open : stock.getLastClose();
					stockDaily.setLastClose(lastCloseInt);
					stockDaily.setOpen(open);
					stockDaily.setClose(close);
					stockDaily.setCloseGap(stockDaily.getClose() - stockDaily.getLastClose());
					BigDecimal closeGap = new BigDecimal(stockDaily.getCloseGap());
					BigDecimal lastClose = new BigDecimal(stockDaily.getLastClose());
					Float closeGapRate = closeGap.divide(lastClose, 4, BigDecimal.ROUND_HALF_UP).floatValue() * 100;
					stockDaily.setCloseGapRate(closeGapRate);
					stockDaily.setHigh(high);
					stockDaily.setHighGap(stockDaily.getHigh() - stockDaily.getLastClose());
					BigDecimal highGap = new BigDecimal(stockDaily.getHighGap());
					Float highGapRate = highGap.divide(lastClose, 4, BigDecimal.ROUND_HALF_UP).floatValue() * 100;
					stockDaily.setHighGapRate(highGapRate);
					stockDaily.setLow(low);
					stockDaily.setLowGap(stockDaily.getLow() - stockDaily.getLastClose());
					BigDecimal lowGap = new BigDecimal(stockDaily.getLowGap());
					Float lowGapRate = lowGap.divide(lastClose, 4, BigDecimal.ROUND_HALF_UP).floatValue() * 100;
					stockDaily.setLowGapRate(lowGapRate);
					stockDaily.setAmount(Long.valueOf(amount));
					stockDaily.setVolume(Long.valueOf(volume));
					stockDaily.setAsset(null);// TODO
					if (Math.abs(stockDaily.getCloseGapRate()) > 11) {
						stockDaily.setIsExrights(true);
						BigDecimal closeGapRate1 = new BigDecimal(stockDaily.getCloseGapRate());
						int exrights = closeGapRate1.divide(new BigDecimal(10), 0, BigDecimal.ROUND_HALF_UP).intValue() * 10;
						stockDaily.setExrights(exrights);
					}

					dailys.add(stockDaily);

					stock.setLastClose(stockDaily.getClose());
					stock.setAsset(stockDaily.getAsset());
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
