package com.xu.stock.data.service.downloador;

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

import com.xu.stock.StockApiConstant;
import com.xu.stock.data.model.Stock;
import com.xu.stock.data.model.StockIndex;
import com.xu.stock.data.service.impl.StockServiceHelper;
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
public class SinaStockIndexDownloador {
	static Logger log = LoggerFactory.getLogger(SinaStockIndexDownloador.class);

	/**
	 * 下载股票指数数据
	 * 
	 * @param stock
	 * @return
	 */
	public static Stock download(Stock stock) {
		// 下载数据
		List<StockIndex> stockIndexes = downloadStockIndex(stock);

		// 检查有效性
		reviewStockIndex(stockIndexes);

		stock.setStockIndexs(stockIndexes);

		return stock;
	}

	/**
	 * 下载历史
	 * 
	 * @param stock
	 * @return
	 */
	private static List<StockIndex> downloadStockIndex(Stock stock) {
		List<StockIndex> stockIndexes = new LinkedList<StockIndex>();

		List<String> urls = buildUrls(stock);// 时间周期大时，要按季度拆分成小的请求

		for (String url : urls) {
			String html = HttpClientHandle.get(url, "gb2312");

			List<StockIndex> indexes = parseHtml(stock, html);// 解析返回的html

			stockIndexes.addAll(indexes);
		}
		return stockIndexes;
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
	private static List<StockIndex> parseHtml(Stock stock, String html) {
		List<StockIndex> indexes = new ArrayList<StockIndex>();
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
					NodeList indexNodes = daylyNodes.item(i).getChildNodes();
					NodeList dateNodes = indexNodes.item(1).getChildNodes().item(0).getChildNodes();
					Node dateNode = dateNodes.getLength() > 1 ? dateNodes.item(1) : dateNodes.item(0);
					String date = StringUtil.replaceBlank(dateNode.getTextContent());

					Integer open = NumberUtil.mul(indexNodes.item(3).getTextContent(), StockServiceHelper.STR_100);
					Integer high = NumberUtil.mul(indexNodes.item(5).getTextContent(), StockServiceHelper.STR_100);
					Integer close = NumberUtil.mul(indexNodes.item(7).getTextContent(), StockServiceHelper.STR_100);
					Integer low = NumberUtil.mul(indexNodes.item(9).getTextContent(), StockServiceHelper.STR_100);
					String volume = indexNodes.item(11).getTextContent();
					String amount = indexNodes.item(13).getTextContent();

					// log.debug(date + "\t" + open + "\t" + high + "\t" + close
					// + "\t" + low + "\t" + volume + "\t" + amount);

					StockIndex stockIndex = new StockIndex();
					stockIndex.setStockId(stock.getStockId());
					stockIndex.setStockCode(stock.getStockCode());
					stockIndex.setStockName(stock.getStockName());
					stockIndex.setDate(DateUtil.stringToDate(date));
					Integer lastCloseInt = stock.getLastClose() == null ? open : stock.getLastClose();
					stockIndex.setLastClose(lastCloseInt);
					stockIndex.setOpen(open);
					stockIndex.setClose(close);
					stockIndex.setCloseGap(stockIndex.getClose() - stockIndex.getLastClose());
					BigDecimal closeGap = new BigDecimal(stockIndex.getCloseGap());
					BigDecimal lastClose = new BigDecimal(stockIndex.getLastClose());
					Float closeGapRate = closeGap.divide(lastClose, 4, BigDecimal.ROUND_HALF_UP).floatValue() * 100;
					stockIndex.setCloseGapRate(closeGapRate);
					stockIndex.setHigh(high);
					stockIndex.setHighGap(stockIndex.getHigh() - stockIndex.getLastClose());
					BigDecimal highGap = new BigDecimal(stockIndex.getHighGap());
					Float highGapRate = highGap.divide(lastClose, 4, BigDecimal.ROUND_HALF_UP).floatValue() * 100;
					stockIndex.setHighGapRate(highGapRate);
					stockIndex.setLow(low);
					stockIndex.setLowGap(stockIndex.getLow() - stockIndex.getLastClose());
					BigDecimal lowGap = new BigDecimal(stockIndex.getLowGap());
					Float lowGapRate = lowGap.divide(lastClose, 4, BigDecimal.ROUND_HALF_UP).floatValue() * 100;
					stockIndex.setLowGapRate(lowGapRate);
					stockIndex.setAmount(Long.valueOf(amount));
					stockIndex.setVolume(Long.valueOf(volume));
					stockIndex.setAsset(null);// TODO
					if (Math.abs(stockIndex.getCloseGapRate()) > 11) {
						stockIndex.setIsExrights(true);
						BigDecimal closeGapRate1 = new BigDecimal(stockIndex.getCloseGapRate());
						int exrights = closeGapRate1.divide(new BigDecimal(10), 0, BigDecimal.ROUND_HALF_UP).intValue() * 10;
						stockIndex.setExrights(exrights);
					}

					indexes.add(stockIndex);

					stock.setLastClose(stockIndex.getClose());
					stock.setAsset(stockIndex.getAsset());
				}
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return indexes;
	}

	/**
	 * 检查下载的数据是否正常，如有异常会进行处理
	 * 
	 * @param stockIndexes
	 */
	private static void reviewStockIndex(List<StockIndex> stockIndexes) {
		// 检查数据合理性
	}

}
