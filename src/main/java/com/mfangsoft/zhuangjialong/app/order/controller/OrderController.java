package com.mfangsoft.zhuangjialong.app.order.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.mfangsoft.zhuangjialong.app.constructAppointment.model.ConstructAppointment;
import com.mfangsoft.zhuangjialong.app.coupons.model.BrandCouponsModel;
import com.mfangsoft.zhuangjialong.app.order.mapper.OrderPayEntityMapper;
import com.mfangsoft.zhuangjialong.app.order.model.OrderCashEntity;
import com.mfangsoft.zhuangjialong.app.order.model.OrderCashNewEntity;
import com.mfangsoft.zhuangjialong.app.order.model.OrderEntity;
import com.mfangsoft.zhuangjialong.app.order.model.OrderJsonModel;
import com.mfangsoft.zhuangjialong.app.order.model.OrderPay;
import com.mfangsoft.zhuangjialong.app.order.model.OrderProduct;
import com.mfangsoft.zhuangjialong.app.order.model.OrderStateRelationEntity;
import com.mfangsoft.zhuangjialong.app.order.model.OrderTracing;
import com.mfangsoft.zhuangjialong.app.order.model.StateOrderNums;
import com.mfangsoft.zhuangjialong.app.order.pay_ali.model.AliOrderRequest;
import com.mfangsoft.zhuangjialong.app.order.pay_weixin.UnifiedOrderRequest;
import com.mfangsoft.zhuangjialong.app.order.service.OrderService;
import com.mfangsoft.zhuangjialong.app.order.util.OrderStatusEnum;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.DateUtils;
import com.mfangsoft.zhuangjialong.common.utils.HttpRequest;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.coupons.mapper.BrandCouponsEntityMapper;
import com.sun.xml.internal.xsom.impl.scd.Iterators.Map;

/**
 * @description：订单控制层
 * @author：Jianghua.Tang @date：2016年5月27日
 * 
 */
@Controller(value = "apporder")
@RequestMapping("/app")

public class OrderController {

	@Autowired
	OrderService orderService;
	@Autowired
	OrderPayEntityMapper orderPayEntityMapper;
	@Autowired
	BrandCouponsEntityMapper brandCouponsEntityMapper;

	public static void main(String[] args) {
		// List<String> testList = new ArrayList<>();
		// testList.add("one");
		// testList.add("two");
		// testList.add("three");
		// testList.add("two");
		// String value = testList.toString();
		// Iterator<String> i = testList.iterator();
		// while (i.hasNext()) {
		// if (i.next().equals("two")) {
		// i.remove();
		// }
		// }
		// try {
		// System.out.println(SecureRandom.getInstanceStrong().nextLong());
		// } catch (NoSuchAlgorithmException e) {
		// e.printStackTrace();
		// }
		//
		// System.out.println(UUID.randomUUID());
		//
		// String account = "18500518279";
		// StringBuffer convert_code = new StringBuffer();
		// convert_code.append(account.substring(0, 3));
		// convert_code.append(java.util.UUID.randomUUID().toString().toUpperCase().substring(0,
		// 8));
		// convert_code.append(account.substring(7, 11));
		// System.out.println(convert_code.toString());

		List<String> list = new ArrayList<>();
//		list.add("ertfetger");
//		list.add("(dsv ,sdv, drv)");
		
		System.out.println(list.toString().replace("[", "").replace("]", ""));
		
		List<String> list_1 = Arrays.asList("1");		
		
		System.out.println(new Gson().toJson(list));

		System.out.println(DateUtils.formatDate_(new Date(new Date().getTime() - 30 * SysConstant.MILLIS_PER_DAY)));
		// HttpRequest.sendPost("http://127.0.0.1:8080/shop-web/common/updoadimgbase64ForImgList",
		// new Gson().toJson(list));
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 30);

		System.out.println(DateUtils.formatDate_(new Date(calendar.getTimeInMillis())));
		
		
		String str = "upload";
		
//		Pattern pattern = Pattern.compile("*upload*");
		
