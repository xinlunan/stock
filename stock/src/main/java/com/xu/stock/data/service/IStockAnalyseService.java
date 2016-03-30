package com.xu.stock.data.service;

import java.util.List;

import com.xu.stock.data.model.Stock;
import com.xu.stock.data.model.StockDaily;

/**
 * 股票分析service接口
 * 
 * @version Revision History
 * 
 *          <pre>
 * Author     Version       Date        Changes
 * lunan.xu    1.0           2015-5-31     Created
 * 
 *          </pre>
 * 
 * @since 1.
 */
public interface IStockAnalyseService {

	/**
	 * 股票分析
	 * 
	 * @return
	 */
	public List<StockDaily> analyse(Stock stock);

}
