package com.xu.stock.data.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xu.stock.data.dao.IStockDao;
import com.xu.stock.data.dao.IStockIndexDao;
import com.xu.stock.data.model.Stock;
import com.xu.stock.data.model.StockIndex;
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
 *          </pre>
 * 
 * @since 1.
 */
@SuppressWarnings("restriction")
@Service("stockService")
public class StockService implements IStockService {
	static Logger log = LoggerFactory.getLogger(StockService.class);
	@Resource
	private IStockDao stockDao;

	@Resource
	private IStockIndexDao stockIndexDao;

	public Stock getStock(String stockCode) {
		return stockDao.getStock(stockCode);
	}

	public List<Stock> getAllStocks() {
		return stockDao.getAllStocks();
	}

	public List<Stock> getUnupatedStocks() {
		return stockDao.getUnupatedStocks();
	}

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

	public void saveStockData(Stock stock) {
		log.info("更新:" + stock.getStockCode());
		filterInvalid(stock);

		stockIndexDao.saveStockIndexs(stock.getStockIndexs());

		stock.setLastDate(StockServiceHelper.getLastDate());
		stockDao.updateStock(stock);
	}

	/**
	 * 过滤无效数据
	 * 
	 * @param stock
	 */
	private void filterInvalid(Stock stock) {
		if (stock.getLastDate() == null) {
			return;
		}
		List<StockIndex> indexs = stock.getStockIndexs();
		List<StockIndex> invalidIndexs = new ArrayList<StockIndex>();
		if (indexs != null) {
			for (int i = 0; i < indexs.size(); i++) {
				StockIndex index = indexs.get(i);
				if (index.getDate().compareTo(stock.getLastDate()) <= 0) {// 日期小于最后日期
					invalidIndexs.add(index);
				}
				if (index.getDate().compareTo(StockServiceHelper.getLastDate()) > 0) {// 日期大于最后有效日期
					invalidIndexs.add(index);
				}
			}
			indexs.removeAll(invalidIndexs);
		}
	}

}