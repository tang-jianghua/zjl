package com.mfangsoft.zhuangjialong.integration.promotion.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionZhekouEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionZhekouParam;

@WriterRepository
public interface PromotionZhekouEntityMapper {
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
    int insert(PromotionZhekouEntity record);

    int insertSelective(PromotionZhekouEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    PromotionZhekouEntity selectByPrimaryKey(Long id);

    List<Map<String,String>> queryZhekouPromotionForPage(Page<Map<String,String>> param);
    
    int updateByPrimaryKeySelective(PromotionZhekouEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(PromotionZhekouEntity record);
    
    List<PromotionZhekouEntity> selectBetweenList(PromotionZhekouEntity record);
    
    String selectProductBetweenList(PromotionZhekouEntity record);
    
    List<PromotionZhekouParam> selectZheKouPromotionProduct();
    
    List<PromotionZhekouParam> selectZheKouPromotionProduct2();
    
    List<PromotionZhekouParam> selectZheKouPromotionProductWithTime(@Param("start_time") Date start_time,@Param("end_time") Date end_time);
    
    PromotionZhekouEntity selectZhekouToolForCartOneProduct(@Param("brand_id")Long brand_id, @Param("product_id")Long product_id);
    
    String selectProductOfOneTool(@Param("id")Long id);
    
}