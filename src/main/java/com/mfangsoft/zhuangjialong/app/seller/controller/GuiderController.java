/**
 * 
 *
 */
package com.mfangsoft.zhuangjialong.app.seller.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.mfangsoft.zhuangjialong.app.applogin.model.CustomerEntity;
import com.mfangsoft.zhuangjialong.app.seller.model.Guild;
import com.mfangsoft.zhuangjialong.app.seller.model.OrderProfitShareModel;
import com.mfangsoft.zhuangjialong.app.seller.model.SellerEntity;
import com.mfangsoft.zhuangjialong.app.seller.service.GuiderService;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.MD5Util;
import com.mfangsoft.zhuangjialong.common.utils.NumberUtil;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;

/**
 * @description：推广导购
 * @author：Liyanchen @date：2016年7月12日
 * 
 */
@Controller(value = "appGuild")
@RequestMapping("/app")
public class GuiderController {

	@Autowired
	GuiderService guiderService;

	@RequestMapping(value = "/sellerlogin", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Guild> sellerlogin(@RequestBody Guild guild, HttpSession session) {
		ResponseMessage<Guild> message = new ResponseMessage<>();
		
		message = guiderService.login(guild, session);
		return message;
	}

	@RequestMapping(value = "/sellerlogout", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Guild> sellerlogout(@RequestBody Guild guild, HttpSession session) {
		ResponseMessage<Guild> message = new ResponseMessage<>();
		
		message = guiderService.logout(guild, session);
		return message;
	}
	
public static void main(String[] args) {
	//id, name, account, password, phone, head_img,create_time,invite_code,partner_id
	Guild guild = new Guild();
	
	guild.setId(123L);
	guild.setName("xxxx");
	guild.setAccount("14235466565");
	guild.setPhone("243252456346");
	guild.setHead_img("http://22343535235");
System.out.println(new Gson().toJson(guild));

System.out.println(MD5Util.MD5("123456"));

}

@RequestMapping(value = "/resetpassword1", method = RequestMethod.POST)
@ResponseBody
@CrossOrigin
public ResponseMessage<Guild> resetPassWord1(@RequestBody Guild guild) {
	ResponseMessage<Guild> message = new ResponseMessage<>();
	
	message = guiderService.resetPassWord1(guild);
	return message;
}

@RequestMapping(value = "/resetpassword2", method = RequestMethod.POST)
@ResponseBody
@CrossOrigin
public ResponseMessage<Boolean> resetPassWord2(@RequestBody SellerEntity record) {
	ResponseMessage<Boolean> message = new ResponseMessage<>();
	
	message = guiderService.resetPassWord2(record);
	return message;
}


/**
 * 查询推广消费者列表
 * 
 * @param param
 * @return
 */

@RequestMapping(value = "/queryguidcustomerlist", method = RequestMethod.POST)
@ResponseBody
@CrossOrigin
public ResponseMessage<List<CustomerEntity>> queryguidcustomerlist(@RequestBody Guild param) {
	ResponseMessage<List<CustomerEntity>> responseMessage = new ResponseMessage<>();
	responseMessage.setCode(SysConstant.SUCCESS_CODE);
	responseMessage.setMessage(SysConstant.SUCCESS_MSG);
	responseMessage.setResult(guiderService.queryguidcustomerlist(param.getId()));
	return responseMessage;
}

	/**
	 * 查询推广订单收入列表
	 * 
	 * @param param
	 * @return
	 */

	@RequestMapping(value = "/queryguidorderlist", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Page<OrderProfitShareModel>> queryguidorderlist(
			@RequestBody Page<OrderProfitShareModel> param) {
		ResponseMessage<Page<OrderProfitShareModel>> responseMessage = new ResponseMessage<>();
		List<OrderProfitShareModel> list = guiderService.guidorderlist(param);
		param.setData(list);
		param.setParam(null);
		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		responseMessage.setResult(param);
		return responseMessage;
	}

	@RequestMapping(value = "/queryinvitmentlist", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Page<CustomerEntity>> queryinvitmentlist(@RequestBody Page<CustomerEntity> param) {
		ResponseMessage<Page<CustomerEntity>> responseMessage = new ResponseMessage<>();

		List<CustomerEntity> list = guiderService.guildCustomerList(param);
		param.setData(list);
		param.setParam(null);
		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		responseMessage.setResult(param);
		return responseMessage;
	}

	
	@RequestMapping(value = "/queryinvitmentSellerForfisrtPage", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Map<String,String>> queryinvitmentSellerForfisrtPage(@RequestBody SellerEntity param) {
		ResponseMessage<Map<String,String>> responseMessage = new ResponseMessage<>();

		Map<String,String> map = guiderService.queryinvitmentSellerForfisrtPage(param);
		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		responseMessage.setResult(map);
		
		return responseMessage;
	}
	
	
	@RequestMapping(value = "/queryinvitmentSellerlist", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Page<SellerEntity>> queryinvitmentSellerlist(@RequestBody Page<SellerEntity> param) {
		ResponseMessage<Page<SellerEntity>> responseMessage = new ResponseMessage<>();

		List<SellerEntity> list = guiderService.selectGuildSellerListPage(param);
		param.setData(list);
		param.setParam(null);
		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		responseMessage.setResult(param);
		return responseMessage;
	}
	
	@RequestMapping(value = "/queryinvitmentlistcount", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage queryinvitmentlistcount(@RequestBody Guild param) {
		ResponseMessage responseMessage = new ResponseMessage<>();

		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		responseMessage.setResult(guiderService.selectGuildCustomerCount(param.getId()));
		return responseMessage;
	}

	@RequestMapping(value = "/guildfirstpage", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Map<String, String>> guildfirstpage(@RequestBody Guild param) {
		ResponseMessage<Map<String, String>> responseMessage = new ResponseMessage<>();

		Map<String, String> map = new HashMap<String, String>();
		map.put("allMoney", NumberUtil.round2(guiderService.selectSumPriceOfAllOrders(param.getId())));
		map.put("thisMonthMoney", NumberUtil.round2(guiderService.selectGuildSumofThisMonth(param.getId())));
		map.put("lastMonthMoney", NumberUtil.round2(guiderService.selectGuildSumofLastMonth(param.getId())));
		map.put("guildCustomerCount", guiderService.selectGuildCustomerCount(param.getId()) + "");
		map.put("GuildSumOrders", guiderService.selectGuildSumOrders(param.getId())+"");
		map.put("GuildSumShiGong", guiderService.selectAppointmentCountByConstructId(param.getId()) + "");
		map.put("TodayGuildSumOrders", guiderService.selectTodayGuildSumOrders(param.getId()) + "");

		map.put("unReadGuildCustomerCount", guiderService.selectUnReadGuildCustomerCount(param.getId()) + "");
		map.put("unReadGuildOrdersCount", guiderService.selectUnReadGuildOrdersCount(param.getId()) + "");
		map.put("unReadShiGongCount", guiderService.selectUnReadAppointmentCountByConstructId(param.getId()) + "");
		map.put("unReadAppointmentCount", guiderService.selectUnReadAppointmentCountByShopGuiderId(param.getId())+"");
		map.put("totalAppointmentCount", guiderService.selectTotalAppointmentCountByShopGuiderId(param.getId())+"");
		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		responseMessage.setResult(map);
		return responseMessage;
	}
//	/**
//	 * 施工订单列表
//	 * @param param
//	 * @return
//	 */
//	@RequestMapping(value = "/queryconstructappointmentlist", method = RequestMethod.POST)
//	@ResponseBody
//	@CrossOrigin
//	public ResponseMessage<Page<ConstructAppointmentModel>> queryconstructappointmentlist(@RequestBody Page<ConstructAppointmentModel> param) {
//		ResponseMessage<Page<ConstructAppointmentModel>> responseMessage = new ResponseMessage<>();
//
//		List<ConstructAppointmentModel> list = guiderService.guildConstructAppointmentBySellerIdForPage(param);
//		param.setData(list);
//		responseMessage.setCode(SysConstant.SUCCESS_CODE);
//		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
//		responseMessage.setResult(param);
//		return responseMessage;
//	}
}
