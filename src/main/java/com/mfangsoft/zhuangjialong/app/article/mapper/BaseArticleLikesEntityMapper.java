package com.mfangsoft.zhuangjialong.app.article.mapper;

import com.mfangsoft.zhuangjialong.app.article.model.BaseArticleLikesEntity;

public interface BaseArticleLikesEntityMapper {
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
    int insert(BaseArticleLikesEntity record);

    int insertSelective(BaseArticleLikesEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BaseArticleLikesEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BaseArticleLikesEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BaseArticleLikesEntity record);
}