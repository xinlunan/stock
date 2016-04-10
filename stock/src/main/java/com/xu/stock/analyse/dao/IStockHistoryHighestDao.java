package com.xu.stock.analyse.dao;

import java.util.List;

import com.xu.stock.analyse.model.StockHistoryHighest;

/**
 * 历史最高点
 * 
 * @version
 * 
 * <pre>
 * Author	Version		Date		Changes
 * lunan.xu 	1.0  		2016年4月8日 	Created
 * </pre>
 * 
 * @since 1.
 */
public interface IStockHistoryHighestDao {

    /**
     * 获取历史最高价
     * 
     * @param stockCode
     * @return
     */
    public List<StockHistoryHighest> getHistoryHighests(String stockCode);


    /**
     * 保存历史最高价
     * 
     * @param trades
     * @return
     */
    public Integer saveHistoryHighest(List<StockHistoryHighest> history);

}
