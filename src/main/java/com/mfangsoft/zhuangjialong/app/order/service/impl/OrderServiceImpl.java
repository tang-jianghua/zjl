package com.mfangsoft.zhuangjialong.app.order.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.mfangsoft.zhuangjialong.app.applogin.mapper.CustomerAddressMapper;
import com.mfangsoft.zhuangjialong.app.applogin.model.CustomerAddress;
import com.mfangsoft.zhuangjialong.app.applogin.model.CustomerEntity;
import com.mfangsoft.zhuangjialong.app.cart.mapper.CartEntityMapper;
import com.mfangsoft.zhuangjialong.app.cart.model.CartEntity;
import com.mfangsoft.zhuangjialong.app.cart.model.CartProduct;
import com.mfangsoft.zhuangjialong.app.constructAppointment.mapper.ConstructAppointmentMapper;
import com.mfangsoft.zhuangjialong.app.constructAppointment.mapper.ConstructStateRelationMapper;
import com.mfangsoft.zhuangjialong.app.constructAppointment.model.ConstructAppointment;
import com.mfangsoft.zhuangjialong.app.constructAppointment.model.ConstructStateRelation;
import com.mfangsoft.zhuangjialong.app.constructAppointment.util.ConstructAppointmentEnum;
import com.mfangsoft.zhuangjialong.app.coupons.mapper.CustomerCouponsEntityMapper;
import com.mfangsoft.zhuangjialong.app.coupons.model.BrandCouponsModel;
import com.mfangsoft.zhuangjialong.app.evaluation.mapper.EvaluationEntityMapper;
import com.mfangsoft.zhuangjialong.app.evaluation.model.EvaluationEntity;
import com.mfangsoft.zhuangjialong.app.newproductcore.service.impl.ProductCoreServiceImpl;
import com.mfangsoft.zhuangjialong.app.order.mapper.OrderCashEntityMapper;
import com.mfangsoft.zhuangjialong.app.order.mapper.OrderCashNewEntityMapper;
import com.mfangsoft.zhuangjialong.app.order.mapper.OrderCouponsEntityMapper;
import com.mfangsoft.zhuangjialong.app.order.mapper.OrderEntityMapper;
import com.mfangsoft.zhuangjialong.app.order.mapper.OrderPayEntityMapper;
import com.mfangsoft.zhuangjialong.app.order.mapper.OrderProductEntityMapper;
import com.mfangsoft.zhuangjialong.app.order.mapper.OrderStateRelationEntityMapper;
import com.mfangsoft.zhuangjialong.app.order.mapper.SellerBalanceDetailEntityMapper;
import com.mfangsoft.zhuangjialong.app.order.model.Order;
import com.mfangsoft.zhuangjialong.app.order.model.OrderCashEntity;
import com.mfangsoft.zhuangjialong.app.order.model.OrderCashNewEntity;
import com.mfangsoft.zhuangjialong.app.order.model.OrderCouponsEntity;
import com.mfangsoft.zhuangjialong.app.order.model.OrderEntity;
import com.mfangsoft.zhuangjialong.app.order.model.OrderJsonModel;
import com.mfangsoft.zhuangjialong.app.order.model.OrderMessage;
import com.mfangsoft.zhuangjialong.app.order.model.OrderPay;
import com.mfangsoft.zhuangjialong.app.order.model.OrderPayEntity;
import com.mfangsoft.zhuangjialong.app.order.model.OrderProduct;
import com.mfangsoft.zhuangjialong.app.order.model.OrderProductEntity;
import com.mfangsoft.zhuangjialong.app.order.model.OrderStateRelationEntity;
import com.mfangsoft.zhuangjialong.app.order.model.OrderTracing;
import com.mfangsoft.zhuangjialong.app.order.model.SellerBalanceDetailEntity;
import com.mfangsoft.zhuangjialong.app.order.model.StateOrderDatile;
import com.mfangsoft.zhuangjialong.app.order.model.StateOrderNums;
import com.mfangsoft.zhuangjialong.app.order.pay_ali.config.AlipayConfig;
import com.mfangsoft.zhuangjialong.app.order.pay_ali.model.AliOrderRequest;
import com.mfangsoft.zhuangjialong.app.order.pay_ali.sign.RSA;
import com.mfangsoft.zhuangjialong.app.order.pay_ali.util.AlipayCore;
import com.mfangsoft.zhuangjialong.app.order.pay_ali.util.AlipayNotify;
import com.mfangsoft.zhuangjialong.app.order.pay_weixin.UnifiedOrderRequest;
import com.mfangsoft.zhuangjialong.app.order.pay_weixin.UnifiedOrderRespose;
import com.mfangsoft.zhuangjialong.app.order.pay_weixin.WPay;
import com.mfangsoft.zhuangjialong.app.order.pay_weixin.WeixinConst;
import com.mfangsoft.zhuangjialong.app.order.service.OrderCouponsService;
import com.mfangsoft.zhuangjialong.app.order.service.OrderService;
import com.mfangsoft.zhuangjialong.app.order.util.OrderManager;
import com.mfangsoft.zhuangjialong.app.order.util.OrderStatusEnum;
import com.mfangsoft.zhuangjialong.app.personalCenter.mapper.CustomerPointEntityMapper;
import com.mfangsoft.zhuangjialong.app.personalCenter.mapper.MessageEntityMapper;
import com.mfangsoft.zhuangjialong.app.personalCenter.mapper.PointEntityMapper;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.CustomerPointEntity;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.CustomerPointType;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.MessageEntity;
import com.mfangsoft.zhuangjialong.app.prepay.mapper.ShopPrepayEntityMapper;
import com.mfangsoft.zhuangjialong.app.prepay.model.ShopPrepayEntity;
import com.mfangsoft.zhuangjialong.app.product.model.Product;
import com.mfangsoft.zhuangjialong.app.promotion.model.AppPromotionTypeEnum;
import com.mfangsoft.zhuangjialong.app.salesnum.mapper.SalesNumEntityMapper;
import com.mfangsoft.zhuangjialong.app.salesnum.service.SalesNumService;
import com.mfangsoft.zhuangjialong.app.seller.mapper.SellerEntityMapper;
import com.mfangsoft.zhuangjialong.common.message.service.impl.SendMessageServiceImpl;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.DateUtils;
import com.mfangsoft.zhuangjialong.common.utils.HttpRequest;
import com.mfangsoft.zhuangjialong.common.utils.JPushUtil;
import com.mfangsoft.zhuangjialong.common.utils.MessageConstant;
import com.mfangsoft.zhuangjialong.common.utils.NumberUtil;
import com.mfangsoft.zhuangjialong.common.utils.PropUtis;
import com.mfangsoft.zhuangjialong.common.utils.RedisUtils;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.coupons.mapper.BrandCouponsEntityMapper;
import com.mfangsoft.zhuangjialong.integration.coupons.model.BrandCouponsEntity;
import com.mfangsoft.zhuangjialong.integration.newcoupons.mapper.BaseCustomerCouponsEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newcoupons.model.BaseBrandCouponsEntity;
import com.mfangsoft.zhuangjialong.integration.newcoupons.model.BaseCustomerCouponsEntity;
import com.mfangsoft.zhuangjialong.integration.newcoupons.model.CustomerCouponsModel;
import com.mfangsoft.zhuangjialong.integration.newproduct.mapper.BaseBrandProdSalesAttrEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newproduct.mapper.BrandNewProductEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BaseBrandProductEntity;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BrandProductOneSaleModel;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.PromotionEntityMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.PromotionZhekouEntityMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.UnionCustomerMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.UnionPromotionMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionEntityStepParam;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionSeckillProductEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionZhekouEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.model.UnionCustomer;
import com.mfangsoft.zhuangjialong.integration.promotion.model.UnionPromotion;
import com.mfangsoft.zhuangjialong.integration.promotion.model.UnionPromotionParam;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * @description：
 * 
 * @author：Liyanchen @date：2016年6月6日
 * 
 */
