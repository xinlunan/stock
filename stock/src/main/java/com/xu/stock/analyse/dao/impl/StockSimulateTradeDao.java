package com.xu.stock.analyse.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winit.framework.dao.impl.BaseDao;
import com.xu.stock.analyse.dao.IStockSimulateTradeDao;
import com.xu.stock.analyse.model.StockSimulateTrade;
import com.xu.stock.analyse.service.StockAnalyseConstants.StrategyType;
import com.xu.stock.analyse.service.StockAnalyseConstants.TradeType;

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
@Repository("stockSimulateTradeDao")
public class StockSimulateTradeDao extends BaseDao<StockSimulateTrade> implements IStockSimulateTradeDao {

	public final String SQL_GET_STOCK_TRADES = getNameSpace() + "getStockSimulateTrades";
	public final String SQL_GET_BUY_STOCK_TRADE = getNameSpace() + "getBuyStockSimulateTrade";
	public final String SQL_GET_SELL_STOCK_TRADE = getNameSpace() + "getSellStockSimulateTrade";
	public final String SQL_INSERT_STOCK_TRADE = getNameSpace() + "insertStockSimulateTrade";
	public final String SQL_GET_BUY_STOCK_TRADES = getNameSpace() + "getBuyStockSimulateTrades";

	public Integer saveStockSimulateTrades(List<StockSimulateTrade> stockSimulateTrades) {
		Integer result = 0;
		for (StockSimulateTrade trade : stockSimulateTrades) {
			if (!existTrade(trade)) {
				getSqlSession().insert(SQL_INSERT_STOCK_TRADE, trade);
			}
		}
		return result;
	}

	public boolean existTrade(StockSimulateTrade trade) {
		StockSimulateTrade oldTrade;
		if (TradeType.BUY.equals(trade.getTradeType())) {
			oldTrade = getSqlSession().selectOne(SQL_GET_BUY_STOCK_TRADE, trade);
		} else {
			oldTrade = getSqlSession().selectOne(SQL_GET_SELL_STOCK_TRADE, trade);
		}
		if (oldTrade != null) {
			return true;
		}
		return false;
	}

	public List<StockSimulateTrade> getStockSimulateTrades(String stockCode) {
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("stockCode", stockCode);
		return getSqlSession().selectList(SQL_GET_STOCK_TRADES, paras);
	}

	public List<StockSimulateTrade> getBuyTrades(StrategyType strategy, String stockCode) {
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("stockCode", stockCode);
		paras.put("strategy", strategy.toString());
		return getSqlSession().selectList(SQL_GET_BUY_STOCK_TRADES, paras);
	}

}
