package com.mfangsoft.zhuangjialong.integration.newproduct.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.app.newproductcore.service.ProductCoreService;
import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BrandProductEntity;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.ClassAttrEntity;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.ClassAttrValueEntity;
import com.mfangsoft.zhuangjialong.integration.newproduct.service.BrandProductCopy;
import com.mfangsoft.zhuangjialong.integration.newproduct.service.BrandProductService;
import com.mfangsoft.zhuangjialong.integration.newproduct.service.ClassAttrService;

@Controller("newproduct")
@RequestMapping("/server")
public class BrandProductController {
	
	@Autowired
	private BrandProductService brandProductService;
	@Autowired
	private ClassAttrService classAttrService;
	
	@Autowired
	private  ProductCoreService productCoreService;
	
	@Autowired
	private BrandProductCopy brandProductCopy;
	
	@RequestMapping(value="/addnewproduct",method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<String>  addBrandProduct(@RequestBody BrandProductEntity brandProductEntity){
		
		
		ResponseMessage<String> message = new ResponseMessage<>();
		try {
			brandProductService.addBrandProduct(brandProductEntity);
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} catch (Exception e) {
			e.printStackTrace();
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			// TODO: handle exception
		}
		
		return message;
		
	}
	
	@RequestMapping(value="/addsolr/{id}",method=RequestMethod.GET)
	@ResponseBody
	public ResponseMessage<String>  addSolrProduct(@PathVariable Long id){
		
		
		ResponseMessage<String> message = new ResponseMessage<>();
		try {
			
			productCoreService.addNewProductDoc(id);
			
			
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} catch (Exception e) {
			
			e.printStackTrace();
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			// TODO: handle exception
		}
		
		return message;
		
	}
	
	
	@RequestMapping(value="/modfiynewproduct",method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<String>  modfiyBrandProduct(@RequestBody BrandProductEntity brandProductEntity){
		
		
		ResponseMessage<String> message = new ResponseMessage<>();
		
		
		
		try {
			brandProductService.modfiyBrandProduct(brandProductEntity);
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} catch (Exception e) {
			
			e.printStackTrace();
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			// TODO: handle exception
		}
		
		return message;
		
	}
	/**
	 * 通过品类获取所有属性
	 * @return
	 */
	@RequestMapping(value="/attrbyclassid",method=RequestMethod.GET)
	@ResponseBody
	public ResponseMessage<List<ClassAttrEntity>>  getAttrByClassId(){
		ResponseMessage<List<ClassAttrEntity>> message = new ResponseMessage<>();
		
		message.setCode(SysConstant.SUCCESS_CODE);
		
		message.setMessage(SysConstant.SUCCESS_MSG);
		
		message.setResult(classAttrService.getClassAttrEntityByClassId());
		
		return message;
		
	}
	
	/**
	 * 通过属性ID获取属性下拉框的数据
	 * 输入参数：{"attr_id":"","iswrite":""}
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/attrvaluebyattrid",method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<List<ClassAttrValueEntity>>  getClassAttrValueEntityByAttrId(@RequestBody  Map<String,Object> map){
		
		
		ResponseMessage<List<ClassAttrValueEntity>> message = new ResponseMessage<>();
		
		message.setCode(SysConstant.SUCCESS_CODE);
		
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(classAttrService.getClassAttrValueEntityByAttrId(new Long(map.get("attr_id").toString()), new Integer(map.get("iswrite").toString()) ));
		
		return message;
		
	}
	
	/**
	 * 如果属性之间有级联关系 需要调用此接口
	 * 输入参数{"id":"","attr_id":""}
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/attrvaluebyparentId",method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<List<ClassAttrValueEntity>>  getClassAttrValueEntityByParentId(@RequestBody  Map<String,Long> map){
		
		
		ResponseMessage<List<ClassAttrValueEntity>> message = new ResponseMessage<>();
		
		message.setCode(SysConstant.SUCCESS_CODE);
		
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(classAttrService.getClassAttrValueEntityByParentId(map));
		
		return message;
		
	}
	
	@RequestMapping(value="/querybrandproductbyid/{id}",method=RequestMethod.GET)
	@ResponseBody
	public ResponseMessage<BrandProductEntity>  queryBrandProductById(@PathVariable Long id){
		
		
		ResponseMessage<BrandProductEntity> message = new ResponseMessage<>();
		
		message.setCode(SysConstant.SUCCESS_CODE);
		
		message.setMessage(SysConstant.SUCCESS_MSG);
		
		message.setResult(brandProductService.getBrandProductById(id));
		return message;
		
	}
	
	@RequestMapping(value="/querynewbrandproduct",method=RequestMethod.POST)
	@ResponseBody
	public Page<Map<String,Object>>  queryBrandProduct(@RequestBody  Page<Map<String,Object>> page){
		
		
		//page.setIspage(false);
		
		return brandProductService.getBrandProductForPage(page);
		
	}
	
	@RequestMapping(value="/querynewbranddevproduct",method=RequestMethod.POST)
	@ResponseBody
	public Page<Map<String,Object>>  queryBrandDevProduct(@RequestBody  Page<Map<String,Object>> page){
		
		
		return brandProductService.getBrandDevProductListForPage(page);
		
	}
	/**
	 * 删除销售属性
	 * 输入参数 销售属性id
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/removebrandprodsales/{id}",method=RequestMethod.GET)
	@ResponseBody
	public ResponseMessage<List<ClassAttrValueEntity>>  removeBrandProdSales(@PathVariable Long id){
		
		
		ResponseMessage<List<ClassAttrValueEntity>> message = new ResponseMessage<>();
		
		message.setCode(SysConstant.SUCCESS_CODE);
		
		message.setMessage(SysConstant.SUCCESS_MSG);
		brandProductService.removeBrandProdSales(id);
		return message;
		
	}
	
	@RequestMapping(value="/offnewproduct",method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<String>  offBrandProduct(@RequestBody List<Long> list){
		
		
		ResponseMessage<String> message = new ResponseMessage<>();
		
		if(list==null||list.size()==0){
			
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			return message;
			
		}
		
		try {
				brandProductService.updateBrandProduct(list, 2);
				productCoreService.updateStateDocs(list, 2);
				message.setCode(SysConstant.SUCCESS_CODE);
				message.setMessage(SysConstant.SUCCESS_MSG);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			// TODO: handle exception
		}
		
		return message;
		
	}
	
	@RequestMapping(value="/isoffnewproduct",method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<String>  isOffBrandProduct(@RequestBody List<Long> list){
		
		ResponseMessage<String> message = new ResponseMessage<>();
		
		if(list==null||list.size()==0){
			
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			return message;
			
		}
		
		try {
	
			
			if(brandProductService.selectPromotionProductByProductId(list)){
				message.setCode(SysConstant.SUCCESS_CODE);
				message.setMessage(SysConstant.SUCCESS_MSG);
				
			}else{
				
				message.setCode(SysConstant.FAILURE_CODE);
				message.setMessage(SysConstant.FAILURE_MSG);
			}
			//productCoreService.updateStateDocs(list, 2);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			// TODO: handle exception
		}
		
		return message;
		
	}
	
	
	
	@RequestMapping(value="/onnewproduct",method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<String>  onBrandProduct(@RequestBody List<Long> list){
		
		
		ResponseMessage<String> message = new ResponseMessage<>();
		
		if(list==null||list.size()==0){
			
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			return message;
			
		}
		
		try {
			brandProductService.updateBrandProduct(list, 1);
			productCoreService.updateStateDocs(list, 1);
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} catch (Exception e) {
			
			e.printStackTrace();
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			// TODO: handle exception
		}
		
		return message;
		
	}
	
	
	/**
	 * 用于产品跳转接口
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/querynewbrandproductforbranner",method=RequestMethod.POST)
	@ResponseBody
	public Page<Map<String,Object>>  queryBrandProductForBranner(@RequestBody  Page<Map<String,Object>> page){
		
		
		return brandProductService.selectProductForBanner(page);
		
	}
	
	/**
	 * 用于产品跳转明细接口
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/getnewbrandproductbrannerbyid/{id}",method=RequestMethod.GET)
	@ResponseBody
	public ResponseMessage<BrandProductEntity>   getnewbrandproductbrannerbyid(@PathVariable Long id){
		ResponseMessage<BrandProductEntity> message = new ResponseMessage<>();
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			message.setResult(brandProductService.getBrandProductBannerById(id));
		
		return message;
	}
	
	/**
	 * 企业copy到品牌
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/copyenttobrand",method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<String>   copyEntToBrand(@RequestBody  Page<Map<String,Object>> page){
		
		ResponseMessage<String> message = new ResponseMessage<>();
		try {
			
			BrandEntity   brandEntity=(BrandEntity) UserContext.getCurrentUserInfo();
			
			brandProductCopy.addFromEntToBrandProduct(page);
			
			message.setCode(SysConstant.SUCCESS_CODE);
			
			
			message.setMessage(SysConstant.SUCCESS_MSG);
			
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			// TODO: handle exception
		}
		
		return message;
	}
	
	/**
	 * 企业copy到品牌
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/selectcopyenttobrand",method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<String>   copyEntToBrand(@RequestBody  List<Long> list){
		
		ResponseMessage<String> message = new ResponseMessage<>();
		try {
			brandProductCopy.addFromEntToBrandProduct(list);
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			// TODO: handle exception
		}
		
		return message;
	}
	
	
	/**
	 * 企业copy到品牌
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/brandtobrand",method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<String>   copyBrandToBrand(@RequestBody Map<String,Object> map){
		
		ResponseMessage<String> message = new ResponseMessage<>();
		try {
			
			BrandEntity   brandEntity=(BrandEntity) UserContext.getCurrentUserInfo();
			
			brandProductCopy.addCopyBrandProduct(new Long(map.get("from_brandId").toString()), new Long(map.get("to_brandId").toString()),new Long(map.get("class_id").toString()));
			
			message.setCode(SysConstant.SUCCESS_CODE);
			
			message.setMessage(SysConstant.SUCCESS_MSG);
			
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			// TODO: handle exception
		}
		
		return message;
	}
	
	
	/**
	 * 企业copy到品牌
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/brandtoent",method=RequestMethod.GET)
	@ResponseBody
	public ResponseMessage<String>   copyBrandToBrand(){
		
		ResponseMessage<String> message = new ResponseMessage<>();
		try {
			
			//BrandEntity   brandEntity=(BrandEntity) UserContext.getCurrentUserInfo();
			
			//brandProductCopy.addCopyBrandProduct(new Long(map.get("from_brandId").toString()), new Long(map.get("to_brandId").toString()),new Long(map.get("class_id").toString()));
			
			
			brandProductCopy.addCopyEntToBrandProduct(107L, 79L, 3L);
			message.setCode(SysConstant.SUCCESS_CODE);
			
			message.setMessage(SysConstant.SUCCESS_MSG);
			
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			// TODO: handle exception
		}
		
		return message;
	}
	

}
