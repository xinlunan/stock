package com.xu.stock.data.service;

import java.util.List;

import com.xu.stock.data.model.StockTrade;

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
public interface IStockTradeService {

	/**
	 * 根据
	 * 
	 * @param stockCode
	 * @return
	 */
	public List<StockTrade> getStockTrades(String stockCode);

	/**
	 * 根据
	 * 
	 * @param stockCode
	 * @return
	 */
	public void saveStockTrades(List<StockTrade> stockTrades);
}
