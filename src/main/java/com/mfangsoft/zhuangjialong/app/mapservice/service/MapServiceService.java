package com.mfangsoft.zhuangjialong.app.mapservice.service;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.app.buildClass.model.BuildClass;
import com.mfangsoft.zhuangjialong.app.mapservice.model.AskForHelpModel;
import com.mfangsoft.zhuangjialong.app.mapservice.model.BaseAskhelpEntity;
import com.mfangsoft.zhuangjialong.app.mapservice.model.BaseSellerServiceEntity;
import com.mfangsoft.zhuangjialong.app.mapservice.model.SellerServiceModel;
import com.mfangsoft.zhuangjialong.app.shop.model.Shop;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年12月14日
* 
*/

public interface MapServiceService {

	/*
	 * 根据城市合伙人获取品类
	 */
	public List<BuildClass> getClasses(Long partner_id);
	
	/*
	 * 根据城市合伙人和品类获取品牌
	 */
	public List<Map<String,Object>> getBrands(Map<String, Long> param);
	
	/*
	 * 根据品牌获取店铺
	 */
	public List<Shop> getShops(Long brand_id);
	
	/*
	 * 发布服务
	 */
	public ResponseMessage<String> addSellerService(BaseSellerServiceEntity entity);

	/*
	 * 查询服务
	 */
	public List<SellerServiceModel> getSellerServices(SellerServiceModel serviceModel);
	
	/*
	 * 查询施工服务
	 */
	public List<SellerServiceModel> getConstructServices(SellerServiceModel serviceModel);
	
	/*
	 * 查询服务详情
	 */
	public SellerServiceModel getSellerServiceDetail(SellerServiceModel serviceModel);
	/*
	 * 查询服务概要
	 */
	public SellerServiceModel getSellerService(SellerServiceModel serviceModel);
	
	/*
	 * 查询求助
	 */
	public List<AskForHelpModel> getAskForHelps(AskForHelpModel askForHelpModel);
	
	/*
	 * 查询单个求助
	 */
	public AskForHelpModel getAskForHelp(AskForHelpModel askForHelpModel);
	
	/*
	 * 发布求助
	 */
	public boolean addAskForHelp(BaseAskhelpEntity askhelpEntity);
}
