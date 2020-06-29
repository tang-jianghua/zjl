package com.mfangsoft.zhuangjialong.app.personalCenter.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.app.personalCenter.model.MessageEntity;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
@WriterRepository
public interface MessageEntityMapper {
    /**
     * 通过主键删除  
     *
     * @MLTH_generated
     */
    int deleteByCustomerId(Long id);

    /**
     * 插入数据  
     *
     * @MLTH_generated
     */
    int insert(MessageEntity record);

    int insertSelective(MessageEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    MessageEntity selectByPrimaryKey(Long id);

    int selectUnReadByCustomerId(Long customer_id);
    /**
     * 通过customer_id查询数据 
     *
     * @MLTH_generated
     */
    List<MessageEntity> selectByCustomer_Id(@Param("customer_id") Long customer_id);
    
    int updateByCustomerIdSelective(MessageEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(MessageEntity record);
    
    void updatemessagestate(@Param("customer_id")Long customer_id, @Param("id_array")Long[] id_array);
}