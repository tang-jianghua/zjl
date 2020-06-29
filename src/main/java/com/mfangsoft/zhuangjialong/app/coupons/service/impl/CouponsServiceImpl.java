package com.mfangsoft.zhuangjialong.app.coupons.service.impl;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.applogin.mapper.CustomerEntityMapper;
import com.mfangsoft.zhuangjialong.app.applogin.mapper.UserEquipmentEntityMapper;
import com.mfangsoft.zhuangjialong.app.applogin.model.CustomerEntity;
import com.mfangsoft.zhuangjialong.app.applogin.model.UserEquipmentEntity;
import com.mfangsoft.zhuangjialong.app.coupons.mapper.CustomerCouponsEntityMapper;
import com.mfangsoft.zhuangjialong.app.coupons.model.BrandCouponsModel;
import com.mfangsoft.zhuangjialong.app.coupons.model.FirstPageCouponsListParam;
import com.mfangsoft.zhuangjialong.app.coupons.service.CouponsService;
import com.mfangsoft.zhuangjialong.app.personalCenter.mapper.MessageEntityMapper;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.CustomerCollectionEntity;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.MessageEntity;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.JPushUtil;
import com.mfangsoft.zhuangjialong.common.utils.MessageConstant;
import com.mfangsoft.zhuangjialong.core.quartz.Quest;
import com.mfangsoft.zhuangjialong.core.quartz.QuestsManagerBean;
import com.mfangsoft.zhuangjialong.integration.coupons.mapper.BrandCouponsEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newcoupons.mapper.BaseBrandCouponsEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newcoupons.model.BaseBrandCouponsEntity;
import com.mfangsoft.zhuangjialong.system.mapper.sysConstantEntityMapper;
import com.mfangsoft.zhuangjialong.system.model.sysConstantEntity;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年9月9日
* 
*/
@Service(value="appCouponsServiceImpl")
public class CouponsServiceImpl implements CouponsService{
	
	@Autowired
	private BrandCouponsEntityMapper brandCouponsEntityMapper;	
	@Autowired
	private UserEquipmentEntityMapper userEquipmentEntityMapper;
	@Autowired
	private CustomerCouponsEntityMapper customerCouponsEntityMapper;
	@Autowired
	private MessageEntityMapper messageEntityMapper;
	@Autowired
	private CustomerEntityMapper customerEntityMapper;
	@Autowired
	private sysConstantEntityMapper sysConstantEntityMapper;
	@Autowired
	BaseBrandCouponsEntityMapper baseBrandCouponsEntityMapper;
	
	@Override
	public List<BrandCouponsModel> selectBrandCoupons(CustomerCollectionEntity param) {
		List<BrandCouponsModel> coupons = brandCouponsEntityMapper.selectCouponsForBrandMain(param.getBrand_id());
		for (int i = 0; i < coupons.size(); i++) {
			BrandCouponsModel brandCouponsModel = coupons.get(i);
			brandCouponsModel.setCustomer_id(param.getCustomer_id());
			brandCouponsModel.setState(customerCouponsEntityMapper.selectCustomerWhetherGainCoupons(brandCouponsModel));
			brandCouponsModel.setCustomer_id(null);
			brandCouponsModel.setBrand_id(null);
			}
		return coupons;
	}
	
	@Override
	public List<BrandCouponsModel> selectBrandRedbags(CustomerCollectionEntity param) {
		List<BrandCouponsModel> redbags = brandCouponsEntityMapper.selectRedBagsForBrandMain(param.getBrand_id());
		for (int i = 0; i < redbags.size(); i++) {
			BrandCouponsModel brandCouponsModel = redbags.get(i);
			brandCouponsModel.setCustomer_id(param.getCustomer_id());
			brandCouponsModel.setState(customerCouponsEntityMapper.selectCustomerWhetherGainCoupons(brandCouponsModel));
			brandCouponsModel.setCustomer_id(null);
			brandCouponsModel.setBrand_id(null);
			}
		return redbags;
	}
	
	@Override
	public synchronized Boolean addBrandCoupons(BrandCouponsModel param) {
		
		
	/*	synchronized (this) {
			
			if(customerCouponsEntityMapper.selectCustomerWhetherGainCoupons(param) >0){
				return false;
			}
			int addCoupons = customerCouponsEntityMapper.addCoupons(param);
			System.out.println("领取优惠券/红包，领取的id为："+param.getId());
			if(addCoupons==0){
				System.out.println("领取失败");
				return false;
			}
			BaseBrandCouponsEntity coupons = brandCouponsEntityMapper.selectByPrimaryKey(param.getCoupons_id());
			if((coupons.getSurplus_count()-1)<0){
				System.out.println("剩余数不足，删除刚领取的红包/优惠券");
				customerCouponsEntityMapper.deleteByPrimaryKey(param.getId());
				System.out.println("删除的id为："+param.getId());
				return false;
			}
			System.out.println("修改之前的剩余数："+coupons.getSurplus_count());
			param.setSurplus_count(coupons.getSurplus_count()-1);
			System.out.println("修改之后的剩余数："+(coupons.getSurplus_count()-1));
			int updateSurplusById = brandCouponsEntityMapper.updateSurplusById(param);
			if(updateSurplusById==0){
				System.out.println("修改失败");
				return false;
			}
		}*/
		
		if(customerCouponsEntityMapper.selectCustomerWhetherGainCoupons(param) >0){
			return false;
		}
		BaseBrandCouponsEntity coupons = brandCouponsEntityMapper.selectByPrimaryKey(param.getCoupons_id());
		if(coupons.getSurplus_count()<1){
			System.out.println("剩余数不足");
			return false;
		}
		CustomerEntity customerEntity = customerEntityMapper.selectByPrimaryKey(param.getCustomer_id());
		if(customerEntity==null){
			return false;
		}
		StringBuffer convert_code = new StringBuffer();
		convert_code.append(customerEntity.getAccount().substring(0, 3));
		convert_code.append(java.util.UUID.randomUUID().toString().toUpperCase().substring(0, 8));
		convert_code.append(customerEntity.getAccount().substring(7, 11));
		param.setConvert_code(convert_code.toString());
		int addCoupons = customerCouponsEntityMapper.addCoupons(param);
		System.out.println("领取优惠券/红包，领取的id为："+param.getId());
		if(addCoupons==0){
			System.out.println("领取失败");
			return false;
		}
		System.out.println("修改之前的剩余数："+coupons.getSurplus_count());
		param.setSurplus_count(coupons.getSurplus_count()-1);
		System.out.println("修改之后的剩余数："+(coupons.getSurplus_count()-1));
		int updateSurplusById = brandCouponsEntityMapper.updateSurplusById(param);
		if(updateSurplusById==0){
			System.out.println("修改失败");
			return false;
		}
		return true;

	}

