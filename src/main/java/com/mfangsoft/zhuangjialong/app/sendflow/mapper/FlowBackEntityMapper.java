package com.mfangsoft.zhuangjialong.app.sendflow.mapper;

import com.mfangsoft.zhuangjialong.app.sendflow.model.FlowBackEntity;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;

@WriterRepository
public interface FlowBackEntityMapper {
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
    int insert(FlowBackEntity record);

    int insertSelective(FlowBackEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    FlowBackEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FlowBackEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(FlowBackEntity record);
}