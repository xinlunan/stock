package com.xu.stock.analyse.dao.impl;

import org.springframework.stereotype.Repository;

import com.winit.framework.dao.impl.BaseDao;
import com.xu.stock.analyse.dao.IStockAnalyseRecordDao;
import com.xu.stock.analyse.model.StockAnalyseRecord;
import com.xu.stock.analyse.model.StockBuyTrade;

/**
 * 股票分析记录
 * 
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
@Repository("stockAnalyseRecordDao")
public class StockAnalyseRecordDao extends BaseDao<StockBuyTrade> implements IStockAnalyseRecordDao {

	public final String SQL_INSERT_ANALYSE_RECORD = getNameSpace() + "insertAnalyseRecord";
	public final String SQL_UPDATE_ANALYSE_RECORD = getNameSpace() + "updateAnalyseRecord";
	public final String SQL_GET_ANALYSE_RECORD = getNameSpace() + "getAnalyseRecord";

	public Integer saveOrUpdate(StockAnalyseRecord analyseRecord) {
		StockAnalyseRecord oldRecord = getAnalyseRecord(analyseRecord);
		if (oldRecord != null) {
			getSqlSession().selectOne(SQL_UPDATE_ANALYSE_RECORD, analyseRecord);
		} else {
			getSqlSession().insert(SQL_INSERT_ANALYSE_RECORD, analyseRecord);
		}
		return 1;
	}

	public StockAnalyseRecord getAnalyseRecord(StockAnalyseRecord analyseRecord) {
		return getSqlSession().selectOne(SQL_GET_ANALYSE_RECORD, analyseRecord);
	}

}
