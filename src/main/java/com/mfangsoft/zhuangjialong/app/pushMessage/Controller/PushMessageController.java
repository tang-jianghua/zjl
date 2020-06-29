package com.mfangsoft.zhuangjialong.app.pushMessage.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.app.pushMessage.model.PushModel;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.JPushUtil;
import com.mfangsoft.zhuangjialong.core.quartz.Quest;
import com.mfangsoft.zhuangjialong.core.quartz.QuestsManagerBean;


/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年9月28日
* 
*/
@RequestMapping(value="/app")
@Controller
public class PushMessageController {
   
	
	@RequestMapping(value="/pushmessage")
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> pushMessage(@RequestBody PushModel pushModel) {
		ResponseMessage<String> message = new ResponseMessage<>();
		QuestsManagerBean.addQuest(new Quest() {
			
			@Override
			public boolean execute() {
				JPushUtil.sendMessage(pushModel.getPlatType(), pushModel.getMobileNo(),
						pushModel.getTitle(), pushModel.getContent());
				return true;
			}
			
			@Override
			public boolean condition() {
				return true;
			}
			
			@Override
			public boolean delete() {
				return true;
			}
		});
		return message;
	}
	@RequestMapping(value="/pushsellermessage")
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> pushSellerMessage(@RequestBody PushModel pushModel) {
		ResponseMessage<String> message = new ResponseMessage<>();
		QuestsManagerBean.addQuest(new Quest() {

			@Override
			public boolean execute() {
				JPushUtil.sendSellerMessage(pushModel.getPlatType(), pushModel.getMobileNo(),
						pushModel.getTitle(), pushModel.getContent());
				return true;
			}

			@Override
			public boolean condition() {
				return true;
			}

			@Override
			public boolean delete() {
				return true;
			}
		});
		return message;
	}
	
}
