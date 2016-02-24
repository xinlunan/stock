package com.xu.stock.data.service;

import java.util.List;

import com.xu.stock.data.model.Stock;

/**
 * 股票service
 * 
 * @version Revision History
 * 
 *          <pre>
 * Author     Version       Date        Changes
 * lunan.xu    1.0           2015-5-23     Created
 * 
 * </pre>
 * @since 1.
 */
public interface IStockService {

	/**
	 * 获取股票
	 * 
	 * @param stockCode
	 * @return
	 */
	public Stock getStock(String stockCode);

	/**
	 * 获取所有股票
	 * 
	 * @return
	 */
	public List<Stock> getAllStocks();

	/**
	 * 获取最久没有更新的股票
	 * 
	 * @return
	 */
	public List<Stock> getUnupatedStocks();

	/**
	 * 插入投票
	 * 
	 * @param stocks
	 * @return
	 */
	public Integer insertStocks(List<Stock> stocks);

	/**
	 * 更新股票信息
	 * 
	 * @param stock
	 */
	public void updateStock(Stock stock);

}
