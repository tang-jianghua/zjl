package com.mfangsoft.zhuangjialong.app.personalCenter.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.mfangsoft.zhuangjialong.app.applogin.mapper.CustomerAddressMapper;
import com.mfangsoft.zhuangjialong.app.applogin.mapper.CustomerEntityMapper;
import com.mfangsoft.zhuangjialong.app.applogin.model.Customer;
import com.mfangsoft.zhuangjialong.app.applogin.model.CustomerAddress;
import com.mfangsoft.zhuangjialong.app.applogin.model.CustomerEntity;
import com.mfangsoft.zhuangjialong.app.appointment.mapper.AppointmentInfoEntityMapper;
import com.mfangsoft.zhuangjialong.app.appointment.mapper.AppointmentMapper;
import com.mfangsoft.zhuangjialong.app.appointment.model.Appointment;
import com.mfangsoft.zhuangjialong.app.appointment.model.AppointmentInfoEntity;
import com.mfangsoft.zhuangjialong.app.appointment.model.AppointmentStateInfo;
import com.mfangsoft.zhuangjialong.app.article.mapper.ArticleEntityMapper;
import com.mfangsoft.zhuangjialong.app.personalCenter.mapper.CustomerPointEntityMapper;
import com.mfangsoft.zhuangjialong.app.personalCenter.mapper.MessageEntityMapper;
import com.mfangsoft.zhuangjialong.app.personalCenter.mapper.PointEntityMapper;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.CustomerPointEntity;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.CustomerPointType;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.MessageEntity;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.Point;
import com.mfangsoft.zhuangjialong.app.personalCenter.service.PersonInformationService;
import com.mfangsoft.zhuangjialong.common.message.service.impl.SendMessageServiceImpl;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.DateUtils;
import com.mfangsoft.zhuangjialong.common.utils.ImgUtil;
import com.mfangsoft.zhuangjialong.common.utils.MD5Util;
import com.mfangsoft.zhuangjialong.common.utils.MessageConstant;
import com.mfangsoft.zhuangjialong.common.utils.NumberUtil;
import com.mfangsoft.zhuangjialong.common.utils.RedisUtils;
import com.mfangsoft.zhuangjialong.common.utils.SendMail;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.core.quartz.Quest;
import com.mfangsoft.zhuangjialong.core.quartz.QuestsManagerBean;
import com.mfangsoft.zhuangjialong.integration.appointment.util.AppointmentState;
import com.mfangsoft.zhuangjialong.integration.brand.mapper.BrandEntityMapper;

@Service
public class PersonInformationServiceImpl implements PersonInformationService {

	@Autowired
	CustomerAddressMapper customerAddressMapper;
	@Autowired
	CustomerEntityMapper customerEntityMapper;
	@Autowired
	AppointmentMapper appointmentMapper;
	@Autowired
	AppointmentInfoEntityMapper appointmentInfoEntityMapper;
	@Autowired
	BrandEntityMapper brandEntityMapper;
	@Autowired
	ArticleEntityMapper articleEntityMapper;
	@Autowired
	PointEntityMapper pointEntityMapper;
	@Autowired
	CustomerPointEntityMapper customerPointEntityMapper;
	@Autowired
	SendMessageServiceImpl sendMessageServiceImpl;
	@Autowired
	MessageEntityMapper messageEntityMapper;
	
