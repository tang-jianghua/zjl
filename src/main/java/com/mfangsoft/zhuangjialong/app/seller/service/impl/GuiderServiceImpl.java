package com.mfangsoft.zhuangjialong.app.seller.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.app.applogin.mapper.CustomerEntityMapper;
import com.mfangsoft.zhuangjialong.app.applogin.model.Customer;
import com.mfangsoft.zhuangjialong.app.applogin.model.CustomerEntity;
import com.mfangsoft.zhuangjialong.app.appointment.mapper.AppointmentMapper;
import com.mfangsoft.zhuangjialong.app.constructAppointment.mapper.ConstructAppointmentMapper;
import com.mfangsoft.zhuangjialong.app.constructAppointment.model.ConstructAppointmentModel;
import com.mfangsoft.zhuangjialong.app.order.mapper.OrderEntityMapper;
import com.mfangsoft.zhuangjialong.app.seller.mapper.SellerEntityMapper;
import com.mfangsoft.zhuangjialong.app.seller.model.Guild;
import com.mfangsoft.zhuangjialong.app.seller.model.OrderProfitShareModel;
import com.mfangsoft.zhuangjialong.app.seller.model.SellerEntity;
import com.mfangsoft.zhuangjialong.app.seller.service.GuiderService;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.MD5Util;
import com.mfangsoft.zhuangjialong.common.utils.RedisUtils;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.common.utils.TokenUtil;
import com.mfangsoft.zhuangjialong.integration.enums.SellerType;

@Service
public class GuiderServiceImpl implements GuiderService {

	@Autowired
	OrderEntityMapper orderEntityMapper;

	@Autowired
	CustomerEntityMapper customerEntityMapper;

	@Autowired
	SellerEntityMapper sellerEntityMapper;

	@Autowired
	ConstructAppointmentMapper constructAppointmentMapper;
	
	
	@Autowired
	AppointmentMapper appointmentMapper;

	@Override
	public ResponseMessage<Guild> login(Guild g, HttpSession session) {
		ResponseMessage<Guild> message = new ResponseMessage<>();

		if (session.getAttribute(SysConstant.APP_CUSTOMER_INFO) != null) {
			message.setCode("1");
			message.setMessage("该用户已登录");
			return message;
		}
		
		//图片验证码
		if(g.getvKey() != null){
			String str = RedisUtils.getValue(g.getvKey());
			if(g.getMcode() == null || str == null ||  !str.equals(g.getMcode())){
				message.setCode(SysConstant.FAILURE_CODE);
				message.setMessage("验证码错误");
				return message;
			}
		}
		
		Guild guild = sellerEntityMapper.selectByAccount(g.getAccount());
		if (guild == null) {
			message.setCode("1");
			message.setMessage("账号不存在");
			return message;
		}
		if (guild.getState() != null && guild.getState() == 0) {
			message.setCode("1");
			message.setMessage("账号被禁止");
			return message;
		}
		if (!guild.getPassword().equals(MD5Util.MD5(g.getPassword()))) {
			message.setCode("1");
			message.setMessage("密码不正确");
			return message;
		}
		guild.setPassword(null);

		guild.setToken(TokenUtil.getIns().getToken(guild.getAccount()));
		guild.setKey(TokenUtil.getIns().getAndIncreaseKey() + guild.getId());
		session.setAttribute(SysConstant.APP_CUSTOMER_INFO, message.getResult());
		RedisUtils.setValue(guild.getKey(), guild.getToken());

		message.setCode("0");
		message.setMessage("登陆成功");
		message.setResult(guild);
		return message;
	}

	public ResponseMessage<Guild> logout(Guild g, HttpSession session) {

		ResponseMessage<Guild> message = new ResponseMessage<>();
		session.removeAttribute(SysConstant.APP_CUSTOMER_INFO);
		if (session.getAttribute(SysConstant.APP_CUSTOMER_INFO) == null) {
			message.setCode("0");
			message.setMessage("退出成功");
		} else {
			message.setCode("1");
			message.setMessage("退出失败");
		}
		return message;
	}

