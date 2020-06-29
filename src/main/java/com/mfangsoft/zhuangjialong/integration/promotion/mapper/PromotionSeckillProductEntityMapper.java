package com.mfangsoft.zhuangjialong.integration.promotion.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.promotion.model.ProductState;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionSeckillProductEntity;
@WriterRepository
public interface PromotionSeckillProductEntityMapper {
	
	  /**
     * 获取此时正参与秒杀的产品id及价格 
     *
     * @MLTH_generated
     */
	List<PromotionSeckillProductEntity> selectProductIdAndPrice();
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
    int insert(PromotionSeckillProductEntity record);

    int insertSelective(PromotionSeckillProductEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    PromotionSeckillProductEntity selectByPrimaryKey(Long id);
    
    PromotionSeckillProductEntity selectByPidTimeIdProductId(@Param("pid")Long pid, @Param("time_id")Long time_id, @Param("product_id")Long product_id);

    int updateByPrimaryKeySelective(PromotionSeckillProductEntity record);

    int updateSellNumByPrimaryId(PromotionSeckillProductEntity record);
    
    List<ProductState> selectStateCountByPIdBrandIdForBrand(@Param("pid")Long pid, @Param("brand_id")Long brand_id);
    
    Integer selectbrandCountByPIdBrandIdForBrand(@Param("pid")Long pid, @Param("brand_id")Long brand_id);
    
    Integer selectProductStateForBrandId(@Param("time_id")Long time_id, @Param("brand_id")Long brand_id);
    
   int updateByPidTimeidProductIdSelective(PromotionSeckillProductEntity record);
   
   int updateSellNumByPrimaryIdForCancelOrder(PromotionSeckillProductEntity record);
   
    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(PromotionSeckillProductEntity record);
    
    int selectSumproductByPidTimeId(@Param("pid")Long pid, @Param("time_id")Long time_id, @Param("brand_id")Long brand_id);
    
    int deleteByPidTimeidProductId(PromotionSeckillProductEntity record);
    
    Integer deleteByBrandIdForTimePart(@Param("time_id")Long time_id, @Param("brand_id")Long brand_id);
    
    int updateSellNum_1();
    
    int updateNoteCount(@Param("pid")Long pid, @Param("time_id")Long time_id, @Param("product_id")Long product_id);
    
    int updateDecresrNoteCount(@Param("pid")Long pid, @Param("time_id")Long time_id, @Param("product_id")Long product_id);
    
    /*
     * 根据time_id查询秒杀产品及价格
     */
    List<Map<String, Object>> selectProductAndPriceByTimeId(Long time_id);
    /*
     * 根据time_id查询秒杀产品及原价
     */
    List<Map<String, Object>> selectProductAndOldPriceByTimeId(Long time_id);
    
    List<String> selectedExistScendSkillPromotionBrandProduct(Map<String,Object> map);
    
    List<String> selectedExistManPromotionBrandProduct(Map<String,Object> map);
}