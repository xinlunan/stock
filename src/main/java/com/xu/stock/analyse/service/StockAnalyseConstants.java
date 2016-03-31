package com.xu.stock.analyse.service;

/**
 * @author lunan.xu 股票API常数
 */
public interface StockAnalyseConstants {

	public enum StrategyType {

		HIGHEST_PROBE, HIGHEST_LOW;

	}

	public interface HighestProbeArgs {
		public static final String D1_LASTWAVECYCLE = "d1";
		public static final String D2_THISWAVECYCLE = "d2";
		public static final String F1_THISFALLRATE = "f1";
		public static final String F2_WARNRATEHIGH = "f2";
		public static final String F3_WARNRATELOW = "f3";
		public static final String F4_EXPECTCRITICALBUFFERRATE = "f4";

	}

}
