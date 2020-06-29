package com.mfangsoft.zhuangjialong.app.newproductcore.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.app.newproductcore.service.ProductCoreService;
import com.mfangsoft.zhuangjialong.integration.newproduct.mapper.BrandNewProductEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newproduct.mapper.BrandProdSeriesEntityMapper;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年12月6日
* 
*/
@Controller
@RequestMapping(value="/productcore")
public class NewProductCoreController {
	
	@Autowired
	ProductCoreService productCoreServiceImpl;
	@Autowired
	BrandProdSeriesEntityMapper productSeriesMapper;
	@Autowired
	BrandNewProductEntityMapper productMapper;
	
	
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@CrossOrigin
	@ResponseBody
	public String delete(@RequestBody Map<String, List<String>> map){
		 productCoreServiceImpl.deleteNewProductDoc(map.get("id"));
   return "SUCCESS";
	}
	
	@RequestMapping(value="/addseries")
	@CrossOrigin
	public String addSeries(){
		List<Map<String,String>> list = productSeriesMapper.selectAll();
		 productCoreServiceImpl.updateProductSeriesDocs(list);
   return "SUCCESS";
	}
	
	@RequestMapping(value="/addstate")
	@CrossOrigin
	public String addState(){
		
		List<Map<String,Object>> state = productMapper.selectAllStateForProductCore();
		productCoreServiceImpl.updateStateDocs(state);
		return "SUCCESS";
	}
	@RequestMapping(value="/setstate")
	@CrossOrigin
	@ResponseBody
	public String setState(@RequestBody Map<String, String> map){
		
		productCoreServiceImpl.updateStateDoc(map);
		return "SUCCESS";
	}
	
	@RequestMapping(value="/updateimages")
	@CrossOrigin
	@ResponseBody
	public String updateImages(@RequestBody Map<String, List<Long>> map){
		
		 productCoreServiceImpl.updateProductImagesDocs(map.get("product_ids"));
   return "SUCCESS";
}
	
	@RequestMapping(value="/updateproduct/{product_id}", method = RequestMethod.GET)
	@CrossOrigin
	@ResponseBody
	public String updateProduct(@PathVariable Long product_id){
		productCoreServiceImpl.updateNewProductDoc(product_id);
		return "SUCCESS";
	}
	@RequestMapping(value="/addproduct/{product_id}", method = RequestMethod.GET)
	@CrossOrigin
	@ResponseBody
	public String addProduct(@PathVariable Long product_id){
		productCoreServiceImpl.addNewProductDoc(product_id);
		return "SUCCESS";
	}
	@RequestMapping(value="/updatesalenum")
	@CrossOrigin
	@ResponseBody
	public String updateSaleNum(){
		 productCoreServiceImpl.updateProductSaleNumDocs();
   return "SUCCESS";
}
}
