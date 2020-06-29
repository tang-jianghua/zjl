package com.mfangsoft.zhuangjialong.integration.user.service.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mfangsoft.zhuangjialong.common.utils.HttpClientUtil;
import com.mfangsoft.zhuangjialong.integration.user.mapper.UserEntityMapper;
import com.mfangsoft.zhuangjialong.integration.user.model.UserEntity;
import com.mfangsoft.zhuangjialong.integration.user.service.EasemobIMService;
@Service
public class EasemobIMServiceImpl implements  EasemobIMService{

	
	@Autowired
	private UserEntityMapper  userEntityMapper;
	
	
	private final static String IM_PW="123456";
	
	private static String IM_REG_HEADER="Bearer ";
	
	
	private  final static String CLIENT_ID="YXA6v8x5wJDoEea53IlihRju4w";
	
	private  final static String CLIENT_SECRET ="YXA6yZjQzcSJpr1i1f5N5OZueGRwm7U";
	
	private  final static String SEND_REG_URL="https://a1.easemob.com/1105161013115969/zhuangjialong/users";
	
	
	private  final static String SEND_TOKEN_URL="https://a1.easemob.com/1105161013115969/zhuangjialong/token";
	
	
	@Override
	public Boolean register(Long id) throws ClientProtocolException, URISyntaxException, IOException {
		// TODO Auto-generated method stub
		UserEntity userEntity=userEntityMapper.selectByPrimaryKey(id);
		
		if(userEntity.getIs_seat().intValue()==1){
			
			
			String token=getToken();
			
			if(token!=null){
				
				if(registerEasemob(userEntity.getAccount(),IM_PW,token)!=null){
					
					return  true;
				}
			}
			
			
		}
		return false;
	}
	
	
	
	private String registerEasemob(String account,String password,String token) throws URISyntaxException, ClientProtocolException, IOException {
		
		
		URI uri = new URI(SEND_REG_URL);
		
		ObjectMapper mapper = new ObjectMapper();
		
		Map<String,Object> map= new HashMap<>();
		
		map.put("username", account);
		
		map.put("password", password);
		
		Header  header = new BasicHeader("Authorization",IM_REG_HEADER+token);
		
		return HttpClientUtil.sendPostForJson(uri, mapper.writeValueAsString(map), header);
	}
	
	
	private String getToken() throws ParseException, JsonProcessingException, IOException, URISyntaxException{
		
		
		URI uri = new URI(SEND_TOKEN_URL);
		
		ObjectMapper mapper = new ObjectMapper();
		
		Map<String,Object> map= new HashMap<>();
		
		map.put("grant_type", "client_credentials");
		
		map.put("client_id", CLIENT_ID);
		map.put("client_secret", CLIENT_SECRET);
		
		String str=HttpClientUtil.sendPostForJson(uri, mapper.writeValueAsString(map));
		
		if(str!=null){
			
			Map<String,Object> resultMap=mapper.readValue(str, Map.class);
			
			return  (String) resultMap.get("access_token");
			
		}
		return null;
	}

	
	public static void main(String[] args) throws ClientProtocolException, URISyntaxException, IOException {
		
		
		EasemobIMServiceImpl easemobIMServiceImpl= new EasemobIMServiceImpl();
		
		easemobIMServiceImpl.register("abc");
	}


	@Override
	public Boolean register(String account) throws ClientProtocolException, URISyntaxException, IOException {
		// TODO Auto-generated method stub
		
		String token=getToken();
		
		if(token!=null){
			
			if(registerEasemob(account,IM_PW,token)!=null){
				
				return  true;
			}
		}
		
		
		return false;
	}



	@Override
	public Boolean register(String account, String pwd)
			throws ClientProtocolException, URISyntaxException, IOException {
		// TODO Auto-generated method stub
		String token=getToken();
		
		if(token!=null){
			
			if(registerEasemob(account,pwd,token)!=null){
				
				return  true;
			}
		}
		
		return false;
	}



	

}
