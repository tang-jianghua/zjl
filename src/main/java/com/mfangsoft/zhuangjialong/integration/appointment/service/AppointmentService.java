package com.mfangsoft.zhuangjialong.integration.appointment.service;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.integration.shop.model.EmployeeEntity;
import com.mfangsoft.zhuangjialong.integration.shop.model.ShopEntity;
import com.mfangsoft.zhuangjialong.integration.user.model.UserEntity;

public interface AppointmentService {

	/**
	 * 预约查询
	 * @param page
	 * @return
	 */
	Page<Map<String,Object>> getAppointmentListForPage(Page<Map<String,Object>>  page);
	
	/**
	 * 选择服务人员
	 * @param createrId
	 * @return
	 */
	List<EmployeeEntity>  getServiceUser();
	
	
	/**
	 * 更新预约状态
	 * @param id
	 * @return
	 */
	Boolean updateAppointmentInfo(Long id,Integer state); 
	
	/**
	 * 查看预约明细
	 * @param id
	 * @return
	 */
     Map<String,Object> queryBrandAppDetail(Long id);
     
     
     
     
     List<ShopEntity>  getShopName();
     
     
     Boolean updateAppointmentInfoShopId(Long id, Long shop_id);
     
     Boolean updateAppointmentInfoService(Long id, Long service_id);
	
	
	
	
}
