package com.mfangsoft.zhuangjialong.app.sendflow.utils;

import java.util.Map;

import com.mfangsoft.zhuangjialong.app.sendflow.utils.HttpClient.HttpMethod;


/**
 * HTTP请求实体
 * 
 *
 */
public class HttpRequestBody {

	/**
	 * 是否HTTPS请求，默认为否
	 */
	private boolean isHttps = false;

	/**
	 * 编码，默认UTF-8
	 */
	private String charSet = "UTF-8";

	/**
	 * http请求方法，默认GET
	 */
	private HttpMethod method = HttpMethod.GET;

	/**
	 * http请求URL，不可为空
	 */
	private String url;

	/**
	 * http请求头
	 */
	private Map<String, String> headers;

	/**
	 * http请求Cookies 按照Cookies拼接格式拼接
	 */
	private String cookies;

	/**
	 * 参数字符串
	 */
	private String queryString;
	
	public HttpRequestBody(String url, String charSet, HttpMethod method,
			Map<String, String> headers, String cookies,
			Map<String, String> params) throws HttpErrorException{
		this(url, charSet, method, headers, cookies, params, null);
	}
	
	public HttpRequestBody(String url, String charSet, String paramsString,HttpMethod method,
			Map<String, String> headers, String cookies) throws HttpErrorException{
		this(url, charSet, method, headers, cookies, null, paramsString);
	}

	public String getUrl(){
		return url;
	}

	public boolean isHttps() {
		return isHttps;
	}

	public HttpMethod getMethod() {
		return method;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public String getCookies() {
		return cookies;
	}

	public String getCharSet() {
		return charSet;
	}

	public String getQueryString() {
		return this.queryString;
	}
	
	private HttpRequestBody(String url, String charSet, HttpMethod method,
			Map<String, String> headers, String cookies,
			Map<String, String> params, String paramsString)
			throws HttpErrorException {
		if(isEmpty(url)){
			throw new HttpErrorException("url is null");
		}
		if (!isEmpty(charSet)) {
			this.charSet = charSet;
		}
		if (method != null) {
			this.method = method;
		}
		this.headers = headers;
		this.cookies = cookies;
		this.queryString = this.generateParamsString(params, paramsString);
		this.url = this.generateUrl(url,params, paramsString);
		if (this.url.startsWith("https")) {
			this.isHttps = true;
		}
	}

	private boolean isEmpty(Object obj) {

		if (obj == null) {
			return true;
		}

		if (obj instanceof String) {
			return "".equals(((String) obj).trim());
		}

		if (obj instanceof Map) {
			return ((Map<?, ?>) obj).size() == 0;
		}

		return true;
	}

	private String generateParamsString(Map<String, String> params,
			String paramsString) {
		
		if (this.method.equals(HttpMethod.GET)) {
			return null;
		}
		
		if(isEmpty(paramsString) && isEmpty(params)){
			return null;
		}
		
		return this.buildQueryString(params, paramsString);

	}

	private String generateUrl(String requestUrl,Map<String, String> params,
			String paramsString){

		if (!requestUrl.startsWith("http")) {
			requestUrl = "http://" + requestUrl;
		}

		if (this.method.equals(HttpMethod.POST)
				|| this.isEmpty(this.queryString)) {
			return requestUrl;
		}
		
		String qstring = this.buildQueryString(params, paramsString);

		if (requestUrl.contains("?")) {
			requestUrl += "&" + qstring;
		} else {
			requestUrl += "?" + qstring;
		}

		return requestUrl;
	}
	
	private String buildQueryString(Map<String, String> params,
			String paramsString){
		
		String queryString = "";

		if (!isEmpty(params)) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				queryString += "&" + entry.getKey() + "=" + entry.getValue();
			}
			if (queryString.length() > 0) {
				queryString = queryString.substring(1);
			}
		} else {
			queryString = paramsString;
		}

		if (isEmpty(queryString)) {
			return null;
		}

		return queryString;
	}
	

}
