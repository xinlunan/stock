package com.xu.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientHandle {
	static Logger log = LoggerFactory.getLogger(HttpClientHandle.class);

	@Test
	public void jUnitTest() {
		get("http://www.baidu.com");
	}

	public static String get(String url) {
		return get(url, "UTF-8");
	}

	/**
	 * 发送 get请求
	 */
	public static String get(String url, String charset) {
		log.debug("http get url: " + url);
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpGet httpGet = new HttpGet(url);

			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(3000)
					.build();//设置请求和传输超时时间
			httpGet.setConfig(requestConfig);
			httpClient.execute(httpGet);//执行请求
			// 执行get请求
			CloseableHttpResponse response = httpClient.execute(httpGet);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					result = EntityUtils.toString(entity, charset);
					log.debug("http get completed. url: " + url);
					log.debug(result);
				}
			} finally {
				response.close();
			}
		} catch (Throwable e) {
			log.error("http get exception", e);
			throw new RuntimeException(e);
		} finally {
			try {
				// 关闭连接,释放资源
				httpClient.close();
			} catch (IOException e) {
				log.error("http het close exception", e);
			}
		}
		return result;
	}

	/**
	 * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
	 */
	public static String post(String url, List<NameValuePair> formparams) {
		return post(url, formparams, "UTF-8");
	}

	/**
	 * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
	 */
	public static String post(String url, List<NameValuePair> formparams, String encoded) {
		log.debug("http post url: " + url);
		String result = "";
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			// 创建参数队列
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, encoded);
			// 创建httppost
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(uefEntity);
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					result = EntityUtils.toString(entity);
					log.debug("http get result: " + result);
				}
			} finally {
				response.close();
			}
		} catch (Throwable e) {
			log.error("http get exception", e);
		} finally {
			try {
				// 关闭连接,释放资源
				httpclient.close();
			} catch (IOException e) {
				log.error("http het close exception", e);
			}
		}
		return result;
	}

	/**
	 * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
	 */
	public static boolean download(String url, String path) {
		log.debug("http post url: " + url);
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			// 创建参数队列
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(new ArrayList<NameValuePair>());
			// 创建httppost
			HttpPost httppost = new HttpPost(url);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(5000)
					.build();//设置请求和传输超时时间
			httppost.setConfig(requestConfig);
			httppost.setEntity(uefEntity);

			CloseableHttpResponse response = httpclient.execute(httppost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				InputStream inputStream = response.getEntity().getContent();
				byte[] result = readByte(inputStream);

				String strRusult = new String(result);
				if (strRusult.startsWith("<script ")) {
					return false;
				}
				FileOutputStream bw = null;
				try {
					// 创建文件对象  
					File f = new File(path);
					// 创建文件路径  
					if (!f.getParentFile().exists())
						f.getParentFile().mkdirs();
					if (f.exists()) {
						f.delete();
					}
					// 写入文件  
					bw = new FileOutputStream(path);
					bw.write(result);
					bw.flush();
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
			log.error("http download exception", e);
			throw new RuntimeException(e);
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				log.error("http download close exception", e);
			}
		}
		return true;
	}

	/**
	 * HttpClient连接SSL
	 */
	public void ssl() {
		//TODO
	}

	/**
	 * 上传文件
	 */
	public void upload() {
		//TODO
	}

	/**
	 * 读取流
	 * 
	 * @param inStream
	 * @return 字节数组
	 */
	public static byte[] readByte(InputStream inStream) {
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		try {
			byte[] buffer = new byte[1024];
			int len = -1;
			while ((len = inStream.read(buffer)) != -1) {
				outSteam.write(buffer, 0, len);
			}

			return outSteam.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException("读取流失败", e);
		} finally {
			try {
				outSteam.close();
			} catch (IOException e) {
				throw new RuntimeException("读取流失败", e);
			}
		}
	}
}