	public ResponseMessage<Guild> resetPassWord1(@RequestBody Guild g) {
		ResponseMessage<Guild> message = new ResponseMessage<Guild>();

		if (g.getMcode() != null && RedisUtils.getValue(g.getPhone()) != null
				&& g.getMcode().equals(RedisUtils.getValue(g.getPhone()))) {
			message.setCode("0");
			message.setMessage("验证成功");
			g = sellerEntityMapper.selectByAccount(g.getPhone());
			message.setResult(g);
			return message;
		} else {
			message.setCode("1");
			message.setMessage("验证码错误");
			return message;
		}
	}
	
	public ResponseMessage<Boolean> resetPassWord2(SellerEntity record) {
		ResponseMessage<Boolean> message = new ResponseMessage<>();
		
		String new_password= MD5Util.MD5(record.getPassword());
		SellerEntity sellerEntity = sellerEntityMapper.selectPrimaryByAccount(record.getAccount());
		if(sellerEntity.getPassword().equals(new_password)){
			message.setCode("1");
			message.setMessage("新密码不可与旧密码相同");
			return message;
		}
		
		sellerEntity.setPassword(new_password);
		if (sellerEntityMapper.updateByPrimaryKeySelective(sellerEntity) > 0) {
			message.setCode("0");
			message.setMessage("修改成功");
			message.setResult(true);
			return message;
		} else {
			message.setCode("1");
			message.setMessage("修改失败");
			return message;
		}

	}

	/**
	 * type 查询类型 0全部 1已结算 2已付款 3 已失效
	 */
	@Override
	public List<OrderProfitShareModel> guidorderlist(Page<OrderProfitShareModel> param) {

		@SuppressWarnings("unchecked")
		Map<String, String> p = (Map<String, String>) param.getParam();

		sellerEntityMapper.updateViewOrderTime(Long.valueOf(p.get("id")), new Date());

		if ("0".equals(p.get("type"))) {
			return orderEntityMapper.selectAllOrdersForGuilderPage(param);
		}
		if ("1".equals(p.get("type"))) {
			return orderEntityMapper.selectYiJiesuanOrdersForGuilderPage(param);
		}
		if ("2".equals(p.get("type"))) {
			return orderEntityMapper.selectYiFuKuanOrdersForGuilderPage(param);
		}
		if ("3".equals(p.get("type"))) {
			return orderEntityMapper.selectYiShiXiaoOrdersForGuilderPage(param);
		}
		return null;

	}

	public List<CustomerEntity> queryguidcustomerlist(Long seller_id){
		
		return customerEntityMapper.selectGuildCustomerList(seller_id);
		
	}

	/**
	 * 推广用户列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerEntity> guildCustomerList(Page<CustomerEntity> param) {

		sellerEntityMapper.updateViewTuiguangTime(Long.valueOf(((Map<String, String>) param.getParam()).get("id")),
				new Date());
		List<CustomerEntity> cl = customerEntityMapper.selectGuildCustomerListPage(param);
		for (CustomerEntity c : cl) {
			int cc = selectGuildCustomerCount(c.getId());
			c.setByExpandCustomerCount(cc);
		}
		return cl;

	}

	/**
	 * 推广 推广卖家列表
	 * 
	 * @param param
	 * @return
	 */
	public List<SellerEntity> selectGuildSellerListPage(Page<SellerEntity> param) {

		sellerEntityMapper.updateViewTuiguangTime(Long.valueOf(((Map<String, String>) param.getParam()).get("id")),
				new Date());
		List<SellerEntity> cl = sellerEntityMapper.selectGuildSellerListPage(param);
		for (SellerEntity c : cl) {
			int cc = selectGuildCustomerCount(c.getId());
			c.setByExpandCustomerCount(cc);
		}
		return cl;

	}

