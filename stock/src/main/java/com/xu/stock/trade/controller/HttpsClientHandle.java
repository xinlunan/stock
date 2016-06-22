package com.xu.stock.trade.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecFactory;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.impl.cookie.BrowserCompatSpec;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xu.util.HttpClientHandle;

@SuppressWarnings({ "deprecation", "hiding" })
public class HttpsClientHandle {

    static Logger log = LoggerFactory.getLogger(HttpsClientHandle.class);
    static HttpClient httpClient = getHttpClient();

    public static void main(String[] args) {
        String mobileVerifyUrl = "https://etrade.newone.com.cn/include/sjxxyzlogin.jsp?from=putong";
        String verifyUrl = "https://etrade.newone.com.cn/validatecode/imgcode";
        String verifyPath = "D:/server/tomcat/tomcat7/apache-tomcat-7.0.54.stock/webapps/stock/images/verify/verify.jpg";
        String getVerifyCode = "https://etrade.newone.com.cn/include/getjym.jsp?sjh=15986643102&yzm=6";
        List<NameValuePair> paras = new ArrayList<NameValuePair>();

        printCookies(httpClient);
        String result = post(httpClient, mobileVerifyUrl, paras, new HashMap<String, Object>());
        printCookies(httpClient);
        download(httpClient, verifyUrl, verifyPath);
        printCookies(httpClient);
        result = post(httpClient, getVerifyCode, paras, new HashMap<String, Object>());
        printCookies(httpClient);

        log.info(result);
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

    public static Object getCookieObject(HttpClient httpClient, Object name) {
        List<Cookie> cookies = ((AbstractHttpClient) httpClient).getCookieStore().getCookies();
        for (int i = 0; i < cookies.size(); i++) {
            if (cookies.get(i).getName().equals(name) == true) {
                return cookies.get(i);
            }
        }
        return null;
    }

    public static HttpClient createHttpClient() {
        HttpClient httpClient = new DefaultHttpClient();

        SchemeRegistry schReg = httpClient.getConnectionManager().getSchemeRegistry();
        schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schReg.register(new Scheme("https", PlainSocketFactory.getSocketFactory(), 433));
        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, HTTP.DEFAULT_CONTENT_CHARSET);
        HttpProtocolParams.setUseExpectContinue(params, true);
        ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, schReg);
        return httpClient;

    }

    public static HttpClient getHttpClient() {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager xtm = buildX509TrustManager();
            ctx.init(null, new TrustManager[] { xtm }, null);
            Scheme scheme = new Scheme("https", 443, new SSLSocketFactory(ctx));
            httpClient.getConnectionManager().getSchemeRegistry().register(scheme);
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
            httpClient.getCookieSpecs().register("easy", csf);
            httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, "easy");
            HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 2000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return httpClient;
    }

    public static String post(String url, List<NameValuePair> formparams) {
        return post(getHttpClient(), url, formparams, new HashMap<String, Object>());
    }

    public static String post(HttpClient httpClient2, String url, List<NameValuePair> paras) {
        return post(httpClient2, url, paras, new HashMap<String, Object>());
    }

    public static String get(HttpClient httpsClient, String url, Map<String, Object> header) {
        String content = "";
        int retry = 3;
        for (int i = 0; i == 0 || i < retry; i++) {
            try {
                DefaultHttpClient httpClient = (DefaultHttpClient) httpsClient;
                HttpGet httpGet = new HttpGet(url);
                RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(2000).build();// 设置请求和传输超时时间
                httpGet.setConfig(requestConfig);
                Iterator<String> it = header.keySet().iterator();
                while (it.hasNext()) {
                    String key = it.next();
                    // httpGet.setHeader(key, header.get(key).toString());
                }

                HttpResponse response = httpClient.execute(httpGet);
                HttpEntity entity = response.getEntity();

                if (null != entity) {
                    content = EntityUtils.toString(entity, "UTF-8");
                    EntityUtils.consume(entity);
                }
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

    public static String post(HttpClient httpsClient, String url, List<NameValuePair> formparams, Map<String, Object> header) {
        String content = "";
        int retry = 3;
        for (int i = 0; i == 0 || i < retry; i++) {
            try {
                DefaultHttpClient httpClient = (DefaultHttpClient) httpsClient;
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(formparams, "UTF-8"));
                RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(2000).build();// 设置请求和传输超时时间
                httpPost.setConfig(requestConfig);
                Iterator<String> it = header.keySet().iterator();
                while (it.hasNext()) {
                    String key = it.next();
                    // httpPost.setHeader(key, header.get(key).toString());
                }

                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();

                if (null != entity) {
                    content = EntityUtils.toString(entity, "UTF-8");
                    EntityUtils.consume(entity);
                }
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

    /**
     * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
     */
    public static boolean download(HttpClient httpsClient, String url, String path) {
        int retry = 3;
        for (int i = 0; i == 0 || i < retry; i++) {
            log.info(url);
            // 创建默认的httpClient实例.
            CloseableHttpClient httpclient = (CloseableHttpClient) httpsClient;
            try {
                // 创建参数队列
                UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(new ArrayList<NameValuePair>());
                // 创建httppost
                HttpPost httppost = new HttpPost(url);
                RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(2000).build();// 设置请求和传输超时时间
                httppost.setConfig(requestConfig);
                httppost.setEntity(uefEntity);

                CloseableHttpResponse response = httpclient.execute(httppost);
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    InputStream inputStream = response.getEntity().getContent();
                    byte[] result = HttpClientHandle.readByte(inputStream);
                    FileOutputStream bw = null;
                    try {
                        // 创建文件对象
                        File f = new File(path);
                        // 创建文件路径
                        if (!f.getParentFile().exists()) f.getParentFile().mkdirs();
                        if (f.exists()) {
                            f.delete();
                        }
                        // 写入文件
                        bw = new FileOutputStream(path);
                        bw.write(result);
                        bw.flush();
                        return true;
                    } finally {
                        try {
                            if (bw != null) {
                                bw.close();
                            }
                            if (response != null) {
                                response.close();
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

    private static X509TrustManager buildX509TrustManager() {
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
        return xtm;
    }

}
