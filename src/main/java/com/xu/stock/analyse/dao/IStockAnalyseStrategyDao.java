package com.xu.stock.analyse.dao;

import java.util.List;

import com.xu.stock.analyse.model.StockAnalyseStrategy;
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
public interface IStockAnalyseStrategyDao {

	/**
	 * 获取分析策略
	 * 
	 * @param stockCode
	 * @return
	 */
	public StockAnalyseStrategy getAnalyseStrategy(StrategyType strategyType);

	/**
	 * 获取所有分析策略版本
	 * 
	 * @param highestProbeBuy
	 * @return
	 */
	public List<StockAnalyseStrategy> getAnalyseStrategys(StrategyType highestProbeBuy);

}
