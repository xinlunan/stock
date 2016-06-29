package com.xu.stock.analyse.dao;

import java.util.List;

import com.xu.stock.analyse.model.StockHighest;
import com.xu.stock.data.model.StockDaily;

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
public interface IStockHighestDao {

    /**
     * 获取历史最高价
     * 
     * @param stockDaily
     * @param parameters
     * @return
     */
    public List<StockHighest> getHighests(StockDaily stockDaily, String parameters);


    /**
     * 保存历史最高价
     * 
     * @param trades
     * @return
     */
    public Integer saveHighest(List<StockHighest> history);

    /**
     * 更新分析过程
     * 
     * @param history
     */
    public Integer updateHighestAnalyse(StockHighest history);

    public StockHighest getLastHighest(String stockCode, String parameters);

}
