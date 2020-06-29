package com.mfangsoft.zhuangjialong.app.sendflow.mapper;

import java.util.List;

import com.mfangsoft.zhuangjialong.app.sendflow.model.FlowBatchEntity;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;

@WriterRepository
public interface FlowBatchEntityMapper {
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
    int insert(FlowBatchEntity record);

    int insertSelective(FlowBatchEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    FlowBatchEntity selectByPrimaryKey(Integer id);
    
    //根据批次号查请求数据
    List<FlowBatchEntity> selectByBatch(String batch_no);

    int updateByPrimaryKeySelective(FlowBatchEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(FlowBatchEntity record);
}