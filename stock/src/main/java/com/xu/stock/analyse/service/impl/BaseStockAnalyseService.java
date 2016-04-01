package com.xu.stock.analyse.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xu.stock.analyse.dao.IStockAnalyseRecordDao;
import com.xu.stock.analyse.dao.IStockAnalyseStrategyDao;
import com.xu.stock.analyse.dao.IStockTradeDao;
import com.xu.stock.analyse.model.StockAnalyseStrategy;
import com.xu.stock.analyse.model.StockTrade;
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
	protected static Logger log = LoggerFactory.getLogger(BaseStockAnalyseService.class);

	@Resource
	protected IStockAnalyseStrategyDao stockAnalyseStrategyDao;
	@Resource
	protected IStockTradeDao stockTradeDao;
	@Resource
	protected IStockAnalyseRecordDao stockAnalyseRecordDao;

	protected StockAnalyseStrategy strategy;

	public void analyse(List<StockDaily> dailys) {

		List<StockAnalyseStrategy> strategys = getAnalyseStrategys();

		for (StockAnalyseStrategy analyseStrategy : strategys) {
			// 构建分析器
			setAnalyseStrategy(analyseStrategy);

			// 分析可购买点
			List<StockTrade> trades = doAnalyse(dailys);

			// 保存购买点
			stockTradeDao.saveStockTrades(trades);
		}

	}

	/**
	 * 执行分析
	 * 
	 * @param dailys
	 * @return
	 */
	public abstract List<StockTrade> doAnalyse(List<StockDaily> dailys);

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
