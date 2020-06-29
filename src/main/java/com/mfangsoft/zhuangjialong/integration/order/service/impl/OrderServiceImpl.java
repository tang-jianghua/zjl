package com.mfangsoft.zhuangjialong.integration.order.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.order.mapper.OrderCouponsEntityMapper;
import com.mfangsoft.zhuangjialong.app.order.mapper.OrderEntityMapper;
import com.mfangsoft.zhuangjialong.app.order.mapper.OrderStateRelationEntityMapper;
import com.mfangsoft.zhuangjialong.app.order.model.OrderCouponsEntity;
import com.mfangsoft.zhuangjialong.app.order.model.OrderEntity;
import com.mfangsoft.zhuangjialong.app.order.model.OrderStateRelationEntity;
import com.mfangsoft.zhuangjialong.app.personalCenter.mapper.PrepaymentEntityMapper;
import com.mfangsoft.zhuangjialong.app.prepay.mapper.ShopPrepayEntityMapper;
import com.mfangsoft.zhuangjialong.app.prepay.model.ShopPrepayEntity;
import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.NumberUtil;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;
import com.mfangsoft.zhuangjialong.integration.coupons.mapper.BrandCouponsEntityMapper;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseEntity;
import com.mfangsoft.zhuangjialong.integration.enums.UserType;
import com.mfangsoft.zhuangjialong.integration.newcoupons.mapper.BaseCustomerCouponsEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newcoupons.model.BaseBrandCouponsEntity;
import com.mfangsoft.zhuangjialong.integration.newcoupons.model.BaseCustomerCouponsEntity;
import com.mfangsoft.zhuangjialong.integration.order.mapper.OrderShellInfoEntityMapper;
import com.mfangsoft.zhuangjialong.integration.order.mapper.OrderShellListEntityMapper;
import com.mfangsoft.zhuangjialong.integration.order.mapper.OrderShellStateRelationEntityMapper;
import com.mfangsoft.zhuangjialong.integration.order.model.OrderPreferential;
import com.mfangsoft.zhuangjialong.integration.order.model.OrderShellInfoEntity;
import com.mfangsoft.zhuangjialong.integration.order.model.OrderShellListEntity;
import com.mfangsoft.zhuangjialong.integration.order.model.OrderShellStateRelationEntity;
import com.mfangsoft.zhuangjialong.integration.order.service.OrderService;
import com.mfangsoft.zhuangjialong.integration.order.util.ShellorderstateEnum;
import com.mfangsoft.zhuangjialong.integration.partner.model.PartnerEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.UnionPromotionMapper;
import com.mfangsoft.zhuangjialong.integration.seller.model.Order;
import com.mfangsoft.zhuangjialong.integration.seller.service.impl.SellerServiceImpl;
import com.mfangsoft.zhuangjialong.integration.shop.mapper.ShopEntityMapper;
import com.mfangsoft.zhuangjialong.integration.shop.model.ShopEntity;
import com.mfangsoft.zhuangjialong.integration.user.model.UserEntity;

