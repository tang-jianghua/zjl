package com.mfangsoft.zhuangjialong.app.helpfeedback.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.helpfeedback.mapper.HelpFeedBackEntityMapper;
import com.mfangsoft.zhuangjialong.app.helpfeedback.model.HelpFeedBackEntity;
import com.mfangsoft.zhuangjialong.app.helpfeedback.service.HelpFeedBackService;
import com.mfangsoft.zhuangjialong.app.personalCenter.mapper.CustomerPointEntityMapper;
import com.mfangsoft.zhuangjialong.app.personalCenter.mapper.MessageEntityMapper;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.CustomerPointEntity;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.CustomerPointType;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.MessageEntity;
import com.mfangsoft.zhuangjialong.common.message.service.impl.SendMessageServiceImpl;
import com.mfangsoft.zhuangjialong.common.utils.MessageConstant;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年7月1日
* 
*/
@Service(value="appHelpFeedBackService")
public class HelpFeedBackServiceImpl implements HelpFeedBackService{
        
	
	@Autowired
	private HelpFeedBackEntityMapper helpFeedBackEntityMapper;
	@Autowired
	CustomerPointEntityMapper customerPointEntityMapper;
	@Autowired
	SendMessageServiceImpl sendMessageServiceImpl;
	@Autowired
	MessageEntityMapper messageEntityMapper;
	
	@Override
	public boolean addFeedBack(HelpFeedBackEntity param) {
		
		if(param.getFeedbacker_type() == 1){
			List<CustomerPointType> list = customerPointEntityMapper.selectPointType();
			CustomerPointType result = null;
			for(CustomerPointType c : list){
				if(c.getType().equals(SysConstant.Qustion_Point_Id)){
					result = c;
				}
			}
			
			CustomerPointEntity customerPointEntity = new CustomerPointEntity();
			customerPointEntity.setCustomer_id(param.getFeedbacker_id());
			customerPointEntity.setName(SysConstant.Qustion_Name);
			customerPointEntity.setTime(new Date());
			customerPointEntity.setType(SysConstant.Qustion_Point_Id);
			customerPointEntity.setPoint(result.getPoint());
			customerPointEntityMapper.insertSelective(customerPointEntity);
			
			MessageEntity messageEntity = new MessageEntity();
			messageEntity.setCustomer_id(param.getFeedbacker_id());
			messageEntity.setType_id(MessageConstant.PointFeedback);
			messageEntity.setParams(result.getPoint().toString());
			messageEntity.setTime(new Date());
			messageEntityMapper.insertSelective(messageEntity);
			
			sendMessageServiceImpl.sendMessage(param.getFeedbacker_id(), result.getPoint());
		}
		
		param.setCreate_date(new Date());
		int i = helpFeedBackEntityMapper.insertSelective(param);
		if(i>0){
			return true;
		}else{
			return false;
		}
	}

}
