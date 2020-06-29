
package com.mfangsoft.zhuangjialong.app.product.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.app.buildClass.model.BuildClass;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.CustomerCollectionEntity;
import com.mfangsoft.zhuangjialong.app.product.model.AppProductModel;
import com.mfangsoft.zhuangjialong.app.product.model.ClassProductModel;
import com.mfangsoft.zhuangjialong.app.product.model.Product;
import com.mfangsoft.zhuangjialong.app.product.model.ProductDetails;
import com.mfangsoft.zhuangjialong.app.product.model.SalesProperty;
import com.mfangsoft.zhuangjialong.app.product.model.SelectPropertiesModel;
import com.mfangsoft.zhuangjialong.app.product.service.ProductService;
import com.mfangsoft.zhuangjialong.app.shop.model.Shop;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BrandProdSalesAttrEntity;

/**
* @description：产品模块控制层
* @author：Jianghua.Tang 
* @date：2016年5月26日
* 
*/
@Controller(value="appproduct")
@RequestMapping("/app")


public class ProductController {
	@Autowired
	private ProductService productServiceImpl;
	 
	//产品甄选
	@RequestMapping(value = "/productselection", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Page<Map<String, Object>>> productSelection(@RequestBody Page<Map<String, Object>> page){
		
		ResponseMessage<Page<Map<String, Object>>> responseMessage=new ResponseMessage<>();
		responseMessage.setCode("0");
		responseMessage.setMessage("获取成功");
		 responseMessage.setResult(productServiceImpl.getProductSelectionForPage(page));
		return responseMessage;
	}
/*	    @RequestMapping(value = "/productselection", method = RequestMethod.POST)
	    @ResponseBody
	    @CrossOrigin
	    public ResponseMessage<Page<ProductListModel>> productSelection(@RequestBody Page<ProductListModel> page){
			 
		 ResponseMessage<Page<ProductListModel>> responseMessage=new ResponseMessage<>();
		 responseMessage.setCode("0");
		 responseMessage.setMessage("获取成功");
		 responseMessage.setResult(productServiceImpl.getSqlProductSelectionForPage(page));
	    	return responseMessage;
	    }*/
	
	//品类筛选默认热推
	    @RequestMapping(value = "/defaultpushbyclass", method = RequestMethod.POST)
	    @ResponseBody
	    @CrossOrigin
	    public ResponseMessage<ClassProductModel> defaultPushByClassForPage(@RequestBody Page<Map<String, Object>> page){
	    	
	    	ResponseMessage<ClassProductModel> responseMessage=new ResponseMessage<>();
	    	responseMessage.setCode("0");
	    	responseMessage.setMessage("获取成功");
       responseMessage.setResult(productServiceImpl.getDefaultPushByClassForPage(page));
	    	
	    	return responseMessage;
	    }
  
	    
/*    @RequestMapping(value = "/defaultpushbyclass", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ResponseMessage<ClassProductModel> defaultPushByClassForPage(@RequestBody Page<ProductListModel> page){
		 
	 ResponseMessage<ClassProductModel> responseMessage=new ResponseMessage<>();
	 responseMessage.setCode("0");
	 responseMessage.setMessage("获取成功");
    responseMessage.setResult(productServiceImpl.getSqlDefaultPushByClassForPage(page));
    	return responseMessage;
    }*/
    
    
    
    
	//获取产品筛选字段
	 @RequestMapping(value = "/getproductconditions", method = RequestMethod.POST)
	    @ResponseBody
	    @CrossOrigin
	    public ResponseMessage<List<SelectPropertiesModel>> getProductConditions(@RequestBody BuildClass param){
			 
		 ResponseMessage<List<SelectPropertiesModel>> responseMessage=new ResponseMessage<>();
		 responseMessage.setCode("0");
		 responseMessage.setMessage("获取成功");
		 //responseMessage.setResult(productServiceImpl.getProductConditions(param));
		 responseMessage.setResult(productServiceImpl.getNewProductConditions(param));
	    	return responseMessage;
	    }
	 
	//品类筛选
	 @RequestMapping(value = "/searchproductbyclass", method = RequestMethod.POST)
	 @ResponseBody
	 @CrossOrigin
	 public ResponseMessage<ClassProductModel> getWallpaperProducts(@RequestBody Page<Map<String, Object>> page){
		 ResponseMessage<ClassProductModel> responseMessage=new ResponseMessage<>();
		 responseMessage.setCode("0");
		 responseMessage.setMessage("获取成功");
		 responseMessage.setResult(productServiceImpl.getNewProductByCondition(page));
		 return responseMessage;
	 }
/*	 @RequestMapping(value = "/searchproductbyclass", method = RequestMethod.POST)
	 @ResponseBody
	 @CrossOrigin
	  public ResponseMessage<ClassProductModel> getWallpaperProducts(@RequestBody Page<ProductListModel> page){
		 ResponseMessage<ClassProductModel> responseMessage=new ResponseMessage<>();
		 responseMessage.setCode("0");
		 responseMessage.setMessage("获取成功");
		 	 responseMessage.setResult(productServiceImpl.getSqlProductByCondition(page));
	    	return responseMessage;
	    }*/

	
	 
/*	 @RequestMapping(value = "/productdetails", method = RequestMethod.POST)
	 @ResponseBody
	 @CrossOrigin
	 public ResponseMessage<Map<String, Object>> getProductDetails(@RequestBody CustomerCollectionEntity param){
		 ResponseMessage<Map<String, Object>> responseMessage=new ResponseMessage<>();
		 responseMessage.setCode("0");
		 responseMessage.setMessage("获取成功");
//		 responseMessage.setResult(productServiceImpl.selectProductDetails(param));
		 responseMessage.setResult(productServiceImpl.getProductDetails(param));
		 
		 return responseMessage;
	 }*/
	 
	 //产品详情
	 @RequestMapping(value = "/productdetails", method = RequestMethod.POST)
	    @ResponseBody
	    @CrossOrigin
	    public ResponseMessage<ProductDetails> getProductDetails(@RequestBody CustomerCollectionEntity param){
		 ResponseMessage<ProductDetails> responseMessage=new ResponseMessage<>();
		 responseMessage.setCode("0");
		 responseMessage.setMessage("获取成功");
//		 responseMessage.setResult(productServiceImpl.selectProductDetails(param));
		 responseMessage.setResult(productServiceImpl.getSqlProductDetails(param));
		 
	    	return responseMessage;
	 }

	 //店铺信息
	 @RequestMapping(value = "/getshopsforproductdetails", method = RequestMethod.POST)
	    @ResponseBody
	    @CrossOrigin
	    public ResponseMessage<List<Shop>> getShopsForProductDetails(@RequestBody Product param){
		 ResponseMessage<List<Shop>> responseMessage=new ResponseMessage<>();
		 responseMessage.setCode("0");
		 responseMessage.setMessage("获取成功");
		 responseMessage.setResult(productServiceImpl.selectShopInfoByProductId(param));
	    	return responseMessage;
	 }
	 
	 
/*	 @RequestMapping(value = "/getproductinfo", method = RequestMethod.POST)
	 @ResponseBody
	 @CrossOrigin
	 public ResponseMessage<Map<String, Object>> getProductDescription(@RequestBody Map<String, Long> param){
		 ResponseMessage<Map<String, Object>> responseMessage=new ResponseMessage<>();
		 responseMessage.setCode("0");
		 responseMessage.setMessage("获取成功");
//		 responseMessage.setResult(productServiceImpl.selectProductInfo(param));
		 responseMessage.setResult(productServiceImpl.getProductInfo(param));
		 return responseMessage;
	 }
*/	 
	 //产品图文详情，案例，参数
	 @RequestMapping(value = "/getproductinfo", method = RequestMethod.POST)
	    @ResponseBody
	    @CrossOrigin
	    public ResponseMessage<Map<String, Object>> getProductDescription(@RequestBody CustomerCollectionEntity param){
		 ResponseMessage<Map<String, Object>> responseMessage=new ResponseMessage<>();
		 responseMessage.setCode("0");
		 responseMessage.setMessage("获取成功");
//		 responseMessage.setResult(productServiceImpl.selectProductInfo(param));
		 responseMessage.setResult(productServiceImpl.getSqlProductInfo(param));
	    	return responseMessage;
	 }
	 
	//收藏产品
	 @RequestMapping(value = "/collectproduct", method = RequestMethod.POST)
	 @ResponseBody
	 @CrossOrigin
	 public ResponseMessage<String> collectProduct(@RequestBody CustomerCollectionEntity param){
		 ResponseMessage<String> responseMessage=new ResponseMessage<>();
		 if(param.getCustomer_id()==null){
			 responseMessage.setCode(SysConstant.FAILURE_CODE);
			 responseMessage.setMessage(SysConstant.not_login);
			 return responseMessage;
		 }
		 Boolean b = productServiceImpl.addCollectProductOrBrand(param);
		 if(b){
		 responseMessage.setCode("0");
		 responseMessage.setMessage("收藏成功");
		 }else{
			 responseMessage.setCode("1");
			 responseMessage.setMessage("收藏失败");
		 }
		 return responseMessage;
	 }
	 
	 //取消收藏产品
	 @RequestMapping(value = "/cancelcollectproduct", method = RequestMethod.POST)
	 @ResponseBody
	 @CrossOrigin
	 public ResponseMessage<String> cancelCollectProduct(@RequestBody CustomerCollectionEntity param){
		 Boolean b = productServiceImpl.updateCollectProductOrBrand(param);
		 ResponseMessage<String> responseMessage=new ResponseMessage<>();
		 if(b){
		 responseMessage.setCode("0");
		 responseMessage.setMessage("取消成功");
		 }else{
			 responseMessage.setCode("1");
			 responseMessage.setMessage("取消失败");
		 }
		 return responseMessage;
	 }
	 
/*	 @RequestMapping(value = "/getselectproperties", method = RequestMethod.POST)
	 @ResponseBody
	 @CrossOrigin
	 public ResponseMessage<SelectPropertiesModel> getSelectProperties(@RequestBody BrandProductEntity param){
		 ResponseMessage<SelectPropertiesModel> responseMessage=new ResponseMessage<>();
		 responseMessage.setCode("0");
		 responseMessage.setMessage("获取成功");
		 responseMessage.setResult(productServiceImpl.getSelectProperties(param));
		 return responseMessage;
	 }
*/	 
	 //获取颜色筛选属性
	 @RequestMapping(value = "/getselectproperties", method = RequestMethod.POST)
	 @ResponseBody
	 @CrossOrigin
	 public ResponseMessage<List<String>> getSelectProperties(@RequestBody AppProductModel param){
		 ResponseMessage<List<String>> responseMessage=new ResponseMessage<>();
		 responseMessage.setCode("0");
		 responseMessage.setMessage("获取成功");
		 responseMessage.setResult(productServiceImpl.getColorModelByProduct_id(param));
		 return responseMessage;
	 }
	 
/*@RequestMapping(value = "/getselectpropertiesbycolor", method = RequestMethod.POST)
@ResponseBody
@CrossOrigin
public ResponseMessage<SelectPropertiesModel> getSelectPropertiesByColor(@RequestBody Salesinfo param){
	ResponseMessage<SelectPropertiesModel> responseMessage=new ResponseMessage<>();
	responseMessage.setCode("0");
	responseMessage.setMessage("获取成功");
	responseMessage.setResult(productServiceImpl.getSelectPropertiesByColor(param));
	return responseMessage;
}
*/	
	 //获取规格筛选属性
	 @RequestMapping(value = "/getselectpropertiesbycolor", method = RequestMethod.POST)
	 @ResponseBody
	 @CrossOrigin
	 public ResponseMessage<List<BrandProdSalesAttrEntity>> getSelectPropertiesByColor(@RequestBody BrandProdSalesAttrEntity param){
		 ResponseMessage<List<BrandProdSalesAttrEntity>> responseMessage=new ResponseMessage<>();
		 responseMessage.setCode("0");
		 responseMessage.setMessage("获取成功");
		 responseMessage.setResult(productServiceImpl.getStandardByProduct_id(param));
		 return responseMessage;
	 }
	 
	 //选择销售属性
	 @RequestMapping(value = "/selectproperties", method = RequestMethod.POST)
	 @ResponseBody
	 @CrossOrigin
	 public ResponseMessage<Map<String, Object>> selectProperties(@RequestBody SalesProperty param){
		 ResponseMessage<Map<String, Object>> responseMessage=new ResponseMessage<>();
		 responseMessage.setCode("0");
		 responseMessage.setMessage("选择成功");
//		 responseMessage.setResult(productServiceImpl.selectProperties(param));
		 responseMessage.setResult(productServiceImpl.getProperties(param));
		 return responseMessage;
	 }
	
}
