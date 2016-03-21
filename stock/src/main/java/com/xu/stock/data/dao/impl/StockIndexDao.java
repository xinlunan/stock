package com.xu.stock.data.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winit.framework.dao.impl.BaseDao;
import com.xu.stock.data.dao.IStockIndexDao;
import com.xu.stock.data.model.StockIndex;

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
@Repository("stockIndexDao")
public class StockIndexDao extends BaseDao<StockIndex> implements IStockIndexDao {

	public final String SQL_GET_STOCK_INDEX = getNameSpace() + "getStockIndex";
	public final String SQL_GET_LAST_STOCK_INDEX = getNameSpace() + "getLastStockIndex";
	public final String SQL_GET_STOCK_INDEXS = getNameSpace() + "getStockIndexs";
	public final String SQL_INSERT_STOCK_INDEX = getNameSpace() + "insertStockIndex";
	public final String SQL_UPDATE_STOCK_INDEX = getNameSpace() + "updateStockIndex";
	public final String SQL_DELETE_STOCK_INDEX = getNameSpace() + "deleteStockIndex";

	public Integer saveStockIndexs(List<StockIndex> stockIndexs) {
		Integer result = 0;
		for (StockIndex stockIndex : stockIndexs) {
			// log.debug(stockIndex.toString());
			getSqlSession().insert(SQL_INSERT_STOCK_INDEX, stockIndex);
			result++;
		}
		return result;
	}

	public List<StockIndex> getStockIndexs(String stockCode) {
		return getSqlSession().selectList(SQL_GET_STOCK_INDEXS, stockCode);
	}

	public StockIndex getLastStockIndex(String stockCode, Date date) {
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("stockCode", stockCode);
		paras.put("date", date);
		return getSqlSession().selectOne(SQL_GET_LAST_STOCK_INDEX, paras);
	}

	public StockIndex getStockIndex(String stockCode, Date date) {
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("stockCode", stockCode);
		paras.put("date", date);
		return getSqlSession().selectOne(SQL_GET_STOCK_INDEX, paras);
	}

	public void updateStockIndex(StockIndex stockIndex) {
		getSqlSession().update(SQL_UPDATE_STOCK_INDEX, stockIndex);
	}

	public void deleteStockIndex(String stockCode, Date date) {
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("stockCode", stockCode);
		paras.put("date", date);
		getSqlSession().delete(SQL_DELETE_STOCK_INDEX, paras);
	}

}
