package com.xu.stock.analyse.service;

import java.math.BigDecimal;
import java.util.List;

import com.xu.stock.analyse.model.StockHighest;
import com.xu.stock.data.model.StockDaily;

/**
 * 历史高点
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
public interface IStockHighestService {

    /**
     * 保存历史高点
     * 
     * @param history
     */
    public void saveHighest(List<StockHighest> history);

    /**
     * 获取历史高点
     * 
     * @param stockCode
     * @param parameters
     * @return
     */
    public List<StockHighest> getHighests(String stockCode, String parameters);

    /**
     * 统计历史最高点 TODO Add comments here.
     * 
     * @param stockDailys
     * @param parameters
     * @param lastWaveCycle
     * @param thisWaveCycle
     * @param thisFallRate
     * @return
     */
    public List<StockHighest> analyseHighestPoints(List<StockDaily> stockDailys, String parameters, Integer lastWaveCycle, Integer thisWaveCycle, BigDecimal thisFallRate);
}
