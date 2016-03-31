package com.xu.stock.data.download;

/**
 * @author lunan.xu 股票API常数
 */
public interface StockApiConstants {

	/**
	 * 新浪股票信息
	 * 
	 * @version
	 * 
	 * 			<pre>
	 * Author	Version		Date		Changes
	 * lunan.xu 	1.0  		2016年3月12日 	Created
	 *
	 *          </pre>
	 * 
	 * @since 1.
	 */
	public interface Sina {
		/** 新浪获取股票指数API地址 */
		// http://money.finance.sina.com.cn/corp/go.php/vMS_MarketHistory/stockid/601006.phtml?year=2015&jidu=2
		public static final String API_URL_GET_STOCK_INDEX = "http://money.finance.sina.com.cn/corp/go.php/vMS_MarketHistory/stockid/";

		/** 修复股票指数API地址 */
		// http://market.finance.sina.com.cn/downxls.php?date=2011-07-08&symbol=sh600900
		public static final String API_URL_REPAIR_STOCK_INDEX = "http://market.finance.sina.com.cn/downxls.php";

		/** 交易明细 */
		// http://vip.stock.finance.sina.com.cn/quotes_service/view/vMS_tradehistory.php?symbol=sh601006&date=2015-09-30
		public static final String API_URL_STOCK_TRADE = "http://vip.stock.finance.sina.com.cn/quotes_service/view/vMS_tradehistory.php?";
	}

	/**
	 * EqbQuant股票信息
	 * 
	 * @version
	 * 
	 * 			<pre>
	 * Author	Version		Date		Changes
	 * lunan.xu 	1.0  		2016年3月12日 	Created
	 *
	 *          </pre>
	 * 
	 * @since 1.
	 */
	public interface EqbQuant {
		/** 获取股票列表API地址 */
		public static final String API_URL_GET_ALL_STOCKS = "http://ctxalgo.com/api/stocks";

		/** 获取股票指数API地址 */
		// http://ctxalgo.com/api/ohlc/sz000001,sh600001,sz000004?start-date=2015-05-20&end-date=2015-05-24
		public static final String API_URL_GET_STOCK_INDEX = "http://ctxalgo.com/api/ohlc/";

	}

}
