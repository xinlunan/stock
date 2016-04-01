package com.xu.stock.analyse.dao;

import com.xu.stock.analyse.model.StockAnalyseRecord;

/**
 * 分析记录
 * 
 * @version
 * 
 * 			<pre>
 * Author	Version		Date		Changes
 * lunan.xu 	1.0  		2016年4月1日 	Created
 *
 *          </pre>
 * 
 * @since 1.
 */
public interface IStockAnalyseRecordDao {
	/**
	 * 保存或更新
	 * 
	 * @param analyseRecord
	 * @return
	 */
	public Integer saveOrUpdate(StockAnalyseRecord analyseRecord);

	/**
	 * 获取分析记录
	 * 
	 * @param analyseRecord
	 * @return
	 */
	public StockAnalyseRecord getAnalyseRecord(StockAnalyseRecord analyseRecord);
}
