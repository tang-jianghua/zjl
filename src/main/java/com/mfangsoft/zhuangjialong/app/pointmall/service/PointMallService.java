package com.mfangsoft.zhuangjialong.app.pointmall.service;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.app.personalCenter.model.CustomerPointEntity;
import com.mfangsoft.zhuangjialong.app.pointmall.model.ConvertProductModel;
import com.mfangsoft.zhuangjialong.app.pointmall.model.PointMallModel;
import com.mfangsoft.zhuangjialong.app.pointmall.model.PointProductModel;
import com.mfangsoft.zhuangjialong.common.model.Page;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年9月22日
* 
*/

public interface PointMallService {
      /*
       * 查询积分商城首页
       */
	PointMallModel selectPointMall(Page<PointProductModel> pages);
	
	   /*
     * 查询积分
     */
	PointMallModel selectPoint(Page<CustomerPointEntity> pages);
	
	/*
	 * 兑换商品
	 */
	String addConvertProduct(ConvertProductModel convertProductModel);
	
	/*
	 * 给手机号添加兑换记录
	 */
	Map<String, Object> addPointConvert(ConvertProductModel convertProductModel); 

	/*
	 * 查询兑换记录
	 */
	Page<ConvertProductModel> selectConvertRecordForPage(Page<ConvertProductModel> page);
	
	/*
	 * 使用已兑换的流量
	 */
	Integer updateConvertFlow(ConvertProductModel convertProductModel);
	
	/*
	 * 成批使用已兑换的流量
	 */
	Map<String, Object> updateConvertFlows(ConvertProductModel convertProductModel);
	
	/*
	 * 查看积分产品详情
	 */
	PointProductModel selectPointProductDetails(ConvertProductModel convertProductModel);
	
	/*
	 * 根据兑换码修改兑换使用状态
	 * 
	 * param:
	 *taskNo 兑换码
	 *state 状态
	 */
	public  boolean updateFlowUsedStateByConvertCode(Map<String, Object> map);
}
