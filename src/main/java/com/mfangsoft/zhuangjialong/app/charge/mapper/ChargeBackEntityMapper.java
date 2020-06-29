package com.mfangsoft.zhuangjialong.app.charge.mapper;

import com.mfangsoft.zhuangjialong.app.charge.model.ChargeBackEntity;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;

@WriterRepository
public interface ChargeBackEntityMapper {
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
    int insert(ChargeBackEntity record);

    int insertSelective(ChargeBackEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    ChargeBackEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChargeBackEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(ChargeBackEntity record);
}