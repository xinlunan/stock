package com.xu.stock.data.dao;

import java.util.Date;
import java.util.List;

import com.xu.stock.data.model.StockMinuteIndex;

/**
 * 股票分时指数Dao
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
public interface IStockMinuteIndexDao {

	/**
	 * 获取股票指数
	 * 
	 * @param index
	 * @return
	 */
	public List<StockMinuteIndex> getStockMinuteIndexs(String stockCode, Date date);

	/**
	 * 保存股票指数
	 * 
	 * @param stockIndexs
	 * @return
	 */
	public Integer saveStockMinuteIndexs(List<StockMinuteIndex> stockIndexs);

}
