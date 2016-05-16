package com.xu.stock.data.service;

import java.util.List;

import com.xu.stock.analyse.model.StockWatchBegin;
import com.xu.stock.data.model.StockDaily;
import com.xu.stock.data.model.StockMinute;

/**
 * 股票指数Service
 * 
 * @version
 * 
 * <pre>
 * Author	Version		Date		Changes
 * lunan.xu 	1.0  		2016年3月30日 	Created
 * </pre>
 * 
 * @since 1.
 */
public interface IStockMinuteService {

    /**
     * 获取当日分时信息
     * 
     * @param daily
     * @return
     */
    public List<StockMinute> fetchStockMinutes(StockDaily daily);

    /**
     * 获取历史购买时刻的分时信息
     * 
     * @param stockDaily
     * @return
     */
    public StockMinute fetchHistoryNearCloseBuyMinute(StockDaily stockDaily);

    /**
     * 获取实时购买时刻的分时信息
     * 
     * @param watchBegin
     * @return
     */
    public StockMinute fetchRealtimeBuyMinute(StockWatchBegin watchBegin);

    /**
     * 获取历史分时信息
     * 
     * @param daily
     * @return
     */
    public List<StockMinute> fetchHistoryMinutes(StockDaily daily);

    /**
     * 获取实时分时信息
     * 
     * @param watchBegin
     * @return
     */
    public List<StockMinute> fetchRealtimeMinute(StockDaily watchBegin);

}
