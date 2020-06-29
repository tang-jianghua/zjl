package com.mfangsoft.zhuangjialong.integration.newproduct.mapper;

import com.mfangsoft.zhuangjialong.integration.newproduct.model.BaseBrandProductEntity;

public interface BaseBrandProductEntityMapper {
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
    int insert(BaseBrandProductEntity record);

    int insertSelective(BaseBrandProductEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BaseBrandProductEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseBrandProductEntity record);

    int updateByPrimaryKeyWithBLOBs(BaseBrandProductEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BaseBrandProductEntity record);
}