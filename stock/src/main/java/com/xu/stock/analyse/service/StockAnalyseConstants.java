package com.xu.stock.analyse.service;

/**
 * 股票分析常数
 * 
 * @version
 * 
 * 			<pre>
 * Author	Version		Date		Changes
 * lunan.xu 	1.0  		2016年3月31日 	Created
 *
 *          </pre>
 * 
 * @since 1.
 */
public interface StockAnalyseConstants {

	public enum StrategyType {

		HIGHEST_PROBE_BUY, HIGHEST_PROBE_SELL, HIGH_LOW_BUY, HIGH_LOW_SELL;

	}

	public interface HighestProbeBuyArgs {
		public static final String D1_LAST_WAVE_CYCLE = "d1";
		public static final String D2_THIS_WAVE_CYCLE = "d2";
		public static final String F1_THIS_FALL_RATE = "f1";
		public static final String F2_WARN_RATE_HIGH = "f2";
		public static final String F3_WARN_RATE_LOW = "f3";

	}

	public interface HighestProbeSellArgs {
		public static final String EXPECT_RATE = "expect_rate";
	}

	public interface HighLowBuyArgs {
		public static final String RISE_DAY = "rise_day";
		public static final String RISE_RATE = "rise_rate";
	}

	public interface HighLowSellArgs {
		public static final String EXPECT_RATE = "expect_rate";
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
