package com.xu.stock.analyse.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.xu.stock.analyse.model.StockAnalyseStrategy;
import com.xu.stock.analyse.model.StockTrade;
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
	private BigDecimal riseRate;

	@Override
	public void setAnalyseStrategy(StockAnalyseStrategy strategy) {
		super.setAnalyseStrategy(strategy);
		this.riseDay = strategy.getIntValue(SerialRiseBuyArgs.RISE_DAY);
		this.riseRate = BigDecimal.valueOf(strategy.getDoubleValue(SerialRiseBuyArgs.RISE_RATE));
	}

	@Override
	public List<StockTrade> doAnalyse(List<StockDaily> dailys) {
		log.info("analyse stock code:" + dailys.get(0).getStockCode());

		// 找出当前股票的连接增长的日期
		List<StockDaily> buyDailyPoints = scanRisePoints(dailys);

		// 得到购入时间点
		return buildStockTrades(buyDailyPoints);
	}

	/**
	 * 创建购买的交易
	 * 
	 * @param buyPoints
	 * @return
	 */
	protected List<StockTrade> buildStockTrades(List<StockDaily> buyPoints) {
		List<StockTrade> trades = new ArrayList<StockTrade>();
		for (StockDaily stockDaily : buyPoints) {
			StockTrade trade = new StockTrade();

			trade.setStockId(stockDaily.getStockId());
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
		if (!isSerialRise(dailys, index)) {
			return false;
		}

		// 增长是否达到预期
		if (!isRistExpected(dailys, index)) {
			return false;
		}

		// 是否涨停
		if (isRistLimit(dailys, index)) {
			return false;
		}

		return true;
	}

	/**
	 * 是否涨停
	 * 
	 * @param dailys
	 * @param index
	 * @return
	 */
	protected boolean isRistLimit(List<StockDaily> dailys, int index) {
		BigDecimal lastPrice = dailys.get(index + riseDay - 1).getClose();
		StockDaily thisDaily = dailys.get(index + riseDay);
		BigDecimal lastRise = thisDaily.getClose().subtract(lastPrice);
		BigDecimal lastRiseRate = lastRise.multiply(BD_100).divide(lastPrice, 2, BigDecimal.ROUND_HALF_UP);

		if (lastRiseRate.compareTo(BigDecimal.valueOf(Double.valueOf(9.8))) > 0
				&& thisDaily.getHigh().compareTo(thisDaily.getClose()) == 0) {
			return true;
		}

		if (lastRiseRate.compareTo(BigDecimal.valueOf(Double.valueOf(4.9))) > 0
				&& lastRiseRate.compareTo(BigDecimal.valueOf(Double.valueOf(5.1))) < 0
				&& thisDaily.getHigh().compareTo(thisDaily.getClose()) == 0) {
			return true;
		}

		return false;
	}

	/**
	 * 增长是否达到预期
	 * 
	 * @param dailys
	 * @param index
	 * @return
	 */
	protected boolean isRistExpected(List<StockDaily> dailys, int index) {
		// 增长未达到预期
		BigDecimal fromPrice = dailys.get(index).getClose();
		BigDecimal allRise = dailys.get(index + riseDay).getClose().subtract(fromPrice);
		BigDecimal allRiseRate = allRise.multiply(BD_100).divide(fromPrice, 2, BigDecimal.ROUND_HALF_UP);
		return allRiseRate.compareTo(riseRate) >= 0;
	}

	/**
	 * 是否连接上涨
	 * 
	 * @param dailys
	 * @param index
	 * @return
	 */
	protected boolean isSerialRise(List<StockDaily> dailys, int index) {
		for (int i = index; i < index + riseDay; i++) {
			if (dailys.get(i).getClose().compareTo(dailys.get(i + 1).getClose()) == 1) {
				return false;
			}
		}
		return true;
	}

	@Override
	public List<StockAnalyseStrategy> getAnalyseStrategys() {
		return stockAnalyseStrategyDao.getAnalyseStrategys(StrategyType.SERIAL_RISE_BUY);
	}
}
