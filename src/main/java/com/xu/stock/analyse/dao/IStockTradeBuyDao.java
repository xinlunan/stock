package com.xu.stock.analyse.dao;

import java.util.List;

import com.xu.stock.analyse.model.StockTradeBuy;

public interface IStockTradeBuyDao {

    /**
     * 保存购买信息
     * 
     * @param buys
     * @return
     */
    public Integer saveStockTradeBuys(List<StockTradeBuy> buys);

    /**
     * 获取购买信息
     * 
     * @param stockCode
     * @param parameters
     * @return
     */
    public List<StockTradeBuy> getStockTradeBuys(String stockCode, String parameters);

    /**
     * 获取未卖出的购买信息
     * 
     * @param stockCode
     * @param parameters
     * @return
     */
    public List<StockTradeBuy> getBoughtStockTradeBuys(String stockCode, String parameters);

}
