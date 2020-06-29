package com.mfangsoft.zhuangjialong.integration.series.controller;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.model.Tree;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.enums.UserState;
import com.mfangsoft.zhuangjialong.integration.enums.UserType;
import com.mfangsoft.zhuangjialong.integration.series.model.BrandSeriesEntity;
import com.mfangsoft.zhuangjialong.integration.series.model.EntSeriesEntity;
import com.mfangsoft.zhuangjialong.integration.series.service.BrandSeriesService;
import com.mfangsoft.zhuangjialong.integration.user.model.UserEntity;

@Controller
@RequestMapping("/server")
public class SeriesController {
	
	@Autowired
    private BrandSeriesService brandSeriesService;
	
	
	@RequestMapping(value="/brand/createseries", method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> addSeries(@RequestBody BrandSeriesEntity brandSeriesEntity){
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		if(brandSeriesService.addBrandSeries(brandSeriesEntity)){
			
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}
		
		
		
		return message;
	}
	
	@RequestMapping(value="/ent/createseries", method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> addEntSeries(@RequestBody EntSeriesEntity entSeriesEntity){
		
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		if(brandSeriesService.addEntSeries(entSeriesEntity)){
			
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}
		
		
		
		return message;
	}
	@RequestMapping(value="/brand/getseriesbyid/{id}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<BrandSeriesEntity> getSeries(@PathVariable Long id)
	{
		ResponseMessage<BrandSeriesEntity> message = new ResponseMessage<BrandSeriesEntity>();
		
		

		
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(brandSeriesService.getBrandSeriesById(id));
		return  message;
		
	}
	
	@RequestMapping(value="/brand/removeseries/{id}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> removeSeries(@PathVariable Long id)
	{
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		if(brandSeriesService.removeBrandSeries(id)){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}

		
		
		//message.setResult(brandSeriesService.getBrandSeriesById(id));
		return  message;
		
	}
	
	
	
	@RequestMapping(value="/ent/removeseries/{id}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> removeEntSeries(@PathVariable Long id)
	{
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		if(brandSeriesService.removeEntSeries(id)){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}

		
		
		//message.setResult(brandSeriesService.getBrandSeriesById(id));
		return  message;
		
	}
	
	@RequestMapping(value="/brand/modifyseries",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> modifyBrandSeries(@RequestBody BrandSeriesEntity brandSeriesEntity)
	{
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		if(brandSeriesService.modifyBrandSeries(brandSeriesEntity)){
		
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		}
		//message.setResult(enterprise);
		return  message;
		
	}
	
	@RequestMapping(value="/ent/modifyseries",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> modifyEntseries(@RequestBody EntSeriesEntity entSeriesEntity)
	{
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		if(brandSeriesService.modifyEntSeries(entSeriesEntity)){
		
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		}
		//message.setResult(enterprise);
		return  message;
		
	}
	
	@RequestMapping(value="/brand/queryseries",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Page<BrandSeriesEntity> querySeries(@RequestBody Page<BrandSeriesEntity> page)
	{
		
		
		return  brandSeriesService.queryBrandSeriesForPage(page);
		
	}
	
	
	@RequestMapping(value="/brand/queryseriesbybrandid",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<BrandSeriesEntity>> querySeriesBrandId()
	{
		ResponseMessage<List<BrandSeriesEntity>> message = new ResponseMessage<List<BrandSeriesEntity>>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(brandSeriesService.getBrandSeriesEntityList(UserContext.getCurrentUserId()));
		return  message;
		
	}
	
	
	@RequestMapping(value="/brand/classifyseries",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public List<Map<String,Object>> queryClassifySeries()
	{
		
		
		return brandSeriesService.getClassifySeries();
		
	}
	
	@RequestMapping(value="/ent/classifyseries",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public List<Map<String,Object>> queryEntClassifySeries()
	{
		
		
		return brandSeriesService.getEntClassifySeries();
		
	}
	
	@RequestMapping(value="/brand/getclassifyseriesfortree",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public List<Tree> getClassifySeriesForTree()
	{
		
	  UserEntity userEntity =UserContext.getCurrentUser();
	  if(userEntity.getUser_type().intValue()==UserType.PLATFORM.getIndex().intValue()){
		  
		  List<Tree> l= new ArrayList<>();
		  return l;
	  }
	  
	  if(userEntity.getUser_type().intValue()==UserType.ENTERPRISE.getIndex().intValue()){
		  
		  List<Tree> l= new ArrayList<>();
		  return l;
	  }
		
		return brandSeriesService.getClassifySeriesForTree();
		
	}
	
	@RequestMapping(value="/ent/getclassifyseriesfortree",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public List<Tree> getEntClassifySeriesForTree()
	{
		 UserEntity userEntity =UserContext.getCurrentUser();
		 if(userEntity.getUser_type().intValue()==UserType.PLATFORM.getIndex().intValue()){
			  
			  List<Tree> l= new ArrayList<>();
			  return l;
		  }
		  
		  if(userEntity.getUser_type().intValue()==UserType.BRAND.getIndex().intValue()){
			  
			  List<Tree> l= new ArrayList<>();
			  return l;
		  }
		
		return brandSeriesService.getEntClassifySeriesForTree();
		
	}
	@RequestMapping(value="/brand/brandserieslistbyclassify/{classify_id}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	
	public ResponseMessage<List<BrandSeriesEntity>> getBrandSeriesListByClassify(@PathVariable Long classify_id){
		ResponseMessage<List<BrandSeriesEntity>> message = new ResponseMessage<List<BrandSeriesEntity>>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		Map<String,Object> map = new HashMap<>();
		map.put("classify_id",classify_id);
		message.setResult(brandSeriesService.getBrandSeriesListByClassify(map));
		return  message;
	}
	
	@RequestMapping(value="/ent/serieslistbyclassify/{classify_id}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	
	public ResponseMessage<List<EntSeriesEntity>> getEntSeriesListByClassify(@PathVariable Long classify_id){
		ResponseMessage<List<EntSeriesEntity>> message = new ResponseMessage<List<EntSeriesEntity>>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		Map<String,Object> map = new HashMap<>();
		map.put("classify_id",classify_id);
		message.setResult(brandSeriesService.getEntSeriesListByClassify(map));
		return  message;
	}
	
	

}
