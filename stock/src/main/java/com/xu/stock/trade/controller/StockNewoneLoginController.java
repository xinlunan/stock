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
import com.xu.stock.trade.controller.util.Rfc1521;

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
@RequestMapping("/trade")
@Service("stockNewoneLoginController")
public class StockNewoneLoginController {

    protected Logger          log        = LoggerFactory.getLogger(this.getClass());

    private DefaultHttpClient httpClient = TradeClientFactory.getHttpClient();
    private String            verifyUrl  = "https://etrade.newone.com.cn/validatecode/imgcode";
    private String            verifyPath = "D:/server/tomcat/tomcat7/apache-tomcat-7.0.54.stock/webapps/stock/images/verify/verify.jpg";

    @Resource
    private IStockService     stockService;
    @Resource
    private IStockDailyDao    stockDailyDao;

    /**
     * 初始化填写手机号，获取短信校验码界面
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/login/step1", method = RequestMethod.GET)
    public ModelAndView initLoginVerify(ModelMap model) {
        log.info("/login/step1");
        String mobileVerifyUrl = "https://etrade.newone.com.cn/include/sjxxyzlogin.jsp?from=putong";
        HttpsClientHandle.get(httpClient, mobileVerifyUrl);
        HttpsClientHandle.download(httpClient, verifyUrl, verifyPath);
        HttpsClientHandle.printCookies(httpClient);
        model.put("mobile", "15986643102");
        return new ModelAndView("login/step1_form", model);
    }

    /**
     * 根据手机号获取短信校验码，并初始化填写短信校验码界面
     * 
     * @param model
     * @param mobile
     * @param code
     * @return
     */
    @RequestMapping(value = "/login/step2", method = RequestMethod.GET)
    public ModelAndView initLoginForm(ModelMap model, String mobile, String code) {
        log.info("initLoginForm");
        String getVerifyCode = "https://etrade.newone.com.cn/include/getjym.jsp?sjh=" + mobile + "&yzm=" + code;
        String result = HttpsClientHandle.get(httpClient, getVerifyCode);

        model.put("mobile", mobile);
        if (result.contains("status:'success',sjh:")) {
            return new ModelAndView("login/step2_form", model);
        } else {
            model.put("msg", result);
            return initLoginVerify(model);
        }
    }

    /**
     * 根据手机号、短信校验码向交易平台验证，并初始化登录界面
     * 
     * @param model
     * @param mobile
     * @param code
     * @return
     */
    @RequestMapping(value = "/login/step3", method = RequestMethod.GET)
    public ModelAndView getVerify(ModelMap model, String mobile, String code) {
        log.info("getVerify");
        String getVerifyCode = "https://etrade.newone.com.cn/include/sjxxyzloginaction.jsp?sjh=" + mobile + "&yzm=" + code;
        String result = HttpsClientHandle.get(httpClient, getVerifyCode);
        if (result.contains("status:'success'")) {
            HttpsClientHandle.download(httpClient, verifyUrl, verifyPath);
            model.put("account", "95996884");
            return new ModelAndView("login/step3_form", model);
        } else {
            model.put("msg", result);
            return initLoginVerify(model);
        }
    }

    /**
     * 根据帐号、密码向交易平台登录
     * 
     * @param model
     * @param mobile
     * @param code
     * @return
     */
    @RequestMapping(value = "/login/step4", method = RequestMethod.POST)
    public ModelAndView login(ModelMap model, String account, String password, String code) {
        log.info("initLoginForm");

        List<NameValuePair> paras = new ArrayList<NameValuePair>();
        paras.add(new BasicNameValuePair("cxmm", ""));
        paras.add(new BasicNameValuePair("firstLogin", "1"));
        paras.add(new BasicNameValuePair("from", "putong"));
        paras.add(new BasicNameValuePair("f_jys", "2"));
        paras.add(new BasicNameValuePair("f_khh", account));
        paras.add(new BasicNameValuePair("f_khh1", ""));
        paras.add(new BasicNameValuePair("f_mm", Rfc1521.encode(password)));
        paras.add(new BasicNameValuePair("f_mm1", ""));
        paras.add(new BasicNameValuePair("f_no", "0"));
        paras.add(new BasicNameValuePair("f_yhkh1", "0"));
        paras.add(new BasicNameValuePair("f_yhkh", "0"));
        paras.add(new BasicNameValuePair("f_yybdm", ""));
        paras.add(new BasicNameValuePair("f_yybmc", ""));
        paras.add(new BasicNameValuePair("isActive", "0"));
        paras.add(new BasicNameValuePair("jjzh1", ""));
        paras.add(new BasicNameValuePair("jsessionid2", (String) HttpsClientHandle.getCookie(httpClient, "jsessionid2")));
        paras.add(new BasicNameValuePair("jybm", "100000"));
        paras.add(new BasicNameValuePair("lczh", ""));
        paras.add(new BasicNameValuePair("macip", "15986643102,"));// 95996884
        paras.add(new BasicNameValuePair("refresh", "window.opener"));
        paras.add(new BasicNameValuePair("sfzh1", ""));
        paras.add(new BasicNameValuePair("target_url", "https://etrade.newone.com.cn/newone/jsp/webTrade.jsp"));
        paras.add(new BasicNameValuePair("txxl", "https://etrade.newone.com.cn/include/loginFormNew.jsp"));
        paras.add(new BasicNameValuePair("validatecode", code));
        paras.add(new BasicNameValuePair("validatecode1", ""));
        paras.add(new BasicNameValuePair("version", "2"));
        String result = HttpsClientHandle.post(httpClient, "https://etrade.newone.com.cn/xtrade", paras);
        if (result.contains("document.location.href")) {
            model.put("result", result);
            return new ModelAndView("trade/list", model);
        } else {
            log.info(result);
            HttpsClientHandle.download(httpClient, verifyUrl, verifyPath);
            model.put("account", "95996884");
            model.put("msg", "用户名或密码或校验码输入错误");
            return new ModelAndView("login/step3_form", model);
        }

    }

}