@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderEntityMapper orderEntityMapper;
	@Autowired
	OrderProductEntityMapper orderProductEntityMapper;
	@Autowired
	OrderStateRelationEntityMapper orderStateRelationEntityMapper;
	@Autowired
	OrderCouponsService orderCouponsService;
	@Autowired
	OrderPayEntityMapper orderPayEntityMapper;
	@Autowired
	PointEntityMapper pointEntityMapper;
	@Autowired
	MessageEntityMapper messageEntityMapper;
	@Autowired
	BrandCouponsEntityMapper brandCouponsEntityMapper;
	@Autowired
	CartEntityMapper cartEntityMapper;
	@Autowired
	OrderCashEntityMapper orderCashEntityMapper;
	@Autowired
	UnionCustomerMapper unionCustomerMapper;
	@Autowired
	EvaluationEntityMapper evaluationEntityMapper;
	@Autowired
	OrderCashNewEntityMapper orderCashNewEntityMapper;
	@Autowired
	ShopPrepayEntityMapper shopPrepayEntityMapper;
	@Autowired
	SellerBalanceDetailEntityMapper sellerBalanceDetailEntityMapper;
	@Autowired
	SellerEntityMapper sellerEntityMapper;
	@Autowired
	BrandNewProductEntityMapper brandNewProductEntityMapper;
	@Autowired
	com.mfangsoft.zhuangjialong.integration.promotion.mapper.PromotionSeckillProductEntityMapper promotionSeckillProductEntityMapper;
	@Autowired
	BaseBrandProdSalesAttrEntityMapper baseBrandProdSalesAttrEntityMapper;
	@Autowired
	private ConstructAppointmentMapper constructAppointmentMapper;
	@Autowired
	private ConstructStateRelationMapper constructStateRelationMapper;
	@Autowired
	private com.mfangsoft.zhuangjialong.app.applogin.mapper.CustomerEntityMapper customerEntityMapper;
	@Autowired
	OrderCouponsEntityMapper orderCouponsEntityMapper;
	@Autowired
	CustomerAddressMapper customerAddressMapper;
	@Autowired
	CustomerPointEntityMapper customerPointEntityMapper;
	@Autowired
	SendMessageServiceImpl sendMessageServiceImpl;
	@Autowired
	UnionPromotionMapper unionPromotionMapper;
	@Autowired
	PromotionEntityMapper promotionEntityMapper;
	@Autowired
	SalesNumEntityMapper salesNumEntityMapper;
	@Autowired
	BaseCustomerCouponsEntityMapper baseCustomerCouponsEntityMapper;
	@Autowired
	CustomerCouponsEntityMapper customerCouponsEntityMapper;
	@Autowired
	SalesNumService salesNumService;
	@Autowired
	PromotionZhekouEntityMapper promotionZhekouEntityMapper;
	@Autowired
	private BrandNewProductEntityMapper brandProductEntityMapper;

	public static NumberUtil ins = new NumberUtil();

	private StringBuffer getStr(Long[] promotion_id) {
		StringBuffer sb = new StringBuffer();
		if (promotion_id != null && promotion_id.length > 0) {
			for (int i = 0; i < promotion_id.length; i++) {
				sb.append(promotion_id[i]);
				if (i != promotion_id.length - 1) {
					sb.append(",");
				}
			}
		}
		return sb;
	}

	public String getOrderCode() {

		return NumberUtil.getIns().code();// 订单号
	}

	public ResponseMessage<List<OrderJsonModel>> addOrders(OrderPay orders,
			ResponseMessage<List<OrderJsonModel>> responseMessage) {

		System.out.println("===========================addOrders============================================================================");
		System.out.println(new Gson().toJson(orders));
		System.out.println("===========================addOrders============================================================================");
		
		Long customer_id = orders.getCustomer_id();
		Long customer_address_id = orders.getCustomer_address_id();
		Long customer_pay_id = orders.getCustomer_pay_id();
		String preOrderStr = orders.getPreOrderStr();
		List<OrderEntity> ordersList = orders.getOrders();
		List<OrderJsonModel> resultList = new ArrayList<OrderJsonModel>();

		for (int i = 0; i < ordersList.size(); i++) {
			// 预处理订单
			if (ordersList.get(i) == null)
				continue;
			OrderEntity thisOrder = ordersList.get(i);
			// String orderCode = NumberUtil.getIns().code();// 订单号

			String orderCode = HttpRequest.sendPost(SysConstant.orderurl, null);
			if (StringUtils.isEmpty(orderCode)) {
				responseMessage.setCode(SysConstant.FAILURE_CODE);
				responseMessage.setMessage(SysConstant.ORDER_ADD_FAIL);
				return responseMessage;
			}
			orderCode = orderCode.replaceAll("\"", "");
			thisOrder.setOrder_code(orderCode);
			// 每个订单赋值 用户id 用户地址 用户选择的支付方式
			thisOrder.setCustomer_id(customer_id);
			CustomerAddress customerAddress = customerAddressMapper.selectByPrimaryKey(customer_address_id);

			thisOrder.setAddress_details(customerAddress.getAddress_details());
			thisOrder.setReceiver_name(customerAddress.getReceiver_name());
			thisOrder.setPhone_num(customerAddress.getPhone_num());
			thisOrder.setRegion_code(customerAddress.getRegion_code());

			thisOrder.setCustomer_pay_id(customer_pay_id);
			thisOrder.setPreOrderStr(preOrderStr);

			verifyPromotionForOrderProdcuts(thisOrder);
			
			// 普通红包优惠券
			List<Long> idList = new ArrayList<>();
			if (thisOrder.getCoupons_id() != null)
				idList.add(thisOrder.getCoupons_id());
			if (thisOrder.getRedbag_id() != null && thisOrder.getRedbag_type() != null
					&& thisOrder.getRedbag_type() == 1)
				idList.add(thisOrder.getRedbag_id());
			if (thisOrder.getZhekou_id() != null)
				idList.add(thisOrder.getZhekou_id());
			if (idList.size() > 0) {
				List<BrandCouponsEntity> brandCouponsEntity = brandCouponsEntityMapper//
						.queryCouponsAboutUseState(true, idList);// 查询所有已使用的
				if (brandCouponsEntity != null && brandCouponsEntity.size() > 0) {
					throw new RuntimeException();
				}
				brandCouponsEntityMapper.updateUseStateByPrimaryKeyList(thisOrder.getShop_id(), true, true, idList,
						thisOrder.getOrder_code());
			}

			// 店铺定金
			if (thisOrder.getRedbag_type() != null && thisOrder.getRedbag_type() == 11
					&& thisOrder.getRedbag_id() != null) {
				ShopPrepayEntity entity = shopPrepayEntityMapper
						.selectByPrimaryKey(thisOrder.getRedbag_id().intValue());
				if (entity != null && entity.getState().equals(1)) {
					throw new RuntimeException();
				}

				ShopPrepayEntity record = new ShopPrepayEntity();
				record.setId(thisOrder.getRedbag_id().intValue());
				record.setState(1);
				record.setUse_time(new Date());
				record.setProduct_order_code(thisOrder.getOrder_code());
				shopPrepayEntityMapper.updateByPrimaryKeySelective(record);
			}

			if (thisOrder.getPromotion_id() != null && thisOrder.getPromotion_id().length > 0) {
				for (Long p : thisOrder.getPromotion_id()) {

					UnionCustomer u = unionCustomerMapper.selectBase(thisOrder.getCustomer_id(), p.intValue());
					if (u != null && u.getState().equals(1)) {
						throw new RuntimeException();
					}

					UnionCustomer unionCustomer = new UnionCustomer();
					unionCustomer.setCustomer_id(thisOrder.getCustomer_id());
					unionCustomer.setPromotion_id(p.intValue());
					unionCustomer.setState(1);
					unionCustomer.setUse_time(new Date());
					unionCustomer.setProduct_order_code(thisOrder.getOrder_code());
					unionCustomerMapper.updateByPromotion_Id(unionCustomer);

				}
			}

			// for (OrderProduct product : thisOrder.getProducts()) {
			// setInfo(product);
			// }
			//
			// // 验证订单
			// if (!OrderManager.validOrder(thisOrder)) {
			// responseMessage.setCode(SysConstant.FAILURE_CODE);
			// responseMessage.setMessage(SysConstant.ORDER_VERIFY_FAIL);
			// return responseMessage;
			// }

			long order_id = insertOrder(thisOrder);
			if (order_id <= 0) {
				responseMessage.setCode(SysConstant.FAILURE_CODE);
				responseMessage.setMessage("保存订单出错!");
				return responseMessage;
			}

			OrderCouponsEntity orderCouponsEntity = new OrderCouponsEntity();
			orderCouponsEntity.setOrder_id((long) order_id);
			orderCouponsEntity.setCoupons_id(thisOrder.getCoupons_id());
			orderCouponsEntity.setZhekou_id(thisOrder.getZhekou_id());

			if (thisOrder.getPromotion_id() != null && thisOrder.getPromotion_id().length > 0) {
				StringBuffer promotion_id = getStr(thisOrder.getPromotion_id());
				orderCouponsEntity.setPromotion_id(promotion_id.toString());
			}

			if (thisOrder.getRedbag_type() != null && thisOrder.getRedbag_id() != null) {
				if (thisOrder.getRedbag_type() == 11) {
					orderCouponsEntity.setPershop_id(thisOrder.getRedbag_id());// 定金
				} else {
					orderCouponsEntity.setRedbag_id(thisOrder.getRedbag_id());// 红包
				}
			}
			orderCouponsService.insertSelective(orderCouponsEntity);
			
			////////////////////// 保存销售属性
			Map<String, Object> product_info = null;

			if (thisOrder.getPreOrderStr() != null) {
				product_info = RedisUtils.getMap(thisOrder.getPreOrderStr() + "map");
			}
			System.out.println(product_info.size() + "thisOrder.getPreOrderStr()================================"
					+ thisOrder.getPreOrderStr());
			for (OrderProduct orderProduct : thisOrder.getProducts()) {
				OrderProductEntity orderProductEntity = new OrderProductEntity();
				orderProductEntity.setOrder_id(order_id);
				orderProductEntity.setProduct_id(orderProduct.getProduct_id());
				orderProductEntity.setProduct_num(orderProduct.getNum());
				orderProductEntity.setSales_property_id(orderProduct.getSales_properity().getId());
				orderProductEntity.setBuy_type(orderProduct.getBuy_type());
				orderProductEntity.setType_value(orderProduct.getType_value());
				if (product_info != null) {
					String info = getProductInfo(orderProduct.getProduct_id(), product_info);
					orderProductEntity.setInfo(info);
				}
				orderProductEntity.setPrice(orderProduct.getSales_properity().getPrice());
				orderProductEntity.setSale_price(orderProduct.getSales_properity().getNew_price());
				orderProductEntity.setCoupon_status(orderProduct.getcanUse());
				orderProductEntity.setPromotion_type(orderProduct.getPromotionType());
				orderProductEntity.setPromotion_id(orderProduct.getPromotion_id());

				insertSalesInfo(orderProductEntity);

				// 更新库存
				baseBrandProdSalesAttrEntityMapper.updateStockAfterOrder(orderProduct.getSales_properity().getId(),
						(long) orderProduct.getNum());

				salesNumService.updateSaleNum(orderProduct.getProduct_id(), (long) orderProduct.getNum());// 增加产品销量

				///////////////////// 更新秒杀产品表
				if (orderProduct.getBuy_type() == 1) {
					String[] type_value = orderProduct.getType_value().split("-");
					PromotionSeckillProductEntity promotionSeckillProductEntity = new PromotionSeckillProductEntity();
					promotionSeckillProductEntity.setPromotion_id(Long.parseLong(type_value[0]));
					promotionSeckillProductEntity.setSell_num(orderProduct.getNum());
					promotionSeckillProductEntity.setTime_id(Long.parseLong(type_value[1]));
					promotionSeckillProductEntity.setProduct_id(orderProduct.getProduct_id());
					promotionSeckillProductEntityMapper.updateSellNumByPrimaryId(promotionSeckillProductEntity);
				}

				///////////////////// 删除购物车
				CartEntity cartEntity = new CartEntity();
				cartEntity.setCustomer_id(thisOrder.getCustomer_id());
				cartEntity.setProduct_id(orderProduct.getProduct_id());
				cartEntity.setSales_property_id(orderProduct.getSales_properity().getId());
				cartEntityMapper.deleteByPrimaryKey(cartEntity);

			}
			////////////////////// 保存支付信息
			OrderPayEntity orderPayEntity = new OrderPayEntity();
			orderPayEntity.setOrder_id(order_id);
			orderPayEntity.setPay_state(OrderStatusEnum.UNPADY.value);
			orderPayEntity.setPay_time(new Date());
			orderPayEntityMapper.insertSelective(orderPayEntity);

			////////////////////// 保存0元支付信息
			if (thisOrder.getOrder_price().equals(0D)) {
				superZeroPriceOrder(order_id, orderCode, new Date());
			} else {
				////////////////////// 保存订单状态
				OrderStateRelationEntity orderStateRelationEntity = new OrderStateRelationEntity();
				orderStateRelationEntity.setOrder_id(order_id);
				orderStateRelationEntity.setOrder_state_code(OrderStatusEnum.NEEDPADY.value);
				orderStateRelationEntity.setTime(new Date());
				orderStateRelationEntityMapper.insertSelective(orderStateRelationEntity);
			}
			// 返回信息
			OrderJsonModel orderJsonModel = new OrderJsonModel(orderCode, thisOrder.getOrder_price());
			if (thisOrder.getOrder_price().equals(0D)) {
				orderJsonModel.setZeroPriceOrder(true);
			} else {
				orderJsonModel.setZeroPriceOrder(false);
			}
			resultList.add(orderJsonModel);
		}
		responseMessage.setCode("0");
		responseMessage.setMessage("订单提交成功");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
		responseMessage.setResult(resultList);
		return responseMessage;

	}

	/**
	 * 保存活动优惠券
	 * @param thisOrder
	 */
	private void saveKaquanInfo(OrderEntity thisOrder){

		

	}
	
	
	private void verifyPromotionForOrderProdcuts(OrderEntity thisOrder) {

		System.out.println("===========================verifyPromotionForOrderProdcuts============================================================================");
		System.out.println(new Gson().toJson(thisOrder));
		System.out.println("===========================verifyPromotionForOrderProdcuts============================================================================");
		
		Map<String, Object> product_info = RedisUtils.getMap(thisOrder.getPreOrderStr() + "map");
		if (product_info == null || product_info.size() <= 0) {
			product_info = new LinkedHashMap<String, Object>();
		}
		Double total_price = 0D;

		List<UnionPromotionParam> unionPromotion = null;
		if (thisOrder.getPromotion_id() != null && thisOrder.getPromotion_id().length > 0) {
			unionPromotion = unionCustomerMapper.selectListForCartByCustomerAndPromIdNew(thisOrder.getCustomer_id(), thisOrder.getPromotion_id());
		}
		
		for (OrderProduct product : thisOrder.getProducts()) {

			BaseBrandProductEntity p_entity = brandProductEntityMapper.selectByPrimaryKey(product.getProduct_id());
			if (product.getIsPercentPay() == null) {
				product.setIsPercentPay(p_entity.getSales_model().equals(2));
				product.setPercent(p_entity.getPercent());
			}
			
			if (product.getBuy_type() == 1) {// 判断秒杀
				String[] type_value = product.getType_value().split("-");
				synchronized (ins) {
					Integer count = promotionEntityMapper.selectPromotionProductAndPriceForOrder(
							Long.parseLong(type_value[0]), Long.parseLong(type_value[1]), product.getProduct_id());

					if (count == null || product.getNum() > count) {
						
						product.getSales_properity().setNew_price(product.getSales_properity().getPrice());
						product.setBuy_type(0);
						product.setType_value(null);

						product.setPromotionType(-1);
						product.setPromotion_id(null);
						product.setCanUseFlag(true, true, true, true, false);

						setInfoToMap(product.getProduct_id(), "原价", product_info, true);

						checkForZhekouTool(product, product_info, p_entity.getBrand_id());
					}
				}
			}

			if (product.getPromotionType() != null) {

				if (product.getPromotionType().equals(AppPromotionTypeEnum.unionZheKou.getTypeValue())) {

					UnionPromotion u = unionPromotionMapper.selectBaseInfoByPrimaryKey(product.getPromotion_id().intValue());
					
					if (u == null || !u.check()) {
						product.getSales_properity().setNew_price(product.getSales_properity().getPrice());
						product.setCanUseFlag(true, true, true, true, false);
						setInfoToMap(product.getProduct_id(), "原价", product_info, true);
						checkForZhekouTool(product, product_info, p_entity.getBrand_id());
					}
					
				} else if (product.getPromotionType().equals(AppPromotionTypeEnum.manEZheKou.getTypeValue())) {

					PromotionEntityStepParam entity = promotionEntityMapper.selectManPromotionByPrimaryId(product.getPromotion_id());
					
					if (entity == null
							|| !entity.condition(product.getNum(), product.getSales_properity().getPrice())) {

						product.getSales_properity().setNew_price(product.getSales_properity().getPrice());
						product.setCanUseFlag(true, true, true, true, false);
						setInfoToMap(product.getProduct_id(), "原价", product_info, true);
						checkForZhekouTool(product, product_info, p_entity.getBrand_id());

					}
				} else if (product.getPromotionType().equals(AppPromotionTypeEnum.manEJian.getTypeValue())) {

					PromotionEntityStepParam entity = promotionEntityMapper.selectManPromotionByPrimaryId(product.getPromotion_id());
					
					if (entity == null
							|| !entity.condition(product.getNum(), product.getSales_properity().getPrice())) {

						product.getSales_properity().setNew_price(product.getSales_properity().getPrice());
						product.setCanUseFlag(true, true, true, true, false);
						setInfoToMap(product.getProduct_id(), "原价", product_info, true);
						checkForZhekouTool(product, product_info, p_entity.getBrand_id());

					}
				} else if (product.getPromotionType().equals(AppPromotionTypeEnum.manEJian.getTypeValue())) {

					PromotionEntityStepParam entity = promotionEntityMapper.selectManPromotionByPrimaryId(product.getPromotion_id());
					
					if (entity == null
							|| !entity.condition(product.getNum(), product.getSales_properity().getPrice())) {

						product.getSales_properity().setNew_price(product.getSales_properity().getPrice());
						product.setCanUseFlag(true, true, true, true, false);
						setInfoToMap(product.getProduct_id(), "原价", product_info, true);
						checkForZhekouTool(product, product_info, p_entity.getBrand_id());

					}
				}
			}

			/////////////////////////////////////////////////////////////

			if (unionPromotion != null && unionPromotion.size() > 0) {
					for (UnionPromotionParam up : unionPromotion) {
						if (up.getUsedFlag())
							continue;
						for (Product p : up.getProduct()) {
							if (p.getProduct_id().equals(product.getProduct_id())) {
								up.setUsedFlag(true);
								Double new_price = product.getSales_properity().getNew_price() - up.getCash_value()/product.getNum();
								product.getSales_properity().setNew_price(NumberUtil.round(new_price > 0 ? new_price : 0, 2, BigDecimal.ROUND_HALF_DOWN));
								break;
							}
						}
					}
			}

			if (thisOrder.getZhekou_id() != null && product.isCanUseZhekouquan()) {
//				CustomerCouponsModel e = brandCouponsEntityMapper.selectInfoById(thisOrder.getZhekou_id());
				BaseCustomerCouponsEntity base1 = baseCustomerCouponsEntityMapper.selectByPrimaryKey(thisOrder.getZhekou_id());
				BaseBrandCouponsEntity base2 = brandCouponsEntityMapper.selectByPrimaryKey(base1.getCoupons_id());
				
				Double p = product.getSales_properity().getNew_price() * base2.getValue();
				p = NumberUtil.round(p, 2, BigDecimal.ROUND_HALF_DOWN);
				product.getSales_properity().setNew_price(p);

			}

			product.setTotal_price(product.getSales_properity().getNew_price() * product.getNum());
			total_price += product.getTotal_price();

		}

		if (thisOrder.getIsdeposit()) {

			// 选中定金支付
			Double per_price = 0D;
			Double left_price = 0D;

			for (OrderProduct product : thisOrder.getProducts()) {
				if (product.getIsPercentPay()) {
					double percent_price = product.getTotal_price() * (product.getPercent() / 100);
					percent_price = NumberUtil.round(percent_price < 0 ? 0 : percent_price, 2,
							BigDecimal.ROUND_HALF_DOWN);
					per_price += percent_price;
					
					setInfoToMap(product.getProduct_id(),
							"定金比例" + product.getPercent().intValue() + "%", product_info, false);
				} else {
					per_price += product.getTotal_price();
				}
			}

			left_price = total_price - per_price;

			if (thisOrder.getCoupons_id() != null) {

				BaseCustomerCouponsEntity base1 = baseCustomerCouponsEntityMapper.selectByPrimaryKey(thisOrder.getCoupons_id());
				BaseBrandCouponsEntity base2 = brandCouponsEntityMapper.selectByPrimaryKey(base1.getCoupons_id());
				
				left_price -= base2.getValue();

			}

			if (thisOrder.getRedbag_id() != null) {

				if (thisOrder.getRedbag_type() == 1) {
					
					BaseCustomerCouponsEntity base1 = baseCustomerCouponsEntityMapper.selectByPrimaryKey(thisOrder.getRedbag_id());
					BaseBrandCouponsEntity base2 = brandCouponsEntityMapper.selectByPrimaryKey(base1.getCoupons_id());
					
					left_price -= base2.getValue();

				} else if (thisOrder.getRedbag_type() == 11) {
					ShopPrepayEntity e = shopPrepayEntityMapper.selectByPrimaryKey(thisOrder.getRedbag_id().intValue());

					left_price -= e.getValue();
				}
			}
			thisOrder.setOrder_price(NumberUtil.round(per_price < 0 ? 0 : per_price, 2, BigDecimal.ROUND_HALF_DOWN));
			thisOrder
					.setDeposit_price(NumberUtil.round(left_price < 0 ? 0 : left_price, 2, BigDecimal.ROUND_HALF_DOWN));

		} else {

			if (thisOrder.getCoupons_id() != null) {
				
				BaseCustomerCouponsEntity base1 = baseCustomerCouponsEntityMapper.selectByPrimaryKey(thisOrder.getCoupons_id());
				BaseBrandCouponsEntity base2 = brandCouponsEntityMapper.selectByPrimaryKey(base1.getCoupons_id());
				
				total_price -= base2.getValue();
			}

			if (thisOrder.getRedbag_id() != null) {

				if (thisOrder.getRedbag_type() == 1) {
					
					BaseCustomerCouponsEntity base1 = baseCustomerCouponsEntityMapper.selectByPrimaryKey(thisOrder.getRedbag_id());
					BaseBrandCouponsEntity base2 = brandCouponsEntityMapper.selectByPrimaryKey(base1.getCoupons_id());
					
//					CustomerCouponsModel e = brandCouponsEntityMapper.selectInfoById(thisOrder.getRedbag_id());
						total_price -= base2.getValue();

				} else if (thisOrder.getRedbag_type() == 11) {
					ShopPrepayEntity e = shopPrepayEntityMapper.selectByPrimaryKey(thisOrder.getRedbag_id().intValue());

					total_price -= e.getValue();
				}
			}
			thisOrder
					.setOrder_price(NumberUtil.round(total_price < 0 ? 0 : total_price, 2, BigDecimal.ROUND_HALF_DOWN));
			thisOrder.setDeposit_price(0D);
		}
		
		RedisUtils.setMap(thisOrder.getPreOrderStr() + "map", product_info);
	}

	/**
	 * 检查折扣工具
	 * 
	 * @param product
	 * @param product_info
	 */
	private void checkForZhekouTool(OrderProduct product, Map<String, Object> product_info, Long brand_id) {

		PromotionZhekouEntity zhekou = promotionZhekouEntityMapper.selectZhekouToolForCartOneProduct(brand_id,
				product.getProduct_id());
		if (zhekou != null) {
			// product.setPromotionType(AppPromotionTypeEnum.zheKouTool.getTypeValue());
			// product.setPromotion_id(zhekou.getId());
			// product.setCanUseFlag(true, true, true, true, false);

			Double price_modle = zhekou.getDiscount() * product.getSales_properity().getPrice();
			price_modle = NumberUtil.round(price_modle, 2, BigDecimal.ROUND_HALF_DOWN);
			product.getSales_properity().setNew_price(price_modle);

			setInfoToMap(product.getProduct_id(), "折扣工具" + zhekou.getDiscount() * 100 + "%", product_info, false);
		}

	}

	/**
	 * 保存产品信息
	 * 
	 * @param product_id
	 *            键
	 * @param value
	 *            值
	 * @param map
	 * @param iscover
	 *            是否覆盖
	 * @return
	 */
	private Map<String, Object> setInfoToMap(Long product_id, String value, Map<String, Object> map, boolean iscover) {

		if (!iscover && map.get(product_id) != null) {
			map.put(product_id.toString(), map.get(product_id) + "-" + value);
		} else {
			map.put(product_id.toString(), value);
		}
		return map;
	}

	private String getProductInfo(Long product_id, Map<String, Object> map) {

		for (Map.Entry<String, Object> entity : map.entrySet()) {
			if (entity.getKey().equals(product_id.toString())) {
				return entity.getValue().toString();
			}
		}
		return null;
	}

	@Override
	public long insertOrder(final OrderEntity order) {

//		OrderEntity orderEntity = new OrderEntity();
//
//		orderEntity.setOrder_code(order.getOrder_code());
//		orderEntity.setCustomer_id(order.getCustomer_id());
//		orderEntity.setShop_id(order.getShop_id());
//		orderEntity.setAddress_details(order.getAddress_details());
//		orderEntity.setReceiver_name(order.getReceiver_name());
//		orderEntity.setPhone_num(order.getPhone_num());
//		orderEntity.setRegion_code(order.getRegion_code());
//		orderEntity.setMemo(order.getMemo());

		Order orderResult = orderEntityMapper.selectPreInfoForOrder(order.getShop_id(), order.getCustomer_id());
		order.setBrand_id(orderResult.getBrand_id());
		order.setCitypartner_id(orderResult.getCitypartner_id());
		order.setExshopper_id(orderResult.getExshopper_id());
		order.setCreate_time(new Date());
		
//		orderEntity.setBrand_id(orderResult.getBrand_id());
//		orderEntity.setCitypartner_id(orderResult.getCitypartner_id());
//		orderEntity.setExshopper_id(orderResult.getExshopper_id());
//		orderEntity.setCreate_time(new Date());

//		orderEntity.setIsdeposit(order.getIsdeposit());// 现付款
//		orderEntity.setOrder_price(order.getOrder_price());// 现付款
//		orderEntity.setDeposit_price(order.getDeposit_price() == null ? 0 : order.getDeposit_price());// 尾款

		orderEntityMapper.insertSelective(order);
		return order.getId();
	}

	private void superZeroPriceOrder(Long order_id, String orderCode, Date time_date) {
		try {
			OrderPayEntity orderPayEntity = new OrderPayEntity();
			orderPayEntity.setOrder_id(order_id);
			orderPayEntity.setPay_state(OrderStatusEnum.PADY.value);
			orderPayEntity.setPay_time(time_date);
			orderPayEntity.setTrade_data("100000000001");
			orderPayEntity.setCustomer_pay_id(OrderStatusEnum.PLATFORM.value);
			orderPayEntityMapper.updateByOrderIdSelective(orderPayEntity);// 更新订单支付参数

			// 记录订单状态
			OrderStateRelationEntity record = new OrderStateRelationEntity();
			record.setOrder_code(orderCode);
			record.setOrder_state_code(OrderStatusEnum.VISTEANDMETER.value);
			record.setTime(time_date);
			insertOrderState(record);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public long insertSalesInfo(final OrderProductEntity entity) {
		return orderProductEntityMapper.insertSelective(entity);
	}

	public int clearOrder(final String orderCode) {
		orderEntityMapper.clearOrderByOrderCode(orderCode);
		return 1;
	}

	public OrderEntity selectOrderSimpleInfoByCode(String code) {
		return orderEntityMapper.selectOrderSimpleInfoByCode(code);
	}

	public List<OrderEntity> selectOrdersByState(Page<OrderEntity> page) {
		return orderEntityMapper.selectOrdersByState(page);
	}

	public long insertOrderState(OrderStateRelationEntity record) {
		System.out.println("-----记录订单状态----------------------------------- " + record.getOrder_state_code());
		long result = orderStateRelationEntityMapper.insertWithCode(record);
		if (OrderStatusEnum.NEEDRECEIVE.value == record.getOrder_state_code()) {// 已发货

			OrderMessage orderMessage = orderEntityMapper.selectordersInfoForMessageByCode(record.getOrder_code());
			if (orderMessage != null) {
				JPushUtil.sendMessage(orderMessage.getPlatform(), orderMessage.getPushstr(),
						MessageConstant.orderSentTitle, MessageFormat.format(MessageConstant.orderSentContent,
								orderMessage.getProduct_name(), orderMessage.getOrder_code()));

				MessageEntity messageEntity = new MessageEntity();
				String productName = " ";
				if (orderMessage.getProduct_name() != null) {
					productName = orderMessage.getProduct_name().length() > 10
							? orderMessage.getProduct_name().substring(0, 10) + "..." : orderMessage.getProduct_name();
				}
				messageEntity.setCustomer_id(orderMessage.getCustomer_id());
				messageEntity.setParams(productName + "," + orderMessage.getOrder_code());
				messageEntity.setType_id(MessageConstant.orderSent);
				messageEntity.setTime(new Date());
				messageEntity.setImgurl(orderMessage.getImgurl());
				messageEntityMapper.insertSelective(messageEntity);
			}

		} else if (OrderStatusEnum.NEEDCOMMENT.value == record.getOrder_state_code()) {// 已签收

			OrderMessage orderMessage = orderEntityMapper.selectordersInfoForMessageByCode(record.getOrder_code());
			if (orderMessage != null) {
				JPushUtil.sendMessage(orderMessage.getPlatform(), orderMessage.getPushstr(),
						MessageConstant.orderReviceTitle, MessageFormat.format(MessageConstant.orderReviceContent,
								orderMessage.getProduct_name(), orderMessage.getOrder_code()));

				MessageEntity messageEntity = new MessageEntity();
				String productName = " ";
				if (orderMessage.getProduct_name() != null) {
					productName = orderMessage.getProduct_name().length() > 10
							? orderMessage.getProduct_name().substring(0, 10) + "..." : orderMessage.getProduct_name();
				}
				messageEntity.setCustomer_id(orderMessage.getCustomer_id());
				messageEntity.setParams(productName + "," + orderMessage.getOrder_code());
				messageEntity.setType_id(MessageConstant.orderRevice);
				messageEntity.setTime(new Date());
				messageEntity.setImgurl(orderMessage.getImgurl());
				messageEntityMapper.insertSelective(messageEntity);
			}
		} else if (OrderStatusEnum.CANCEl.value == record.getOrder_state_code()) {// 取消
			List<OrderProductEntity> order_products = orderProductEntityMapper
					.selectByOrderCode(record.getOrder_code());
			if (order_products != null) {
				for (OrderProductEntity p : order_products) {
					if (p.getBuy_type() == 1) {

						String[] str = p.getType_value().split("-");
						PromotionSeckillProductEntity entity = new PromotionSeckillProductEntity();
						entity.setSell_num(p.getProduct_num());
						entity.setPromotion_id(Long.parseLong(str[0]));
						entity.setTime_id(Long.parseLong(str[1]));
						entity.setProduct_id(p.getProduct_id());
						promotionSeckillProductEntityMapper.updateSellNumByPrimaryIdForCancelOrder(entity);// 更新秒杀库存

						System.out.println("---------还原秒杀库存--------------" + record.getOrder_code() + "|"
								+ p.getProduct_id() + "|" + p.getProduct_num() + "|" + Long.parseLong(str[0]));
					}
					updateAddStockCancleOrder(p.getSales_property_id(), p.getProduct_num());// 更新产品库存

					salesNumService.updateSaleNum(p.getProduct_id(), -(long) p.getProduct_num());// 减少产品销量

					System.out.println("---------还原库存--------------" + record.getOrder_code() + "|" + p.getProduct_id()
							+ "|" + p.getProduct_num() + "|");

				}
			}
			reBackCards(record.getOrder_code());// 还原各种券

		}
		return result;
	}

	public void reBackCards(String order_code) {
		// 还原优惠券红包
		List<Long> idList = new ArrayList<>();
		OrderCouponsEntity coupons = orderCouponsEntityMapper.selectByOrderId(order_code);
		if (coupons.getCoupons_id() != null)
			idList.add(coupons.getCoupons_id());
		if (coupons.getRedbag_id() != null)
			idList.add(coupons.getRedbag_id());
		if (coupons.getZhekou_id() != null)
			idList.add(coupons.getZhekou_id());
		if (idList.size() > 0) {
			brandCouponsEntityMapper.updateUseStaterebackbyList(idList);
		}
		String promotion_ids = coupons.getPromotion_id();

		if (StringUtils.isNotEmpty(promotion_ids)) {

			List<String> list_1 = Arrays.asList(promotion_ids);

			for (String p : list_1) {
				unionCustomerMapper.updateUnuseStateByPromotionId(coupons.getCustomer_id(), Integer.parseInt(p));
			}
		}

		if (coupons.getPershop_id() != null) {

			shopPrepayEntityMapper.updateUnuseStateByPrimaryKey(coupons.getPershop_id().intValue());
		}
	}

	public StateOrderNums customerorderstatenum(OrderEntity param) {

		StateOrderNums stateOrderNums = new StateOrderNums();
		List<StateOrderDatile> list = orderEntityMapper.customerorderstatenum(param.getCustomer_id());

		for (StateOrderDatile entity : list) {
			switch (entity.getState()) {
			case 1000:
				stateOrderNums.setNeedpayNum(entity.getNum());
				break;
			case 1001:
			case 7000:
				stateOrderNums.setNeedDeliveryNum(entity.getNum());
				break;
			case 2000:
				stateOrderNums.setNeedReciveNum(entity.getNum());
				break;
			case 2001:
				stateOrderNums.setNeedCommentNum(entity.getNum());
				break;
			case 3000:
				stateOrderNums.setSuccessNum(entity.getNum());
				break;
			case 4000:
				stateOrderNums.setCancleNum(entity.getNum());
				break;
			}
		}

		stateOrderNums.setPointNum(customerPointEntityMapper.selectTotalPointByCustomerId(param.getCustomer_id()));

		return stateOrderNums;
	}

	public int updateOrderState(String code, int order_state_code) {

		return orderEntityMapper.updateOrderState(code, order_state_code);
	}

	public int selectOrderState(String code) {
		return orderEntityMapper.selectOrderState(code);
	}

	public OrderEntity selectOneOrderByCode(String code) {

		return orderEntityMapper.selectOneOrderByCode(code);
	}

	public void updateGroupIdByOrderCode(Map<String, Object> map) {
		orderPayEntityMapper.updateGroupIdByOrderCode(map);
	}

	public int selectPayStateByCodes(List<String> list) {
		return orderPayEntityMapper.selectPayStateByCodes(list);
	}

	public List<OrderEntity> selectOrdersByGroupCode(String groupCodeId) {

		return orderEntityMapper.selectOrdersByGroupCode(groupCodeId);
	}

	public void updateByGroupIdSelective(OrderPayEntity orderPayEntity) {
		orderPayEntityMapper.updateByGroupIdSelective(orderPayEntity);
	}

	public List<OrderPayEntity> selectPayStateByGroupId(String grouppay_id) {
		return orderPayEntityMapper.selectPayStateByGroupId(grouppay_id);
	}

	public int selectOrdersCountsByState(long customer_id, List<String> state) {
		return orderEntityMapper.selectOrdersCountsByState(customer_id, state);
	}

	@Override
	public Page<OrderEntity> customerOrderList(OrderPay orderPay) {
		int page_index = orderPay.getPage() < 0 ? 1 : orderPay.getPage();
		int page_size = orderPay.getPageSize() <= 0 ? 5 : orderPay.getPageSize();

		Page<OrderEntity> page = new Page<OrderEntity>();
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("customer_id", orderPay.getCustomer_id());
		paramsMap.put("stateList", orderPay.getState());
		page.setPage((page_index - 1) * page_size);
		page.setPageSize(page_size);
		page.setParam(paramsMap);

		Page<OrderEntity> resultPage = new Page<OrderEntity>();
		List<OrderEntity> list = selectOrdersByState(page);
		if (list.isEmpty()) {
			resultPage.setTotal(selectOrdersCountsByState(orderPay.getCustomer_id(), orderPay.getState()));
			resultPage.setPage(page_index);
			resultPage.setPageSize(page_size);
			return resultPage;
		}
		for (OrderEntity e : list) {
			for (OrderProduct product : e.getProducts()) {
				setInfo(product);
			}
		}
		resultPage.setTotal(selectOrdersCountsByState(orderPay.getCustomer_id(), orderPay.getState()));
		resultPage.setPage(page_index);
		resultPage.setPageSize(page_size);
		resultPage.setData(list);

		return resultPage;
	}

	private OrderProduct setInfo(OrderProduct product) {

		BrandProductOneSaleModel productInfo = brandNewProductEntityMapper
				.selectProductOneSaleInfo(product.getProduct_id(), product.getSales_properity().getId());
		if (product.getSales_properity().getPrice() == null) {
			product.getSales_properity().setPrice(productInfo.getSalesAttrEntity().getPrice());
		}
		product.getSales_properity().setStock(productInfo.getSalesAttrEntity().getStock());

		StringBuffer sb = new StringBuffer();
		if (productInfo.getSalesAttrEntity().getColor() != null) {
			sb.append("颜色:" + productInfo.getSalesAttrEntity().getColor());
		}
		if (productInfo.getSalesAttrEntity().getModel() != null) {
			sb.append(" 型号:" + productInfo.getSalesAttrEntity().getModel());
		}
		if (productInfo.getSalesAttrEntity().getStandard() != null) {
			sb.append(" 规格:" + productInfo.getSalesAttrEntity().getStandard());
		}
		if (product.getUnit() != null) {
			sb.append(" 单价:" + product.getUnit());
		}
		product.setInfo(sb.toString());
		return product;
	}

	@Override
	public OrderEntity customerOrderDetails(OrderEntity param) {

		OrderEntity orderEntity = selectOneOrderByCode(param.getOrder_code());

		if (orderEntity == null) {
			return null;
		}
		List<OrderProduct> listP = orderEntity.getProducts();
		for (OrderProduct product : listP) {

			setInfo(product);

		}
		List<BrandCouponsEntity> list = orderEntity.getCouponsinfo();
		for (BrandCouponsEntity b : list) {
			if (b.getType().equals(1)) {
				if (orderEntity.getRedbagList() == null) {
					orderEntity.setRedbagList(new ArrayList<BrandCouponsEntity>());
				}
				orderEntity.getRedbagList().add(b);
			} else if (b.getType().equals(2)){
				if (orderEntity.getCouponsList() == null) {
					orderEntity.setCouponsList(new ArrayList<BrandCouponsEntity>());
				}
				orderEntity.getCouponsList().add(b);
			}else if (b.getType().equals(3)){
				if (orderEntity.getZhekouList() == null) {
					orderEntity.setZhekouList(new ArrayList<BrandCouponsEntity>());
				}
				orderEntity.getZhekouList().add(b);
			}
		}
		if(orderEntity.getShopPrepayEntity() != null){
			if (orderEntity.getRedbagList() == null) {
				orderEntity.setRedbagList(new ArrayList<BrandCouponsEntity>());
			}
			BrandCouponsEntity b = new BrandCouponsEntity();
			b.setId(orderEntity.getShopPrepayEntity().getId().longValue());
			b.setValue(orderEntity.getShopPrepayEntity().getValue().doubleValue());
			b.setType(11);
			b.setName("店铺定金");
			orderEntity.getRedbagList().add(b);
		}
		orderEntity.setCouponsinfo(null);
		return orderEntity;
	}

	public List<OrderProduct> customerorderproductsdetails(OrderEntity param) {

		OrderEntity orderEntity = selectOneOrderByCode(param.getOrder_code());
		List<OrderProduct> listOrderProduct = new ArrayList<OrderProduct>();
		if (orderEntity == null) {
			return null;
		}
		List<EvaluationEntity> listEvaluation = evaluationEntityMapper
				.selectEvaluationListByOrderCode(param.getOrder_code());

		List<OrderProduct> listP = orderEntity.getProducts();
		for (OrderProduct product : listP) {
			product.setIsPingjia(false);
			if (listEvaluation != null && listEvaluation.size() > 0) {
				for (EvaluationEntity e : listEvaluation) {
					if (e.getProduct_id().equals(product.getProduct_id())
							&& e.getSales_property_id().equals(product.getSales_properity().getId())) {
						product.setIsPingjia(true);
					}
				}
			}

			setInfo(product);
			listOrderProduct.add(product);
		}
		return listOrderProduct;
	}

	public UnifiedOrderRequest customerPayOrder(UnifiedOrderRequest unifiedOrderRequest)
			throws UnsupportedEncodingException {

		String groupOrderId = OrderManager.makeUUID();// 随机数作为分组支付id grouppay_id
		List<String> orderCodeList = unifiedOrderRequest.getOrders();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("grouppay_id", groupOrderId);
		map.put("listOrdersId", orderCodeList);
		updateGroupIdByOrderCode(map);// 存储总订单号

		Double price_total = 0D;
		List<OrderEntity> orderList = orderEntityMapper.selectOrdersByCodeList(orderCodeList);
		for (OrderEntity o : orderList) {
			// if (o.getIsdeposit()) {
			// price_total += o.getDeposit_price();
			// } else {
			price_total += o.getOrder_price();
			// }
		}

		if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
			price_total = 0.01D;
		}

		String prepay_id = WPay
				.httpOrder(WPay.createOrderInfo(unifiedOrderRequest, groupOrderId, ((int) (price_total * 100)) + ""));

		unifiedOrderRequest.setPrepay_id(prepay_id);
		unifiedOrderRequest.setTimestamp(StringUtils.substring(System.currentTimeMillis() + "", 0, 10));
		unifiedOrderRequest.setAttach("test1");

		return unifiedOrderRequest;
	}

	public UnifiedOrderRequest customerpayorderfordeposit(UnifiedOrderRequest unifiedOrderRequest)
			throws UnsupportedEncodingException {

		List<String> orderCodeList = unifiedOrderRequest.getOrders();
		if (orderCodeList.size() != 1)
			return unifiedOrderRequest;// 只接受一个订单号
		Double price_total = 0D;
		List<OrderEntity> orderList = orderEntityMapper.selectOrdersByCodeList(orderCodeList);// 一个订单
		for (OrderEntity o : orderList) {
			if (o.getIsdeposit()) {
				price_total += o.getDeposit_price();
			} else {
				return unifiedOrderRequest;
			}
		}

		if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
			price_total = 0.01D;
		}

		String prepay_id = WPay.httpOrder(WPay.createOrderInfoForDeposit(unifiedOrderRequest, orderCodeList.get(0),
				((int) (price_total * 100)) + ""));

		unifiedOrderRequest.setPrepay_id(prepay_id);
		unifiedOrderRequest.setTimestamp(StringUtils.substring(System.currentTimeMillis() + "", 0, 10));
		return unifiedOrderRequest;
	}

	public UnifiedOrderRequest customerpayorderforcash(OrderCashEntity orderCashEntity)
			throws UnsupportedEncodingException {

		UnifiedOrderRequest unifiedOrderRequest = new UnifiedOrderRequest();

		String orderCode = HttpRequest.sendPost(SysConstant.orderurl, null);// 订单号
		if (StringUtils.isEmpty(orderCode))
			throw new RuntimeException();// 没有请求到订单号，返回
		orderCode = orderCode.replaceAll("\"", "");
		orderCashEntity.setCode(orderCode);
		if (insertCashOrder(orderCashEntity) <= 0) {
			return unifiedOrderRequest;
		}

		if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
			orderCashEntity.setOrder_price(0.01D);
		}

		String prepay_id = WPay.httpOrder(WPay.createOrderInfoForCash(unifiedOrderRequest, orderCode,
				((int) (orderCashEntity.getOrder_price() * 100)) + ""));

		unifiedOrderRequest.setPrepay_id(prepay_id);
		unifiedOrderRequest.setTimestamp(StringUtils.substring(System.currentTimeMillis() + "", 0, 10));
		return unifiedOrderRequest;

	}

	public UnifiedOrderRequest customerpayorderforPreShopcartPay(OrderCashNewEntity orderCashNewEntity)
			throws UnsupportedEncodingException {

		UnifiedOrderRequest unifiedOrderRequest = new UnifiedOrderRequest();

		String orderCode = HttpRequest.sendPost(SysConstant.orderurl, null);// 订单号
		if (StringUtils.isEmpty(orderCode))
			throw new RuntimeException();// 没有请求到订单号，返回
		orderCode = orderCode.replaceAll("\"", "");

		orderCashNewEntity.setCode(orderCode);
		orderCashNewEntity = insertCashOrderNew(orderCashNewEntity);

		if (orderCashNewEntity == null) {// 保存初始订单信息
			return unifiedOrderRequest;
		}

		if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
			orderCashNewEntity.setOrder_price(0.01D);
		}

		String prepay_id = WPay.httpOrder(WPay.createOrderInfoForCashNew(unifiedOrderRequest, orderCode,
				((int) (orderCashNewEntity.getOrder_price() * 100)) + ""));

		unifiedOrderRequest.setPrepay_id(prepay_id);
		unifiedOrderRequest.setTimestamp(StringUtils.substring(System.currentTimeMillis() + "", 0, 10));
		return unifiedOrderRequest;

	}

	public UnifiedOrderRequest customerpayorderforconstruct(ConstructAppointment param)
			throws UnsupportedEncodingException {

		UnifiedOrderRequest unifiedOrderRequest = new UnifiedOrderRequest();
		String orderCode = HttpRequest.sendPost(SysConstant.orderurl, null);// 订单号
		if (StringUtils.isEmpty(orderCode))
			throw new RuntimeException();// 没有请求到订单号，返回
		orderCode = orderCode.replaceAll("\"", "");

		ConstructAppointment appointment = constructAppointmentMapper.selectByPrimaryKey(param.getId());
		CustomerEntity customerEntity = customerEntityMapper.selectByPrimaryKey(appointment.getCustomer_id());
		OrderCashNewEntity orderCashNewEntity = new OrderCashNewEntity();
		orderCashNewEntity.setCode(orderCode);
		orderCashNewEntity.setCustomer_id(appointment.getConstruct_id());
		orderCashNewEntity.setExshopper_id(customerEntity.getExshopper_id());
		orderCashNewEntity.setOrder_price(appointment.getConstruct_data());
		orderCashNewEntity.setCreate_time(new Date());
		orderCashNewEntity.setPay_state(OrderStatusEnum.UNPADY.value);
		orderCashNewEntity.setType(OrderStatusEnum.CONSTRUCT.value);
		orderCashNewEntity.setType_id(param.getId());
		int result = orderCashNewEntityMapper.insertSelective(orderCashNewEntity);

		if (result <= 0) {
			return unifiedOrderRequest;
		}

		if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
			orderCashNewEntity.setOrder_price(0.01D);
		}

		String prepay_id = WPay.httpOrder(WPay.createOrderInfoForConstrat(unifiedOrderRequest, orderCode,
				((int) (orderCashNewEntity.getOrder_price() * 100)) + ""));

		unifiedOrderRequest.setPrepay_id(prepay_id);
		unifiedOrderRequest.setTimestamp(StringUtils.substring(System.currentTimeMillis() + "", 0, 10));
		return unifiedOrderRequest;

	}

	public synchronized UnifiedOrderRequest customerpayorderforCoupons(BrandCouponsModel param)
			throws UnsupportedEncodingException {

		UnifiedOrderRequest unifiedOrderRequest = new UnifiedOrderRequest();
		String orderCode = HttpRequest.sendPost(SysConstant.orderurl, null);// 订单号
		if (StringUtils.isEmpty(orderCode))
			throw new RuntimeException();// 没有请求到订单号，返回
		orderCode = orderCode.replaceAll("\"", "");

		// if(customerCouponsEntityMapper.selectCustomerWhetherGainCoupons(param)
		// >0){
		// return unifiedOrderRequest;
		// }
		BaseBrandCouponsEntity coupons = brandCouponsEntityMapper.selectByPrimaryKey(param.getCoupons_id());

		if (coupons == null) {
			return unifiedOrderRequest;
		}

		if (coupons.getSurplus_count() < 1) {
			return unifiedOrderRequest;
		}

		CustomerEntity customerEntity = customerEntityMapper.selectByPrimaryKey(param.getCustomer_id());

		if (customerEntity == null) {
			return unifiedOrderRequest;
		}

		OrderCashNewEntity orderCashNewEntity = new OrderCashNewEntity();
		orderCashNewEntity.setCode(orderCode);
		orderCashNewEntity.setCustomer_id(param.getCustomer_id());
		orderCashNewEntity.setExshopper_id(customerEntity.getExshopper_id());
		orderCashNewEntity.setOrder_price(param.getCoupons_price());
		orderCashNewEntity.setCreate_time(new Date());
		orderCashNewEntity.setPay_state(OrderStatusEnum.UNPADY.value);
		orderCashNewEntity.setType(OrderStatusEnum.CONPONS.value);
		orderCashNewEntity.setType_id(param.getCoupons_id());

		int result = orderCashNewEntityMapper.insertSelective(orderCashNewEntity);

		if (result <= 0) {
			return unifiedOrderRequest;
		}

		if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
			orderCashNewEntity.setOrder_price(0.01D);
		}

		String prepay_id = WPay.httpOrder(WPay.createOrderInfoForCoupons(unifiedOrderRequest, orderCode,
				((int) (orderCashNewEntity.getOrder_price() * 100)) + ""));

		unifiedOrderRequest.setPrepay_id(prepay_id);
		unifiedOrderRequest.setTimestamp(StringUtils.substring(System.currentTimeMillis() + "", 0, 10));
		return unifiedOrderRequest;

	}

	@Override
	public List<OrderTracing> selectOrderTraceInfoByCode(String order_code) {
		return orderEntityMapper.selectOrderTraceInfoByCode(order_code);
	}

	public List<OrderMessage> selectPreExpireorders() {
		return orderEntityMapper.selectPreExpireorders();
	}

	public List<OrderMessage> selectSecKIllExpireorders() {
		return orderEntityMapper.selectSecKIllExpireorders();
	}

	public String selectAnyOneImgForOrderProduct(String code) {
		return orderEntityMapper.selectAnyOneImgForOrderProduct(code);
	}

	public List<OrderProductEntity> selectProductListByOrderIds(String orderIds) {
		return orderProductEntityMapper.selectProductListByOrderIds(orderIds);
	}

	public int updateAddStockCancleOrder(Long id, Integer stock) {
		return baseBrandProdSalesAttrEntityMapper.updateAddStockCancleOrder(id, stock);
	}

	@Override
	public String cancelExpireorders() {
		return orderEntityMapper.cancelExpireorders();
	}

	public List<OrderMessage> selectCanceledordersForMessage(String orderIds) {
		return orderEntityMapper.selectCanceledordersForMessage(orderIds);
	}

	public void notifyWeixin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			InputStream in = request.getInputStream();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			out.close();
			in.close();

			String msgxml = new String(out.toByteArray(), "utf-8");

			XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
			xStream.alias("xml", UnifiedOrderRespose.class);
			UnifiedOrderRespose unifiedOrderRespose = (UnifiedOrderRespose) xStream.fromXML(msgxml.toString());

			String result_code = unifiedOrderRespose.getResult_code();
			String out_trade_no = unifiedOrderRespose.getOut_trade_no();// 订单组编号
			String transaction_id = unifiedOrderRespose.getTransaction_id();//
			String trade_type = unifiedOrderRespose.getTrade_type();
			Date time_date = DateUtils.parseDateNospace(unifiedOrderRespose.getTime_end());
			int total_fee = Integer.valueOf(unifiedOrderRespose.getTotal_fee());
			Double amount = new Double(total_fee) / 100;// 订单金额 元

			System.out.println("----------request attach--------" + unifiedOrderRespose.getAttach());

			String str = new Gson().toJson(unifiedOrderRespose);
			Gson gson = new Gson();
			Map<String, String> map = gson.fromJson(str, Map.class);
			String key = null;
			if ("JSAPI".equals(trade_type)) {
				key = WeixinConst.keyH5;
			} else {
				key = WeixinConst.key;
			}
			System.out.println("----------request--------" + new Gson().toJson(map) + "----------------"
					+ WPay.verifyResponse(map, key));
			if ("SUCCESS".equals(result_code)) {
				if (WPay.verifyResponse(map, key)) {// 验证签名是否正确
					List<OrderPayEntity> payList = orderPayEntityMapper.selectPayStateByGroupId(out_trade_no);

					if (payList != null && payList.get(0).getPay_state() == 0) {// 未支付
						dealOrder(out_trade_no, time_date, transaction_id, amount, OrderStatusEnum.WEIXIN.value);
						response.getWriter().write(setXml("SUCCESS", "OK")); // 告诉微信已经收到通知了
					} else {
						response.getWriter().write(setXml("SUCCESS", "OK"));
					}
				} else {
					System.out.println("------------------验证失败------------------- ");
				}
			} else {
				System.out.println("=====支付失败" + out_trade_no + "|return_msg|" + unifiedOrderRespose.getReturn_msg()
						+ "\r\n信息| " + unifiedOrderRespose.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void WXRnotifyForDeposit(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		InputStream in = request.getInputStream();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = in.read(buffer)) != -1) {
			out.write(buffer, 0, len);
		}
		out.close();
		in.close();

		String msgxml = new String(out.toByteArray(), "utf-8");

		XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
		xStream.alias("xml", UnifiedOrderRespose.class);
		UnifiedOrderRespose unifiedOrderRespose = (UnifiedOrderRespose) xStream.fromXML(msgxml.toString());

		String result_code = unifiedOrderRespose.getResult_code();
		String out_trade_no = unifiedOrderRespose.getOut_trade_no();// 订单号
		String transaction_id = unifiedOrderRespose.getTransaction_id();//
		String trade_type = unifiedOrderRespose.getTrade_type();
		Date time_date = DateUtils.parseDateNospace(unifiedOrderRespose.getTime_end());
		int total_fee = Integer.valueOf(unifiedOrderRespose.getTotal_fee());
		String sign = unifiedOrderRespose.getSign();
		Double amount = new Double(total_fee) / 100;// 订单金额 元

		try {
			String str = new Gson().toJson(unifiedOrderRespose);
			Gson gson = new Gson();
			Map<String, String> map = gson.fromJson(str, Map.class);

			String key = null;
			if ("JSAPI".equals(trade_type)) {
				key = WeixinConst.keyH5;
			} else {
				key = WeixinConst.key;
			}
			if ("SUCCESS".equals(result_code)) {
				if (WPay.verifyResponse(map, key)) {// 验证签名是否正确
					OrderPayEntity state = orderPayEntityMapper.selectPayStateByCode(out_trade_no);

					if (state.getPay_state() == OrderStatusEnum.PADY.value && !state.isIsdeposit()) {// 待付尾款状态
						dealOrderForDeposit(out_trade_no, time_date, transaction_id, amount,
								OrderStatusEnum.WEIXIN.value);
						response.getWriter().write(setXml("SUCCESS", "OK")); // 告诉微信已经收到通知了
					} else {
						response.getWriter().write(setXml("SUCCESS", "OK"));
					}
				} else {
					System.out.println("------------------验证失败------------------- ");
				}
			} else {
				System.out.println("=====支付失败" + out_trade_no + "|return_msg|" + unifiedOrderRespose.getReturn_msg()
						+ "\r\n信息| " + unifiedOrderRespose.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void WXRnotifyForCash(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		InputStream in = request.getInputStream();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = in.read(buffer)) != -1) {
			out.write(buffer, 0, len);
		}
		out.close();
		in.close();

		String msgxml = new String(out.toByteArray(), "utf-8");

		XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
		xStream.alias("xml", UnifiedOrderRespose.class);
		UnifiedOrderRespose unifiedOrderRespose = (UnifiedOrderRespose) xStream.fromXML(msgxml.toString());

		String result_code = unifiedOrderRespose.getResult_code();
		String out_trade_no = unifiedOrderRespose.getOut_trade_no();// 订单号
		String transaction_id = unifiedOrderRespose.getTransaction_id();//
		String trade_type = unifiedOrderRespose.getTrade_type();
		Date time_date = DateUtils.parseDateNospace(unifiedOrderRespose.getTime_end());
		int total_fee = Integer.valueOf(unifiedOrderRespose.getTotal_fee());
		String sign = unifiedOrderRespose.getSign();
		Double amount = new Double(total_fee) / 100;// 订单金额 元

		try {
			String str = new Gson().toJson(unifiedOrderRespose);
			Gson gson = new Gson();
			Map<String, String> map = gson.fromJson(str, Map.class);
			String key = null;
			if ("JSAPI".equals(trade_type)) {
				key = WeixinConst.keyH5;
			} else {
				key = WeixinConst.key;
			}
			System.out.println("----------request--------" + str);
			if ("SUCCESS".equals(result_code)) {
				if (WPay.verifyResponse(map, key)) {// 验证签名是否正确
					OrderCashEntity o = orderCashEntityMapper.selectByCode(out_trade_no);

					if (o != null && o.getPay_state() == OrderStatusEnum.UNPADY.value) {// 待付款状态
						o.setPay_state(OrderStatusEnum.PADY.value);
						o.setPay_time(new Date());
						o.setCustomer_pay_id(OrderStatusEnum.WEIXIN.value);
						o.setTrade_data(transaction_id);
						updateOrderCash(o);

						if (o.getType().equals(1)) {
							unionPromotionMapper.updateSellNumByPrimaryKey(o.getPromotion_id().intValue(), 1);
						}

						UnionCustomer unionCustomer = new UnionCustomer();
						unionCustomer.setPromotion_id(o.getPromotion_id().intValue());
						unionCustomer.setCreate_time(new Date());
						unionCustomer.setCustomer_id(o.getCustomer_id());
						unionCustomer.setState(0);
						unionCustomer.setOrder_code(out_trade_no);
						unionCustomerMapper.insertSelective(unionCustomer);

						response.getWriter().write(setXml("SUCCESS", "OK"));
					} else {
						response.getWriter().write(setXml("SUCCESS", "OK"));
					}
				} else {
					System.out.println("------------------验证失败------------------- ");
				}
			} else {
				System.out.println("=====支付失败" + out_trade_no + "|return_msg|" + unifiedOrderRespose.getReturn_msg()
						+ "\r\n信息| " + unifiedOrderRespose.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void WXRnotifyForPreShopcartPay(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		InputStream in = request.getInputStream();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = in.read(buffer)) != -1) {
			out.write(buffer, 0, len);
		}
		out.close();
		in.close();

		String msgxml = new String(out.toByteArray(), "utf-8");

		XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
		xStream.alias("xml", UnifiedOrderRespose.class);
		UnifiedOrderRespose unifiedOrderRespose = (UnifiedOrderRespose) xStream.fromXML(msgxml.toString());

		String result_code = unifiedOrderRespose.getResult_code();
		String out_trade_no = unifiedOrderRespose.getOut_trade_no();// 订单号
		String transaction_id = unifiedOrderRespose.getTransaction_id();//
		String trade_type = unifiedOrderRespose.getTrade_type();
		Date time_date = DateUtils.parseDateNospace(unifiedOrderRespose.getTime_end());
		int total_fee = Integer.valueOf(unifiedOrderRespose.getTotal_fee());
		String sign = unifiedOrderRespose.getSign();
		Double amount = new Double(total_fee) / 100;// 订单金额 元

		System.out
				.println(total_fee + "  =====================" + amount + "===================  " + amount.intValue());
		try {
			String str = new Gson().toJson(unifiedOrderRespose);
			Gson gson = new Gson();
			Map<String, String> map = gson.fromJson(str, Map.class);
			String key = null;
			if ("JSAPI".equals(trade_type)) {
				key = WeixinConst.keyH5;
			} else {
				key = WeixinConst.key;
			}
			System.out.println("----------request--------" + str);
			if ("SUCCESS".equals(result_code)) {
				if (WPay.verifyResponse(map, key)) {// 验证签名是否正确

					OrderCashNewEntity o = orderCashNewEntityMapper.selectByCode(out_trade_no);
					if (o != null && o.getPay_state() == OrderStatusEnum.UNPADY.value) {// 待付款状态
						o.setPay_state(OrderStatusEnum.PADY.value);
						o.setPay_time(new Date());
						o.setCustomer_pay_id(OrderStatusEnum.WEIXIN.value);
						o.setTrade_data(transaction_id);
						updateOrderCashNew(o);

						ShopPrepayEntity shopPrepayEntity = new ShopPrepayEntity();
						shopPrepayEntity.setCustomer_id(o.getCustomer_id());
						shopPrepayEntity.setValue(o.getOrder_price().intValue());
						shopPrepayEntity.setShop_id(o.getShop_id());
						shopPrepayEntity.setCreate_time(new Date());
						shopPrepayEntity.setState(0);
						shopPrepayEntity.setOrder_code(out_trade_no);

						shopPrepayEntityMapper.insertSelective(shopPrepayEntity);

						response.getWriter().write(setXml("SUCCESS", "OK"));
					} else {
						response.getWriter().write(setXml("SUCCESS", "OK"));
					}

				} else {
					System.out.println("------------------验证失败------------------- ");
				}
			} else {
				System.out.println("=====支付失败" + out_trade_no + "|return_msg|" + unifiedOrderRespose.getReturn_msg()
						+ "\r\n信息| " + unifiedOrderRespose.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void WXRnotifyForConstract(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		InputStream in = request.getInputStream();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = in.read(buffer)) != -1) {
			out.write(buffer, 0, len);
		}
		out.close();
		in.close();

		String msgxml = new String(out.toByteArray(), "utf-8");

		XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
		xStream.alias("xml", UnifiedOrderRespose.class);
		UnifiedOrderRespose unifiedOrderRespose = (UnifiedOrderRespose) xStream.fromXML(msgxml.toString());

		String result_code = unifiedOrderRespose.getResult_code();
		String out_trade_no = unifiedOrderRespose.getOut_trade_no();// 订单号
		String transaction_id = unifiedOrderRespose.getTransaction_id();//
		String trade_type = unifiedOrderRespose.getTrade_type();
		Date time_date = DateUtils.parseDateNospace(unifiedOrderRespose.getTime_end());
		int total_fee = Integer.valueOf(unifiedOrderRespose.getTotal_fee());
		String sign = unifiedOrderRespose.getSign();
		Double amount = new Double(total_fee) / 100;// 订单金额 元

		System.out
				.println(total_fee + "  =====================" + amount + "===================  " + amount.intValue());
		try {
			String str = new Gson().toJson(unifiedOrderRespose);
			Gson gson = new Gson();
			Map<String, String> map = gson.fromJson(str, Map.class);
			String key = null;
			if ("JSAPI".equals(trade_type)) {
				key = WeixinConst.keyH5;
			} else {
				key = WeixinConst.key;
			}
			System.out.println("----------request--------" + str);
			if ("SUCCESS".equals(result_code)) {
				if (WPay.verifyResponse(map, key)) {// 验证签名是否正确

					OrderCashNewEntity o = orderCashNewEntityMapper.selectByCode(out_trade_no);
					if (o != null && o.getPay_state() == OrderStatusEnum.UNPADY.value) {// 待付款状态
						o.setPay_state(OrderStatusEnum.PADY.value);
						o.setPay_time(new Date());
						o.setCustomer_pay_id(OrderStatusEnum.WEIXIN.value);
						o.setTrade_data(transaction_id);
						updateOrderCashNew(o);

						ConstructStateRelation constructStateRelation = new ConstructStateRelation();
						constructStateRelation.setConstruct_appointment_id(o.getType_id());
						constructStateRelation.setCreate_time(new Date());
						constructStateRelation.setState_code(ConstructAppointmentEnum.NEEDCOMMENT.code);
						constructStateRelationMapper.insertSelective(constructStateRelation);

						response.getWriter().write(setXml("SUCCESS", "OK"));
					} else {
						response.getWriter().write(setXml("SUCCESS", "OK"));
					}

				} else {
					System.out.println("------------------验证失败------------------- ");
				}
			} else {
				System.out.println("=====支付失败" + out_trade_no + "|return_msg|" + unifiedOrderRespose.getReturn_msg()
						+ "\r\n信息| " + unifiedOrderRespose.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void WXRnotifyForCoupons(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		InputStream in = request.getInputStream();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = in.read(buffer)) != -1) {
			out.write(buffer, 0, len);
		}
		out.close();
		in.close();

		String msgxml = new String(out.toByteArray(), "utf-8");

		XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
		xStream.alias("xml", UnifiedOrderRespose.class);
		UnifiedOrderRespose unifiedOrderRespose = (UnifiedOrderRespose) xStream.fromXML(msgxml.toString());

		String result_code = unifiedOrderRespose.getResult_code();
		String out_trade_no = unifiedOrderRespose.getOut_trade_no();// 订单号
		String transaction_id = unifiedOrderRespose.getTransaction_id();//
		String trade_type = unifiedOrderRespose.getTrade_type();
		Date time_date = DateUtils.parseDateNospace(unifiedOrderRespose.getTime_end());
		int total_fee = Integer.valueOf(unifiedOrderRespose.getTotal_fee());
		String sign = unifiedOrderRespose.getSign();
		Double amount = new Double(total_fee) / 100;// 订单金额 元

		System.out
				.println(total_fee + "  =====================" + amount + "===================  " + amount.intValue());
		try {
			String str = new Gson().toJson(unifiedOrderRespose);
			Gson gson = new Gson();
			Map<String, String> map = gson.fromJson(str, Map.class);
			String key = null;
			if ("JSAPI".equals(trade_type)) {
				key = WeixinConst.keyH5;
			} else {
				key = WeixinConst.key;
			}
			System.out.println("----------request--------" + str);
			if ("SUCCESS".equals(result_code)) {
				if (WPay.verifyResponse(map, key)) {// 验证签名是否正确

					OrderCashNewEntity o = orderCashNewEntityMapper.selectByCode(out_trade_no);
					if (o != null && o.getPay_state() == OrderStatusEnum.UNPADY.value) {// 待付款状态
						o.setPay_state(OrderStatusEnum.PADY.value);
						o.setPay_time(new Date());
						o.setCustomer_pay_id(OrderStatusEnum.WEIXIN.value);
						o.setTrade_data(transaction_id);
						updateOrderCashNew(o);

						BaseCustomerCouponsEntity record = new BaseCustomerCouponsEntity();
						record.setCoupons_id(o.getType_id());
						record.setCreate_time(new Date());
						record.setCustomer_id(o.getCustomer_id());
						record.setIsused(false);
						record.setOrder_code(out_trade_no);

						CustomerEntity customerEntity = customerEntityMapper.selectByPrimaryKey(o.getCustomer_id());
						if (customerEntity != null) {
							StringBuffer convert_code = new StringBuffer();
							convert_code.append(customerEntity.getAccount().substring(0, 3));
							convert_code.append(UUID.randomUUID().toString().toUpperCase().substring(0, 8));
							convert_code.append(customerEntity.getAccount().substring(7, 11));
							record.setConvert_code(convert_code.toString());
						}

						baseCustomerCouponsEntityMapper.insertSelective(record);

						brandCouponsEntityMapper.deleteSurplusById(1, o.getType_id());

						response.getWriter().write(setXml("SUCCESS", "OK"));
					} else {
						response.getWriter().write(setXml("SUCCESS", "OK"));
					}

				} else {
					System.out.println("------------------验证失败------------------- ");
				}
			} else {
				System.out.println("=====支付失败" + out_trade_no + "|return_msg|" + unifiedOrderRespose.getReturn_msg()
						+ "\r\n信息| " + unifiedOrderRespose.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String setXml(String return_code, String return_msg) {
		return "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA[" + return_msg
				+ "]]></return_msg></xml>";
	}

	public UnifiedOrderRequest customerPayOrderH5(UnifiedOrderRequest unifiedOrderRequest)
			throws UnsupportedEncodingException {

		if (unifiedOrderRequest.getOpenid() == null) {
			return unifiedOrderRequest;
		}

		String groupOrderId = OrderManager.makeUUID();// 随机数作为分组支付id grouppay_id
		List<String> orderCodeList = unifiedOrderRequest.getOrders();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("grouppay_id", groupOrderId);
		map.put("listOrdersId", orderCodeList);
		updateGroupIdByOrderCode(map);// 存储总订单号

		Double price_total = 0D;
		List<OrderEntity> orderList = orderEntityMapper.selectOrdersByCodeList(orderCodeList);
		for (OrderEntity o : orderList) {
			// if (o.getIsdeposit()) {
			// price_total += o.getDeposit_price();
			// } else {
			price_total += o.getOrder_price();
			// }
		}

		if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
			price_total = 0.01D;
		}
		String orderInfo = WPay.createOrderInfoH5(unifiedOrderRequest, groupOrderId, ((int) (price_total * 100)) + "",
				WeixinConst.notify_url, WeixinConst.notify_url_debug);
		String prepay_id = WPay.httpOrder(orderInfo);

		unifiedOrderRequest.setPrepay_id(prepay_id);

		unifiedOrderRequest.setSign(WPay.createSignH5(unifiedOrderRequest, WeixinConst.keyH5, prepay_id));

		return unifiedOrderRequest;
	}

	public UnifiedOrderRequest customerpayorderfordepositH5(UnifiedOrderRequest unifiedOrderRequest)
			throws UnsupportedEncodingException {

		if (unifiedOrderRequest.getOpenid() == null) {
			return unifiedOrderRequest;
		}

		List<String> orderCodeList = unifiedOrderRequest.getOrders();
		if (orderCodeList.size() != 1)
			return unifiedOrderRequest;// 只接受一个订单号
		Double price_total = 0D;
		List<OrderEntity> orderList = orderEntityMapper.selectOrdersByCodeList(orderCodeList);// 一个订单
		for (OrderEntity o : orderList) {
			if (o.getIsdeposit()) {
				price_total += o.getDeposit_price();
			} else {
				return unifiedOrderRequest;
			}
		}

		if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
			price_total = 0.01D;
		}

		String orderInfo = WPay.createOrderInfoH5(unifiedOrderRequest, orderCodeList.get(0),
				((int) (price_total * 100)) + "", WeixinConst.notify_deposit_url, WeixinConst.notify_deposit_url_debug);
		String prepay_id = WPay.httpOrder(orderInfo);

		unifiedOrderRequest.setPrepay_id(prepay_id);
		unifiedOrderRequest.setSign(WPay.createSignH5(unifiedOrderRequest, WeixinConst.keyH5, prepay_id));

		return unifiedOrderRequest;
	}

	public UnifiedOrderRequest customerpayorderforcashH5(OrderCashEntity orderCashEntity)
			throws UnsupportedEncodingException {

		UnifiedOrderRequest unifiedOrderRequest = new UnifiedOrderRequest();

		if (orderCashEntity.getOpenid() == null) {
			return unifiedOrderRequest;
		}
		unifiedOrderRequest.setOpenid(orderCashEntity.getOpenid());
		unifiedOrderRequest.setSpbill_create_ip(orderCashEntity.getSpbill_create_ip());

		String orderCode = HttpRequest.sendPost(SysConstant.orderurl, null);// 订单号
		if (StringUtils.isEmpty(orderCode))
			throw new RuntimeException();// 没有请求到订单号，返回
		orderCode = orderCode.replaceAll("\"", "");
		orderCashEntity.setCode(orderCode);
		if (insertCashOrder(orderCashEntity) <= 0) {
			return unifiedOrderRequest;
		}

		if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
			orderCashEntity.setOrder_price(0.01D);
		}
		String orderInfo = WPay.createOrderInfoH5(unifiedOrderRequest, orderCode,
				((int) (orderCashEntity.getOrder_price() * 100)) + "", WeixinConst.notify_cash_url,
				WeixinConst.notify_cash_url_debug);
		if (orderInfo != null) {
			return unifiedOrderRequest;
		}

		String prepay_id = WPay.httpOrder(orderInfo);

		unifiedOrderRequest.setPrepay_id(prepay_id);
		unifiedOrderRequest.setSign(WPay.createSignH5(unifiedOrderRequest, WeixinConst.keyH5, prepay_id));

		return unifiedOrderRequest;

	}

	public UnifiedOrderRequest customerpayorderforcashNewH5(OrderCashNewEntity orderCashNewEntity)
			throws UnsupportedEncodingException {

		UnifiedOrderRequest unifiedOrderRequest = new UnifiedOrderRequest();

		if (orderCashNewEntity.getOpenid() == null) {
			return unifiedOrderRequest;
		}

		unifiedOrderRequest.setOpenid(orderCashNewEntity.getOpenid());
		unifiedOrderRequest.setSpbill_create_ip(orderCashNewEntity.getSpbill_create_ip());

		String orderCode = HttpRequest.sendPost(SysConstant.orderurl, null);// 订单号
		if (StringUtils.isEmpty(orderCode))
			throw new RuntimeException();// 没有请求到订单号，返回
		orderCode = orderCode.replaceAll("\"", "");

		orderCashNewEntity.setCode(orderCode);
		orderCashNewEntity = insertCashOrderNew(orderCashNewEntity);

		if (orderCashNewEntity == null) {// 保存初始订单信息
			return unifiedOrderRequest;
		}

		if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
			orderCashNewEntity.setOrder_price(0.01D);
		}
		String orderInfo = WPay.createOrderInfoH5(unifiedOrderRequest, orderCode,
				((int) (orderCashNewEntity.getOrder_price() * 100)) + "", WeixinConst.notify_cash_url_new,
				WeixinConst.notify_cash_url_new_debug);
		String prepay_id = WPay.httpOrder(orderInfo);

		unifiedOrderRequest.setPrepay_id(prepay_id);
		unifiedOrderRequest.setSign(WPay.createSignH5(unifiedOrderRequest, WeixinConst.keyH5, prepay_id));

		return unifiedOrderRequest;

	}

	public UnifiedOrderRequest customerpayorderforconstructH5(ConstructAppointment param)
			throws UnsupportedEncodingException {

		UnifiedOrderRequest unifiedOrderRequest = new UnifiedOrderRequest();

		if (param.getOpenid() == null) {
			return unifiedOrderRequest;
		}

		unifiedOrderRequest.setOpenid(param.getOpenid());
		unifiedOrderRequest.setSpbill_create_ip(param.getSpbill_create_ip());

		String orderCode = HttpRequest.sendPost(SysConstant.orderurl, null);// 订单号
		if (StringUtils.isEmpty(orderCode))
			throw new RuntimeException();// 没有请求到订单号，返回
		orderCode = orderCode.replaceAll("\"", "");

		ConstructAppointment appointment = constructAppointmentMapper.selectByPrimaryKey(param.getId());
		CustomerEntity customerEntity = customerEntityMapper.selectByPrimaryKey(appointment.getCustomer_id());
		OrderCashNewEntity orderCashNewEntity = new OrderCashNewEntity();
		orderCashNewEntity.setCode(orderCode);
		orderCashNewEntity.setCustomer_id(appointment.getConstruct_id());
		orderCashNewEntity.setExshopper_id(customerEntity.getExshopper_id());
		orderCashNewEntity.setOrder_price(appointment.getConstruct_data());
		orderCashNewEntity.setCreate_time(new Date());
		orderCashNewEntity.setPay_state(OrderStatusEnum.UNPADY.value);
		orderCashNewEntity.setType(OrderStatusEnum.CONSTRUCT.value);
		orderCashNewEntity.setType_id(param.getId());
		int result = orderCashNewEntityMapper.insertSelective(orderCashNewEntity);

		if (result <= 0) {
			return unifiedOrderRequest;
		}

		if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
			orderCashNewEntity.setOrder_price(0.01D);
		}

		String orderInfo = WPay.createOrderInfoH5(unifiedOrderRequest, orderCode,
				((int) (orderCashNewEntity.getOrder_price() * 100)) + "", WeixinConst.notify_constract,
				WeixinConst.notify_constract_debug);
		String prepay_id = WPay.httpOrder(orderInfo);

		unifiedOrderRequest.setPrepay_id(prepay_id);
		unifiedOrderRequest.setSign(WPay.createSignH5(unifiedOrderRequest, WeixinConst.keyH5, prepay_id));

		return unifiedOrderRequest;

	}

	public synchronized UnifiedOrderRequest customerpayorderforCouponsH5(BrandCouponsModel param)
			throws UnsupportedEncodingException {

		UnifiedOrderRequest unifiedOrderRequest = new UnifiedOrderRequest();

		if (param.getOpenid() == null) {
			return unifiedOrderRequest;
		}

		unifiedOrderRequest.setOpenid(param.getOpenid());
		unifiedOrderRequest.setSpbill_create_ip(param.getSpbill_create_ip());

		String orderCode = HttpRequest.sendPost(SysConstant.orderurl, null);// 订单号
		if (StringUtils.isEmpty(orderCode))
			throw new RuntimeException();// 没有请求到订单号，返回
		orderCode = orderCode.replaceAll("\"", "");

		// if(customerCouponsEntityMapper.selectCustomerWhetherGainCoupons(param)
		// >0){
		// return unifiedOrderRequest;
		// }
		BaseBrandCouponsEntity coupons = brandCouponsEntityMapper.selectByPrimaryKey(param.getCoupons_id());

		if (coupons == null) {
			return unifiedOrderRequest;
		}

		if (coupons.getSurplus_count() < 1) {
			return unifiedOrderRequest;
		}

		CustomerEntity customerEntity = customerEntityMapper.selectByPrimaryKey(param.getCustomer_id());

		if (customerEntity == null) {
			return unifiedOrderRequest;
		}

		OrderCashNewEntity orderCashNewEntity = new OrderCashNewEntity();
		orderCashNewEntity.setCode(orderCode);
		orderCashNewEntity.setCustomer_id(param.getCustomer_id());
		orderCashNewEntity.setExshopper_id(customerEntity.getExshopper_id());
		orderCashNewEntity.setOrder_price(param.getCoupons_price());
		orderCashNewEntity.setCreate_time(new Date());
		orderCashNewEntity.setPay_state(OrderStatusEnum.UNPADY.value);
		orderCashNewEntity.setType(OrderStatusEnum.CONPONS.value);
		orderCashNewEntity.setType_id(param.getCoupons_id());

		int result = orderCashNewEntityMapper.insertSelective(orderCashNewEntity);

		if (result <= 0) {
			return unifiedOrderRequest;
		}

		if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
			orderCashNewEntity.setOrder_price(0.01D);
		}

		String prepay_id = WPay.httpOrder(WPay.createOrderInfoH5(unifiedOrderRequest, orderCode,
				((int) (orderCashNewEntity.getOrder_price() * 100)) + "", WeixinConst.notify_coupons,
				WeixinConst.notify_coupons_debug));

		unifiedOrderRequest.setPrepay_id(prepay_id);
		unifiedOrderRequest.setSign(WPay.createSignH5(unifiedOrderRequest, WeixinConst.keyH5, prepay_id));

		return unifiedOrderRequest;

	}

	public ResponseMessage customerAliPayOrder(AliOrderRequest aliOrderRequest)
			throws ClientProtocolException, IOException {

		ResponseMessage<Object> responseMessage = new ResponseMessage<Object>();
		try {
			String groupOrderId = OrderManager.makeUUID();// 随机数作为分组支付id
															// grouppay_id
			List<String> orderCodeList = aliOrderRequest.getOrders();

			// if (selectPayStateByCodes(orderCodeList) != orderCodeList.size())
			// {//
			// 检查订单都未支付
			// responseMessage.setCode(SysConstant.FAILURE_CODE);
			// responseMessage.setMessage(SysConstant.ORDER_PAY_FAIL);
			// return responseMessage;
			// }

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("grouppay_id", groupOrderId);
			map.put("listOrdersId", orderCodeList);

			updateGroupIdByOrderCode(map);// 存储总订单号
			Double price_total = 0D;
			List<OrderEntity> orderList = orderEntityMapper.selectOrdersByCodeList(orderCodeList);
			for (OrderEntity o : orderList) {
				// if (o.getIsdeposit()) {
				// price_total += o.getDeposit_price();
				// } else {
				price_total += o.getOrder_price();
				// }
			}

			if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
				price_total = 0.01;
			}
			AliOrderRequest ali = new AliOrderRequest();
//			Map<String, String> parammap = new HashMap<String, String>();
//			parammap.put("partner", AlipayConfig.partner);
//			parammap.put("seller_id", AlipayConfig.seller_id);
//			parammap.put("out_trade_no", groupOrderId);
//			parammap.put("subject", AlipayConfig.subject);
//			parammap.put("body", AlipayConfig.body);
//			parammap.put("total_fee", price_total.toString());
//			if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
//				parammap.put("notify_url", AlipayConfig.notify_url_debug);
//			} else {
//				parammap.put("notify_url", AlipayConfig.notify_url);
//			}
//			parammap.put("service", AlipayConfig.service);
//			parammap.put("payment_type", AlipayConfig.payment_type);
//			parammap.put("_input_charset", AlipayConfig.input_charset);
//			parammap.put("extra_common_param", "test_extra_common_param");
//			AlipayCore.paraFilter(parammap);
//			String orderinfo = AlipayCore.createLinkString(parammap);// andorid后台拼接

			StringBuilder result_ios = new StringBuilder();
			result_ios.append("partner=").append("\"").append(AlipayConfig.partner).append("\"").append("&");
			result_ios.append("seller_id=").append("\"").append(AlipayConfig.seller_id).append("\"").append("&");
			result_ios.append("out_trade_no=").append("\"").append(groupOrderId).append("\"").append("&");
			result_ios.append("subject=").append("\"").append(AlipayConfig.subject).append("\"").append("&");
			result_ios.append("body=").append("\"").append(AlipayConfig.body).append("\"").append("&");
			result_ios.append("total_fee=").append("\"").append(price_total.toString()).append("\"").append("&");
			if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
				result_ios.append("notify_url=").append("\"").append(AlipayConfig.notify_url_debug).append("\"").append("&");
			}else{
				result_ios.append("notify_url=").append("\"").append(AlipayConfig.notify_url).append("\"").append("&");
			}
			result_ios.append("service=").append("\"").append(AlipayConfig.service).append("\"").append("&");
			result_ios.append("payment_type=").append("\"").append(AlipayConfig.payment_type).append("\"").append("&");
			result_ios.append("_input_charset=").append("\"").append(AlipayConfig.input_charset).append("\"");
			
			ali.setOrderInfo(result_ios.toString());
			
			String sign = "";
			   try {
			      sign = URLEncoder.encode(RSA.sign(result_ios.toString(), AlipayConfig.private_key, "utf-8"), "utf-8");//private_key私钥
			   } catch (UnsupportedEncodingException e) {
			      e.printStackTrace();
			   }
			   result_ios.append("&sign=\""+sign+"\"&");
			   result_ios.append("sign_type=\"RSA\"");
			
			ali.setSign(result_ios.toString());
			ali.setSign_type(AlipayConfig.sign_type);

			ali.setPartner(AlipayConfig.partner);
			ali.setSeller_id(AlipayConfig.seller_id);
			ali.setOut_trade_no(groupOrderId);
			ali.setSubject(AlipayConfig.subject);
			ali.setBody(AlipayConfig.body);
			ali.setTotal_fee(price_total.toString());
			if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
				ali.setNotify_url(AlipayConfig.notify_url_debug);
			} else {
				ali.setNotify_url(AlipayConfig.notify_url);
			}
			ali.setService(AlipayConfig.service);
			ali.setPayment_type(AlipayConfig.payment_type);
			ali.set_input_charset(AlipayConfig.input_charset);
			ali.setPrivate_key(AlipayConfig.private_key);// ios前台拼接

			responseMessage.setCode("0");
			responseMessage.setMessage("success");
			responseMessage.setResult(ali);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseMessage;
	}

	public void notifyAli(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 需要根据 优惠券 红包 参加的活动 预付金重新计算订单价格
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		String out_trade_no = request.getParameter("out_trade_no");// 订单号
		String tradeNo = request.getParameter("trade_no");// 支付宝交易号
		Double amount = Double.parseDouble(request.getParameter("total_fee"));// 交易金额
		String tradeStatus = request.getParameter("trade_status");// 交易状态
		String payer = request.getParameter("buyer_email");// 支付者账号
		String notify_type = request.getParameter("notify_type");// 交易状态

		System.out.println(tradeStatus + " -------------notify_type-------------------------- " + notify_type);
		System.out.println("-------------extra_common_param-------------------------- "
				+ request.getParameter("extra_common_param"));

		System.out.println(tradeStatus + " -------------requestParams-------------------------- "
				+ new Gson().toJson(requestParams));

		// Date time =
		// DateUtils.parseDate_(request.getParameter("gmt_payment"));
		System.out.println("----------request--------" + new Gson().toJson(params) + "----------------"
				+ AlipayNotify.verify(params));
		if ("TRADE_SUCCESS".equals(tradeStatus)) {
			if (AlipayNotify.verify(params)) {// 验证成功

				List<OrderPayEntity> payList = orderPayEntityMapper.selectPayStateByGroupId(out_trade_no);
				if (payList != null && payList.get(0).getPay_state() == 0) {// 未支付
					dealOrder(out_trade_no, new Date(), tradeNo, amount, OrderStatusEnum.ALI.value);
					response.getWriter().write("success");
					response.getWriter().flush();
					response.getWriter().close();
				} else {
					response.getWriter().write("success");
					response.getWriter().flush();
					response.getWriter().close();
				}
			} else {
				System.out.println("=====验证失败==================================================");
			}
		} else {// 验证失败
			System.out.println("----------request失败--------" + out_trade_no);
		}
	}

	public ResponseMessage customerAliPayOrderForDeposit(AliOrderRequest aliOrderRequest)
			throws ClientProtocolException, IOException {

		ResponseMessage<Object> responseMessage = new ResponseMessage<Object>();
		List<String> orderCodeList = aliOrderRequest.getOrders();
		if (orderCodeList.size() != 1)
			return responseMessage;
		Double price_total = 0D;
		List<OrderEntity> orderList = orderEntityMapper.selectOrdersByCodeList(orderCodeList);
		for (OrderEntity o : orderList) {
			if (o.getIsdeposit()) {
				price_total += o.getDeposit_price();
			} else {
				return responseMessage;
			}
		}

		if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
			price_total = 0.01;
		}

		AliOrderRequest ali = new AliOrderRequest();
//		Map<String, String> parammap = new HashMap<String, String>();
//		parammap.put("partner", AlipayConfig.partner);
//		parammap.put("seller_id", AlipayConfig.seller_id);
//		parammap.put("out_trade_no", orderCodeList.get(0));
//		parammap.put("subject", AlipayConfig.subject);
//		parammap.put("body", AlipayConfig.body);
//		parammap.put("total_fee", price_total.toString());
//		if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
//			parammap.put("notify_url", AlipayConfig.notify_deposit_url_debug);
//		} else {
//			parammap.put("notify_url", AlipayConfig.notify_deposit_url);
//		}
//		parammap.put("service", AlipayConfig.service);
//		parammap.put("payment_type", AlipayConfig.payment_type);
//		parammap.put("_input_charset", AlipayConfig.input_charset);
//		AlipayCore.paraFilter(parammap);
//		String orderinfo = AlipayCore.createLinkString(parammap);// andorid后台拼接

		StringBuilder result_ios = new StringBuilder();
		result_ios.append("partner=").append("\"").append(AlipayConfig.partner).append("\"").append("&");
		result_ios.append("seller_id=").append("\"").append(AlipayConfig.seller_id).append("\"").append("&");
		result_ios.append("out_trade_no=").append("\"").append(orderCodeList.get(0)).append("\"").append("&");
		result_ios.append("subject=").append("\"").append(AlipayConfig.subject).append("\"").append("&");
		result_ios.append("body=").append("\"").append(AlipayConfig.body).append("\"").append("&");
		result_ios.append("total_fee=").append("\"").append(price_total.toString()).append("\"").append("&");
		if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
			result_ios.append("notify_url=").append("\"").append(AlipayConfig.notify_deposit_url_debug).append("\"").append("&");
		}else{
			result_ios.append("notify_url=").append("\"").append(AlipayConfig.notify_deposit_url).append("\"").append("&");
		}
		result_ios.append("service=").append("\"").append(AlipayConfig.service).append("\"").append("&");
		result_ios.append("payment_type=").append("\"").append(AlipayConfig.payment_type).append("\"").append("&");
		result_ios.append("_input_charset=").append("\"").append(AlipayConfig.input_charset).append("\"");
		
		ali.setOrderInfo(result_ios.toString());
		
		String sign = "";
		   try {
		      sign = URLEncoder.encode(RSA.sign(result_ios.toString(), AlipayConfig.private_key, "utf-8"), "utf-8");//private_key私钥
		   } catch (UnsupportedEncodingException e) {
		      e.printStackTrace();
		   }
		   result_ios.append("&sign=\""+sign+"\"&");
		   result_ios.append("sign_type=\"RSA\"");
		
		ali.setSign(result_ios.toString());
		ali.setSign_type(AlipayConfig.sign_type);

		ali.setPartner(AlipayConfig.partner);
		ali.setSeller_id(AlipayConfig.seller_id);
		ali.setOut_trade_no(orderCodeList.get(0));
		ali.setSubject(AlipayConfig.subject);
		ali.setBody(AlipayConfig.body);
		ali.setTotal_fee(price_total.toString());
		if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
			ali.setNotify_url(AlipayConfig.notify_deposit_url_debug);
		} else {
			ali.setNotify_url(AlipayConfig.notify_deposit_url);
		}
		ali.setService(AlipayConfig.service);
		ali.setPayment_type(AlipayConfig.payment_type);
		ali.set_input_charset(AlipayConfig.input_charset);
		ali.setPrivate_key(AlipayConfig.private_key);// ios前台拼接

		responseMessage.setCode("0");
		responseMessage.setMessage("success");
		responseMessage.setResult(ali);
		return responseMessage;
	}

	public void ALIXYZnotifyForDeposit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 需要根据 优惠券 红包 参加的活动 预付金重新计算订单价格
		try {
			Map<String, String> params = new HashMap<String, String>();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
				}
				// valueStr = new String(valueStr.getBytes("ISO-8859-1"),
				// "utf-8");
				params.put(name, valueStr);
			}
			String out_trade_no = request.getParameter("out_trade_no");// 订单号
			String tradeNo = request.getParameter("trade_no");// 支付宝交易号
			Double amount = Double.parseDouble(request.getParameter("total_fee"));// 交易金额
			String tradeStatus = request.getParameter("trade_status");// 交易状态
			String payer = request.getParameter("buyer_email");// 支付者账号
			// Date time =
			// DateUtils.parseDate_(request.getParameter("gmt_payment"));
			System.out.println("----------request--------" + new Gson().toJson(params));
			System.out.println(request.getParameter("gmt_payment" + "=====验证================================"
					+ tradeStatus + "|" + AlipayNotify.verify(params)));
			if ("TRADE_SUCCESS".equals(tradeStatus)) {
				if (AlipayNotify.verify(params)) {// 验证成功

					OrderPayEntity state = orderPayEntityMapper.selectPayStateByCode(out_trade_no);

					if (state.getPay_state() == OrderStatusEnum.PADY.value && !state.isIsdeposit()) {// 待付尾款状态
						dealOrderForDeposit(out_trade_no, new Date(), tradeNo, amount, OrderStatusEnum.ALI.value);
						response.getWriter().write("success");
					} else {
						response.getWriter().write("success");
					}
				} else {
					System.out.println("=====验证失败==================================================");
				}
			} else {// 验证失败
				System.out.println("----------request失败--------" + out_trade_no);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void dealOrder(String out_trade_no, Date time_date, String transaction_id, Double amount,
			Integer customer_pay_id) {
		try {
			System.out.println("----------dealOrder-----------------------" + out_trade_no + "------" + time_date
					+ "----------" + transaction_id + "------" + amount + "--------" + customer_pay_id);
			OrderPayEntity orderPayEntity = new OrderPayEntity();
			orderPayEntity.setGrouppay_id(out_trade_no);
			orderPayEntity.setPay_state(OrderStatusEnum.PADY.value);
			orderPayEntity.setPay_time(time_date);
			orderPayEntity.setTrade_data(transaction_id);
			orderPayEntity.setCustomer_pay_id(customer_pay_id);
			updateByGroupIdSelective(orderPayEntity);// 更新订单支付参数

			List<OrderEntity> orders = selectOrdersByGroupCode(out_trade_no);
			for (OrderEntity orderEntity : orders) {
				// 记录订单状态
				OrderStateRelationEntity record = new OrderStateRelationEntity();
				record.setOrder_code(orderEntity.getOrder_code());
				record.setOrder_state_code(OrderStatusEnum.VISTEANDMETER.value);
				record.setTime(time_date);
				insertOrderState(record);
				if (!orderEntity.getIsdeposit()) {
					// 不是定金支付的单， 记录卖家分成
					Double rate = sellerEntityMapper.selectRateByCustomerId(orderEntity.getCustomer_id());
					double money = (orderEntity.getOrder_price() + orderEntity.getDeposit_price());
					if (rate != null && rate > 0 && money > 0.01D) {

						SellerBalanceDetailEntity entity = new SellerBalanceDetailEntity();
						entity.setOrder_id(orderEntity.getId());
						double m = NumberUtil.round(money * rate, 2, BigDecimal.ROUND_HALF_UP);
						entity.setAmount(m);
						entity.setCreate_time(new Date());
						entity.setSeller_id(orderEntity.getExshopper_id());
						entity.setType(1);

						sellerBalanceDetailEntityMapper.insertSelective(entity);
					}
				}

				List<OrderProductEntity> list_p = orderProductEntityMapper.selectListByOrderId(orderEntity.getId());
				for (int k = 0; k < list_p.size(); k++) {
					salesNumEntityMapper.updateForCustmer(list_p.get(k).getProduct_num(),
							list_p.get(k).getProduct_id());
				}

				//////////////////////////// 积分和通知//////////////
				List<CustomerPointType> list = customerPointEntityMapper.selectPointType();
				CustomerPointType result = null;
				for (CustomerPointType c : list) {
					if (c.getType().equals(SysConstant.Order_Point_Id)) {
						result = c;
					}
				}

				int point = (int) (orderEntity.getOrder_price() / result.getPoint() + 0.5);
				if (point > 0) {

					CustomerPointEntity customerPointEntity = new CustomerPointEntity();
					customerPointEntity.setCustomer_id(orderEntity.getCustomer_id());
					customerPointEntity.setName(orderEntity.getOrder_code());
					customerPointEntity.setTime(new Date());
					customerPointEntity.setType(SysConstant.Order_Point_Id);
					customerPointEntity.setPoint(point);
					customerPointEntityMapper.insertSelective(customerPointEntity);

					MessageEntity messageEntity = new MessageEntity();
					messageEntity.setCustomer_id(orderEntity.getCustomer_id());
					messageEntity.setType_id(MessageConstant.PointOrder);
					messageEntity.setParams(point + "");
					messageEntity.setTime(new Date());
					messageEntityMapper.insertSelective(messageEntity);

					sendMessageServiceImpl.sendMessage(orderEntity.getCustomer_id(), point);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// SysWebscoketHandler.sendMessageToUser(sell_id, new
		// TextMessage(CharBuffer.wrap("小明购买一次商品快去看看吧..."),
		// true));
		// 保存订单积分

	}

	private void dealOrderForDeposit(String out_trade_no, Date time_date, String transaction_id, Double amount,
			Integer customer_pay_id) {
		try {
			OrderPayEntity orderPayEntity = new OrderPayEntity();
			orderPayEntity.setOrder_code(out_trade_no);
			orderPayEntity.setIsdeposit(true);
			orderPayEntity.setDeposit_time(time_date);
			orderPayEntity.setTrade_data_deposit(transaction_id);
			orderPayEntity.setDeposit_price(amount);
			orderPayEntityMapper.updateDepositByOrderCode(orderPayEntity);// 更新订单尾款支付参数

			OrderStateRelationEntity record = new OrderStateRelationEntity();
			record.setOrder_code(out_trade_no);
			record.setOrder_state_code(OrderStatusEnum.NEEDCOMMENT.value);
			record.setTime(time_date);
			insertOrderState(record);
			// SysWebscoketHandler.sendMessageToUser(sell_id, new
			// TextMessage(CharBuffer.wrap("小明购买一次商品快去看看吧..."),
			// true));
			// 保存订单积分
			List<CustomerPointType> list = customerPointEntityMapper.selectPointType();
			CustomerPointType result = null;
			for (CustomerPointType c : list) {
				if (c.getType().equals(SysConstant.Order_Point_Id)) {
					result = c;
				}
			}

			OrderEntity orderEntity = orderEntityMapper.selectOneOrdersByCode(out_trade_no);
			int point = (int) (orderEntity.getDeposit_price() / result.getPoint() + 0.5);
			if (point > 0) {

				CustomerPointEntity customerPointEntity = new CustomerPointEntity();
				customerPointEntity.setCustomer_id(orderEntity.getCustomer_id());
				customerPointEntity.setName(out_trade_no);
				customerPointEntity.setTime(time_date);
				customerPointEntity.setType(SysConstant.Order_Point_Id);
				customerPointEntity.setPoint(point);
				customerPointEntityMapper.insertSelective(customerPointEntity);

				MessageEntity messageEntity = new MessageEntity();
				messageEntity.setCustomer_id(orderEntity.getCustomer_id());
				messageEntity.setType_id(MessageConstant.PointOrder);
				messageEntity.setParams(point + "");
				messageEntity.setTime(new Date());
				messageEntityMapper.insertSelective(messageEntity);

				sendMessageServiceImpl.sendMessage(orderEntity.getCustomer_id(), point);
			}

			Double rate = sellerEntityMapper.selectRateByCustomerId(orderEntity.getCustomer_id());
			if (rate != null && rate > 0) {
				SellerBalanceDetailEntity entity = new SellerBalanceDetailEntity();
				entity.setOrder_id(orderEntity.getId());
				double m = NumberUtil.round(((orderEntity.getDeposit_price() + orderEntity.getOrder_price()) * rate), 2,
						BigDecimal.ROUND_HALF_UP);
				entity.setAmount(m);
				entity.setCreate_time(new Date());
				entity.setSeller_id(orderEntity.getExshopper_id());
				entity.setType(1);
				// 只在支付尾款后记录分成
				sellerBalanceDetailEntityMapper.insertSelective(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ResponseMessage customerAliPayOrderForCash(OrderCashEntity orderCashEntity)
			throws ClientProtocolException, IOException {

		ResponseMessage<Object> responseMessage = new ResponseMessage<Object>();

		UnionCustomer record = new UnionCustomer();
		record.setCustomer_id(orderCashEntity.getCustomer_id());
		record.setPromotion_id(orderCashEntity.getPromotion_id().intValue());
		if (unionCustomerMapper.selectWitherHasCash(record) > 0) {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage("已购买过这个折扣券");
			return responseMessage;
		}

		String orderCode = HttpRequest.sendPost(SysConstant.orderurl, null);// 订单号
		if (StringUtils.isEmpty(orderCode))
			throw new RuntimeException();// 没有请求到订单号，返回
		orderCode = orderCode.replaceAll("\"", "");

		orderCashEntity.setCode(orderCode);
		if (insertCashOrder(orderCashEntity) <= 0) {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
			return responseMessage;
		}

		if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
			orderCashEntity.setOrder_price(0.01);
		}

		AliOrderRequest ali = new AliOrderRequest();
		// String orderinfo = AliPayWorker.getOrderInfo("0.01", groupOrderId);
//		Map<String, String> parammap = new HashMap<String, String>();
//		parammap.put("partner", AlipayConfig.partner);
//		parammap.put("seller_id", AlipayConfig.seller_id);
//		parammap.put("out_trade_no", orderCode);
//		parammap.put("subject", AlipayConfig.subject);
//		parammap.put("body", AlipayConfig.body);
//		parammap.put("total_fee", orderCashEntity.getOrder_price().toString());
//		if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
//			parammap.put("notify_url", AlipayConfig.notify_cash_url_debug);
//		} else {
//			parammap.put("notify_url", AlipayConfig.notify_cash_url);
//		}
//		parammap.put("service", AlipayConfig.service);
//		parammap.put("payment_type", AlipayConfig.payment_type);
//		parammap.put("_input_charset", AlipayConfig.input_charset);
//		AlipayCore.paraFilter(parammap);
//		String orderinfo = AlipayCore.createLinkString(parammap);// andorid后台拼接

		StringBuilder result_ios = new StringBuilder();
		result_ios.append("partner=").append("\"").append(AlipayConfig.partner).append("\"").append("&");
		result_ios.append("seller_id=").append("\"").append(AlipayConfig.seller_id).append("\"").append("&");
		result_ios.append("out_trade_no=").append("\"").append(orderCode).append("\"").append("&");
		result_ios.append("subject=").append("\"").append(AlipayConfig.subject).append("\"").append("&");
		result_ios.append("body=").append("\"").append(AlipayConfig.body).append("\"").append("&");
		result_ios.append("total_fee=").append("\"").append(orderCashEntity.getOrder_price().toString()).append("\"").append("&");
		if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
			result_ios.append("notify_url=").append("\"").append(AlipayConfig.notify_cash_url_debug).append("\"").append("&");
		}else{
			result_ios.append("notify_url=").append("\"").append(AlipayConfig.notify_cash_url).append("\"").append("&");
		}
		result_ios.append("service=").append("\"").append(AlipayConfig.service).append("\"").append("&");
		result_ios.append("payment_type=").append("\"").append(AlipayConfig.payment_type).append("\"").append("&");
		result_ios.append("_input_charset=").append("\"").append(AlipayConfig.input_charset).append("\"");
		
		ali.setOrderInfo(result_ios.toString());
		
		String sign = "";
		   try {
		      sign = URLEncoder.encode(RSA.sign(result_ios.toString(), AlipayConfig.private_key, "utf-8"), "utf-8");//private_key私钥
		   } catch (UnsupportedEncodingException e) {
		      e.printStackTrace();
		   }
		   result_ios.append("&sign=\""+sign+"\"&");
		   result_ios.append("sign_type=\"RSA\"");
		
		ali.setSign(result_ios.toString());
		ali.setSign_type(AlipayConfig.sign_type);

		ali.setPartner(AlipayConfig.partner);
		ali.setSeller_id(AlipayConfig.seller_id);
		ali.setOut_trade_no(orderCode);
		ali.setSubject(AlipayConfig.subject);
		ali.setBody(AlipayConfig.body);
		ali.setTotal_fee(orderCashEntity.getOrder_price().toString());
		if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
			ali.setNotify_url(AlipayConfig.notify_cash_url_debug);
		} else {
			ali.setNotify_url(AlipayConfig.notify_cash_url);
		}
		ali.setService(AlipayConfig.service);
		ali.setPayment_type(AlipayConfig.payment_type);
		ali.set_input_charset(AlipayConfig.input_charset);
		ali.setPrivate_key(AlipayConfig.private_key);// ios前台拼接

		responseMessage.setCode("0");
		responseMessage.setMessage("success");
		responseMessage.setResult(ali);
		return responseMessage;

	}

	private long insertCashOrder(OrderCashEntity orderCashEntity) {

		OrderCashEntity o = orderCashEntityMapper.selectInfoForNewOrder(orderCashEntity.getCustomer_id(),
				orderCashEntity.getPromotion_id());
		if (o.getType().equals(1)) {
			orderCashEntity.setType(1);
		}
		orderCashEntity.setExshopper_id(o.getExshopper_id());
		orderCashEntity.setOrder_price(o.getOrder_price());
		orderCashEntity.setCreate_time(new Date());
		orderCashEntity.setPay_state(OrderStatusEnum.UNPADY.value);

		return orderCashEntityMapper.insertSelective(orderCashEntity);
	}

	public void ALIXYZnotifyForCash(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		String out_trade_no = request.getParameter("out_trade_no");// 订单号
		String tradeNo = request.getParameter("trade_no");// 支付宝交易号
		Double amount = Double.parseDouble(request.getParameter("total_fee"));// 交易金额
		String tradeStatus = request.getParameter("trade_status");// 交易状态
		String payer = request.getParameter("buyer_email");// 支付者账号
		// Date time =
		// DateUtils.parseDate_(request.getParameter("gmt_payment"));
		System.out.println("----------request--------" + new Gson().toJson(params));
		System.out.println(tradeStatus + "----------request--现金券------" + out_trade_no + "----------------"
				+ AlipayNotify.verify(params));
		if ("TRADE_SUCCESS".equals(tradeStatus)) {
			if (AlipayNotify.verify(params)) {// 验证成功

				OrderCashEntity o = orderCashEntityMapper.selectByCode(out_trade_no);
				if (o != null && o.getPay_state() == OrderStatusEnum.UNPADY.value) {// 待付款状态
					o.setPay_state(OrderStatusEnum.PADY.value);
					o.setPay_time(new Date());
					o.setCustomer_pay_id(OrderStatusEnum.ALI.value);
					o.setTrade_data(tradeNo);
					updateOrderCash(o);

					if (o.getType().equals(1)) {
						unionPromotionMapper.updateSellNumByPrimaryKey(o.getPromotion_id().intValue(), 1);
					}

					UnionCustomer unionCustomer = new UnionCustomer();
					unionCustomer.setPromotion_id(o.getPromotion_id().intValue());
					unionCustomer.setCreate_time(new Date());
					unionCustomer.setCustomer_id(o.getCustomer_id());
					unionCustomer.setState(0);
					unionCustomer.setOrder_code(out_trade_no);
					unionCustomerMapper.insertSelective(unionCustomer);

					response.getWriter().write("success");
				} else {
					response.getWriter().write("success");
				}
			} else {
				System.out.println("=====验证失败==================================================");
			}
		} else {// 验证失败
			System.out.println("----------request失败--------" + out_trade_no);
		}
	}

	public ResponseMessage customerAliPayOrderForPreShopcartPay(OrderCashNewEntity orderCashNewEntity)
			throws ClientProtocolException, IOException {

		ResponseMessage<Object> responseMessage = new ResponseMessage<Object>();
		String orderCode = HttpRequest.sendPost(SysConstant.orderurl, null);// 订单号
		if (StringUtils.isEmpty(orderCode))
			throw new RuntimeException();// 没有请求到订单号，返回
		orderCode = orderCode.replaceAll("\"", "");

		orderCashNewEntity.setCode(orderCode);

		orderCashNewEntity = insertCashOrderNew(orderCashNewEntity);
		if (orderCashNewEntity == null) {// 保存初始订单信息
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setCode(SysConstant.FAILURE_MSG);
			return responseMessage;
		}

		if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
			orderCashNewEntity.setOrder_price(0.01);
		}

		System.out.println("-----orderCashNewEntity--------------------------------------"
				+ orderCashNewEntity.getOrder_price().toString());
		AliOrderRequest ali = new AliOrderRequest();
		// String orderinfo = AliPayWorker.getOrderInfo("0.01", groupOrderId);
//		Map<String, String> parammap = new HashMap<String, String>();
//		parammap.put("partner", AlipayConfig.partner);
//		parammap.put("seller_id", AlipayConfig.seller_id);
//		parammap.put("out_trade_no", orderCode);
//		parammap.put("subject", AlipayConfig.subject);
//		parammap.put("body", AlipayConfig.body);
//		parammap.put("total_fee", orderCashNewEntity.getOrder_price().toString());
//		if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
//			parammap.put("notify_url", AlipayConfig.notify_cash_new_url_debug);
//		} else {
//			parammap.put("notify_url", AlipayConfig.notify_cash_new_url);
//		}
//		parammap.put("service", AlipayConfig.service);
//		parammap.put("payment_type", AlipayConfig.payment_type);
//		parammap.put("_input_charset", AlipayConfig.input_charset);
//		AlipayCore.paraFilter(parammap);
//		String orderinfo = AlipayCore.createLinkString(parammap);// andorid后台拼接

		StringBuilder result_ios = new StringBuilder();
		result_ios.append("partner=").append("\"").append(AlipayConfig.partner).append("\"").append("&");
		result_ios.append("seller_id=").append("\"").append(AlipayConfig.seller_id).append("\"").append("&");
		result_ios.append("out_trade_no=").append("\"").append(orderCode).append("\"").append("&");
		result_ios.append("subject=").append("\"").append(AlipayConfig.subject).append("\"").append("&");
		result_ios.append("body=").append("\"").append(AlipayConfig.body).append("\"").append("&");
		result_ios.append("total_fee=").append("\"").append(orderCashNewEntity.getOrder_price().toString()).append("\"").append("&");
		if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
			result_ios.append("notify_url=").append("\"").append(AlipayConfig.notify_cash_new_url_debug).append("\"").append("&");
		}else{
			result_ios.append("notify_url=").append("\"").append(AlipayConfig.notify_cash_new_url).append("\"").append("&");
		}
		result_ios.append("service=").append("\"").append(AlipayConfig.service).append("\"").append("&");
		result_ios.append("payment_type=").append("\"").append(AlipayConfig.payment_type).append("\"").append("&");
		result_ios.append("_input_charset=").append("\"").append(AlipayConfig.input_charset).append("\"");
		
		ali.setOrderInfo(result_ios.toString());
		
		String sign = "";
		   try {
		      sign = URLEncoder.encode(RSA.sign(result_ios.toString(), AlipayConfig.private_key, "utf-8"), "utf-8");//private_key私钥
		   } catch (UnsupportedEncodingException e) {
		      e.printStackTrace();
		   }
		   result_ios.append("&sign=\""+sign+"\"&");
		   result_ios.append("sign_type=\"RSA\"");
		
		ali.setSign(result_ios.toString());
		ali.setSign_type(AlipayConfig.sign_type);

		ali.setPartner(AlipayConfig.partner);
		ali.setSeller_id(AlipayConfig.seller_id);
		ali.setOut_trade_no(orderCode);
		ali.setSubject(AlipayConfig.subject);
		ali.setBody(AlipayConfig.body);
		ali.setTotal_fee(orderCashNewEntity.getOrder_price().toString());
		if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
			ali.setNotify_url(AlipayConfig.notify_cash_new_url_debug);
		} else {
			ali.setNotify_url(AlipayConfig.notify_cash_new_url);
		}
		ali.setService(AlipayConfig.service);
		ali.setPayment_type(AlipayConfig.payment_type);
		ali.set_input_charset(AlipayConfig.input_charset);
		ali.setPrivate_key(AlipayConfig.private_key);// ios前台拼接

		responseMessage.setCode("0");
		responseMessage.setMessage("success");
		responseMessage.setResult(ali);
		return responseMessage;

	}

	public ResponseMessage customerAliPayOrderForConstruct(ConstructAppointment param)
			throws ClientProtocolException, IOException {

		ResponseMessage<Object> responseMessage = new ResponseMessage<Object>();
		String orderCode = HttpRequest.sendPost(SysConstant.orderurl, null);// 订单号
		if (StringUtils.isEmpty(orderCode))
			throw new RuntimeException();// 没有请求到订单号，返回
		orderCode = orderCode.replaceAll("\"", "");

		ConstructAppointment appointment = constructAppointmentMapper.selectByPrimaryKey(param.getId());
		CustomerEntity customerEntity = customerEntityMapper.selectByPrimaryKey(appointment.getCustomer_id());
		OrderCashNewEntity orderCashNewEntity = new OrderCashNewEntity();
		orderCashNewEntity.setCode(orderCode);
		orderCashNewEntity.setCustomer_id(appointment.getConstruct_id());
		orderCashNewEntity.setExshopper_id(customerEntity.getExshopper_id());
		orderCashNewEntity.setOrder_price(appointment.getConstruct_data());
		orderCashNewEntity.setCreate_time(new Date());
		orderCashNewEntity.setPay_state(OrderStatusEnum.UNPADY.value);
		orderCashNewEntity.setType(OrderStatusEnum.CONSTRUCT.value);
		orderCashNewEntity.setType_id(param.getId());
		int result = orderCashNewEntityMapper.insertSelective(orderCashNewEntity);

		if (result <= 0) {// 保存初始订单信息
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setCode(SysConstant.FAILURE_MSG);
			return responseMessage;
		}

		if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
			orderCashNewEntity.setOrder_price(0.01);
		}

		System.out.println("-----orderCashNewEntity--------------------------------------"
				+ orderCashNewEntity.getOrder_price().toString());
		AliOrderRequest ali = new AliOrderRequest();
		// String orderinfo = AliPayWorker.getOrderInfo("0.01", groupOrderId);
//		Map<String, String> parammap = new HashMap<String, String>();
//		parammap.put("partner", AlipayConfig.partner);
//		parammap.put("seller_id", AlipayConfig.seller_id);
//		parammap.put("out_trade_no", orderCode);
//		parammap.put("subject", AlipayConfig.subject);
//		parammap.put("body", AlipayConfig.body);
//		parammap.put("total_fee", orderCashNewEntity.getOrder_price().toString());
//		if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
//			parammap.put("notify_url", AlipayConfig.notify_constract_url_debug);
//		} else {
//			parammap.put("notify_url", AlipayConfig.notify_constract_url);
//		}
//		parammap.put("service", AlipayConfig.service);
//		parammap.put("payment_type", AlipayConfig.payment_type);
//		parammap.put("_input_charset", AlipayConfig.input_charset);
//		AlipayCore.paraFilter(parammap);
//		String orderinfo = AlipayCore.createLinkString(parammap);// andorid后台拼接

		StringBuilder result_ios = new StringBuilder();
		result_ios.append("partner=").append("\"").append(AlipayConfig.partner).append("\"").append("&");
		result_ios.append("seller_id=").append("\"").append(AlipayConfig.seller_id).append("\"").append("&");
		result_ios.append("out_trade_no=").append("\"").append(orderCode).append("\"").append("&");
		result_ios.append("subject=").append("\"").append(AlipayConfig.subject).append("\"").append("&");
		result_ios.append("body=").append("\"").append(AlipayConfig.body).append("\"").append("&");
		result_ios.append("total_fee=").append("\"").append(orderCashNewEntity.getOrder_price().toString()).append("\"").append("&");
		if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
			result_ios.append("notify_url=").append("\"").append(AlipayConfig.notify_constract_url_debug).append("\"").append("&");
		}else{
			result_ios.append("notify_url=").append("\"").append(AlipayConfig.notify_constract_url).append("\"").append("&");
		}
		result_ios.append("service=").append("\"").append(AlipayConfig.service).append("\"").append("&");
		result_ios.append("payment_type=").append("\"").append(AlipayConfig.payment_type).append("\"").append("&");
		result_ios.append("_input_charset=").append("\"").append(AlipayConfig.input_charset).append("\"");
		
		ali.setOrderInfo(result_ios.toString());
		
		String sign = "";
		   try {
		      sign = URLEncoder.encode(RSA.sign(result_ios.toString(), AlipayConfig.private_key, "utf-8"), "utf-8");//private_key私钥
		   } catch (UnsupportedEncodingException e) {
		      e.printStackTrace();
		   }
		   result_ios.append("&sign=\""+sign+"\"&");
		   result_ios.append("sign_type=\"RSA\"");
		
		ali.setSign(result_ios.toString());
		ali.setSign_type(AlipayConfig.sign_type);

		ali.setPartner(AlipayConfig.partner);
		ali.setSeller_id(AlipayConfig.seller_id);
		ali.setOut_trade_no(orderCode);
		ali.setSubject(AlipayConfig.subject);
		ali.setBody(AlipayConfig.body);
		ali.setTotal_fee(orderCashNewEntity.getOrder_price().toString());
		if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
			ali.setNotify_url(AlipayConfig.notify_constract_url_debug);
		} else {
			ali.setNotify_url(AlipayConfig.notify_constract_url);
		}
		ali.setService(AlipayConfig.service);
		ali.setPayment_type(AlipayConfig.payment_type);
		ali.set_input_charset(AlipayConfig.input_charset);
		ali.setPrivate_key(AlipayConfig.private_key);// ios前台拼接

		responseMessage.setCode("0");
		responseMessage.setMessage("success");
		responseMessage.setResult(ali);
		return responseMessage;

	}

	public synchronized ResponseMessage customerAliPayOrderForCoupons(BrandCouponsModel param)
			throws ClientProtocolException, IOException {

		ResponseMessage<Object> responseMessage = new ResponseMessage<Object>();
		String orderCode = HttpRequest.sendPost(SysConstant.orderurl, null);// 订单号
		if (StringUtils.isEmpty(orderCode))
			throw new RuntimeException();// 没有请求到订单号，返回
		orderCode = orderCode.replaceAll("\"", "");

		// if(customerCouponsEntityMapper.selectCustomerWhetherGainCoupons(param)
		// >0){
		// responseMessage.setCode(SysConstant.FAILURE_CODE);
		// responseMessage.setCode("不可以重复购买");
		// return responseMessage;
		// }

		BaseBrandCouponsEntity coupons = brandCouponsEntityMapper.selectByPrimaryKey(param.getCoupons_id());

		if (coupons == null) {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
			return responseMessage;
		}

		if (coupons.getSurplus_count() < 1) {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setCode("剩余数不足");
			return responseMessage;
		}

		CustomerEntity customerEntity = customerEntityMapper.selectByPrimaryKey(param.getCustomer_id());

		if (customerEntity == null) {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
			return responseMessage;
		}

		OrderCashNewEntity orderCashNewEntity = new OrderCashNewEntity();
		orderCashNewEntity.setCode(orderCode);
		orderCashNewEntity.setCustomer_id(param.getCustomer_id());
		orderCashNewEntity.setExshopper_id(customerEntity.getExshopper_id());
		orderCashNewEntity.setOrder_price(param.getCoupons_price());
		orderCashNewEntity.setCreate_time(new Date());
		orderCashNewEntity.setPay_state(OrderStatusEnum.UNPADY.value);
		orderCashNewEntity.setType(OrderStatusEnum.CONPONS.value);
		orderCashNewEntity.setType_id(param.getCoupons_id());

		int result = orderCashNewEntityMapper.insertSelective(orderCashNewEntity);

		if (result <= 0) {// 保存初始订单信息
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setCode(SysConstant.FAILURE_MSG);
			return responseMessage;
		}

		if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
			orderCashNewEntity.setOrder_price(0.01);
		}

		AliOrderRequest ali = new AliOrderRequest();
		// String orderinfo = AliPayWorker.getOrderInfo("0.01", groupOrderId);
