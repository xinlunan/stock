package com.xu.stock.data.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xu.stock.data.dao.IStockTradeDao;
import com.xu.stock.data.model.StockTrade;
import com.xu.stock.data.service.IStockTradeService;

/**
 * 股票指数service实现
 * 
 * 
 * @version
 * 
 * 			<pre>
 * Author	Version		Date		Changes
 * lunan.xu 	1.0  		2016年3月30日 	Created
 *
 *          </pre>
 * 
 * @since 1.
 */
@SuppressWarnings("restriction")
@Service("stockTradeService")
public class StockTradeService implements IStockTradeService {
	static Logger log = LoggerFactory.getLogger(StockTradeService.class);

	@Resource
	private IStockTradeDao stockTradeDao;

	public List<StockTrade> getStockTrades(String stockCode) {
		return stockTradeDao.getStockTrades(stockCode);
	}

	public void saveStockTrades(List<StockTrade> stockTrades) {
		stockTradeDao.saveStockTrades(stockTrades);
	}

}