package com.xu.stock.data.dao;

import java.util.List;

import com.xu.stock.data.model.StockTrade;

/**
 * 股票交易信息
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
public interface IStockTradeDao {

	/**
	 * 获取股票交易信息
	 * 
	 * @param index
	 * @return
	 */
	public List<StockTrade> getStockTrades(String stockCode);

	/**
	 * 保存股票交易信息
	 * 
	 * @param stockIndexs
	 * @return
	 */
	public Integer saveStockTrades(List<StockTrade> trades);

}
