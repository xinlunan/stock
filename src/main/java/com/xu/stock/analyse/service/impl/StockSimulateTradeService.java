package com.xu.stock.analyse.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xu.stock.analyse.dao.IStockSimulateTradeDao;
import com.xu.stock.analyse.model.StockSimulateTrade;
import com.xu.stock.analyse.service.IStockSimulateTradeService;

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
@Service("stockSimulateTradeService")
public class StockSimulateTradeService implements IStockSimulateTradeService {
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource
	private IStockSimulateTradeDao stockSimulateTradeDao;

	public List<StockSimulateTrade> getStockSimulateTrades(String stockCode) {
		return stockSimulateTradeDao.getStockSimulateTrades(stockCode);
	}

	public void saveStockSimulateTrades(List<StockSimulateTrade> stockSimulateTrades) {
		stockSimulateTradeDao.saveStockSimulateTrades(stockSimulateTrades);
	}

}