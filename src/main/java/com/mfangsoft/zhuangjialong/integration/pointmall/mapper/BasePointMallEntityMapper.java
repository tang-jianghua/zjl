package com.mfangsoft.zhuangjialong.integration.pointmall.mapper;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.pointmall.model.BasePointMallEntity;
@WriterRepository
public interface BasePointMallEntityMapper {
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
    int insert(BasePointMallEntity record);

    int insertSelective(BasePointMallEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BasePointMallEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BasePointMallEntity record);

    int updateByPrimaryKeyWithBLOBs(BasePointMallEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BasePointMallEntity record);
}