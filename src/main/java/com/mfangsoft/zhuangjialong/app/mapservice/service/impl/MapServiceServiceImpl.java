package com.mfangsoft.zhuangjialong.app.mapservice.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.buildClass.model.BuildClass;
import com.mfangsoft.zhuangjialong.app.mapservice.mapper.AskhelpMapper;
import com.mfangsoft.zhuangjialong.app.mapservice.mapper.SellerServiceMapper;
import com.mfangsoft.zhuangjialong.app.mapservice.model.AskForHelpModel;
import com.mfangsoft.zhuangjialong.app.mapservice.model.BaseAskhelpEntity;
import com.mfangsoft.zhuangjialong.app.mapservice.model.BaseSellerServiceEntity;
import com.mfangsoft.zhuangjialong.app.mapservice.model.SellerServiceModel;
import com.mfangsoft.zhuangjialong.app.mapservice.service.MapServiceService;
import com.mfangsoft.zhuangjialong.app.shop.model.Shop;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.DistanceUtil;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.brand.mapper.BrandEntityMapper;
import com.mfangsoft.zhuangjialong.integration.partner.mapper.PartnerEntityMapper;
import com.mfangsoft.zhuangjialong.integration.shop.mapper.ShopEntityMapper;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年12月14日
* 
*/
@Service
public class MapServiceServiceImpl implements MapServiceService{

	@Autowired
	AskhelpMapper askhelpMapper;
	@Autowired
	SellerServiceMapper sellerServiceMapper;
	@Autowired
	PartnerEntityMapper partnerEntityMapper;
	@Autowired
	BrandEntityMapper  brandEntityMapper;
	@Autowired
	ShopEntityMapper shopEntityMapper;
	
	private static final int SERVICE_TYPE = 1;//产品服务

	private static final int CONSTRUCT_TYPE =2;//施工服务
	
	@Override
	public List<BuildClass> getClasses(Long partner_id) {
		return partnerEntityMapper.selectClassByPartnerId(partner_id);
	}

	@Override
	public List<Map<String,Object>> getBrands(Map<String, Long> param) {
		return brandEntityMapper.selectBrandByPartnerIdAndClassId(param);
	}

	@Override
	public List<Shop> getShops(Long brand_id) {
		return shopEntityMapper.selectShopByBrandId(brand_id);
	}

	@Override
	public ResponseMessage<String> addSellerService(BaseSellerServiceEntity entity) {
		 ResponseMessage<String> message = new ResponseMessage<>();
		entity.setCreate_time(new Date());
		if(entity.getService_type()==2){
			SellerServiceModel sellerServiceModel = sellerServiceMapper.selectByLBSWithoutSellerId(entity);
			if(sellerServiceModel!=null){
				message.setCode(SysConstant.LBS_OCCUPIED_CODE);
				message.setMessage(SysConstant.LBS_OCCUPIED_MESSAGE);
			}else{
				sellerServiceMapper.hideService(entity.getSeller_id());
				if( sellerServiceMapper.insertSelective(entity)>0){
					message.setCode(SysConstant.SUCCESS_CODE);
					message.setMessage(SysConstant.SUCCESS_CODE);
				}else{
					message.setCode(SysConstant.FAILURE_CODE);
					message.setMessage(SysConstant.FAILURE_MSG);
				}
			}
		}else{
			sellerServiceMapper.hideService(entity.getSeller_id());
			if( sellerServiceMapper.insertSelective(entity)>0){
				message.setCode(SysConstant.SUCCESS_CODE);
				message.setMessage(SysConstant.SUCCESS_CODE);
			}else{
				message.setCode(SysConstant.FAILURE_CODE);
				message.setMessage(SysConstant.FAILURE_MSG);
			}
		}
		
		return message;
	}

	@Override
	public List<SellerServiceModel> getSellerServices(SellerServiceModel serviceModel) {
		List<SellerServiceModel> list = sellerServiceMapper.selectByClassIdAndReionCode(serviceModel);
		if(list.size()>0){
		for (SellerServiceModel sellerServiceModel : list) {
			sellerServiceModel.setDistance(DistanceUtil.getDistance(serviceModel.getCurrent_lbs(), sellerServiceModel.getLbs()));
		}
		}
			return list;
	}

	@Override
	public List<SellerServiceModel> getConstructServices(SellerServiceModel serviceModel) {
		return	sellerServiceMapper.selectByConstructTypeAndReionCode(serviceModel);
	}
	
	@Override
	public SellerServiceModel getSellerServiceDetail(SellerServiceModel serviceModel) {
		switch (serviceModel.getService_type()){
		case SERVICE_TYPE:
			return sellerServiceMapper.selectDetailById(serviceModel.getId());
		case CONSTRUCT_TYPE:
			return sellerServiceMapper.selectConstructServiceDetailById(serviceModel.getId());
            default : return null;
		}
		
	}
	@Override
	public SellerServiceModel getSellerService(SellerServiceModel serviceModel) {
		switch (serviceModel.getService_type()){
		case SERVICE_TYPE:
			SellerServiceModel model = sellerServiceMapper.selectById(serviceModel.getId());
			model.setDistance(DistanceUtil.getDistance(serviceModel.getLbs(), model.getLbs()));
			return model;
		case CONSTRUCT_TYPE:
			SellerServiceModel constructModel = sellerServiceMapper.selectConstructServiceById(serviceModel.getId());
			//添加距离
			constructModel.setDistance(DistanceUtil.getDistance(serviceModel.getLbs(), constructModel.getLbs()));
			return constructModel;
            default : return null;
		}
	
	}

	@Override
	public List<AskForHelpModel> getAskForHelps(AskForHelpModel askForHelpModel) {
		return askhelpMapper.selectByRegionCode(askForHelpModel.getRegion_code());
	}

	@Override
	public AskForHelpModel getAskForHelp(AskForHelpModel askForHelpModel) {
		AskForHelpModel model = askhelpMapper.selectById(askForHelpModel.getId());
		//添加距离
		model.setDistance(DistanceUtil.getDistance(askForHelpModel.getLbs(),model.getLbs()));
		return model;
	}

	@Override
	public boolean addAskForHelp(BaseAskhelpEntity askhelpEntity) {
		if(askhelpMapper.selectByLBSAndUserId(askhelpEntity).size()==0){
		askhelpEntity.setCreate_time(new Date());
		return askhelpMapper.insertSelective(askhelpEntity)>0;
		}else{
		return false;
	}
	}

}
