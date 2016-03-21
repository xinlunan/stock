package com.xu.stock.data.service.analyse;

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
	/** 上次增长幅度 */
	private Integer f1_LastRoseRate;
	/** 这次下跌幅度 */
	private Integer f2_ThisFallRate;
	/** 离最高价最佳相差幅度 */
	private Integer f3_ExpectCriticalRate;
	/** 最佳相差幅度缓冲范围 */
	private Integer f4_ExpectCriticalBufferRate;

	public HighestProbeConfig() {
		d1_LastWaveCycle = AppPropertyHandle.getInt("stock.analyse.highest.probe.config.d1_LastWaveCycle");
		d2_ThisWaveCycle = AppPropertyHandle.getInt("stock.analyse.highest.probe.config.d2_ThisWaveCycle");
		f1_LastRoseRate = AppPropertyHandle.getInt("stock.analyse.highest.probe.config.f1_LastRoseRate");
		f2_ThisFallRate = AppPropertyHandle.getInt("stock.analyse.highest.probe.config.f2_ThisFallRate");
		f3_ExpectCriticalRate = AppPropertyHandle.getInt("stock.analyse.highest.probe.config.f3_ExpectCriticalRate");
		f4_ExpectCriticalBufferRate = AppPropertyHandle
				.getInt("stock.analyse.highest.probe.config.f4_ExpectCriticalBufferRate");
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

}
