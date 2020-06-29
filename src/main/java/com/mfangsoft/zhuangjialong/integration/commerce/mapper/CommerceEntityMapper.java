package com.mfangsoft.zhuangjialong.integration.commerce.mapper;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.commerce.model.CommerceEntity;
@WriterRepository
public interface CommerceEntityMapper {
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
    int insert(CommerceEntity record);

    int insertSelective(CommerceEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    CommerceEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CommerceEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(CommerceEntity record);
}