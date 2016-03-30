package com.xu.stock.data.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winit.framework.dao.impl.BaseDao;
import com.xu.stock.data.dao.IStockTradeDao;
import com.xu.stock.data.model.StockTrade;

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
@Repository("stockTradeDao")
public class StockTradeDao extends BaseDao<StockTrade> implements IStockTradeDao {

	public final String SQL_GET_STOCK_TRADES = getNameSpace() + "getStockTrades";
	public final String SQL_INSERT_STOCK_TRADE = getNameSpace() + "insertStockTrade";

	public Integer saveStockTrades(List<StockTrade> stockTrades) {
		Integer result = 0;
		for (StockTrade trade : stockTrades) {
			getSqlSession().insert(SQL_INSERT_STOCK_TRADE, trade);
			result++;
		}
		return result;
	}

	public List<StockTrade> getStockTrades(String stockCode) {
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("stockCode", stockCode);
		return getSqlSession().selectList(SQL_GET_STOCK_TRADES, paras);
	}

}
