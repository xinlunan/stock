package com.xu.stock.data.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xu.stock.data.model.Stock;
import com.xu.stock.data.model.StockDaily;
import com.xu.util.DateUtil;

public class StockAnalyseWorker extends Thread {
	protected static Logger log = LoggerFactory.getLogger(StockAnalyseWorker.class);

	List<Stock> stocks;
	IStockService stockService;
	IStockAnalyseService stockAnalyseService;

	public void run() {
		log.info("StockDailyWorker run size" + stocks.size());

		for (Stock stock : stocks) {

			try {
				List<StockDaily> points = stockAnalyseService.analyse(stock);

				for (StockDaily stockDaily : points) {
					log.info(stock.getStockCode() + " buy point：" + DateUtil.date2String(stockDaily.getDate()));
				}

			} catch (Exception e) {
				log.error("stock fetch daily error stock :" + stock.getStockCode(), e);
			}
		}

	}

	public void setStocks(List<Stock> stocks) {
		this.stocks = stocks;
	}

	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

	public void setStockAnalyseService(IStockAnalyseService stockAnalyseService) {
		this.stockAnalyseService = stockAnalyseService;
	}

}
