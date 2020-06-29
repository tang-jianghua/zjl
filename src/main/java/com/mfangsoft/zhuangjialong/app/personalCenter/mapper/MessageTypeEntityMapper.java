package com.mfangsoft.zhuangjialong.app.personalCenter.mapper;

import com.mfangsoft.zhuangjialong.app.personalCenter.model.MessageTypeEntity;

public interface MessageTypeEntityMapper {
    /**
     * 通过主键删除  
     *
     * @MLTH_generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入数据  
     *
     * @MLTH_generated
     */
    int insert(MessageTypeEntity record);

    int insertSelective(MessageTypeEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    MessageTypeEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MessageTypeEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(MessageTypeEntity record);
}