package com.xu.stock.data.service;

import java.util.Date;
import java.util.List;

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
    public void saveStockMinutes(List<StockMinute> stockMinutes);

    /**
     * 获取当日分时信息
     * 
     * @param stockDaily
     * @param date
     * @return
     */
    public List<StockMinute> getStockMinutes(StockDaily stockDaily, Date date);

    /**
     * 获取收盘前购买点,14:45
     * 
     * @param stockDaily
     * @param date
     * @return
     */
    public StockMinute getNearCloseBuyMinute(StockDaily stockDaily, Date date);

    /**
     * 下载分时数据
     * 
     * @param stockDaily
     * @param date
     * @return
     */
    public List<StockMinute> downloadStockMinutes(StockDaily stockDaily, Date date);
}
