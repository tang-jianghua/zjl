package com.mfangsoft.zhuangjialong.app.appointment.service;

import com.mfangsoft.zhuangjialong.app.appointment.model.Appointment;
import com.mfangsoft.zhuangjialong.app.appointment.model.AppointmentInfoEntity;
import com.mfangsoft.zhuangjialong.app.buildClass.model.BuildClass;
import com.mfangsoft.zhuangjialong.app.product.model.SelectPropertiesModel;
import com.mfangsoft.zhuangjialong.common.model.Page;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年7月7日
* 
*/

public interface AppointmentService {
     
	    /**
     * 获取品类预约风格和空间
     *
     * @MLTH_generated
     */
    SelectPropertiesModel getStyleAndSpaceByClass(BuildClass param);
    
    /**
     * 上传预约
     *
     * @MLTH_generated
     */
    public boolean insertAppointment(Appointment appointment);
    
    /*
     * 店铺导购获取预约订单
     */
    Page<Appointment> selectAppointmentsForShopGuider(Page<Appointment> page);
    
    /*
     * 店铺导购获取预约订单详情
     */
    Appointment selectAppointmentDetailForShopGuider(Appointment appointment);
    
}
