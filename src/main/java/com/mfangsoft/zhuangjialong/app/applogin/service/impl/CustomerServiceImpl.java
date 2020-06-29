package com.mfangsoft.zhuangjialong.app.applogin.service.impl;


import java.io.IOException;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.util.security.Credential.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.mfangsoft.zhuangjialong.app.applogin.mapper.BaseSellerLogEntityMapper;
import com.mfangsoft.zhuangjialong.app.applogin.mapper.CustomerEntityMapper;
import com.mfangsoft.zhuangjialong.app.applogin.mapper.UserEquipmentEntityMapper;
import com.mfangsoft.zhuangjialong.app.applogin.model.BaseSellerLogEntity;
import com.mfangsoft.zhuangjialong.app.applogin.model.Customer;
import com.mfangsoft.zhuangjialong.app.applogin.model.CustomerEntity;
import com.mfangsoft.zhuangjialong.app.applogin.model.UserEquipmentEntity;
import com.mfangsoft.zhuangjialong.app.applogin.service.CustomerService;
import com.mfangsoft.zhuangjialong.app.personalCenter.mapper.CustomerPointEntityMapper;
import com.mfangsoft.zhuangjialong.app.personalCenter.mapper.MessageEntityMapper;
import com.mfangsoft.zhuangjialong.app.personalCenter.mapper.PointEntityMapper;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.CustomerPointEntity;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.CustomerPointType;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.MessageEntity;
import com.mfangsoft.zhuangjialong.app.pointmall.model.ConvertProductModel;
import com.mfangsoft.zhuangjialong.app.pointmall.service.impl.PointMallServiceImpl;
import com.mfangsoft.zhuangjialong.app.sendflow.service.SendFlowService;
import com.mfangsoft.zhuangjialong.app.sendflow.utils.FlowPackageUtil;
import com.mfangsoft.zhuangjialong.app.sendsms.service.SendSMSService;
import com.mfangsoft.zhuangjialong.common.message.service.impl.SendMessageServiceImpl;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.DateUtils;
import com.mfangsoft.zhuangjialong.common.utils.EncrUtil;
import com.mfangsoft.zhuangjialong.common.utils.MD5Util;
import com.mfangsoft.zhuangjialong.common.utils.MessageConstant;
import com.mfangsoft.zhuangjialong.common.utils.MobileUtil;
import com.mfangsoft.zhuangjialong.common.utils.RedisUtils;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.common.utils.TokenUtil;
import com.mfangsoft.zhuangjialong.core.exception.ServiceException;
import com.mfangsoft.zhuangjialong.integration.pointmall.mapper.PointConvertEntityMapper;
import com.mfangsoft.zhuangjialong.integration.user.service.EasemobIMService;

/**
 * @description：
 * 
 * @author：Jianghua.Tang @date：2016年6月2日
 * 
 */
@Service
public class CustomerServiceImpl implements CustomerService {

	public static String START_TIME="2016-11-03 00:00:00";//活动开始时间
	public static String END_TIME="2016-11-30 23:59:59";//活动结束时间
	
	@Autowired
	private CustomerEntityMapper customerEntityMapper;
	
	@Autowired
	private UserEquipmentEntityMapper userEquipmentEntityMapper;
	
	@Autowired
	private SendSMSService sendSMSServiceImpl;
	
	@Autowired
	CustomerPointEntityMapper customerPointEntityMapper;

	@Autowired
	SendMessageServiceImpl sendMessageServiceImpl;
	
	@Autowired
	SendFlowService sendFlowServiceImpl;
	@Autowired
	PointConvertEntityMapper pointConvertEntityMapper;
	@Autowired
	MessageEntityMapper messageEntityMapper;
	@Autowired
	EasemobIMService easemobIMService;
	
