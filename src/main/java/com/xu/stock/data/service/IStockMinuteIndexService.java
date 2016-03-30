package com.xu.stock.data.service;

import java.util.Date;
import java.util.List;

import com.xu.stock.data.model.StockMinuteIndex;

/**
 * 股票指数Service
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
public interface IStockMinuteIndexService {

	/**
	 * 根据
	 * 
	 * @param stockCode
	 * @return
	 */
	public List<StockMinuteIndex> getStockMinuteIndexs(String stockCode, Date date);

	/**
	 * 根据
	 * 
	 * @param stockCode
	 * @return
	 */
	public void saveStockMinuteIndexs(List<StockMinuteIndex> stockMinuteIndexs);
}
