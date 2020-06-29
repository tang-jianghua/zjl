package com.mfangsoft.zhuangjialong.integration.prepayment.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.prepayment.model.PrepayValue;
import com.mfangsoft.zhuangjialong.integration.prepayment.service.PrepayValueService;

@Controller
@RequestMapping("/prepayvalue")
public class PrepayValueController {
	@Autowired
	PrepayValueService prepayValueService;
	
	/**
	 * 返回全部定金值 150;300;500;800;1000
	 * @return
	 */
	@RequestMapping(value="/getprepayvaluelist",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<PrepayValue>> prePayValueList(){
		ResponseMessage<List<PrepayValue>> message = new ResponseMessage<List<PrepayValue>>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(prepayValueService.selectAll());
		return message;
	}
}
