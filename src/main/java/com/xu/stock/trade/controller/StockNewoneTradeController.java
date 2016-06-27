package com.xu.stock.trade.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.http.NameValuePair;
import org.apache.http.impl.client.DefaultHttpClient;
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
import com.xu.stock.trade.vo.AuthorizeStock;
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

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(ModelMap model) {
        log.info("list");

        String result = HttpsClientHandle.get(httpClient, "https://etrade.newone.com.cn/xtrade?jybm=100040");
        if (result.contains("股票名称")) {
            model.put("result", result);
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
        log.info("list");

        // 取消现有委托
        cancelAuthorize();

        // 重新委托
        String holdStocksHtml = HttpsClientHandle.get(httpClient, "https://etrade.newone.com.cn/xtrade?jybm=100040");
        List<HoldStock> holdStocks = HoldStock.parse(holdStocksHtml);
        for (HoldStock holdStock : holdStocks) {
            String fullStockCode = holdStock.getStockCode().startsWith("6") ? "sh" + holdStock.getStockCode() : "sz" + holdStock.getStockCode();
            String currentHtml = HttpsClientHandle.get(httpClient, "https://etrade.newone.com.cn/xtrade?jybm=100020&zqdm=" + fullStockCode);
            StockCurrentPrice current = StockCurrentPrice.parseCurrentPrice(currentHtml);

            List<NameValuePair> paras = new ArrayList<NameValuePair>();
            paras.add(new BasicNameValuePair("btn_wt", ""));
            paras.add(new BasicNameValuePair("form_items", "1"));
            paras.add(new BasicNameValuePair("gddm", "putong"));
            paras.add(new BasicNameValuePair("Input5", "2"));
            paras.add(new BasicNameValuePair("jybm", ""));
            paras.add(new BasicNameValuePair("kyzj", ""));
            paras.add(new BasicNameValuePair("mmlb", ""));
            paras.add(new BasicNameValuePair("pwd", ""));
            paras.add(new BasicNameValuePair("pwdflag", "0"));
            paras.add(new BasicNameValuePair("qzts", "0"));
            paras.add(new BasicNameValuePair("scdm", "0"));
            paras.add(new BasicNameValuePair("sjwt", ""));
            paras.add(new BasicNameValuePair("tsgp", ""));
            paras.add(new BasicNameValuePair("wltp", "0"));
            paras.add(new BasicNameValuePair("wtjg", ""));
            paras.add(new BasicNameValuePair("wtsl", (String) HttpsClientHandle.getCookie(httpClient, "jsessionid2")));
            paras.add(new BasicNameValuePair("zcts", "100000"));
            paras.add(new BasicNameValuePair("zdkm", ""));
            paras.add(new BasicNameValuePair("zqdm", "15986643102,"));// 95996884
            paras.add(new BasicNameValuePair("zqmc", "window.opener"));
            String result = HttpsClientHandle.post(httpClient, "https://etrade.newone.com.cn/xtrade", paras);

        }

        return new ModelAndView("newone/default");
    }

    /**
     * 取消现有委托
     */
    @RequestMapping(value = "/authorize/cancel", method = RequestMethod.GET)
    public String cancelAuthorize() {
        String currentAuthorizeHtml = HttpsClientHandle.get(httpClient, "https://etrade.newone.com.cn/xtrade?jybm=100050&kcdbz=1&random=" + System.currentTimeMillis());
        List<AuthorizeStock> authorizeStocks = AuthorizeStock.parseAuthorizeStocks(currentAuthorizeHtml);
        for (AuthorizeStock stock : authorizeStocks) {
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            formparams.add(new BasicNameValuePair("hth", stock.getAuthorizeNo()));
            formparams.add(new BasicNameValuePair("gddm", stock.getOwnerNo()));
            HttpsClientHandle.post(httpClient, "https://etrade.newone.com.cn/xtrade?jybm=100013", formparams);
        }
        return "success";
    }

}
