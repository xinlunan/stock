package com.xu.stock.analyse.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xu.stock.analyse.dao.IStockAnalyseRecordDao;
import com.xu.stock.analyse.dao.IStockAnalyseStrategyDao;
import com.xu.stock.analyse.dao.IStockSimulateTradeDao;
import com.xu.stock.analyse.model.StockAnalyseStrategy;
import com.xu.stock.analyse.model.StockBuyTrade;
import com.xu.stock.analyse.service.IStockAnalyseService;
import com.xu.stock.data.model.StockDaily;

/**
 * 股票分析基类
 * 
 * @version
 * 
 * 			<pre>
 * Author	Version		Date		Changes
 * lunan.xu 	1.0  		2016年3月31日 	Created
 *
 *          </pre>
 * 
 * @since 1.
 */
@SuppressWarnings("restriction")
public abstract class BaseStockAnalyseService implements IStockAnalyseService {
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	public static final BigDecimal BD_100 = BigDecimal.valueOf(100);
	protected StockAnalyseStrategy strategy;

	@Resource
	protected IStockAnalyseStrategyDao stockAnalyseStrategyDao;
	@Resource
	protected IStockSimulateTradeDao stockSimulateTradeDao;
	@Resource
	protected IStockAnalyseRecordDao stockAnalyseRecordDao;


	public void analyse(List<StockDaily> dailys) {

		List<StockAnalyseStrategy> strategys = getAnalyseStrategys();

		for (StockAnalyseStrategy analyseStrategy : strategys) {
			// 构建分析器
			setAnalyseStrategy(analyseStrategy);

			// 分析可购买点
			List<StockBuyTrade> trades = doAnalyse(dailys);

			// 保存购买点
			stockSimulateTradeDao.saveStockSimulateTrades(trades);
		}

	}

	/**
	 * 执行分析
	 * 
	 * @param dailys
	 * @return
	 */
	public abstract List<StockBuyTrade> doAnalyse(List<StockDaily> dailys);

	/**
	 * 设置分析策略版本
	 * 
	 * @param strategy
	 */
	public void setAnalyseStrategy(StockAnalyseStrategy strategy) {
		this.strategy = strategy;
	}

	/**
	 * 获取所有的策略版本
	 * 
	 * @return
	 */
	public abstract List<StockAnalyseStrategy> getAnalyseStrategys();

}
