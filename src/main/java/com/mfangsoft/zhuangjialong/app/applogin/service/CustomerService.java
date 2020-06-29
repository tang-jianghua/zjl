package com.mfangsoft.zhuangjialong.app.applogin.service;

import com.mfangsoft.zhuangjialong.app.applogin.model.BaseSellerLogEntity;
import com.mfangsoft.zhuangjialong.app.applogin.model.Customer;
import com.mfangsoft.zhuangjialong.app.applogin.model.CustomerEntity;
import com.mfangsoft.zhuangjialong.app.applogin.model.UserEquipmentEntity;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.core.exception.ServiceException;

/**
* @description：消费者接口
* @author：Jianghua.Tang 
* @date：2016年6月2日
* 
*/

public interface CustomerService {
	
	
	/**
	 * @description：添加国庆节兑换记录（流量）
	 * @param：消费者id
	 */
	public String addFlowConvertRecordWithProbability(CustomerEntity customer);
	
	/**
	    * @description：添加国庆节兑换记录（流量）
	    * @param：消费者id
	    */
	public String addFlowConvertRecordWithoutProbability(CustomerEntity customer);
	
	/**
	    * @description：根据账号查询消费者数据
	    * @param：Customer
	    */
	public CustomerEntity selectByAccount(String account) throws ServiceException; 
	
	/**
	    * @description：登录
	    * @param：Customer
	    */
	public ResponseMessage<Customer> login(Customer customer) throws ServiceException;
		
	/**
	    * @description：注册
	    * @param：Customer
	    */
	public ResponseMessage<String> addAccount(Customer customer) throws ServiceException ;
	
	/**
	    * @description：修改密码
	    * @param：CustomerEntity
	    */
	public ResponseMessage<String> modifyPassword(Customer customer) throws ServiceException;
	
	/**
	    * @description：发送短信验证码
	    * @param：CustomerEntity
	    */
	public boolean sendMS(CustomerEntity customer) throws ServiceException;
	
	public void insertOrUpdateSelective(UserEquipmentEntity record);
	
	public  int updateByPrimaryKeySelective(UserEquipmentEntity record);
	
	public UserEquipmentEntity selectUserEquipmentByCustomerId(Long customer_id);
	
	/*
	 * 记录卖家段登陆日志
	 */
	public boolean insertSellerLog(BaseSellerLogEntity baseSellerLogEntity);
}
