package com.mfangsoft.zhuangjialong.app.appointment.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.applogin.mapper.CustomerAddressMapper;
import com.mfangsoft.zhuangjialong.app.applogin.model.CustomerAddress;
import com.mfangsoft.zhuangjialong.app.appointment.mapper.AppointmentInfoEntityMapper;
import com.mfangsoft.zhuangjialong.app.appointment.mapper.AppointmentMapper;
import com.mfangsoft.zhuangjialong.app.appointment.model.Appointment;
import com.mfangsoft.zhuangjialong.app.appointment.model.AppointmentInfo;
import com.mfangsoft.zhuangjialong.app.appointment.model.AppointmentInfoEntity;
import com.mfangsoft.zhuangjialong.app.appointment.model.AppointmentStateInfo;
import com.mfangsoft.zhuangjialong.app.appointment.service.AppointmentService;
import com.mfangsoft.zhuangjialong.app.buildClass.model.BuildClass;
import com.mfangsoft.zhuangjialong.app.product.model.SelectPropertiesModel;
import com.mfangsoft.zhuangjialong.app.seller.mapper.SellerEntityMapper;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.DateUtils;
import com.mfangsoft.zhuangjialong.integration.appointment.util.AppointmentState;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年7月7日
* 
*/
@Service(value="appAppointment")
public class AppointmentServiceImpl implements AppointmentService{
    
	
	@Autowired
	private AppointmentMapper appointmentMapper;
	@Autowired
	private AppointmentInfoEntityMapper appointmentInfoEntityMapper;
	@Autowired
	private CustomerAddressMapper customerAddressMapper;
   @Autowired
   private SellerEntityMapper sellerEntityMapper;
	
	
	@Override
	public SelectPropertiesModel getStyleAndSpaceByClass(BuildClass param) {
	
		SelectPropertiesModel selectPropertiesModel = new SelectPropertiesModel();
		selectPropertiesModel.setSpaces(appointmentMapper.selectSpacesByClassId(param));
	
		selectPropertiesModel.setStyles(appointmentMapper.selectStylesByClassId(param));
		
		return selectPropertiesModel;
	}
	
	
	@Override
	public boolean insertAppointment(final Appointment appointment) {
		CustomerAddress customerAddress = customerAddressMapper.selectByPrimaryKey(appointment.getAddress_id());
		if(customerAddress==null){
			return false;
		}
		appointment.setAddress_details(customerAddress.getAddress_details());
		appointment.setReceiver_name(customerAddress.getReceiver_name());
		appointment.setRegion_code(customerAddress.getRegion_code());
		appointment.setPhone_num(customerAddress.getPhone_num());
			appointment.setCreate_time(new Date());
			int insert = appointmentMapper.insertAppointment(appointment);
			if(insert==0){
				return false;
			}
		for (int i = 0; i < appointment.getAppointmentInfos().size(); i++) {
			AppointmentInfo appointmentInfo = appointment.getAppointmentInfos().get(i);
			appointmentInfo.setAppointment_id(appointment.getId());
			appointmentInfo.setState(1);
			appointmentInfo.setDeal_info(AppointmentState.state1 +  DateUtils.formatDate_(appointment.getCreate_time()));
			if(appointmentInfo.getStart_time() == null){
				return false;
			}
			
			if(appointmentInfoEntityMapper.insertSelective(appointmentInfo)==0){
				return false;
			}
		}
		
		return true;
	}


	@Override
	public Page<Appointment> selectAppointmentsForShopGuider(Page<Appointment> page) {
		List<Appointment> list = appointmentMapper.selectAppointmentsByServerIdAndStateForPage(page);
		if(list.size()>0){
			Set<Long> ids = new HashSet<>();
			for (Appointment appointment : list) {
				if(appointment.getSeen_state() == 0){
				ids.add(appointment.getId());
				}
			}
			if(ids.size()>0){
			appointmentMapper.updateSeenStateByIds(ids);
			}
		}
		page.setParam(null);
		page.setData(list);
		return page;
	}



	@Override
	public Appointment selectAppointmentDetailForShopGuider(Appointment appointment) {
		Appointment result = appointmentMapper.selectAppointmentdetailForShopGuider(appointment.getAppointment_info_id());
		if(result!=null){
			result.setDeal_info_array(result.getDeal_info().split(AppointmentState.splitChar));
			result.setDeal_info(null);
		}
		List<AppointmentStateInfo> statesList = new ArrayList<AppointmentStateInfo>();
		if (result.getDeal_info_array() != null) {
			for (int i = 0; i < result.getDeal_info_array().length; i++) {
				String t = result.getDeal_info_array()[i];
				AppointmentStateInfo state = new AppointmentStateInfo();
				String[] str = t.split("T");
				if (str.length > 0) {
					state.setState(str[0]);
				}
				if (str.length > 1) {
					state.setTime(str[1]);
				}
				statesList.add(state);
			}
			Collections.sort(statesList);
		}
		result.setStates(statesList.size() > 0 ? statesList : null);
		result.setDeal_info_array(null);
		return result;
	}

}
