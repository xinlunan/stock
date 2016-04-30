package com.xu.stock.data.service;

import java.util.Date;
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
     * 保存分时信息
     * 
     * @param stockMinutes
     */
    public void saveStockDailyMinutes(String stockCode, Date date, List<StockMinute> stockMinutes);

    /**
     * 获取当日分时信息
     * 
     * @param daily
     * @return
     */
    public List<StockMinute> getStockMinutes(StockDaily daily);

    /**
     * 获取收盘前购买点,14:45
     * 
     * @param stockDaily
     * @return
     */
    public StockMinute fetchHistoryNearCloseBuyMinute(StockDaily stockDaily);

    /**
     * 下载分时数据
     * 
     * @param daily
     * @return
     */
    public List<StockMinute> downloadHistoryStockMinutes(StockDaily daily);

    /**
     * 获取分时数据
     * 
     * @param watchBegin
     * @return
     */
    public StockMinute fetchRealNearCloseBuyMinute(StockWatchBegin watchBegin);
}
