package com.mfangsoft.zhuangjialong.common.message.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.applogin.model.UserEquipmentEntity;

@Service
public interface SendMessageService {

	
	List<UserEquipmentEntity> selectAllEquepmentInfoByCustomerId(List<Long> customer_id);
	
	
	
	
}
