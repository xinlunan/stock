package com.xu.stock.data.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.xu.stock.data.dao.IStockDao;
import com.xu.stock.data.dao.IStockIndexDao;
import com.xu.stock.data.model.Stock;
import com.xu.stock.data.service.IStockService;

/**
 * 股票service实现
 * 
 * @version Revision History
 * 
 *          <pre>
 * Author     Version       Date        Changes
 * lunan.xu    1.0           2015-5-23     Created
 * 
 * </pre>
 * @since 1.
 */
@Service("stockService")
public class StockService implements IStockService {
	static Logger log = Logger.getLogger(StockService.class);
	@Resource
	private IStockDao stockDao;

	@Resource
	private IStockIndexDao stockIndexDao;

	@Override
	public Stock getStock(String stockCode) {
		return stockDao.getStock(stockCode);
	}

	@Override
	public List<Stock> getAllStocks() {
		return stockDao.getAllStocks();
	}

	@Override
	public List<Stock> getUnupatedStocks() {
		return stockDao.getUnupatedStocks();
	}

	@Override
	public Integer insertStocks(List<Stock> stocks) {
		Integer result = 0;
		for (Stock stock : stocks) {
			Stock exsitStock = stockDao.getStock(stock.getStockCode());
			if (exsitStock == null) {
				result = result + stockDao.insertStock(stock);
			}
		}

		return result;
	}

	@Override
	public void updateStock(Stock stock) {
		log.info("更新股票");
		stockIndexDao.saveStockIndexs(stock.getStockIndexs());

		stock.setLastDate(StockServiceHelper.getLastDate());
		stockDao.updateStock(stock);
	}

}