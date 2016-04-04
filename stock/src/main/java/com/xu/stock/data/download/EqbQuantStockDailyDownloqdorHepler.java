package com.xu.stock.data.download;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.xu.stock.data.model.Stock;
import com.xu.stock.data.model.StockDaily;
import com.xu.stock.data.service.IStockService;
import com.xu.util.DateUtil;
import com.xu.util.DocumentUtil;
import com.xu.util.HttpClientHandle;

import net.sf.ezmorph.bean.MorphDynaBean;

/**
 * 股票指数控制层辅助类
 * 
 * @version Revision History
 * 
 *          <pre>
 * Author     Version       Date        Changes
 * lunan.xu    1.0           2015-5-29     Created
 * 
 * </pre>
 * @since 1.
 */
public class EqbQuantStockDailyDownloqdorHepler {
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	public static final BigDecimal BD_100 = BigDecimal.valueOf(100);

	/**
	 * 构建初始化Url
	 * 
	 * @param stock
	 * @return http://ctxalgo.com/api/ohlc/sz000001,sh600001,sz000004?start-date=2015-05-20&end-date=2015-05-24
	 */
	public static String buildInitUrl(Stock stock) {
		String fullCode = stock.getExchange() + stock.getStockCode();
		String startDate = "2005-01-01";
		String endDate = DateUtil.getSrvDate();

		StringBuffer url = new StringBuffer();
		url.append(StockApiConstants.EqbQuant.API_URL_GET_STOCK_INDEX).append(fullCode);
		url.append("?start-date=").append(startDate).append("&end-date=").append(endDate);
		// url.append("&exright=true");// 前复权

		return url.toString();
	}

