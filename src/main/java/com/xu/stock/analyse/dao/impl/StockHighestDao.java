package com.xu.stock.analyse.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winit.framework.dao.impl.BaseDao;
import com.xu.stock.analyse.dao.IStockHighestDao;
import com.xu.stock.analyse.model.StockHighest;

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
@Repository("stockHighestDao")
public class StockHighestDao extends BaseDao<StockHighest> implements IStockHighestDao {

    public final String SQL_INSERT_HISTORY_HIGHEST         = getNameSpace() + "insertHighest";
    public final String SQL_GET_HISTORY_HIGHESTS           = getNameSpace() + "getHighests";
    public final String SQL_UPDATE_HISTORY_HIGHEST_ANALYSE = getNameSpace() + "updateHighestAnalyse";

    public List<StockHighest> getHighests(String stockCode, String parameters) {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("stockCode", stockCode);
        paras.put("parameters", parameters);
        return getSqlSession().selectList(SQL_GET_HISTORY_HIGHESTS, paras);
    }

    public Integer saveHighest(List<StockHighest> historys) {
        Integer result = 0;
        for (StockHighest history : historys) {
            result = result + getSqlSession().insert(SQL_INSERT_HISTORY_HIGHEST, history);
        }
        return result;
    }

    public Integer updateHighestAnalyse(StockHighest history) {
        return getSqlSession().update(SQL_UPDATE_HISTORY_HIGHEST_ANALYSE, history);
    }

}
