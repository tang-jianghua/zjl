package com.mfangsoft.zhuangjialong.app.charge.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mfangsoft.zhuangjialong.app.applogin.mapper.CustomerEntityMapper;
import com.mfangsoft.zhuangjialong.app.applogin.mapper.UserEquipmentEntityMapper;
import com.mfangsoft.zhuangjialong.app.applogin.model.CustomerEntity;
import com.mfangsoft.zhuangjialong.app.applogin.model.UserEquipmentEntity;
import com.mfangsoft.zhuangjialong.app.charge.mapper.ChargeBackEntityMapper;
import com.mfangsoft.zhuangjialong.app.charge.mapper.ChargeBatchEntityMapper;
import com.mfangsoft.zhuangjialong.app.charge.model.ChargeBackEntity;
import com.mfangsoft.zhuangjialong.app.charge.model.ChargeBatchEntity;
import com.mfangsoft.zhuangjialong.app.charge.service.ChargeService;
import com.mfangsoft.zhuangjialong.app.inviteconvert.mapper.InviteConvertEntityMapper;
import com.mfangsoft.zhuangjialong.app.inviteconvert.model.InviteConvertEntity;
import com.mfangsoft.zhuangjialong.app.inviteconvert.service.impl.InviteConvertServiceImpl;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.MessageEntity;
import com.mfangsoft.zhuangjialong.app.pointmall.service.impl.PointMallServiceImpl;
import com.mfangsoft.zhuangjialong.app.sendsms.service.impl.SendSMSServiceImpl;
import com.mfangsoft.zhuangjialong.common.utils.JPushUtil;
import com.mfangsoft.zhuangjialong.common.utils.MD5Util;
import com.mfangsoft.zhuangjialong.common.utils.MessageConstant;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.core.quartz.Quest;
import com.mfangsoft.zhuangjialong.core.quartz.QuestsManagerBean;
import com.mfangsoft.zhuangjialong.integration.pointmall.mapper.PointConvertEntityMapper;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年10月28日
* 
*/
@Service
public class ChargeServiceImpl implements ChargeService{
	
	private static final String APIKEY="qRo4P9e700yjKU8F5uli";//apikey
	
	private static final String USERNAME="cf_molitiehe";//用户名
	
	private static final String URL="http://f.ihuyi.com/phone?action=recharge"; 
	
	
	@Autowired
	ChargeBatchEntityMapper chargeBatchEntityMapper;
	
	@Autowired
	ChargeBackEntityMapper chargeBackEntityMapper;
	
	@Autowired
	PointConvertEntityMapper pointConvertEntityMapper;
	
	@Autowired
	CustomerEntityMapper customerEntityMapper;
	
	@Autowired
	InviteConvertEntityMapper inviteConvertEntityMapper;
	
	@Autowired
	UserEquipmentEntityMapper userEquipmentEntityMapper;
	
	@Autowired
	SendSMSServiceImpl sendSMSServiceImpl;
	
	
	
	@Override
	public boolean addChargeBatch(int charge_package,String order_id,String mobile) {
		Calendar cd = Calendar.getInstance();  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");  
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8")); // 设置时区为GMT  
		    String timestamp = sdf.format(cd.getTime()); 
		Date date = new Date();
		
		//签名
		String sign_="apikey="+APIKEY+"&mobile="+mobile+"&orderid="+order_id+"&package="+charge_package+"&timestamp="+timestamp+"&username="+USERNAME;
		String sign = MD5Util.MD5(sign_);
		
		//封装话费批次数据
		ChargeBatchEntity batchEntity = new ChargeBatchEntity();
		batchEntity.setCharge_package(charge_package);
		batchEntity.setCreate_time(date);
		batchEntity.setMobile(mobile);
		batchEntity.setOrder_id(order_id);
		batchEntity.setSign(sign);

		
	StringBuffer url_buffer=new StringBuffer();
		
	url_buffer.append(URL);
	url_buffer.append("&username="+USERNAME);
	url_buffer.append("&mobile="+mobile);
	url_buffer.append("&orderid="+order_id);
	url_buffer.append("&package="+charge_package);
	url_buffer.append("&timestamp="+timestamp);
	url_buffer.append("&sign="+sign);
		
		//把buffer链接存入新建的URL中
	try {
		URL url = new URL(url_buffer.toString());

		//打开URL链接
		HttpURLConnection connection;
			connection = (HttpURLConnection)url.openConnection();
		//使用POST方式发送
		connection.setRequestMethod("POST");

		//使用长链接方式
		connection.setRequestProperty("Connection", "Keep-Alive");
		
		//发送短信内容
		BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
		
		//获取返回值
		String result = reader.readLine();
		//输出result内容，查看返回值，成功为success，错误为error，详见该文档起始注释
		System.out.println(result);
		ObjectMapper json = new ObjectMapper();
		 ChargeBatchEntity readValue = json.readValue(result, ChargeBatchEntity.class);
		 batchEntity.setCode(readValue.getCode());
		 batchEntity.setMessage(readValue.getMessage());
		 batchEntity.setTask_id(readValue.getTaskid());
		 int insert = chargeBatchEntityMapper.insert(batchEntity);
		 if(insert==0){
			 return false;
		 }
		 if(readValue.getCode()!=1){
			 return false;
		 }
	}catch (Exception e) {
		System.out.println("http请求异常");
		e.printStackTrace();
		
		return false;
		
	}
		return true;
	}

