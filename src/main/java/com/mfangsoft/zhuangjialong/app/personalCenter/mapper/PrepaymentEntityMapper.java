package com.mfangsoft.zhuangjialong.app.personalCenter.mapper;

import com.mfangsoft.zhuangjialong.app.personalCenter.model.PrepaymentEntity;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;


@WriterRepository
public interface PrepaymentEntityMapper {
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
    int insert(PrepaymentEntity record);

    int insertSelective(PrepaymentEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    PrepaymentEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PrepaymentEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(PrepaymentEntity record);
}