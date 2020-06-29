package com.mfangsoft.zhuangjialong.integration.question.mapper;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.question.model.QuestionTypeEntity;
@WriterRepository
public interface QuestionTypeEntityMapper {
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
    int insert(QuestionTypeEntity record);

    int insertSelective(QuestionTypeEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    QuestionTypeEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QuestionTypeEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(QuestionTypeEntity record);
    
    /**
     * 获取所有问题类型
     *
     * @MLTH_generated
     */
    List<QuestionTypeEntity> selectQuestionTypes(Map<String,Object> map);
    /**
     * 获取所有问题类型以及问题
     *
     * @MLTH_generated
     */
    List<QuestionTypeEntity> selectQuestionsInTypes();
}