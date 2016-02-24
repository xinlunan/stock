package com.xu.stock.data.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.ezmorph.bean.MorphDynaBean;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.xu.stock.data.model.Stock;
import com.xu.stock.data.model.StockIndex;
import com.xu.stock.data.service.IStockIndexService;
import com.xu.stock.data.service.IStockService;
import com.xu.util.DateDiffUtil;
import com.xu.util.DateUtil;
import com.xu.util.HttpClientHandle;

/**
 * 股票指数控制层
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
@Service("stockIndexController")
public class StockIndexController {
	static Logger log = Logger.getLogger(StockIndexController.class);

	private static final int NUM_300 = 300;

	@Resource
	IStockService stockService;

	@Resource
	IStockIndexService stockIndexService;

	/**
	 * 初始化股票指数
	 */
	public void initStockIndex() {
		boolean hasException = false;

		hasException = doInitStockIndex();

		//如果初始化过程产生了异常。则重新抓取异常
		if (hasException) {
			initStockIndex();//递归初始化方法
		}

	}

	/**
	 * 更新所有股票指数
	 * 
	 * @return
	 */
	public void updateStockIndex() {
		log.info("更新股票数据");
		try {
			// 取出所有股票
			List<Stock> stocks = stockService.getUnupatedStocks();

			int pages = stocks.size() % NUM_300 == 0 ? stocks.size() / NUM_300 : (stocks.size() / NUM_300 + 1);
			for (int i = 0; i < pages; i++) {
				int diff = DateDiffUtil.getWorkDay(DateUtil.addDay(stocks.get(0).getLastDate(), 1), new Date());
				if (diff <= 0) {
					return;
				}

				int fromIndex = i * NUM_300;
				int toIndex = i == (pages - 1) ? stocks.size() : (i + 1) * NUM_300;
				List<Stock> subStocks = stocks.subList(fromIndex, toIndex);
				doUpdateStockIndex(subStocks);
			}

			repairStockIndex();
		} catch (Exception e) {
			log.error(e);
			updateStockIndex();
		}

	}

	/**
	 * 更新所有股票指数
	 * 
	 * @param stocks
	 */
	private void doUpdateStockIndex(List<Stock> stocks) {
		//组装初始化url
		String url = StockIndexControllerHepler.buildUpdateUrl(stocks);

		// 获取股票指数
		String jsonStr = HttpClientHandle.get(url);

		//解析并保存结果
		resolveAndSave(jsonStr);
	}

	/**
	 * 初始化股票指数
	 * 
	 * @return
	 */
	private boolean doInitStockIndex() {
		boolean hasException = false;
		// 取出所有股票
		List<Stock> stocks = stockService.getAllStocks();

		for (Stock stock : stocks) {// 抓取每支股票指数
			Date lastDate = stock.getLastDate();
			try {
				if (lastDate == null) {// 日期为空需求初始化
					doInitStockIndex(stock);
				}

			} catch (Exception e) {
				hasException = true;
				log.error("stock fetch index error stock :" + stock.getStockCode(), e);
			}
		}
		return hasException;
	}

	/**
	 * 抓取并保存股票指数
	 * 
	 * @param stock
	 * 
	 * @param fullCode
	 * @param startDate
	 * @param endDate
	 */
	private void doInitStockIndex(Stock stock) {
		log.info("初始化股票数据:" + stock.getStockCode());

		//组装初始化url
		String url = StockIndexControllerHepler.buildInitUrl(stock);

		// 获取股票指数
		String jsonStr = HttpClientHandle.get(url);

		//解析并保存结果
		resolveAndSave(jsonStr);
	}

	/**
	 * 解决并保存 1、解析股票指数 2、更新股票信息、保存股票指数
	 * 
	 * @param jsonStr
	 */
	@SuppressWarnings("unchecked")
	private void resolveAndSave(String jsonStr) {
		JSONObject obj = JSONObject.fromObject(jsonStr);
		Map<String, MorphDynaBean> map = (HashMap<String, MorphDynaBean>) JSONObject.toBean(obj, HashMap.class);
		Iterator<String> stockCodes = map.keySet().iterator();
		while (stockCodes.hasNext()) {// 每支股票日指数
			String stockCode = stockCodes.next();
			MorphDynaBean dateIndexs = (MorphDynaBean) map.get(stockCode);// 每日所有指数

			// 解析每天股票指数
			Stock stock = StockIndexControllerHepler.resolve(stockService, dateIndexs);

			//更新股票信息、保存股票指数 
			stockService.updateStock(stock);
		}

	}

	/**
	 * 修复股票指数
	 */
	public void repairStockIndex() {
		boolean hasException = false;
		// 取出所有股票
		List<Stock> stocks = stockService.getAllStocks();

		for (Stock stock : stocks) {// 取每支股
			List<StockIndex> indexs = stockIndexService.getStockIndex(stock.getStockCode());
			Collections.sort(indexs);
			for (int i = 0; i < indexs.size(); i++) {// 取每支股指数
				if (i == 0) {
					continue;
				}

				StockIndex index = indexs.get(i);
				StockIndex lastIndex = indexs.get(i - 1);
				int diff = DateDiffUtil.getWorkDay(lastIndex.getDate(), index.getDate());
				//股票指数日期跳空。可能是接口问题，也可能是停牌
				if (diff > 1 && index.getUpdated() == null) {
					if (doRepair(stock, index, lastIndex)) {
						hasException = true;
					}
				}
			}
		}
		if (hasException) {
			repairStockIndex();
		}
	}

	/**
	 * 更新服务指数
	 * 
	 * @param stock
	 * @param index
	 * @param lastIndex
	 */
	private boolean doRepair(Stock stock, StockIndex index, StockIndex lastIndex) {
		boolean hasException = false;
		try {
			List<StockIndex> repairIndexs = new ArrayList<StockIndex>();
			boolean flag = true;
			for (int j = 1; flag; j++) {
				Date nextDate = DateUtil.addDay(lastIndex.getDate(), j);
				log.info("stock index is exception, stock :" + stock.getStockCode() + " "
						+ DateUtil.getDate(nextDate, "yyyy-MM-dd"));

				StockIndex repairIndex = StockIndexControllerHepler.getRepairStockIndexByExcel(stock.getExchange()
						+ stock.getStockCode(), nextDate);
				if (repairIndex != null) {//这一天真没数据。即当天停牌
					repairIndexs.add(repairIndex);
				}

				if (DateDiffUtil.getWorkDay(nextDate, index.getDate()) == 1) { //追平当前日期
					flag = false;
				}
			}
			stockIndexService.repairStockIndex(index, repairIndexs);
		} catch (Exception e) {
			hasException = true;
			log.error("stock repair index error, stock :" + stock.getStockCode(), e);
		}
		return hasException;
	}

}
