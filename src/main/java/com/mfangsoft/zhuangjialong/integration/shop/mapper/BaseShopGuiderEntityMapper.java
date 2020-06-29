package com.mfangsoft.zhuangjialong.integration.shop.mapper;

import com.mfangsoft.zhuangjialong.integration.shop.model.BaseShopGuiderEntity;

public interface BaseShopGuiderEntityMapper {
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
    int insert(BaseShopGuiderEntity record);

    int insertSelective(BaseShopGuiderEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BaseShopGuiderEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseShopGuiderEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BaseShopGuiderEntity record);
}