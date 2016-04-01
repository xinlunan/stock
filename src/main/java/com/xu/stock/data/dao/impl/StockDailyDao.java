package com.xu.stock.data.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winit.framework.dao.impl.BaseDao;
import com.xu.stock.data.dao.IStockDailyDao;
import com.xu.stock.data.model.StockDaily;

/**
 * 股票指数Dao实现
 * 
 * @version Revision History
 * 
 *          <pre>
 * Author     Version       Date        Changes
 * lunan.xu    1.0           2015-5-23     Created
 * 
 *          </pre>
 * 
 * @since 1.
 */
@Repository("stockDailyDao")
public class StockDailyDao extends BaseDao<StockDaily> implements IStockDailyDao {

	public final String SQL_GET_STOCK_INDEX = getNameSpace() + "getStockDaily";
	public final String SQL_GET_NEXT_STOCK_INDEX = getNameSpace() + "getNextStockDaily";
	public final String SQL_GET_LAST_STOCK_INDEX = getNameSpace() + "getLastStockDaily";
	public final String SQL_GET_STOCK_INDEXS = getNameSpace() + "getStockDailys";
	public final String SQL_INSERT_STOCK_INDEX = getNameSpace() + "insertStockDaily";
	public final String SQL_UPDATE_STOCK_INDEX = getNameSpace() + "updateStockDaily";
	public final String SQL_DELETE_STOCK_INDEX = getNameSpace() + "deleteStockDaily";

	public Integer saveStockDailys(List<StockDaily> stockDailys) {
		Integer result = 0;
		for (StockDaily stockDaily : stockDailys) {
			// log.debug(stockDaily.toString());
			getSqlSession().insert(SQL_INSERT_STOCK_INDEX, stockDaily);
			result++;
		}
		return result;
	}

	public List<StockDaily> getRrightStockDailys(String stockCode) {
		return getSqlSession().selectList(SQL_GET_STOCK_INDEXS, stockCode);
	}

	public List<StockDaily> getStockDailys(String stockCode) {
		return getRrightStockDailys(stockCode);
	}

	public StockDaily getLastStockDaily(String stockCode, Date date) {
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("stockCode", stockCode);
		paras.put("date", date);
		return getSqlSession().selectOne(SQL_GET_LAST_STOCK_INDEX, paras);
	}

	public StockDaily getStockDaily(String stockCode, Date date) {
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("stockCode", stockCode);
		paras.put("date", date);
		return getSqlSession().selectOne(SQL_GET_NEXT_STOCK_INDEX, paras);
	}

	public StockDaily getNextStockDaily(String stockCode, Date date) {
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("stockCode", stockCode);
		paras.put("date", date);
		return getSqlSession().selectOne(SQL_GET_NEXT_STOCK_INDEX, paras);
	}

	public void updateStockDaily(StockDaily stockDaily) {
		getSqlSession().update(SQL_UPDATE_STOCK_INDEX, stockDaily);
	}

	public void deleteStockDaily(String stockCode, Date date) {
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("stockCode", stockCode);
		paras.put("date", date);
		getSqlSession().delete(SQL_DELETE_STOCK_INDEX, paras);
	}

}
