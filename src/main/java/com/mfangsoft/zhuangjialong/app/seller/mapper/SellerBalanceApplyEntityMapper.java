package com.mfangsoft.zhuangjialong.app.seller.mapper;

import com.mfangsoft.zhuangjialong.app.seller.model.SellerBalanceApplyEntity;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
@WriterRepository
public interface SellerBalanceApplyEntityMapper {
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
    int insert(SellerBalanceApplyEntity record);

    int insertSelective(SellerBalanceApplyEntity record);
    
    SellerBalanceApplyEntity selectBySellerId(Long id);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    SellerBalanceApplyEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SellerBalanceApplyEntity record);

    int updateByPrimaryKeyWithBLOBs(SellerBalanceApplyEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(SellerBalanceApplyEntity record);
}