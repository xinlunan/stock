package com.xu.stock.analyse.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winit.framework.dao.impl.BaseDao;
import com.xu.stock.analyse.dao.IStockWatchBeginDao;
import com.xu.stock.analyse.model.StockWatchBegin;
import com.xu.stock.analyse.service.StockAnalyseConstants.StrategyType;

/**
 * 可能购买日期
 * 
 * @version
 * 
 * <pre>
 * Author	Version		Date		Changes
 * lunan.xu 	1.0  		2016年4月26日 	Created
 * </pre>
 * 
 * @since 1.
 */
@Repository("stockWatchBeginDao")
public class StockWatchBeginDao extends BaseDao<StockWatchBegin> implements IStockWatchBeginDao {

    public final String SQL_INSERT_WATCH_BEGIN           = getNameSpace() + "insertWatchBegin";
    public final String SQL_GET_UN_ANALYSE_WATCH_BEGIINS = getNameSpace() + "getUnAnalyseWatchBegins";
    public final String SQL_UPDATE_STATUS                = getNameSpace() + "updateStatus";

    public Integer saveWatchBegins(List<StockWatchBegin> watchBegins) {
        Integer result = 0;
        for (StockWatchBegin watchBegin : watchBegins) {
            result = result + getSqlSession().insert(SQL_INSERT_WATCH_BEGIN, watchBegin);
        }
        return result;
    }

    public List<StockWatchBegin> getUnAnalyseWatchBegins(StrategyType strategy, String stockCode) {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("strategy", strategy.toString());
        paras.put("stockCode", stockCode);
        return getSqlSession().selectList(SQL_GET_UN_ANALYSE_WATCH_BEGIINS, paras);
    }

    public void updateStatus(StockWatchBegin watchBegin) {
        getSqlSession().selectList(SQL_UPDATE_STATUS, watchBegin);
    }

}
