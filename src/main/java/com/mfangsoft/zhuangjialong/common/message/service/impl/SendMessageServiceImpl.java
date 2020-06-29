package com.mfangsoft.zhuangjialong.common.message.service.impl;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.applogin.mapper.UserEquipmentEntityMapper;
import com.mfangsoft.zhuangjialong.app.applogin.model.UserEquipmentEntity;
import com.mfangsoft.zhuangjialong.app.order.model.OrderMessage;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.MessageEntity;
import com.mfangsoft.zhuangjialong.common.message.service.SendMessageService;
import com.mfangsoft.zhuangjialong.common.utils.JPushUtil;
import com.mfangsoft.zhuangjialong.common.utils.MessageConstant;
import com.mfangsoft.zhuangjialong.core.quartz.Quest;
import com.mfangsoft.zhuangjialong.core.quartz.QuestsManagerBean;
@Service
public class SendMessageServiceImpl implements SendMessageService {

	@Autowired
	UserEquipmentEntityMapper userEquipmentEntityMapper;

	@Override
	public List<UserEquipmentEntity> selectAllEquepmentInfoByCustomerId(List<Long> customer_id) {
		return userEquipmentEntityMapper.selectAllByCustomerId(customer_id);
	}
	
	public void regbagReceiveMessage(Long customer_id,Double value) {
		
		UserEquipmentEntity userEquipmentEntity = userEquipmentEntityMapper.selectByCustomerId(customer_id);
		if(userEquipmentEntity != null){
			JPushUtil.sendMessage(userEquipmentEntity.getPlatform(), userEquipmentEntity.getPushstr(), MessageConstant.RegbagTitle, 
					MessageFormat.format(MessageConstant.RegbagContent,userEquipmentEntity.getCustomer_name(),value));
			
		}
		
		
		
//		for (OrderMessage o : orderMessageList) {
//				JPushUtil.sendMessage(o.getPlatform(), o.getPushstr(), MessageConstant.orderCanceledTitle,
//						MessageFormat.format(MessageConstant.orderCanceledContent, o.getOrder_code()));
//		}
//		// 添加消息到数据库
//		for (OrderMessage o : orderMessageList) {
//			MessageEntity messageEntity = new MessageEntity();
//			messageEntity.setCustomer_id(o.getCustomer_id());
//			messageEntity.setParams(o.getOrder_code());
//			messageEntity.setType_id(MessageConstant.orderCanceled);
//			messageEntity.setTime(new Date());
//			messageEntity.setImgurl(o.getImgurl());
//			messageEntityMapper.insertSelective(messageEntity);
//		}
	}
	
	public void sendMessage(Long customer_id,int point){
		
		final UserEquipmentEntity userEquipmentEntity = userEquipmentEntityMapper.selectByCustomerId(customer_id);
		if(userEquipmentEntity != null){
			QuestsManagerBean.addQuest(new Quest() {

				@Override
				public boolean execute() {
					JPushUtil.sendMessage(userEquipmentEntity.getPlatform(), userEquipmentEntity.getPushstr(),
							MessageConstant.PointTitle, MessageFormat.format(MessageConstant.PointContent, point));
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
		
		}
	}

}
