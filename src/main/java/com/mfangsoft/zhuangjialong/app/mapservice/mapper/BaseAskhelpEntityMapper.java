package com.mfangsoft.zhuangjialong.app.mapservice.mapper;

import com.mfangsoft.zhuangjialong.app.mapservice.model.BaseAskhelpEntity;

public interface BaseAskhelpEntityMapper {
    /**
     * 通过主键删除  
     *
     * @MLTH_generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入数据  
     *
     * @MLTH_generated
     */
    int insert(BaseAskhelpEntity record);

    int insertSelective(BaseAskhelpEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BaseAskhelpEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BaseAskhelpEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BaseAskhelpEntity record);
}