package com.xu.stock.analyse.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.xu.stock.analyse.model.StockAnalyseStrategy;
import com.xu.stock.analyse.model.StockTrade;
import com.xu.stock.analyse.service.StockAnalyseConstants.HighestProbeBuyArgs;
import com.xu.stock.analyse.service.StockAnalyseConstants.StrategyType;
import com.xu.stock.analyse.service.StockAnalyseConstants.TradeNature;
import com.xu.stock.analyse.service.StockAnalyseConstants.TradeType;
import com.xu.stock.data.model.StockDaily;
import com.xu.util.DateUtil;

/**
 * 最高点探测分析
 * 
 * @version
 * 
 * 			<pre>
 * Author	Version		Date		Changes
 * lunan.xu 	1.0  		2016年3月19日 	Created
 *
 *          </pre>
 * 
 * @since 1.
 */
@Service("highestProbeBuyAnalyseService")
public class HighestProbeBuyAnalyst extends BaseStockAnalyseService {

	private Integer lastWaveCycle;
	private Integer thisWaveCycle;
	private Integer thisFallRate;
	private Integer warnRateHigh;
	private Integer warnRateLow;

	@Override
	public List<StockAnalyseStrategy> getAnalyseStrategys() {
		return stockAnalyseStrategyDao.getAnalyseStrategys(StrategyType.HIGHEST_PROBE_BUY);
	}

	@Override
	public void setAnalyseStrategy(StockAnalyseStrategy strategy) {
		super.setAnalyseStrategy(strategy);
		this.lastWaveCycle = strategy.getIntValue(HighestProbeBuyArgs.D1_LAST_WAVE_CYCLE);
		this.thisWaveCycle = strategy.getIntValue(HighestProbeBuyArgs.D2_THIS_WAVE_CYCLE);
		this.thisFallRate = strategy.getIntValue(HighestProbeBuyArgs.F1_THIS_FALL_RATE);
		this.warnRateHigh = strategy.getIntValue(HighestProbeBuyArgs.F2_WARN_RATE_HIGH);
		this.warnRateLow = strategy.getIntValue(HighestProbeBuyArgs.F3_WARN_RATE_LOW);
	}

	@Override
	public List<StockTrade> doAnalyse(List<StockDaily> dailys) {
		// 找出当前股票的历史最高点的日期
		List<Integer> highestPoints = scanHighestPoints(dailys);

		// 根据最高点找出试探突破点的日期
		List<StockDaily> buyDailyPoints = scanBuyPoints(dailys, highestPoints);

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

			trade.setSellDate(null);
			trade.setSellHour(null);
			trade.setSellMinute(null);
			trade.setSellTradePrice(BigDecimal.valueOf(0));
			trade.setSellHighPrice(BigDecimal.valueOf(0));
			trade.setSellClosePrice(BigDecimal.valueOf(0));

			trade.setProfit(BigDecimal.valueOf(0));
			trade.setProfitRate(BigDecimal.valueOf(0));

			trade.setTradeType(TradeType.BUY);
			trade.setTradeNature(TradeNature.VIRTUAL);
			trade.setStrategy(StrategyType.HIGHEST_PROBE_BUY.toString());
			trade.setVersion(strategy.getVersion());
			trade.setParameters(strategy.getParameters());
			log.info(trade.toString());

			trades.add(trade);
		}
		return trades;
	}

	/**
	 * 统计购买点
	 * 
	 * @param stockDailys
	 * @param highestPonits
	 * @param thisFallRate
	 * @param warnRateHigh
	 * @param warnRateLow
	 * @return
	 */
	private List<StockDaily> scanBuyPoints(List<StockDaily> stockDailys, List<Integer> highestPonits) {
		List<StockDaily> buyPoints = new LinkedList<StockDaily>();

		for (Integer point : highestPonits) {

			StockDaily highestStockDaily = stockDailys.get(point);
			Integer highest = highestStockDaily.getClose();
			Integer warnHigh = highest - BigDecimal.valueOf(highest.longValue())
					.multiply(BigDecimal.valueOf(warnRateHigh)).divide(BigDecimal.valueOf(100)).intValue();
			Integer warnLow = highest - BigDecimal.valueOf(highest.longValue())
					.multiply(BigDecimal.valueOf(warnRateLow)).divide(BigDecimal.valueOf(100)).intValue();
			Integer lowest = Integer.MAX_VALUE;
			Integer rate = Integer.MIN_VALUE;
			for (int i = point; i < stockDailys.size(); i++) {
				int current = stockDailys.get(i).getClose();
				int currentHighest = stockDailys.get(i).getHigh();
				if (current < lowest) {
					lowest = stockDailys.get(i).getClose();
					Integer closeGap = highest - lowest;
					rate = BigDecimal.valueOf(closeGap.longValue()).multiply(BigDecimal.valueOf(100))
							.divide(BigDecimal.valueOf(highest.longValue()), 2, BigDecimal.ROUND_HALF_UP).intValue();
				}
				if (rate > thisFallRate && current >= warnLow && current <= warnHigh && currentHighest < highest) {

					log.info("buy point:" + stockDailys.get(point).getStockCode() + "\t"
							+ DateUtil.date2String(stockDailys.get(point).getDate()) + "\t"
							+ stockDailys.get(point).getClose() + "\t"
							+ DateUtil.date2String(stockDailys.get(i).getDate()) + "\t"
							+ stockDailys.get(i).getClose());

					buyPoints.add(stockDailys.get(i));
					break;
				}
				if (current > warnHigh && stockDailys.get(i).getDate().after(stockDailys.get(point).getDate())) {
					break;
				}

			}

		}

		return buyPoints;
	}

	/**
	 * 统计历史最高点
	 * 
	 * @param stockDailys
	 * @param date1
	 * @param date2
	 * @param beginIndex
	 * @param endIndex
	 * @return
	 */
	private List<Integer> scanHighestPoints(List<StockDaily> stockDailys) {
		int beginIndex = lastWaveCycle;
		int endIndex = stockDailys.size() - thisWaveCycle;

		List<Integer> highestPoints = new ArrayList<Integer>();
		for (int index = beginIndex; index < endIndex; index++) {
			if (isHighest(stockDailys, index, lastWaveCycle, thisWaveCycle)) {
				highestPoints.add(index);
			}
		}
		return highestPoints;
	}

	/**
	 * 当前点是不是区间内最高点
	 * 
	 * @param stockDailys
	 * @param index
	 * @param date1
	 * @param date2
	 * @return
	 */
	private boolean isHighest(List<StockDaily> stockDailys, int index, int date1, int date2) {
		for (int i = index - date1; i < index + date2; i++) {
			if (i < index) {
				if (stockDailys.get(i).getClose() > stockDailys.get(index).getClose()) {
					return false;
				}
			}
			if (i > index) {
				if (stockDailys.get(i).getClose() >= stockDailys.get(index).getClose()) {
					return false;
				}
			}
		}
		return true;
	}

}
