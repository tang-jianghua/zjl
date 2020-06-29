package com.mfangsoft.zhuangjialong.app.constructAppointment.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.applogin.mapper.CustomerAddressMapper;
import com.mfangsoft.zhuangjialong.app.applogin.model.CustomerAddress;
import com.mfangsoft.zhuangjialong.app.constructAppointment.mapper.ConstructAppointmentMapper;
import com.mfangsoft.zhuangjialong.app.constructAppointment.mapper.ConstructStateRelationMapper;
import com.mfangsoft.zhuangjialong.app.constructAppointment.model.ConstructAppointment;
import com.mfangsoft.zhuangjialong.app.constructAppointment.model.ConstructAppointmentModel;
import com.mfangsoft.zhuangjialong.app.constructAppointment.model.ConstructStateRelation;
import com.mfangsoft.zhuangjialong.app.constructAppointment.service.ConstructAppointmentService;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.integration.region.service.RegionService;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年7月4日
* 
*/
@Service
public class ConstructAppointmentServiceImpl implements ConstructAppointmentService{


	@Autowired
	private ConstructAppointmentMapper constructAppointmentMapper;
	@Autowired
	private ConstructStateRelationMapper constructStateRelationMapper;
	@Autowired
	private RegionService regionServiceImpl;
	@Autowired
	CustomerAddressMapper customerAddressMapper;
	
	@Override
	public boolean addConstructAppointment(ConstructAppointment param) {
		param.setCreate_time(new Date());
		if(param.getConstruct_id()==null){
			return false;
		}
		if(param.getAddress_id() == null){
			return false;
		}
		
		CustomerAddress address = customerAddressMapper.selectByPrimaryKey(param.getAddress_id());
		param.setAddress_details(address.getAddress_details());
		param.setRegion_code(address.getRegion_code());
		param.setReceiver_name(address.getReceiver_name());
		param.setPhone_num(address.getPhone_num());
		
		int i = constructAppointmentMapper.insertSelective(param);
		ConstructStateRelation constructStateRelation = new ConstructStateRelation();
		constructStateRelation.setConstruct_appointment_id(param.getId());
		constructStateRelation.setCreate_time(new Date());
		constructStateRelation.setState_code("1001");
		int j = constructStateRelationMapper.insertSelective(constructStateRelation);
		if(i > 0 && j > 0){
			return true;
		}else{
			return false;
		}
	}
	
	
	
	@Override
	public Page<ConstructAppointmentModel> selectConstructAppointmentByCustomerIdForPage(Page<ConstructAppointmentModel> param) {
		
		List<ConstructAppointmentModel> constructAppointments = constructAppointmentMapper.selectConstructAppointmentByCustomerIdForPage(param);
		for (ConstructAppointmentModel constructAppointmentModel : constructAppointments) {
		 ConstructStateRelation constructStateRelation = constructStateRelationMapper.selectStateByAppointmentId(constructAppointmentModel.getId());
		 constructAppointmentModel.setState_code(constructStateRelation.getState_code());
		 constructAppointmentModel.setState(constructStateRelation.getState());
/*			if("1001".equals(constructStateRelation.getState_code())||"5001".equals(constructStateRelation.getState_code())||"6001".equals(constructStateRelation.getState_code())){
				constructAppointmentModel.setTotal_price(null);
			}
*/		}
		param.setData(constructAppointments);
		param.setParam(null);
		return param;
	}

	
	@Override
	public boolean updateConstruct(ConstructStateRelation param) {
		int i = constructStateRelationMapper.selectWhetherCancleByConstructAppointmentId(param);
		if(i>0){
			return false;
		}
		param.setCreate_time(new Date());
        param.setState_code("5001");
		if(constructStateRelationMapper.insertSelective(param)>0){
		return true;
		}else{
	    return false;
		}
	}


	@Override
	public boolean addCommentConstruct(ConstructAppointment param) {
		ConstructAppointment constructAppointment = constructAppointmentMapper.selectByPrimaryKey(param.getId());
		if(constructAppointment.getScore() != null){
			return false;
		}
		param.setEvaluate_time(new Date());
		
		int i = constructAppointmentMapper.updateByPrimaryKeySelective(param);
		 if(i==0){
        	 return false;
         }
		 
		if(param.getEvaluate_imgs().size()>0){
			int j = constructAppointmentMapper.addEvaImgs(param);
         if(j==0){
        	 return false;
         }
		}
		ConstructStateRelation record = new ConstructStateRelation();
		record.setConstruct_appointment_id(constructAppointment.getId());
		record.setCreate_time(new Date());
		record.setState_code("4001");
		int k = constructStateRelationMapper.insertSelective(record);
		 if(k ==0){
        	 return false;
         }
		 return true;
	}



	@Override
	public Page<ConstructAppointment> queryConstructEvaluation(Page<ConstructAppointment> param) {
		
		List<ConstructAppointment> evaluation = constructAppointmentMapper.selectEvaluationOfConstructerForPage(param);
		for (ConstructAppointment constructAppointment : evaluation) {
			constructAppointment.setEvaluate_imgs(constructAppointmentMapper.selectEvaluationImages(constructAppointment.getId()));
		}
		param.setParam(null);
		param.setData(evaluation); 
		return param;
	}
}
