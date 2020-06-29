package com.mfangsoft.zhuangjialong.integration.partner.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.applogin.mapper.CustomerEntityMapper;
import com.mfangsoft.zhuangjialong.app.applogin.mapper.UserEquipmentEntityMapper;
import com.mfangsoft.zhuangjialong.app.applogin.model.CustomerEntity;
import com.mfangsoft.zhuangjialong.app.applogin.model.UserEquipmentEntity;
import com.mfangsoft.zhuangjialong.app.newproductcore.service.ProductCoreService;
import com.mfangsoft.zhuangjialong.app.sendsms.service.SendSMSService;
import com.mfangsoft.zhuangjialong.common.utils.JPushUtil;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.core.quartz.Quest;
import com.mfangsoft.zhuangjialong.core.quartz.QuestsManagerBean;
import com.mfangsoft.zhuangjialong.integration.partner.mapper.OpenCityEntityMapper;
import com.mfangsoft.zhuangjialong.integration.partner.mapper.PartnerEntityMapper;
import com.mfangsoft.zhuangjialong.integration.partner.model.CustomerSMSModel;
import com.mfangsoft.zhuangjialong.integration.partner.model.OpenCityEntity;
import com.mfangsoft.zhuangjialong.integration.partner.model.PartnerEntity;
import com.mfangsoft.zhuangjialong.integration.partner.service.OpenCityService;
import com.mfangsoft.zhuangjialong.integration.partner.service.PartnerService;
import com.mfangsoft.zhuangjialong.integration.region.service.RegionService;
import com.mfangsoft.zhuangjialong.system.service.SysConstantService;
@Service
public class OpenCityServiceImpl implements OpenCityService {

	@Autowired
	public PartnerService partnerService;
	
	@Autowired
	private RegionService regionService;
	
	@Autowired
	private OpenCityEntityMapper openCityEntityMapper;
	
	@Autowired
	private CustomerEntityMapper customerEntityMapper;
	@Autowired
	private SendSMSService sendSMSServiceImpl;
	
	@Autowired
	private SysConstantService sysConstantService;
	
	@Autowired
	private PartnerEntityMapper  partnerEntityMapper;
	
	@Autowired
	private UserEquipmentEntityMapper userEquipmentEntityMapper;
	
	@Autowired
	private ProductCoreService productCoreServiceImpl;
	
	private final static String CLOSE_CITY_MESSAGE = "尊敬的{0}您好，由于系统维护,{1}即将于{2}关闭，您在该城市已经下的订单会正常进行服务不用担心。给您带来的不便请您谅解，祝您生活愉快！";

	private static String CLOSE_CITY_TITLE="CLOSE_CITY_TITLE";
	
	private static String CLOSE_CITY_CONTENT="CLOSE_CITY_CONTENT";
	
	

	
	@Override
	public Boolean addOpenCity(Long id) {
		// TODO Auto-generated method stub
		
		
		
//		List<Integer> l=openCityEntityMapper.queryOpenCtiyByPartnerId(id,2);
//		if(l!=null&&l.size()>0){
//			
//			OpenCityEntity cityEntity = new OpenCityEntity();
//			cityEntity.setId(l.get(0));
//			cityEntity.setState(1);
//			openCityEntityMapper.updateByPrimaryKeySelective(cityEntity);
//			
//		}else{
//			PartnerEntity partnerEntity=partnerService.getPartnerById(id);
//			 String region_str=partnerEntity.getRegion_id();
//			 if(region_str!=null){
//				 String region_name=regionService.selectreginName(region_str.split(",")[1]);
//				 
//				  OpenCityEntity cityEntity = new OpenCityEntity();
//				  
//				  cityEntity.setRegion_code(region_str.split(",")[1]);
//				  cityEntity.setRegion_name(region_name);
//				  cityEntity.setState(1);
//				  cityEntity.setPartner_id(partnerEntity.getId());
//				  openCityEntityMapper.insertSelective(cityEntity);
//			 }
//		}
		
		
		 
		 
		 PartnerEntity entity = new PartnerEntity();
		 
		 entity.setCheck_state(2);
		 entity.setId(id);
		 productCoreServiceImpl.updatePartnerStateDoc(id, 2);
		 partnerEntityMapper.updateByPrimaryKeySelective(entity);
		
		return true;
	}