	@Override
	public String addChargeBack(ChargeBackEntity chargeBackEntity) {
		Date date=new Date();
		int insert = chargeBackEntityMapper.insert(chargeBackEntity);
		if(insert==0){
		return "FALURE";
		}
		//查询批次号
		ChargeBatchEntity chargeBatchEntity = chargeBatchEntityMapper.selectByTaskId(chargeBackEntity.getTask_id());
		/*Map<String, Object> map= new HashMap<>();
		map.put("convert_code", chargeBatchEntity.getOrder_id());*/
		InviteConvertEntity inviteConvertEntity=new InviteConvertEntity();
		inviteConvertEntity.setConvert_no(chargeBatchEntity.getOrder_id());
		inviteConvertEntity.setBack_time(date);
		//查询消费者信息
		CustomerEntity customerEntity = customerEntityMapper.selectByAccount(chargeBackEntity.getMobile());
		if(customerEntity==null){
			return "FAILURE";
		}
		//构造信息记录对象
		MessageEntity messageEntity = new MessageEntity();
		messageEntity.setTime(date);
		messageEntity.setCustomer_id(customerEntity.getId());
		
		//查询消费者设备信息
		final UserEquipmentEntity userEquipmentEntity = userEquipmentEntityMapper.selectByCustomerId(customerEntity.getId());
		
		//充值失败
		if(chargeBackEntity.getState()==2){
			//修改已兑换的话费包使用状态
			/*map.put("convert_state", PointMallServiceImpl.CONVERT_UNUSED);*/
			//pointConvertEntityMapper.updateConvertStateByConvertCode(map);
			inviteConvertEntity.setState(InviteConvertServiceImpl.CHARGED_FAIL);
			 inviteConvertEntityMapper.updateByPrimaryKeySelective(inviteConvertEntity);
			//添加信息类型
			messageEntity.setType_id(MessageConstant.ChargeFailExp);
			messageEntity.setParams(customerEntity.getName());
			
			//发送失败短信
			sendSMSServiceImpl.sendSMS(chargeBackEntity.getMobile(), MessageFormat.format(MessageConstant.ChargeFailContent,customerEntity.getName()), SysConstant.ZJL_MESSAGE_SUF);
			
			
			//推送失败消息
		if(userEquipmentEntity != null){
			QuestsManagerBean.addQuest(new Quest() {
				@Override
				public boolean execute() {
					JPushUtil.sendMessage(userEquipmentEntity.getPlatform(), userEquipmentEntity.getPushstr(), MessageConstant.ChargeFailTitle, 
							MessageFormat.format(MessageConstant.ChargeFailContent,customerEntity.getName())
							);
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
			
			
			
			return "FALURE";
		}
		//修改已兑换的话费包使用状态
/*		map.put("convert_state", PointMallServiceImpl.CONVERT_USED);
		pointConvertEntityMapper.updateConvertStateByConvertCode(map);*/
		inviteConvertEntity.setState(InviteConvertServiceImpl.CHARGED_SUCCESS);
		 inviteConvertEntityMapper.updateByPrimaryKeySelective(inviteConvertEntity);
		//添加信息类型
		messageEntity.setType_id(MessageConstant.ChargeSuccessExp);
		messageEntity.setParams(customerEntity.getName()+","+customerEntity.getAccount()+","+chargeBatchEntity.getCharge_package());
		
		
		//推送成功消息
	if(userEquipmentEntity != null){
		QuestsManagerBean.addQuest(new Quest() {
			@Override
			public boolean execute() {
				JPushUtil.sendMessage(userEquipmentEntity.getPlatform(), userEquipmentEntity.getPushstr(), MessageConstant.ChargeSuccessTitle, 
						MessageFormat.format(MessageConstant.ChargeSuccessContent, customerEntity.getName(),customerEntity.getAccount(),chargeBatchEntity.getCharge_package())
						);
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
		
		
		
			return "SUCCESS";
	
	}

}
