package com.xu.stock.data.dao.impl;

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
@Repository("stockMinuteDao")
public class StockMinuteDao extends BaseDao<StockMinute> implements IStockMinuteDao {

	public final String SQL_GET_STOCK_MINUTE_INDEX = getNameSpace() + "getStockMinute";
	public final String SQL_INSERT_STOCK_MINUTE_INDEX = getNameSpace() + "insertStockMinute";

	public Integer saveStockMinutes(List<StockMinute> stockMinutes) {
		Integer result = 0;
		for (StockMinute stockMinute : stockMinutes) {
			getSqlSession().insert(SQL_INSERT_STOCK_MINUTE_INDEX, stockMinute);
			result++;
		}
		return result;
	}

	public List<StockMinute> getStockMinutes(String stockCode, Date date) {
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("stockCode", stockCode);
		paras.put("date", date);
		return getSqlSession().selectList(SQL_GET_STOCK_MINUTE_INDEX, paras);
	}

}