@Service("order")
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderEntityMapper orderEntityMapper;
	@Autowired
	private OrderStateRelationEntityMapper orderStateRelationEntityMapper;
	@Autowired
	private BrandCouponsEntityMapper brandCouponsEntityMapper;
	@Autowired
	private BaseCustomerCouponsEntityMapper baseCustomerCouponsEntityMapper;

	@Autowired
	private OrderCouponsEntityMapper orderCouponsEntityMapper;
	@Autowired
	private UnionPromotionMapper unionPromotionMapper;

	@Autowired
	private PrepaymentEntityMapper prepaymentEntityMapper;

	@Autowired
	private ShopPrepayEntityMapper shopPrepayEntityMapper;

	@Autowired
	private com.mfangsoft.zhuangjialong.app.order.service.OrderService orderService;

	@Autowired
	OrderShellListEntityMapper orderShellListEntityMapper;
	@Autowired
	OrderShellInfoEntityMapper orderShellInfoEntityMapper;
	@Autowired
	OrderShellStateRelationEntityMapper orderShellStateRelationEntityMapper;
	@Autowired
	SellerServiceImpl sellerServiceImpl;
	@Autowired
	ShopEntityMapper shopEntityMapper;

	@Override
	public Page<Map<String, Object>> getOrderListForPage(Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub

		Map<String, Object> map;

		UserEntity user = UserContext.getCurrentUser();

		if (page.getParam() != null) {
			map = (Map<String, Object>) page.getParam();

		} else {

			map = new HashMap<>();
			page.setParam(map);

		}
		if (user.getUser_type() == UserType.SHOP.getIndex()) {

			ShopEntity shopEntity = (ShopEntity) UserContext.getCurrentUserInfo();
			map.put("shop_id", shopEntity.getId());
		} else if (user.getUser_type() == UserType.PARTNER.getIndex()) {

			PartnerEntity partnerEntity = (PartnerEntity) UserContext.getCurrentUserInfo();

			map.put("partner", partnerEntity.getId());

		} else if (user.getUser_type() == UserType.ENTERPRISE.getIndex()) {

			EnterpriseEntity enterpriseEntity = (EnterpriseEntity) UserContext.getCurrentUserInfo();

			map.put("ent_id", enterpriseEntity.getId());

		} else if (user.getUser_type() == UserType.BRAND.getIndex()) {

			BrandEntity brandEntity = (BrandEntity) UserContext.getCurrentUserInfo();
			map.put("brand", brandEntity.getId());
		}

		List<Map<String, Object>> list = orderEntityMapper.getOrderListForPage(page);

		for (Map<String, Object> map2 : list) {

			String s = this.getOrderPrefer(new Long(map2.get("id").toString()));
			map2.put("cash_info", s);

		}

		page.setData(list);

		return page;
	}

	private List<Map<String, Object>> getOrderProduct(List<Long> list) {

		return null;

	}

	public String getOrderPrefer(Long order_id) {

		OrderPreferential orderPreferential = new OrderPreferential();
		OrderCouponsEntity orderCouponsEntity = orderCouponsEntityMapper.selectOrderCouponsByOrderId(order_id);

		if (orderCouponsEntity.getCoupons_id() != null) {

			BaseCustomerCouponsEntity customerCouponsEntity = baseCustomerCouponsEntityMapper
					.selectByPrimaryKey(orderCouponsEntity.getCoupons_id());

			if (customerCouponsEntity != null) {

				BaseBrandCouponsEntity couponsEntity = brandCouponsEntityMapper
						.selectByPrimaryKey(customerCouponsEntity.getCoupons_id());
				if (couponsEntity != null) {
					orderPreferential.setCouponsName(couponsEntity.getName());
					orderPreferential.setCouponsValue(couponsEntity.getValue());
				}
			}
		}

		if (orderCouponsEntity.getRedbag_id() != null) {

			BaseCustomerCouponsEntity customerCouponsEntity = baseCustomerCouponsEntityMapper
					.selectByPrimaryKey(orderCouponsEntity.getRedbag_id());
			if (customerCouponsEntity != null) {

				BaseBrandCouponsEntity couponsEntity = brandCouponsEntityMapper
						.selectByPrimaryKey(customerCouponsEntity.getCoupons_id());
				if (couponsEntity != null) {
					orderPreferential.setRadBagName(couponsEntity.getName());
					orderPreferential.setRadBagValue(couponsEntity.getValue());
				}
			}
		}

		if (orderCouponsEntity.getZhekou_id() != null) {

			BaseCustomerCouponsEntity customerCouponsEntity = baseCustomerCouponsEntityMapper
					.selectByPrimaryKey(orderCouponsEntity.getZhekou_id());
			if (customerCouponsEntity != null) {

				BaseBrandCouponsEntity couponsEntity = brandCouponsEntityMapper
						.selectByPrimaryKey(customerCouponsEntity.getCoupons_id());
				if (couponsEntity != null) {
					orderPreferential.setRadBagName(couponsEntity.getName());
					orderPreferential.setRadBagValue(couponsEntity.getValue());
				}
			}
		}

		if (StringUtils.isNotEmpty(orderCouponsEntity.getPromotion_id())) {
			orderPreferential
					.setUnionCouponslist(unionPromotionMapper.selectUnionCon(orderCouponsEntity.getPromotion_id()));
		}

		if (orderCouponsEntity.getPershop_id() != null) {

			ShopPrepayEntity prepaymentEntity = shopPrepayEntityMapper
					.selectByPrimaryKey(orderCouponsEntity.getPershop_id().intValue());

			orderPreferential.setShopCouponsName("定金卷");
			orderPreferential.setShopCouponsValue(new Double(prepaymentEntity.getValue()));
		}

		return orderPreferential.toString();

	}

	@Override
	public Map<String, Object> getOrderDetails(Long id) {
		// TODO Auto-generated method stub
		return orderEntityMapper.getOrderdetails(id);
	}

	@Override
	public Boolean insertOrderState(OrderStateRelationEntity orderStateRelationEntity) {
		// TODO Auto-generated method stub

		OrderEntity record = orderEntityMapper.selectByPrimaryKey(orderStateRelationEntity.getOrder_id());

		orderStateRelationEntity.setOrder_code(record.getOrder_code());

		orderStateRelationEntity.setTime(new Date());
		if (orderService.insertOrderState(orderStateRelationEntity) > 0) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public Page<Map<String, Object>> selectOrdercashcouponList(Page<Map<String, Object>> page) {

		UserEntity userEntity = UserContext.getCurrentUser();

		if (userEntity.getUser_type() == UserType.PARTNER.getIndex()) {

			Map<String, Object> map = (Map<String, Object>) page.getParam();

			PartnerEntity entity = (PartnerEntity) UserContext.getCurrentUserInfo();

			map.put("partner", entity.getId());
		}

		if (userEntity.getUser_type() == UserType.ENTERPRISE.getIndex()) {

			Map<String, Object> map = (Map<String, Object>) page.getParam();

			EnterpriseEntity entity = (EnterpriseEntity) UserContext.getCurrentUserInfo();

			map.put("enterprise_id", entity.getId());
		}

		page.setData(orderEntityMapper.selectOrdercashcouponListForPage(page));

		return page;
	}

	@Override
	public Page<Map<String, Object>> selectOrdercashcouponNewList(Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub
		UserEntity userEntity = UserContext.getCurrentUser();

		if (userEntity.getUser_type() == UserType.BRAND.getIndex()) {

			Map<String, Object> map = (Map<String, Object>) page.getParam();

			BrandEntity brandEntity = (BrandEntity) UserContext.getCurrentUserInfo();

			map.put("brand_id", brandEntity.getId());

		}

		if (userEntity.getUser_type() == UserType.PARTNER.getIndex()) {

			Map<String, Object> map = (Map<String, Object>) page.getParam();

			PartnerEntity brandEntity = (PartnerEntity) UserContext.getCurrentUserInfo();

			map.put("citypartner_id", brandEntity.getId());

		}

		if (userEntity.getUser_type() == UserType.ENTERPRISE.getIndex()) {

			Map<String, Object> map = (Map<String, Object>) page.getParam();

			EnterpriseEntity brandEntity = (EnterpriseEntity) UserContext.getCurrentUserInfo();

			map.put("enterprise_id", brandEntity.getId());

		}

		if (userEntity.getUser_type() == UserType.SHOP.getIndex()) {

			Map<String, Object> map = (Map<String, Object>) page.getParam();

			ShopEntity brandEntity = (ShopEntity) UserContext.getCurrentUserInfo();

			map.put("shop_id", brandEntity.getId());

		}

		page.setData(orderEntityMapper.selectOrdercashcouponNewListForPage(page));

		return page;
	}

	@Override
	public List<Map<String, Object>> selectorderTotal() {
		// TODO Auto-generated method stub

		Map<String, Object> map = new HashMap<>();
		UserEntity user = UserContext.getCurrentUser();

		if (user.getUser_type() == UserType.SHOP.getIndex()) {

			ShopEntity shopEntity = (ShopEntity) UserContext.getCurrentUserInfo();
			map.put("shop_id", shopEntity.getId());
		} else if (user.getUser_type() == UserType.PARTNER.getIndex()) {

			PartnerEntity partnerEntity = (PartnerEntity) UserContext.getCurrentUserInfo();

			map.put("partner_id", partnerEntity.getId());

		} else if (user.getUser_type() == UserType.ENTERPRISE.getIndex()) {

			EnterpriseEntity enterpriseEntity = (EnterpriseEntity) UserContext.getCurrentUserInfo();

			map.put("ent_id", enterpriseEntity.getId());

		} else if (user.getUser_type() == UserType.BRAND.getIndex()) {

			BrandEntity brandEntity = (BrandEntity) UserContext.getCurrentUserInfo();
			map.put("brand_id", brandEntity.getId());
		}
		return orderEntityMapper.selectorderTotal(map);
	}

	@Override
	public Page<Map<String, Object>> getOrderNewListForPage(Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub

		Map<String, Object> map;

		UserEntity user = UserContext.getCurrentUser();

		if (page.getParam() != null) {
			map = (Map<String, Object>) page.getParam();

		} else {

			map = new HashMap<>();
			page.setParam(map);

		}
		if (user.getUser_type() == UserType.SHOP.getIndex()) {

			ShopEntity shopEntity = (ShopEntity) UserContext.getCurrentUserInfo();
			map.put("shop_id", shopEntity.getId());
		} else if (user.getUser_type() == UserType.PARTNER.getIndex()) {

			PartnerEntity partnerEntity = (PartnerEntity) UserContext.getCurrentUserInfo();

			map.put("partner", partnerEntity.getId());

		} else if (user.getUser_type() == UserType.ENTERPRISE.getIndex()) {

			EnterpriseEntity enterpriseEntity = (EnterpriseEntity) UserContext.getCurrentUserInfo();

			map.put("ent_id", enterpriseEntity.getId());

		} else if (user.getUser_type() == UserType.BRAND.getIndex()) {

			BrandEntity brandEntity = (BrandEntity) UserContext.getCurrentUserInfo();
			map.put("brand", brandEntity.getId());
		}

		List<Map<String, Object>> list = orderEntityMapper.selectOrderForPage(page);

		for (Map<String, Object> map2 : list) {

			String s = this.getOrderPrefer(new Long(map2.get("id").toString()));
			map2.put("cash_info", s);

		}

		page.setData(list);

		for (Map<String, Object> map2 : list) {

			map2.put("order_products", orderEntityMapper.selectOrderProduct(new Long(map2.get("id").toString())));
		}

		return page;
	}

	@Override
	public Page<Map<String, Object>> selectOrderCouponsList(Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub

		UserEntity userEntity = UserContext.getCurrentUser();

		if (userEntity.getUser_type() == UserType.BRAND.getIndex()) {

			Map<String, Object> map = (Map<String, Object>) page.getParam();

			BrandEntity brandEntity = (BrandEntity) UserContext.getCurrentUserInfo();

			map.put("brand_id", brandEntity.getId());

		}

		if (userEntity.getUser_type() == UserType.PARTNER.getIndex()) {

			Map<String, Object> map = (Map<String, Object>) page.getParam();

			PartnerEntity brandEntity = (PartnerEntity) UserContext.getCurrentUserInfo();

			map.put("citypartner_id", brandEntity.getId());

		}

		if (userEntity.getUser_type() == UserType.ENTERPRISE.getIndex()) {

			Map<String, Object> map = (Map<String, Object>) page.getParam();

			EnterpriseEntity brandEntity = (EnterpriseEntity) UserContext.getCurrentUserInfo();

			map.put("enterprise_id", brandEntity.getId());

		}

		if (userEntity.getUser_type() == UserType.SHOP.getIndex()) {

			Map<String, Object> map = (Map<String, Object>) page.getParam();

			ShopEntity brandEntity = (ShopEntity) UserContext.getCurrentUserInfo();

			map.put("shop_id", brandEntity.getId());

		}

		page.setData(orderEntityMapper.selectOrderBrandCouponsPage(page));

		return page;
	}

	public Page<Map<String, Object>> queryShellOrderListByPage(Page<Map<String, Object>> page) {

		UserEntity userEntity = UserContext.getCurrentUser();

		if (userEntity.getUser_type() == UserType.ENTERPRISE.getIndex()) {

			Map<String, Object> map = (Map<String, Object>) page.getParam();

			EnterpriseEntity brandEntity = (EnterpriseEntity) UserContext.getCurrentUserInfo();

			map.put("enterprise_id", brandEntity.getId());

		}

		if (userEntity.getUser_type() == UserType.PARTNER.getIndex()) {

			Map<String, Object> map = (Map<String, Object>) page.getParam();

			PartnerEntity brandEntity = (PartnerEntity) UserContext.getCurrentUserInfo();

			map.put("partner_id", brandEntity.getId());

		}

		if (userEntity.getUser_type() == UserType.BRAND.getIndex()) {

			Map<String, Object> map = (Map<String, Object>) page.getParam();

			BrandEntity brandEntity = (BrandEntity) UserContext.getCurrentUserInfo();

			map.put("brand_id", brandEntity.getId());

		}

		if (userEntity.getUser_type() == UserType.SHOP.getIndex()) {

			Map<String, Object> map = (Map<String, Object>) page.getParam();

			ShopEntity brandEntity = (ShopEntity) UserContext.getCurrentUserInfo();

			map.put("shop_id", brandEntity.getId());

		}

		page.setData(orderShellListEntityMapper.queryShellOrderListByPage(page));

		return page;
	}

	@Override
	public OrderShellInfoEntity selectShellOrderInfo() {

		return orderShellInfoEntityMapper.selectData();

	}

	@Override
	public Boolean setShellOrderDay(OrderShellInfoEntity record) {

		// record 需要 ID days rate

		OrderShellInfoEntity entity = orderShellInfoEntityMapper.selectWholeData();

		if (entity == null) {
			if (record.getDays() == null || record.getRate() == null) {
				return false;
			}
			record.setUpdate_time(new Date());
			return orderShellInfoEntityMapper.insertSelective(record) > 0;
		} else {
			if (record.getDays() != null) {
				entity.setOld_days(entity.getDays());
				entity.setDays(record.getDays());
			}
			if (record.getRate() != null) {
				entity.setRate(record.getRate());
			}
			entity.setUpdate_time(new Date());
			return orderShellInfoEntityMapper.updateByPrimaryKeySelective(entity) > 0;
		}
	}

	@Override
	public Map<String, Object> queryOneShellOrderById(Long id) {

		return orderShellListEntityMapper.queryOneShellOrderById(id);
	}

	@Override
	public Page<Order> queryShellRealOrdersListByIdPage(Page<Order> page) {

		Map map = (Map<String, Object>) page.getParam();

		List<Long> list = orderEntityMapper.selectOrdersListForShellOrders(Long.parseLong(map.get("shell_id") + ""));
		map.put("order_id_list", list);
		sellerServiceImpl.selectOrderListBackForPage(page);

		return page;
	}

	@Override
	public Boolean updateShellRealOrdersState(OrderShellStateRelationEntity record) {

		record.setState_time(new Date());
		Integer result = orderShellStateRelationEntityMapper.insertSelective(record);

		return result != null && result > 0;
	}

	public Boolean exuShellOrders(OrderShellInfoEntity entity) {

		// 执行
		List<OrderEntity> orderList = orderEntityMapper.selectOrdersForShell(entity.getStart_time());

		Map<Long, Double> shop_money = new HashMap<Long, Double>();
		Map<Long, List<Long>> shop_orderIds = new HashMap<Long, List<Long>>();

		if (orderList != null) {
			for (OrderEntity o : orderList) {

				if (shop_money.containsKey(o.getShop_id())) {
					shop_money.put(o.getShop_id(), shop_money.get(o.getShop_id()) + o.getOrder_price());
				} else {
					shop_money.put(o.getShop_id(), o.getOrder_price());
				}

				if (shop_orderIds.containsKey(o.getShop_id())) {
					shop_orderIds.get(o.getShop_id()).add(o.getId());
				} else {
					List<Long> list = new ArrayList<>();
					list.add(o.getId());
					shop_orderIds.put(o.getShop_id(), list);
				}
			}
		}
		Date date = new Date();

		List<Long> shop_id_list = new ArrayList<>();

		for (Iterator<Long> ite = shop_money.keySet().iterator(); ite.hasNext();) {
			Long shop_id = ite.next();
			shop_id_list.add(shop_id);

			OrderShellListEntity orderShellListEntity = new OrderShellListEntity();
			orderShellListEntity.setMoney_pre(NumberUtil.round(shop_money.get(shop_id), 2, BigDecimal.ROUND_HALF_DOWN));
			orderShellListEntity.setMoney_platform(
					NumberUtil.round(shop_money.get(shop_id) * entity.getRate(), 2, BigDecimal.ROUND_HALF_DOWN));
			orderShellListEntity.setShell_code(NumberUtil.getIns().code());
			orderShellListEntity.setShop_id(shop_id);
			orderShellListEntity.setCreate_time(date);
			orderShellListEntity.setStart_time(entity.getStart_time());
			orderShellListEntity.setEnd_time(entity.getEnd_time());

			orderShellListEntityMapper.insertSelective(orderShellListEntity);

			OrderShellStateRelationEntity record = new OrderShellStateRelationEntity();
			record.setShell_id(orderShellListEntity.getId());
			record.setShell_state(ShellorderstateEnum.CREATE.state);
			record.setState_time(new Date());

			orderShellStateRelationEntityMapper.insertSelective(record);

			orderEntityMapper.updateShellId(orderShellListEntity.getId(), shop_orderIds.get(shop_id));
		}

		if (shop_id_list.size() <= 0) {
			shop_id_list.add(0L);
		}
		List<Long> shop_id_list_result = shopEntityMapper.selectAllShop(shop_id_list);

		if (shop_id_list_result != null && shop_id_list_result.size() > 0) {
			for (Long shop_id : shop_id_list_result) {
				OrderShellListEntity orderShellListEntity = new OrderShellListEntity();
				orderShellListEntity.setMoney_pre(0D);
				orderShellListEntity.setMoney_platform(0D);
				orderShellListEntity.setShell_code(NumberUtil.getIns().code());
				orderShellListEntity.setShop_id(shop_id);
				orderShellListEntity.setCreate_time(date);
				orderShellListEntity.setStart_time(entity.getStart_time());
				orderShellListEntity.setEnd_time(entity.getEnd_time());

				orderShellListEntityMapper.insertSelective(orderShellListEntity);

				OrderShellStateRelationEntity record = new OrderShellStateRelationEntity();
				record.setShell_id(orderShellListEntity.getId());
				record.setShell_state(ShellorderstateEnum.CREATE.state);
				record.setState_time(new Date());

				orderShellStateRelationEntityMapper.insertSelective(record);
			}
		}

		return true;
	}

}
