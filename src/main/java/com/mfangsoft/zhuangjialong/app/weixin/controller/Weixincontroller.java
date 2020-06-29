package com.mfangsoft.zhuangjialong.app.weixin.controller;

import java.security.MessageDigest;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mfangsoft.zhuangjialong.app.weixin.model.WeixinReturnValue;
import com.mfangsoft.zhuangjialong.app.weixin.model.WeixinShare;
import com.mfangsoft.zhuangjialong.common.utils.HttpRequest;
import com.mfangsoft.zhuangjialong.common.utils.RedisUtils;
import com.mfangsoft.zhuangjialong.common.utils.StringUtils;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;

@Controller
@RequestMapping("/app/weixin")
public class Weixincontroller {
	
	@RequestMapping(value = "/getJsSign", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Map<String,Object> getJsSign(WeixinShare weixinShare){
		Map<String,Object> json = new HashMap<String,Object>();
		try {
			String url = weixinShare.getUrl();
			String ticket = "";
			String signature= "";
			if (RedisUtils.getValue(SysConstant.WEIXINSHARETICKET) != null) {
				ticket = RedisUtils.getValue(SysConstant.WEIXINSHARETICKET).toString();
			}
			while (StringUtils.isBlank(ticket)) {
				ticket = getJsApiTicket();
			}
			String timestamp=create_timestamp();
			String nonceStr =create_nonce_str();
			// 注意这里参数名必须全部小写，且必须有序
			String string1 = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr + "&timestamp="
					+ timestamp + "&url=" + url;
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
			Map<String,Object> map =new HashMap<String,Object>();
			map.put("timestamp", timestamp);
			map.put("nonceStr", nonceStr);
			map.put("signature", signature);
			json.put("success", true);
			json.put("data", map);
		}
		catch (Exception e){
			e.printStackTrace();
			json.put("success", false);
			json.put("errorMsg", e.toString());
		}
		return json;
	}
	/**
	 * 获取ticket
	 * @return
	 * @throws Exception
	 */
	public String getJsApiTicket() throws Exception {
		String access_token = "";
		if (RedisUtils.getValue(SysConstant.WEIXINSHAREACCESSTOKEN) != null) {
			access_token = RedisUtils.getValue(SysConstant.WEIXINSHAREACCESSTOKEN).toString();
		}
		while (StringUtils.isBlank(access_token)) {
			access_token = getAccessToken();
		}
		String responseContent = HttpRequest.sendGet("https://api.weixin.qq.com/cgi-bin/ticket/getticket",
				"access_token=" + access_token + "&type=jsapi");
		ObjectMapper objectMapper = new ObjectMapper();
		WeixinReturnValue json = objectMapper.readValue(responseContent, WeixinReturnValue.class);
		String ticket = "";
		if (json != null) {
			if (json.getErrcode() == 0) {
				ticket = json.getTicket();
				Long expiresIn = json.getExpires_in();
				RedisUtils.setWithTimeLimit(SysConstant.WEIXINSHARETICKET, ticket, expiresIn);
			}
		}
		return ticket;
	}
	/**
	 * 获取token
	 * @return
	 * @throws Exception
	 */
	public String getAccessToken() throws Exception {
	/*"grant_type=client_credential&appid=wx9cba0cf200210601&secret=f91f74f32fa3ed1f8bfc838b2845ac91");*/
		String responseContent = HttpRequest.sendGet("https://api.weixin.qq.com/cgi-bin/token",
				"grant_type=client_credential&appid=wxebc7f519e90620c9&secret=1bd00b915addd90d2cbc8b3c0853ac23");
		ObjectMapper objectMapper = new ObjectMapper();
		WeixinReturnValue json = objectMapper.readValue(responseContent, WeixinReturnValue.class);
		String accessToken = "";
		if (json != null) {
			if (json.getAccess_token() != null) {
				accessToken = json.getAccess_token();
				Long expiresIn = json.getExpires_in();
				RedisUtils.setWithTimeLimit(SysConstant.WEIXINSHAREACCESSTOKEN, accessToken, expiresIn);
			}
		}
		return accessToken;
	}
	/**
	 * 生成随机串
	 * @return
	 */
	private static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}
	/**
	 * 获取当前时间戳
	 * @return
	 */
	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}
	/**
	 * hex1算法
	 * @param hash
	 * @return
	 */
	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}
}
