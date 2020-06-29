package com.mfangsoft.zhuangjialong.app.product.service;





import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.app.buildClass.model.BuildClass;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.CustomerCollectionEntity;
import com.mfangsoft.zhuangjialong.app.product.model.AppProductModel;
import com.mfangsoft.zhuangjialong.app.product.model.ClassProductModel;
import com.mfangsoft.zhuangjialong.app.product.model.Product;
import com.mfangsoft.zhuangjialong.app.product.model.ProductDetails;
import com.mfangsoft.zhuangjialong.app.product.model.ProductListModel;
import com.mfangsoft.zhuangjialong.app.product.model.SalesProperty;
import com.mfangsoft.zhuangjialong.app.product.model.Salesinfo;
import com.mfangsoft.zhuangjialong.app.product.model.SelectPropertiesModel;
import com.mfangsoft.zhuangjialong.app.shop.model.Shop;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BrandProdSalesAttrEntity;


/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年6月12日
* 
*/

public interface ProductService {
	

	  /**
     * 收藏产品/关注品牌
     *
     * @MLTH_generated
     */
	Boolean addCollectProductOrBrand(CustomerCollectionEntity param);
	
	  /**
     * 取消收藏产品/取消关注品牌
     *
     * @MLTH_generated
     */
	Boolean updateCollectProductOrBrand(CustomerCollectionEntity param);
	
	  /**
     * 获取默认热推产品列表
     *
     * @MLTH_generated
     */
	ClassProductModel getDefaultPushByClassForPage(Page<Map<String, Object>> page);
	
/*	 *//**
     * 获取默认热推产品列表
     *
     * @MLTH_generated
     *//*
	ClassProductModel getSqlDefaultPushByClassForPage(Page<ProductListModel> page);*/
	
	 /**
     * 获取根据条件获取产品列表
     *
     * @MLTH_generated
     */
	ClassProductModel getNewProductByCondition(Page<Map<String, Object>> page);
	
	/* *//**
     * 获取根据条件获取产品列表
     *
     * @MLTH_generated
     *//*
	ClassProductModel getSqlProductByCondition(Page<ProductListModel> page);*/
	
	
	/**
     * 获取产品详情页数据
     *
     * @MLTH_generated
     */
	ProductDetails getSqlProductDetails(CustomerCollectionEntity param);
	
	/**
	 * 获取产品其他信息（图文+案例+参数）
	 *
	 * @MLTH_generated
	 */
	Map<String, Object> selectProductInfo(Map<String, Long> param);
	/**
	 * 获取产品其他信息（图文+案例+参数）
	 *
	 * @MLTH_generated
	 */
	Map<String, Object> getProductInfo(Map<String, Long> param);
	/**
     * 获取产品其他信息（图文+案例+参数）
     *
     * @MLTH_generated
     */
	Map<String, Object> getSqlProductInfo(CustomerCollectionEntity param);

    
    /**
     * 通过产品id获取店铺信息
     *
     * @MLTH_generated
     */
    List<Shop> selectShopInfoByProductId(Product param);
    
    /**
     * 通过产品id获取选择属性
     *
     * @MLTH_generated
     *//*
    SelectPropertiesModel getSelectProperties(AppProductModel param);*/
    
    /**
     * 通过产品id获取选择属性
     *
     * @MLTH_generated
     */
    List<String> getColorModelByProduct_id(AppProductModel param);
    
    /**
     * 通过产品id获取选择属性
     *
     * @MLTH_generated
     */
    List<BrandProdSalesAttrEntity> getStandardByProduct_id(BrandProdSalesAttrEntity param);
    
/*    *//**
     * 通过产品id和颜色获取选择属性
     *
     * @MLTH_generated
     *//*
    SelectPropertiesModel getSelectPropertiesByColor(Salesinfo param);*/
/*    *//**
     * 选择销售属性
     *
     * @MLTH_generated
     *//*
    Map<String, Object> selectProperties(SalesProperty param);*/
    /**
     * 选择销售属性
     *
     * @MLTH_generated
     */
    Map<String, Object> getProperties(SalesProperty param);

    
    
    /**
     * 获取品类产品筛选条件
     *
     * @MLTH_generated
     */
    List<SelectPropertiesModel> getNewProductConditions(BuildClass param);
    
/*    *//**
     * 获取品类产品风格和空间
     *
     * @MLTH_generated
     *//*
    SelectPropertiesModel getProductStyleAndSpace(BuildClass param);*/
    
    /**
     * 产品甄选
     *
     * @MLTH_generated
     */
    Page<Map<String, Object>> getProductSelectionForPage(Page<Map<String, Object>> page);
/*    *//**
     * 产品甄选
     *
     * @MLTH_generated
     *//*
    Page<ProductListModel> getSqlProductSelectionForPage(Page<ProductListModel> page);*/
}
