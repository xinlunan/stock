package com.xu.stock.analyse.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xu.stock.analyse.dao.IStockAnalyseRecordDao;
import com.xu.stock.analyse.dao.IStockAnalyseStrategyDao;
import com.xu.stock.analyse.dao.IStockTradeDao;
import com.xu.stock.analyse.model.StockAnalyseStrategy;
import com.xu.stock.analyse.service.IStockAnalyseService;
import com.xu.stock.data.model.StockDaily;
import com.xu.stock.data.service.IStockService;

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

    public static final BigDecimal     BD_100 = BigDecimal.valueOf(100);
    public static final BigDecimal     BD_10  = BigDecimal.valueOf(10);
    public static final BigDecimal     BD_1   = BigDecimal.valueOf(1);
	protected StockAnalyseStrategy strategy;

    @Resource
    private IStockService              stockService;
	@Resource
	protected IStockAnalyseStrategyDao stockAnalyseStrategyDao;
	@Resource
    protected IStockTradeDao           stockTradeDao;
	@Resource
	protected IStockAnalyseRecordDao stockAnalyseRecordDao;


	@Override
    public void analyse(List<StockDaily> dailys) {

        stockService.getStockForUpdate(dailys.get(0).getStockCode());
		List<StockAnalyseStrategy> strategys = getAnalyseStrategys();

		for (StockAnalyseStrategy analyseStrategy : strategys) {
			// 构建分析器
			setAnalyseStrategy(analyseStrategy);

            doAnalyse(dailys);
		}

	}

	/**
	 * 执行分析
	 * 
	 * @param dailys
	 * @return
	 */
    public abstract void doAnalyse(List<StockDaily> dailys);

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
