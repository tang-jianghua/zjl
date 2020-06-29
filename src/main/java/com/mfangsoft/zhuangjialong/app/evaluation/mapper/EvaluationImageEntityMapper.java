package com.mfangsoft.zhuangjialong.app.evaluation.mapper;

import java.util.List;

import com.mfangsoft.zhuangjialong.app.evaluation.model.EvaluationImageEntity;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;

@WriterRepository
public interface EvaluationImageEntityMapper {
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
    int insert(EvaluationImageEntity record);

    int insertSelective(EvaluationImageEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    EvaluationImageEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(EvaluationImageEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(EvaluationImageEntity record);
    
    /**
     * 通过评论id查询数据 
     *
     * @MLTH_generated
     */
    List<EvaluationImageEntity>  selectByEvaluationId(Long evaluation_id);
    
    List<String> selectImagesByEvaluationId(Long evaluation_id);
}