	/**
	 * 推广 首页
	 * 
	 * @param param
	 * @return
	 */
	public Map<String, String> queryinvitmentSellerForfisrtPage(SellerEntity record) {
		Long id = record.getId();
		Map<String, String> map = new HashMap<>();

		map.put("tuiguangSum", sellerEntityMapper.selectGuildSellerSumCount(id) + "");
		map.put("TodayCount", sellerEntityMapper.selectGuildSellerTodayCount(id, new Date()) + "");
		map.put("UnreadCount", sellerEntityMapper.selectGuildSellerUnreadCount(id) + "");

		sellerEntityMapper.updateTuiguangTime(record.getId(), new Date());

		return map;
	}

	/**
	 * 施工订单列表
	 */
	@Override
	public List<ConstructAppointmentModel> guildConstructAppointmentBySellerIdForPage(
			Page<ConstructAppointmentModel> param) {

		return constructAppointmentMapper.selectConstructAppointmentBySellerIdForPage(param);

	}

	/**
	 * 历史推广用户总数
	 */
	@Override
	public Integer selectGuildCustomerCount(Long exshopper_id) {
		Integer r = customerEntityMapper.selectGuildCustomerCount(exshopper_id);
		return r == null ? 0 : r;
	}

	/**
	 * 上月实际收入
	 */
	@Override
	public Double selectGuildSumofLastMonth(Long id) {
		Double r = orderEntityMapper.selectGuildSumofLastMonth(id);
		return r == null ? 0D : r;
	}

	/**
	 * 本月估计收入
	 */
	@Override
	public Double selectGuildSumofThisMonth(Long id) {
		Double r = orderEntityMapper.selectGuildSumofThisMonth(id);
		return r == null ? 0D : r;
	}

	/**
	 * 历史推广订单总数
	 */
	@Override
	public Integer selectGuildSumOrders(Long id) {
		Integer r = orderEntityMapper.selectGuildSumOrders(id);
		return r == null ? 0 : r;
	}

	/**
	 * 今日推广订单总数
	 */
	@Override
	public Integer selectTodayGuildSumOrders(Long id) {
		Integer r = orderEntityMapper.selectTodayGuildSumOrders(id);
		return r == null ? 0 : r;
	}

	/**
	 * 历史订单分成总收入
	 */
	@Override
	public Double selectSumPriceOfAllOrders(Long id) {
		Double r = orderEntityMapper.selectSumPriceOfAllOrders(id);
		return r == null ? 0D : r;
	}

	/**
	 * 历史施工总数
	 */
	@Override
	public Integer selectAppointmentCountByConstructId(Long id) {
		Integer r = constructAppointmentMapper.selectAppointmentCountByConstructId(id);
		return r == null ? 0 : r;
	}

	/**
	 * 未读推广用户个数
	 */
	@Override
	public Integer selectUnReadGuildCustomerCount(Long id) {
		Integer r = customerEntityMapper.selectUnReadGuildCustomerCount(id);
		return r == null ? 0 : r;
	}

	/**
	 * 未读推广订单个数
	 */
	@Override
	public Integer selectUnReadGuildOrdersCount(Long id) {
		Integer r = orderEntityMapper.selectUnReadOrdersForGuilder(id);
		return r == null ? 0 : r;
	}

	/**
	 * 未读施工个数
	 */
	@Override
	public Integer selectUnReadAppointmentCountByConstructId(Long id) {
		Integer r = constructAppointmentMapper.selectUnReadAppointmentCountByConstructId(id);
		return r == null ? 0 : r;
	}

	@Override
	public Integer selectUnReadAppointmentCountByShopGuiderId(Long id) {
		return appointmentMapper.selectUncheckedAppointmentNo(id);
	}
	@Override
	public Integer selectTotalAppointmentCountByShopGuiderId(Long id) {
		return appointmentMapper.selectTotalAppointmentNo(id);
	}
}
