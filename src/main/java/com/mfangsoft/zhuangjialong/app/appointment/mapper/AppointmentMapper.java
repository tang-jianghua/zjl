package com.mfangsoft.zhuangjialong.app.appointment.mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.app.appointment.model.Appointment;
import com.mfangsoft.zhuangjialong.app.buildClass.model.BuildClass;
import com.mfangsoft.zhuangjialong.app.product.model.Salesinfo;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年10月26日
* 
*/
@WriterRepository
public interface AppointmentMapper extends BaseAppointmentMapper{
	
	List<Appointment> selectByCustomerId(Long customer_id);
	
	/*
	 * 通过预约信息id查询预约详情
	 */
	Appointment selectDatilByAppointmentInfoId(Long id);
		/*
		 * 通过属性查询主键ID
		 */
	    Long selectIdByAppointment(Appointment appointment);
	    
	    
		 /*
		  *  通过品类预约空间
		  */
	    List<Salesinfo>  selectSpacesByClassId(BuildClass param);
	    
	    /*
	     * 通过品类预约风格
	     */
	    List<Salesinfo>  selectStylesByClassId(BuildClass param);
	    
	    /*
	     * 添加预约主表
	     */
	    int insertAppointment(Appointment appointment);
	    
	    List<Map<String,Object>> getAppointmentListForPage(Page<Map<String,Object>> page);
	    
	    /*
	     * 根据服务人员id和状态获取预约信息（店铺导购卖家端用）
	     */
	    List<Appointment> selectAppointmentsByServerIdAndStateForPage(Page<Appointment> page);
	    
	    /*
	     * 查询预约详情
	     */
	   Appointment selectAppointmentdetailForShopGuider(Long appointment_info_id);
	   
	   /*
	    * 查询未查看的预约数
	    */
	   int selectUncheckedAppointmentNo(Long server_id);
	   /*
	    * 查询总的预约数
	    */
	   int selectTotalAppointmentNo(Long server_id);
	   
	   int updateSeenStateByIds(@Param("list") Set<Long> list);
}
