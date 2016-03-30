package com.xu.stock.data.service.analyse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xu.stock.data.model.StockIndex;
import com.xu.stock.data.service.IStockAnalyst;
import com.xu.util.DateUtil;

/**
 * 最高点探测分析师
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

	/** 探测配置信息 */
	HighestProbeConfig config = new HighestProbeConfig();
	/** 可购买点 */
	List<StockIndex> buyPoints = new LinkedList<StockIndex>();

	public static HighestProbeAnalyst newInstance() {
		return new HighestProbeAnalyst();
	}

	public List<StockIndex> putStockIndexs(List<StockIndex> stockIndexs) {
		int thisFallRate = config.getF1_ThisFallRate();
		int warnRateHigh = config.getF2_WarnRateHigh();
		int warnRateLow = config.getF3_WarnRateLow();
		int lastWaveCycle = config.getD1_LastWaveCycle();
		int thisWaveCycle = config.getD2_ThisWaveCycle();

		List<Integer> highestPoints = countHighestPoints(stockIndexs, lastWaveCycle, thisWaveCycle);

		return countBuyPoints(stockIndexs, highestPoints, thisFallRate, warnRateHigh, warnRateLow);

	}

	/**
	 * 统计购买点
	 * 
	 * @param stockIndexs
	 * @param highestPonits
	 * @param thisFallRate
	 * @param warnRateHigh
	 * @param warnRateLow
	 * @return
	 */
	private List<StockIndex> countBuyPoints(List<StockIndex> stockIndexs, List<Integer> highestPonits,
			Integer thisFallRate, Integer warnRateHigh, int warnRateLow) {

		for (Integer point : highestPonits) {

			StockIndex highestStockIndex = stockIndexs.get(point);
			Integer highest = highestStockIndex.getClose();
			Integer warnHigh = highest - BigDecimal.valueOf(highest.longValue())
					.multiply(BigDecimal.valueOf(warnRateHigh)).divide(BigDecimal.valueOf(100)).intValue();
			Integer warnLow = highest - BigDecimal.valueOf(highest.longValue())
					.multiply(BigDecimal.valueOf(warnRateLow)).divide(BigDecimal.valueOf(100)).intValue();
			Integer lowest = Integer.MAX_VALUE;
			Integer rate = Integer.MIN_VALUE;
			for (int i = point; i < stockIndexs.size(); i++) {
				int current = stockIndexs.get(i).getClose();
				if (current < lowest) {
					lowest = stockIndexs.get(i).getClose();
					Integer closeGap = highest - lowest;
					rate = BigDecimal.valueOf(closeGap.longValue()).multiply(BigDecimal.valueOf(100))
							.divide(BigDecimal.valueOf(highest.longValue()), 2, BigDecimal.ROUND_HALF_UP).intValue();
				}
				if (rate > thisFallRate && current >= warnLow && current <= warnHigh) {

					log.info("buy point:" + stockIndexs.get(point).getStockCode() + "\t"
							+ DateUtil.date2String(stockIndexs.get(point).getDate()) + "\t"
							+ stockIndexs.get(point).getClose() + "\t"
							+ DateUtil.date2String(stockIndexs.get(i).getDate()) + "\t"
							+ stockIndexs.get(i).getClose());

					buyPoints.add(stockIndexs.get(i));
					break;
				}
				if (current > warnHigh && stockIndexs.get(i).getDate().after(stockIndexs.get(point).getDate())) {
					break;
				}

			}

		}

		return buyPoints;
	}

	/**
	 * 统计历史最高点
	 * 
	 * @param stockIndexs
	 * @param date1
	 * @param date2
	 * @param beginIndex
	 * @param endIndex
	 * @return
	 */
	private List<Integer> countHighestPoints(List<StockIndex> stockIndexs, int date1, int date2) {
		int beginIndex = date1;
		int endIndex = stockIndexs.size() - date2;

		List<Integer> highestPoints = new ArrayList<Integer>();
		for (int index = beginIndex; index < endIndex; index++) {
			if (isHighest(stockIndexs, index, date1, date2)) {
				highestPoints.add(index);
			}
		}
		return highestPoints;
	}

	/**
	 * 当前点是不是区间内最高点
	 * 
	 * @param stockIndexs
	 * @param index
	 * @param date1
	 * @param date2
	 * @return
	 */
	private boolean isHighest(List<StockIndex> stockIndexs, int index, int date1, int date2) {
		for (int i = index - date1; i < index + date2; i++) {
			if (stockIndexs.get(i).getClose() > stockIndexs.get(index).getClose()) {
				return false;
			}
		}
		return true;
	}

	public List<StockIndex> getBuyPoints() {
		return buyPoints;
	}

	private HighestProbeAnalyst() {
	}

}
