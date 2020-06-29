package com.mfangsoft.zhuangjialong.app.constructAppointment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.app.constructAppointment.model.ConstructStateRelation;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;

@WriterRepository
public interface ConstructStateRelationMapper {
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
    int insert(ConstructStateRelation record);

    int insertSelective(ConstructStateRelation record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    ConstructStateRelation selectByPrimaryKey(Long id);

    List<ConstructStateRelation> selectAllState();
    
    int updateByPrimaryKeySelective(ConstructStateRelation record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(ConstructStateRelation record);
    
    
    /**
     * 查询某施工预约是否取消
     *
     * @MLTH_generated
     */
    int selectWhetherCancleByConstructAppointmentId(ConstructStateRelation record);
    
    ConstructStateRelation selectByAppointmentId(@Param("construct_appointment_id") Long construct_appointment_id);
    
    ConstructStateRelation selectStateByAppointmentId(Long construct_appointment_id);
}