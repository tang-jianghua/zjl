package com.mfangsoft.zhuangjialong.app.seller.mapper;

import com.mfangsoft.zhuangjialong.app.seller.model.BaseSellerInfoEntity;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
@WriterRepository
public interface BaseSellerInfoEntityMapper {
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
    int insert(BaseSellerInfoEntity record);

    int insertSelective(BaseSellerInfoEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BaseSellerInfoEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseSellerInfoEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BaseSellerInfoEntity record);
}