	public ResponseMessage<String> modifycustomerinfo(CustomerEntity customerEntity, HttpServletRequest request){
		ResponseMessage<String> responseMessage = new ResponseMessage<String>();
		try {
			String savePath = "";
			String imgBase64 = customerEntity.getImgBase64();
			if(!StringUtils.isEmpty(imgBase64)){
				savePath = ImgUtil.saveBase64Img(imgBase64);
				customerEntity.setHead_url(savePath);
			}else{
				savePath =  customerEntity.getHead_url();
			}
			if (customerEntityMapper.updateByPrimaryKeySelective(customerEntity) > 0) {
				responseMessage.setCode(SysConstant.SUCCESS_CODE);
				responseMessage.setMessage(SysConstant.SUCCESS_MSG);
				responseMessage.setResult(savePath);
				return responseMessage;
			} else {
				responseMessage.setCode(SysConstant.FAILURE_CODE);
				responseMessage.setMessage(SysConstant.FAILURE_MSG);
				return responseMessage;
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
			return responseMessage;
		}
		
		
	}
			
	public ResponseMessage modifyphonenumber(Customer c) {
		
		ResponseMessage responseMessage = new ResponseMessage<>();
		
		if(customerEntityMapper.selectByAccount(c.getNew_phone()) != null){
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage("此手机号已被注册");
			return responseMessage;
		}
		
		CustomerEntity ce= customerEntityMapper.selectByPrimaryKey(c.getCustomer_id());
		
		String vcode1 = RedisUtils.getValue(ce.getAccount());//手机验证码
		String vcode2 = RedisUtils.getValue(SysConstant.CUSTOMER_VCODE + c.getCustomer_id());//邮件验证码
		
		if (c.getVcode().equals(vcode1) || c.getVcode().equals(vcode2)) {
			CustomerEntity customerEntity = new CustomerEntity();
			customerEntity.setId(c.getCustomer_id());
			customerEntity.setPhone(c.getNew_phone());
			customerEntity.setAccount(c.getNew_phone());
			if (customerEntityMapper.updateByPrimaryKeySelective(customerEntity) > 0) {
				responseMessage.setCode(SysConstant.SUCCESS_CODE);
				responseMessage.setMessage(SysConstant.SUCCESS_MSG);
				return responseMessage;
			}
		}
		responseMessage.setCode(SysConstant.FAILURE_CODE);
		responseMessage.setMessage(SysConstant.FAILURE_MSG);
		return responseMessage;
	}
			
	public ResponseMessage addemail(Customer c){
		
		ResponseMessage responseMessage = new ResponseMessage<>();

		if (c.getVcode().equals(RedisUtils.getValue(SysConstant.CUSTOMER_VCODE + c.getCustomer_id().toString()))) {
			CustomerEntity customerEntity = new CustomerEntity();
			customerEntity.setId(c.getCustomer_id());
			customerEntity.setEmail(c.getEmail());

			if (customerEntityMapper.updateByPrimaryKeySelective(customerEntity) > 0) {
				responseMessage.setCode(SysConstant.SUCCESS_CODE);
				responseMessage.setMessage(SysConstant.SUCCESS_MSG);
				return responseMessage;
			}
		}
		responseMessage.setCode(SysConstant.FAILURE_CODE);
		responseMessage.setMessage(SysConstant.FAILURE_MSG);
		return responseMessage;
	}
	
	public ResponseMessage modifyemail(Customer c){
		ResponseMessage responseMessage = new ResponseMessage<>();

		if (c.getVcode().equals(RedisUtils.getValue(SysConstant.CUSTOMER_VCODE + c.getCustomer_id().toString()))) {
			CustomerEntity customerEntity = new CustomerEntity();
			customerEntity.setId(c.getCustomer_id());
			customerEntity.setEmail(c.getNew_email());

			if (customerEntityMapper.updateByPrimaryKeySelective(customerEntity) > 0) {
				responseMessage.setCode(SysConstant.SUCCESS_CODE);
				responseMessage.setMessage(SysConstant.SUCCESS_MSG);
				return responseMessage;
			}
		}
		responseMessage.setCode(SysConstant.FAILURE_CODE);
		responseMessage.setMessage(SysConstant.FAILURE_MSG);
		return responseMessage;
		
	}
	
	public ResponseMessage modifypassword(Customer c){
		
		ResponseMessage responseMessage = new ResponseMessage<>();

		CustomerEntity e = customerEntityMapper.selectByPrimaryKey(c.getCustomer_id());
		if (e.getPassword().equals(MD5Util.MD5(c.getNew_pwd()))) {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.MODIFY_PSW_1);
			return responseMessage;
		}
		if (e.getPassword().equals(MD5Util.MD5(c.getPwd()))) {
			CustomerEntity customerEntity = new CustomerEntity();
			customerEntity.setId(c.getCustomer_id());
			customerEntity.setPassword(MD5Util.MD5(c.getNew_pwd()));

			if (customerEntityMapper.updateByPrimaryKeySelective(customerEntity) > 0) {
				responseMessage.setCode(SysConstant.SUCCESS_CODE);
				responseMessage.setMessage(SysConstant.SUCCESS_MSG);
				return responseMessage;
			}
		}
		responseMessage.setCode(SysConstant.FAILURE_CODE);
		responseMessage.setMessage(SysConstant.MODIFY_PSW);
		
		return responseMessage;
	}
	
