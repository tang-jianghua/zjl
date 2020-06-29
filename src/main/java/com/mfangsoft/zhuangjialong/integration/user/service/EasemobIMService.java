package com.mfangsoft.zhuangjialong.integration.user.service;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;

public interface EasemobIMService {
	
	
	
	Boolean register(Long  id) throws ClientProtocolException, URISyntaxException, IOException;
	
	
	Boolean register(String  account) throws ClientProtocolException, URISyntaxException, IOException;
	
	
	Boolean register(String  account,String pwd) throws ClientProtocolException, URISyntaxException, IOException;
	

}
