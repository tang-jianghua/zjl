package com.mfangsoft.zhuangjialong.app.article.mapper;

import com.mfangsoft.zhuangjialong.app.article.model.BaseMainArticleColumnEntity;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;

@WriterRepository
public interface BaseMainArticleColumnEntityMapper {
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
    int insert(BaseMainArticleColumnEntity record);

    int insertSelective(BaseMainArticleColumnEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BaseMainArticleColumnEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BaseMainArticleColumnEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BaseMainArticleColumnEntity record);
}