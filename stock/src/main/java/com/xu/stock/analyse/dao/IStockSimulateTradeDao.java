package com.xu.stock.analyse.dao;

import java.util.List;

import com.xu.stock.analyse.model.StockSimulateTrade;
import com.xu.stock.analyse.service.StockAnalyseConstants.StrategyType;

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
public interface IStockSimulateTradeDao {

	/**
	 * 获取所有购买交易信息
	 * 
	 * @param strategy
	 * @param stockCode
	 * @return
	 */
	public List<StockSimulateTrade> getBuyTrades(StrategyType strategy, String stockCode);

	/**
	 * 获取股票交易信息
	 * 
	 * @param stockCode
	 * @return
	 */
	public List<StockSimulateTrade> getStockSimulateTrades(String stockCode);

	/**
	 * 是否存在当前交易
	 * 
	 * @param trade
	 * @return
	 */
	public boolean existTrade(StockSimulateTrade trade);

	/**
	 * 保存股票交易信息
	 * 
	 * @param trades
	 * @return
	 */
	public Integer saveStockSimulateTrades(List<StockSimulateTrade> trades);

}
