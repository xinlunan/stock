package com.xu.stock.data.service;

import java.util.List;

import com.xu.stock.data.model.StockIndex;

/**
 * 股票指数Service
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
public interface IStockIndexService {

	/**
	 * 修复股票指数
	 * 
	 * 即删除index，插入repairIndexs
	 * 
	 * @param repairIndexs
	 * @param index
	 */
	public void repairStockIndex(StockIndex index, List<StockIndex> repairIndexs);

	/**
	 * 根据
	 * 
	 * @param stockCode
	 * @return
	 */
	public List<StockIndex> getStockIndex(String stockCode);
}
