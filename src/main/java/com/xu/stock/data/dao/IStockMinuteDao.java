package com.xu.stock.data.dao;

import java.util.Date;
import java.util.List;

import com.xu.stock.data.model.StockMinute;

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
public interface IStockMinuteDao {

	/**
	 * 获取股票指数
	 * 
	 * @param stockCode
	 * @param date
	 * @return
	 */
    public List<StockMinute> getStockMinutes(String stockCode, Date date);

	/**
	 * 保存股票指数
	 * 
	 * @param stockMinutes
	 * @return
	 */
	public Integer saveStockMinutes(List<StockMinute> stockMinutes);

    /**
     * 获取收盘前的购买点
     * 
     * @param stockCode
     * @param date
     * @param hour
     * @param minute
     * @return
     */
    public StockMinute getHistoryNearCloseBuyMinute(String stockCode, Date date, Integer hour, Integer minute);

    /**
     * 获取收盘前的购买点
     * 
     * @param stockCode
     * @param date
     * @param hour
     * @param minute
     * @return
     */
    public StockMinute getNearCloseBuyMinute(String stockCode, Date date, Integer hour, Integer minute);

    /**
     * 获取分时信息
     * 
     * @param stockCode
     * @param date
     * @param hour
     * @param minute
     * @return
     */
    public StockMinute getStockMinute(String stockCode, Date date, Integer hour, Integer minute);

}
