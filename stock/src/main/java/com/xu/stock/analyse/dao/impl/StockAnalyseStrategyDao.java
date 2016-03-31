package com.xu.stock.analyse.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winit.framework.dao.impl.BaseDao;
import com.xu.stock.analyse.dao.IStockAnalyseStrategyDao;
import com.xu.stock.analyse.model.StockAnalyseStrategy;

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
@Repository("analyseStrategyDao")
public class StockAnalyseStrategyDao extends BaseDao<StockAnalyseStrategy> implements IStockAnalyseStrategyDao {

	public final String SQL_GET_ANALYSE_STRATEGY = getNameSpace() + "getAnalyseStrategy";

	public StockAnalyseStrategy getAnalyseStrategy(String strategyType) {
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("strategyType", strategyType);
		return getSqlSession().selectOne(SQL_GET_ANALYSE_STRATEGY, paras);
	}

}
