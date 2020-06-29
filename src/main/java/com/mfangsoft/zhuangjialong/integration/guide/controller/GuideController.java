package com.mfangsoft.zhuangjialong.integration.guide.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.extension.model.Extension;
import com.mfangsoft.zhuangjialong.integration.guide.model.Guide;

/**
 * 推广导购
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/server")
public class GuideController {
	
	/**
	 * 推广导购查询
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/queryguide",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Page<Guide>> queryGuide(@RequestBody Page<Guide> page)
	
	{
		ResponseMessage<Page<Guide>> message = new ResponseMessage<Page<Guide>>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		//message.setResult(enterprise);
		List<Guide> guides = new ArrayList<Guide>();
		Guide guide = new Guide();
		guide.setName("李");
		guide.setCreate_time(new Date());
		
		guide.setPhonenum("19828749450");
		
		guide.setExtension_name("李");
		
		guide.setState(1);
		
		guide.setExtend_count(100);
		
		guide.setIncome(new Double(20000.00));
		guide.setUser_account("1982874945");
		guides.add(guide);
		//extensions.add(partner2);
		page.setData(guides);
		message.setResult(page);
		
		return  message;
		
	}

}
