package com.mfangsoft.zhuangjialong.app.order.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.constructAppointment.model.ConstructAppointment;
import com.mfangsoft.zhuangjialong.app.coupons.model.BrandCouponsModel;
import com.mfangsoft.zhuangjialong.app.order.model.OrderCashEntity;
import com.mfangsoft.zhuangjialong.app.order.model.OrderCashNewEntity;
import com.mfangsoft.zhuangjialong.app.order.model.OrderEntity;
import com.mfangsoft.zhuangjialong.app.order.model.OrderJsonModel;
import com.mfangsoft.zhuangjialong.app.order.model.OrderMessage;
import com.mfangsoft.zhuangjialong.app.order.model.OrderPay;
import com.mfangsoft.zhuangjialong.app.order.model.OrderPayEntity;
import com.mfangsoft.zhuangjialong.app.order.model.OrderProduct;
import com.mfangsoft.zhuangjialong.app.order.model.OrderProductEntity;
import com.mfangsoft.zhuangjialong.app.order.model.OrderStateRelationEntity;
import com.mfangsoft.zhuangjialong.app.order.model.OrderTracing;
import com.mfangsoft.zhuangjialong.app.order.model.StateOrderNums;
import com.mfangsoft.zhuangjialong.app.order.pay_ali.model.AliOrderRequest;
import com.mfangsoft.zhuangjialong.app.order.pay_weixin.UnifiedOrderRequest;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;

/**
* @description：
* @author：Liyanchen 
* @date：2016年6月14日
* 
*/
@Service
public interface OrderService {
	
	public String getOrderCode();
	
	public ResponseMessage<List<OrderJsonModel>> addOrders(OrderPay orders, ResponseMessage<List<OrderJsonModel>> responseMessage);
	
   public long insertOrder(OrderEntity order);

   public long insertSalesInfo(OrderProductEntity entity);
   
   public int clearOrder(final String orderCode);
   
   public int selectOrdersCountsByState(long customer_id,List<String> state);
   
   public List<OrderEntity> selectOrdersByState(Page<OrderEntity> page);
   /**
    * 插入一条订单状态记录
    * @param record 需要 order_code order_state_code 两个参数
    * @return
    */
   public long insertOrderState(OrderStateRelationEntity record);
   
   public int updateOrderState(String code, int order_state_code);
   
   public OrderEntity selectOrderSimpleInfoByCode(String code);
   
   public int selectOrderState(String code);
   
   public OrderEntity selectOneOrderByCode(String code);
   
   public int selectPayStateByCodes(List<String> list);
   
   public void updateGroupIdByOrderCode(Map<String, Object> map);
   
   public List<OrderEntity> selectOrdersByGroupCode(String groupCodeId);
   
   public void updateByGroupIdSelective(OrderPayEntity orderPayEntity);
   
   public List<OrderPayEntity> selectPayStateByGroupId(String grouppay_id);
   
   public Page<OrderEntity> customerOrderList(OrderPay orderPay);
   
   public OrderEntity customerOrderDetails(OrderEntity param);
   
   public List<OrderProduct> customerorderproductsdetails(OrderEntity param);
   
   public List<OrderTracing> selectOrderTraceInfoByCode(String order_code);
   
   public List<OrderMessage> selectPreExpireorders();
   
   public List<OrderMessage> selectSecKIllExpireorders();
   
   public String cancelExpireorders();
   
   public void reBackCards(String order_code);
   
   public StateOrderNums customerorderstatenum(OrderEntity param);
   
   public List<OrderMessage> selectCanceledordersForMessage(String orderIds);
   
   public List<OrderProductEntity> selectProductListByOrderIds(String orderIds);
   
   public int updateAddStockCancleOrder(Long id,Integer stock);
   
   public String selectAnyOneImgForOrderProduct(String code);
   /**
    *普通支付
    */
   public UnifiedOrderRequest customerPayOrder(UnifiedOrderRequest unifiedOrderRequest)throws UnsupportedEncodingException;
   /**
    *尾款支付
    */
   public UnifiedOrderRequest customerpayorderfordeposit(UnifiedOrderRequest unifiedOrderRequest)throws UnsupportedEncodingException;
   /**
    *联盟活动券支付
    */
   public UnifiedOrderRequest customerpayorderforcash(OrderCashEntity orderCashEntity)throws UnsupportedEncodingException;
   /**
    *商铺定金券支付
    */
   public UnifiedOrderRequest customerpayorderforPreShopcartPay(OrderCashNewEntity orderCashNewEntity)throws UnsupportedEncodingException;
   
