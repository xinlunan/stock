package com.xu.stock.analyse.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.xu.stock.analyse.model.StockAnalyseStrategy;
import com.xu.stock.analyse.model.StockTrade;
import com.xu.stock.analyse.service.StockAnalyseConstants.HighLowBuyArgs;
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
@Service("highLowBuyAnalyseService")
public class HighLowBuyAnalyseService extends BaseStockAnalyseService {
	private Integer riseDay;
	private Integer riseRate;

	@Override
	public void setAnalyseStrategy(StockAnalyseStrategy strategy) {
		super.setAnalyseStrategy(strategy);
		this.riseDay = strategy.getIntValue(HighLowBuyArgs.RISE_DAY);
		this.riseRate = strategy.getIntValue(HighLowBuyArgs.RISE_RATE);
	}

	@Override
	public List<StockTrade> doAnalyse(List<StockDaily> dailys) {

		// 找出当前股票的连接增长的日期
		List<StockDaily> buyDailyPoints = scanRisePoints(dailys);

		// 得到购入时间点
		return buildStockTrades(buyDailyPoints);
	}

	/**
	 * 分析购买的时间点
	 * 
	 * @param buyPoints
	 * @return
	 */
	private List<StockTrade> buildStockTrades(List<StockDaily> buyPoints) {
		List<StockTrade> trades = new ArrayList<StockTrade>();
		for (StockDaily stockDaily : buyPoints) {
			StockTrade trade = new StockTrade();

			trade.setStockId(stockDaily.getStockId());
			trade.setStockCode(stockDaily.getStockCode());
			trade.setStockName(stockDaily.getStockName());

			trade.setBuyDate(stockDaily.getDate());
			trade.setBuyHour(15);
			trade.setBuyMinute(0);
			trade.setBuyTradePrice(BigDecimal.valueOf(stockDaily.getClose()));
			trade.setBuyHighPrice(BigDecimal.valueOf(stockDaily.getHigh()));
			trade.setBuyClosePrice(BigDecimal.valueOf(stockDaily.getClose()));

			trade.setSellDate(stockDaily.getDate());
			trade.setSellHour(15);
			trade.setSellMinute(0);
			trade.setSellTradePrice(BigDecimal.valueOf(0));
			trade.setSellHighPrice(BigDecimal.valueOf(0));
			trade.setSellClosePrice(BigDecimal.valueOf(0));

			trade.setProfit(BigDecimal.valueOf(0));
			trade.setProfitRate(BigDecimal.valueOf(0));

			trade.setTradeType(TradeType.BUY);
			trade.setTradeNature(TradeNature.VIRTUAL);
			trade.setStrategy(StrategyType.HIGH_LOW_BUY.toString());
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
	private List<StockDaily> scanRisePoints(List<StockDaily> dailys) {
		int endIndex = dailys.size() - riseDay;

		List<StockDaily> risePoints = new ArrayList<StockDaily>();
		for (int index = 0; index < endIndex; index++) {
			if (isSerialRise(dailys, index)) {
				risePoints.add(dailys.get(index + riseDay - 1));
			}
		}
		return risePoints;
	}

	/**
	 * 是否连接上涨
	 * 
	 * @param dailys
	 * @param index
	 * @param riseDay2
	 * @return
	 */
	private boolean isSerialRise(List<StockDaily> dailys, int index) {
		for (int i = index; i < index + riseDay; i++) {
			if (dailys.get(i).getClose() > dailys.get(i + 1).getClose()) {
				return false;
			}
		}
		Integer fromPrice = dailys.get(index).getClose();
		Integer endPrice = dailys.get(index + riseDay - 1).getClose();
		Integer rise = endPrice - fromPrice;
		Float thisRiseRate = BigDecimal.valueOf(rise).divide(BigDecimal.valueOf(fromPrice), 4, BigDecimal.ROUND_HALF_UP)
				.floatValue() * 100;
		if (thisRiseRate > riseRate) {
			return true;
		}
		return false;
	}

	@Override
	public List<StockAnalyseStrategy> getAnalyseStrategys() {
		return stockAnalyseStrategyDao.getAnalyseStrategys(StrategyType.HIGH_LOW_BUY);
	}
}
