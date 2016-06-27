package com.xu.stock.trade.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xu.stock.trade.controller.TradeClientFactory;
import com.xu.stock.trade.controller.util.HttpsClientHandle;
import com.xu.stock.trade.service.IStockNewoneSessionService;

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
        HttpsClientHandle.get(TradeClientFactory.getHttpClient(), "https://etrade.newone.com.cn/xtrade?jybm=100040");
    }

}
