package com.mfangsoft.zhuangjialong.integration.promotion.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.app.product.model.Product;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionEntityStepParam;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionStepProductEntity;

@WriterRepository
public interface PromotionStepProductEntityMapper {
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
    int insert(PromotionStepProductEntity record);

    int insertSelective(PromotionStepProductEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    PromotionStepProductEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PromotionStepProductEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(PromotionStepProductEntity record);
    
    List<Product> selectProductOfOneStepPromotionForBrand(PromotionStepProductEntity param);
    
    PromotionEntityStepParam selectProductOfOneStepPromotionForPlatform(PromotionEntityStepParam param);
    
    Integer selectProductStateForBrandId(@Param(value = "promotion_id") Long promotion_id, @Param(value = "brand_id") Long brand_id);
    
    int deleteByBrandIdForTimePart(@Param(value = "promotion_id") Long promotion_id, @Param(value = "brand_id") Long brand_id);
    
    List<Product> selectProductOfOnePromotion(@Param(value = "promotion_id")Long promotion_id, @Param(value = "region_code")String region_code);
    
    List<Product> selectAppPromotionProductforPage(Page page);
}