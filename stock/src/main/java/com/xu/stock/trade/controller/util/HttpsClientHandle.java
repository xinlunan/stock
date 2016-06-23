package com.xu.stock.trade.controller.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecFactory;
import org.apache.http.cookie.CookieSpecRegistry;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BestMatchSpecFactory;
import org.apache.http.impl.cookie.BrowserCompatSpec;
import org.apache.http.impl.cookie.BrowserCompatSpecFactory;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xu.util.HttpClientHandle;

@SuppressWarnings({ "deprecation", "hiding" })
public class HttpsClientHandle {

    static Logger            log              = LoggerFactory.getLogger(HttpsClientHandle.class);
    static DefaultHttpClient httpClient       = getDefaultHttpClient();
    private static String    inputPhoneUrl    = "https://etrade.newone.com.cn/include/sjxxyzlogin.jsp?from=putong";
    private static String    imgVerifyCodeUrl = "https://etrade.newone.com.cn/validatecode/imgcode";
    private static String    smsVerifyCodeUrl = "https://etrade.newone.com.cn/include/getjym.jsp?sjh=15986643102&yzm=6";
    private static String    verifyPath       = "D:/server/tomcat/tomcat7/apache-tomcat-7.0.54.stock/webapps/stock/images/verify/verify.jpg";

