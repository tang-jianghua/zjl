package com.mfangsoft.zhuangjialong.app.inviteconvert.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.applogin.mapper.CustomerEntityMapper;
import com.mfangsoft.zhuangjialong.app.applogin.model.CustomerEntity;
import com.mfangsoft.zhuangjialong.app.charge.service.ChargeService;
import com.mfangsoft.zhuangjialong.app.constant.mapper.AppConstantEntityMapper;
import com.mfangsoft.zhuangjialong.app.inviteconvert.mapper.InviteConvertEntityMapper;
import com.mfangsoft.zhuangjialong.app.inviteconvert.mapper.InviteProductEntityMapper;
import com.mfangsoft.zhuangjialong.app.inviteconvert.model.InviteConvertEntity;
import com.mfangsoft.zhuangjialong.app.inviteconvert.model.InviteConvertMainModel;
import com.mfangsoft.zhuangjialong.app.inviteconvert.model.InviteProductEntity;
import com.mfangsoft.zhuangjialong.app.inviteconvert.service.InviteConvertService;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年10月31日
* 
*/
@Service
public class InviteConvertServiceImpl implements InviteConvertService{
	
	public  final static Integer CHARGING = 1;//充值中
    public  final static Integer CHARGED_SUCCESS =2;//充值成功
    public final static Integer CHARGED_FAIL =3;//充值失败
   
   
   @Autowired
	private InviteProductEntityMapper inviteProductEntityMapper;
   
   @Autowired
	private AppConstantEntityMapper appConstantEntityMapper;
   
   @Autowired
	private CustomerEntityMapper customerEntityMapper;
   
   @Autowired
	private InviteConvertEntityMapper inviteConvertEntityMapper;
   
   @Autowired
	private ChargeService chargeServiceImpl;
    
	@Override
	public InviteConvertMainModel getInviteMain(InviteConvertEntity inviteConvertEntity) {
		InviteConvertMainModel inviteConvertMainModel = new InviteConvertMainModel();
		inviteConvertMainModel.setProducts(inviteProductEntityMapper.selectAll());
		inviteConvertMainModel.setCharge_rule(appConstantEntityMapper.selectByKey("INVITE_CONVERT_RULE").getValue_txt());
		if(inviteConvertEntity.getCustomer_id()!=null){
		Map<String, Object> map = new HashMap<>();
		map.put("customer_id", inviteConvertEntity.getCustomer_id());
		map.put("start_time", appConstantEntityMapper.selectByKey("2016_11_11_start_time").getValue_date());
		map.put("end_time", appConstantEntityMapper.selectByKey("2016_11_11_end_time").getValue_date());
		 List<CustomerEntity> customers = customerEntityMapper.selectInvitCountByDate(map);
		int convertedInviteNo = inviteConvertEntityMapper.selectConvertedInviteNo(inviteConvertEntity.getCustomer_id());
		inviteConvertMainModel.setInvite_no(customers.size()-convertedInviteNo);
		inviteConvertMainModel.setCustomers(customers); 
		}else{
			inviteConvertMainModel.setInvite_no(0);
		}
		return inviteConvertMainModel;
	}
	@Override
	public synchronized boolean addInviteConvert(InviteConvertEntity inviteConvertEntity) {
		InviteProductEntity inviteProductEntity = inviteProductEntityMapper.selectByPrimaryKey(inviteConvertEntity.getProduct_id());
		Map<String, Object> map = new HashMap<>();
		map.put("customer_id", inviteConvertEntity.getCustomer_id());
		map.put("start_time", appConstantEntityMapper.selectByKey("2016_11_11_start_time").getValue_date());
		map.put("end_time", appConstantEntityMapper.selectByKey("2016_11_11_end_time").getValue_date());
		int invitCount = customerEntityMapper.selectInvitCountByDate(map).size();
		int convertedInviteNo = inviteConvertEntityMapper.selectConvertedInviteNo(inviteConvertEntity.getCustomer_id());
		if(invitCount-convertedInviteNo<inviteProductEntity.getInvite_no()){
			return false;
		}
		CustomerEntity customerEntity = customerEntityMapper.selectByPrimaryKey(inviteConvertEntity.getCustomer_id());
		inviteConvertEntity.setCreate_time(new Date());
		inviteConvertEntity.setState(CHARGING);
		String convert_no = this.getConvert_no(customerEntity.getAccount());
		inviteConvertEntity.setConvert_no(convert_no);
		boolean addChargeBatch = chargeServiceImpl.addChargeBatch(inviteProductEntity.getPackage_value(), convert_no, customerEntity.getAccount());
		if(!addChargeBatch){
			return false;
		}
		int insertSelective = inviteConvertEntityMapper.insertSelective(inviteConvertEntity);
		if(insertSelective==0){
			return false;
		}
		return true;
	}
	
	String getConvert_no(String mobile){
		StringBuffer convert_no = new StringBuffer();
		convert_no.append(mobile.substring(0, 3));
		convert_no.append(java.util.UUID.randomUUID().toString().toUpperCase().substring(0, 8));
		convert_no.append(mobile.substring(7, 11));
		InviteConvertEntity inviteConvertEntity = inviteConvertEntityMapper.selectByPrimaryKey(convert_no.toString());
		if(inviteConvertEntity!=null){
		return	this.getConvert_no(mobile);
		}
		return convert_no.toString();
	}

}
