package com.mfangsoft.zhuangjialong.integration.promotion.controller;

import java.text.ParseException;
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

import com.mfangsoft.zhuangjialong.app.product.model.Product;
import com.mfangsoft.zhuangjialong.app.promotion.model.PromotionTimer;
import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.brand.mapper.BrandEntityMapper;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;
import com.mfangsoft.zhuangjialong.integration.enums.UserType;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.UnionProductMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.UnionPromotionMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.model.BrandProduct;
import com.mfangsoft.zhuangjialong.integration.promotion.model.ProductState;
import com.mfangsoft.zhuangjialong.integration.promotion.model.Promotion;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionSeckillProductEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionSeckillTimeEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.model.UnionPromotion;
import com.mfangsoft.zhuangjialong.integration.promotion.service.PromotionService;
import com.mfangsoft.zhuangjialong.integration.promotion.service.UnionPromotionService;
import com.mfangsoft.zhuangjialong.integration.user.model.UserEntity;
import com.sun.tools.javac.resources.javac;

@Controller(value = "promtionserver")
@RequestMapping("/server")
public class PromotionController {

	@Autowired
	private UnionPromotionService promotionService;
	@Autowired
	PromotionService promotionService2;
	@Autowired
	UnionPromotionMapper unionPromotionMapper;
	@Autowired
	BrandEntityMapper brandEntityMapper;
	@Autowired
	UnionPromotionService unionPromotionService;
	
