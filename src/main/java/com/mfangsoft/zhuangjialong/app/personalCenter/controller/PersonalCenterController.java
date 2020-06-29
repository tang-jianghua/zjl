package com.mfangsoft.zhuangjialong.app.personalCenter.controller;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gson.Gson;
import com.mfangsoft.zhuangjialong.app.applogin.model.CustomerEntity;
import com.mfangsoft.zhuangjialong.app.order.service.OrderService;
import com.mfangsoft.zhuangjialong.app.personalCenter.mapper.CustomerCollectionEntityMapper;
import com.mfangsoft.zhuangjialong.app.personalCenter.mapper.CustomerPointEntityMapper;
import com.mfangsoft.zhuangjialong.app.personalCenter.mapper.PointEntityMapper;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.CollectBrandModel;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.CustomerPointEntity;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.HistoryEntity;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.MessageEntity;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.PartnerSendMessageModel;
import com.mfangsoft.zhuangjialong.app.personalCenter.service.PersonalCenterService;
import com.mfangsoft.zhuangjialong.app.product.model.ProductListModel;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.HttpRequest;
import com.mfangsoft.zhuangjialong.common.utils.MessageConstant;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.coupons.mapper.BrandCouponsEntityMapper;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.BuildClassEntity;
import com.mfangsoft.zhuangjialong.integration.newcoupons.model.CustomerCouponsModel;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.ClassAttrValueEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.model.UnionCustomer;
import com.mfangsoft.zhuangjialong.integration.promotion.model.UnionPromotion;

/**
 * @description：
 * 
 * @author：Jianghua.Tang @date：2016年5月28日
 * 
 */
@Controller
@RequestMapping("/app")
public class PersonalCenterController {
	@Autowired
	private PersonalCenterService personalCenterServiceImpl;
	@Autowired
	private BrandCouponsEntityMapper brandCouponsEntityMapper;
	@Autowired
	PointEntityMapper pointEntityMapper;
	@Autowired
	CustomerCollectionEntityMapper customerCollectionEntityMapper;
	@Autowired
	OrderService orderService;
	@Autowired
	CustomerPointEntityMapper customerPointEntityMapper;

