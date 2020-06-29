package com.mfangsoft.zhuangjialong.integration.brandclassify.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.brandclassify.model.BrandClassifyEntity;
import com.mfangsoft.zhuangjialong.integration.brandclassify.service.BrandClassifyService;

@Controller
@RequestMapping("/server")
public class BrandClassifyController {
	@Autowired
	private BrandClassifyService brandClassifyService;

	
	@RequestMapping(value="/brand/queryclassify",method=RequestMethod.POST)
	@ResponseBody
	public  Page<BrandClassifyEntity> getClassifyList(@RequestBody Page<BrandClassifyEntity> page)
	{
		brandClassifyService.queryBrandClassifiesForPage(page);
		
		
		return page;
	}
	@RequestMapping(value="/brand/createclassify",method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<String>  addClassify(@RequestBody BrandClassifyEntity brandClassifyEntity ){
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		try {
			brandClassifyService.addBrandClassify(brandClassifyEntity);
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
		
		
	}
	
	@RequestMapping(value="/brand/removeclassify/{id}",method=RequestMethod.GET)
	@ResponseBody
	public ResponseMessage<String>  removeClassify(@PathVariable Long id){
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		try {
			if(brandClassifyService.removeBrandClassify(id)){
				message.setCode(SysConstant.SUCCESS_CODE);
				message.setMessage(SysConstant.SUCCESS_MSG);
			}else{
				message.setCode(SysConstant.FAILURE_CODE);
				message.setMessage(SysConstant.FAILURE_MSG);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
		
		
	}
	@RequestMapping(value="/brand/modifyclassify",method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<String>  modifyClassify(@RequestBody BrandClassifyEntity brandClassifyEntity ){
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		
		try {
			brandClassifyService.modifyBrandClassify(brandClassifyEntity);
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
	}

	
	@RequestMapping(value="/brand/getclassifybyid/{id}",method=RequestMethod.GET)
	@ResponseBody
	public ResponseMessage<BrandClassifyEntity>  getClassify(@PathVariable Long id ){
		ResponseMessage<BrandClassifyEntity> message = new ResponseMessage<BrandClassifyEntity>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(brandClassifyService.getBrandClassifyById(id));
		return message;
	}
	
	
	/**
	 * 用于企业或者品牌查询分类接口
	 * @return
	 */
	@RequestMapping(value="/brand/queryclassifies",method=RequestMethod.GET)
	@ResponseBody
	public ResponseMessage<List<BrandClassifyEntity>>  getBrandClassifyEntityList(){
		ResponseMessage<List<BrandClassifyEntity>> message = new ResponseMessage<List<BrandClassifyEntity>>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(brandClassifyService.queryBrandClassifies());
		return message;
	}
	/**
	 * 用于开发者分类查询接口
	 * @return
	 */
	@RequestMapping(value="/brand/devqueryclassifies",method=RequestMethod.GET)
	@ResponseBody
	public ResponseMessage<List<BrandClassifyEntity>>  getDevBrandClassifyEntityList(){
		ResponseMessage<List<BrandClassifyEntity>> message = new ResponseMessage<List<BrandClassifyEntity>>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);

		message.setResult(brandClassifyService.queryBrandClassifies());
		return message;
	}
}
