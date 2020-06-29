package com.mfangsoft.zhuangjialong.integration.newproduct.mapper;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BaseClassAttrValueEntity;
@WriterRepository
public interface BaseClassAttrValueEntityMapper {
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
    int insert(BaseClassAttrValueEntity record);

    int insertSelective(BaseClassAttrValueEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BaseClassAttrValueEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseClassAttrValueEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BaseClassAttrValueEntity record);
}