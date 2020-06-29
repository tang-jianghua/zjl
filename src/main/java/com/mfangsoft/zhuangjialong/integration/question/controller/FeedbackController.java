package com.mfangsoft.zhuangjialong.integration.question.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.app.helpfeedback.model.HelpFeedBackEntity;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.coupons.model.BrandCouponsEntity;
import com.mfangsoft.zhuangjialong.integration.question.service.FeedbackService;

@Controller
@RequestMapping("/server")
public class FeedbackController {
	
	
	@Autowired
	private FeedbackService feedbackService;
	@RequestMapping(value="/queryfeedback",method=RequestMethod.POST)
	@ResponseBody
	public Page<Map<String,Object>> getHelpFeedBackForPage(@RequestBody Page<Map<String,Object>> page){
		return feedbackService.getHelpFeedBackForPage(page);
	}
	@RequestMapping(value="/sendRedBag",method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<String>  sendRedBag(@RequestBody   Map<String,Object> map ){
		
		
		ResponseMessage<String> message = new ResponseMessage<>();
		if(map.get("feedback_id")==null){
			
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			return message;
		}
		
		if(map.get("coupons")==null){
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			return message;
		}
		
		 
		 try {
			
			 feedbackService.sendCoupons(new Integer(map.get("feedback_id").toString()), ((BrandCouponsEntity)map.get("coupons")));
			 message.setCode(SysConstant.SUCCESS_CODE);
		     message.setMessage(SysConstant.SUCCESS_MSG);
			 
			 
			 
		} catch (Exception e) {
			// TODO: handle exception
			
			 message.setCode(SysConstant.FAILURE_CODE);
		     message.setMessage(e.getMessage());
		}
		 return message;
	 }
	    
	@RequestMapping(value="/sendCoupons",method=RequestMethod.POST)
	@ResponseBody   
	public ResponseMessage<String>   sendCoupons(@RequestBody   Map<String,Object> map){
		 
		ResponseMessage<String> message = new ResponseMessage<>();
		if(map.get("feedback_id")==null){
			
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			return message;
		}
		
		if(map.get("coupons")==null){
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			return message;
		}
		
		 
		 try {
			
			 feedbackService.sendCoupons(new Integer(map.get("feedback_id").toString()), ((BrandCouponsEntity)map.get("coupons")));
			 message.setCode(SysConstant.SUCCESS_CODE);
		     message.setMessage(SysConstant.SUCCESS_MSG);
			 
			 
			 
		} catch (Exception e) {
			// TODO: handle exception
			
			 message.setCode(SysConstant.FAILURE_CODE);
		     message.setMessage(e.getMessage());
		}
		 return message;

}
}
