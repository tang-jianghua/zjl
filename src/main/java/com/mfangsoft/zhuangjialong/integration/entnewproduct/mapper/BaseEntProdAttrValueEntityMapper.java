package com.mfangsoft.zhuangjialong.integration.entnewproduct.mapper;

import java.util.List;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.entnewproduct.model.BaseEntProdAttrValueEntity;
@WriterRepository
public interface BaseEntProdAttrValueEntityMapper {
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
    int insert(BaseEntProdAttrValueEntity record);

    int insertSelective(BaseEntProdAttrValueEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BaseEntProdAttrValueEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseEntProdAttrValueEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BaseEntProdAttrValueEntity record);
    
    
    
}