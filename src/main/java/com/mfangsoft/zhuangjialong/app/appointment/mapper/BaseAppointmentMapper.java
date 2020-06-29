package com.mfangsoft.zhuangjialong.app.appointment.mapper;

import com.mfangsoft.zhuangjialong.app.appointment.model.BaseAppointment;

public interface BaseAppointmentMapper {
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
    int insert(BaseAppointment record);

    int insertSelective(BaseAppointment record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BaseAppointment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseAppointment record);

    int updateByPrimaryKeyWithBLOBs(BaseAppointment record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BaseAppointment record);
}