	public ResponseMessage<String> addAddress(CustomerAddress value){
		
		ResponseMessage<String> responseMessage = new ResponseMessage<>();
		List<CustomerAddress> list = customerAddressMapper.selectByCustomerId(value.getCustomer_id());
		if(list == null){
			value.setState(true);
		}
		//如果是默认地址首先更新数据库地址为非默认
		if(value.getState()){
			CustomerAddress c = new CustomerAddress();
			c.setCustomer_id(value.getCustomer_id());
			c.setState(false);
			updateStateByCustomerId(c);
		}
		
		if(insert(value) > 0){
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			return responseMessage;
		}
		responseMessage.setCode(SysConstant.FAILURE_CODE);
		responseMessage.setMessage(SysConstant.FAILURE_MSG);
		return responseMessage;
		
	}
	
	public ResponseMessage modifyAddress(@RequestBody CustomerAddress address){
		
		ResponseMessage responseMessage = new ResponseMessage();
		
		//如果是默认地址首先更新数据库地址为非默认
		if(address.getState()){
			CustomerAddress c = new CustomerAddress();
			c.setCustomer_id(address.getCustomer_id());
			c.setState(false);
			updateStateByCustomerId(c);
		}
		
		if(updateByPrimaryKeySelective(address) > 0){
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			return responseMessage;
		}
		responseMessage.setCode(SysConstant.FAILURE_CODE);
		responseMessage.setMessage(SysConstant.FAILURE_MSG);
		return responseMessage;
		
	}
	
	public List<CustomerAddress> selectByCustomerId(CustomerAddress customerAddress) {

		List<CustomerAddress> customerAddressList = customerAddressMapper
				.selectByCustomerId(customerAddress.getCustomer_id());
		List<CustomerAddress> cList = new ArrayList<CustomerAddress>();
		if (customerAddress.getRegion_code() != null) {
			for (CustomerAddress c : customerAddressList) {
				cList.add(c);
				String code = customerAddress.getRegion_code().substring(0, 4);// 参数
				String code1 = c.getRegion_code().substring(0, 4);// 数据
				if (code1.equals(code)) {
					c.setIsselfregion_area(1);
				} else {
					c.setIsselfregion_area(0);
				}
				if (customerAddress.getRegion_code().endsWith("0000")) {
					if (c.getRegion_code().substring(0, 2).equals(customerAddress.getRegion_code().substring(0, 2))) {
						c.setIsselfregion_area(1);
					}
				}
			}

		} else {
			cList = customerAddressList;
		}
		return cList;
	}

	public List<CustomerAddress> selectByCustomerIdAndRegionCode(Long customer_id, String region_code) {
		return customerAddressMapper.selectByCustomerIdAndRegionCode(customer_id, region_code);
	}

	public CustomerAddress selectById(Long id) {
		return customerAddressMapper.selectByPrimaryKey(id);
	}

	@Override
	public int insert(CustomerAddress record) {
		return customerAddressMapper.insert(record);
	}

	@Override
	public int updateStateByCustomerId(CustomerAddress record) {
		return customerAddressMapper.updateStateByCustomerId(record);
	}

