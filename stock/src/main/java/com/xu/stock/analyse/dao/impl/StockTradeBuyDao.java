package com.xu.stock.analyse.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winit.framework.dao.impl.BaseDao;
import com.xu.stock.analyse.dao.IStockTradeBuyDao;
import com.xu.stock.analyse.model.StockTradeBuy;

/**
 * 购买
 * 
 * @version
 * 
 * <pre>
 * Author	Version		Date		Changes
 * lunan.xu 	1.0  		2016年4月29日 	Created
 * </pre>
 * 
 * @since 1.
 */
@Repository("stockTradeBuyDao")
public class StockTradeBuyDao extends BaseDao<StockTradeBuy> implements IStockTradeBuyDao {

    private final String SQL_UPDATE_STOCK_BUY      = getNameSpace() + "updateStatus";
    public final String  SQL_INSERT_STOCK_BUY      = getNameSpace() + "insertStockTradeBuy";
    public final String  SQL_GET_STOCK_BUYS        = getNameSpace() + "getStockTradeBuys";
    public final String  SQL_GET_BOUGHT_STOCK_BUYS = getNameSpace() + "getBoughtStockTradeBuys";

    public Integer saveStockTradeBuys(List<StockTradeBuy> buys) {
        Integer result = 0;
        for (StockTradeBuy buy : buys) {
            result = result + getSqlSession().insert(SQL_INSERT_STOCK_BUY, buy);
        }
        return result;
    }

    public List<StockTradeBuy> getStockTradeBuys(String stockCode, String strategy, String parameters) {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("stockCode", stockCode);
        paras.put("strategy", strategy);
        paras.put("parameters", parameters);
        return getSqlSession().selectList(SQL_GET_STOCK_BUYS, paras);
    }

    public List<StockTradeBuy> getBoughtStockTradeBuys(String stockCode, String strategy, String parameters) {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("stockCode", stockCode);
        paras.put("strategy", strategy);
        paras.put("parameters", parameters);
        return getSqlSession().selectList(SQL_GET_BOUGHT_STOCK_BUYS, paras);
    }

    public Integer updateStatus(StockTradeBuy buy) {
        return getSqlSession().insert(SQL_UPDATE_STOCK_BUY, buy);
    }

}
