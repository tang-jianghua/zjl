package com.mfangsoft.zhuangjialong.integration.newproduct.mapper;

import com.mfangsoft.zhuangjialong.integration.newproduct.model.BaseClassAttrEntity;

public interface BaseClassAttrEntityMapper {
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
    int insert(BaseClassAttrEntity record);

    int insertSelective(BaseClassAttrEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BaseClassAttrEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseClassAttrEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BaseClassAttrEntity record);
}