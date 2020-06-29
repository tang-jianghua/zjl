package com.mfangsoft.zhuangjialong.app.personalCenter.service;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.mfangsoft.zhuangjialong.app.applogin.model.Customer;
import com.mfangsoft.zhuangjialong.app.applogin.model.CustomerAddress;
import com.mfangsoft.zhuangjialong.app.applogin.model.CustomerEntity;
import com.mfangsoft.zhuangjialong.app.appointment.model.Appointment;
import com.mfangsoft.zhuangjialong.app.appointment.model.AppointmentInfoEntity;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.Point;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;

@Service
public interface PersonInformationService {

	ResponseMessage<String> modifycustomerinfo(CustomerEntity customerEntity, HttpServletRequest request);
			
	List<CustomerAddress> selectByCustomerId(CustomerAddress customerAddress);
	
	List<CustomerAddress> selectByCustomerIdAndRegionCode(Long customer_id,String region_code);
	
	ResponseMessage modifyphonenumber(Customer c);
		
	ResponseMessage addemail( Customer c);
	
	ResponseMessage modifyemail( Customer c);
	
	ResponseMessage modifypassword(Customer c);
	
	ResponseMessage<String> addAddress(CustomerAddress value);
	
	ResponseMessage modifyAddress(CustomerAddress address);
	
	CustomerAddress selectById(Long id);
	
	int insert(CustomerAddress record);
	
	int updateStateByCustomerId(CustomerAddress record);
	
	int updateByPrimaryKeySelective(CustomerAddress record);
	
	int deleteByPrimaryKeyAndCustomerId(Long id, Long customer_id);
	
	Integer selectInvitCount(Long customer_id);
	
	List<Appointment> selectAppointmentByCustomerId(Long customer_id) throws ParseException;
	
	Appointment selectDatilByAppointmentInfoId(Long id);
	
	int cancelappointment(Long id);
	
	void requestvcodebyemail(Long id,String email);
	
	void addsharepoint(Point point);
}
