package com.xu.stock.data.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winit.framework.dao.impl.BaseDao;
import com.xu.stock.data.dao.IStockMinuteDao;
import com.xu.stock.data.model.StockMinute;

/**
 * 股票分时指数Dao实现
 * 
 * @version
 * 
 * <pre>
 * Author	Version		Date		Changes
 * lunan.xu 	1.0  		2016年3月30日 	Created
 * </pre>
 * 
 * @since 1.
 */
@Repository("stockMinuteDao")
public class StockMinuteDao extends BaseDao<StockMinute> implements IStockMinuteDao {

    public final String SQL_GET_STOCK_MINUTES                  = getNameSpace() + "getStockMinutes";
    public final String SQL_GET_STOCK_MINUTE                   = getNameSpace() + "getStockMinute";
    public final String SQL_GET_HISTORY_STOCK_CLOSE_BUY_MINUTE = getNameSpace() + "getHistoryNearCloseBuyMinute";
    public final String SQL_GET_STOCK_CLOSE_BUY_MINUTE         = getNameSpace() + "getNearCloseBuyMinute";
    public final String SQL_INSERT_STOCK_MINUTE                = getNameSpace() + "insertStockMinute";

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Integer saveStockMinutes(List<StockMinute> stockMinutes) {
        if (!stockMinutes.isEmpty()) {
            return getSqlSession().insert(SQL_INSERT_STOCK_MINUTE, stockMinutes);
        }
        return 0;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<StockMinute> getStockMinutes(String stockCode, Date date) {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("stockCode", stockCode);
        paras.put("date", date);
        return getSqlSession().selectList(SQL_GET_STOCK_MINUTES, paras);
    }

    public StockMinute getHistoryNearCloseBuyMinute(String stockCode, Date date, Integer hour, Integer minute) {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("stockCode", stockCode);
        paras.put("date", date);
        paras.put("hour", hour);
        paras.put("minute", minute);
        return getSqlSession().selectOne(SQL_GET_HISTORY_STOCK_CLOSE_BUY_MINUTE, paras);
    }

    public StockMinute getNearCloseBuyMinute(String stockCode, Date date, Integer hour, Integer minute) {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("stockCode", stockCode);
        paras.put("date", date);
        paras.put("hour", hour);
        paras.put("minute", minute);
        return getSqlSession().selectOne(SQL_GET_HISTORY_STOCK_CLOSE_BUY_MINUTE, paras);
    }

    public StockMinute getStockMinute(String stockCode, Date date, Integer hour, Integer minute) {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("stockCode", stockCode);
        paras.put("date", date);
        paras.put("hour", hour);
        paras.put("minute", minute);
        return getSqlSession().selectOne(SQL_GET_STOCK_MINUTE, paras);
    }

}
