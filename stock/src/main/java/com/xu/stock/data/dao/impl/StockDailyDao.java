package com.xu.stock.data.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winit.framework.dao.impl.BaseDao;
import com.xu.stock.data.dao.IStockDailyDao;
import com.xu.stock.data.model.Stock;
import com.xu.stock.data.model.StockDaily;

/**
 * 股票指数Dao实现
 * 
 * @version Revision History
 * 
 * <pre>
 * Author     Version       Date        Changes
 * lunan.xu    1.0           2015-5-23     Created
 * </pre>
 * 
 * @since 1.
 */
@Repository("stockDailyDao")
public class StockDailyDao extends BaseDao<StockDaily> implements IStockDailyDao {

    public final String SQL_GET_UPDATE_DAILY_VOLUME_RATIO = getNameSpace() + "updateDailyVolumeRatio";
    public final String SQL_GET_UPDATE_DAILY_MA           = getNameSpace() + "updateDailyMa";
    public final String SQL_GET_NO_MA_STOCK_INDEX         = getNameSpace() + "getNoMaStockDailys";
    public final String SQL_GET_HISTORY_STOCK_INDEX       = getNameSpace() + "getHistoryStockDailys";
    public final String SQL_GET_STOCK_INDEX               = getNameSpace() + "getStockDaily";
    public final String SQL_GET_NEXT_STOCK_INDEX          = getNameSpace() + "getNextStockDaily";
    public final String SQL_GET_LAST_STOCK_INDEX          = getNameSpace() + "getLastStockDaily";
    public final String SQL_GET_STOCK_INDEXS              = getNameSpace() + "getStockDailys";
    public final String SQL_INSERT_STOCK_INDEX            = getNameSpace() + "insertStockDaily";
    public final String SQL_UPDATE_STOCK_INDEX            = getNameSpace() + "updateStockDaily";
    public final String SQL_DELETE_STOCK_INDEX            = getNameSpace() + "deleteStockDaily";

    @Override
    public Integer saveStockDailys(List<StockDaily> stockDailys) {
        if (!stockDailys.isEmpty()) {
            getSqlSession().insert(SQL_INSERT_STOCK_INDEX, stockDailys);
        }
        return stockDailys.size();
    }

    @Override
    public List<StockDaily> getRightStockDailys(String stockCode) {
        return getSqlSession().selectList(SQL_GET_STOCK_INDEXS, stockCode);
    }

    @Override
    public List<StockDaily> getStockDailys(String stockCode) {
        return getRightStockDailys(stockCode);
    }

    @Override
    public StockDaily getLastStockDaily(String stockCode, Date date) {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("stockCode", stockCode);
        paras.put("date", date);
        return getSqlSession().selectOne(SQL_GET_LAST_STOCK_INDEX, paras);
    }

    @Override
    public StockDaily getStockDaily(String stockCode, Date date) {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("stockCode", stockCode);
        paras.put("date", date);
        return getSqlSession().selectOne(SQL_GET_NEXT_STOCK_INDEX, paras);
    }

    @Override
    public StockDaily getNextStockDaily(String stockCode, Date date) {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("stockCode", stockCode);
        paras.put("date", date);
        return getSqlSession().selectOne(SQL_GET_NEXT_STOCK_INDEX, paras);
    }

    @Override
    public void updateStockDaily(StockDaily stockDaily) {
        getSqlSession().update(SQL_UPDATE_STOCK_INDEX, stockDaily);
    }

    @Override
    public void deleteStockDaily(String stockCode, Date date) {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("stockCode", stockCode);
        paras.put("date", date);
        getSqlSession().delete(SQL_DELETE_STOCK_INDEX, paras);
    }

    @Override
    public List<StockDaily> getNoMaStockDailys(Stock stock) {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("stockCode", stock.getStockCode());
        return getSqlSession().selectList(SQL_GET_NO_MA_STOCK_INDEX, paras);
    }

    @Override
    public void countDailyVolumeRatio(StockDaily daily) {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("stockCode", daily.getStockCode());
        paras.put("date", daily.getDate());
        getSqlSession().update(SQL_GET_UPDATE_DAILY_VOLUME_RATIO, paras);
    }

    @Override
    public void countDailyMa(StockDaily daily, int size) {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("stockCode", daily.getStockCode());
        paras.put("date", daily.getDate());
        paras.put("size", size);
        getSqlSession().update(SQL_GET_UPDATE_DAILY_MA, paras);
    }

}
