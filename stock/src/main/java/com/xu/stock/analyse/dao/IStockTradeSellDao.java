package com.xu.stock.analyse.dao;

import java.util.List;

import com.xu.stock.analyse.model.StockTradeSell;

/**
 * 卖出
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
public interface IStockTradeSellDao {

    /**
     * 保存卖出信息
     * 
     * @param sellTrade
     */
    public Integer saveTradeSell(StockTradeSell sellTrade);

    /**
     * 保存卖出信息
     * 
     * @param sells
     * @return
     */
    public Integer saveStockTradeSells(List<StockTradeSell> sells);

}
