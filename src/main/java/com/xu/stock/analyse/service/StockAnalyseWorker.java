package com.xu.stock.analyse.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xu.stock.data.dao.IStockDailyDao;
import com.xu.stock.data.model.Stock;
import com.xu.stock.data.model.StockDaily;

public class StockAnalyseWorker extends Thread {
	protected static Logger log = LoggerFactory.getLogger(StockAnalyseWorker.class);

	private List<Stock> stocks;
	private IStockAnalyseService stockAnalyseService;
	private IStockDailyDao stockDailyDao;

	public void run() {
		log.info("StockDailyWorker run size" + stocks.size());

		for (Stock stock : stocks) {

			try {
				log.info("analyse stock code:" + stock.getStockCode());

				// 所有交易记录
				List<StockDaily> dailys = stockDailyDao.getRrightStockDailys(stock.getStockCode());

				if (!dailys.isEmpty()) {
					stockAnalyseService.analyse(dailys);
				}

			} catch (Exception e) {
				log.error("stock fetch daily error stock :" + stock.getStockCode(), e);
			}
		}

	}

	public void setStocks(List<Stock> stocks) {
		this.stocks = stocks;
	}

	public void setStockDailyDao(IStockDailyDao stockDailyDao) {
		this.stockDailyDao = stockDailyDao;
	}

	public void setStockAnalyseService(IStockAnalyseService stockAnalyseService) {
		this.stockAnalyseService = stockAnalyseService;
	}

}
