package com.mfangsoft.zhuangjialong.app.order.mapper;

import com.mfangsoft.zhuangjialong.app.order.model.OrderStateRelationEntity;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;

@WriterRepository
public interface OrderStateRelationEntityMapper {
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
	int insert(OrderStateRelationEntity record);

	int insertSelective(OrderStateRelationEntity record);
	
	long insertWithCode(OrderStateRelationEntity record);

	/**
	 * 通过主键查询数据
	 *
	 * @MLTH_generated
	 */
	OrderStateRelationEntity selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(OrderStateRelationEntity record);

	/**
	 * 通过主键更新数据
	 *
	 * @MLTH_generated
	 */
	int updateByPrimaryKey(OrderStateRelationEntity record);
}