package com.mfangsoft.zhuangjialong.integration.pointmall.mapper;

import java.util.List;
import java.util.Map;import org.omg.CORBA.OBJ_ADAPTER;

import com.mfangsoft.zhuangjialong.app.pointmall.model.ConvertProductModel;
import com.mfangsoft.zhuangjialong.app.pointmall.model.FlowPackageModel;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年9月23日
* 
*/
@WriterRepository
public interface PointConvertEntityMapper  extends BasePointConvertEntityMapper{
	
	/*
	 *根据id查询数据 
	 */
	List<ConvertProductModel> selectAllByPrimaryKey(ConvertProductModel convertProductModel);
	
	/*
	 * 临时测试
	 */
	List<ConvertProductModel> selectUnConvertRecordAtTime();

	/*
	 * 查询兑换记录
	 */
	List<ConvertProductModel> selectConvertRecordForPage(Page<ConvertProductModel> page);
	
	/*
	 * 查询积分流量套餐
	 */
	List<ConvertProductModel> selectFlowPackage(String convert_code);
	
	/*
	 * 查询产品库存
	 */
	Integer selectPointProductConvertedNo(Long product_id);
	
	/*
	 * 查询流量包的套餐值
	 */
	List<String> selectFlowByProductId(Long product_id);
	/*
	 * 查询流量包的套餐值
	 */
	List<String> selectFlowByConvertCode(String convert_code);
	
	/*
	 * 根据主键查询流量套餐详情
	 */
	FlowPackageModel selectFlowDetailsById(Long id);
	
	
	/*
	 * 查看消费者的第一个流量包的使用状态
	 */
	ConvertProductModel selectFirstFlowConvertByCustomerId(Long customer_Id);
	
	/*
	 * 通过兑换码将用户的流量包改为已使用状态
	 */
	int updateConvertStateByConvertCode(Map<String, Object> map);
	/*
	 * 通过消费者账号获取第一个流量包
	 */
	ConvertProductModel selectFirstFlowConvertByAccount(String account);
}
