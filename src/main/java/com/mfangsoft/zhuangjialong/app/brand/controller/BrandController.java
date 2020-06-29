package com.mfangsoft.zhuangjialong.app.brand.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.app.brand.model.Brand;
import com.mfangsoft.zhuangjialong.app.brand.model.BrandEnModel;
import com.mfangsoft.zhuangjialong.app.brand.model.BrandModel;
import com.mfangsoft.zhuangjialong.app.brand.model.BrandNewProduct;
import com.mfangsoft.zhuangjialong.app.brand.model.RegionModel;
import com.mfangsoft.zhuangjialong.app.brand.model.RegionWithLetter;
import com.mfangsoft.zhuangjialong.app.brand.service.BrandService;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.CustomerCollectionEntity;
import com.mfangsoft.zhuangjialong.app.product.model.ProductListModel;
import com.mfangsoft.zhuangjialong.app.shop.model.Shop;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.brand.mapper.BrandEntityMapper;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;
import com.mfangsoft.zhuangjialong.integration.brandclassify.model.BrandClassifyEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.BuildClassEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseOneEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseTwoEntity;
import com.mfangsoft.zhuangjialong.integration.prepayment.model.PrepayValue;
import com.mfangsoft.zhuangjialong.integration.shop.model.ShopEntity;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年5月26日
* 
*/
@Controller(value="appbrand")
@RequestMapping("/app")

public class BrandController {
	@Autowired
	private BrandService brandServiceImpl;
	@Autowired
	private BrandEntityMapper brandEntityMapper;
	
