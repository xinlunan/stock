package com.xu.stock.analyse.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winit.framework.dao.impl.BaseDao;
import com.xu.stock.analyse.dao.IStockHistoryHighestDao;
import com.xu.stock.analyse.model.StockHistoryHighest;

/**
 * 股票历史最高价
 * 
 * @version
 * 
 * <pre>
 * Author	Version		Date		Changes
 * lunan.xu 	1.0  		2016年4月8日 	Created
 * </pre>
 * 
 * @since 1.
 */
@Repository("stockHistoryHighestDao")
public class StockHistoryHighestDao extends BaseDao<StockHistoryHighest> implements IStockHistoryHighestDao {

    public final String SQL_INSERT_HISTORY_HIGHEST = getNameSpace() + "insertHistoryHighest";
    public final String SQL_GET_HISTORY_HIGHESTS   = getNameSpace() + "getHistoryHighests";

    public List<StockHistoryHighest> getHistoryHighests(String stockCode) {
        return getSqlSession().selectList(SQL_GET_HISTORY_HIGHESTS, stockCode);
    }

    public Integer saveHistoryHighest(List<StockHistoryHighest> historys) {
        Integer result = 0;
        for (StockHistoryHighest history : historys) {
            getSqlSession().insert(SQL_INSERT_HISTORY_HIGHEST, history);
            result++;
        }
        return result;
    }

}
