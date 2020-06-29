package com.mfangsoft.zhuangjialong.integration.promotion.mapper;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionZhekouProductEntity;

@WriterRepository
public interface PromotionZhekouProductEntityMapper {
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
    int insert(PromotionZhekouProductEntity record);

    int insertSelective(PromotionZhekouProductEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    PromotionZhekouProductEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PromotionZhekouProductEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(PromotionZhekouProductEntity record);
    
    List<String> selectedExistZhekouPromotionBrandProduct(Map<String, Object> map);
    
    List<Map<String, Object>> selectAllProductForPromotionForBrandByPage(Page<Map<String, Object>> page);
    
}