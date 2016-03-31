package com.xu.stock.analyse.dao;

import java.util.List;

import com.xu.stock.analyse.model.StockTrade;

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
	 * @param stockCode
	 * @return
	 */
	public List<StockTrade> getStockTrades(String stockCode);

	/**
	 * 是否存在当前交易
	 * 
	 * @param trade
	 * @return
	 */
	public boolean existTrade(StockTrade trade);

	/**
	 * 保存股票交易信息
	 * 
	 * @param trades
	 * @return
	 */
	public Integer saveStockTrades(List<StockTrade> trades);

}
