package com.mfangsoft.zhuangjialong.integration.pointmall.mapper;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.pointmall.model.BaseMallFlowEntity;
@WriterRepository
public interface BaseMallFlowEntityMapper {
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
    int insert(BaseMallFlowEntity record);

    int insertSelective(BaseMallFlowEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BaseMallFlowEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BaseMallFlowEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BaseMallFlowEntity record);
}