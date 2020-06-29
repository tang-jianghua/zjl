package com.mfangsoft.zhuangjialong.integration.enterprise.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.BaseEnterpriseTwoEntity;

@WriterRepository
public interface BaseEnterpriseTwoEntityMapper {
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
    int insert(BaseEnterpriseTwoEntity record);

    int insertSelective(BaseEnterpriseTwoEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BaseEnterpriseTwoEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseEnterpriseTwoEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BaseEnterpriseTwoEntity record);
}