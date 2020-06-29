package com.mfangsoft.zhuangjialong.integration.shop.controller;

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

import com.mfangsoft.zhuangjialong.app.seller.model.SellerEntity;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.seller.model.SellerModel;
import com.mfangsoft.zhuangjialong.integration.shop.model.ShopGuiderModel;
import com.mfangsoft.zhuangjialong.integration.shop.service.ShopGuiderService;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年12月23日
* 
*/
@Controller("server")
@RequestMapping(value="/server")
public class ShopGuiderController {
	
	@Autowired
	ShopGuiderService shopGuiderServiceImpl;
	

	/**
	 * 添加店面导购
	 * @param SellerModel
	 * @return
	 */
	@RequestMapping(value="/addShopGuider",method=RequestMethod.POST)
	@CrossOrigin
	@ResponseBody
	 public ResponseMessage<String>  addShopGuider(@RequestBody SellerModel sellerModel){
		 return shopGuiderServiceImpl.addShopGuider(sellerModel);
	 }
	
	/**
	 * 编辑店面导购
	 * @param SellerModel
	 * @return
	 */
	@RequestMapping(value="/modifyShopGuider",method=RequestMethod.POST)
	@CrossOrigin
	@ResponseBody
	public ResponseMessage<String>  modifyShopGuider(@RequestBody SellerModel sellerModel){
		return shopGuiderServiceImpl.modifyShopGuider(sellerModel);
	}
	/**
	 * 编辑店面导购认证状态
	 * @param SellerModel
	 * @return
	 */
	@RequestMapping(value="/modifyShopGuiderState",method=RequestMethod.POST)
	@CrossOrigin
	@ResponseBody
	 public ResponseMessage<String>  modifyShopGuiderState(@RequestBody Map<String, Object> sellerModel){
		 ResponseMessage<String> message = new ResponseMessage<>();
			if(shopGuiderServiceImpl.modifyShopGuiderState(sellerModel)){
				message.setCode(SysConstant.SUCCESS_CODE);
				message.setMessage(SysConstant.SUCCESS_MSG);
			}else{
				message.setCode(SysConstant.FAILURE_CODE);
				message.setMessage(SysConstant.FAILURE_MSG);
			}
			return message;
	 }
	
	/**
	 * 查询店面导购
	 * @param SellerModel
	 * @return
	 */
	@RequestMapping(value="/queryShopGuideres",method=RequestMethod.POST)
	@ResponseBody
	 public Page<ShopGuiderModel>  queryShopGuideres(@RequestBody Page<ShopGuiderModel> page){
		return	shopGuiderServiceImpl.getShopGuideresForPage(page);
	 }
	
	/**
	 * 查询店面导购
	 * @param SellerModel
	 * @return
	 */
	@RequestMapping(value="/queryShopGuider/{id}",method=RequestMethod.GET)
	@ResponseBody
	 public ResponseMessage<Map<String, Object>> queryShopGuider(@PathVariable Long id){
		Map<String, Object> shopGuider = shopGuiderServiceImpl.getShopGuider(id);
		 ResponseMessage<Map<String, Object>> message = new ResponseMessage<>();
		if(shopGuider!=null){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			message.setResult(shopGuider);
		}else{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
	 }
	
	/**
	 * 查询店面导购
	 * @param SellerModel
	 * @return
	 */
	@RequestMapping(value="/queryemployee",method=RequestMethod.GET)
	@ResponseBody
	 public  List<Map<String, Object>> queryShopGuiderForSelect(){

		return  shopGuiderServiceImpl.queryShopGuiderForSelect();
	 }
}
