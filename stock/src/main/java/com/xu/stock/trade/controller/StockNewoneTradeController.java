package com.xu.stock.trade.controller;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.http.NameValuePair;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
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
import com.xu.stock.trade.service.impl.StockNewoneSessionService;
import com.xu.stock.trade.vo.HoldStock;
import com.xu.stock.trade.vo.StockCurrentPrice;

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
// 95996884
public class StockNewoneTradeController {

    protected Logger                   log        = LoggerFactory.getLogger(this.getClass());

    private DefaultHttpClient          httpClient = TradeClientFactory.getHttpClient();

    @Resource
    private IStockService              stockService;
    @Resource
    private IStockDailyDao             stockDailyDao;
    @Resource
    private StockNewoneLoginController stockNewoneLoginController;
    @Resource
    private StockNewoneSessionService  stockNewoneSessionService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(ModelMap model) {
        log.info("list");

        HttpsClientHandle.printCookies(httpClient);
        String result = HttpsClientHandle.get(httpClient, "https://etrade.newone.com.cn/xtrade?jybm=100040");
        HttpsClientHandle.printCookies(httpClient);
        if (result.contains("股票名称")) {
            model.put("result", result.replace("width=780", "width=100%"));
            return new ModelAndView("newone/default", model);
        } else {
            log.info(result);
            model.put("msg", "登录超时，重新登录");
            return stockNewoneLoginController.initLoginVerify(model);
        }
    }

    /**
     * 按期望值委托
     * 
     * @return
     */
    @RequestMapping(value = "/authorize/expect", method = RequestMethod.GET)
    public ModelAndView authorizeExpect() {
        log.info("/trade/trade/authorize/expect");

        // 1、取消现有委托
        stockNewoneSessionService.cancelAuthorize();

        setPwdCookie();

        // 2、查询可卖数据
        String holdStocksHtml = HttpsClientHandle.get(httpClient, "https://etrade.newone.com.cn/xtrade?jybm=100040");
        List<HoldStock> holdStocks = HoldStock.parse(holdStocksHtml);
        HttpsClientHandle.get(httpClient, "https://etrade.newone.com.cn/xtrade?jybm=100020");
        HttpsClientHandle.get(httpClient, "https://etrade.newone.com.cn/xtrade?jybm=100050");
        for (HoldStock holdStock : holdStocks) {
            if (holdStock.getSellableCount() > 0) {
                // 3、获取当前行情
                String fullStockCode = holdStock.getStockCode().startsWith("6") ? "sh" + holdStock.getStockCode() : "sz" + holdStock.getStockCode();
                String currentHtml = HttpsClientHandle.get(httpClient, "https://etrade.newone.com.cn/newtrade/func/getWDData.jsp?zqdm=" + fullStockCode);
                StockCurrentPrice current = StockCurrentPrice.parse(currentHtml);
                if (current.getCurrentPrice().compareTo(current.getRiseStopPrice()) != 0) {
                    try {
                        // 4、委托卖出
                        StringBuffer urlSb = new StringBuffer("https://etrade.newone.com.cn/xtrade?jybm=100012&form_items=1&scdm=1&mmlb=2&qzts=0&mykhh=95996884&zcts=");
                        urlSb.append("&gddm=").append(holdStock.getOwnerNo()).append("&zqdm=").append(current.getStockCode());
                        urlSb.append("&zqmc=").append(URLEncoder.encode(current.getStockName(), "UTF-8"));
                        urlSb.append("&sjwt=0").append("&wtjg=").append(countExpectAuthorizePrice(current));
                        urlSb.append("&kyzj=1000,000.00").append("&zdkm=").append(holdStock.getSellableCount());
                        urlSb.append("&Input5=").append(URLEncoder.encode("全部", "UTF-8")).append("&wtsl=").append(holdStock.getSellableCount());
                        urlSb.append("&pwd=&pwdflag=false&btn_wt=").append(URLEncoder.encode("下单", "UTF-8"));

                        List<NameValuePair> paras = new ArrayList<NameValuePair>();
                        paras.add(new BasicNameValuePair("btn_wt", "下单"));
                        paras.add(new BasicNameValuePair("form_items", "1"));
                        paras.add(new BasicNameValuePair("gddm", holdStock.getOwnerNo()));
                        paras.add(new BasicNameValuePair("Input5", "全部"));
                        paras.add(new BasicNameValuePair("jybm", "100012"));
                        paras.add(new BasicNameValuePair("kyzj", "1000,000.00"));
                        paras.add(new BasicNameValuePair("mmlb", "2"));// 2:卖出,1:买入
                        paras.add(new BasicNameValuePair("mykhh", "95996884"));// 95996884
                        paras.add(new BasicNameValuePair("pwd", ""));
                        paras.add(new BasicNameValuePair("pwdflag", "false"));
                        paras.add(new BasicNameValuePair("qzts", "0"));
                        paras.add(new BasicNameValuePair("scdm", current.getStockCode().startsWith("6") ? "1" : "2"));
                        paras.add(new BasicNameValuePair("sjwt", "0"));
                        paras.add(new BasicNameValuePair("wtjg", countExpectAuthorizePrice(current)));
                        paras.add(new BasicNameValuePair("wtsl", "" + holdStock.getSellableCount()));
                        paras.add(new BasicNameValuePair("zcts", ""));
                        paras.add(new BasicNameValuePair("zdkm", "" + holdStock.getSellableCount()));
                        paras.add(new BasicNameValuePair("zqdm", current.getStockCode()));
                        paras.add(new BasicNameValuePair("zqmc", current.getStockName()));
                        String result = HttpsClientHandle.post(httpClient, "https://etrade.newone.com.cn/xtrade", paras);
                        log.info(result);
                        log.info(result);
                    } catch (Exception e) {
                        log.error("", e);
                    }
                }
            }
        }
        return new ModelAndView("newone/default");
    }

    private void setPwdCookie() {
        BasicClientCookie stdCookie = new BasicClientCookie("wtPwdflag", "false");
        stdCookie.setVersion(0);
        stdCookie.setDomain("etrade.newone.com.cn");
        stdCookie.setPath("/");
        stdCookie.setSecure(true);
        stdCookie.setAttribute(ClientCookie.VERSION_ATTR, "0");
        stdCookie.setAttribute(ClientCookie.DOMAIN_ATTR, "etrade.newone.com.cn");

        httpClient.getCookieStore().addCookie(stdCookie);
        HttpsClientHandle.printCookies(httpClient);
    }

    private String countExpectAuthorizePrice(StockCurrentPrice current) {
        BigDecimal expectProfit = current.getRiseStopPrice().subtract(current.getLastClose()).multiply(BigDecimal.valueOf(0.7));

        return expectProfit.add(current.getLastClose()).toString();
    }

    /**
     * 取消现有委托
     */
    @RequestMapping(value = "/authorize/cancel", method = RequestMethod.GET)
    public String cancelAuthorize() {
        stockNewoneSessionService.cancelAuthorize();
        return "success";
    }

}
