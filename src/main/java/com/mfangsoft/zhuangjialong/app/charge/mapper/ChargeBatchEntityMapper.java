package com.mfangsoft.zhuangjialong.app.charge.mapper;

import com.mfangsoft.zhuangjialong.app.charge.model.ChargeBatchEntity;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;

@WriterRepository
public interface ChargeBatchEntityMapper {
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
    int insert(ChargeBatchEntity record);

    int insertSelective(ChargeBatchEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    ChargeBatchEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChargeBatchEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(ChargeBatchEntity record);
    
    /*
     * 根据任务号获取充值信息
     */
    ChargeBatchEntity selectByTaskId(String task_id);
    
}