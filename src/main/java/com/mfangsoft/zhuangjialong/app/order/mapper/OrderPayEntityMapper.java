package com.mfangsoft.zhuangjialong.app.order.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.junit.runners.Parameterized.Parameters;

import com.mfangsoft.zhuangjialong.app.order.model.OrderPayEntity;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;

@WriterRepository
public interface OrderPayEntityMapper {
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
	int insert(OrderPayEntity record);

	int insertSelective(OrderPayEntity record);

	/**
	 * 通过主键查询数据
	 *
	 * @MLTH_generated
	 */
	OrderPayEntity selectByPrimaryKey(Long id);
	
	OrderPayEntity selectPayStateByCode(@Param("code")String code);
	
	List<OrderPayEntity> selectPayStateByGroupId(@Param("grouppay_id")String grouppay_id);
/**
 * 校验n个订单是否都未支付
 * @param id
 * @return
 */
	int selectPayStateByCodes(List<String> list);
	
	int updateByPrimaryKeySelective(OrderPayEntity record);
	
	/**
	 * 通过主键更新数据
	 *
	 * @MLTH_generated
	 */
	int updateByPrimaryKey(OrderPayEntity record);

	void updateGroupIdByOrderCode(Map<String, Object> map);

	void updateByGroupIdSelective(OrderPayEntity orderPayEntity);
	
	void updateDepositByOrderCode(OrderPayEntity orderPayEntity);
	
	int updateByOrderIdSelective(OrderPayEntity orderPayEntity);

}