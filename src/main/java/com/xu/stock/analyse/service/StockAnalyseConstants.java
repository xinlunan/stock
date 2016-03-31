package com.xu.stock.analyse.service;

/**
 * @author lunan.xu 股票API常数
 */
public interface StockAnalyseConstants {

	public enum StrategyType {

		HIGHEST_PROBE, HIGHEST_LOW;

	}

	public interface HighestProbeArgs {
		public static final String D1_LAST_WAVE_CYCLE = "d1";
		public static final String D2_THIS_WAVE_CYCLE = "d2";
		public static final String F1_THIS_FALL_RATE = "f1";
		public static final String F2_WARN_RATE_HIGH = "f2";
		public static final String F3_WARN_RATE_LOW = "f3";

	}

	public interface TradeType {
		public static final String BUY = "BUY";
		public static final String SELL = "SELL";
	}

	public interface TradeNature {
		public static final String REAL = "BUY";
		public static final String VIRTUAL = "VIRTUAL";
	}

}
