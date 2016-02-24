package com.xu.stock;

/**
 * @author lunan.xu 股票API常数
 */
public interface StockApiConstant {
	/** 获取股票列表API地址 */
	public static final String API_URL_GET_ALL_STOCKS = "http://ctxalgo.com/api/stocks";
	/** 获取股票指数API地址 */
	public static final String API_URL_GET_STOCK_INDEX = "http://ctxalgo.com/api/ohlc/";
	//TODO http://money.finance.sina.com.cn/corp/go.php/vMS_MarketHistory/stockid/601006.phtml?year=2015&jidu=2
	/** 修复股票指数API地址 */
	public static final String API_URL_REPAIR_STOCK_INDEX = "http://market.finance.sina.com.cn/downxls.php";

	//任一天详细交易信息
	//http://vip.stock.finance.sina.com.cn/quotes_service/view/vMS_tradehistory.php?symbol=sh601006&date=2015-09-30
}
