package com.mfangsoft.zhuangjialong.integration.entnewproduct.mapper;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.entnewproduct.model.BaseEntProdSalesAttrEntity;
@WriterRepository
public interface BaseEntProdSalesAttrEntityMapper {
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
    int insert(BaseEntProdSalesAttrEntity record);

    int insertSelective(BaseEntProdSalesAttrEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BaseEntProdSalesAttrEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseEntProdSalesAttrEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BaseEntProdSalesAttrEntity record);
    
    
    
    int updateProdSalesAttr(Long id);
}