	@Autowired
	BaseSellerLogEntityMapper sellerLogEntityMapper;
	
	
	@Override
	public ResponseMessage<Customer> login(Customer customerParam) {
		ResponseMessage<Customer> message = new ResponseMessage<>();	
		if(customerParam.getVcode()!=null &&customerParam.getVcode()!="" ){
			String vcode = RedisUtils.getValue(customerParam.getVkey());
			if (!customerParam.getVcode().toLowerCase().equals(vcode)) {
				message.setCode("1");
				message.setMessage("验证码错误");
				return message;
			}else{
				RedisUtils.delValue(customerParam.getVkey());
			}
		}
		
		CustomerEntity customerEntity = customerEntityMapper.selectByAccount(customerParam.getAccount());
		if ( customerEntity== null) {
			message.setCode("1");
			message.setMessage("账号不存在");
			return message;
		}
		if (!customerEntity.getPassword().equals(MD5Util.MD5(customerParam.getPwd()))) {
			message.setCode("2");
			message.setMessage("密码不正确");
			return message;
	}
		Customer customer= new Customer();
			 Date start_time;
			 Date end_time ;
			try {
				start_time = DateUtils.parseDate_(START_TIME);
				end_time = DateUtils.parseDate_(END_TIME);
				  if(customerEntity.getCreate_time().getTime()>=start_time.getTime() && customerEntity.getCreate_time().getTime()<=end_time.getTime()){
					  //if(true){	 
					 ConvertProductModel convertProductModel = pointConvertEntityMapper.selectFirstFlowConvertByCustomerId(customerEntity.getId());
					 if(convertProductModel!=null){
						 
					  customer.setFlow(pointConvertEntityMapper.selectFlowByProductId(convertProductModel.getProduct_id()).get(0));
					  customer.setConvert_code(convertProductModel.getConvert_code());
					  if(convertProductModel.getConvert_state() == 1&& convertProductModel.getValid_state() ==true){
						  customer.setFlow_used_state(true);
					  }else {
						  customer.setFlow_used_state(false);
					  }
					 }
				 }
			}catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		customer.setCustomer_id(customerEntity.getId());
		customer.setCustomer_name(customerEntity.getName());
		customer.setCustomer_head(customerEntity.getHead_url());
		customer.setCustomer_phone(customerEntity.getAccount());
		customer.setEmail(customerEntity.getEmail());
	    customer.setCustomer_point(customerPointEntityMapper.selectTotalPointByCustomerId(customerEntity.getId()));
		customer.setToken(TokenUtil.getIns().getToken(customerEntity.getAccount()));
		customer.setKey(TokenUtil.getIns().getAndIncreaseKey()+customerEntity.getId());
		message.setCode("0");
		message.setMessage("登陆成功");
		message.setResult(customer);
		return message;
	}
	public static void main(String[] args) {
		
		char  flag = "D94kb6h8jwk0py4".charAt(0);
		
		System.out.println('D'==flag);
	}
	@Override
	public String addFlowConvertRecordWithoutProbability(CustomerEntity customer) {

		 Long product_id_10=22L;//10M流量包id
		 Long product_id_20=23L;//20M流量包id
		 Long product_id_50=24L;//50M流量包id
		Date start_time;
		Date end_time;
		String flow = null;
		try {
			start_time = DateUtils.parseDate_(START_TIME);
			end_time = DateUtils.parseDate_(END_TIME);
			
			if(customer.getCreate_time().getTime()>=start_time.getTime() && customer.getCreate_time().getTime()<=end_time.getTime()){
				// if(true){
				ConvertProductModel convertProductModel = new ConvertProductModel();
				convertProductModel.setCustomer_id(customer.getId()); 
				convertProductModel.setConvert_type(PointMallServiceImpl.FLOW_TYPE);
				convertProductModel.setConvert_state(PointMallServiceImpl.CONVERT_UNUSED);
				convertProductModel.setCrater_time(customer.getCreate_time());
				convertProductModel.setConvert_point(0);
				StringBuffer convert_code = new StringBuffer();
				convert_code.append(customer.getAccount().substring(0, 3));
				convert_code.append(java.util.UUID.randomUUID().toString().toUpperCase().substring(0, 8));
				convert_code.append(customer.getAccount().substring(7, 11));
				convertProductModel.setConvert_code(convert_code.toString());
				if(RedisUtils.lpop(SysConstant.REG_PRE_HUNDRED)!=null){
					convertProductModel.setProduct_id(product_id_50);
				}else{
					switch (MobileUtil.validateMobile(customer.getAccount())){
					case SysConstant.CUCC:
						convertProductModel.setProduct_id(product_id_20);break;
                  default: convertProductModel.setProduct_id(product_id_10);
					}	
				}
				this.addConvertFlow(convertProductModel);
				flow = pointConvertEntityMapper.selectFlowByProductId(convertProductModel.getProduct_id()).get(0);
			} 
		}catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flow;
	}
	@Override
	public String addFlowConvertRecordWithProbability(CustomerEntity customer) {
		 Date national_day_start;
		 Date national_day_end;
		 String flow = null;
		try {
			national_day_start = DateUtils.parseDate_(SysConstant.NATIONAL_DAY_START);
			 national_day_end = DateUtils.parseDate_(SysConstant.NATIONAL_DAY_END);
			 
			 if(customer.getCreate_time().getTime()>=national_day_start.getTime() && customer.getCreate_time().getTime()<=national_day_end.getTime()){
				 //	 if(true){
				 ConvertProductModel convertProductModel = new ConvertProductModel();
				 convertProductModel.setCustomer_id(customer.getId());
				 convertProductModel.setConvert_type(PointMallServiceImpl.FLOW_TYPE);
				 convertProductModel.setConvert_state(PointMallServiceImpl.CONVERT_UNUSED);
				 convertProductModel.setCrater_time(customer.getCreate_time());
				 convertProductModel.setConvert_point(0);
				 StringBuffer convert_code = new StringBuffer();
				 convert_code.append(customer.getAccount().substring(0, 3));
				 convert_code.append(java.util.UUID.randomUUID().toString().toUpperCase().substring(0, 8));
				 convert_code.append(customer.getAccount().substring(7, 11));
				 convertProductModel.setConvert_code(convert_code.toString());
				 switch (MobileUtil.validateMobile(customer.getAccount())){
				 case SysConstant.CUCC:
					 convertProductModel.setProduct_id(FlowPackageUtil.getCucc());break;
				 case SysConstant.CTCC:
					 convertProductModel.setProduct_id(FlowPackageUtil.getCtcc());break;
				 case SysConstant.CMCC:
					 convertProductModel.setProduct_id(FlowPackageUtil.getCmcc());break;
				 }
				 this.addConvertFlow(convertProductModel);
				  flow = pointConvertEntityMapper.selectFlowByProductId(convertProductModel.getProduct_id()).get(0);
		} 
		}catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			 return flow;
	}
	/*
	 * 迭代赠送流量
	 */
	public int addConvertFlow(ConvertProductModel convertProductModel){
		int i = pointConvertEntityMapper.insertSelective(convertProductModel);
		if(i==0){
			this.addConvertFlow(convertProductModel);
		}
		return i;
	}
	