		System.out.println(Pattern.matches("^.*upload.*", "ttuploadimageswithsize"));
		
	}

	@RequestMapping(value = "/testquartz", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage testquartz(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		ResponseMessage<List<OrderJsonModel>> responseMessage = new ResponseMessage<List<OrderJsonModel>>();

		System.out.println("session.getId() : " + session.getId());

		System.out.println("request code : " + request.getParameter("code"));

		// try {
		// Mark2D.get2DMarkToFile("www.baidu.com", response, 200,
		// 200,"e:/","test.png");
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		String redirect_uri = "http%3A%2F%2Fwww.zjial.com%2Fshop-web%2Fapp%2Ftestquartz";
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxebc7f519e90620c9&redirect_uri="
				+ redirect_uri + "&response_type=code&scope=snsapi_base&state=0#wechat_redirect";
		HttpRequest.sendPost(url, "");

		ConcurrentUpdateSolrClient client = new ConcurrentUpdateSolrClient("http://url", 10, 5);
		
		return responseMessage;
	}

	@RequestMapping(value = "/getOrderCode", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public String getOrderCode() {
		return orderService.getOrderCode();
	}

	@RequestMapping(value = "/addorder", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<OrderJsonModel>> addOrder(@RequestBody OrderPay orders) {
		ResponseMessage<List<OrderJsonModel>> responseMessage = new ResponseMessage<List<OrderJsonModel>>();
		
		try{
			return orderService.addOrders(orders, responseMessage);
		}catch(Exception e){
			e.printStackTrace();
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
			return responseMessage;
		}
		
	}

	@RequestMapping(value = "/customerorderlist", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Page<OrderEntity>> customerOrderList(@RequestBody OrderPay orderPay) {

		ResponseMessage<Page<OrderEntity>> responseMessage = new ResponseMessage<Page<OrderEntity>>();
		Page<OrderEntity> resultPage = orderService.customerOrderList(orderPay);
		responseMessage.setCode("0");
		responseMessage.setMessage("success");
		responseMessage.setResult(resultPage);
		return responseMessage;
	}

	@RequestMapping(value = "/customerorderdetails", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<OrderEntity> customerOrderDetails(@RequestBody OrderEntity param) {

		ResponseMessage<OrderEntity> responseMessage = new ResponseMessage<OrderEntity>();

		OrderEntity o = orderService.customerOrderDetails(param);
		responseMessage.setCode("0");
		responseMessage.setMessage("success");
		responseMessage.setResult(o);
		return responseMessage;

	}

	/**
	 * 查询订单商品详情列表，用于评价
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/customerorderproductsdetails", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<OrderProduct>> customerorderoneproductdetails(@RequestBody OrderEntity param) {

		ResponseMessage<List<OrderProduct>> responseMessage = new ResponseMessage<List<OrderProduct>>();

		List<OrderProduct> oList = orderService.customerorderproductsdetails(param);
		responseMessage.setCode("0");
		responseMessage.setMessage("success");
		responseMessage.setResult(oList);
		return responseMessage;

	}

	@RequestMapping(value = "/customercancelorder", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage customerCanceOrder(@RequestBody OrderStateRelationEntity orderEntity) {
		ResponseMessage responseMessage = new ResponseMessage();

		orderEntity.setOrder_state_code(OrderStatusEnum.CANCEl.value);
		orderEntity.setTime(new Date());

		if (orderService.insertOrderState(orderEntity) > 0) {
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			return responseMessage;
		}
		responseMessage.setCode(SysConstant.FAILURE_CODE);
		responseMessage.setMessage(SysConstant.ORDER_UPDATE_FAIL);

		return responseMessage;
	}

	@RequestMapping(value = "/customerdeleteorder", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Object> customerDeleteOrder(@RequestBody OrderStateRelationEntity orderEntity) {
		ResponseMessage<Object> responseMessage = new ResponseMessage<Object>();

		orderEntity.setOrder_state_code(OrderStatusEnum.DELETED.value);
		orderEntity.setTime(new Date());

		if (orderService.insertOrderState(orderEntity) > 0) {
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			return responseMessage;
		}
		responseMessage.setCode(SysConstant.FAILURE_CODE);
		responseMessage.setMessage(SysConstant.ORDER_UPDATE_FAIL);
		return responseMessage;
	}

	@RequestMapping(value = "/customerpayorder", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage customerPayOrder(@RequestBody UnifiedOrderRequest unifiedOrderRequest)
			throws ClientProtocolException, IOException {
		// 需要根据 优惠券 红包 参加的活动 预付金重新计算订单价格

		ResponseMessage<Object> responseMessage = new ResponseMessage<Object>();
		UnifiedOrderRequest result = orderService.customerPayOrder(unifiedOrderRequest);
		if (StringUtils.isEmpty(result.getPrepay_id())) {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
			return responseMessage;
		}
		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		responseMessage.setResult(result);
		return responseMessage;
	}

	/**
	 * 微信支付订单尾款
	 * 
	 * @param unifiedOrderRequest
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@RequestMapping(value = "/customerpayorderfordeposit", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage customerpayorderfordeposit(@RequestBody UnifiedOrderRequest unifiedOrderRequest)
			throws ClientProtocolException, IOException {
		// 需要根据 优惠券 红包 参加的活动 预付金重新计算订单价格

		ResponseMessage<Object> responseMessage = new ResponseMessage<Object>();
		UnifiedOrderRequest result = orderService.customerpayorderfordeposit(unifiedOrderRequest);
		if (StringUtils.isEmpty(result.getPrepay_id())) {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
			return responseMessage;
		}
		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		responseMessage.setResult(result);
		return responseMessage;
	}

	/**
	 * 微信支付虚拟券
	 * 
	 * @param unifiedOrderRequest
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@RequestMapping(value = "/customerpayorderforcash", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage customerpayorderforcash(@RequestBody OrderCashEntity orderCashEntity)
			throws ClientProtocolException, IOException {
		// 需要根据 优惠券 红包 参加的活动 预付金重新计算订单价格

		ResponseMessage<Object> responseMessage = new ResponseMessage<Object>();
		UnifiedOrderRequest result = orderService.customerpayorderforcash(orderCashEntity);
		if (StringUtils.isEmpty(result.getPrepay_id())) {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
			return responseMessage;
		}
		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		responseMessage.setResult(result);
		return responseMessage;
	}

	/**
	 * 微信支付商铺虚拟券 新
	 * 
	 * @param unifiedOrderRequest
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@RequestMapping(value = "/customerpayorderforcashnew", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage customerpayorderforPreShopcartPay(@RequestBody OrderCashNewEntity orderCashNewEntity)
			throws ClientProtocolException, IOException {
		// 需要根据 优惠券 红包 参加的活动 预付金重新计算订单价格

		ResponseMessage<Object> responseMessage = new ResponseMessage<Object>();
		UnifiedOrderRequest result = orderService.customerpayorderforPreShopcartPay(orderCashNewEntity);
		if (StringUtils.isEmpty(result.getPrepay_id())) {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
			return responseMessage;
		}
		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		responseMessage.setResult(result);
		return responseMessage;
	}

	/**
	 * 微信支付商铺虚拟券 新
	 * 
	 * @param unifiedOrderRequest
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@RequestMapping(value = "/customerpayorderforconstruct", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage customerpayorderforconstruct(@RequestBody ConstructAppointment param)
			throws ClientProtocolException, IOException {
		// 需要根据 优惠券 红包 参加的活动 预付金重新计算订单价格

		ResponseMessage<Object> responseMessage = new ResponseMessage<Object>();
		UnifiedOrderRequest result = orderService.customerpayorderforconstruct(param);
		if (StringUtils.isEmpty(result.getPrepay_id())) {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
			return responseMessage;
		}
		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		responseMessage.setResult(result);
		return responseMessage;
	}

	/**
	 * 微信支付优惠券 新
	 * 
	 * @param unifiedOrderRequest
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@RequestMapping(value = "/customerpayorderforcoupons", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage customerpayorderforCoupons(@RequestBody BrandCouponsModel param)
			throws ClientProtocolException, IOException {

		ResponseMessage<Object> responseMessage = new ResponseMessage<Object>();
		UnifiedOrderRequest result = orderService.customerpayorderforCoupons(param);
		if (StringUtils.isEmpty(result.getPrepay_id())) {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
			return responseMessage;
		}
		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		responseMessage.setResult(result);
		return responseMessage;
	}

	// 微信异步通知 - 首次支付
	@RequestMapping(value = "/WXRnotify", method = RequestMethod.POST)
	@ResponseBody
	public void notifyWeixin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		orderService.notifyWeixin(request, response);
	}

	// 微信异步通知 - 尾款
	@RequestMapping(value = "/WXRnotifyForDeposit", method = RequestMethod.POST)
	@ResponseBody
	public void WXRnotifyForDeposit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		orderService.WXRnotifyForDeposit(request, response);
	}

	// 微信异步通知 - 虚拟券
	@RequestMapping(value = "/WXRnotifyForCash", method = RequestMethod.POST)
	@ResponseBody
	public void WXRnotifyForCash(HttpServletRequest request, HttpServletResponse response) throws Exception {
		orderService.WXRnotifyForCash(request, response);
	}

	// 微信异步通知 - 虚拟券 新
	@RequestMapping(value = "/WXRnotifyForCashNew", method = RequestMethod.POST)
	@ResponseBody
	public void WXRnotifyForPreShopcartPay(HttpServletRequest request, HttpServletResponse response) throws Exception {
		orderService.WXRnotifyForPreShopcartPay(request, response);
	}

	// 微信异步通知 - 施工预约
	@RequestMapping(value = "/WXRnotifyForConstract", method = RequestMethod.POST)
	@ResponseBody
	public void WXRnotifyForConstract(HttpServletRequest request, HttpServletResponse response) throws Exception {
		orderService.WXRnotifyForConstract(request, response);
	}

	// 微信异步通知 - 优惠券
	@RequestMapping(value = "/WXRnotifyForCoupons", method = RequestMethod.POST)
	@ResponseBody
	public void WXRnotifyForCoupons(HttpServletRequest request, HttpServletResponse response) throws Exception {
		orderService.WXRnotifyForCoupons(request, response);
	}

	@RequestMapping(value = "/customerpayorderH5", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage customerPayOrderH5(@RequestBody UnifiedOrderRequest unifiedOrderRequest)
			throws ClientProtocolException, IOException {
		// 需要根据 优惠券 红包 参加的活动 预付金重新计算订单价格

		ResponseMessage<Object> responseMessage = new ResponseMessage<Object>();
		UnifiedOrderRequest result = orderService.customerPayOrderH5(unifiedOrderRequest);
		if (StringUtils.isEmpty(result.getPrepay_id())) {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
			return responseMessage;
		}
		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		responseMessage.setResult(result);
		return responseMessage;
	}
	/**
	 * 微信支付订单尾款H5
	 * 
	 * @param unifiedOrderRequest
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@RequestMapping(value = "/customerpayorderfordepositH5", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage customerpayorderfordepositH5(@RequestBody UnifiedOrderRequest unifiedOrderRequest)
			throws ClientProtocolException, IOException {
		// 需要根据 优惠券 红包 参加的活动 预付金重新计算订单价格

		ResponseMessage<Object> responseMessage = new ResponseMessage<Object>();
		UnifiedOrderRequest result = orderService.customerpayorderfordepositH5(unifiedOrderRequest);
		if (StringUtils.isEmpty(result.getPrepay_id())) {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
			return responseMessage;
		}
		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		responseMessage.setResult(result);
		return responseMessage;
	}
	
	/**
	 * 微信支付虚拟券
	 * 
	 * @param unifiedOrderRequest
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@RequestMapping(value = "/customerpayorderforcashH5", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage customerpayorderforcashH5(@RequestBody OrderCashEntity orderCashEntity)
			throws ClientProtocolException, IOException {
		// 需要根据 优惠券 红包 参加的活动 预付金重新计算订单价格

		ResponseMessage<Object> responseMessage = new ResponseMessage<Object>();
		UnifiedOrderRequest result = orderService.customerpayorderforcashH5(orderCashEntity);
		if (StringUtils.isEmpty(result.getPrepay_id())) {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
			return responseMessage;
		}
		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		responseMessage.setResult(result);
		return responseMessage;
	}

	/**
	 * 微信支付商铺虚拟券 新H5
	 * 
	 * @param unifiedOrderRequest
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@RequestMapping(value = "/customerpayorderforcashnewH5", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage customerpayorderforcashNewH5(@RequestBody OrderCashNewEntity orderCashNewEntity)
			throws ClientProtocolException, IOException {
		// 需要根据 优惠券 红包 参加的活动 预付金重新计算订单价格

		ResponseMessage<Object> responseMessage = new ResponseMessage<Object>();
		UnifiedOrderRequest result = orderService.customerpayorderforcashNewH5(orderCashNewEntity);
		if (StringUtils.isEmpty(result.getPrepay_id())) {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
			return responseMessage;
		}
		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		responseMessage.setResult(result);
		return responseMessage;
	}

	/**
	 * 微信支付商铺虚拟券 新H5
	 * 
	 * @param unifiedOrderRequest
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@RequestMapping(value = "/customerpayorderforconstructH5", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage customerpayorderforconstructH5(@RequestBody ConstructAppointment param)
			throws ClientProtocolException, IOException {
		// 需要根据 优惠券 红包 参加的活动 预付金重新计算订单价格

		ResponseMessage<Object> responseMessage = new ResponseMessage<Object>();
		UnifiedOrderRequest result = orderService.customerpayorderforconstructH5(param);
		if (StringUtils.isEmpty(result.getPrepay_id())) {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
			return responseMessage;
		}
		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		responseMessage.setResult(result);
		return responseMessage;
	}

	/**
	 * 微信支付优惠券 新H5
	 * 
	 * @param unifiedOrderRequest
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@RequestMapping(value = "/customerpayorderforcouponsH5", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage customerpayorderforCouponsH5(@RequestBody BrandCouponsModel param)
			throws ClientProtocolException, IOException {

		ResponseMessage<Object> responseMessage = new ResponseMessage<Object>();
		UnifiedOrderRequest result = orderService.customerpayorderforCouponsH5(param);
		if (StringUtils.isEmpty(result.getPrepay_id())) {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
			return responseMessage;
		}
		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		responseMessage.setResult(result);
		return responseMessage;
	}
	
	/**
	 * 阿里支付请求参数
	 * 
	 * @param unifiedOrderRequest
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@RequestMapping(value = "/customerAliPayOrder", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage customerAliPayOrder(@RequestBody AliOrderRequest aliOrderRequest)
			throws ClientProtocolException, IOException {

		return orderService.customerAliPayOrder(aliOrderRequest);

	}

	// 阿里异步通知
	@RequestMapping(value = "/ALIXYZnotify", method = RequestMethod.POST)
	@ResponseBody
	public void notifyAli(HttpServletRequest request, HttpServletResponse response) throws Exception {

		orderService.notifyAli(request, response);
	}

	/**
	 * 阿里支付尾款请求参数
	 * 
	 * @param unifiedOrderRequest
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@RequestMapping(value = "/customerAliPayOrderForDeposit", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage customerAliPayOrderForDeposit(@RequestBody AliOrderRequest aliOrderRequest)
			throws ClientProtocolException, IOException {

		return orderService.customerAliPayOrderForDeposit(aliOrderRequest);

	}

	// 阿里异步通知 尾款
	@RequestMapping(value = "/ALIXYZnotifyForDeposit", method = RequestMethod.POST)
	@ResponseBody
	public void ALIXYZnotifyForDeposit(HttpServletRequest request, HttpServletResponse response) throws Exception {

		orderService.ALIXYZnotifyForDeposit(request, response);
	}

	/**
	 * 阿里支付虚拟券请求参数
	 * 
	 * @param unifiedOrderRequest
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@RequestMapping(value = "/customerAliPayOrderForCash", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage customerAliPayOrderForCash(@RequestBody OrderCashEntity orderCashEntity)
			throws ClientProtocolException, IOException {

		return orderService.customerAliPayOrderForCash(orderCashEntity);

	}

	/**
	 * 阿里异步通知 虚拟券-品牌折扣券
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/ALIXYZnotifyForCash", method = RequestMethod.POST)
	@ResponseBody
	public void ALIXYZnotifyForCash(HttpServletRequest request, HttpServletResponse response) throws Exception {

		orderService.ALIXYZnotifyForCash(request, response);
	}

	/**
	 * 阿里支付虚拟券请求参数_new
	 * 
	 * @param unifiedOrderRequest
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@RequestMapping(value = "/customerAliPayOrderForCashNew", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage customerAliPayOrderForPreShopcartPay(@RequestBody OrderCashNewEntity orderCashNewEntity)
			throws ClientProtocolException, IOException {

		return orderService.customerAliPayOrderForPreShopcartPay(orderCashNewEntity);

	}

	/**
	 * 阿里异步通知 虚拟券_new
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/ALIXYZnotifyForCashNew", method = RequestMethod.POST)
	@ResponseBody
	public void ALIXYZnotifyForPreShopcartPay(HttpServletRequest request, HttpServletResponse response) throws Exception {

		orderService.ALIXYZnotifyForPreShopcartPay(request, response);
	}

	/**
	 * 阿里施工预约支付请求参数_new
	 * 
	 * @param unifiedOrderRequest
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@RequestMapping(value = "/customerAliPayOrderForConstruct", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage customerAliPayOrderForConstruct(@RequestBody ConstructAppointment param)
			throws ClientProtocolException, IOException {

		return orderService.customerAliPayOrderForConstruct(param);

	}

	/**
	 * 阿里异步通知 施工预约_new
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/ALIXYZnotifyForConstruct", method = RequestMethod.POST)
	@ResponseBody
	public void ALIXYZnotifyForConstruct(HttpServletRequest request, HttpServletResponse response) throws Exception {

		orderService.ALIXYZnotifyForConstruct(request, response);
	}

	/**
	 * 阿里优惠券支付请求参数_new
	 * 
	 * @param unifiedOrderRequest
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@RequestMapping(value = "/customerAliPayOrderForCoupons", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage customerAliPayOrderForCoupons(@RequestBody BrandCouponsModel param)
			throws ClientProtocolException, IOException {

		return orderService.customerAliPayOrderForCoupons(param);

	}

	/**
	 * 阿里异步通知 优惠券_new
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/ALIXYZnotifyForCoupons", method = RequestMethod.POST)
	@ResponseBody
	public void ALIXYZnotifyForCoupons(HttpServletRequest request, HttpServletResponse response) throws Exception {

		orderService.ALIXYZnotifyForCoupons(request, response);
	}

	/*
	 * 确认收货，订单待评价
	 * 
	 */
	@RequestMapping(value = "/customerconfirmgoods", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage customerConfirmGoods(@RequestBody OrderStateRelationEntity orderEntity) {

		ResponseMessage<Object> responseMessage = new ResponseMessage<Object>();

		OrderStateRelationEntity record = new OrderStateRelationEntity();
		record.setOrder_code(orderEntity.getOrder_code());
		record.setOrder_state_code(OrderStatusEnum.NEEDCOMMENT.value);
		record.setTime(new Date());

		if (orderService.insertOrderState(record) > 0) {
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			return responseMessage;
		}
		responseMessage.setCode(SysConstant.FAILURE_CODE);
		responseMessage.setMessage(SysConstant.ORDER_UPDATE_FAIL);

		return responseMessage;

	}

	@RequestMapping(value = "/customerordertracing", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<OrderTracing>> customerOrderTracking(@RequestBody OrderEntity order) {
		ResponseMessage<List<OrderTracing>> responseMessage = new ResponseMessage<List<OrderTracing>>();

		List<OrderTracing> orderTracing = orderService.selectOrderTraceInfoByCode(order.getOrder_code());
		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		responseMessage.setResult(orderTracing);
		return responseMessage;
	}

	/**
	 * 获取一个用户个状态订单个数
	 * 
	 * @param param
	 *            customer_id
	 * @return
	 */
	@RequestMapping(value = "/customerorderstatenum", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<StateOrderNums> customerorderstatenum(@RequestBody OrderEntity param) {

		ResponseMessage<StateOrderNums> responseMessage = new ResponseMessage<StateOrderNums>();

		responseMessage.setCode("0");
		responseMessage.setMessage("success");
		responseMessage.setResult(orderService.customerorderstatenum(param));
		return responseMessage;

	}
}
