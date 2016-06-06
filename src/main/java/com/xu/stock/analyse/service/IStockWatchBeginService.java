package com.xu.stock.analyse.service;

import java.math.BigDecimal;
import java.util.List;

import com.xu.stock.data.model.StockDaily;

/**
 * 可购买的点
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
public interface IStockWatchBeginService {

    /**
     * 根据最高点找出可能试探突破点的日期
     * 
     * @param dailys
     * @param lastWaveCycle
     * @param thisWaveCycle
     * @param thisFallRate
     */
    public void analyseBatchBeginByHighest(List<StockDaily> dailys, Integer lastWaveCycle, Integer thisWaveCycle, BigDecimal thisFallRate);

}
