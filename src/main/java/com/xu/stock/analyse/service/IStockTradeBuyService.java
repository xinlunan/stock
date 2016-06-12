package com.xu.stock.analyse.service;

import java.util.List;

import com.xu.stock.data.model.StockDaily;

/**
 * 购买点分析
 * 
 * @version
 * 
 * <pre>
 * Author	Version		Date		Changes
 * lunan.xu 	1.0  		2016年4月28日 	Created
 * </pre>
 * 
 * @since 1.
 */
public interface IStockTradeBuyService {

    /**
     * 分析购买点
     * 
     * @param dailys
     * @param parameters
     */
    public void analyseStockTradeBuy(List<StockDaily> dailys);

    /**
     * 发送分析购买的结果邮件
     */
    public void sendStockAnalyseResultMail();

}
