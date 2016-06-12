package com.xu.stock.data.service;

import java.util.Date;
import java.util.List;

import com.xu.stock.data.model.Stock;
import com.xu.stock.data.model.StockDaily;

/**
 * 股票service
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
     * 获取待分析买入的股票
     * 
     * @return
     */
    public List<Stock> getAnalyseBuyStocks();

    /**
     * 获取待分析卖出的股票
     * 
     * @return
     */
    public List<Stock> getAnalyseSellStocks();

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
	 * 保留股票数据
	 * 
	 * @param stock
	 */
	public void saveStockData(Stock stock);

    /**
     * 根据
     * 
     * @param stockCode
     * @return
     */
    public StockDaily getNextDaily(String stockCode, Date date);
}
