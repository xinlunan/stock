package com.xu.stock.analyse.service;

import java.math.BigDecimal;
import java.util.List;

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
public interface IHighestProbeHighestPointService {

    /**
     * 统计历史最高点
     * 
     * @param stockDailys
     * @param parameters
     * @param lastWaveCycle
     * @param thisWaveCycle
     * @param thisFallRate
     */
    public void analyseHighestPoints(List<StockDaily> stockDailys, Integer lastWaveCycle, Integer thisWaveCycle, BigDecimal thisFallRate);
}