	/**
	 * 获取修复股票指数的数据
	 * 
	 * http://market.finance.sina.com.cn/pricehis.php?symbol=sh600900&startdate=2011-08-17&enddate=2011-08-17
	 * 
	 * @param fullCode
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static StockDaily getRepairStockDailyByPrice(String fullCode, Date date) throws Exception {
		String url = buildRepairUrl(fullCode, date);

		String resutlStr = HttpClientHandle.get(url, "gb2312");

		if (resutlStr.indexOf("id=\"divListError\"") > 0) {
			return null;
		}

		resutlStr = resutlStr.substring(resutlStr.indexOf("<tbody>"), resutlStr.lastIndexOf("</tbody>") + 8);

		StockDaily daily = parseString2StockDaily(resutlStr);

		return daily;
	}

	/**
	 * 解析股票指数
	 * 
	 * @param resutlStr
	 * @return
	 * @throws Exception
	 */
	private static StockDaily parseString2StockDaily(String resutlStr) throws Exception {
		Long volume = 0l;
		Float high = 0f;
		Float low = 0f;
		Document document = DocumentUtil.string2Doc(resutlStr);
		XPath xpath = XPathFactory.newInstance().newXPath();
		XPathExpression expr = xpath.compile("/tbody/tr");
		Object result = expr.evaluate(document, XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
		for (int i = 0; i < nodes.getLength(); i++) {
			String value = nodes.item(i).getChildNodes().item(3).getTextContent();
			volume = volume + Long.valueOf(value);
		}
		high = Float.valueOf(nodes.item(0).getChildNodes().item(1).getTextContent());
		low = Float.valueOf(nodes.item(nodes.getLength() - 1).getChildNodes().item(1).getTextContent());
		volume = volume / 100;
		System.out.println(volume);
		System.out.println(high);
		System.out.println(low);

		StockDaily daily = new StockDaily();
		return daily;
	}

	/**
	 * 修复股票数据库地址
	 * 
	 * http://market.finance.sina.com.cn/downxls.php?date=2011-07-08&symbol=sh600900
	 * 
	 * @param fullCode
	 * @param nextDate
	 * @return
	 */
	private static String buildRepairUrl(String fullCode, Date nextDate) {
		String date = DateUtil.getDate(nextDate, "yyyy-MM-dd");

		StringBuffer url = new StringBuffer();
		url.append(StockApiConstants.Sina.API_URL_REPAIR_STOCK_INDEX).append("?symbol=").append(fullCode);
		url.append("&date=").append(date);
		return url.toString();
	}

	/**
	 * 构建更新Url
	 * 
	 * @param subStocks
	 * @return
	 */
	public static String buildUpdateUrl(List<Stock> stocks) {
		Date startDate = null;
		StringBuffer fullCodes = new StringBuffer();
		String start = null;
		String end = DateUtil.getSrvDate();
		for (int i = 0; i < stocks.size(); i++) {
			Stock stock = stocks.get(i);
			fullCodes.append(stock.getExchange()).append(stock.getStockCode());
			if (i != stocks.size() - 1) {
				fullCodes.append(",");
			}
			startDate = stock.getLastDate();
		}
		start = DateUtil.addDays(DateUtil.date2String(startDate, "yyyy-MM-dd"), 1);

		StringBuffer url = new StringBuffer();
		url.append(StockApiConstants.EqbQuant.API_URL_GET_STOCK_INDEX).append(fullCodes);
		url.append("?start-date=").append(start).append("&end-date=").append(end);
		// url.append("&exright=true");// 前复权

		return url.toString();
	}

	@SuppressWarnings("unchecked")
	public static Stock resolve(IStockService stockService, MorphDynaBean dateIndexs) {
		List<String> dates = (List<String>) dateIndexs.get("dates");// 日期数组
		List<Double> opens = (List<Double>) dateIndexs.get("opens");// 每日开盘价指数
		List<Double> closes = (List<Double>) dateIndexs.get("closes");// 每日收盘价指数
		List<Double> highs = (List<Double>) dateIndexs.get("highs");// 每日最高价指数
		List<Double> lows = (List<Double>) dateIndexs.get("lows");// 每日最低价指数
		List<Double> amounts = (List<Double>) dateIndexs.get("amounts");// 每日成交额指数
		List<Double> volumes = (List<Double>) dateIndexs.get("volumes");// 每日成交量指数
		String stockCode = ((String) dateIndexs.get("stock_id")).substring(2);// 股票代码
		String stockName = (String) dateIndexs.get("stock_name");// 每日成交量指数

		Stock stock = stockService.getStock(stockCode);

		List<StockDaily> stockDailys = new ArrayList<StockDaily>();
		for (int i = 0; i < dates.size(); i++) {
			if (i == dates.size() - 1 && !dates.get(i).endsWith("15:00:00")) {
				continue;// 最后一天还未到收盘时间时，不作记录
			}

			StockDaily stockDaily = new StockDaily();
			stockDaily.setStockId(stock.getStockId());
			stockDaily.setStockCode(stockCode);
			stockDaily.setStockName(stockName);
			stockDaily.setDate(DateUtil.stringToDate(dates.get(i).substring(0, 10)));
			BigDecimal lastCloseInt = stock.getLastClose() == null ? BigDecimal.valueOf(opens.get(i)) : stock.getLastClose();
			stockDaily.setLastClose(lastCloseInt);
			stockDaily.setOpen(BigDecimal.valueOf(opens.get(i)));
			stockDaily.setClose(BigDecimal.valueOf(closes.get(i)));
			stockDaily.setCloseGap(stockDaily.getClose().subtract(stockDaily.getLastClose()));
			BigDecimal closeGapRate = stockDaily.getCloseGap().multiply(BD_100).divide(stockDaily.getLastClose(), 2, BigDecimal.ROUND_HALF_UP);
			stockDaily.setCloseGapRate(closeGapRate);
			stockDaily.setHigh(BigDecimal.valueOf(highs.get(i)));
			stockDaily.setHighGap(stockDaily.getHigh().subtract(stockDaily.getLastClose()));
			BigDecimal highGapRate = stockDaily.getHighGap().multiply(BD_100).divide(stockDaily.getLastClose(), 2, BigDecimal.ROUND_HALF_UP);
			stockDaily.setHighGapRate(highGapRate);
			stockDaily.setLow(BigDecimal.valueOf(lows.get(i)));
			stockDaily.setLowGap(stockDaily.getLow().subtract(stockDaily.getLastClose()));
			BigDecimal lowGapRate = stockDaily.getLowGap().multiply(BD_100).divide(stockDaily.getLastClose(), 2, BigDecimal.ROUND_HALF_UP);
			stockDaily.setLowGapRate(lowGapRate);
			stockDaily.setAmount(amounts.get(i).longValue());
			stockDaily.setVolume(volumes.get(i).longValue());
			Long asset = null;
			stockDaily.setAsset(asset);

			stockDailys.add(stockDaily);

			stock.setLastClose(stockDaily.getClose());
			stock.setAsset(stockDaily.getAsset());
		}
		stock.setStockDailys(stockDailys);

		return stock;
	}

	/**
	 * 通过excel修正股票指数
	 * 
	 * @param string
	 * @param nextDate
	 * @return
	 * @throws IOException
	 */
	public static StockDaily getRepairStockDailyByExcel(String fullCode, Date date) throws Exception {
		String url = buildRepairUrl(fullCode, date);

		String path = "D:\\temp\\" + fullCode + "_" + DateUtil.getDate(date, "yyyyMMdd") + ".xls";
		boolean download = HttpClientHandle.download(url, path);
		if (!download) {
			return null;
		}
		StockDaily daily = parseStockDailyExcel(path, fullCode, date);

		return daily;
	}

	public static StockDaily parseStockDailyExcel(String file, String fullCode, Date date) throws IOException {
		Long volume = 0l;
		Long amount = 0l;
		BigDecimal high = BigDecimal.valueOf(Integer.MIN_VALUE);
		BigDecimal low = BigDecimal.valueOf(Integer.MAX_VALUE);
		BigDecimal close = BigDecimal.valueOf(0);
		BigDecimal open = BigDecimal.valueOf(0);

		InputStream is = new FileInputStream(new File(file));
		try {
			List<String[]> list = new ArrayList<String[]>();
			String tempString = null;

			BufferedReader reader = new BufferedReader(new FileReader(file));
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				String[] prices = tempString.split("\t");
				list.add(prices);
			}
			reader.close();

			// 循环行Row
			for (int rowNum = 1; rowNum < list.size(); rowNum++) {
				String[] prices = list.get(rowNum);
				if (prices == null) {
					continue;
				}
				BigDecimal intPrice = BigDecimal.valueOf(Double.valueOf(prices[1]));
				if (rowNum == 1) {
					close = intPrice;
				}
				if (intPrice.compareTo(high) == 1) {
					high = intPrice;
				}
				if (intPrice.compareTo(low) == -1) {
					low = intPrice;
				}
				volume = volume + Long.valueOf(prices[3]);
				amount = amount + Long.valueOf(prices[4]);

				if (rowNum == list.size() - 1) {
					open = intPrice;
				}
			}

			if (volume != 0 && amount != 0) {
				StockDaily stockDaily = new StockDaily();
				stockDaily.setOpen(open);
				stockDaily.setClose(close);
				stockDaily.setHigh(high);
				stockDaily.setLow(low);
				stockDaily.setAmount(amount);
				stockDaily.setVolume(volume);
				stockDaily.setDate(date);
				stockDaily.setStockCode(fullCode.substring(2));

				return stockDaily;
			}
			return null;
		} finally {
			is.close();
		}

	}

