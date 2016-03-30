package com.xu.stock.analyse.service;

import java.util.List;

import com.xu.stock.data.model.StockDaily;

/**
 * 股票分析师
 * 
 * @version
 * 
 * 			<pre>
 * Author	Version		Date		Changes
 * lunan.xu 	1.0  		2016年3月19日 	Created
 *
 *          </pre>
 * 
 * @since 1.
 */
public interface IStockAnalyst {

	/**
	 * 输入股票指数进行分析
	 * 
	 * @param dailys
	 */
	public List<StockDaily> putStockDailys(List<StockDaily> dailys);

	/**
	 * 获取分析可购买点
	 * 
	 * @return
	 */
	public List<StockDaily> getBuyPoints();

}