    public static void main(String[] args) {

        try {
            String result = null;
            result = get(httpClient, inputPhoneUrl);
            log.info(result);
            printCookies(httpClient);

            download(httpClient, imgVerifyCodeUrl, verifyPath);
            printCookies(httpClient);

            result = get(httpClient, smsVerifyCodeUrl);
            log.info(result);
            printCookies(httpClient);

            String verifySmsCode = "https://etrade.newone.com.cn/include/sjxxyzloginaction.jsp?sjh=15986643102&yzm=";
            result = get(httpClient, verifySmsCode);
            log.info(result);
            printCookies(httpClient);

            download(httpClient, imgVerifyCodeUrl, verifyPath);
            printCookies(httpClient);

            List<NameValuePair> paras = new ArrayList<NameValuePair>();
            paras.add(new BasicNameValuePair("cxmm", ""));
            paras.add(new BasicNameValuePair("firstLogin", "1"));
            paras.add(new BasicNameValuePair("from", "putong"));
            paras.add(new BasicNameValuePair("f_jys", "2"));
            paras.add(new BasicNameValuePair("f_khh", "95996884"));
            paras.add(new BasicNameValuePair("f_khh1", ""));
            paras.add(new BasicNameValuePair("f_mm", "OTEyMzAz"));
            paras.add(new BasicNameValuePair("f_mm1", ""));
            paras.add(new BasicNameValuePair("f_no", "0"));
            paras.add(new BasicNameValuePair("f_yhkh1", "0"));
            paras.add(new BasicNameValuePair("f_yhkh", "0"));
            paras.add(new BasicNameValuePair("f_yybdm", ""));
            paras.add(new BasicNameValuePair("f_yybmc", ""));
            paras.add(new BasicNameValuePair("isActive", "0"));
            paras.add(new BasicNameValuePair("jjzh1", ""));
            paras.add(new BasicNameValuePair("jsessionid2", (String) getCookie(httpClient, "jsessionid2")));
            paras.add(new BasicNameValuePair("jybm", "100000"));
            paras.add(new BasicNameValuePair("lczh", ""));
            paras.add(new BasicNameValuePair("macip", "15986643102,"));// 95996884
            paras.add(new BasicNameValuePair("refresh", "window.opener"));
            paras.add(new BasicNameValuePair("sfzh1", ""));
            paras.add(new BasicNameValuePair("target_url", "https://etrade.newone.com.cn/newone/jsp/webTrade.jsp"));
            paras.add(new BasicNameValuePair("txxl", "https://etrade.newone.com.cn/include/loginFormNew.jsp"));
            // paras.add(new BasicNameValuePair("validatecode", 15));
            paras.add(new BasicNameValuePair("validatecode1", ""));
            paras.add(new BasicNameValuePair("version", "2"));
            result = post(httpClient, "https://etrade.newone.com.cn/xtrade", paras);
            log.info(result);
            printCookies(httpClient);

            result = get(httpClient, "https://etrade.newone.com.cn/xtrade?jybm=100040");
            log.info(result);
            printCookies(httpClient);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void printCookies(HttpClient httpClient) {
        List<Cookie> cookies = ((AbstractHttpClient) httpClient).getCookieStore().getCookies();
        if (cookies.isEmpty()) {
            log.info("None");
        } else {
            for (int i = 0; i < cookies.size(); i++) {
                log.info("- " + cookies.get(i).toString());
            }
        }
    }

    public static Object getCookie(HttpClient httpClient, Object name) {
        List<Cookie> cookies = ((AbstractHttpClient) httpClient).getCookieStore().getCookies();
        for (int i = 0; i < cookies.size(); i++) {
            if (cookies.get(i).getName().equals(name) == true) {
                return cookies.get(i).getValue();
            }
        }
        return null;
    }

    private static CookieSpecFactory buildCookieFactory() {
        CookieSpecFactory csf = new CookieSpecFactory() {
            @Override
            public CookieSpec newInstance(HttpParams params) {
                return new BrowserCompatSpec() {
                    @Override
                    public void validate(Cookie cookie, CookieOrigin origin) throws MalformedCookieException {
                        // Oh, I am easy
                    }
                };
            }
        };
        return csf;
    }

    public static String get(DefaultHttpClient httpClient, String url) {
        String content = "";
        int retry = 3;
        for (int i = 0; i == 0 || i < retry; i++) {
            try {
                HttpGet httpGet = new HttpGet(url);
                httpGet.setConfig(RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(2000).build());
                HttpResponse response = httpClient.execute(httpGet);
                HttpEntity entity = response.getEntity();

                if (null != entity) {
                    content = EntityUtils.toString(entity, "UTF-8");
                    EntityUtils.consume(entity);
                }
                return content;
            } catch (Throwable e) {
                if (i >= retry - 1) {
                    log.error("http download exception", e);
                    throw new RuntimeException(e);
                } else {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e1) {
                        throw new RuntimeException(e1);
                    }
                    continue;
                }
            }
        }
        return content;
    }

    public static String post(HttpClient httpClient, String url, List<NameValuePair> formparams) {
        String content = "";
        int retry = 3;
        for (int i = 0; i == 0 || i < retry; i++) {
            try {
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(formparams, "UTF-8"));
                httpPost.setConfig(RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(2000).build());
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                if (null != entity) {
                    content = EntityUtils.toString(entity, "UTF-8");
                    EntityUtils.consume(entity);
                }
                return content;
            } catch (Throwable e) {
                if (i >= retry - 1) {
                    throw new RuntimeException(e);
                } else {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e1) {
                        throw new RuntimeException(e1);
                    }
                    continue;
                }
            }
        }
        return content;
    }

    /**
     * 下载文件
     * 
     * @param httpClient
     * @param url
     * @param path
     * @return
     */
    public static boolean download(HttpClient httpClient, String url, String path) {
        int retry = 3;
        for (int i = 0; i == 0 || i < retry; i++) {
            log.info(url);
            try {
                UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(new ArrayList<NameValuePair>());
                HttpPost httppost = new HttpPost(url);
                httppost.setConfig(RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(2000).build());
                httppost.setEntity(uefEntity);
                HttpResponse response = httpClient.execute(httppost);
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    InputStream inputStream = response.getEntity().getContent();
                    byte[] bytes = HttpClientHandle.readByte(inputStream);
                    FileOutputStream bw = null;
                    try {
                        File file = new File(path);
                        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
                        if (file.exists()) file.delete();
                        bw = new FileOutputStream(path);
                        bw.write(bytes);
                        bw.flush();
                        return true;
                    } finally {
                        try {
                            if (bw != null) {
                                bw.close();
                            }
                            if (inputStream != null) {
                                inputStream.close();
                            }
                        } catch (Exception e) {
                            log.error("finally BufferedOutputStream shutdown close", e);
                        }
                    }
                }
            } catch (Throwable e) {
                if (i >= retry - 1) {
                    log.error("http download exception", e);
                    // 扫异常
                    throw new RuntimeException(e);
                } else {
                    // 重试
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e1) {
                        throw new RuntimeException(e1);
                    }
                    continue;
                }
            }
        }
        log.debug("http download end ");
        return true;
    }

    public static DefaultHttpClient getDefaultHttpClient() {
        DefaultHttpClient httpClient = new DefaultHttpClient();

        SchemeRegistry schReg = httpClient.getConnectionManager().getSchemeRegistry();
        CookieSpecRegistry cookieReg = ((AbstractHttpClient) httpClient).getCookieSpecs();
        HttpParams httpParams = httpClient.getParams();
        schReg.register(new Scheme("https", new SSLSocketFactory(buildSSLContext()), 443));
        schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        cookieReg.register(CookieSpecs.BEST_MATCH, new BestMatchSpecFactory());
        cookieReg.register(CookieSpecs.BROWSER_COMPATIBILITY, new BrowserCompatSpecFactory());
        cookieReg.register("mySpec", buildCookieFactory());
        httpParams.setParameter(ClientPNames.COOKIE_POLICY, "mySpec");
        httpParams.setParameter("http.protocol.single-cookie-header", true);
        return httpClient;
    }

    private static SSLContext buildSSLContext() {
        X509TrustManager xtm = new X509TrustManager() { // 创建TrustManager
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                //
            }
            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                //
            }
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, new TrustManager[] { xtm }, null);
            return ctx;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