	/**
	 * 根据excel解决StockDaily
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public static StockDaily parseStockDailyExcel_bak(String file) throws IOException {
		Long volume = 0l;
		Long amount = 0l;
		BigDecimal high = BigDecimal.valueOf(Integer.MIN_VALUE);
		BigDecimal low = BigDecimal.valueOf(Integer.MAX_VALUE);
		BigDecimal close = BigDecimal.valueOf(0);
		BigDecimal open = BigDecimal.valueOf(0);

		InputStream is = new FileInputStream(new File(file));
		try {
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);// 第一张工作表Sheet
			// 循环行Row
			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				Double price = Double.valueOf(getValue(hssfRow.getCell((short) 1)));
				BigDecimal intPrice = BigDecimal.valueOf(price);
				if (rowNum == 1) {
					close = intPrice;
				} else {
					if (intPrice.compareTo(high) == 1) {
						high = intPrice;
					}
					if (intPrice.compareTo(low) == -1) {
						low = intPrice;
					}
				}
				volume = volume + Long.valueOf(getValue(hssfRow.getCell((short) 3)));
				amount = amount + Long.valueOf(getValue(hssfRow.getCell((short) 4)));

				if (rowNum == hssfSheet.getLastRowNum() - 1) {
					open = intPrice;
				}
			}

		} finally {
			is.close();
		}

		StockDaily stockDaily = new StockDaily();
		stockDaily.setOpen(open);
		stockDaily.setClose(close);
		stockDaily.setHigh(high);
		stockDaily.setLow(low);
		stockDaily.setAmount(amount);
		stockDaily.setVolume(volume);

		return stockDaily;
	}

	/**
	 * 得到Excel表中的值
	 * 
	 * @param hssfCell Excel中的每一个格子
	 * @return Excel中每一个格子中的值
	 */
	@SuppressWarnings({ "static-access" })
	private static String getValue(HSSFCell hssfCell) {
		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
			// 返回布尔类型的值
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			// 返回数值类型的值
			return String.valueOf(hssfCell.getNumericCellValue());
		} else {
			// 返回字符串类型的值
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}
}
