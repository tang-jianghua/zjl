package com.mfangsoft.zhuangjialong.integration.question.mapper;

import java.util.List;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.question.model.QuestionEntity;
@WriterRepository
public interface QuestionEntityMapper {
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
    int insert(QuestionEntity record);

    int insertSelective(QuestionEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    QuestionEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QuestionEntity record);

    int updateByPrimaryKeyWithBLOBs(QuestionEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(QuestionEntity record);
    
    /**
     * 通过主键获取问题详情  
     *
     * @MLTH_generated
     */
    String selectDetailsByPrimaryKey(Integer id);
    
    
    List<QuestionEntity> getQuestionForPage(Page<QuestionEntity> page);
}