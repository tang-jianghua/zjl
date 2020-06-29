package com.mfangsoft.zhuangjialong.integration.promotion.mapper;

import java.util.List;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionStepConditionEntity;

@WriterRepository
public interface PromotionStepConditionEntityMapper {
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
    int insert(PromotionStepConditionEntity record);

    int insertSelective(PromotionStepConditionEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    PromotionStepConditionEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PromotionStepConditionEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(PromotionStepConditionEntity record);
    
    List<PromotionStepConditionEntity> selectListByPromotionId(Long promotion_id);
    
    
    
}