	@Override
	public List<Map<String, Object>> queryOpenCity() {
		// TODO Auto-generated method stub
		return openCityEntityMapper.queryOpenCity();
	}

	@Override
	public Boolean closeCity(PartnerEntity partnerEntity ) {
		// TODO Auto-generated method stub
		
	
//		OpenCityEntity cityEntity=openCityEntityMapper.selectByPrimaryKey(id);
//		cityEntity.setState(2);
//		openCityEntityMapper.updateByPrimaryKeySelective(cityEntity);
		
		
		partnerEntityMapper.updateByPrimaryKeySelective(partnerEntity);
		 productCoreServiceImpl.updatePartnerStateDoc(partnerEntity.getId(), partnerEntity.getCheck_state());
		return true;
	}

	@Override
	public Boolean checkOpenCtiy(Long id) {
		// TODO Auto-generated method stub
		List<Integer> l=openCityEntityMapper.queryOpenCtiyByPartnerId(id,1);
		
		if(l!=null&&l.size()>0){
			return false;
		}else{
			return true;
		}
		
	
	}

	@Override
	public Boolean pushMessage(Long id,String colesTime) {
		
		
		List<CustomerEntity> customerEntities=customerEntityMapper.selectAllcustomer();
		
		PartnerEntity  partnerEntity=partnerService.getPartnerById(id);
		
		
		List<String>  list = new ArrayList<>();
		String reString=partnerEntity.getRegion_id();
		if(partnerEntity.getIsglobal().intValue()==1){
			
			list.add(regionService.selectreginName(reString.substring(0, 6)));
		}else{
			
			list.add(regionService.selectreginName(reString.substring(13+1,reString.length())));
		}
		
		list.add(colesTime);
			QuestsManagerBean.addQuest(new Quest() {

				@Override
				public boolean execute() {
					
					for (CustomerEntity customerEntity : customerEntities) {
						
						UserEquipmentEntity equipmentEntity =userEquipmentEntityMapper.selectByCustomerId(customerEntity.getId());
						
					JPushUtil.sendMessage(equipmentEntity.getPlatform(), equipmentEntity.getPushstr(),
							sysConstantService.getSysConstantByKey(CLOSE_CITY_TITLE).getValue(), MessageFormat.format(sysConstantService.getSysConstantByKey(CLOSE_CITY_CONTENT).getValue(), list));
					
					}
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
			
		
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Boolean sendSMS(Long id,String colesTime) {
		List<CustomerSMSModel> customerSMSModels = openCityEntityMapper.selectUnEndOrderCustomerByPartnerId(id);
		for (CustomerSMSModel customerSMSModel : customerSMSModels) {
			if(customerSMSModel.getAccount()!=null){
				QuestsManagerBean.addQuest(new Quest() {

					@Override
					public boolean execute() {
						sendSMSServiceImpl.sendSMS(customerSMSModel.getAccount(), MessageFormat.format(CLOSE_CITY_MESSAGE, customerSMSModel.getName(),customerSMSModel.getRegion_name(),colesTime), SysConstant.ZJL_MESSAGE_SUF);
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

	@Override
	public Boolean forceClose(Long id) {
		// TODO Auto-generated method stub
		
		PartnerEntity partnerEntity = new PartnerEntity();
		partnerEntity.setId(id);
		partnerEntity.setCheck_state(1);
		
		partnerEntityMapper.updateByPrimaryKeySelective(partnerEntity);
		 productCoreServiceImpl.updatePartnerStateDoc(id, 1);
		return true;
	}

}
