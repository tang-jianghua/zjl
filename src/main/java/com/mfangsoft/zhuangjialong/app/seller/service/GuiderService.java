package com.mfangsoft.zhuangjialong.app.seller.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.mfangsoft.zhuangjialong.app.applogin.model.CustomerEntity;
import com.mfangsoft.zhuangjialong.app.constructAppointment.model.ConstructAppointmentModel;
import com.mfangsoft.zhuangjialong.app.seller.model.Guild;
import com.mfangsoft.zhuangjialong.app.seller.model.OrderProfitShareModel;
import com.mfangsoft.zhuangjialong.app.seller.model.SellerEntity;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;

@Service
public interface GuiderService {

	public ResponseMessage<Guild> login(Guild g, HttpSession session);
	
	public ResponseMessage<Guild> logout(Guild g, HttpSession session);
	
	public ResponseMessage<Guild> resetPassWord1(Guild guild);
	
	public ResponseMessage<Boolean> resetPassWord2(SellerEntity record);
	
	public List<OrderProfitShareModel> guidorderlist(Page<OrderProfitShareModel> param);
	
	public List<CustomerEntity> queryguidcustomerlist(Long seller_id);
	
	public List<CustomerEntity> guildCustomerList(Page<CustomerEntity> param);
	
	public Map<String,String> queryinvitmentSellerForfisrtPage(SellerEntity record);
	
	public List<SellerEntity> selectGuildSellerListPage(Page<SellerEntity> param);
	
	public Integer selectGuildCustomerCount(@Param("exshopper_id") Long exshopper_id);
	
	public Double selectGuildSumofLastMonth(Long id);
	
	public Double selectGuildSumofThisMonth(Long id);
	
	public Integer selectGuildSumOrders(Long id);
	
	public Integer selectTodayGuildSumOrders(Long id);
	
	public Double selectSumPriceOfAllOrders(Long id);
	
	public Integer selectUnReadGuildCustomerCount(Long id);
	
	public Integer selectUnReadGuildOrdersCount(Long id);
	
	public Integer selectUnReadAppointmentCountByConstructId(Long id);
	
	/*
	 * 查询店铺导购未读的预约订单数
	 */
	public Integer selectUnReadAppointmentCountByShopGuiderId(Long id);
	/*
	 * 查询店铺导购预约订单总数
	 */
	public Integer selectTotalAppointmentCountByShopGuiderId(Long id);
	
	public Integer selectAppointmentCountByConstructId(Long id);
	
	public List<ConstructAppointmentModel> guildConstructAppointmentBySellerIdForPage(Page<ConstructAppointmentModel> param);
	
}
