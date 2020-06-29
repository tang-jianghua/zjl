package com.mfangsoft.zhuangjialong.app.brand.service;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.app.brand.model.Brand;
import com.mfangsoft.zhuangjialong.app.brand.model.BrandEnModel;
import com.mfangsoft.zhuangjialong.app.brand.model.BrandModel;
import com.mfangsoft.zhuangjialong.app.brand.model.BrandNewProduct;
import com.mfangsoft.zhuangjialong.app.brand.model.BrandScore;
import com.mfangsoft.zhuangjialong.app.brand.model.RegionModel;
import com.mfangsoft.zhuangjialong.app.brand.model.RegionWithLetter;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.CustomerCollectionEntity;
import com.mfangsoft.zhuangjialong.app.product.model.ProductListModel;
import com.mfangsoft.zhuangjialong.app.shop.model.Shop;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;
import com.mfangsoft.zhuangjialong.integration.brandclassify.model.BrandClassifyEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.BuildClassEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseOneEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseTwoEntity;
import com.mfangsoft.zhuangjialong.integration.prepayment.model.PrepayValue;
import com.mfangsoft.zhuangjialong.integration.shop.model.ShopEntity;

/**
* @description：品牌接口
* @author：Jianghua.Tang 
* @date：2016年6月12日
* 
*/
public interface BrandService {
   
	/**
     * 获取品类列表
     *
     * @MLTH_generated
     */
    public List<BuildClassEntity> selectClassesByRegion(Map<String, String> map);
    
    /**
     * 获取区域品牌列表
     *
     * @MLTH_generated
     */
    public List<BrandEntity> selectRegionBrand(BrandEntity param);
    
    
    /**
     * 获取区域品牌列表(按拼音分类排序)
     *
     * @MLTH_generated
     */
    public List<BrandEnModel> selectRegionBrandForEn(Map<String, Object> map);
    
    /**
     * 根据品类获取品牌列表
     *
     * @MLTH_generated
     */
    List<BrandModel> selectRegionBrandByClassId(Map<String, Object> map);
    


	  /**
   * 收藏产品/关注品牌
   *
   * @MLTH_generated
   */
	public Boolean addCollectProductOrBrand(CustomerCollectionEntity param);
	
	  /**
   * 取消收藏产品/取消关注品牌
   *
   * @MLTH_generated
   */
	Boolean updateCollectProductOrBrand(CustomerCollectionEntity param);
	
	   /**
     * 通过品牌id获取质量评分、服务评分
     *
     * @MLTH_generated
     */
    BrandScore selectTotalScoreByBrandId(Map<String , Long> param);
    
    /**
     * 通过品牌头部信息
     *
     * @MLTH_generated
     */
    BrandModel selectBrandHead(Map<String , Long> param);
    
    /**
     * 通过品牌首页信息
     *
     * @MLTH_generated
     */
    BrandModel selectBrandMain(CustomerCollectionEntity param);
    /**
     * 通过品牌首页信息
     *
     * @MLTH_generated
     */
    BrandModel getBrandMain(CustomerCollectionEntity param);
    /**
     * 通过品牌首页信息
     *
     * @MLTH_generated
     */
    BrandModel getSqlBrandMain(CustomerCollectionEntity param);
    
    /**
     * 通过品牌id获取品牌分类及系列  
     *
     * @MLTH_generated
     */
    List<BrandClassifyEntity> selectBrandClassifyAndSeries(BrandEntity param);
    
    
    /**
     * 获取品牌全部产品
     *
     * @MLTH_generated
     */
    Page<Map<String, Object>> getAllProductForPage(Page<Map<String, Object>> page);
/*    *//**
     * 获取品牌全部产品
     *
     * @MLTH_generated
     *//*
    Page<ProductListModel> getSqlAllProductForPage(Page<ProductListModel> page);*/
    
/*    *//**
     * 根据品牌id获取新品上市列表
     *
     * @MLTH_generated
     *//*
    Page<BrandNewProduct> selectNewProductByBrandIdForPage(Page<BrandNewProduct> page);*/
    
    /**
     * 根据品牌id获取新品上市列表
     *
     * @MLTH_generated
     */
    Page<BrandNewProduct> getNewProductByBrandIdForPage(Page<BrandNewProduct> page);
    
    /**
     * 通过企业id获取企业简介（type传值 0：企业简介）
     *
     * @MLTH_generated
     */
    String selectEnterpriseAboutByBrandId(BrandModel param);
    /**
     * 通过企业id获取企业信息（type传值 1：发展历程）
     *
     * @MLTH_generated
     */
    String selectEnterpriseDevelopmentByBrandId(BrandModel param);
    /**
     * 通过企业id获取企业一级信息（type传值 2：工程案例）
     *
     * @MLTH_generated
     */
    List<EnterpriseOneEntity> selectEnterpriseCaseByBrandId(BrandModel param);
    /**
     * 通过企业id获取企业一级信息（type传值 3：品牌荣誉）
     *
     * @MLTH_generated
     */
    List<EnterpriseOneEntity> selectEnterpriseHonorByBrandId(BrandModel param);
    /**
     * 通过企业id获取企业一级信息（type传值 4：店面风采）
     *
     * @MLTH_generated
     */
    List<EnterpriseOneEntity> selectEnterpriseMienByBrandId(BrandModel param);
    /**
     * 通过企业id获取企业二级信息
     *
     * @MLTH_generated
     */
    List<EnterpriseTwoEntity> selectEnterpriseTwoByOneId(Long one_id);
    
    /**
     * 通过品牌id获取热线电话
     *
     * @MLTH_generated
     */
    String selectHotLineByBrandId(Map<String, Object> param);
    
    /**
     * 通过品牌id获取店铺信息
     *
     * @MLTH_generated
     */
    List<Shop> selectShopInfoByBrandId(ShopEntity param);
    
    /**
     * 查询所有金额 
     *
     * @MLTH_generated
     */
    List<PrepayValue> selectAllPrepayValues();
    
    /**
     * 获取所有品牌服务的所有区域
     *
     * @MLTH_generated
     */
    List<RegionModel> selectServiceRegion();
    
    
    /**
     * 获取所有品牌服务的所有城市
     *
     * @MLTH_generated
     */
    List<RegionWithLetter> selectServiceCityRegion();
    
    /**
     * 获取所有品牌服务的所有城市
     *
     * @MLTH_generated
     */
    boolean selectWhetherInRegion(Map<String, String>  param);
    
    /**
     * 根据区域编码和品类id获取所有品牌
     *
     * @MLTH_generated
     */
    List<Brand> selectBrandsByRegionCodeAndClassIds(Map<String, Object>  param);
}
