package com.xu.stock.download.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xu.stock.data.model.Stock;
import com.xu.stock.data.service.IStockService;
import com.xu.stock.download.StockDailyDownloadWorker;
import com.xu.util.CollectionUtil;
import com.xu.util.ThreadUtil;

/**
 * 股票指数控制层
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
@SuppressWarnings("restriction")
@Service("stockDailyDownloadController")
public class StockDailyDownloadController {
	protected static Logger log = LoggerFactory.getLogger(StockDailyDownloadController.class);

	@Resource
	private IStockService stockService;

	/**
	 * 下载股票交易数据
	 */
	public void downloadStockDaily(int threads) {
		List<Stock> stocks = stockService.getAllStocks();

		List<List<Stock>> stockGroups = CollectionUtil.subListByPages(stocks, threads);

		List<Runnable> workers = new ArrayList<Runnable>();
		for (List<Stock> subStocks : stockGroups) {
			StockDailyDownloadWorker worker = new StockDailyDownloadWorker();
			worker.setStocks(subStocks);
			worker.setStockService(stockService);
			worker.start();
			workers.add(worker);

		}

		ThreadUtil.threadsJoin(workers);
	}

}
