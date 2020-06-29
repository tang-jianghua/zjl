package com.mfangsoft.zhuangjialong.app.article.mapper;

import com.mfangsoft.zhuangjialong.app.article.model.BaseArticleEvaluationEntity;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
@WriterRepository
public interface BaseArticleEvaluationEntityMapper {
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
    int insert(BaseArticleEvaluationEntity record);

    int insertSelective(BaseArticleEvaluationEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BaseArticleEvaluationEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BaseArticleEvaluationEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BaseArticleEvaluationEntity record);
}