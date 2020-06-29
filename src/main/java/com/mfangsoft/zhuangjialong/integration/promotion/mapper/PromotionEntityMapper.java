package com.mfangsoft.zhuangjialong.integration.promotion.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.app.product.model.Product;
import com.mfangsoft.zhuangjialong.app.promotion.model.PromotionTimer;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.promotion.model.BrandProduct;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionEntityStepParam;
@WriterRepository
public interface PromotionEntityMapper {
	
	List<PromotionEntity> selectAll();
	
	List<Map<String,String>> getSencondKillPromotionForPage(Page<Map<String,String>> page);
	
	PromotionEntity selectSencondKillPromotionByPrimaryKey(@Param("id")Long id, @Param("brand_id")Long brand_id);
	
	List<Map<String,String>> getbrandpromotionList1ByPage(Page<Map<String,String>> page);
	
	List<PromotionEntity> getbrandpromotionList2ByPage(Page<PromotionEntity> page);
	
	List<PromotionTimer> selectpromotionTimeForAppByRegionCode(@Param("region_code")String region_code);
	
	List<PromotionTimer> selectpromotionTime(@Param("pid")Long pid);
	
	List<Product> selectpromotionProductForPage(Page<Product> page);
	
	List<Product> selectpromotionProductFor2Page(Page<Product> page);
	
	List<PromotionTimer> selectNoteProductByCustomerId(@Param("customer_id")Long customer_id);
	
	List<Map<String,Object>> selectpromotionProductForBrandByPage(Page<Map<String,Object>> page);
	
	List<Long> selectUnioPromotionProductList(@Param("pid")Long pid);
	
	List<Map<String,String>> selectProductOfOnepromotionForBrandByPage(Page<Map<String,String>> page);
	
	List<PromotionTimer> selectBrandListOfOnepromotionForPlatform(@Param("pid")Long pid, @Param("time_id")Long time_id);
	
	PromotionTimer selectProductOfOnepromotionForPlatform(@Param("pid")Long pid, @Param("time_id")Long time_id, @Param("brand_id")Long brand_id, @Param("stateList")Integer[] stateList);
	/**
	 * 秒杀条件判断
	 */
	Boolean checkTotalProductSumconditionForCustomer(@Param("pid")Long pid, @Param("time_id")Long time_id, @Param("product_id")Long product_id);
	/**
	 * 秒杀条件判断
	 */
	Boolean checkPersonProductSumconditionForCustomer(@Param("pid")Long pid, @Param("time_id")Long time_id, @Param("product_id")Long product_id);
	
	List<Product> selectpromotionproductforcart(String region_code);
	
	Integer selectPromotionProductAndPriceForOrder(@Param("pid")Long pid, @Param("time_id")Long time_id, @Param("product_id")Long product_id);
	
	List<Product> selectPromotionProductAndPriceForCart(@Param("customer_id") Long customer_id, @Param("region_code") String region_code);
	
//	selectpromotionpriceforcart
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
    int insert(PromotionEntity record);

    int insertSelective(PromotionEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    PromotionEntity selectByPrimaryKey(Long id);

    int updateBrandIdByPrimaryKey(@Param(value = "id") Long id, @Param(value = "brand_id") String brand_id);
    
    HashMap<String, Object> selectByPrimaryKeyForStep(@Param(value = "id")Long id, @Param(value = "brand_id")Long brand_id);
    
    int updateByPrimaryKeySelective(PromotionEntity record);

    int updateByPrimaryKeyWithBLOBs(PromotionEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(PromotionEntity record);
    
    
    List<Map<String,Object>> queryPromotionLinkPage(Page<Map<String,Object>> page);
    
    List<Map<String,Object>> getStepPromotionForPage(Page<Map<String,Object>> page);
    
    List<PromotionEntityStepParam> querypromotionforfirstpage(String region_code);
    
    PromotionEntityStepParam selectPromotionByPrimaryKey(@Param(value = "id")Long id);
    
    List<BrandProduct> selectPromotionBrandInfoById(@Param(value = "id")Long id, @Param(value = "region_code")String region_code);
    
    PromotionEntityStepParam selectManPromotionForCartOneProduct(@Param(value = "type")Integer type, @Param(value = "region_code")String region_code, @Param(value = "brand_id")Long brand_id, @Param(value = "product_id")Long product_id);

    PromotionEntityStepParam selectManPromotionByPrimaryId(@Param(value = "id")Long id);

}