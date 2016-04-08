package com.xu.stock.data.download;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.xu.stock.data.model.Stock;
import com.xu.util.DateDiffUtil;
import com.xu.util.DateUtil;

import net.sf.json.JSONObject;

/**
 * 股票service辅助类
 * 
 * @version Revision History
 * 
 *          <pre>
 * Author     Version       Date        Changes
 * lunan.xu    1.0           2015-5-28     Created
 * 
 *          </pre>
 * 
 * @since 1.
 */
public class StockDownloadHelper {

	public static final String DATE_PARRTEN_YYYY_MM_DD = "yyyy-MM-dd";
	public static final String DATE_PARRTEN_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final Double NUM_100 = new Double(100);
	public static final Integer INT_100 = 100;
	public static final String STR_100 = "100";

	/**
	 * 将返回的json结果转换成实体对象
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static List<Stock> converStocks(String jsonStr) {
		List<Stock> stocks = new ArrayList<Stock>();
		@SuppressWarnings({ "static-access", "unused" })
		JSONObject obj = new JSONObject().fromObject(jsonStr);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		HashMap<String, String> map = (HashMap) JSONObject.toBean(obj, HashMap.class);
		Iterator<String> stockCodes = map.keySet().iterator();
		while (stockCodes.hasNext()) {
			String stockFullCode = stockCodes.next();
			String exchange = stockFullCode.substring(0, 2);
			String stockCode = stockFullCode.substring(2);
			String stockName = map.get(stockFullCode);

			Stock stock = new Stock();
			stock.setExchange(exchange);
			stock.setStockCode(stockCode);
			stock.setStockName(stockName);
			stock.setLastDate(DateUtil.stringToDate("2005-01-01"));

			stocks.add(stock);
		}

		Collections.sort(stocks);// 排序
		return stocks;
	}

	protected boolean isUpdated(Date lastDate) {
		Date currentDate = new Date();
		int diff = DateDiffUtil.getWorkDay(DateUtil.date2String(lastDate, StockDownloadHelper.DATE_PARRTEN_YYYY_MM_DD),
				DateUtil.date2String(currentDate, StockDownloadHelper.DATE_PARRTEN_YYYY_MM_DD));
		return diff > 0;
	}

	/**
	 * 计算更新日期
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Date getLastDate() {
		Date date = new Date();
		if ((date.getHours() == 15 && date.getMinutes() >= 45) || date.getHours() > 15) {
			return date;
		} else {
			date = DateUtil.addDay(date, -1);
			date.setHours(15);
			date.setMinutes(45);
            date.setSeconds(0);
            return DateUtil.stringToDate(DateUtil.date2String(date, "yyyy-MM-dd HH:mm:ss"));
		}
	}
}