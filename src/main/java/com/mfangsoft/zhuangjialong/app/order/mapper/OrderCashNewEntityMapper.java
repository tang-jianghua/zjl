package com.mfangsoft.zhuangjialong.app.order.mapper;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.app.order.model.OrderCashNewEntity;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;

@WriterRepository
public interface OrderCashNewEntityMapper {
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
    int insert(OrderCashNewEntity record);

    int insertSelective(OrderCashNewEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    OrderCashNewEntity selectByPrimaryKey(Long id);
    
    OrderCashNewEntity selectInfoForNewOrder(@Param("customer_id") Long customer_id,@Param("id") Long cash_id);
    
    OrderCashNewEntity selectByCode(@Param("code") String code);
    
    int updateByPrimaryKeySelective(OrderCashNewEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(OrderCashNewEntity record);
}