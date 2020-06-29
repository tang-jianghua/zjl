package com.mfangsoft.zhuangjialong.integration.newproduct.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.app.brand.model.BrandNewProduct;
import com.mfangsoft.zhuangjialong.app.buildClass.model.BuildClass;
import com.mfangsoft.zhuangjialong.app.order.model.OrderProduct;
import com.mfangsoft.zhuangjialong.app.product.model.ProductDetails;
import com.mfangsoft.zhuangjialong.app.product.model.ProductListModel;
import com.mfangsoft.zhuangjialong.app.product.model.SelectPropertiesModel;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BrandProdAttrValueName;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BrandProductEntity;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BrandProductOneSaleModel;
@WriterRepository
public interface BrandNewProductEntityMapper extends BaseBrandProductEntityMapper {
	
	
	BrandProductEntity selectAllByPrimaryKey(Long id);
	
	OrderProduct selectNameImgByPrimaryKey(Long id);
	
	List<Map<String,Object>> selectBrandProductForPage(Page<Map<String,Object>> map);
	
	
	List<Map<String,Object>> getBrandDevProductListForPage(Page<Map<String,Object>> map);
	
	
	int updateBrandProduct(Map<String,Object> map);
	
	BrandProductOneSaleModel selectProductOneSaleInfo(@Param("product_id")Long product_id, @Param("sale_id")Long sale_id);
	
	List<Map<String,Object>> selectViewbyproduct(Long product_id);
	
	BrandProdAttrValueName  selectproductAttrValueNameByProductId(Long id);
	
	List<BrandProdAttrValueName> selectproductAttrValueName();
	
	
	List<Map<String,Object>> selectAllBrandProduct(Map<String,Object> map);
	
	
	int deleteProdSeries(Long product_id);
	
	int deleteProdsalesattr(Long product_id);
	
	int deleteProdImage(Long product_id);
	
	int deleteProdAttrvalue(Long product_id);
	
	List<ProductListModel> selectPriceInfoByBrandId(Long brand_id);
	
	List<Map<String,Object>> selectAllEntProduct(Map<String,Object> map);
	
	
	int deleteEntProdAttrvalue(Long product_id);
	
	int deleteEntProdImage(Long product_id);
	
	int deleteEntProdsalesattr(Long product_id);
	
	int deleteEntProdSeries(Long product_id);
	
	int deleteEntProduct(Long product_id);
	
	
	List<Long> selectPromotionProductByProductId(List<Long> product_id);
	
	
    List<Map<String, Object>> selectProductForBannerPage(Page<Map<String, Object>> page);
    
    
    List<Long> selectBrandProductByBrandId(@Param("brand_id")Long brand_id,@Param("class_id") Long class_id);
	
    List<Map<String, Object>> getBrandHotClassifyProductPage(Page<Map<String, Object>> page);
    
    Long selectBrandProductByTmpflag(Map<String,Object> map);
    
	/**
	 * 
	* @description：查询品类默认产品
	* @param：
	 */
	List<ProductListModel> selectDefaultProductsForPage(Page<ProductListModel> page);
	
	/**
	 * 
	* @description：产品活动类型
	* @param：
	*  1折扣券 2 折扣 3满减 4秒杀 5一口价 6联盟
	 */
	List<Integer> selectPromotionTypesByProductId(Long product_id);
	
	/**
	 * 
	* @description：查询品类产品
	* @param：
	 */
	List<ProductListModel> selectClassProductsForPage(Page<ProductListModel> page);
	
	/**
	 * 
	* @description：产品甄选
	* @param：
	 */
	List<ProductListModel> selectProductSelectionForPage(Page<ProductListModel> page);
	
	/**
	 * 
	* @description：产品详情页
	* @param：
	 */
	ProductDetails selectProductDetailsByProductId(Long product_id);
	
	/**
	 * 
	* @description：产品图文详情
	* @param：
	 */
	String selectDescriptionByProductId(Long product_id);
	
	/**
	 * 
	* @description：产品筛选属性
	* @param：
	 */
    List<SelectPropertiesModel> selectAttrsByProductId(Long product_id);
    
	/**
	 * 
	* @description：根据区域编码查询筛选属性
	* @param：
	 */
    List<SelectPropertiesModel> selectAttrsByRegoinCodeClassId(BuildClass param);
    
	/**
	 * 
	* @description：产品秒杀价
	* @param：
	 */
	Double selectSecKillPriceByProductId(Long product_id);
    /**
     * 通过产品id查找品类id
     *
     * @MLTH_generated
     */
    Long selectClassIdById(Long id);
    
    /**
     * 通过产品id查询销量
     *
     * @MLTH_generated
     */
     Long  selectSaleNumByProductId(Long product_id);
    /**
     * 品牌首页列表
     * @param page
     * @return
     */
    List<Map<String,Object>> getBrandPaoductBrandMainPage(Page<Map<String,Object>> page);
    
    
    /**
     * 品牌首页列表显示
     * @param page
     * @return
     */
    List<Map<String,Object>> getBrandPaoductBrandMainListPage(Page<Map<String,Object>> page);
    
    
    
    List<Map<String,Object>> queryProductForPromotionForPage(Page<Map<String,Object>> page);
    
    
    List<Long> selectUnionByBrands(@Param("brands") String brands);

	/**
	 * 
	* @description：查询首页产品
	* @param：
	 */
	List<ProductListModel> selectNewMainProductsForPage(Page<ProductListModel> page);

	   /**
     * 通过产品型号，品牌名称，区域编码获取产品id
     * @param page
     * @return
     */
    Long selectProductIdByModel(Map<String, String>  map);
    
    /**
     * 根据壁纸品牌id获取新品上市列表
     *
     * @MLTH_generated
     */
    List<BrandNewProduct>  selectNewProductByBrandIdForPage(Page<BrandNewProduct> page);
    
    
    /**
     * 查询所有产品的状态
     *
     * @MLTH_generated
     */
    
    List<Map<String, Object>> selectAllStateForProductCore();
    /**
     * 查询所有产品的详情
     *
     * @MLTH_generated
     */
    
    List<Map<String, Object>> selectAllDescriptionForProductCore();
    
    List<Map<String, Object>> selectZhekouProductForPage(Page<Map<String,Object>> page);
    
    /**
     * 查询所有产品的删选属性id
     *
     * @MLTH_generated
     */
    List<Map<String, Object>> selectAllAttrValueForProductCore();

}
