package com.xu.stock.analyse.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.xu.stock.analyse.model.StockAnalyseStrategy;
import com.xu.stock.analyse.model.StockSimulateTrade;
import com.xu.stock.analyse.service.StockAnalyseConstants.SerialRiseBuyArgs;
import com.xu.stock.analyse.service.StockAnalyseConstants.StrategyType;
import com.xu.stock.analyse.service.StockAnalyseConstants.TradeNature;
import com.xu.stock.analyse.service.StockAnalyseConstants.TradeType;
import com.xu.stock.data.model.StockDaily;

/**
 * 最高最低价差价分析
 * 
 * @version Revision History
 * 
 *          <pre>
 * Author     Version       Date        Changes
 * lunan.xu    1.0           2015-5-31     Created
 * 
 *          </pre>
 * 
 * @since 1.
 */
@Service("serialRiseBuyAnalyseService")
public class SerialRiseBuyAnalyseService extends BaseStockAnalyseService {
	protected Integer riseDay;
    protected BigDecimal riseRate;

	@Override
	public void setAnalyseStrategy(StockAnalyseStrategy strategy) {
		super.setAnalyseStrategy(strategy);
		this.riseDay = strategy.getIntValue(SerialRiseBuyArgs.RISE_DAY);
		this.riseRate = BigDecimal.valueOf(strategy.getDoubleValue(SerialRiseBuyArgs.RISE_RATE));
	}

	@Override
	public List<StockSimulateTrade> doAnalyse(List<StockDaily> dailys) {
		log.info("analyse stock code:" + dailys.get(0).getStockCode());

		// 找出当前股票的连接增长的日期
		List<StockDaily> buyDailyPoints = scanRisePoints(dailys);

		// 得到购入时间点
		return buildStockSimulateTrades(buyDailyPoints);
	}

	/**
	 * 创建购买的交易
	 * 
	 * @param buyPoints
	 * @return
	 */
	protected List<StockSimulateTrade> buildStockSimulateTrades(List<StockDaily> buyPoints) {
		List<StockSimulateTrade> trades = new ArrayList<StockSimulateTrade>();
		for (StockDaily stockDaily : buyPoints) {
			StockSimulateTrade trade = new StockSimulateTrade();

			trade.setStockCode(stockDaily.getStockCode());
			trade.setStockName(stockDaily.getStockName());

			trade.setBuyDate(stockDaily.getDate());
			trade.setBuyHour(15);
			trade.setBuyMinute(0);
			trade.setBuyTradePrice(stockDaily.getClose());
			trade.setBuyHighPrice(stockDaily.getHigh());
			trade.setBuyClosePrice(stockDaily.getClose());

			trade.setSellDate(stockDaily.getDate());
			trade.setSellHour(15);
			trade.setSellMinute(0);
			trade.setSellTradePrice(BigDecimal.valueOf(0));
			trade.setSellHighPrice(BigDecimal.valueOf(0));
			trade.setSellClosePrice(BigDecimal.valueOf(0));

			trade.setProfit(BigDecimal.valueOf(0));
			trade.setProfitRate(BigDecimal.valueOf(0));
			trade.setHighProfitRate(BigDecimal.valueOf(0));
			trade.setCloseProfitRate(BigDecimal.valueOf(0));

			trade.setTradeType(TradeType.BUY);
			trade.setTradeNature(TradeNature.VIRTUAL);
			trade.setStrategy(StrategyType.SERIAL_RISE_BUY.toString());
			trade.setVersion(strategy.getVersion());
			trade.setParameters(strategy.getParameters());
			log.info(trade.toString());

			trades.add(trade);
		}
		return trades;
	}

	/**
	 * 查找连接增长点
	 * 
	 * @param dailys
	 * @return
	 */
	protected List<StockDaily> scanRisePoints(List<StockDaily> dailys) {
		int endIndex = dailys.size() - riseDay;

		List<StockDaily> risePoints = new ArrayList<StockDaily>();
		for (int index = 0; index < endIndex; index++) {
			if (isBuyDaily(dailys, index)) {
				risePoints.add(dailys.get(index + riseDay));
			}
		}
		return risePoints;
	}

	/**
	 * 是否是购买点
	 * 
	 * @param dailys
	 * @param index
	 * @param riseDay2
	 * @return
	 */
	protected boolean isBuyDaily(List<StockDaily> dailys, int index) {
		// 是否连接上涨
        if (!StockAnalyseUtil.isSerialRise(dailys, index, riseDay)) {
			return false;
		}

		// 增长是否达到预期
        if (!StockAnalyseUtil.isRistExpected(dailys, index, riseDay, riseRate)) {
			return false;
		}

		// 是否涨停
        if (StockAnalyseUtil.isLastDayRiseLimit(dailys, index)) {
			return false;
		}

		return true;
	}





	@Override
	public List<StockAnalyseStrategy> getAnalyseStrategys() {
		return stockAnalyseStrategyDao.getAnalyseStrategys(StrategyType.SERIAL_RISE_BUY);
	}
}
