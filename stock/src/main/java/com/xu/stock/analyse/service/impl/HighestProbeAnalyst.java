package com.xu.stock.analyse.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xu.stock.analyse.model.StockAnalyseStrategy;
import com.xu.stock.analyse.service.IStockAnalyst;
import com.xu.stock.analyse.service.StockAnalyseConstants.HighestProbeArgs;
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
public class HighestProbeAnalyst implements IStockAnalyst {
	protected static Logger log = LoggerFactory.getLogger(HighestProbeAnalyst.class);

	/** 可购买点 */
	private List<StockDaily> buyPoints = new LinkedList<StockDaily>();

	private StockAnalyseStrategy strategy;

	public static HighestProbeAnalyst newInstance(StockAnalyseStrategy strategy) {
		return new HighestProbeAnalyst(strategy);
	}

	public List<StockDaily> putStockDailys(List<StockDaily> stockDailys) {

		int lastWaveCycle = strategy.getIntValue(HighestProbeArgs.D1_LASTWAVECYCLE);
		int thisWaveCycle = strategy.getIntValue(HighestProbeArgs.D2_THISWAVECYCLE);
		int thisFallRate = strategy.getIntValue(HighestProbeArgs.F1_THISFALLRATE);
		int warnRateHigh = strategy.getIntValue(HighestProbeArgs.F2_WARNRATEHIGH);
		int warnRateLow = strategy.getIntValue(HighestProbeArgs.F3_WARNRATELOW);

		List<Integer> highestPoints = countHighestPoints(stockDailys, lastWaveCycle, thisWaveCycle);

		return countBuyPoints(stockDailys, highestPoints, thisFallRate, warnRateHigh, warnRateLow);

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
	private List<StockDaily> countBuyPoints(List<StockDaily> stockDailys, List<Integer> highestPonits,
			Integer thisFallRate, Integer warnRateHigh, int warnRateLow) {

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
				if (current < lowest) {
					lowest = stockDailys.get(i).getClose();
					Integer closeGap = highest - lowest;
					rate = BigDecimal.valueOf(closeGap.longValue()).multiply(BigDecimal.valueOf(100))
							.divide(BigDecimal.valueOf(highest.longValue()), 2, BigDecimal.ROUND_HALF_UP).intValue();
				}
				if (rate > thisFallRate && current >= warnLow && current <= warnHigh) {

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
	private List<Integer> countHighestPoints(List<StockDaily> stockDailys, int date1, int date2) {
		int beginIndex = date1;
		int endIndex = stockDailys.size() - date2;

		List<Integer> highestPoints = new ArrayList<Integer>();
		for (int index = beginIndex; index < endIndex; index++) {
			if (isHighest(stockDailys, index, date1, date2)) {
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
			if (stockDailys.get(i).getClose() > stockDailys.get(index).getClose()) {
				return false;
			}
		}
		return true;
	}

	public List<StockDaily> getBuyPoints() {
		return buyPoints;
	}

	private HighestProbeAnalyst(StockAnalyseStrategy strategy) {
		this.strategy = strategy;
	}

}
