package com.xu.stock.analyse.service;

import java.util.List;

import com.xu.stock.analyse.model.StockBuyTrade;

/**
 * 股票指数Service
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
public interface IStockSimulateTradeService {

	/**
	 * 根据
	 * 
	 * @param stockCode
	 * @return
	 */
	public List<StockBuyTrade> getStockSimulateTrades(String stockCode);

	/**
	 * 根据
	 * 
	 * @param stockCode
	 * @return
	 */
	public void saveStockSimulateTrades(List<StockBuyTrade> stockSimulateTrades);
}