	@Override
	public int updateByPrimaryKeySelective(CustomerAddress record) {
		return customerAddressMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteByPrimaryKeyAndCustomerId(Long id, Long customer_id) {
		return customerAddressMapper.deleteByPrimaryKeyAndCustomerId(id, customer_id);
	}

	@Override
	public Integer selectInvitCount(Long customer_id) {
		return customerEntityMapper.selectInvitCount(customer_id);
	}

	@Override
	public List<Appointment> selectAppointmentByCustomerId(Long customer_id) throws ParseException {
		List<Appointment> list = appointmentMapper.selectByCustomerId(customer_id);

		for (Appointment a : list) {
			if (a.getState() == AppointmentState.statecode1 && a.getStart_time() != null) {
				double end_time = (double) (a.getEnd_time().getTime() - System.currentTimeMillis());
				if (end_time < 0) {
					a.setNote(SysConstant.APPOINTMENT_NOTE_EXP);
				} else {
					double dd = (double) (a.getStart_time().getTime() - System.currentTimeMillis());
					if (dd > 0 && dd < 3 * SysConstant.MILLIS_PER_DAY) {
						double s = dd / SysConstant.MILLIS_PER_DAY;
						a.setNote(String.format(SysConstant.APPOINTMENT_NOTE, String.format("%.1f", s)));
					}
				}
			}
		}
		return list;
	}

	@Override
	public Appointment selectDatilByAppointmentInfoId(Long id) {

		Appointment a = appointmentMapper.selectDatilByAppointmentInfoId(id);
		if (a != null && a.getDeal_info() != null) {
			String deal_info = a.getDeal_info();
			a.setDeal_info(null);
			a.setDeal_info_array(deal_info.split(AppointmentState.splitChar));
		}
		List<AppointmentStateInfo> statesList = new ArrayList<AppointmentStateInfo>();
		if (a.getDeal_info_array() != null) {
			for (int i = 0; i < a.getDeal_info_array().length; i++) {
				String t = a.getDeal_info_array()[i];
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

			// , new Comparator<AppointmentStateInfo>() {
			//
			// @Override
			// public int compare(AppointmentStateInfo o1, AppointmentStateInfo
			// o2) {
			// if(o1.getTime() == null || o2.getTime() == null) return 1;
			// try {
			// DateUtils.parseDate_(o1.getTime()).getTime();
			// return (int)(DateUtils.parseDate_(o1.getTime()).getTime() -
			// DateUtils.parseDate_(o2.getTime()).getTime());
			// } catch (ParseException e) {
			// e.printStackTrace();
			// }
			// return -1;
			// }
			//
			// }
		}
		a.setStates(statesList.size() > 0 ? statesList : null);
		a.setDeal_info_array(null);
		return a;
	}

	public int cancelappointment(Long id) {

		AppointmentInfoEntity a = appointmentInfoEntityMapper.selectByPrimaryKey(id);

		a.setState(AppointmentState.statecode5);

		a.setDeal_info(a.getDeal_info() + AppointmentState.splitChar + AppointmentState.state5 + DateUtils.formatDate_(new Date()));

		return appointmentInfoEntityMapper.updateByPrimaryKeySelective(a);
	}

	@Override
	public void requestvcodebyemail(final Long id, final String email) {
		QuestsManagerBean.addQuest(new Quest() {

			@Override
			public boolean execute() {
				String vcode = NumberUtil.SixNumber();
				SendMail.setTextMail(email, SysConstant.MAIL_SUBJECT1, vcode);
				RedisUtils.setWithTimeLimit(SysConstant.CUSTOMER_VCODE + id, vcode,
						(long) (SysConstant.SECOND_PER_MINUTE * 10));// 设置用户验证码，10分钟有效
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

	public void addsharepoint(Point point) {
		// String name = "";
		// if(point.getShareType() ==1){//1 产品 2 品牌 3 活动 4 新闻
		// OrderProduct orderproduct =
		// brandProductEntityMapper.selectNameImgByPrimaryKey(point.getId());
		// if(orderproduct!=null) name = orderproduct.getProduct_name();
		// }else if(point.getShareType() ==2){
		// name = brandEntityMapper.selectBrandNameById(point.getId());
		// }else if(point.getShareType() ==3){
		// //待添加
		// }else if(point.getShareType() ==4){
		// ArticleEntity articleEntity =
		// articleEntityMapper.selectByPrimaryKey(point.getId());
		// if(articleEntity != null) name = articleEntity.getTitle();
		// }
		
		List<CustomerPointType> list = customerPointEntityMapper.selectPointType();
		CustomerPointType result = null;
		for(CustomerPointType c : list){
			if(c.getType().equals(SysConstant.Share_Point_Id)){
				result = c;
			}
		}
		CustomerPointEntity customerPointEntity = new CustomerPointEntity();
		customerPointEntity.setCustomer_id(point.getCustomer_id());
		customerPointEntity.setName(point.getTitle());
		customerPointEntity.setTime(new Date());
		customerPointEntity.setType(SysConstant.Share_Point_Id);
		customerPointEntity.setPoint(result.getPoint());
		customerPointEntityMapper.insertSelective(customerPointEntity);
		
		MessageEntity messageEntity = new MessageEntity();
		messageEntity.setCustomer_id(point.getCustomer_id());
		messageEntity.setType_id(MessageConstant.PointShare);
		messageEntity.setParams(result.getPoint().toString());
		messageEntity.setTime(new Date());
		messageEntityMapper.insertSelective(messageEntity);
		
		sendMessageServiceImpl.sendMessage(point.getCustomer_id(), result.getPoint());
	}

}
