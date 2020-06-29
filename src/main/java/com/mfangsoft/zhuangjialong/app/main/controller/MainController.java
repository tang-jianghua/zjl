package com.mfangsoft.zhuangjialong.app.main.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.app.article.model.ArticleEntity;
import com.mfangsoft.zhuangjialong.app.main.model.CaseEntity;
import com.mfangsoft.zhuangjialong.app.main.model.ColumnImg;
import com.mfangsoft.zhuangjialong.app.main.model.ColumnModel;
import com.mfangsoft.zhuangjialong.app.main.model.IntroduceProductModel;
import com.mfangsoft.zhuangjialong.app.main.model.MainBannerEntity;
import com.mfangsoft.zhuangjialong.app.main.service.MainService;
import com.mfangsoft.zhuangjialong.app.product.model.ProductListModel;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;

/**
* @description：首页控制层
* @author：Jianghua.Tang 
* @date：2016年5月28日
* 
*/
@Controller(value="appmain")
@RequestMapping("/app")

public class MainController {
	
	@Autowired
	private MainService mainServiceImpl;
	
	
	@RequestMapping(value = "/mainbanner", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<MainBannerEntity>> mainBanner(@RequestBody Map<String, String> param){
		ResponseMessage<List<MainBannerEntity>> responseMessage=new ResponseMessage<>();
		responseMessage.setCode("0");
		responseMessage.setMessage("获取成功");
		responseMessage.setResult(mainServiceImpl.selectMainBanners(param.get("region_code")));
		return responseMessage;
	}
	
	
	@RequestMapping(value = "/mainad", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<MainBannerEntity> mainAd(){
		ResponseMessage <MainBannerEntity> responseMessage=new ResponseMessage<>();
		responseMessage.setCode("0");
		responseMessage.setMessage("获取成功");
		responseMessage.setResult(mainServiceImpl.getAd());
		return responseMessage;
	}
	
	
	@RequestMapping(value = "/mainbutton", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<ColumnImg>> mainButton(){
	
		ResponseMessage<List<ColumnImg>> responseMessage=new ResponseMessage<>();
		responseMessage.setCode("0");
		responseMessage.setMessage("success");
		responseMessage.setResult(mainServiceImpl.selectByImgType(ColumnImg.COLUMN_IMG_TYPE));
		return responseMessage;
	}
	
	
	@RequestMapping(value = "/mainheadline", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Map<String, Object>> mainHeadLine(){
		Map<String, Object> result = mainServiceImpl.selectMainHeadLine();
		ResponseMessage<Map<String, Object>> responseMessage=new ResponseMessage<>();
		responseMessage.setCode("0");
		responseMessage.setMessage("获取成功");
		responseMessage.setResult(result);
		return responseMessage;
	}
	
	
	
	
	@RequestMapping(value = "/mainsinglecase", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<ColumnImg> mainSingleCase(){
		ColumnImg result = mainServiceImpl.selectByImgType(4).get(0);
		ResponseMessage<ColumnImg> responseMessage=new ResponseMessage<>();  
		responseMessage.setCode("0");
		responseMessage.setMessage("获取成功");
		responseMessage.setResult(result);
		return responseMessage;
	}
	
	
	@RequestMapping(value = "/maincolumn", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ColumnModel<List<ColumnImg>> mainColumn(){
		ColumnModel<List<ColumnImg>> responseMessage=new ColumnModel<>();  
		String column_value="#01bc81";
		responseMessage.setCode("0");
		responseMessage.setMessage("获取成功");
		responseMessage.setColumn_value(column_value);
		responseMessage.setResult(mainServiceImpl.selectMainColumn());
		return responseMessage;}
	
	
	@RequestMapping(value = "/singlecaselist", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<CaseEntity>> singleCaseList(@RequestBody Map<String, String> param){
		List<CaseEntity> caseList = mainServiceImpl.selectCaseList(param);
		ResponseMessage<List<CaseEntity>> responseMessage=new ResponseMessage<>();  
		responseMessage.setCode("0");
		responseMessage.setMessage("获取成功");
		responseMessage.setResult(caseList);
		return responseMessage;
	}
	
	
	
	@RequestMapping(value = "/singlecasedetails", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<ArticleEntity> singleCaseDetails(@RequestBody CaseEntity caseEntity){
		ResponseMessage<ArticleEntity> responseMessage=new ResponseMessage<>();
		responseMessage.setCode("0");
		responseMessage.setMessage("获取成功");
		responseMessage.setResult( mainServiceImpl.selectForCaseDetails(caseEntity.getId()));
		return responseMessage;
	}
	
	
	@RequestMapping(value = "/mainregionbrand", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<BrandEntity>> mainRegionBrand(@RequestBody BrandEntity brandEntity){
		ResponseMessage<List<BrandEntity>> responseMessage=new ResponseMessage<>();
		responseMessage.setCode("0");
		responseMessage.setMessage("获取成功");
		responseMessage.setResult(mainServiceImpl.selectMainRegionBrand(brandEntity));
		return responseMessage;
	}
	
	
    @RequestMapping(value = "/mainhotproducts", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Page<IntroduceProductModel>> mainHotProducts(@RequestBody Page<IntroduceProductModel> page){
		ResponseMessage<Page<IntroduceProductModel>> responseMessage=new ResponseMessage<>();
		responseMessage.setCode("0");
		responseMessage.setMessage("获取成功");
     	responseMessage.setResult( mainServiceImpl.getMainHotProductForPage(page));
		return responseMessage;
	}
    
    @RequestMapping(value = "/mainhotproduct", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Page<ProductListModel>> mainHotProduct(@RequestBody Page<ProductListModel> page){
		ResponseMessage<Page<ProductListModel>> responseMessage=new ResponseMessage<>();
		responseMessage.setCode("0");
		responseMessage.setMessage("获取成功");
	responseMessage.setResult( mainServiceImpl.getSqlMainHotProductForPage(page));
		return responseMessage;
	}
}
