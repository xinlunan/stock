package com.xu.stock.analyse.service;

import java.io.Serializable;

import com.winit.framework.config.AppPropertyHandle;

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
public class HighestProbeConfig implements Serializable {
	private static final long serialVersionUID = -7253656148791370045L;

	/** 上次波动周期 */
	private Integer d1_LastWaveCycle;
	/** 本次波动周期 */
	private Integer d2_ThisWaveCycle;
	/** 这次下跌幅度 */
	private Integer f1_ThisFallRate;
	/** 上次增长幅度 */
	private Integer f2_WarnRateHigh;
	/** 离最高价最佳相差幅度 */
	private Integer f3_WarnRateLow;

	public HighestProbeConfig() {
		d1_LastWaveCycle = AppPropertyHandle.getInt("stock.analyse.highest.probe.config.d1_LastWaveCycle");
		d2_ThisWaveCycle = AppPropertyHandle.getInt("stock.analyse.highest.probe.config.d2_ThisWaveCycle");
		f1_ThisFallRate = AppPropertyHandle.getInt("stock.analyse.highest.probe.config.f1_ThisFallRate");
		f2_WarnRateHigh = AppPropertyHandle.getInt("stock.analyse.highest.probe.config.f2_WarnRateHigh");
		f3_WarnRateLow = AppPropertyHandle.getInt("stock.analyse.highest.probe.config.f3_WarnRateLow");
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

	public Integer getF1_ThisFallRate() {
		return f1_ThisFallRate;
	}

	public void setF1_ThisFallRate(Integer f1_ThisFallRate) {
		this.f1_ThisFallRate = f1_ThisFallRate;
	}

	public Integer getF2_WarnRateHigh() {
		return f2_WarnRateHigh;
	}

	public void setF2_WarnRateHigh(Integer f2_WarnRateHigh) {
		this.f2_WarnRateHigh = f2_WarnRateHigh;
	}

	public Integer getF3_WarnRateLow() {
		return f3_WarnRateLow;
	}

	public void setF3_WarnRateLow(Integer f3_WarnRateLow) {
		this.f3_WarnRateLow = f3_WarnRateLow;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
