package com.mfangsoft.zhuangjialong.app.constructAppointment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mfangsoft.zhuangjialong.app.constructAppointment.mapper.ConstructAppointmentMapper;
import com.mfangsoft.zhuangjialong.app.constructAppointment.model.ConstructAppointment;
import com.mfangsoft.zhuangjialong.app.constructAppointment.model.ConstructAppointmentModel;
import com.mfangsoft.zhuangjialong.app.constructAppointment.model.ConstructStateRelation;
import com.mfangsoft.zhuangjialong.common.model.Page;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年7月4日
* 
*/

public interface ConstructAppointmentService {
    
    /**
	* @description：添加施工预约
	* @param：param
	*/
	public boolean addConstructAppointment(ConstructAppointment param);
	
	/**
	* @description：查询消费者的施工列表
	* @param：param
	*/
	public Page<ConstructAppointmentModel> selectConstructAppointmentByCustomerIdForPage(Page<ConstructAppointmentModel> param);

	/**
	* @description：取消施工预约
	* @param：param
	*/
	public boolean updateConstruct(ConstructStateRelation param);
	
	
	/**
	* @description：施工预约评分
	* @param：param
	*/
	public boolean addCommentConstruct(ConstructAppointment param);
	
	/**
	* @description：查询施工的评价列表
	* @param：param
	*/
	public Page<ConstructAppointment> queryConstructEvaluation(Page<ConstructAppointment> param);
	
}
