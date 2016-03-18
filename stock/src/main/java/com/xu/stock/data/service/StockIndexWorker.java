package com.xu.stock.data.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.xu.stock.data.model.Stock;
import com.xu.stock.data.service.downloador.SinaStockIndexDownloador;
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
@Transactional // 测试时，事务将会被回滚
public class StockIndexWorker extends Thread {
	static Logger log = LoggerFactory.getLogger(StockIndexWorker.class);

	private List<Stock> stocks;
	private IStockService stockService;
	private IStockIndexService stockIndexService;

	public void run() {
		log.info("StockIndexWorker run size" + stocks.size());

		for (Stock stock : stocks) {// 抓取每支股票指数
			try {
				if (hasValidDay(stock.getLastDate())) {// 存在有效日期
					downloadStockIndex(stock);
				}

			} catch (Exception e) {
				log.error("stock fetch index error stock :" + stock.getStockCode(), e);
			}
		}
	}

	private static boolean hasValidDay(Date lastDate) {
		if (lastDate == null)
			return true;

		boolean hasValidDay = false;
		Date beginDate = DateUtil.getNextWorkDay(lastDate);
		beginDate = DateUtil.stringToDate(DateUtil.date2String(beginDate, "yyyy-MM-dd"));

		Date today = new Date();

		long timediff = today.getTime() - beginDate.getTime();

		if (timediff > ((15 * 60 + 30) * 60 * 1000)) {
			hasValidDay = true;
		}
		return hasValidDay;
	}

	public static void main(String[] args) {
		System.out.println(hasValidDay(DateUtil.stringToDate("2016-03-17 15:29:00")));
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
