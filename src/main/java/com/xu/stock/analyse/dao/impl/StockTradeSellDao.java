package com.xu.stock.analyse.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winit.framework.dao.impl.BaseDao;
import com.xu.stock.analyse.dao.IStockTradeSellDao;
import com.xu.stock.analyse.model.StockTradeSell;

/**
 * 卖出记录
 * 
 * @version
 * 
 * <pre>
 * Author	Version		Date		Changes
 * lunan.xu 	1.0  		2016年4月30日 	Created
 * </pre>
 * 
 * @since 1.
 */
@Repository("stockTradeSellDao")
public class StockTradeSellDao extends BaseDao<StockTradeSell> implements IStockTradeSellDao {

    public final String SQL_INSERT_STOCK_SELL      = getNameSpace() + "insertStockTradeSell";
    public final String SQL_GET_STOCK_SELLS        = getNameSpace() + "getStockTradeSells";
    public final String SQL_GET_BOUGHT_STOCK_SELLS = getNameSpace() + "getBoughtStockTradeSells";

    public Integer saveStockTradeSells(List<StockTradeSell> sells) {
        Integer result = 0;
        for (StockTradeSell sell : sells) {
            result = result + getSqlSession().insert(SQL_INSERT_STOCK_SELL, sell);
        }
        return result;
    }

    public List<StockTradeSell> getStockTradeSells(String stockCode, String parameters) {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("stockCode", stockCode);
        paras.put("parameters", parameters);
        return getSqlSession().selectList(SQL_GET_STOCK_SELLS, paras);
    }

    public List<StockTradeSell> getBoughtStockTradeSells(String stockCode, String parameters) {
        // TODO Auto-generated method stub
        return null;
    }

}
