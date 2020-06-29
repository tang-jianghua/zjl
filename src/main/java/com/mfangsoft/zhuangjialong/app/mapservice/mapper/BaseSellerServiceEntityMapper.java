package com.mfangsoft.zhuangjialong.app.mapservice.mapper;

import com.mfangsoft.zhuangjialong.app.mapservice.model.BaseSellerServiceEntity;

public interface BaseSellerServiceEntityMapper {
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
    int insert(BaseSellerServiceEntity record);

    int insertSelective(BaseSellerServiceEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BaseSellerServiceEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BaseSellerServiceEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BaseSellerServiceEntity record);
}