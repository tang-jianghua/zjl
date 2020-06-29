package com.mfangsoft.zhuangjialong.app.article.mapper;

import com.mfangsoft.zhuangjialong.app.article.model.BaseArticleCollectionEntity;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;

@WriterRepository
public interface BaseArticleCollectionEntityMapper {
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
    int insert(BaseArticleCollectionEntity record);

    int insertSelective(BaseArticleCollectionEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BaseArticleCollectionEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BaseArticleCollectionEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BaseArticleCollectionEntity record);
}