	@RequestMapping(value = "/querycoupons", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<CustomerCouponsModel>> queryCoupons(@RequestBody CustomerCouponsModel b) {

		return personalCenterServiceImpl.queryCoupons(b);
	}

	@RequestMapping(value = "/queryredbag", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<CustomerCouponsModel>> queryRedbag(@RequestBody CustomerCouponsModel b) {
		List<CustomerCouponsModel> brandCouponsEntity = personalCenterServiceImpl
				.selectRedBagByCustomerId(b);

		ResponseMessage<List<CustomerCouponsModel>> responseMessage = new ResponseMessage<>();
		responseMessage.setCode("0");
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		responseMessage.setResult(brandCouponsEntity);
		return responseMessage;

	}

	@RequestMapping(value = "/querycash", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<UnionPromotion>> queryCash(@RequestBody UnionCustomer param) {

		ResponseMessage<List<UnionPromotion>> responseMessage = new ResponseMessage<>();

		if (param.getCustomer_id() == null) {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.not_login);
			return responseMessage;
		}
		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		responseMessage.setResult(personalCenterServiceImpl.selectCashByCustomerId(param.getCustomer_id()));
		return responseMessage;

	}

	@RequestMapping(value = "/getpointdescription", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<String>> getpointdescription() {

		List<String> msg = new ArrayList<>();
		msg.add(String.format("1. 预约积分：每预约成功一次积%d分", SysConstant.Appointment_Point));
		msg.add(String.format("2. 推广积分：每推广一个客户积%d分", SysConstant.Expand_Point));
		msg.add(String.format("3. 分享积分：每分享成功一次积%d分", SysConstant.Share_Point));
		msg.add(String.format("4. 订单积分：商品金额每%d元积1分", SysConstant.Order_Point));

		ResponseMessage<List<String>> responseMessage = new ResponseMessage<List<String>>();

		responseMessage.setCode("0");
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		responseMessage.setResult(msg);
		return responseMessage;
	}

	@RequestMapping(value = "/querytotalpoint", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Integer> queryTotalPoints(@RequestBody CustomerCouponsModel b) {

		Long customer_id = b.getCustomer_id();
		int sum = customerPointEntityMapper.selectSumPoint(customer_id);
		
		ResponseMessage<Integer> responseMessage = new ResponseMessage<>();
		responseMessage.setCode("0");
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		responseMessage.setResult(sum);
		return responseMessage;
	}
	
	@RequestMapping(value = "/querysumpoint", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Integer> querysumpoint(@RequestBody CustomerPointEntity b) {

		Long customer_id = b.getCustomer_id();
		int sum = customerPointEntityMapper.selectSumPoint(customer_id);
		
		ResponseMessage<Integer> responseMessage = new ResponseMessage<>();
		responseMessage.setCode("0");
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		responseMessage.setResult(sum);
		return responseMessage;
	}
	
	@RequestMapping(value = "/querypointlist", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<CustomerPointEntity>> querypointlist(
			@RequestBody CustomerPointEntity customerPointEntity) {
		ResponseMessage<List<CustomerPointEntity>> responseMessage = new ResponseMessage<List<CustomerPointEntity>>();

		List<CustomerPointEntity> ap = customerPointEntityMapper.querypointlist(customerPointEntity.getCustomer_id());
			responseMessage.setCode("0");
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			responseMessage.setResult(ap);
			return responseMessage;
	}

	@RequestMapping(value = "/querycollectbrand", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<CollectBrandModel>> queryCollectBrand(@RequestBody Map<String, Object> param) {

		ResponseMessage<List<CollectBrandModel>> responseMessage = new ResponseMessage<>();

		List<CollectBrandModel> list = personalCenterServiceImpl.selectBrandByCustomerId(param);
		if (list.size() > 0) {
			responseMessage.setCode("0");
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			responseMessage.setResult(list);
			return responseMessage;
		}
		responseMessage.setCode("1");
		responseMessage.setMessage("无任何收藏品牌");
		return responseMessage;
	}

	@JsonInclude(value = Include.NON_NULL)
	class searchRequest {
		String search_text;
		Long customer_id;

		public String getSearch_text() {
			return search_text;
		}

		public void setSearch_text(String search_text) {
			this.search_text = search_text;
		}

		public Long getCustomer_id() {
			return customer_id;
		}

		public void setCustomer_id(Long customer_id) {
			this.customer_id = customer_id;
		}
	}

	
	@RequestMapping(value = "/searchcollectproduct", method = RequestMethod.POST)

	@ResponseBody

	@CrossOrigin
	public ResponseMessage<Page<Map<String, Object>>> searchCollectProduct(
			@RequestBody Page<Map<String, Object>> param) {

		ResponseMessage<Page<Map<String, Object>>> responseMessage = new ResponseMessage<>();
		Map<String, Object> map = (Map<String, Object>) param.getParam();
		if (map.get("customer_id") == null) {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.not_login);
			return responseMessage;
		}
		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG); //
		responseMessage.setResult(personalCenterServiceImpl.getCollectionProduct(param));
		return responseMessage;

	}
	   
/*	  @RequestMapping(value = "/searchcollectproduct", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Page<ProductListModel>> searchCollectProduct(@RequestBody Page<ProductListModel> param) {

		ResponseMessage<Page<ProductListModel>> responseMessage = new ResponseMessage<>();
		Map<String, Object> map = (Map<String, Object>) param.getParam();
		if (map.get("customer_id") == null) {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.not_login);
			return responseMessage;
		}
		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		 responseMessage.setResult(personalCenterServiceImpl.getSqlCollectionProduct(param));
		return responseMessage;

	}*/

	@RequestMapping(value = "/canclecollections", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> cancleCollections(@RequestBody Map<String, Object> param) {
		ResponseMessage<String> responseMessage = new ResponseMessage<>();
		boolean b = personalCenterServiceImpl.updateCollections(param);
		if (b) {
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			return responseMessage;
		}
		responseMessage.setCode(SysConstant.FAILURE_CODE);
		responseMessage.setMessage(SysConstant.FAILURE_MSG);
		return responseMessage;
	}

	@RequestMapping(value = "/addhistory", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> addHistory(@RequestBody HistoryEntity history) {
		ResponseMessage<String> responseMessage = new ResponseMessage<>();
		if (history.getCustomer_id() == null) {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.not_login);
			return responseMessage;
		}
		boolean b = personalCenterServiceImpl.addHistory(history);
		if (b) {
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			return responseMessage;
		}
		responseMessage.setCode(SysConstant.FAILURE_CODE);
		responseMessage.setMessage(SysConstant.FAILURE_MSG);
		return responseMessage;
	}

	@RequestMapping(value = "/queryhistory", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<Map<String, Object>>> queryHistory(@RequestBody HistoryEntity param) {
		ResponseMessage<List<Map<String, Object>>> responseMessage = new ResponseMessage<>();
		if (param.getCustomer_id() == null) {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.not_login);
			return responseMessage;
		}
		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		responseMessage.setResult(personalCenterServiceImpl.getHistoryByCustomerId(param));
		return responseMessage;
	}
	  
	
/*	@RequestMapping(value = "/queryhistory", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<ProductListModel>> queryHistory(@RequestBody HistoryEntity param) {
		ResponseMessage<List<ProductListModel>> responseMessage = new ResponseMessage<>();
		if (param.getCustomer_id() == null) {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.not_login);
			return responseMessage;
		}
		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		responseMessage.setResult(personalCenterServiceImpl.getSqlHistoryByCustomerId(param));
		return responseMessage;
	}*/

	@RequestMapping(value = "/removeallhistory", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> removeAllHistory(@RequestBody HistoryEntity param) {
		ResponseMessage<String> responseMessage = new ResponseMessage<>();
		if (param.getCustomer_id() == null) {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.not_login);
			return responseMessage;
		}
		personalCenterServiceImpl.updateAllHistory(param);
		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		return responseMessage;
	}

	/**
	 * 消费者查询消息
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/qureymessage", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Map<String, List<MessageEntity>>> qureyMessage(@RequestBody MessageEntity param) {
		ResponseMessage<Map<String, List<MessageEntity>>> responseMessage = new ResponseMessage<>();
		List<MessageEntity> messageList = new ArrayList<MessageEntity>();

		List<MessageEntity> messageEntity = personalCenterServiceImpl.selectByCustomer_Id(param.getCustomer_id());
		if (messageEntity != null && messageEntity.size() > 0) {
			for (MessageEntity m : messageEntity) {

				String title = m.getTitle();
				String content = m.getContent();

				if (m.getType_id().intValue() == MessageConstant.SmartEquipment.intValue()) {
					if (StringUtils.isNotEmpty(m.getParams())) {
						if (m.getParams().split(",").length > 0) {
							title = MessageFormat.format(m.getContent(), m.getParams().split(",")[0]);
						} else {
							continue;
						}
						if (m.getParams().split(",").length > 1) {
							content = MessageFormat.format(m.getContent(), m.getParams().split(",")[1]);
						} else {
							continue;
						}
					} else {
						continue;
					}
				} else {
					if (StringUtils.isNotEmpty(m.getParams())) {
						content = MessageFormat.format(m.getContent(), m.getParams().split(","));
					}
				}
				
				MessageEntity me = new MessageEntity();
				me.setId(m.getId());
				me.setTitle(title);
				me.setContent(content);
				me.setCustomer_id(m.getCustomer_id());
				me.setGroup(m.getGroup());
				me.setIsread(m.getIsread());
				me.setTime(m.getTime());
				int typeid = m.getType_id();
				if (typeid == MessageConstant.orderExpire || typeid == MessageConstant.orderCanceled
						|| typeid == MessageConstant.orderSent || typeid == MessageConstant.orderRevice) {
					// 如果是订单消息，传订单号
					if (!StringUtils.isNotEmpty(m.getParams())) {
						continue;
					}
					String[] str = m.getParams().split(",");
					if (str.length > 1) {// 第2参数
						me.setMessage_order_id(str[1]);
					} else if (str.length == 1) {// 第1参数
						me.setMessage_order_id(str[0]);
					}
					me.setImgurl(m.getImgurl());
				}

				messageList.add(me);
			}
		}
		Map<String, List<MessageEntity>> result = new HashMap<String, List<MessageEntity>>();

		List<MessageEntity> list1 = new ArrayList<MessageEntity>();
		result.put("orderMessage", list1);
		List<MessageEntity> list2 = new ArrayList<MessageEntity>();
		result.put("promotionMessage", list2);
		List<MessageEntity> list3 = new ArrayList<MessageEntity>();
		result.put("notenMessage", list3);
		List<MessageEntity> list4 = new ArrayList<MessageEntity>();
		result.put("smartMessage", list4);
		
		for (MessageEntity m : messageList) {
			switch (m.getGroup()) {
			case 1:
				result.get("orderMessage").add(m);
				break;
			case 2:
				result.get("promotionMessage").add(m);
				break;
			case 3:
				result.get("notenMessage").add(m);
				break;
			case 4:
				result.get("smartMessage").add(m);
				break;
			}
		}

		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		responseMessage.setResult(result);
		return responseMessage;
	}

	/**
	 * 消费者清空消息 param 需要customer_id
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/clearmessage", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage clearmessage(@RequestBody MessageEntity param) {
		ResponseMessage responseMessage = new ResponseMessage<>();
		personalCenterServiceImpl.deleteMessageByCustomerId(param.getCustomer_id());

		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		return responseMessage;
	}

	/**
	 * param 需要customer_id
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/queryunreadmessage", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Integer> queryunreadmessage(@RequestBody MessageEntity param) {
		ResponseMessage<Integer> responseMessage = new ResponseMessage<>();
		int count = personalCenterServiceImpl.selectUnReadByCustomerId(param.getCustomer_id());

		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		responseMessage.setResult(count);
		return responseMessage;
	}

	/**
	 * 消费者更新未读消息为已读状态 param 需要customer_id
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/updatemessagestate", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage updatemessagestate(@RequestBody MessageEntity param) {
		ResponseMessage responseMessage = new ResponseMessage<>();

		if (param.getId_array() == null || param.getId_array().length <= 0) {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
			return responseMessage;
		}
		personalCenterServiceImpl.updatemessagestate(param.getCustomer_id(), param.getId_array());

		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		return responseMessage;
	}

	/**
	 * 消费者设置是否接受3种消息 param 需要customer_id
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/updatereceivemessage", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage updatereceivemessage(@RequestBody MessageEntity param) {
		ResponseMessage responseMessage = new ResponseMessage<>();

		CustomerEntity customer = new CustomerEntity();
		customer.setId(param.getCustomer_id());
		customer.setIsreceive_message_order(param.getIsreceive_message_order());
		customer.setIsreceive_message_appointment(param.getIsreceive_message_appointment());
		customer.setIsreceive_message_note(param.getIsreceive_message_note());
		customer.setIsreceive_message_smart(param.getIsreceive_message_smart());

		personalCenterServiceImpl.updatereceivemessage(customer);

		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		return responseMessage;
	}

	/**
	 * 查询接受消息开关 param 需要customer_id
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/queryreceivemessage", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage queryreceivemessage(@RequestBody MessageEntity param) {
		ResponseMessage responseMessage = new ResponseMessage<>();

		CustomerEntity c = personalCenterServiceImpl.queryreceivemessage(param.getCustomer_id());

		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		responseMessage.setResult(c);
		return responseMessage;
	}

	@RequestMapping(value = "/queryallspaces", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Map<String, Object>> queryAllSpaces() {
		ResponseMessage<Map<String, Object>> responseMessage = new ResponseMessage<>();
		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		responseMessage.setResult(personalCenterServiceImpl.selectAllSpaces());
		return responseMessage;
	}

	/*
	 * @RequestMapping(value = "/queryallstyles", method = RequestMethod.POST)
	 * 
	 * @ResponseBody
	 * 
	 * @CrossOrigin public ResponseMessage<List<WallpaperAttrEntity>>
	 * queryAllStyles() { ResponseMessage<List<WallpaperAttrEntity>>
	 * responseMessage = new ResponseMessage<>();
	 * responseMessage.setCode(SysConstant.SUCCESS_CODE);
	 * responseMessage.setMessage(SysConstant.SUCCESS_MSG);
	 * responseMessage.setResult(personalCenterServiceImpl.selectAllStyles());
	 * return responseMessage; }
	 */

	@RequestMapping(value = "/queryallstyles", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<ClassAttrValueEntity>> queryAllStyles() {
		ResponseMessage<List<ClassAttrValueEntity>> responseMessage = new ResponseMessage<>();
		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		responseMessage.setResult(personalCenterServiceImpl.selectStyles());
		return responseMessage;
	}

	@RequestMapping(value = "/queryallclasses", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<BuildClassEntity>> queryAllClasses() {
		ResponseMessage<List<BuildClassEntity>> responseMessage = new ResponseMessage<>();
		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		responseMessage.setResult(personalCenterServiceImpl.selectAllClasses());
		return responseMessage;
	}

	@RequestMapping(value = "/sendpartnermessage", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Boolean> sendpartnermessage(@RequestBody PartnerSendMessageModel pModel) {
		ResponseMessage<Boolean> responseMessage = new ResponseMessage<>();
		if (personalCenterServiceImpl.sendpartnermessage(pModel)) {
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		} else {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
		}
		return responseMessage;
	}
	
	@RequestMapping(value = "/get2DMarkToStream", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public void get2DMarkToStream(@RequestBody String[] url, HttpServletResponse response) {

		personalCenterServiceImpl.get2DMarkToStream(url[0],response);
		
	}
	
}
