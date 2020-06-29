package com.mfangsoft.zhuangjialong.app.crowdfunding;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mfangsoft.zhuangjialong.app.constant.mapper.AppConstantEntityMapper;
import com.mfangsoft.zhuangjialong.app.jumptoweb.service.impl.JumpServiceImpl;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年11月3日
* 
*/
@Controller
@RequestMapping("/app")
public class CrowdFundingController {
	 @Autowired
	 AppConstantEntityMapper appConstantEntityMapper;
	
	@RequestMapping(value = "/getcrowdfundingbutton", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Map<String, Object>> getCrowdFundingButton(@RequestBody Map<String, Object> userData) {
		ResponseMessage<Map<String, Object>> message = new ResponseMessage<>();
			Date start_time = appConstantEntityMapper.selectByKey("2016_11_11_start_time").getValue_date();
			Date end_time = appConstantEntityMapper.selectByKey("2016_11_11_end_time").getValue_date();
			String crowd_funding_url = appConstantEntityMapper.selectByKey("CROWD_FUNDING").getValue_str();
			Date date = new Date();
			Map<String, Object> map =new HashMap<>();
			ObjectMapper o = new ObjectMapper();
			userData.put("native_boolean", true);
			try {
				map.put("url", JumpServiceImpl.BATH_PRE+crowd_funding_url+"?result="+ URLEncoder.encode(o.writeValueAsString(userData), "UTF-8"));
			} catch (UnsupportedEncodingException | JsonProcessingException e) {
				e.printStackTrace();
				message.setCode(SysConstant.FAILURE_CODE);
				message.setMessage(SysConstant.FAILURE_MSG);
				return message;
			}
			if(date.getTime()>start_time.getTime()&&date.getTime()<end_time.getTime()){
				map.put("visible", true);
			}else{
				map.put("visible", false);
			}
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			message.setResult(map);
		return message;
	}
}
