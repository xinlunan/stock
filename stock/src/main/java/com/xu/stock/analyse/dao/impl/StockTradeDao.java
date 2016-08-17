package com.xu.stock.analyse.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winit.framework.dao.impl.BaseDao;
import com.xu.stock.analyse.dao.IStockTradeDao;
import com.xu.stock.analyse.model.StockTrade;
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
@Repository("stockTradeDao")
public class StockTradeDao extends BaseDao<StockTrade> implements IStockTradeDao {

    public final String SQL_GET_STOCK_TRADES     = getNameSpace() + "getStockTrades";
    public final String SQL_GET_BUY_STOCK_TRADE  = getNameSpace() + "getBuyStockTrade";
    public final String SQL_GET_SELL_STOCK_TRADE = getNameSpace() + "getSellStockTrade";
    public final String SQL_INSERT_STOCK_TRADE   = getNameSpace() + "insertStockTrade";
    public final String SQL_GET_BUY_STOCK_TRADES = getNameSpace() + "getBuyStockTrades";

    @Override
    public Integer saveStockTrades(List<StockTrade> stockTrades) {
		Integer result = 0;
        for (StockTrade trade : stockTrades) {
			if (!existTrade(trade)) {
				getSqlSession().insert(SQL_INSERT_STOCK_TRADE, trade);
			}
		}
		return result;
	}

	@Override
    public boolean existTrade(StockTrade trade) {
		StockTrade oldTrade;
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

    @Override
    public List<StockTrade> getStockTrades(String stockCode) {
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("stockCode", stockCode);
		return getSqlSession().selectList(SQL_GET_STOCK_TRADES, paras);
	}

	@Override
    public List<StockTrade> getBuyTrades(StrategyType strategy, String stockCode) {
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("stockCode", stockCode);
		paras.put("strategy", strategy.toString());
		return getSqlSession().selectList(SQL_GET_BUY_STOCK_TRADES, paras);
	}

}