   /**
    *施工预约支付
    */
   public UnifiedOrderRequest customerpayorderforconstruct(ConstructAppointment param)throws UnsupportedEncodingException;
   /**
    * 优惠券支付
    */
   public UnifiedOrderRequest customerpayorderforCoupons(BrandCouponsModel param)throws UnsupportedEncodingException;
   
   public void notifyWeixin(HttpServletRequest request, HttpServletResponse response) throws Exception;
   
   public void WXRnotifyForDeposit(HttpServletRequest request, HttpServletResponse response) throws Exception;
   
   public void WXRnotifyForCash(HttpServletRequest request, HttpServletResponse response) throws Exception;
   
   public void WXRnotifyForPreShopcartPay(HttpServletRequest request, HttpServletResponse response) throws Exception;
   
   public void WXRnotifyForConstract(HttpServletRequest request, HttpServletResponse response) throws Exception;
   
   public void WXRnotifyForCoupons(HttpServletRequest request, HttpServletResponse response) throws Exception;
   
   /**
    *普通H5支付
    */
   public UnifiedOrderRequest customerPayOrderH5(UnifiedOrderRequest unifiedOrderRequest)throws UnsupportedEncodingException;
   
   /**
    *尾款支付H5
    */
   public UnifiedOrderRequest customerpayorderfordepositH5(UnifiedOrderRequest unifiedOrderRequest)throws UnsupportedEncodingException;
   /**
    *联盟活动券支付H5
    */
   public UnifiedOrderRequest customerpayorderforcashH5(OrderCashEntity orderCashEntity)throws UnsupportedEncodingException;
   /**
    *商铺定金券支付H5
    */
   public UnifiedOrderRequest customerpayorderforcashNewH5(OrderCashNewEntity orderCashNewEntity)throws UnsupportedEncodingException;
   /**
    *施工预约支付H5
    */
   public UnifiedOrderRequest customerpayorderforconstructH5(ConstructAppointment param)throws UnsupportedEncodingException;
   /**
    * 优惠券支付H5
    */
   public UnifiedOrderRequest customerpayorderforCouponsH5(BrandCouponsModel param)throws UnsupportedEncodingException;
   
   /**
    *普通支付
    */
	public ResponseMessage customerAliPayOrder(AliOrderRequest aliOrderRequest)
			throws ClientProtocolException, IOException;
	public void notifyAli(HttpServletRequest request, HttpServletResponse response) throws Exception;
	/**
	 *尾款支付
	 */
	public ResponseMessage customerAliPayOrderForDeposit(AliOrderRequest aliOrderRequest)
			throws ClientProtocolException, IOException;
	public void ALIXYZnotifyForDeposit(HttpServletRequest request, HttpServletResponse response) throws Exception;
	/**
	    *联盟活动券支付
	    */
	public ResponseMessage customerAliPayOrderForCash(OrderCashEntity orderCashEntity)
			throws ClientProtocolException, IOException;
	public void ALIXYZnotifyForCash(HttpServletRequest request, HttpServletResponse response) throws Exception;
	/**
	    *商铺定金券支付
	    */
	public ResponseMessage customerAliPayOrderForPreShopcartPay(OrderCashNewEntity orderCashNewEntity)
			throws ClientProtocolException, IOException;
	public void ALIXYZnotifyForPreShopcartPay(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	/**
	 * 阿里施工预约支付
	 */
	public ResponseMessage customerAliPayOrderForConstruct(ConstructAppointment param)throws ClientProtocolException, IOException;
	public void ALIXYZnotifyForConstruct(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	/**
	 * 阿里优惠券支付
	 */
	public ResponseMessage customerAliPayOrderForCoupons(BrandCouponsModel param)throws ClientProtocolException, IOException;
	public void ALIXYZnotifyForCoupons(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
