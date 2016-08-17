package com.xu.stock.analyse.service;

import java.util.List;

import com.xu.stock.data.model.StockDaily;

/**
 * 卖出交易分析
 * 
 * @version
 * 
 * <pre>
 * Author	Version		Date		Changes
 * lunan.xu 	1.0  		2016年4月30日 	Created
 * </pre>
 * 
 * @since 1.
 */
public interface IHighestProbeSellService {

    /**
     * 分析卖出信息
     * 
     * @param dailys
     * @param parameters
     */
    public void analyseStockTradeSell(List<StockDaily> dailys);

}