	@RequestMapping(value = "/queryclass", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<BuildClassEntity>> selMatQueryClass(@RequestBody Map<String, String> map){
		ResponseMessage<List<BuildClassEntity>> responseMessage=new ResponseMessage<>();
		responseMessage.setCode("0");
		responseMessage.setMessage("获取成功");
		List<BuildClassEntity> classesByRegion = brandServiceImpl.selectClassesByRegion(map);
		responseMessage.setResult(classesByRegion);
		return responseMessage;
	}
	
	
	@RequestMapping(value = "/queryhotbrand", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<BrandEntity>> selMatQueryHotBrand(@RequestBody BrandEntity brandEntity){
		ResponseMessage<List<BrandEntity>> responseMessage=new ResponseMessage<>();
		responseMessage.setCode("0");
		responseMessage.setMessage("获取成功");
		responseMessage.setResult(brandServiceImpl.selectRegionBrand(brandEntity));
		return responseMessage;
	}
	
	  @RequestMapping(value = "/querybrand", method = RequestMethod.POST)
	  @ResponseBody
	  @CrossOrigin
     public ResponseMessage<List<BrandModel>> queryBrand(@RequestBody Map<String, Object> param){
		 ResponseMessage<List<BrandModel>> responseMessage=new ResponseMessage<>();
		 responseMessage.setCode("0");
		 responseMessage.setMessage("获取成功");
		 responseMessage.setResult(brandServiceImpl.selectRegionBrandByClassId(param));
		 return responseMessage;
	}
	  
	  @RequestMapping(value = "/queryBrandsByClassIdsAndRegionCode", method = RequestMethod.POST)
	  @ResponseBody
	  @CrossOrigin
     public ResponseMessage<List<Brand>> queryBrandsByClassIdsAndRegionCode(@RequestBody Map<String, Object> param){
		 ResponseMessage<List<Brand>> responseMessage=new ResponseMessage<>();
		 responseMessage.setCode("0");
		 responseMessage.setMessage("获取成功");
		 responseMessage.setResult(brandServiceImpl.selectBrandsByRegionCodeAndClassIds(param));
		 return responseMessage;
	}
	  
	  @RequestMapping(value = "/querybrandforen", method = RequestMethod.POST)
	  @ResponseBody
	  @CrossOrigin
     public ResponseMessage<List<BrandEnModel>> queryBrandForEn(@RequestBody Map<String, Object> param){
		 ResponseMessage<List<BrandEnModel>> responseMessage=new ResponseMessage<>();
		 responseMessage.setCode("0");
		 responseMessage.setMessage("获取成功");
		 responseMessage.setResult(brandServiceImpl.selectRegionBrandForEn(param));
		 return responseMessage;
	}
	
	 @RequestMapping(value = "/querybrandhead", method = RequestMethod.POST)
	 @ResponseBody
	 @CrossOrigin
	 public ResponseMessage<BrandModel> queryBrandHead(@RequestBody Map<String, Long> param){
		 ResponseMessage<BrandModel> responseMessage=new ResponseMessage<>();
		 if(param.get("brand_id")==null){
			 responseMessage.setCode(SysConstant.FAILURE_CODE);
			 responseMessage.setMessage(SysConstant.FAILURE_MSG);
			 return responseMessage;
		 }
		 responseMessage.setCode("0");
		 responseMessage.setMessage("获取成功");
		 responseMessage.setResult(brandServiceImpl.selectBrandHead(param));
		 return responseMessage;
	}
	 

	
	@RequestMapping(value = "/querybrandmain", method = RequestMethod.POST)
	 @ResponseBody
	 @CrossOrigin
	 public ResponseMessage<BrandModel> queryBrandMain(@RequestBody CustomerCollectionEntity param){
		 ResponseMessage<BrandModel> responseMessage=new ResponseMessage<>();
		 responseMessage.setCode("0");
		 responseMessage.setMessage("获取成功");
//		 responseMessage.setResult(brandServiceImpl.selectBrandMain(param));
//		 responseMessage.setResult(brandServiceImpl.getBrandMain(param));
		 responseMessage.setResult(brandServiceImpl.getSqlBrandMain(param));
		 return responseMessage;
	}
	
	
	
	 @RequestMapping(value = "/getbrandcustomcategory", method = RequestMethod.POST)
	 @ResponseBody
	 @CrossOrigin
	 public ResponseMessage<List<BrandClassifyEntity>> getBrandCustomCategory(@RequestBody BrandEntity param){
		 ResponseMessage<List<BrandClassifyEntity>> responseMessage=new ResponseMessage<>();
		 responseMessage.setCode("0");
		 responseMessage.setMessage("success");
		 responseMessage.setResult(brandServiceImpl.selectBrandClassifyAndSeries(param));
		 return responseMessage;
	 }
	 
	 
	 @RequestMapping(value = "/querybrandnewproducts", method = RequestMethod.POST)
	 @ResponseBody
	 @CrossOrigin
	 public ResponseMessage<Page<BrandNewProduct>> queryBrandNewProducts(@RequestBody Page<BrandNewProduct> page){
		 ResponseMessage<Page<BrandNewProduct>> responseMessage=new ResponseMessage<>();
		 responseMessage.setCode("0");
		 responseMessage.setMessage("success");
		 responseMessage.setResult(brandServiceImpl.getNewProductByBrandIdForPage(page));
		 return responseMessage;
	 }
	 
	 @RequestMapping(value = "/querybranddetails", method = RequestMethod.POST)
	 @ResponseBody
	 @CrossOrigin
	 public ResponseMessage<String> queryBrandDetails(@RequestBody BrandModel param){
		 ResponseMessage<String> responseMessage=new ResponseMessage<>();
		 responseMessage.setCode("0");
		 responseMessage.setMessage("success");
		 responseMessage.setResult(brandServiceImpl.selectEnterpriseAboutByBrandId(param));
		 return responseMessage;
	 }
	 @RequestMapping(value = "/queryenterprisedevelopment", method = RequestMethod.POST)
	 @ResponseBody
	 @CrossOrigin
	 public ResponseMessage<String> queryEnterpriseDevelopment(@RequestBody BrandModel param){
		 String string = brandServiceImpl.selectEnterpriseDevelopmentByBrandId(param);
		 ResponseMessage<String> responseMessage=new ResponseMessage<>();
		 responseMessage.setCode("0");
		 responseMessage.setMessage("success");
		 responseMessage.setResult(string);
		 return responseMessage;
	 }
	 
	 @RequestMapping(value = "/queryenterprisecase", method = RequestMethod.POST)
	 @ResponseBody
	 @CrossOrigin
	 public ResponseMessage<List<EnterpriseOneEntity>> queryEnterpriseCase(@RequestBody BrandModel param){
		 ResponseMessage<List<EnterpriseOneEntity>> responseMessage=new ResponseMessage<>();
		 responseMessage.setCode("0");
		 responseMessage.setMessage("success");
		 responseMessage.setResult(brandServiceImpl.selectEnterpriseCaseByBrandId(param));
		 return responseMessage;
	 }
	 
	 @RequestMapping(value = "/queryenterprisehonor", method = RequestMethod.POST)
	 @ResponseBody
	 @CrossOrigin
	 public ResponseMessage<List<EnterpriseOneEntity>> queryEnterpriseHonor(@RequestBody BrandModel param){
		 ResponseMessage<List<EnterpriseOneEntity>> responseMessage=new ResponseMessage<>();
		 responseMessage.setCode("0");
		 responseMessage.setMessage("success");
		 responseMessage.setResult(brandServiceImpl.selectEnterpriseHonorByBrandId(param));
		 return responseMessage;
	 }
	 
	 @RequestMapping(value = "/queryenterprisemien", method = RequestMethod.POST)
	 @ResponseBody
	 @CrossOrigin
	 public ResponseMessage<List<EnterpriseOneEntity>> queryEnterpriseMien(@RequestBody BrandModel param){
		 ResponseMessage<List<EnterpriseOneEntity>> responseMessage=new ResponseMessage<>();
		 responseMessage.setCode("0");
		 responseMessage.setMessage("success");
		 responseMessage.setResult(brandServiceImpl.selectEnterpriseMienByBrandId(param));
		 return responseMessage;
	 }
	 @RequestMapping(value = "/queryenterprisetwo", method = RequestMethod.POST)
	 @ResponseBody
	 @CrossOrigin
	 public ResponseMessage<List<EnterpriseTwoEntity>> queryEnterpriseTwo(@RequestBody EnterpriseTwoEntity param){
		 ResponseMessage<List<EnterpriseTwoEntity>> responseMessage=new ResponseMessage<>();
		 responseMessage.setCode("0");
		 responseMessage.setMessage("success");
		 responseMessage.setResult(brandServiceImpl.selectEnterpriseTwoByOneId(param.getOne_id()));
		 return responseMessage;
	 }
	 
	 @RequestMapping(value = "/querybrandhotline", method = RequestMethod.POST)
	 @ResponseBody
	 @CrossOrigin
	 public ResponseMessage<String> queryBrandHotLine(@RequestBody Map<String, Object> param){
		 ResponseMessage<String> responseMessage=new ResponseMessage<>();
		 responseMessage.setCode("0");
		 responseMessage.setMessage("success");
		 responseMessage.setResult(brandServiceImpl.selectHotLineByBrandId(param));
		 return responseMessage;
	 }
	 
	 @RequestMapping(value = "/querybrandshops", method = RequestMethod.POST)
	 @ResponseBody
	 @CrossOrigin
	 public ResponseMessage<List<Shop>> queryBrandShops(@RequestBody ShopEntity param){
		 ResponseMessage<List<Shop>> responseMessage=new ResponseMessage<>();
		 responseMessage.setCode("0");
		 responseMessage.setMessage("success");
		 responseMessage.setResult(brandServiceImpl.selectShopInfoByBrandId(param));
		 return responseMessage;
	 }
	
	
	 @RequestMapping(value = "/querybrandallproducts", method = RequestMethod.POST)
	 @ResponseBody
	 @CrossOrigin
	 public ResponseMessage<Page<Map<String, Object>>> queryBrandAllProducts(@RequestBody Page<Map<String, Object>> param){
		 
		 ResponseMessage<Page<Map<String, Object>>> responseMessage=new ResponseMessage<>();
		 responseMessage.setCode(SysConstant.SUCCESS_CODE);
		 responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		 responseMessage.setResult(brandServiceImpl.getAllProductForPage(param));
		 return responseMessage;
	 }
	/*@RequestMapping(value = "/querybrandallproducts", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Page<ProductListModel>> queryBrandAllProducts(@RequestBody Page<ProductListModel> param){
		
		ResponseMessage<Page<ProductListModel>> responseMessage=new ResponseMessage<>();
		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		responseMessage.setResult(brandServiceImpl.getSqlAllProductForPage(param));
		return responseMessage;
	}*/
	
	@RequestMapping(value = "/collectbrand", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Integer> collectBrand(@RequestBody CustomerCollectionEntity param){
		
		ResponseMessage<Integer> responseMessage=new ResponseMessage<>();
		 if(param.getCustomer_id()==null){
			 responseMessage.setCode(SysConstant.FAILURE_CODE);
			 responseMessage.setMessage(SysConstant.not_login);
			 return responseMessage;
		 }
		Boolean b = brandServiceImpl.addCollectProductOrBrand(param);
		Integer collectNum = brandEntityMapper.selectBrandCollectNum(param.getBrand_id());
		if(b){
		responseMessage.setCode("0");
		responseMessage.setMessage("关注成功");
		}else{
			responseMessage.setCode("1");
			responseMessage.setMessage("关注失败");
		}
		responseMessage.setResult(collectNum);
		return responseMessage;
	}
	@RequestMapping(value = "/cancelcollectbrand", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Integer> cancelCollectBrand(@RequestBody CustomerCollectionEntity param){
		Boolean b = brandServiceImpl.updateCollectProductOrBrand(param);
		
		 ResponseMessage<Integer> responseMessage=new ResponseMessage<>();
			if(b){
				responseMessage.setCode("0");
				responseMessage.setMessage("取消关注成功");
				}else{
					responseMessage.setCode("1");
					responseMessage.setMessage("取消关注失败");
				}
			Integer collectNum = brandEntityMapper.selectBrandCollectNum(param.getBrand_id());
			responseMessage.setResult(collectNum);
		 return responseMessage;
	}
	
	@RequestMapping(value = "/selectallprepayvalues", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<PrepayValue>> selectAllPrepayValues(){
		
		 ResponseMessage<List<PrepayValue>> responseMessage=new ResponseMessage<>();
				responseMessage.setCode("0");
				responseMessage.setMessage("成功");
				responseMessage.setResult(brandServiceImpl.selectAllPrepayValues());
		 return responseMessage;
	}
	
	@RequestMapping(value = "/selectserviceregion", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<RegionModel>> selectServiceRegion(){
		
		 ResponseMessage<List<RegionModel>> responseMessage=new ResponseMessage<>();
				responseMessage.setCode("0");
				responseMessage.setMessage("成功");
				responseMessage.setResult(brandServiceImpl.selectServiceRegion());
		 return responseMessage;
	}
	
	@RequestMapping(value = "/selectservicecityregion", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<RegionWithLetter>> selectServiceCityRegion(){
		
		 ResponseMessage<List<RegionWithLetter>> responseMessage=new ResponseMessage<>();
				responseMessage.setCode("0");
				responseMessage.setMessage("成功");
				responseMessage.setResult(brandServiceImpl.selectServiceCityRegion());
		 return responseMessage;
	}
	
	@RequestMapping(value = "/inregion", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> inRegion(@RequestBody Map<String, String> param){
		ResponseMessage<String> responseMessage=new ResponseMessage<>();
		if(brandServiceImpl.selectWhetherInRegion(param)){
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
			
		}
		 return responseMessage;
	}
}
