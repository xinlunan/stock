package com.xu.stock.data.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xu.stock.data.model.Stock;
import com.xu.stock.data.service.IStockAnalyseService;
import com.xu.stock.data.service.IStockService;
import com.xu.stock.data.service.StockAnalyseWorker;
import com.xu.util.CollectionUtil;
import com.xu.util.ThreadUtil;

/**
 * 股票分析控制层
 * 
 * 
 * @version
 * 
 * 			<pre>
 * Author	Version		Date		Changes
 * lunan.xu 	1.0  		2016年3月19日 	Created
 *
 *          </pre>
 * 
 * @since 1.
 */
@SuppressWarnings("restriction")
public abstract class BaseStockAnalyseController {
	protected static Logger log = LoggerFactory.getLogger(BaseStockAnalyseController.class);

	@Resource
	private IStockService stockService;

	public void analyse(int threads) {
		List<Stock> stocks = stockService.getAllStocks();

		List<List<Stock>> stockGroups = CollectionUtil.subListByPages(stocks, threads);

		List<Runnable> workers = new ArrayList<Runnable>();
		for (List<Stock> subStocks : stockGroups) {
			StockAnalyseWorker worker = new StockAnalyseWorker();
			worker.setStocks(subStocks);
			worker.setStockService(stockService);
			worker.setStockAnalyseService(getStockAnalyseService());
			worker.start();
			workers.add(worker);

		}

		ThreadUtil.threadsJoin(workers);

	}

	public abstract IStockAnalyseService getStockAnalyseService();

}
