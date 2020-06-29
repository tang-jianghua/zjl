package com.mfangsoft.zhuangjialong.app.cart.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.app.cart.model.CartEntity;
import com.mfangsoft.zhuangjialong.app.cart.model.CartShop;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;

@WriterRepository
public interface CartEntityMapper {
	/**
	 * 通过主键删除
	 *
	 * @MLTH_generated
	 */
	int deleteByPrimaryKey(CartEntity cartEntity);

	/**
	 * 插入数据
	 *
	 * @MLTH_generated
	 */
	int insert(CartEntity record);

	int insertSelective(CartEntity record);

	/**
	 * 通过主键查询数据
	 *
	 * @MLTH_generated
	 */
	CartEntity selectByPrimaryKey(Long id);

	/**
	 * 用于检查是否有相同物品，需要增加个数的
	 */
	CartEntity selectByProductIdAndSaleInfoId(@Param("customer_id") Long customer_id,
			@Param("product_id") Long product_id, @Param("sales_property_id") Long sales_property_id,@Param("shop_id") Long shop_id);

	int updateByPrimaryKeySelective(CartEntity record);

	/**
	 * 通过主键更新数据
	 *
	 * @MLTH_generated
	 */
	int updateByPrimaryKey(CartEntity record);

	/**
	 * 通过消费者id获取购物车数据
	 *
	 * @MLTH_generated
	 */
	List<CartShop> selectByCustomerId(@Param("customer_id")Long customer_id, @Param("region_code")String region_code);

	List<CartShop> selectByCartId(@Param("id")Long id);
	/**
	 * 通过消费者id获取购物车店铺id
	 *
	 * @MLTH_generated
	 */
	List<CartEntity> selectShopIdByCustomerId(Long customer_id);

}