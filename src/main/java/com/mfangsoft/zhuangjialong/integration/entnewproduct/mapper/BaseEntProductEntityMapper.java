package com.mfangsoft.zhuangjialong.integration.entnewproduct.mapper;

import com.mfangsoft.zhuangjialong.integration.entnewproduct.model.BaseEntProductEntity;

public interface BaseEntProductEntityMapper {
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
    int insert(BaseEntProductEntity record);

    int insertSelective(BaseEntProductEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BaseEntProductEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseEntProductEntity record);

    int updateByPrimaryKeyWithBLOBs(BaseEntProductEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BaseEntProductEntity record);
}