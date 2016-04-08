package com.xu.stock.analyse.service;

/**
 * 股票分析常数
 * 
 * @version
 * 
 * <pre>
 * Author	Version		Date		Changes
 * lunan.xu 	1.0  		2016年3月31日 	Created
 * </pre>
 * 
 * @since 1.
 */
public interface StockAnalyseConstants {

    public enum StrategyType {
        HIGHEST_PROBE_BUY, HIGHEST_PROBE_SELL, SERIAL_RISE_BUY, SERIAL_RISE_SELL, GENERAL_RISE_BUY;
    }

    public interface HighestProbeBuyArgs {
        public static final String D1_LAST_WAVE_CYCLE = "d1";
        public static final String D2_THIS_WAVE_CYCLE = "d2";
        public static final String F1_THIS_FALL_RATE  = "f1";
        public static final String F2_BUY_RATE_HIGH   = "f2";
        public static final String F3_BUY_RATE_LOW    = "f3";
        public static final String F4_WARN_RATE_LOW   = "f4";

    }

    public interface HighestProbeSellArgs {

        public static final String EXPECT_RATE = "expect_rate";
        public static final String HOLD_DAY    = "hold_day";
        public static final String STOP_LOSS   = "stop_loss";
    }

    public interface SerialRiseBuyArgs {

        public static final String RISE_DAY  = "rise_day";
        public static final String RISE_RATE = "rise_rate";
    }

    public interface GeneralRiseBuyArgs {

        public static final String RISE_DAY   = "rise_day";
        public static final String RISE_RATE  = "rise_rate";
        public static final String SERIAL_DAY = "serial_day";
    }

    public interface SerialRiseSellArgs {

        public static final String EXPECT_RATE = "expect_rate";
        public static final String HOLD_DAY    = "hold_day";
        public static final String STOP_LOSS   = "stop_loss";
    }

    public interface TradeType {

        public static final String BUY  = "BUY";
        public static final String SELL = "SELL";
    }

    public interface TradeNature {

        public static final String REAL    = "BUY";
        public static final String VIRTUAL = "VIRTUAL";
    }

}