//		Map<String, String> parammap = new HashMap<String, String>();
//		parammap.put("partner", AlipayConfig.partner);
//		parammap.put("seller_id", AlipayConfig.seller_id);
//		parammap.put("out_trade_no", orderCode);
//		parammap.put("subject", AlipayConfig.subject);
//		parammap.put("body", AlipayConfig.body);
//		parammap.put("total_fee", orderCashNewEntity.getOrder_price().toString());
//		if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
//			parammap.put("notify_url", AlipayConfig.notify_coupons_url_debug);
//		} else {
//			parammap.put("notify_url", AlipayConfig.notify_coupons_url);
//		}
//		parammap.put("service", AlipayConfig.service);
//		parammap.put("payment_type", AlipayConfig.payment_type);
//		parammap.put("_input_charset", AlipayConfig.input_charset);
//		parammap = AlipayCore.paraFilter(parammap);
//		String orderinfo = AlipayCore.createLinkString(parammap);// andorid后台拼接
		
		StringBuilder result_ios = new StringBuilder();
		result_ios.append("partner=").append("\"").append(AlipayConfig.partner).append("\"").append("&");
		result_ios.append("seller_id=").append("\"").append(AlipayConfig.seller_id).append("\"").append("&");
		result_ios.append("out_trade_no=").append("\"").append(orderCode).append("\"").append("&");
		result_ios.append("subject=").append("\"").append(AlipayConfig.subject).append("\"").append("&");
		result_ios.append("body=").append("\"").append(AlipayConfig.body).append("\"").append("&");
		result_ios.append("total_fee=").append("\"").append(orderCashNewEntity.getOrder_price().toString()).append("\"").append("&");
		if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
			result_ios.append("notify_url=").append("\"").append(AlipayConfig.notify_coupons_url_debug).append("\"").append("&");
		}else{
			result_ios.append("notify_url=").append("\"").append(AlipayConfig.notify_coupons_url).append("\"").append("&");
		}
		result_ios.append("service=").append("\"").append(AlipayConfig.service).append("\"").append("&");
		result_ios.append("payment_type=").append("\"").append(AlipayConfig.payment_type).append("\"").append("&");
		result_ios.append("_input_charset=").append("\"").append(AlipayConfig.input_charset).append("\"");
		
		ali.setOrderInfo(result_ios.toString());
		
		String sign = "";
		   try {
		      sign = URLEncoder.encode(RSA.sign(result_ios.toString(), AlipayConfig.private_key, "utf-8"), "utf-8");//private_key私钥
		   } catch (UnsupportedEncodingException e) {
		      e.printStackTrace();
		   }
		   result_ios.append("&sign=\""+sign+"\"&");
		   result_ios.append("sign_type=\"RSA\"");
		
		ali.setSign(result_ios.toString());
		ali.setSign_type(AlipayConfig.sign_type);
		ali.setPartner(AlipayConfig.partner);
		ali.setSeller_id(AlipayConfig.seller_id);
		ali.setOut_trade_no(orderCode);
		ali.setSubject(AlipayConfig.subject);
		ali.setBody(AlipayConfig.body);
		ali.setTotal_fee(orderCashNewEntity.getOrder_price().toString());
		if (PropUtis.getValue(SysConstant.DEBUG).equals("true")) {
			ali.setNotify_url(AlipayConfig.notify_coupons_url_debug);
		} else {
			ali.setNotify_url(AlipayConfig.notify_coupons_url);
		}
		ali.setService(AlipayConfig.service);
		ali.setPayment_type(AlipayConfig.payment_type);
		ali.set_input_charset(AlipayConfig.input_charset);
		ali.setPrivate_key(AlipayConfig.private_key);// ios前台拼接

		responseMessage.setCode("0");
		responseMessage.setMessage("success");
		responseMessage.setResult(ali);
		return responseMessage;

	}

	private OrderCashNewEntity insertCashOrderNew(OrderCashNewEntity orderCashNewEntity) {

		// 需要 Customer_id type Type_id shop_id
		OrderCashNewEntity o = orderCashNewEntityMapper.selectInfoForNewOrder(orderCashNewEntity.getCustomer_id(),
				orderCashNewEntity.getType_id());

		System.out.println("buyCash" + orderCashNewEntity.getType_id() + "---------------" + o.getOrder_price());
		orderCashNewEntity.setExshopper_id(o.getExshopper_id());
		orderCashNewEntity.setOrder_price(o.getOrder_price());
		orderCashNewEntity.setCreate_time(new Date());
		orderCashNewEntity.setPay_state(OrderStatusEnum.UNPADY.value);
		orderCashNewEntity.setType(OrderStatusEnum.SHOPCASH.value);
		int result = orderCashNewEntityMapper.insertSelective(orderCashNewEntity);

		if (result <= 0) {
			return null;
		} else {
			return orderCashNewEntity;
		}
	}

	public void ALIXYZnotifyForPreShopcartPay(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		String out_trade_no = request.getParameter("out_trade_no");// 订单号
		String tradeNo = request.getParameter("trade_no");// 支付宝交易号
		Double amount = Double.parseDouble(request.getParameter("total_fee"));// 交易金额
		String tradeStatus = request.getParameter("trade_status");// 交易状态
		String payer = request.getParameter("buyer_email");// 支付者账号
		// Date time =
		// DateUtils.parseDate_(request.getParameter("gmt_payment"));
		System.out.println("----------request--------" + new Gson().toJson(params));
		System.out.println(tradeStatus + "----------request--现金券------" + out_trade_no + "----------------"
				+ AlipayNotify.verify(params));
		if ("TRADE_SUCCESS".equals(tradeStatus)) {
			if (AlipayNotify.verify(params)) {// 验证成功

				OrderCashNewEntity o = orderCashNewEntityMapper.selectByCode(out_trade_no);
				if (o != null && o.getPay_state().equals(OrderStatusEnum.UNPADY.value)) {// 待付款状态
					o.setPay_state(OrderStatusEnum.PADY.value);
					o.setPay_time(new Date());
					o.setCustomer_pay_id(OrderStatusEnum.ALI.value);
					o.setTrade_data(tradeNo);
					updateOrderCashNew(o);

					ShopPrepayEntity shopPrepayEntity = new ShopPrepayEntity();
					shopPrepayEntity.setCustomer_id(o.getCustomer_id());
					shopPrepayEntity.setValue(o.getOrder_price().intValue());
					System.out.println(o.getOrder_price() + " ==================amount================================="
							+ amount.intValue());
					shopPrepayEntity.setShop_id(o.getShop_id());
					shopPrepayEntity.setCreate_time(new Date());
					shopPrepayEntity.setState(0);
					shopPrepayEntity.setOrder_code(out_trade_no);

					shopPrepayEntityMapper.insertSelective(shopPrepayEntity);

					response.getWriter().write("success");
				} else {
					response.getWriter().write("success");
				}
			} else {
				System.out.println("=====验证失败==================================================");
			}
		} else {// 验证失败
			System.out.println("----------request失败--------" + out_trade_no);
		}
	}

	public void ALIXYZnotifyForConstruct(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		String out_trade_no = request.getParameter("out_trade_no");// 订单号
		String tradeNo = request.getParameter("trade_no");// 支付宝交易号
		Double amount = Double.parseDouble(request.getParameter("total_fee"));// 交易金额
		String tradeStatus = request.getParameter("trade_status");// 交易状态
		String payer = request.getParameter("buyer_email");// 支付者账号
		// Date time =
		// DateUtils.parseDate_(request.getParameter("gmt_payment"));
		System.out.println("----------request--------" + new Gson().toJson(params));
		System.out.println(tradeStatus + "----------request--现金券------" + out_trade_no + "----------------"
				+ AlipayNotify.verify(params));
		if ("TRADE_SUCCESS".equals(tradeStatus)) {
			if (AlipayNotify.verify(params)) {// 验证成功

				OrderCashNewEntity o = orderCashNewEntityMapper.selectByCode(out_trade_no);
				if (o != null && o.getPay_state().equals(OrderStatusEnum.UNPADY.value)) {// 待付款状态
					o.setPay_state(OrderStatusEnum.PADY.value);
					o.setPay_time(new Date());
					o.setCustomer_pay_id(OrderStatusEnum.ALI.value);
					o.setTrade_data(tradeNo);
					updateOrderCashNew(o);

					ConstructStateRelation constructStateRelation = new ConstructStateRelation();
					constructStateRelation.setConstruct_appointment_id(o.getType_id());
					constructStateRelation.setCreate_time(new Date());
					constructStateRelation.setState_code(ConstructAppointmentEnum.NEEDCOMMENT.code);
					constructStateRelationMapper.insertSelective(constructStateRelation);

					response.getWriter().write("success");
				} else {
					response.getWriter().write("success");
				}
			} else {
				System.out.println("=====验证失败==================================================");
			}
		} else {// 验证失败
			System.out.println("----------request失败--------" + out_trade_no);
		}
	}

	public void ALIXYZnotifyForCoupons(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		String out_trade_no = request.getParameter("out_trade_no");// 订单号
		String tradeNo = request.getParameter("trade_no");// 支付宝交易号
		Double amount = Double.parseDouble(request.getParameter("total_fee"));// 交易金额
		String tradeStatus = request.getParameter("trade_status");// 交易状态
		String payer = request.getParameter("buyer_email");// 支付者账号
		// Date time =
		// DateUtils.parseDate_(request.getParameter("gmt_payment"));
		System.out.println("----------request--------" + new Gson().toJson(params));
		System.out.println(tradeStatus + "----------request--现金券------" + out_trade_no + "----------------"
				+ AlipayNotify.verify(params));
		if ("TRADE_SUCCESS".equals(tradeStatus)) {
			if (AlipayNotify.verify(params)) {// 验证成功

				OrderCashNewEntity o = orderCashNewEntityMapper.selectByCode(out_trade_no);
				if (o != null && o.getPay_state().equals(OrderStatusEnum.UNPADY.value)) {// 待付款状态
					o.setPay_state(OrderStatusEnum.PADY.value);
					o.setPay_time(new Date());
					o.setCustomer_pay_id(OrderStatusEnum.ALI.value);
					o.setTrade_data(tradeNo);
					updateOrderCashNew(o);

					BaseCustomerCouponsEntity record = new BaseCustomerCouponsEntity();
					record.setCoupons_id(o.getType_id());
					record.setCreate_time(new Date());
					record.setCustomer_id(o.getCustomer_id());
					record.setIsused(false);
					record.setOrder_code(out_trade_no);

					CustomerEntity customerEntity = customerEntityMapper.selectByPrimaryKey(o.getCustomer_id());
					if (customerEntity != null) {
						StringBuffer convert_code = new StringBuffer();
						convert_code.append(customerEntity.getAccount().substring(0, 3));
						convert_code.append(UUID.randomUUID().toString().toUpperCase().substring(0, 8));
						convert_code.append(customerEntity.getAccount().substring(7, 11));
						record.setConvert_code(convert_code.toString());
					}
					baseCustomerCouponsEntityMapper.insertSelective(record);

					brandCouponsEntityMapper.deleteSurplusById(1, o.getType_id());

					response.getWriter().write("success");
				} else {
					response.getWriter().write("success");
				}
			} else {
				System.out.println("=====验证失败==================================================");
			}
		} else {// 验证失败
			System.out.println("----------request失败--------" + out_trade_no);
		}
	}

	private void updateOrderCash(OrderCashEntity record) {
		// 添加消息
		orderCashEntityMapper.updateByPrimaryKeySelective(record);

	}

	private void updateOrderCashNew(OrderCashNewEntity record) {
		// 添加消息
		orderCashNewEntityMapper.updateByPrimaryKeySelective(record);

	}
}
