package com.mfangsoft.zhuangjialong.app.order.mapper;

import com.mfangsoft.zhuangjialong.app.order.model.SellerBalanceDetailEntity;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
@WriterRepository
public interface SellerBalanceDetailEntityMapper {
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
    int insert(SellerBalanceDetailEntity record);

    int insertSelective(SellerBalanceDetailEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    SellerBalanceDetailEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SellerBalanceDetailEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(SellerBalanceDetailEntity record);
}