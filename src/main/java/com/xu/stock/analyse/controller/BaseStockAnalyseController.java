package com.xu.stock.analyse.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xu.stock.analyse.service.IStockAnalyseService;
import com.xu.stock.analyse.service.StockAnalyseWorker;
import com.xu.stock.data.dao.IStockDailyDao;
import com.xu.stock.data.model.Stock;
import com.xu.stock.data.service.IStockService;
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
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource
	private IStockService stockService;
	@Resource
	private IStockDailyDao stockDailyDao;

	public void analyse(int threads) {
		List<Stock> stocks = getAnalyseStocks();

		List<List<Stock>> stockGroups = CollectionUtil.subListByPages(stocks, threads);

		List<Runnable> workers = new ArrayList<Runnable>();
		for (List<Stock> subStocks : stockGroups) {
			StockAnalyseWorker worker = new StockAnalyseWorker();
			worker.setStocks(subStocks);
			worker.setStockDailyDao(stockDailyDao);
			worker.setStockAnalyseService(getStockAnalyseService());
			worker.start();
			workers.add(worker);
		}

		ThreadUtil.threadsJoin(workers);

	}

    public List<Stock> getAnalyseStocks() {
        List<Stock> stocks = stockService.getAllStocks();
        return stocks;
    }

	public abstract IStockAnalyseService getStockAnalyseService();

}
