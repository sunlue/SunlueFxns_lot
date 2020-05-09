package com.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Map;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

public class Http {

	public static String chartSet = "UTF-8";
	public static final String WXPAYSDK_VERSION = "eByteSDK_WeiXin/0.0.1";
	public static final String USER_AGENT = WXPAYSDK_VERSION + " (" + System.getProperty("os.arch") + " "
			+ System.getProperty("os.name") + " " + System.getProperty("os.version") + ") Java/"
			+ System.getProperty("java.version") + " HttpClient/"
			+ HttpClient.class.getPackage().getImplementationVersion();
	private static final String CERT_LOCAL_PATH = "";
	static char[] CERT_PASSWORD;

	/**
	 * 设置编码
	 * 
	 * @param chartSet
	 */
	public static void setChartSet(String chartSet) {
		Http.chartSet = chartSet;
	}

	/**
	 * GET 请求
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String get(String url) throws Exception {
		return get(url, null);
	}

	/**
	 * GET 请求
	 * 
	 * @param url
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static String get(String url, Map<String, String> param) throws Exception {
		URIBuilder builder = new URIBuilder(url);
		if (param != null) {
			for (String key : param.keySet()) {
				builder.addParameter(key, param.get(key));
			}
		}
		URI uri = builder.build();
		HttpGet httpGet = new HttpGet(uri);
		return requestSend(httpGet);
	}

	/**
	 * 发送POST请求
	 * 
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String post(String url) throws ClientProtocolException, IOException {
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Content-Type", "application/xml");
		return requestSend(httpPost);
	}

	/**
	 * 发送POST请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String post(String url, String params) throws ClientProtocolException, IOException {
		HttpPost httpPost = new HttpPost(url);
		HttpEntity requestEntity = new StringEntity(params, chartSet); // 设置请求体
		httpPost.setEntity(requestEntity);
		httpPost.addHeader("Content-Type", "application/xml"); // 设置请求头
		return requestSend(httpPost);
	}

	/**
	 * 发送POST请求
	 * 
	 * @param url
	 * @param params
	 * @param cerPath
	 * @param cerPassword
	 * @return
	 * @throws Exception
	 */
	public static String post(String url, String params, boolean useCert) throws Exception {
		HttpPost httpPost = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(6000).setConnectTimeout(8000).build();
		httpPost.setConfig(requestConfig);
		StringEntity postEntity = new StringEntity(params, chartSet);
		httpPost.addHeader("Content-Type", "text/xml");
		httpPost.addHeader("User-Agent", USER_AGENT);
		httpPost.setEntity(postEntity);
		return requestCertSend(httpPost, useCert);
	}

	/**
	 * 带证书请求一次
	 * 
	 * @param url
	 * @param data
	 * @param connectTimeoutMs
	 * @param readTimeoutMs
	 * @param useCert
	 * @return
	 * @throws Exception
	 */
	public static String requestCertSend(HttpUriRequest method, boolean useCert) throws Exception {
		BasicHttpClientConnectionManager connManager;
		if (useCert) {
			// 证书
			InputStream certStream = Http.class.getClassLoader().getResourceAsStream(CERT_LOCAL_PATH);
			KeyStore ks = KeyStore.getInstance("PKCS12");
			ks.load(certStream, CERT_PASSWORD);
			// 实例化密钥库 & 初始化密钥工厂
			KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmf.init(ks, CERT_PASSWORD);
			// 创建 SSLContext
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());

			SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext,
					new String[] { "TLSv1" }, null, new DefaultHostnameVerifier());

			connManager = new BasicHttpClientConnectionManager(RegistryBuilder.<ConnectionSocketFactory>create()
					.register("http", PlainConnectionSocketFactory.getSocketFactory())
					.register("https", sslConnectionSocketFactory).build(), null, null, null);
		} else {
			connManager = new BasicHttpClientConnectionManager(
					RegistryBuilder.<ConnectionSocketFactory>create()
							.register("http", PlainConnectionSocketFactory.getSocketFactory())
							.register("https", SSLConnectionSocketFactory.getSocketFactory()).build(),
					null, null, null);
		}

		HttpClient httpClient = HttpClientBuilder.create().setConnectionManager(connManager).build();
		HttpResponse httpResponse = httpClient.execute(method);
		HttpEntity httpEntity = httpResponse.getEntity();
		return EntityUtils.toString(httpEntity, chartSet);

	}

	/**
	 * 无证书请求
	 * 
	 * @param method
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private static String requestSend(HttpUriRequest method) throws ClientProtocolException, IOException {
		CloseableHttpClient HttpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String result = "";
		try {
			response = HttpClient.execute(method);
			HttpEntity entity = response.getEntity();
			if (response.getStatusLine().getStatusCode() == 200 && entity != null) {
				result = EntityUtils.toString(entity, chartSet);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				HttpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
