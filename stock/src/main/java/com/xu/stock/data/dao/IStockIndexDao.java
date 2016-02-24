package com.xu.stock.data.dao;

import java.util.Date;
import java.util.List;

import com.xu.stock.data.model.StockIndex;

/**
 * 股票指数Dao
 * 
 * @version Revision History
 * 
 *          <pre>
 * Author     Version       Date        Changes
 * lunan.xu    1.0           2015-5-25     Created
 * 
 * </pre>
 * @since 1.
 */
public interface IStockIndexDao {

	/**
	 * 获取股票指数
	 * 
	 * @param index
	 * @return
	 */
	public StockIndex getStockIndex(String stockCode, Date date);

	/**
	 * 根据股票编码获取股票指数
	 * 
	 * @param stockCode
	 * @return
	 */
	public List<StockIndex> getStockIndexs(String stockCode);

	/**
	 * 保存股票指数
	 * 
	 * @param stockIndexs
	 * @return
	 */
	public Integer saveStockIndexs(List<StockIndex> stockIndexs);

	/**
	 * 更新股票指数日期
	 * 
	 * @param stockCode
	 * @param date
	 */
	public void updateStockIndex(StockIndex stockIndex);

	/**
	 * 删除股票指数
	 * 
	 * @param stockCode
	 * @param date
	 */
	public void deleteStockIndex(String stockCode, Date date);

	/**
	 * 获取上一天股票指数
	 * 
	 * @param stockCode
	 * @param date
	 * @return
	 */
	public StockIndex getLastStockIndex(String stockCode, Date date);

}