	/* (non-Javadoc)
	 * @see com.mfangsoft.zhuangjialong.app.coupons.service.CouponsService#sendMessage(com.mfangsoft.zhuangjialong.app.coupons.model.BrandCouponsModel)
	 */
	@Override
	public Boolean sendMessage(BrandCouponsModel param) {
		BaseBrandCouponsEntity coupons = brandCouponsEntityMapper.selectByPrimaryKey(param.getCoupons_id());
		switch(coupons.getType()){
		case 1:
		final UserEquipmentEntity userEquipmentEntity = userEquipmentEntityMapper.selectByCustomerId(param.getCustomer_id());
		if(userEquipmentEntity != null){
			final Double value = coupons.getValue();
			QuestsManagerBean.addQuest(new Quest() {
				@Override
				public boolean execute() {
					JPushUtil.sendMessage(userEquipmentEntity.getPlatform(), userEquipmentEntity.getPushstr(), MessageConstant.RegbagTitle, 
							MessageFormat.format(MessageConstant.RegbagContent,userEquipmentEntity.getCustomer_name(), value));
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

			
			
			MessageEntity messageEntity = new MessageEntity();
			messageEntity.setCustomer_id(userEquipmentEntity.getCustomer_id());
			messageEntity.setParams(userEquipmentEntity.getCustomer_name() + "," + coupons.getValue());
			messageEntity.setType_id(MessageConstant.Regbag);
			messageEntity.setTime(new Date());
			messageEntityMapper.insertSelective(messageEntity);
		}
		break;
		case 2:
			UserEquipmentEntity userEquipmentEntityc = userEquipmentEntityMapper.selectByCustomerId(param.getCustomer_id());
			if(userEquipmentEntityc != null){
				JPushUtil.sendMessage(userEquipmentEntityc.getPlatform(), userEquipmentEntityc.getPushstr(), MessageConstant.CouponsTitle, 
						MessageFormat.format(MessageConstant.CouponsContent,userEquipmentEntityc.getCustomer_name(),coupons.getValue()));
				
				MessageEntity messageEntity = new MessageEntity();
				messageEntity.setCustomer_id(userEquipmentEntityc.getCustomer_id());
				messageEntity.setParams(userEquipmentEntityc.getCustomer_name() + "," + coupons.getValue());
				messageEntity.setType_id(MessageConstant.Coupons);
				messageEntity.setTime(new Date());
				messageEntityMapper.insertSelective(messageEntity);
		
		}
			break;
	}
		return true;
	}
	
	public BrandCouponsModel queryPromotionCoupons(){
		sysConstantEntity syscont = sysConstantEntityMapper.getSysConstantByKey("shuang11couponsid");
		
		BrandCouponsModel ins = new BrandCouponsModel();
		
		if(syscont != null){
			
			Long coupons_id = Long.valueOf(syscont.getValue().toString());
			
			BaseBrandCouponsEntity baseBrandCouponsEntity = baseBrandCouponsEntityMapper.selectByPrimaryKey(coupons_id);
			if(baseBrandCouponsEntity == null){
				return null;
			}
			Integer count = customerCouponsEntityMapper.selectCouponsCount(coupons_id);
			
			ins.setCoupons_id(coupons_id);
			ins.setCoupons_price(baseBrandCouponsEntity.getCoupons_price());
			ins.setCount(count);
			ins.setValue(baseBrandCouponsEntity.getValue());
		}
		
		return ins;
	}

	@Override
	public List<Map<String,Object>> queryFirstPageCouponsList(Page<Map<String,Object>> page) {
		
		 
		return brandCouponsEntityMapper.queryFirstPageCouponsListForPage(page);
				
		
	}

	@Override
	public Map<String, Object> queryOneFirstPageCoupons(Map<String, Object> param) {

		Map<String,Object> map = brandCouponsEntityMapper.queryOneFirstPageCoupons(param);
		
		
		return map;
	}
	
	@Override
	public List<Map<String,Object>> queryOneFirstPageCouponsShops(Map<String,Object> param){
		
		return brandCouponsEntityMapper.queryOneCouponsShopInfo(Long.parseLong(param.get("id").toString()));

	}
	
}
