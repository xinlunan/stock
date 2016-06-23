package com.xu.stock.trade.controller;

import javax.annotation.Resource;

import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.xu.stock.data.dao.IStockDailyDao;
import com.xu.stock.data.service.IStockService;
import com.xu.stock.trade.controller.util.HttpsClientHandle;

/**
 * 股票分析控制层
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
@SuppressWarnings({ "restriction", "deprecation" })
@Controller
@Scope("prototype")
@RequestMapping("/trade/trade")
@Service("stockNewoneTradeController")
public class StockNewoneTradeController {

    protected Logger          log        = LoggerFactory.getLogger(this.getClass());

    private DefaultHttpClient httpClient = TradeClientFactory.getHttpClient();

    @Resource
    private IStockService     stockService;
    @Resource
    private IStockDailyDao          stockDailyDao;
    @Resource
    private StockNewoneLoginController stockNewoneLoginController;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView login(ModelMap model) {
        log.info("initLoginForm");

        String result = HttpsClientHandle.get(httpClient, "https://etrade.newone.com.cn/xtrade?jybm=100040");
        if (result.contains("股票名称")) {
            model.put("result", result);
            return new ModelAndView("newone/default", model);
        } else {
            model.put("msg", "登录超时，重新登录");
            return stockNewoneLoginController.initLoginVerify(model);
        }
    }

}
