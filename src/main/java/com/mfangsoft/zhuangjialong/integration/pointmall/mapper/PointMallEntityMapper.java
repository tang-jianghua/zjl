package com.mfangsoft.zhuangjialong.integration.pointmall.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.app.pointmall.model.PointProductModel;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.pointmall.model.BaseMallFlowEntity;
import com.mfangsoft.zhuangjialong.integration.pointmall.model.BasePointConvertEntity;
import com.mfangsoft.zhuangjialong.integration.pointmall.model.PointMallEntity;
@WriterRepository
public interface PointMallEntityMapper extends BasePointMallEntityMapper {
	
	
	
	
	
	List<Map<String, Object>> queryPoinMallPage(Page<Map<String, Object>> page);
	
	
	List<Map<String, Object>> queryOperator();
	
	
	List<Map<String, Object>> queryFlowPackage(String operator_code);
	
	
	List<Map<String, Object>> queryPartner();
	
	
	
	List<Map<String, Object>> queryBrand(List<Long> partnerId);
	
	
	List<Map<String, Object>> queryAllBrand();
	
	
	List<Map<String, Object>> queryShop(List<Long> brandId);
	
	
	List<Map<String, Object>> queryPointConverPage(Page<Map<String, Object>> page);
	
	
	BasePointConvertEntity  querysPointConvert(@Param("convertCode") String convertCode,@Param("shop_id") String shop_id);
	
	
	List<BaseMallFlowEntity> queryMallFlowByProductId(Long productId);
	
	PointMallEntity  selectAllByPrimaryKey(Long id);
	
	/*
	 * 查询积分商城的产品
	 * 参数：region_code
	 */
	List<PointProductModel> queryPointProductsForPage(Page<PointProductModel> pages);
	

	
	/*
	 * 查询积分产品详情
	 * 参数：region_code
	 */
	PointProductModel queryPointProductDetails(Long id);
	
}
