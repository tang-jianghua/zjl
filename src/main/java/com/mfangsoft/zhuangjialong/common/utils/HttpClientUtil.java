package com.mfangsoft.zhuangjialong.common.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpClientUtil {
	
	
	
	public static  String sendPostForJson(URI uri,  String json,Header header) throws ParseException, IOException{
		
		HttpClient client =   HttpClients.createDefault();
		
		HttpPost   httppost = new  HttpPost();
		
		httppost.setURI(uri);
		
		httppost.setHeader(header);
		
		ObjectMapper mapper = new ObjectMapper();
		StringEntity stringEntity = new StringEntity(json,"UTF-8");
		
		httppost.setEntity(stringEntity);
		
		HttpResponse httpResponse  =client.execute(httppost);
		
		if(httpResponse.getStatusLine().getStatusCode()==200){
			
			HttpEntity httpEntity=httpResponse.getEntity();
			
			String requst=EntityUtils.toString(httpEntity, "UTF-8");
			
			return requst;
			
		}
		return null;
		
	}
	
	public static  String sendPostForJson(URI uri,  String json) throws ParseException, IOException{
		
		HttpClient client =   HttpClients.createDefault();
		
		HttpPost   httppost = new  HttpPost();
		
		httppost.setURI(uri);
		
		StringEntity stringEntity = new StringEntity(json,"UTF-8");
		
		httppost.setEntity(stringEntity);
		
		HttpResponse httpResponse  =client.execute(httppost);
		
		if(httpResponse.getStatusLine().getStatusCode()==200){
			
			HttpEntity httpEntity=httpResponse.getEntity();
			
			String requst=EntityUtils.toString(httpEntity, "UTF-8");
			
			return requst;
			
		}
		return null;
		
	}
	
	
	public static  String sendGetForJson(URI uri) throws ParseException, IOException{
		
		HttpClient client =   HttpClients.createDefault();
		
		HttpGet   httppost = new  HttpGet();
		
		httppost.setURI(uri);
		
		
		
		HttpResponse httpResponse  =client.execute(httppost);
		
		if(httpResponse.getStatusLine().getStatusCode()==200){
			
			HttpEntity httpEntity=httpResponse.getEntity();
			
			String requst=EntityUtils.toString(httpEntity, "UTF-8");
			
			return requst;
			
		}
		return null;
		
	}
	
	
public static  String sendPost(String url,Map<String,String> param) throws ParseException, IOException, URISyntaxException{
	 
	    URI uri = new URI(url); 	
	
		HttpClient client =   HttpClients.createDefault();
		
		HttpPost   httppost = new  HttpPost();
		
		httppost.setURI(uri);
		
		
		List<BasicNameValuePair>  nameValuePairs =new ArrayList<>();
		
		Set<String> keySet = param.keySet();  
		
		
		 for(String key : keySet) {  
			 
			 nameValuePairs.add(new BasicNameValuePair(key, param.get(key)));
		     
		    }  
		
	
		
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
		
		HttpResponse httpResponse  =client.execute(httppost);
		
		if(httpResponse.getStatusLine().getStatusCode()==200){
			
			HttpEntity httpEntity=httpResponse.getEntity();
			
			String requst=EntityUtils.toString(httpEntity, "UTF-8");
			
			return requst;
			
		}
		return null;
		
	}
 
}