	@Override
	public ResponseMessage<String> addAccount(Customer customer) {
		ResponseMessage<String> message = new ResponseMessage<>();
		String vcode = RedisUtils.getValue(customer.getAccount());
		Date new_date = new Date();
       
		/*if (StringUtils.isEmpty(customer.getVcode())) {
			message.setCode("1");
			message.setMessage("验证码不能为空");
			return message;
		}
		if(StringUtils.isEmpty(vcode)){
			
			message.setCode("1");
			message.setMessage("未获取验证码或验证码已过期");
			return message;
		}
		if (!customer.getVcode().equals(vcode)) {
			message.setCode("1");
			message.setMessage("验证码错误");
			return message;
		}*/
		if (customerEntityMapper.selectByAccount(customer.getAccount()) != null) {
			message.setCode("1");
			message.setMessage("账号已被注册");
			RedisUtils.delValue(customer.getAccount());
			return message;
		}
		CustomerEntity customerEntity = new CustomerEntity();
		String invite_code = customer.getInvite_code();
		System.out.println("------------invite_code------------------------------------------------------- " + invite_code);
		if(invite_code !=null){
			customerEntity.setProperty(2);
		char flag = invite_code.charAt(0);
		String invite_id = EncrUtil.decrypt(invite_code.substring(1, invite_code.length()).toString());
		System.out.println("------------invite_code------------------------------------------------------- " + invite_id);
		if('C'==flag){
			customerEntity.setInviter_id(Long.parseLong(invite_id));
			Long exshopper_id = customerEntityMapper.selectByPrimaryKey(Long.parseLong(invite_id)).getExshopper_id();
			System.out.println("------------exshopper_id------------------------------------------------------- " + exshopper_id);
			customerEntity.setExshopper_id(exshopper_id);
			
			List<CustomerPointType> list = customerPointEntityMapper.selectPointType();
			CustomerPointType result = null;
			for(CustomerPointType c : list){
				if(c.getType().equals(SysConstant.Expand_Point_Id)){
					result = c;
				}
			}
			
			CustomerPointEntity customerPointEntity = new CustomerPointEntity();
			customerPointEntity.setCustomer_id(Long.valueOf(invite_id));
			customerPointEntity.setName(SysConstant.Expand_Name);
			customerPointEntity.setTime(new_date);
			customerPointEntity.setType(SysConstant.Expand_Point_Id);
			customerPointEntity.setPoint(result.getPoint());
			customerPointEntityMapper.insertSelective(customerPointEntity);
			
			MessageEntity messageEntity = new MessageEntity();
			messageEntity.setCustomer_id(Long.valueOf(invite_id));
			messageEntity.setType_id(MessageConstant.PointExpand);
			messageEntity.setParams(result.getPoint().toString());
			messageEntity.setTime(new Date());
			messageEntityMapper.insertSelective(messageEntity);
			
			sendMessageServiceImpl.sendMessage(Long.valueOf(invite_id), result.getPoint());
		}else if('D'==flag){
			customerEntity.setExshopper_id(Long.parseLong(invite_id));
		}
		}else{
			customerEntity.setProperty(1);
		}
		customerEntity.setAccount(customer.getAccount());
		customerEntity.setEmail(customer.getEmail());
		customerEntity.setPassword(MD5Util.MD5(customer.getPwd()));
		customerEntity.setPhone(customer.getAccount());
		customerEntity.setCreate_time(new_date);
		customerEntity.setName(this.newName());
		if (customerEntityMapper.insertSelective(customerEntity) == 0) {
			message.setCode("1");
			message.setMessage("注册失败");
			return message;
		}
		 this.addFlowConvertRecordWithoutProbability(customerEntity);
		
			System.out.println("------------exshopper_id--------------------- " + new Gson().toJson(customerEntity));
			message.setCode("0");
			message.setMessage("注册成功");
			RedisUtils.delValue(customer.getAccount());
			return message;
	}
	
