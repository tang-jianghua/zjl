package com.mfangsoft.zhuangjialong.integration.newcoupons.mapper;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.newcoupons.model.BaseBrandCouponsEntity;
@WriterRepository
public interface BaseBrandCouponsEntityMapper {
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
    int insert(BaseBrandCouponsEntity record);

    int insertSelective(BaseBrandCouponsEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BaseBrandCouponsEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseBrandCouponsEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BaseBrandCouponsEntity record);
}