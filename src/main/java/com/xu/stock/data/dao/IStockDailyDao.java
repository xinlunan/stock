package com.xu.stock.data.dao;

import java.util.Date;
import java.util.List;

import com.xu.stock.data.model.Stock;
import com.xu.stock.data.model.StockDaily;

/**
 * 股票指数Dao
 * 
 * @version Revision History
 * 
 *          <pre>
 * Author     Version       Date        Changes
 * lunan.xu    1.0           2015-5-25     Created
 * 
 *          </pre>
 * 
 * @since 1.
 */
public interface IStockDailyDao {

	/**
	 * 获取股票指数
	 * 
	 * @param stockCode
	 * @param date
	 * @return
	 */
	public StockDaily getStockDaily(String stockCode, Date date);

	/**
	 * 根据股票编码获取后复权后股票指数
	 * 
	 * @param stockCode
	 * @return
	 */
	public List<StockDaily> getRightStockDailys(String stockCode);

	/**
	 * 根据股票编码获取后复权后股票指数
	 * 
	 * @param stockCode
	 * @return
	 */
	public List<StockDaily> getStockDailys(String stockCode);

	/**
	 * 保存股票指数
	 * 
	 * @param stockDailys
	 * @return
	 */
	public Integer saveStockDailys(List<StockDaily> stockDailys);

	/**
	 * 更新股票指数日期
	 * 
	 * @param stockCode
	 * @param date
	 */
	public void updateStockDaily(StockDaily stockDaily);

	/**
	 * 删除股票指数
	 * 
	 * @param stockCode
	 * @param date
	 */
	public void deleteStockDaily(String stockCode, Date date);

	/**
	 * 获取上一天股票指数
	 * 
	 * @param stockCode
	 * @param date
	 * @return
	 */
	public StockDaily getLastStockDaily(String stockCode, Date date);

	/**
	 * 获取下一天的指数
	 * 
	 * @param stockCode
	 * @param date
	 * @return
	 */
	public StockDaily getNextStockDaily(String stockCode, Date date);

    public List<StockDaily> getNoMaStockDailys(Stock stock);

    public void countDailyVolumeRatio(StockDaily daily);

    public void countDailyMa(StockDaily daily, int i);

}
