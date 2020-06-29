package com.mfangsoft.zhuangjialong.app.promotion.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mfangsoft.zhuangjialong.app.order.mapper.OrderStateRelationEntityMapper;
import com.mfangsoft.zhuangjialong.app.order.model.OrderMessage;
import com.mfangsoft.zhuangjialong.app.order.model.OrderStateRelationEntity;
import com.mfangsoft.zhuangjialong.app.order.service.OrderService;
import com.mfangsoft.zhuangjialong.app.order.util.OrderStatusEnum;
import com.mfangsoft.zhuangjialong.app.personalCenter.mapper.MessageEntityMapper;
import com.mfangsoft.zhuangjialong.app.promotion.mapper.PromotionNoteEntityMapper;
import com.mfangsoft.zhuangjialong.app.promotion.model.PromotionMessage;
import com.mfangsoft.zhuangjialong.app.promotion.model.PromotionNoteEntity;
import com.mfangsoft.zhuangjialong.common.utils.JPushUtil;
import com.mfangsoft.zhuangjialong.common.utils.MessageConstant;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.core.quartz.Quest;
import com.mfangsoft.zhuangjialong.core.quartz.QuestsManagerBean;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.PromotionSeckillProductEntityMapper;

public class PromotionQuartz {

	@Autowired
	OrderService orderService;
	@Autowired
	MessageEntityMapper messageEntityMapper;
	@Autowired
	OrderStateRelationEntityMapper orderStateRelationEntityMapper;
	@Autowired
	PromotionSeckillProductEntityMapper promotionSeckillProductEntityMapper;
	@Autowired
	PromotionNoteEntityMapper promotionNoteEntityMapper;

	// 已发送订单即将取消消息的
	static Map<Long, Long> orderIsSentMessageList = new HashMap<Long, Long>();
	// 已发送产品提醒消息的
	static Map<String, List> noteIsSentMessageList = new HashMap<String, List>();

	public void execute() {
		if (SysConstant.getIpAdress().contains(SysConstant.ip)) {
			checkPromotionAndcancelOrders();
			sendPromotionMessage();
		}
	}

	/////////////////// 取消秒杀活动订单////////////////////
	private void checkPromotionAndcancelOrders() {
		try {
			List<OrderMessage> orderList = orderService.selectSecKIllExpireorders();// 查询所有待过期秒杀产品，customer
																					// order_code
																					// 等是重复的，需要getOne()过滤
			
			System.out.println("-----------------PromotionQuartz----------------" + orderList.size());
			Map<String, Long> distinctOrderCodeList = new HashMap<String, Long>();
			for (OrderMessage o : orderList) {
				
				if(!distinctOrderCodeList.containsKey(o.getOrder_code())){
					distinctOrderCodeList.put(o.getOrder_code(), o.getCustomer_id());// code唯一的map
					
					// 取消订单
					OrderStateRelationEntity record = new OrderStateRelationEntity();
					record.setOrder_code(o.getOrder_code());
					record.setOrder_state_code(OrderStatusEnum.CANCEl.value);
					orderService.insertOrderState(record);
				}

			}
			// 推送和更新数据库分离，不至于影响推送
			// orderIsSentMessageList =
			// RedisUtils.getMap(SysConstant.PromotionQuartz);
			for (Map.Entry<String, Long> entity : distinctOrderCodeList.entrySet()) {// code唯一的map
				if (!orderIsSentMessageList.containsKey(entity.getValue())) {
					
					orderIsSentMessageList.put(entity.getValue(), System.currentTimeMillis());
					
					// 任取一条用以发消息
					final OrderMessage o = getOne(orderList, entity.getKey());
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

			long checkTime = System.currentTimeMillis() - 24 * SysConstant.MILLIS_PER_HOUT;
			Iterator<Long> iter = orderIsSentMessageList.keySet().iterator();
			for (; iter.hasNext();) {
				if (orderIsSentMessageList.get(iter.next()) < checkTime) {
					iter.remove();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private OrderMessage getOne(List<OrderMessage> orderList, String order_code) {
		for (OrderMessage m : orderList) {
			if (m.getOrder_code().equals(order_code))
				return m;
		}
		return null;
	}

	/////////////////////////// 秒杀活动提醒///////////////////////////
	private void sendPromotionMessage() {

		List<PromotionMessage> customersList = promotionNoteEntityMapper.selectNoteForMessage();
		if (customersList != null) {
			for (final PromotionMessage p : customersList) {

				QuestsManagerBean.addQuest(new Quest() {

					@Override
					public boolean execute() {
						JPushUtil.sendMessage(p.getPlatform(), p.getPushstr(), p.getTitle(),
								MessageFormat.format(MessageConstant.PromotionMesage, p.getPstart_time()));
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

				PromotionNoteEntity record = new PromotionNoteEntity();
				record.setCustomer_id(p.getCustomer_id());
				record.setPid(p.getPid());
				record.setTime_id(p.getTime_id());
				record.setIsnoted(1);
				promotionNoteEntityMapper.updateByCustomerId(record);

				System.out.println("活动提醒推送--------" + p.getCustomer_id() + "|" + p.getPlatform() + p.getPushstr());
			}
		}

	}

	// private void saveIntoMapList(Long customer_id, String pstart_time) {
	//
	// if (noteIsSentMessageList.get(pstart_time) == null) {
	// List list = new ArrayList<Long>();
	// list.add(customer_id);
	// noteIsSentMessageList.put(pstart_time, list);
	// } else {
	// noteIsSentMessageList.get(pstart_time).add(customer_id);
	// }
	//
	// }
	//
	// private boolean isContin(Long customer_id, String pstart_time) {
	//
	// if (noteIsSentMessageList.get(pstart_time) != null) {
	// if (noteIsSentMessageList.get(pstart_time).contains(customer_id)) {
	// return true;
	// }
	// }
	// return false;
	// }

	public static void main(String[] args) {

		List list = new ArrayList<Long>();
		list.add(new Long(1));
		Long v = new Long(2);
		Long v2 = new Long(2);
		System.out.println(v == v2);

	}
}
