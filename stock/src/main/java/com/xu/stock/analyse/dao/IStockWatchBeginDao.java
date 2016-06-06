package com.xu.stock.analyse.dao;

import java.util.List;

import com.xu.stock.analyse.model.StockWatchBegin;
import com.xu.stock.analyse.service.StockAnalyseConstants.StrategyType;

/**
 * 观察点日期
 * 
 * @version
 * 
 * <pre>
 * Author   Version     Date        Changes
 * lunan.xu     1.0         2016年4月26日  Created
 * </pre>
 * 
 * @since 1.
 */
public interface IStockWatchBeginDao {

    /**
     * 保存观察点
     * 
     * @param watchBegins
     * @return
     */
    public Integer saveWatchBegins(List<StockWatchBegin> watchBegins);

    /**
     * 获取未分析的观察点
     * 
     * @param type
     * @param parameters
     * @param stockCode
     * @return
     */
    public List<StockWatchBegin> getUnAnalyseWatchBegins(StrategyType type, String stockCode);

    /**
     * 更新分析状态
     * 
     * @param watchBegin
     */
    public void updateStatus(StockWatchBegin watchBegin);

}
