package com.mfangsoft.zhuangjialong.integration.pointmall.mapper;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.pointmall.model.BasePointConvertEntity;
@WriterRepository
public interface BasePointConvertEntityMapper {
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
    int insert(BasePointConvertEntity record);

    int insertSelective(BasePointConvertEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BasePointConvertEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BasePointConvertEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BasePointConvertEntity record);
}