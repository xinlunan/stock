package com.xu.stock.data.service.analyse;

import java.io.Serializable;

import com.xu.stock.data.model.StockIndex;

/**
 * 最高点探测
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
public class HighestProbeValue implements Serializable {
	private static final long serialVersionUID = -7253656148791370045L;
	/** 样本初始价 */
	private StockIndex p1_FirstPrice;
	/** 上次最低价 */
	private StockIndex p2_LastLowest;
	/** 上次最高价 */
	private StockIndex p3_LastHighest;
	/** 这次最低价 */
	private StockIndex p4_ThisLowest;
	/** 当前价 */
	private StockIndex p5_CurrentPrice;
	/** 预期临界最低价 */
	private StockIndex p6_ExpectCriticalLow;
	/** 预期临界最高价 */
	private StockIndex p7_ExpectCriticalHigh;
	/** 上次波动周期 */
	private Integer d1_LastWaveCycle = 0;
	/** 本次波动周期 */
	private Integer d2_ThisWaveCycle = 0;
	/** 上次增长幅度 */
	private Integer f1_LastRoseRate = 0;
	/** 这次下跌幅度 */
	private Integer f2_ThisFallRate = 0;
	/** 离最高价最佳相差幅度 */
	private Integer f3_ExpectCriticalRate = 0;
	/** 最佳相差幅度缓冲范围 */
	private Integer f4_ExpectCriticalBufferRate = 0;

	public StockIndex getP1_FirstPrice() {
		return p1_FirstPrice;
	}

	public void setP1_FirstPrice(StockIndex p1_FirstPrice) {
		this.p1_FirstPrice = p1_FirstPrice;
	}

	public StockIndex getP2_LastLowest() {
		return p2_LastLowest;
	}

	public void setP2_LastLowest(StockIndex p2_LastLowest) {
		this.p2_LastLowest = p2_LastLowest;
	}

	public StockIndex getP3_LastHighest() {
		return p3_LastHighest;
	}

	public void setP3_LastHighest(StockIndex p3_LastHighest) {
		this.p3_LastHighest = p3_LastHighest;
	}

	public StockIndex getP4_ThisLowest() {
		return p4_ThisLowest;
	}

	public void setP4_ThisLowest(StockIndex p4_ThisLowest) {
		this.p4_ThisLowest = p4_ThisLowest;
	}

	public StockIndex getP5_CurrentPrice() {
		return p5_CurrentPrice;
	}

	public void setP5_CurrentPrice(StockIndex p5_CurrentPrice) {
		this.p5_CurrentPrice = p5_CurrentPrice;
	}

	public StockIndex getP6_ExpectCriticalLow() {
		return p6_ExpectCriticalLow;
	}

	public void setP6_ExpectCriticalLow(StockIndex p6_ExpectCriticalLow) {
		this.p6_ExpectCriticalLow = p6_ExpectCriticalLow;
	}

	public StockIndex getP7_ExpectCriticalHigh() {
		return p7_ExpectCriticalHigh;
	}

	public void setP7_ExpectCriticalHigh(StockIndex p7_ExpectCriticalHigh) {
		this.p7_ExpectCriticalHigh = p7_ExpectCriticalHigh;
	}

	public Integer getD1_LastWaveCycle() {
		return d1_LastWaveCycle;
	}

	public void setD1_LastWaveCycle(Integer d1_LastWaveCycle) {
		this.d1_LastWaveCycle = d1_LastWaveCycle;
	}

	public Integer getD2_ThisWaveCycle() {
		return d2_ThisWaveCycle;
	}

	public void setD2_ThisWaveCycle(Integer d2_ThisWaveCycle) {
		this.d2_ThisWaveCycle = d2_ThisWaveCycle;
	}

	public Integer getF1_LastRoseRate() {
		return f1_LastRoseRate;
	}

	public void setF1_LastRoseRate(Integer f1_LastRoseRate) {
		this.f1_LastRoseRate = f1_LastRoseRate;
	}

	public Integer getF2_ThisFallRate() {
		return f2_ThisFallRate;
	}

	public void setF2_ThisFallRate(Integer f2_ThisFallRate) {
		this.f2_ThisFallRate = f2_ThisFallRate;
	}

	public Integer getF3_ExpectCriticalRate() {
		return f3_ExpectCriticalRate;
	}

	public void setF3_ExpectCriticalRate(Integer f3_ExpectCriticalRate) {
		this.f3_ExpectCriticalRate = f3_ExpectCriticalRate;
	}

	public Integer getF4_ExpectCriticalBufferRate() {
		return f4_ExpectCriticalBufferRate;
	}

	public void setF4_ExpectCriticalBufferRate(Integer f4_ExpectCriticalBufferRate) {
		this.f4_ExpectCriticalBufferRate = f4_ExpectCriticalBufferRate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
