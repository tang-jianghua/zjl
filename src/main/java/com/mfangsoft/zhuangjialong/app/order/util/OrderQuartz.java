package com.mfangsoft.zhuangjialong.app.order.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.mfangsoft.zhuangjialong.app.applogin.model.UserEquipmentEntity;
import com.mfangsoft.zhuangjialong.app.order.model.OrderMessage;
import com.mfangsoft.zhuangjialong.app.order.model.OrderProductEntity;
import com.mfangsoft.zhuangjialong.app.order.service.OrderService;
import com.mfangsoft.zhuangjialong.app.personalCenter.mapper.MessageEntityMapper;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.MessageEntity;
import com.mfangsoft.zhuangjialong.common.utils.DateUtils;
import com.mfangsoft.zhuangjialong.common.utils.JPushUtil;
import com.mfangsoft.zhuangjialong.common.utils.MessageConstant;
import com.mfangsoft.zhuangjialong.common.utils.RedisUtils;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.core.quartz.Quest;
import com.mfangsoft.zhuangjialong.core.quartz.QuestsManagerBean;
import com.mfangsoft.zhuangjialong.integration.brandclassify.mapper.BrandClassifyEntityMapper;
import com.mfangsoft.zhuangjialong.integration.coupons.mapper.BrandCouponsEntityMapper;
import com.mfangsoft.zhuangjialong.integration.coupons.model.CouponsRedbagMessage;

public class OrderQuartz {

	@Autowired
	OrderService orderService;
	@Autowired
	MessageEntityMapper messageEntityMapper;
	@Autowired
	BrandCouponsEntityMapper brandCouponsEntityMapper;

	// 已发送订单即将取消消息的
	static Map<Long, Long> orderIsSentMessageList = new HashMap<Long, Long>();

	// 已发送红包优惠券即将取消消息的
	static Map<Long, Long> couponsRedbagSentMessageList = new HashMap<Long, Long>();

	public void execute() {

		if (SysConstant.getIpAdress().contains(SysConstant.ip)) {
			checkAndcancelOrders();// 订单即将取消
			cancelordersBySystem();// 订单取消
			checkExpriCouponsAndRedbag();// 优惠券红包即将取消
		}
	}

	/////////////////// 订单即将取消////////////////////
	private void checkAndcancelOrders() {
		List<OrderMessage> listTemp = new ArrayList<>();
		// o.code,o.customer_id,IMGURL
		List<OrderMessage> orderList = orderService.selectPreExpireorders();

		System.out.println("-----------------OrderQuartz------------------" + orderList.size());
		long checkTime = System.currentTimeMillis() - 24 * SysConstant.MILLIS_PER_HOUT;

		Map<String, Object> map = RedisUtils.getMap("checkAndcancelOrders");

		for (final OrderMessage o : orderList) {
			if (!map.containsKey(o.getOrder_code())) {
				listTemp.add(o);
				// orderIsSentMessageList.put(o.getCustomer_id(),
				// System.currentTimeMillis());

				map.put(o.getOrder_code(), System.currentTimeMillis());
				QuestsManagerBean.addQuest(new Quest() {

					@Override
					public boolean execute() {
						JPushUtil.sendMessage(o.getPlatform(), o.getPushstr(), MessageConstant.orderExpireTitle,
								MessageFormat.format(MessageConstant.orderExpireContent, o.getOrder_code()));
						return true;
					}

					@Override
					public boolean condition() {
						return true;
					}

					@Override
					public boolean delete() {
						return true;
					}
				});

			}
		}
		Iterator<String> iter = map.keySet().iterator();
		for (; iter.hasNext();) {
			String key = iter.next();
			long valuetime = (long) map.get(key);
			if (valuetime < checkTime) {
				iter.remove();
			}
		}

		RedisUtils.setMap("checkAndcancelOrders", map);
		// 添加消息到数据库
		for (OrderMessage o : listTemp) {
			MessageEntity messageEntity = new MessageEntity();
			messageEntity.setCustomer_id(o.getCustomer_id());
			messageEntity.setParams(o.getOrder_code());
			messageEntity.setType_id(MessageConstant.orderExpire);
			messageEntity.setTime(new Date());
			messageEntity.setImgurl(o.getImgurl());
			messageEntityMapper.insertSelective(messageEntity);
		}
		listTemp = null;
	}

