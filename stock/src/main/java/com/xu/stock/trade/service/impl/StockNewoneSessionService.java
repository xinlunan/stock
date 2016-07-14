package com.xu.stock.trade.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xu.stock.trade.controller.TradeClientFactory;
import com.xu.stock.trade.controller.util.HttpsClientHandle;
import com.xu.stock.trade.service.IStockNewoneSessionService;
import com.xu.stock.trade.vo.AuthorizeStock;

/**
 * 保持会话
 * 
 * @version
 * 
 * <pre>
 * Author	Version		Date		Changes
 * lunan.xu 	1.0  		2016年3月19日 	Created
 * </pre>
 * 
 * @since 1.
 */
@Service("stockNewoneSessionService")
public class StockNewoneSessionService implements IStockNewoneSessionService {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void keepAlive() {
        String result = HttpsClientHandle.get(TradeClientFactory.getHttpClient(), "https://etrade.newone.com.cn/xtrade?jybm=100040");
        HttpsClientHandle.printCookies(TradeClientFactory.getHttpClient());
        if (result.contains("股票")) {
            log.info("http client is alive");
        } else {
            log.info(result);
        }

    }

    @Override
    public void cancelAuthorize() {
        String currentAuthorizeHtml = HttpsClientHandle.get(TradeClientFactory.getHttpClient(), "https://etrade.newone.com.cn/xtrade?jybm=100050&kcdbz=1&random=" + System.currentTimeMillis());
        List<AuthorizeStock> authorizeStocks = AuthorizeStock.parse(currentAuthorizeHtml);
        for (AuthorizeStock stock : authorizeStocks) {
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            formparams.add(new BasicNameValuePair("hth", stock.getAuthorizeNo()));
            formparams.add(new BasicNameValuePair("gddm", stock.getOwnerNo()));
            HttpsClientHandle.post(TradeClientFactory.getHttpClient(), "https://etrade.newone.com.cn/xtrade?jybm=100013", formparams);
        }
    }

}
