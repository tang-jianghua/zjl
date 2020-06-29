package com.mfangsoft.zhuangjialong.app.jumptoweb.controller;


import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mfangsoft.zhuangjialong.app.constant.mapper.AppConstantEntityMapper;
import com.mfangsoft.zhuangjialong.app.constant.model.AppConstantEntity;
import com.mfangsoft.zhuangjialong.app.jumptoweb.model.ParameterModel;
import com.mfangsoft.zhuangjialong.app.jumptoweb.service.JumpService;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.RedisUtils;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年7月14日
* 
*/
@Controller
@RequestMapping("/app")
public class JumpController {
	
	@Autowired
	JumpService jumpServiceImpl;
	@Autowired
	AppConstantEntityMapper appConstantEntityMapper;
	
	@RequestMapping(value = "/jumptoweb", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Map<String, Object>> jumpToWeb(@RequestBody ParameterModel param) throws UnsupportedEncodingException {
		ResponseMessage<Map<String, Object>> message = new ResponseMessage<>();
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			try {
				message.setResult(jumpServiceImpl.selectByType(param));
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return message;
	}
	/**
	 * 短信短链接重定向到指定链接
	 * @return 
	 * @return
	 */
	@RequestMapping(value="/R/{key}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public void downloadLinkRedirect(HttpServletResponse response,@PathVariable String key) throws Exception{
		AppConstantEntity appConstantEntity = appConstantEntityMapper.selectByKey(key);
		if(appConstantEntity.getValue_str()!=null){
		response.sendRedirect(appConstantEntity.getValue_str());
		}
	}
	
	
	/**
	 * 短信短链接重定向到指定链接
	 * @return 
	 * @return
	 */
	@RequestMapping(value="/jumpconnnection",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> jumpConnnection(@RequestBody ParameterModel param) throws UnsupportedEncodingException {
		ResponseMessage<String> message = new ResponseMessage<>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		try {
			message.setResult(jumpServiceImpl.makeUrlByType(param));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return message;
}
}
