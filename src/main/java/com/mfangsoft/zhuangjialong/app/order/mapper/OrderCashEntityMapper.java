package com.mfangsoft.zhuangjialong.app.order.mapper;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.app.order.model.OrderCashEntity;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
@WriterRepository
public interface OrderCashEntityMapper {
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
    int insert(OrderCashEntity record);

    long insertSelective(OrderCashEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    OrderCashEntity selectByPrimaryKey(Long id);
    OrderCashEntity selectInfoForNewOrder(@Param("customer_id") Long customer_id,@Param("promotion_id") Long promotion_id);
    
    OrderCashEntity selectByCode(@Param("code") String code);
    
    int updateByPrimaryKeySelective(OrderCashEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(OrderCashEntity record);
}