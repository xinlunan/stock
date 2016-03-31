package com.xu.stock.analyse.dao;

import com.xu.stock.analyse.model.StockAnalyseStrategy;

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
public interface IStockAnalyseStrategyDao {

	/**
	 * 获取分析策略
	 * 
	 * @param stockCode
	 * @return
	 */
	public StockAnalyseStrategy getAnalyseStrategy(String strategyType);

}
