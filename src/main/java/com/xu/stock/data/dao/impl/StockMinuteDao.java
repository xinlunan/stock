package com.xu.stock.data.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

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

    private final String SQL_GET_HISTORY_STOCK_MINUTE            = getNameSpace() + "getHistoryMinutes";
    private final String SQL_GET_REALTIME_STOCK_MINUTE           = getNameSpace() + "getRealtimeMinutes";
    public final String  SQL_GET_STOCK_MINUTES                   = getNameSpace() + "getStockMinutes";
    public final String  SQL_GET_STOCK_MINUTE                    = getNameSpace() + "getStockMinute";
    public final String  SQL_GET_HISTORY_STOCK_CLOSE_BUY_MINUTE  = getNameSpace() + "getHistoryNearCloseBuyMinute";
    public final String  SQL_GET_REALTIME_STOCK_CLOSE_BUY_MINUTE = getNameSpace() + "getRealtimeNearCloseBuyMinute";
    public final String  SQL_GET_STOCK_CLOSE_BUY_MINUTE          = getNameSpace() + "getNearCloseBuyMinute";
    public final String  SQL_INSERT_STOCK_MINUTE                 = getNameSpace() + "insertStockMinute";

    @Override
    public Integer saveStockMinutes(List<StockMinute> stockMinutes) {
        Integer result = 0;
        for (StockMinute stockMinute : stockMinutes) {
            StockMinute existMinute = getStockMinute(stockMinute.getStockCode(), stockMinute.getDate(), stockMinute.getHour(), stockMinute.getMinute());
            if (existMinute == null) {
                result = result + getSqlSession().insert(SQL_INSERT_STOCK_MINUTE, stockMinute);
            }
        }
        return result;
    }

    @Override
    public Integer saveStockMinute(StockMinute stockMinute) {
        List<StockMinute> stockMinutes = new ArrayList<StockMinute>();
        stockMinutes.add(stockMinute);
        return saveStockMinutes(stockMinutes);
    }

    @Override
    public List<StockMinute> getStockMinutes(String stockCode, Date date) {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("stockCode", stockCode);
        paras.put("date", date);
        return getSqlSession().selectList(SQL_GET_STOCK_MINUTES, paras);
    }

    @Override
    public StockMinute getHistoryNearCloseBuyMinute(String stockCode, Date date, Integer hour, Integer minute) {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("stockCode", stockCode);
        paras.put("date", date);
        paras.put("hour", hour);
        paras.put("minute", minute);
        return getSqlSession().selectOne(SQL_GET_HISTORY_STOCK_CLOSE_BUY_MINUTE, paras);
    }

    @Override
    public StockMinute getRealtimeNearCloseBuyMinute(String stockCode, Date date, Integer hour, Integer minute) {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("stockCode", stockCode);
        paras.put("date", date);
        paras.put("hour", hour);
        paras.put("minute", minute);
        return getSqlSession().selectOne(SQL_GET_REALTIME_STOCK_CLOSE_BUY_MINUTE, paras);
    }

    @Override
    public StockMinute getStockMinute(String stockCode, Date date, Integer hour, Integer minute) {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("stockCode", stockCode);
        paras.put("date", date);
        paras.put("hour", hour);
        paras.put("minute", minute);
        return getSqlSession().selectOne(SQL_GET_STOCK_MINUTE, paras);
    }

    @Override
    public List<StockMinute> getHistoryMinutes(String stockCode, Date date) {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("stockCode", stockCode);
        paras.put("date", date);
        return getSqlSession().selectList(SQL_GET_HISTORY_STOCK_MINUTE, paras);
    }

    @Override
    public List<StockMinute> getRealtimeMinutes(String stockCode, Date date) {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("stockCode", stockCode);
        paras.put("date", date);
        return getSqlSession().selectList(SQL_GET_REALTIME_STOCK_MINUTE, paras);
    }

}
