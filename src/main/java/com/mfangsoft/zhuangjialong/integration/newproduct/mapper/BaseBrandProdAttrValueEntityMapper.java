package com.mfangsoft.zhuangjialong.integration.newproduct.mapper;

import com.mfangsoft.zhuangjialong.integration.newproduct.model.BaseBrandProdAttrValueEntity;

public interface BaseBrandProdAttrValueEntityMapper {
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
    int insert(BaseBrandProdAttrValueEntity record);

    int insertSelective(BaseBrandProdAttrValueEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BaseBrandProdAttrValueEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseBrandProdAttrValueEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BaseBrandProdAttrValueEntity record);
}