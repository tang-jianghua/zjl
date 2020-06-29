package com.mfangsoft.zhuangjialong.app.applogin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.app.applogin.model.UserEquipmentEntity;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
@WriterRepository
public interface UserEquipmentEntityMapper {
    /**
     * 通过主键删除  
     *
     * @MLTH_generated
     */
    int deleteByPrimaryKey(String pushstr);

    /**
     * 插入数据  
     *
     * @MLTH_generated
     */
    int insert(UserEquipmentEntity record);

    int insertSelective(UserEquipmentEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    UserEquipmentEntity selectByPrimaryKey(String pushstr);
    
    UserEquipmentEntity  selectByCustomerIdForModify(Long customer_id);

    int updateByCustomerIdKeySelective(UserEquipmentEntity record);
    
    int updateByPrimaryKeySelective(UserEquipmentEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(UserEquipmentEntity record);
    
    UserEquipmentEntity selectByCustomerId(Long customer_id);
    
    
    List<UserEquipmentEntity> selectAllByCustomerId(List<Long> customer_id);
    
    List<UserEquipmentEntity> selectAllByCustomerAccount(@Param("customerAccountList")List<String> customerAccountList);
}