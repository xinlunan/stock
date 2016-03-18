package com.xu.stock.data.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xu.stock.data.controller.StockIndexControllerHepler;
import com.xu.stock.data.model.Stock;
import com.xu.stock.data.model.StockIndex;
import com.xu.util.DateDiffUtil;
import com.xu.util.DateUtil;

/**
 * 
 * 获取股票数据Worker
 * 
 * @version
 * 
 * 			<pre>
 * Author	Version		Date		Changes
 * lunan.xu 	1.0  		2016年3月12日 	Created
 *
 *          </pre>
 * 
 * @since 1.
 */
public class StockIndexWorker extends Thread {
	static Logger log = LoggerFactory.getLogger(StockIndexWorker.class);

	private List<Stock> stocks;
	private IStockService stockService;
	private IStockIndexService stockIndexService;

	public void run() {
		log.info("StockIndexWorker run size" + stocks.size());

		for (Stock stock : stocks) {// 抓取每支股票指数
			try {

				downloadStockIndex(stock);

			} catch (Exception e) {
				log.error("stock fetch index error stock :" + stock.getStockCode(), e);
			}
		}
	}

	/**
	 * 抓取并保存股票指数
	 * 
	 * @param stock
	 */
	private void downloadStockIndex(Stock stock) {
		log.info("初始化股票数据:" + stock.getStockCode());

		Stock stockData = SinaStockIndexDownloador.download(stock);

		stockService.saveStockData(stockData);

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
				// 股票指数日期跳空。可能是接口问题，也可能是停牌
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

				StockIndex repairIndex = StockIndexControllerHepler
						.getRepairStockIndexByExcel(stock.getExchange() + stock.getStockCode(), nextDate);
				if (repairIndex != null) {// 这一天真没数据。即当天停牌
					repairIndexs.add(repairIndex);
				}

				if (DateDiffUtil.getWorkDay(nextDate, index.getDate()) == 1) { // 追平当前日期
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

	public IStockService getStockService() {
		return stockService;
	}

	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

	public IStockIndexService getStockIndexService() {
		return stockIndexService;
	}

	public void setStockIndexService(IStockIndexService stockIndexService) {
		this.stockIndexService = stockIndexService;
	}

	public List<Stock> getStocks() {
		return stocks;
	}

	public void setStocks(List<Stock> stocks) {
		this.stocks = stocks;
	}

}
