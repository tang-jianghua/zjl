package com.mfangsoft.zhuangjialong.app.freeorder.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.app.freeorder.util.FreeOrderUtil;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.RedisUtils;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年11月11日
* 
*/
@Controller
@RequestMapping(value="/app")
public class FreeOrderController {

	
	
	@RequestMapping(value = "/getfreeordertotalnum", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Map<String, Object>> getFreeOrderTotalNum(){
		ResponseMessage<Map<String, Object>> message=new ResponseMessage<>();
		String freeOrderTotalNum = RedisUtils.getValue(FreeOrderUtil.FREE_ORDER_TOTAL_NUM);
		String freeOrderNum = RedisUtils.getValue(FreeOrderUtil.FREE_ORDER_NUM);
		if(freeOrderTotalNum==null){
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}else{
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			Map<String, Object> map = new HashMap<>();
			map.put("totalPrice", Long.parseLong(freeOrderTotalNum));
			map.put("totalPeople", Long.parseLong(freeOrderNum));
			message.setResult(map);
		}
		return message;
	}
	@RequestMapping(value = "/setfreeordertotalnnum", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public boolean setFreeOrderTotalNnum(){
		RedisUtils.setValue(FreeOrderUtil.FREE_ORDER_TOTAL_NUM, FreeOrderUtil.FREE_ORDER_INI_TOTAL_NUM);
		RedisUtils.setValue(FreeOrderUtil.FREE_ORDER_NUM, FreeOrderUtil.FREE_ORDER_INI_NUM);
		return true;
	}
}
