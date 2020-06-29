package com.mfangsoft.zhuangjialong.app.order.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.app.order.model.OrderProductEntity;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;

@WriterRepository
public interface OrderProductEntityMapper {
	/**
	 * 通过主键删除
	 *
	 * @MLTH_generated
	 */
	int deleteByPrimaryKey(Long id);
	
	List<Long> selectProductIdByOrderId(Long order_id);

	List<OrderProductEntity> selectProductListByOrderIds(@Param("orderIds") String orderIds);
	
	/**
	 * 插入数据
	 *
	 * @MLTH_generated
	 */
	int insert(OrderProductEntity record);

	int insertSelective(OrderProductEntity record);

	/**
	 * 通过主键查询数据
	 *
	 * @MLTH_generated
	 */
	OrderProductEntity selectByPrimaryKey(Long id);

	List<OrderProductEntity> selectListByOrderId(@Param("order_id") Long order_id);
	
	List<OrderProductEntity> selectByOrderCode(@Param("order_code") String order_code);
	
	int updateByPrimaryKeySelective(OrderProductEntity record);

	/**
	 * 通过主键更新数据
	 *
	 * @MLTH_generated
	 */
	int updateByPrimaryKey(OrderProductEntity record);
}