	@RequestMapping(value = "/createplatformpromotion", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> createPlatformPromotion(@RequestBody PromotionEntity promotion) {

		// promotion.setType(0);//秒杀
		return promotionService2.addPromotion(promotion);

	}

	@RequestMapping(value = "/queryplatformpromotionListByPage", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Page<Map<String,String>> queryplatformpromotionListByPage(@RequestBody Page<Map<String,String>> page) {

		List<Map<String,String>> list = promotionService2.queryplatformpromotionListByPage(page);
		if (list != null) {
			page.setData(list);
			return page;
		}
		return page;

	}

	/**
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/getplatformpromotionbyid/{id}", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public PromotionEntity getPlatformPromotionById(@PathVariable Long id) {

		return promotionService2.getPlatformPromotionById(id);

	}

	@RequestMapping(value = "/modifyplatformpromotion", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> modifyPlatformPromotion(@RequestBody PromotionEntity promotion) {

		ResponseMessage<String> message = new ResponseMessage<String>();

		try {

			if (promotionService2.modifyPromotion(promotion)) {
				message.setCode(SysConstant.SUCCESS_CODE);
				message.setMessage(SysConstant.SUCCESS_MSG);
			} else {
				message.setCode(SysConstant.FAILURE_CODE);
				message.setMessage(SysConstant.FAILURE_MSG);
			}

		} catch (ParseException e) {
			e.printStackTrace();
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		return message;

	}

	@RequestMapping(value = "/selectBrandListOfOnepromotionForPlatform", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public List<PromotionTimer> selectBrandListOfOnepromotionForPlatform(@RequestBody PromotionTimer promotion) {

		return promotionService2.selectBrandListOfOnepromotionForPlatform(promotion);

	}
	
	@RequestMapping(value = "/getProductOfOnepromotionForPlatform", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public PromotionTimer getProductOfOnepromotionForPlatform(@RequestBody PromotionTimer promotion) {

		return promotionService2.getProductOfOnepromotionForPlatform(promotion);

	}
	
	@RequestMapping(value = "/getbrandpromotionListByPage1", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Page<Map<String,String>> getbrandpromotionListByPage1(@RequestBody Page<Map<String,String>> page) {

		List<Map<String,String>> list = promotionService2.getbrandpromotionListByPage1(page);
		if (list != null) {
			page.setData(list);
			return page;
		}
		return page;

	}

	@RequestMapping(value = "/getbrandpromotionListByPage2", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Page<PromotionEntity> getbrandpromotionListByPage2(@RequestBody Page<PromotionEntity> page) {

		List<PromotionEntity> list = promotionService2.getbrandpromotionListByPage2(page);
		if (list != null) {
			page.setData(list);
			return page;
		}
		return page;

	}

	@RequestMapping(value = "/getbrandpromotionproductListByPage", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Page<Map<String,Object>> getbrandpromotionListByPage(@RequestBody Page<Map<String,Object>> page) {

		List<Map<String,Object>> list = promotionService2.getbrandpromotionproductListByPage(page);
		if (list != null) {
			page.setData(list);
			return page;
		}
		return page;

	}

	@RequestMapping(value = "/selectProductOfOnepromotionForBrandByPage", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Page<Map<String,String>> selectProductOfOnepromotionForBrandByPage(@RequestBody Page<Map<String,String>> page) {

		List<Map<String,String>> list = promotionService2.selectProductOfOnepromotionForBrandByPage(page);
		if (list != null) {
			page.setData(list);
			return page;
		}
		return page;

	}
	
	@RequestMapping(value = "/getProductInfoOfOnepromotionForBrand/{pid}", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public Map<String,Integer> getProductInfoOfOnepromotionForBrand(@PathVariable Long pid) {
		
		return promotionService2.getProductInfoOfOnepromotionForBrand(pid);

	}
	
	@RequestMapping(value = "/addmodifybrandpromotionproductList", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> addmodifybrandpromotionproductList(@RequestBody PromotionSeckillProductEntity param) {

		ResponseMessage<String> message = new ResponseMessage<String>();
		
		return promotionService2.addmodifybrandpromotionproductList(param);
		
	}
	
	@RequestMapping(value = "/removebrandpromotionproduct", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> removebrandpromotionproduct(@RequestBody List<PromotionSeckillProductEntity> param) {

		ResponseMessage<String> message = new ResponseMessage<String>();
		
		return promotionService2.removebrandpromotionproduct(param);
		
	}

	@RequestMapping(value = "/modifyonepromotionproduct", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> modifyonepromotionproduct(@RequestBody List<PromotionSeckillProductEntity> param) {

		ResponseMessage<String> message = new ResponseMessage<String>();
		
		if (promotionService2.modifyonepromotionproduct(param)) {
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} else {
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;

	}
	/**
	 * 品牌报名一个秒杀活动
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/registerPromotionforbrand", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> registerPromotionforbrand(@RequestBody PromotionSeckillTimeEntity param) {

		ResponseMessage<String> message = new ResponseMessage<String>();
		
		if (promotionService2.registerPromotionforbrand(param)) {
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} else {
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;

	}
	
	/**
	 * 品牌取消报名一个秒杀活动
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/cancleregisterPromotionforbrand", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> cancleregisterPromotionforbrand(@RequestBody PromotionSeckillTimeEntity param) {

		return promotionService2.cancleRegisterPromotionforbrand(param);

	}
	
	
	
	
	
	
	
	
	
	////////////////////////一下代码没用////////////////////////////////////////////////////////////////////////////
	/**
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/removeplatformpromotion/{id}", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> removePlatformPromotion(@PathVariable Long id) {

		ResponseMessage<String> message = new ResponseMessage<String>();

		message.setCode(SysConstant.SUCCESS_CODE);

		message.setMessage(SysConstant.SUCCESS_MSG);

		return message;

	}

	/**
	 * 审核平台活动
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/checkplatformpromotion", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> checkPlatformPromotion(@RequestBody Map<String, Object> map) {

		ResponseMessage<String> message = new ResponseMessage<String>();

		message.setCode(SysConstant.SUCCESS_CODE);

		message.setMessage(SysConstant.SUCCESS_MSG);

		return message;

	}

	@RequestMapping(value = "/createbrandpromotion", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> createBrandPromotion(@RequestBody Promotion promotion) {

		promotion.setRange_type(1);
		ResponseMessage<String> message = new ResponseMessage<String>();

		message.setCode(SysConstant.SUCCESS_CODE);

		message.setMessage(SysConstant.SUCCESS_MSG);

		return message;

	}

	@RequestMapping(value = "/modifybrandpromotion", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> modifyBrandPromotion(@RequestBody Promotion promotion) {

		promotion.setRange_type(2);
		ResponseMessage<String> message = new ResponseMessage<String>();

		message.setCode(SysConstant.SUCCESS_CODE);

		message.setMessage(SysConstant.SUCCESS_MSG);

		return message;

	}

	@RequestMapping(value = "/getbrandpromotionbyid/{id}", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> getBrandPromotion(@PathVariable Long id) {

		ResponseMessage<String> message = new ResponseMessage<String>();

		message.setCode(SysConstant.SUCCESS_CODE);

		message.setMessage(SysConstant.SUCCESS_MSG);

		return message;

	}

	@RequestMapping(value = "/removebrandpromotion/{id}", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> removeBrandPromotion(@PathVariable Long id) {

		ResponseMessage<String> message = new ResponseMessage<String>();

		message.setCode(SysConstant.SUCCESS_CODE);

		message.setMessage(SysConstant.SUCCESS_MSG);

		return message;

	}

	@RequestMapping(value = "/checkbrandpromotion", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> checkBrandPromotion(@RequestBody Map<String, Object> map) {

		ResponseMessage<String> message = new ResponseMessage<String>();

		message.setCode(SysConstant.SUCCESS_CODE);

		message.setMessage(SysConstant.SUCCESS_MSG);

		return message;

	}
	/**
	 * 品牌管理员后台-活动管理-品牌活动-联盟活动-查看单条活动明细
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/queryunionpromotiondetail/{id}",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Map<String, Object>> queryUnionPromotionDetail(@PathVariable Integer id){
		ResponseMessage<Map<String, Object>> message = new ResponseMessage<Map<String, Object>>();
		try{
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			Map<String, Object> map = unionPromotionMapper.selectByPrimaryKey(id);
			String[] idStr = map.get("brand_ids").toString().split(",");
			List<String> idList = java.util.Arrays.asList(idStr);
			map.put("brandArray", brandEntityMapper.selectBrandByIDs(idList));
			message.setResult(map);
		}
		catch(Exception e){
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
	}
	/**
	 * 
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/querybrandpromotion", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Page<Promotion>> queryBrandPromotion(@RequestBody Page<Promotion> page) {

		ResponseMessage<Page<Promotion>> message = new ResponseMessage<Page<Promotion>>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		// message.setResult(enterprise);
		List<Promotion> promotions = new ArrayList<Promotion>();

		Promotion promotion = new Promotion();

		promotions.add(promotion);
		page.setData(promotions);
		message.setResult(page);
		return message;

	}
	//添加联盟活动
	@RequestMapping(value = "/addunionpromotion", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> addUnionPromotion(@RequestBody UnionPromotion unionPromotion) {
		ResponseMessage<String> message = new ResponseMessage<String>();
		try {
			promotionService.addUnionPromotion(unionPromotion);
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return message;
	}

	/**
	 * 修改活动
	 * 
	 * @param unionPromotion
	 * @return
	 */
	@RequestMapping(value = "/modifyunionpromotion", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> modifyUnionPromotion(@RequestBody UnionPromotion unionPromotion) {
		ResponseMessage<String> message = new ResponseMessage<String>();
		try {

			promotionService.modifyUnionPromotion(unionPromotion);

			message.setCode(SysConstant.SUCCESS_CODE);

			message.setMessage(SysConstant.SUCCESS_MSG);
		} catch (Exception e) {

			message.setCode(SysConstant.FAILURE_CODE);

			message.setMessage(SysConstant.FAILURE_MSG);
			// TODO: handle exception
		}

		return message;

	}
	/**
	 * 下架上架操作
	 * @param unionPromotion
	 * @return
	 */
	@RequestMapping(value = "/modifyonoffflag", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> modifyOnOffFlag(@RequestBody UnionPromotion unionPromotion) {
		ResponseMessage<String> message = new ResponseMessage<String>();
		try{
			if (promotionService.modifyOnOffFlag(unionPromotion.getId(), unionPromotion.getOnOffFlag())){
				message.setCode(SysConstant.SUCCESS_CODE);
				message.setMessage(SysConstant.SUCCESS_MSG);
			}
			else{
				message.setCode(SysConstant.FAILURE_CODE);
				message.setMessage(SysConstant.FAILURE_MSG);
			}
		}
		catch (Exception e){
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
	}
	/**
	 * 删除活动
	 * @param id 联盟活动id
	 * @return
	 */
	@RequestMapping(value = "/deleteunionpromotion", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> deleteUnionPromotion(@RequestBody Map<String,Integer> map) {
		ResponseMessage<String> message = new ResponseMessage<String>();
		if (unionPromotionService.deleteUnionPromotion(map.get("id"))){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} 
		else{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
	}
	/**
	 * 活动列表
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/queryunionpromotion", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Page<Map<String, Object>> queryUnionPromotionForPage(@RequestBody Page<Map<String, Object>> map) {

		return promotionService.queryUnionPromotionForPage(map);
	}
	/**
	 * 编辑联盟活动封面图、活动图
	 * @return
	 */
	@RequestMapping(value = "/modifyunionpromotionimage", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> modifyUnionPromotionCoverImage(@RequestBody UnionPromotion unionPromotion) {
		ResponseMessage<String> message = new ResponseMessage<String>();
		try{
			unionPromotionMapper.updateByPrimaryKeySelective(unionPromotion);
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}
		catch (Exception e){
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
	}	
	/**
	 * 活动产品列表
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/queryproductforpromotion", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Page<Map<String, Object>> queryProductForPromotionForPage(@RequestBody Page<Map<String, Object>> map) {

		return promotionService.queryProductForPromotionForPage(map);

	}

	/**
	 * 查询品牌名称
	 * 
	 * @return
	 */
	@RequestMapping(value = "/querybrandname", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public List<Map<String, Object>> queryBrandName() {

		return promotionService.queryBrandName();
	}

	/**
	 * 添加活动产品
	 * 
	 * @return
	 */
	@RequestMapping(value = "/addUnionPromotionProduct", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> addUnionPromotionProduct(@RequestBody UnionPromotion promotion) {
		ResponseMessage<String> message = new ResponseMessage<String>();
		try {
			promotionService.addUnionPromotionProduct(promotion);
		} catch (Exception e) {
			// TODO: handle exception
			message.setCode(SysConstant.FAILURE_CODE);

			message.setMessage(SysConstant.FAILURE_MSG);
		}

		return message;
	}

	/**
	 * 修改活动产品
	 * 
	 * @return
	 */
	@RequestMapping(value = "/modifyUnionPromotionProduct", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> modifyUnionPromotionProduct(@RequestBody UnionPromotion promotion) {
		ResponseMessage<String> message = new ResponseMessage<String>();

		try {
			promotionService.addUnionPromotionProduct(promotion);
		} catch (Exception e) {
			// TODO: handle exception
			message.setCode(SysConstant.FAILURE_CODE);

			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
	}
	
	
	/**
	 * 跳转活动列表
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/querypromotionforlink", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Page<Map<String, Object>> queryPromotionForLink(@RequestBody Page<Map<String, Object>> page) {

		return promotionService2.queryPromotionLink(page);

	}
	
	
	
	
	
	
	

}
