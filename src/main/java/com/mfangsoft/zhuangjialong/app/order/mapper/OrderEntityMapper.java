package com.mfangsoft.zhuangjialong.app.order.mapper;

import java.util.Date;
import java.util.List;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.app.order.model.Order;
import com.mfangsoft.zhuangjialong.app.order.model.OrderEntity;
import com.mfangsoft.zhuangjialong.app.order.model.OrderMessage;
import com.mfangsoft.zhuangjialong.app.order.model.OrderTracing;
import com.mfangsoft.zhuangjialong.app.order.model.StateOrderDatile;
import com.mfangsoft.zhuangjialong.app.seller.model.OrderProfitShareModel;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;

@WriterRepository
public interface OrderEntityMapper {
	/**
	 * 通过主键删除
	 *
	 * @MLTH_generated
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * 插入数据
	 *
	 * @MLTH_generated
	 */
	int insert(OrderEntity record);

	int insertSelective(OrderEntity record);

	Order selectPreInfoForOrder(@Param("shop_id")Long shop_id, @Param("customer_id")Long customer_id);
	
	List<OrderMessage> selectPreExpireorders();
	
	List<OrderMessage> selectSecKIllExpireorders();
	
	String cancelExpireorders();
	
	List<OrderMessage>  selectCanceledordersForMessage(String orderIds);
	
	List<OrderTracing> selectOrderTraceInfoByCode(String order_code);
	
	List<OrderEntity> selectOrdersByCodeList(@Param("codeList") List<String> codeList);
	
	OrderEntity selectOneOrdersByCode(@Param("code") String code);
	
	List<OrderEntity> selectOrdersForShell(@Param("start_time")Date start_time);
	
	Long updateShellId(@Param("shell_id")Long shell_id, @Param("shellIdList")List<Long> shellIdList);
	
	/**
	 * 通过主键查询数据
	 *
	 * @MLTH_generated
	 */
	OrderEntity selectByPrimaryKey(Long id);
	/**
	 * 通过订单号查询订单简单信息
	 *
	 * @MLTH_generated
	 */
	OrderEntity selectOrderSimpleInfoByCode(String code);
	
	/**
	 * 通过状态查询订单数量
	 *
	 * @MLTH_generated
	 */
	Integer selectOrdersCountsByState(@Param("customer_id") Long customer_id,@Param("stateList")List<String> stateList);
	
	/**
	 * 清楚订单及相关数据
	 *
	 * @MLTH_generated
	 */
	OrderEntity clearOrderByOrderCode(String orderCode);

	int updateByPrimaryKeySelective(OrderEntity record);

	int updateByPrimaryKeyWithBLOBs(OrderEntity record);

	/**
	 * 通过主键更新数据
	 *
	 * @MLTH_generated
	 */
	int updateByPrimaryKey(OrderEntity record);

	List<OrderEntity> selectOrdersByState(Page<OrderEntity> page);

	int updateOrderState(@Param("code") String code, @Param("order_state_code") int order_state_code);

	int selectOrderState(String code);

	OrderEntity selectOneOrderByCode(String code);

	List<OrderEntity> selectOrdersByGroupCode(String grouppay_id);
	
	
	List<Map<String,Object>> getOrderListForPage(Page<Map<String,Object>> page);
	
	List<com.mfangsoft.zhuangjialong.integration.seller.model.Order> getOrderListBackForPage(Page<com.mfangsoft.zhuangjialong.integration.seller.model.Order> page);
	
	List<Map<String,Object>> getOrderListBackForExcelPage(Page<Map<String,Object>> page);
	
	Map<String,Object>  getOrderdetails(Long order_id);

	String selectAnyOneImgForOrderProduct(String code);
	
	OrderMessage selectordersInfoForMessageByCode(String code);
	
	List<OrderProfitShareModel> selectAllOrdersForGuilderPage(Page<OrderProfitShareModel> param);
	
	List<OrderProfitShareModel> selectYiJiesuanOrdersForGuilderPage(Page<OrderProfitShareModel> param);
	
	List<OrderProfitShareModel> selectYiFuKuanOrdersForGuilderPage(Page<OrderProfitShareModel> param);

	List<OrderProfitShareModel> selectYiShiXiaoOrdersForGuilderPage(Page<OrderProfitShareModel> param);
	
	Double selectGuildSumofLastMonth(@Param("id")Long id);
	
	Double selectGuildSumofThisMonth(@Param("id")Long id);
	
	Integer selectGuildSumOrders(@Param("id")Long id);
	
	Integer selectTodayGuildSumOrders(@Param("id")Long id);
	
	Double selectSumPriceOfAllOrders(@Param("id")Long id);
	
	Integer selectUnReadOrdersForGuilder(@Param("id")Long id);
	
	List<StateOrderDatile> customerorderstatenum(Long customer_id);
	
	
	List<Map<String,Object>> selectOrdercashcouponListForPage(Page<Map<String,Object>> page);
	
	
	List<Map<String,Object>> selectOrdercashcouponNewListForPage(Page<Map<String,Object>> page);
	
	
	
	List<Map<String,Object>>selectorderTotal(Map<String,Object> map);
	
	
	
	/**
	 * 新版2.1订单修改
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> selectOrderProduct(Long id);
	
	
	/**
	 * 新版2.1订单修改
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> selectOrderForPage(Page<Map<String,Object>> page);
	
	
	
	
	List<Map<String,Object>> selectOrderBrandCouponsPage(Page<Map<String,Object>> page);
	
	List<Long> selectOrdersListForShellOrders(@Param("id")Long id);
}