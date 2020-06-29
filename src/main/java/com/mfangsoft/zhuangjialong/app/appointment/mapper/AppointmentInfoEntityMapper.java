package com.mfangsoft.zhuangjialong.app.appointment.mapper;

import java.util.List;

import java.util.Map;

import com.mfangsoft.zhuangjialong.app.appointment.model.Appointment;
import com.mfangsoft.zhuangjialong.app.appointment.model.AppointmentInfo;
import com.mfangsoft.zhuangjialong.app.appointment.model.AppointmentInfoEntity;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;

@WriterRepository
public interface AppointmentInfoEntityMapper {
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
    int insert(AppointmentInfo record);

    int insertSelective(AppointmentInfo record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    AppointmentInfoEntity selectByPrimaryKey(Long id);

    Appointment selectInfoById(Long id);
    
    int updateByPrimaryKeySelective(AppointmentInfoEntity record);

    int updateByPrimaryKeyWithBLOBs(AppointmentInfoEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(AppointmentInfoEntity record);
    
    /**
     * 通过预约信息找预约id  
     *
     * @MLTH_generated
     */
   Long selectByAppointmentInfo(AppointmentInfo record);
   
   
   
   Map<String,Object> queryBrandAppDetail(Long id);
   
   
}