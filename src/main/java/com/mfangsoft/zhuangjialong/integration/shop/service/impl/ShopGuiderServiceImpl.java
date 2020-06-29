package com.mfangsoft.zhuangjialong.integration.shop.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.seller.mapper.BaseSellerInfoEntityMapper;
import com.mfangsoft.zhuangjialong.app.seller.mapper.SellerConstructInfoEntityMapper;
import com.mfangsoft.zhuangjialong.app.seller.mapper.SellerEntityMapper;
import com.mfangsoft.zhuangjialong.app.seller.model.BaseSellerInfoEntity;
import com.mfangsoft.zhuangjialong.app.seller.model.SellerEntity;
import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.MD5Util;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseEntity;
import com.mfangsoft.zhuangjialong.integration.enums.SellerType;
import com.mfangsoft.zhuangjialong.integration.enums.ShopGuiderCertificationState;
import com.mfangsoft.zhuangjialong.integration.enums.UserType;
import com.mfangsoft.zhuangjialong.integration.partner.model.PartnerEntity;
import com.mfangsoft.zhuangjialong.integration.seller.model.SellerModel;
import com.mfangsoft.zhuangjialong.integration.shop.mapper.BaseShopGuiderEntityMapper;
import com.mfangsoft.zhuangjialong.integration.shop.mapper.ShopGuiderMapper;
import com.mfangsoft.zhuangjialong.integration.shop.model.ShopEntity;
import com.mfangsoft.zhuangjialong.integration.shop.model.ShopGuiderModel;
import com.mfangsoft.zhuangjialong.integration.shop.service.ShopGuiderService;
import com.mfangsoft.zhuangjialong.integration.user.model.UserEntity;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年12月23日
* 
*/
@Service
public class ShopGuiderServiceImpl implements ShopGuiderService{
	
	private static final String ShopEntity = null;
	private static final String MSG="手机号或用户名已被占用";//返回信息
	@Autowired
	SellerEntityMapper sellerEntityMapper;
	@Autowired
	SellerConstructInfoEntityMapper sellerConstructInfoEntityMapper;
	@Autowired
	BaseSellerInfoEntityMapper baseSellerInfoEntityMapper;
	@Autowired
	BaseShopGuiderEntityMapper baseShopGuiderEntityMapper;
	@Autowired
	ShopGuiderMapper shopGuiderMapper;

	@Override
	public ResponseMessage<String> addShopGuider(SellerModel sellerModel) {
		UserEntity u = UserContext.getCurrentUser();
		ResponseMessage<String> message =new ResponseMessage<>();
		if(u.getUser_type()==UserType.SHOP.getIndex()){
			SellerEntity sellerEntity = sellerEntityMapper.selectByAccountOrName(sellerModel);
			if(sellerEntity!=null){
				message.setCode(SysConstant.FAILURE_CODE);
				message.setMessage(MSG);
				return message;
			}
		ShopEntity shopEntity =(ShopEntity)UserContext.getCurrentUserInfo();
		sellerModel.setPartner_id(shopEntity.getCitypartner_id());
		sellerModel.setPassword(MD5Util.MD5(sellerModel.getPassword()));
		sellerModel.setSeller_type(SellerType.ShopGuider.getIndex());
		sellerModel.setCreate_time(new Date());
		sellerEntityMapper.insertSelective(sellerModel);
		BaseSellerInfoEntity sellerInfoEntity = new BaseSellerInfoEntity();
		sellerInfoEntity.setSeller_id(sellerModel.getId());
		sellerInfoEntity.setCommission_rate(SysConstant.Seller_rate);
		 baseSellerInfoEntityMapper.insertSelective(sellerInfoEntity);
		 ShopGuiderModel guiderModel = new ShopGuiderModel();
		 guiderModel.setBrand_id(shopEntity.getBrand_id());
		 guiderModel.setShop_id(shopEntity.getId());
		 guiderModel.setCertification_state(ShopGuiderCertificationState.NoCommit.getIndex());
		 guiderModel.setSeller_info_id(sellerInfoEntity.getId());
		 guiderModel.setUser_id(u.getId());
		 guiderModel.setIsgenerate(1);
		 baseShopGuiderEntityMapper.insertSelective(guiderModel);
		}
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		 return message;
	}

	@Override
	public Page<ShopGuiderModel> getShopGuideresForPage(Page<ShopGuiderModel> page) {
		UserEntity u = UserContext.getCurrentUser();
		Map<String, Object> map = (Map<String, Object>)page.getParam();
		if(u.getUser_type()==UserType.SHOP.getIndex() ){
			ShopEntity shopEntity = (ShopEntity)UserContext.getCurrentUserInfo();
			map.put("shop_id", shopEntity.getId());
		}else if(u.getUser_type()==UserType.PARTNER.getIndex() ){
			PartnerEntity entity =(PartnerEntity)UserContext.getCurrentUserInfo();
			map.put("partner_id", entity.getId());
		}else if(u.getUser_type()==UserType.ENTERPRISE.getIndex() ){
			EnterpriseEntity entity =(EnterpriseEntity)UserContext.getCurrentUserInfo();
			map.put("enterprise_id", entity.getId());
		}
	List<ShopGuiderModel> list = shopGuiderMapper.selectShopGuiderForPage(page);
		page.setData(list);
		return page;
	}

	@Override
	public ResponseMessage<String> modifyShopGuider(SellerModel sellerModel) {
		UserEntity u = UserContext.getCurrentUser();
		ResponseMessage<String> message = new ResponseMessage<>();
		if(u.getUser_type()==UserType.SHOP.getIndex() || u.getUser_type()==UserType.PARTNER.getIndex()){
			SellerEntity sellerEntity = sellerEntityMapper.selectByAccountOrNameWithoutId(sellerModel);
			if(sellerEntity!=null){
				message.setCode(SysConstant.FAILURE_CODE);
				message.setMessage(MSG);
				return message;
			}
			if(sellerModel.getPassword() !=null){
				String password = sellerModel.getPassword();
				sellerModel.setPassword(MD5Util.MD5(password));
			}
			if(sellerEntityMapper.updateByPrimaryKeySelective(sellerModel)>0){
	    	 message.setCode(SysConstant.SUCCESS_CODE);
				message.setMessage(SysConstant.SUCCESS_MSG);
				return message;
	     }
		}
		message.setCode(SysConstant.FAILURE_CODE);
		message.setMessage(MSG);
		return message;
	}

	@Override
	public Map<String, Object> getShopGuider(Long id) {
		return sellerEntityMapper.selectShopGuider(id);
	}

	@Override
	public Boolean modifyShopGuiderState(Map<String, Object> sellerModel) {
		return shopGuiderMapper.updateCertificationStateBySellerId(sellerModel)>0;
	}

	@Override
	public  List<Map<String, Object>> queryShopGuiderForSelect() {
		ShopEntity shopEntity=(ShopEntity) UserContext.getCurrentUserInfo();
		return sellerEntityMapper.selectPassShopGuiderByShopId(shopEntity.getId());
	}

}
