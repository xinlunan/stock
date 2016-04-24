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
     * <pre>
     * Author	Version		Date		Changes
     * lunan.xu 	1.0  		2016年3月12日 	Created
     * </pre>
     * 
     * @since 1.
     */
    public interface Sina {

        /** 新浪获取股票指数API地址 */
        // http://money.finance.sina.com.cn/corp/go.php/vMS_MarketHistory/stockid/601006.phtml?year=2015&jidu=2
        public static final String API_URL_GET_STOCK_INDEX        = "http://money.finance.sina.com.cn/corp/go.php/vMS_MarketHistory/stockid/";
        // "http://vip.stock.finance.sina.com.cn/corp/go.php/vMS_FuQuanMarketHistory/stockid/000001.phtml?year=2016&jidu=2"
        public static final String API_URL_GET_FUQUAN_STOCK_INDEX = "http://vip.stock.finance.sina.com.cn/corp/go.php/vMS_FuQuanMarketHistory/stockid/";

        /** 修复股票指数API地址 */
        // http://market.finance.sina.com.cn/downxls.php?date=2011-07-08&symbol=sh600900
        // http://stock.gtimg.cn/data/index.php?appn=detail&action=download&c=sh601857&d=20160408
        public static final String API_URL_REPAIR_STOCK_MINUTE_INDEX     = "http://market.finance.sina.com.cn/downxls.php";

        /** 交易明细 */
        // http://vip.stock.finance.sina.com.cn/quotes_service/view/vMS_tradehistory.php?symbol=sh601006&date=2015-09-30
        public static final String API_URL_STOCK_TRADE            = "http://vip.stock.finance.sina.com.cn/quotes_service/view/vMS_tradehistory.php?";

        // http://vip.stock.finance.sina.com.cn/quotes_service/view/vMS_tradehistory.php?symbol=sh601006&date=2015-09-30
        public static final String API_URL_STOCK_DETAL            = "http://finance.sina.com.cn/realstock/company/##/nc.shtml";

        /** 实时信息 */
        // http://hq.sinajs.cn/list=sz300360
        public static final String API_URL_STOCK_REAL_DETAL       = "http://hq.sinajs.cn/list=";
    }

    /**
     * EqbQuant股票信息
     * 
     * @version
     * 
     * <pre>
     * Author	Version		Date		Changes
     * lunan.xu 	1.0  		2016年3月12日 	Created
     * </pre>
     * 
     * @since 1.
     */
    public interface EqbQuant {

        /** 获取股票列表API地址 */
        public static final String API_URL_GET_ALL_STOCKS  = "http://ctxalgo.com/api/stocks";

        /** 获取股票指数API地址 */
        // http://ctxalgo.com/api/ohlc/sz000001,sh600001,sz000004?start-date=2015-05-20&end-date=2015-05-24
        public static final String API_URL_GET_STOCK_INDEX = "http://ctxalgo.com/api/ohlc/";

    }

    public interface Yahoo {

        /** 新浪获取股票指数API地址 */
        // http://table.finance.yahoo.com/table.csv?s=000001.sz
        // http://blog.csdn.net/stanmarsh/article/details/9795485 介绍
        public static final String API_URL_GET_STOCK_INDEX = "http://money.finance.sina.com.cn/corp/go.php/vMS_MarketHistory/stockid/";

    }

    public interface Sohu {

        /** 新浪获取股票指数API地址 */
        // http://q.stock.sohu.com/hisHq?code=cn_000001&start=20130609&end=20130730
        public static final String API_URL_GET_STOCK_INDEX = "http://q.stock.sohu.com/hisHq?code=cn_";

    }

}