    //获取一个昵称
	public  String newName(){
		String name = java.util.UUID.randomUUID().toString().toUpperCase().substring(0, 8);
		if(customerEntityMapper.selectByName(name)!=null){
			return this.newName();
		}else{
			return name;
		}
	}
	
	
	@Override
	public CustomerEntity selectByAccount(String account) {
		return customerEntityMapper.selectByAccount(account);
	}

	@Override
	public ResponseMessage<String> modifyPassword(Customer customer) {
		ResponseMessage<String> message = new ResponseMessage<>();
		String vcode = RedisUtils.getValue(customer.getAccount());
		if (StringUtils.isEmpty(customer.getVcode())) {
			message.setCode("1");
			message.setMessage("验证码不能为空");
			return message;
		}
		if(StringUtils.isEmpty(vcode)){
			message.setCode("1");
			message.setMessage("未获取验证码或验证码已过期");
			return message;
		}
		if (!customer.getVcode().equals(vcode)) {
			message.setCode("1");
			message.setMessage("验证码错误");
			return message;
		}
		CustomerEntity customerEntity = customerEntityMapper.selectByAccount(customer.getAccount());
		customerEntity.setPassword(MD5Util.MD5(customer.getNew_pwd()));
		int updateByPrimaryKeySelective = customerEntityMapper.updateByPrimaryKeySelective(customerEntity);
		if(updateByPrimaryKeySelective>0){
			RedisUtils.delValue(customer.getAccount());
			message.setCode("0");
			message.setMessage("修改成功");
			RedisUtils.delValue(customer.getAccount());
			return message;
			}else{
				message.setCode("1");
				message.setMessage("修改失败");
				return message;
			}
		
	}

	@Override
	public boolean sendMS(CustomerEntity customer) throws ServiceException {
		if(RedisUtils.getValue(customer.getPhone()) != null){
			RedisUtils.delValue(customer.getPhone());
		}
		
		StringBuffer vcode = new StringBuffer(); 
		
        Random random = new Random();  
        
        for (int i = 0; i < 6; i++) {  
        	
        	vcode.append(random.nextInt(10));  
            
        }  
		
		String content = MessageFormat.format(SysConstant.V_CODE_MESSAGE, vcode.toString());  //要发送的短信内容，特别注意：签名必须设置，网页验证码应用需要加添加【图形识别码】。
		
		boolean sendSMS = sendSMSServiceImpl.sendSMS(customer.getPhone(), content,SysConstant.ZJL_MESSAGE_SUF);
		if(sendSMS){
			//将验证码存到reids中
			RedisUtils.setWithTimeLimit(customer.getPhone(),vcode.toString(), 600L);
		}
		
		return sendSMS;
	}
	
	
	public void insertOrUpdateSelective(UserEquipmentEntity record){
		try{
//		if(record.getCustomer_id() != null && record.getCustomer_id() >0){
//			if(userEquipmentEntityMapper.selectByCustomerIdForModify(record.getCustomer_id()) != null){
//				userEquipmentEntityMapper.updateByCustomerIdKeySelective(record);
//				return ;
//			}
//		}
		if(userEquipmentEntityMapper.selectByPrimaryKey(record.getPushstr()) != null){
			userEquipmentEntityMapper.updateByPrimaryKeySelective(record);
		}else{
			userEquipmentEntityMapper.insertSelective(record);
		}
		}catch(Exception e){
			System.out.println(new Gson().toJson(record));
			e.printStackTrace();
		}
	}
	
	public  int updateByPrimaryKeySelective(UserEquipmentEntity record){
		return userEquipmentEntityMapper.updateByPrimaryKeySelective(record);
	}

	public UserEquipmentEntity selectUserEquipmentByCustomerId(Long customer_id){
		return userEquipmentEntityMapper.selectByCustomerId(customer_id);
	}
	
	
	@Override
	public boolean insertSellerLog(BaseSellerLogEntity baseSellerLogEntity) {
		return sellerLogEntityMapper.insertSelective(baseSellerLogEntity)>0;
	}
}
