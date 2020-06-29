package com.mfangsoft.zhuangjialong.app.mapservice.mapper;

import java.util.List;

import com.mfangsoft.zhuangjialong.app.mapservice.model.BaseSellerServiceEntity;
import com.mfangsoft.zhuangjialong.app.mapservice.model.SellerServiceModel;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年12月14日
* 
*/
@WriterRepository
public interface SellerServiceMapper extends BaseSellerServiceEntityMapper{

	
	SellerServiceModel selectBySellerId(Long seller_Id);
	
	/*
	 * 隐藏某卖家的所有服务
	 */
	int hideService(Long seller_id);
	
	/*
	 * 查询服务
	 */
	List<SellerServiceModel> selectByClassIdAndReionCode(SellerServiceModel model);
	
	/*
	 * 查询服务
	 */
	List<SellerServiceModel> selectByConstructTypeAndReionCode(SellerServiceModel model);
	
	/*
	 * 查询服务详情
	 */
	SellerServiceModel selectDetailById(Integer id);
	/*
	 * 查询施工服务详情
	 */
	SellerServiceModel selectConstructServiceDetailById(Integer id);
	
	/*
	 * 查询服务概要
	 */
	SellerServiceModel selectById(Integer id);
	/*
	 * 查询施工服务概要
	 */
	SellerServiceModel selectConstructServiceById(Integer id);
	
	/*
	 * 查询某个坐标不包含某个服务的其他服务
	 */
	SellerServiceModel selectByLBSWithoutSellerId(BaseSellerServiceEntity model);
}
