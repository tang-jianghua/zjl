package com.mfangsoft.zhuangjialong.app.order.mapper;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.app.order.model.OrderCouponsEntity;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;

@WriterRepository
public interface OrderCouponsEntityMapper {
	/**
	 * 通过主键删除
	 *
	 * @MLTH_generated
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * 插入数据
	 *
	 * @MLTH_generated
	 */
	int insert(OrderCouponsEntity record);

	int insertSelective(OrderCouponsEntity record);

	/**
	 * 通过主键查询数据
	 *
	 * @MLTH_generated
	 */
	OrderCouponsEntity selectByPrimaryKey(Long id);

	OrderCouponsEntity selectByOrderId(@Param("code")String code);
	
	int updateByPrimaryKeySelective(OrderCouponsEntity record);

	/**
	 * 通过主键更新数据
	 *
	 * @MLTH_generated
	 */
	int updateByPrimaryKey(OrderCouponsEntity record);
	
	
	OrderCouponsEntity selectOrderCouponsByOrderId(Long order_id);
}