package com.xu.stock.data.service;

import java.util.List;

import com.xu.stock.data.model.StockDaily;

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
public interface IStockDailyService {

	/**
	 * 修复股票指数
	 * 
	 * 即删除index，插入repairIndexs
	 * 
	 * @param repairIndexs
	 * @param daily
	 */
	public void repairStockDaily(StockDaily daily, List<StockDaily> repairIndexs);

	/**
	 * 根据
	 * 
	 * @param stockCode
	 * @return
	 */
	public List<StockDaily> getStockDaily(String stockCode);

}
