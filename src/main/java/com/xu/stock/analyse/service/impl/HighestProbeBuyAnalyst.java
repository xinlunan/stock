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
	private BigDecimal thisFallRate;
	private BigDecimal warnRateHigh;
	private BigDecimal warnRateLow;

	@Override
	public List<StockAnalyseStrategy> getAnalyseStrategys() {
		return stockAnalyseStrategyDao.getAnalyseStrategys(StrategyType.HIGHEST_PROBE_BUY);
	}

	@Override
	public void setAnalyseStrategy(StockAnalyseStrategy strategy) {
		super.setAnalyseStrategy(strategy);
		this.lastWaveCycle = strategy.getIntValue(HighestProbeBuyArgs.D1_LAST_WAVE_CYCLE);
		this.thisWaveCycle = strategy.getIntValue(HighestProbeBuyArgs.D2_THIS_WAVE_CYCLE);
		this.thisFallRate = BigDecimal.valueOf(strategy.getDoubleValue(HighestProbeBuyArgs.F1_THIS_FALL_RATE));
		this.warnRateHigh = BigDecimal.valueOf(strategy.getDoubleValue(HighestProbeBuyArgs.F2_WARN_RATE_HIGH));
		this.warnRateLow = BigDecimal.valueOf(strategy.getDoubleValue(HighestProbeBuyArgs.F3_WARN_RATE_LOW));
	}

	@Override
	public List<StockTrade> doAnalyse(List<StockDaily> dailys) {
		log.info("analyse stock code:" + dailys.get(0).getStockCode());

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
			trade.setBuyTradePrice(stockDaily.getClose());
			trade.setBuyHighPrice(stockDaily.getHigh());
			trade.setBuyClosePrice(stockDaily.getClose());

			trade.setSellDate(null);
			trade.setSellHour(null);
			trade.setSellMinute(null);
			trade.setSellTradePrice(BigDecimal.valueOf(0));
			trade.setSellHighPrice(BigDecimal.valueOf(0));
			trade.setSellClosePrice(BigDecimal.valueOf(0));

			trade.setProfit(BigDecimal.valueOf(0));
			trade.setProfitRate(BigDecimal.valueOf(0));
			trade.setHighProfitRate(BigDecimal.valueOf(0));
			trade.setCloseProfitRate(BigDecimal.valueOf(0));

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
			BigDecimal highest = highestStockDaily.getClose();
			BigDecimal warnHigh = highest.subtract(highest.multiply(warnRateHigh).divide(BD_100));
			BigDecimal warnLow = highest.subtract(highest.multiply(warnRateLow).divide(BD_100));
			BigDecimal lowest = BigDecimal.valueOf(Integer.MAX_VALUE);
			BigDecimal rate = BigDecimal.valueOf(Integer.MIN_VALUE);
			for (int i = point; i < stockDailys.size(); i++) {
				BigDecimal current = stockDailys.get(i).getClose();
				BigDecimal currentHighest = stockDailys.get(i).getHigh();
				if (current.compareTo(lowest) == -1) {
					lowest = stockDailys.get(i).getClose();
					BigDecimal closeGap = highest.subtract(lowest);
					rate = closeGap.multiply(BD_100).divide(highest, 2, BigDecimal.ROUND_HALF_UP);
				}
				if (rate.compareTo(thisFallRate) == 1 && current.compareTo(warnLow) >= 0 && current.compareTo(warnHigh) <= 0 && currentHighest.compareTo(highest) <= 0) {
					log.info("buy point:" + stockDailys.get(point).getStockCode() + "\t"
							+ DateUtil.date2String(stockDailys.get(point).getDate()) + "\t"
							+ stockDailys.get(point).getClose() + "\t"
							+ DateUtil.date2String(stockDailys.get(i).getDate()) + "\t"
							+ stockDailys.get(i).getClose());

					buyPoints.add(stockDailys.get(i));
					break;
				}
				if (current.compareTo(warnHigh) == 1 && stockDailys.get(i).getDate().after(stockDailys.get(point).getDate())) {
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
            if (StockAnalyseUtil.isHighest(stockDailys, index, lastWaveCycle, thisWaveCycle)) {
				// 是否涨停
                if (!StockAnalyseUtil.isLastDayRiseLimit(stockDailys, index)) {
					highestPoints.add(index);
				}

			}
		}
		return highestPoints;
	}




}
