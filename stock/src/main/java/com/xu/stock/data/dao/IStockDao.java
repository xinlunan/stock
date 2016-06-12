package com.xu.stock.data.dao;

import java.util.List;

import com.xu.stock.data.model.Stock;

/**
 * 股票Dao
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
public interface IStockDao {

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
	 * 添加股票
	 * 
	 * @param stock
	 * @return
	 */
	public Integer insertStock(Stock stock);

	/**
	 * 更新股票信息
	 * 
	 * @param stock
	 * @return
	 */
	public Integer updateStock(Stock stock);

    public List<Stock> getAnalyseBuyStocks();

    public List<Stock> getAnalyseSellStocks();

}