	//////////////////////// 取消过期订单/////////////
	private void cancelordersBySystem() {
		String ids = orderService.cancelExpireorders();// 取消24小时订单
		if (StringUtils.isNotEmpty(ids)) {
			ids = ids + "0";

			List<OrderProductEntity> list = orderService.selectProductListByOrderIds(ids);
			for (OrderProductEntity p : list) {
				orderService.updateAddStockCancleOrder(p.getSales_property_id(), p.getProduct_num());// 增加产品表库存
				
				orderService.reBackCards(p.getCode());
			}

			List<OrderMessage> orderMessageList = orderService.selectCanceledordersForMessage(ids);
			checkAndcancelOrders(orderMessageList);

		}

	}

	private void checkAndcancelOrders(List<OrderMessage> orderMessageList) {

		for (final OrderMessage o : orderMessageList) {
			QuestsManagerBean.addQuest(new Quest() {

				@Override
				public boolean execute() {
					JPushUtil.sendMessage(o.getPlatform(), o.getPushstr(), MessageConstant.orderCanceledTitle,
							MessageFormat.format(MessageConstant.orderCanceledContent, o.getOrder_code()));
					return true;
				}

				@Override
				public boolean condition() {
					return true;
				}

				@Override
				public boolean delete() {
					return true;
				}
			});
		}
		// 添加消息到数据库
		for (OrderMessage o : orderMessageList) {
			MessageEntity messageEntity = new MessageEntity();
			messageEntity.setCustomer_id(o.getCustomer_id());
			messageEntity.setParams(o.getOrder_code());
			messageEntity.setType_id(MessageConstant.orderCanceled);
			messageEntity.setTime(new Date());
			messageEntity.setImgurl(o.getImgurl());
			messageEntityMapper.insertSelective(messageEntity);
		}

	}

	/////////////////////// 检查即将过期红包优惠券///////////////////
	private void checkExpriCouponsAndRedbag() {

		List<CouponsRedbagMessage> listTemp = new ArrayList<>();

		List<CouponsRedbagMessage> u = brandCouponsEntityMapper.selectPreExpireCouponsRedbagsForMessage();

		List<String> delList = new ArrayList<>();
		long checkTime = System.currentTimeMillis() - 24 * SysConstant.MILLIS_PER_HOUT;

		Map<String, Object> map = RedisUtils.getMap("checkExpriCouponsAndRedbag");
		for (final CouponsRedbagMessage o : u) {
			if (!map.containsKey(o.getCustomer_id().toString())) {
				listTemp.add(o);
				map.put(o.getCustomer_id().toString(), System.currentTimeMillis());

				QuestsManagerBean.addQuest(new Quest() {

					@Override
					public boolean execute() {
						if(o.getType().intValue() == 2){
						JPushUtil.sendMessage(o.getPlatform(), o.getPushstr(), MessageConstant.CouponsExpTitle,
								MessageFormat.format(MessageConstant.CouponsExpContent, o.getName(), o.getValue(),
										DateUtils.formatDate_(o.getEnd_time())));
						}else{
							JPushUtil.sendMessage(o.getPlatform(), o.getPushstr(), MessageConstant.RedbagExpTitle,
									MessageFormat.format(MessageConstant.RedbagExpContent, o.getName(), o.getValue(),
											DateUtils.formatDate_(o.getEnd_time())));
						}
						return true;
					}

					@Override
					public boolean condition() {
						return true;
					}

					@Override
					public boolean delete() {
						return true;
					}
				});

			}
		}

		Iterator<String> iter = map.keySet().iterator();
		for (; iter.hasNext();) {
			String key = iter.next();
			long valuetime = (long) map.get(key);
			if (valuetime < checkTime) {
				iter.remove();
			}
		}
		RedisUtils.setMap("checkExpriCouponsAndRedbag", map);

		// 添加消息到数据库
		for (CouponsRedbagMessage o : listTemp) {
			MessageEntity messageEntity = new MessageEntity();
			messageEntity.setCustomer_id(o.getCustomer_id());
			messageEntity.setParams(o.getName() + "," + o.getValue() + "," + DateUtils.formatDate_(o.getEnd_time()));
			messageEntity.setType_id(MessageConstant.CouponsExp);
			messageEntity.setTime(new Date());
			messageEntityMapper.insertSelective(messageEntity);
		}

		listTemp = null;
	}

}
