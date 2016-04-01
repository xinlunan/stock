package com.xu.stock.analyse.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winit.framework.dao.impl.BaseDao;
import com.xu.stock.analyse.dao.IStockAnalyseStrategyDao;
import com.xu.stock.analyse.model.StockAnalyseStrategy;
import com.xu.stock.analyse.service.StockAnalyseConstants.StrategyType;

/**
 * 股票分时指数Dao实现
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
@Repository("stockAnalyseStrategyDao")
public class StockAnalyseStrategyDao extends BaseDao<StockAnalyseStrategy> implements IStockAnalyseStrategyDao {

	public final String SQL_GET_ANALYSE_STRATEGY = getNameSpace() + "getAnalyseStrategy";
	public final String SQL_GET_ANALYSE_STRATEGY_VERSION = getNameSpace() + "getAnalyseStrategyVersion";

	public StockAnalyseStrategy getAnalyseStrategy(StrategyType strategyType) {
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("strategyType", strategyType.toString());
		return getSqlSession().selectOne(SQL_GET_ANALYSE_STRATEGY, paras);
	}

	public List<StockAnalyseStrategy> getAnalyseStrategys(StrategyType strategyType) {
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("strategyType", strategyType.toString());
		return getSqlSession().selectList(SQL_GET_ANALYSE_STRATEGY_VERSION, paras);
	}

}
