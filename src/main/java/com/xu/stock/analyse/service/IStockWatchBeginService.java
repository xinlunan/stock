package com.xu.stock.analyse.service;

import java.math.BigDecimal;
import java.util.List;

import com.xu.stock.analyse.model.StockHighest;
import com.xu.stock.analyse.model.StockWatchBegin;
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
     * @param highestPoints
     * @param parameters
     * @param thisFallRate
     * @param warnRateLow
     * @param buyRateLow
     * @param buyRateHigh
     * @return
     */
    public List<StockWatchBegin> analyseBatchBeginByHighest(List<StockDaily> dailys, List<StockHighest> highestPoints, String parameters, BigDecimal thisFallRate, BigDecimal warnRateLow, BigDecimal buyRateLow, BigDecimal buyRateHigh);

}
