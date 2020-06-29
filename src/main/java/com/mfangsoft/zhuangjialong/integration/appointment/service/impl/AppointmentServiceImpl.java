package com.mfangsoft.zhuangjialong.integration.appointment.service.impl;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.applogin.mapper.BaseSellerLogEntityMapper;
import com.mfangsoft.zhuangjialong.app.applogin.mapper.CustomerEntityMapper;
import com.mfangsoft.zhuangjialong.app.applogin.mapper.UserEquipmentEntityMapper;
import com.mfangsoft.zhuangjialong.app.applogin.model.BaseSellerLogEntity;
import com.mfangsoft.zhuangjialong.app.applogin.model.UserEquipmentEntity;

import com.mfangsoft.zhuangjialong.app.appointment.mapper.AppointmentInfoEntityMapper;
import com.mfangsoft.zhuangjialong.app.appointment.mapper.AppointmentMapper;
import com.mfangsoft.zhuangjialong.app.appointment.model.Appointment;

import com.mfangsoft.zhuangjialong.app.appointment.model.AppointmentInfo;
import com.mfangsoft.zhuangjialong.app.appointment.model.AppointmentInfoEntity;
import com.mfangsoft.zhuangjialong.app.appointment.model.BaseAppointment;
import com.mfangsoft.zhuangjialong.app.brand.model.BrandNewProduct;
import com.mfangsoft.zhuangjialong.app.personalCenter.mapper.CustomerPointEntityMapper;
import com.mfangsoft.zhuangjialong.app.personalCenter.mapper.MessageEntityMapper;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.CustomerPointEntity;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.CustomerPointType;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.MessageEntity;
import com.mfangsoft.zhuangjialong.app.shop.model.Shop;
import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.message.service.impl.SendMessageServiceImpl;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.DateUtils;
import com.mfangsoft.zhuangjialong.common.utils.JPushUtil;
import com.mfangsoft.zhuangjialong.common.utils.MessageConstant;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.core.quartz.Quest;
import com.mfangsoft.zhuangjialong.core.quartz.QuestsManagerBean;
import com.mfangsoft.zhuangjialong.integration.appointment.service.AppointmentService;
import com.mfangsoft.zhuangjialong.integration.appointment.util.AppointmentState;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.mapper.EnterpriseEntityMapper;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseEntity;
import com.mfangsoft.zhuangjialong.integration.enums.UserType;
import com.mfangsoft.zhuangjialong.integration.partner.model.PartnerEntity;
import com.mfangsoft.zhuangjialong.integration.shop.mapper.EmployeeEntityMapper;
import com.mfangsoft.zhuangjialong.integration.shop.mapper.ShopEntityMapper;
import com.mfangsoft.zhuangjialong.integration.shop.model.EmployeeEntity;
import com.mfangsoft.zhuangjialong.integration.shop.model.ShopEntity;
import com.mfangsoft.zhuangjialong.integration.user.model.UserEntity;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	private AppointmentMapper appointmentEntityMapper;
	
	@Autowired
	private AppointmentInfoEntityMapper appointmentInfoEntityMapper;
	
	@Autowired
	private EmployeeEntityMapper  employeeEntityMapper;
	
	@Autowired
	private ShopEntityMapper shopEntityMapper;
	
	@Autowired
	CustomerPointEntityMapper customerPointEntityMapper;
	
	@Autowired
	private UserEquipmentEntityMapper userEquipmentEntityMapper;
	
	@Autowired
	MessageEntityMapper messageEntityMapper;
	
	@Autowired
	SendMessageServiceImpl sendMessageServiceImpl;
	@Autowired
	BaseSellerLogEntityMapper sellerLogEntityMapper;
	
	@Override
	public Page<Map<String, Object>> getAppointmentListForPage(Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub
		
		popuPageParam(page);
		  List<Map<String,Object>>  list=     appointmentEntityMapper.getAppointmentListForPage(page);
		  
		  
		  
		  
		  
		  page.setData(list);
		return page;
	}
	
   private void popuPageParam(Page<Map<String, Object>> page){
		
		if(UserContext.getCurrentUser().getUser_type() == UserType.BRAND.getIndex()){
			
			BrandEntity brandEntity =(BrandEntity) UserContext.getCurrentUserInfo();
			if (page.getParam() != null) {

				Map<String, Object> map = (Map<String, Object>) page.getParam();
				
				map.put("brand_id", brandEntity.getId());
			} else {

				Map<String, Object> map = new HashMap<>();

				map.put("brand_id", brandEntity.getId());
				page.setParam(map);

			}
			
			
		}
		if(UserContext.getCurrentUser().getUser_type() == UserType.ENTERPRISE.getIndex()){
			
			EnterpriseEntity enterpriseEntity =(EnterpriseEntity) UserContext.getCurrentUserInfo();
			
			
			if (page.getParam() != null) {

				Map<String, Object> map = (Map<String, Object>) page.getParam();
				
				map.put("ent_id", enterpriseEntity.getId());
				
			
			} else {

				Map<String, Object> map = new HashMap<>();

				map.put("ent_id", enterpriseEntity.getId());
				
				page.setParam(map);

			}
		}
		
		if(UserContext.getCurrentUser().getUser_type() == UserType.PARTNER.getIndex()){
			
			PartnerEntity partnerEntity =(PartnerEntity) UserContext.getCurrentUserInfo();
			
			
			if (page.getParam() != null) {

				Map<String, Object> map = (Map<String, Object>) page.getParam();
				
				map.put("partner_id", partnerEntity.getId());
				
				

			} else {

				Map<String, Object> map = new HashMap<>();
				map.put("partner_id", partnerEntity.getId());
				page.setParam(map);

			}
		}
		
		if(UserContext.getCurrentUser().getUser_type() == UserType.SHOP.getIndex()){
			
			ShopEntity shopEntity =(ShopEntity) UserContext.getCurrentUserInfo();
			
			
			if (page.getParam() != null) {

				Map<String, Object> map = (Map<String, Object>) page.getParam();
				
				map.put("shop_id", shopEntity.getId());

			} else {

				Map<String, Object> map = new HashMap<>();

				map.put("shop_id", shopEntity.getId());

				page.setParam(map);

			}
		}
   }
		
	@Override
	public List<EmployeeEntity> getServiceUser() {
		// TODO Auto-generated method stub
		
		ShopEntity shopEntity=(ShopEntity) UserContext.getCurrentUserInfo();
		return employeeEntityMapper.getEmployeeList(shopEntity.getId());
	}
	@Override
	public Boolean updateAppointmentInfo(Long id,Integer state) {
		// TODO Auto-generated method stub
		
		AppointmentInfoEntity appointmentEntity=appointmentInfoEntityMapper.selectByPrimaryKey(id);
		
		appointmentEntity.setState(state);
		//appointmentEntity.setId(id);
		
        StringBuffer  bu = new StringBuffer(appointmentEntity.getDeal_info());
		
		switch (state) {
		case 6:
		
		bu.append(";");
		bu.append(AppointmentState.state6);
		bu.append(DateUtils.formatDate_(new Date()));
			break;
			
		case 2:
			bu.append(";");
			
			bu.append(AppointmentState.state2);
			bu.append(DateUtils.formatDate_(new Date()));
			addAppointPoint(id);
			break;
			
		case 3:
			bu.append(";");
			
			bu.append(AppointmentState.state3);
			bu.append(DateUtils.formatDate_(new Date()));
			break;
		case 4:
			bu.append(";");
			
			bu.append(AppointmentState.state4);
			bu.append(DateUtils.formatDate_(new Date()));
			break;

		default:
			break;
		}
		
		
		
		appointmentEntity.setDeal_info(bu.toString());
		
		
		if(state==6){
			appointmentEntity.setServer_id(null);
		}
		if(appointmentInfoEntityMapper.updateByPrimaryKeyWithBLOBs(appointmentEntity)>0){
			if(state==4){
				//查询消费者设备信息
				final BaseSellerLogEntity logEntity = sellerLogEntityMapper.selectLastLoginBySellerId(appointmentEntity.getServer_id());
				//查询续约信息
				BaseAppointment appointment = appointmentEntityMapper.selectByPrimaryKey(appointmentEntity.getAppointment_id());
				
				//查询消费者设备信息
				final UserEquipmentEntity userEquipmentEntity = userEquipmentEntityMapper.selectByCustomerId(appointment.getCustomer_id());
				//推送成功消息
				if(logEntity != null && userEquipmentEntity!=null){
					QuestsManagerBean.addQuest(new Quest() {
						@Override
						public boolean execute() {
							JPushUtil.sendSellerMessage(logEntity.getDevice_type(), logEntity.getDevice_no(), SysConstant.APPOINTMENT_SERVICE_TITLE, 
									MessageFormat.format(SysConstant.APPOINTMENT_SERVICE_DONE_CONTENT,appointment.getReceiver_name()));
							JPushUtil.sendMessage(userEquipmentEntity.getPlatform(), userEquipmentEntity.getPushstr(), SysConstant.APPOINTMENT_SERVICE_TITLE, 
									MessageFormat.format(SysConstant.APPOINTMENT_SERVICE_DONE_CONTENT_CLIENT,appointment.getReceiver_name()));
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
			}
			return true;
		}
		
		return false;
	}
	
	private void addAppointPoint(Long id){
		
		Appointment appointment = appointmentInfoEntityMapper.selectInfoById(id);
		
		List<CustomerPointType> list = customerPointEntityMapper.selectPointType();
		CustomerPointType result = null;
		for(CustomerPointType c : list){
			if(c.getType().equals(SysConstant.Appointment_Point_Id)){
				result = c;
			}
		}
		
		Integer point = result.getPoint();
		
		CustomerPointEntity customerPointEntity = new CustomerPointEntity();
		customerPointEntity.setCustomer_id(appointment.getCustomer_id());
		customerPointEntity.setName(appointment.getShop_name());
		customerPointEntity.setTime(new Date());
		customerPointEntity.setType(SysConstant.Appointment_Point_Id);
		customerPointEntity.setPoint(point);
		customerPointEntityMapper.insertSelective(customerPointEntity);
		
		MessageEntity messageEntity = new MessageEntity();
		messageEntity.setCustomer_id(appointment.getCustomer_id());
		messageEntity.setParams(point + "");
		messageEntity.setType_id(MessageConstant.PointAppointment);
		messageEntity.setTime(new Date());
		messageEntityMapper.insertSelective(messageEntity);
		
		sendMessageServiceImpl.sendMessage(appointment.getCustomer_id(), point);
		
	}

	@Override
	public Map<String, Object> queryBrandAppDetail(Long id) {
		// TODO Auto-generated method stub
		return appointmentInfoEntityMapper.queryBrandAppDetail(id);
	}
	@Override
	public List<ShopEntity> getShopName() {
		// TOD
		BrandEntity brand = (BrandEntity) UserContext.getCurrentUserInfo();
		
		return shopEntityMapper.getShopNameByBrand(brand.getId());
	}
	@Override
	public Boolean updateAppointmentInfoShopId(Long id, Long shop_id) {
		// TODO Auto-generated method stub
		
		AppointmentInfoEntity record = new AppointmentInfoEntity();
		record.setShop_id(shop_id);
		record.setId(id);
		if(appointmentInfoEntityMapper.updateByPrimaryKeySelective(record)>0){
			return true;
		}
		return false;
	}
	@Override
	public Boolean updateAppointmentInfoService(Long id, Long service_id) {
		// TODO Auto-generated method stub
		AppointmentInfoEntity record = new AppointmentInfoEntity();
		record.setId(id);
		record.setServer_id(service_id);
		if(appointmentInfoEntityMapper.updateByPrimaryKeySelective(record)>0){
			//查询消费者设备信息
			final BaseSellerLogEntity logEntity = sellerLogEntityMapper.selectLastLoginBySellerId(service_id);
			//推送成功消息
			if(logEntity != null){
				QuestsManagerBean.addQuest(new Quest() {
					@Override
					public boolean execute() {
						JPushUtil.sendSellerMessage(logEntity.getDevice_type(), logEntity.getDevice_no(), SysConstant.APPOINTMENT_SERVICE_TITLE, 
								SysConstant.APPOINTMENT_SERVICE_START_CONTENT);
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
			return true;
		}
		return false;
	
	}
	

}
