package com.mfangsoft.zhuangjialong.app.cart.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.mfangsoft.zhuangjialong.app.cart.model.CartEntity;
import com.mfangsoft.zhuangjialong.app.cart.model.CartShop;
import com.mfangsoft.zhuangjialong.app.cart.model.CartShopParam;
import com.mfangsoft.zhuangjialong.app.cart.service.CartService;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.coupons.model.BrandCouponsEntity;
import com.mfangsoft.zhuangjialong.integration.newcoupons.model.BaseBrandCouponsEntity;
import com.mfangsoft.zhuangjialong.integration.newcoupons.model.CustomerCouponsModel;
import com.mfangsoft.zhuangjialong.integration.promotion.model.UnionCustomer;
import com.mfangsoft.zhuangjialong.integration.promotion.model.UnionPromotion;

/**
 * @description：购物车控制层
 * @author：Jianghua.Tang @date：2016年5月27日
 * 
 */
@Controller(value = "appcart")
@RequestMapping("/app")

public class CartController {

	@Autowired
	private CartService cartService;

	@RequestMapping(value = "/addcart", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Long> addCart(@RequestBody CartEntity cartEntity) {
		ResponseMessage<Long> responseMessage = new ResponseMessage<>();
		Long id = cartService.insertSelective(cartEntity);
		if (id != null && id > 0) {
			responseMessage.setCode("0");
			responseMessage.setMessage("添加成功");
			responseMessage.setResult(id);
			return responseMessage;
		} else {
			responseMessage.setCode("1");
			responseMessage.setMessage("添加失败");
			return responseMessage;
		}
	}

	@RequestMapping(value = "/removecartproduct", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> removeCartProduct(@RequestBody List<CartEntity> cartEntities) {
		ResponseMessage<String> responseMessage = new ResponseMessage<>();
		cartService.deleteByPrimaryKey(cartEntities);
		responseMessage.setCode("0");
		responseMessage.setMessage("删除成功");
		return responseMessage;
	}

	@RequestMapping(value = "/editcartproduct", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> editCartProduct(@RequestBody List<CartEntity> cartEntities) {
		ResponseMessage<String> responseMessage = new ResponseMessage<>();
		if (cartService.updateByPrimaryKeySelective(cartEntities)) {
			responseMessage.setCode("0");
			responseMessage.setMessage("修改成功");
			return responseMessage;
		}
		responseMessage.setCode("1");
		responseMessage.setMessage("修改失败");
		return responseMessage;
	}

	@RequestMapping(value = "/cartdata", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<CartShop>> getCartData(@RequestBody CartEntity cartEntity) {
		ResponseMessage<List<CartShop>> responseMessage = new ResponseMessage<>();
		try {
			List<CartShop> cartShopList = cartService.getCartData(cartEntity);
				responseMessage.setCode("0");
				responseMessage.setMessage(SysConstant.SUCCESS_MSG);
				responseMessage.setResult(cartShopList);
				return responseMessage;
		} catch (Exception e) {
			responseMessage.setCode("1");
			responseMessage.setMessage("获取购物车失败");
			return responseMessage;
		}
	}
	
	@RequestMapping(value = "/getredbagcouponsforcart", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<CustomerCouponsModel>> getredbagcouponsforcart(@RequestBody CartShop cartShop) {
		ResponseMessage<List<CustomerCouponsModel>> responseMessage = new ResponseMessage<>();
		List<CustomerCouponsModel> cartShopList = new ArrayList<CustomerCouponsModel>();
		cartShopList = cartService.getredbagcouponsforcart(cartShop);

			responseMessage.setCode("0");
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			responseMessage.setResult(cartShopList);
			return responseMessage;
	}
	
	@RequestMapping(value = "/getallflagsofbenefitforcart", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<CartShop> getallflagsofbenefitforcart(@RequestBody CartShop cartShop) {
		ResponseMessage<CartShop> responseMessage = new ResponseMessage<>();

			responseMessage.setCode("0");
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			responseMessage.setResult(cartService.getAllFlagsOfBenefitForCart(cartShop));
			return responseMessage;
	}
	
	@RequestMapping(value = "/getallflagsofbenefitforallshop", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<CartShopParam> getallflagsofbenefitforallshop(@RequestBody CartShopParam param) {
		ResponseMessage<CartShopParam> responseMessage = new ResponseMessage<>();

			responseMessage.setCode("0");
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			responseMessage.setResult(cartService.getallflagsofbenefitforallshop(param));
			return responseMessage;
	}
	
	@RequestMapping(value = "/getpromotionforcart", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<UnionPromotion>> getCustomerPromotionforcart(@RequestBody CartShop cartShop) {
		ResponseMessage<List<UnionPromotion>> responseMessage = new ResponseMessage<>();
		
		List<UnionPromotion> unionCustomerList = new ArrayList<UnionPromotion>();
		unionCustomerList = cartService.getCustomerPromotionforcart(cartShop);
		
			responseMessage.setCode("0");
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			responseMessage.setResult(unionCustomerList);
			return responseMessage;
	}

	@RequestMapping(value = "/calculateforcart", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Map<String,Double>> calculateforcart(@RequestBody CartShop cartShop) {
		ResponseMessage<Map<String,Double>> responseMessage = new ResponseMessage<>();
		Map<String,Double> price = cartService.calculateforcart(cartShop);

		if(price != null){
			responseMessage.setCode("0");
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			responseMessage.setResult(price);
			return responseMessage;
		}else{
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
			return responseMessage;
		}
	}
	
	@RequestMapping(value = "/newcalculateforcart", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Map<String,Object>> calculateforcartNew(@RequestBody CartShop cartShop) {
		ResponseMessage<Map<String,Object>> responseMessage = new ResponseMessage<>();
		Map<String,Object> price = cartService.calculateforcart_new(cartShop);

		if(price != null){
			responseMessage.setCode("0");
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			responseMessage.setResult(price);
			return responseMessage;
		}else{
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
			return responseMessage;
		}
	}
	
	@RequestMapping(value = "/relaseUsedIds", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage relaseShopCashId(@RequestBody CartShop cartShop) {
		ResponseMessage responseMessage = new ResponseMessage<>();

		if(cartService.relaseUsedIds(cartShop) != null){
			responseMessage.setCode("0");
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			return responseMessage;
		}else{
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
			return responseMessage;
		}
	}
	
	@RequestMapping(value = "/getpercentpayment", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Integer> getpercentpayment(@RequestBody CartShop cartShop) {
		ResponseMessage<Integer> responseMessage = new ResponseMessage<>();
		Integer price = cartService.getpercentpayment(cartShop);

		if(price != null){
			responseMessage.setCode("0");
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			responseMessage.setResult(price);
			return responseMessage;
		}else{
			responseMessage.setCode("0");
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			responseMessage.setResult(100);
			return responseMessage;
		}
	}
	
	@RequestMapping(value = "/whetherHaveQuanforcart", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<BaseBrandCouponsEntity>> whetherHaveQuanforcart(@RequestBody CartShop cartShop) {
		ResponseMessage<List<BaseBrandCouponsEntity>> responseMessage = new ResponseMessage<>();
		try{
		List<BaseBrandCouponsEntity> list = cartService.whetherHaveQuanforcart(cartShop.getCustomer_id(), cartShop.getShop_id());

			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			responseMessage.setResult(list);
		}catch(Exception e){
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
		}
		return responseMessage;
	}
	
}
