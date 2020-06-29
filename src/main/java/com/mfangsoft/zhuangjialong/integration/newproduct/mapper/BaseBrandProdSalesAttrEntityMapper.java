package com.mfangsoft.zhuangjialong.integration.newproduct.mapper;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BaseBrandProdSalesAttrEntity;
@WriterRepository
public interface BaseBrandProdSalesAttrEntityMapper {
    /**
     * 通过主键删除  
     *
     * @MLTH_generated
     */
    int deleteByPrimaryKey(@Param("id")Long id);

    /**
     * 插入数据  
     *
     * @MLTH_generated
     */
    int insert(BaseBrandProdSalesAttrEntity record);

    int insertSelective(BaseBrandProdSalesAttrEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BaseBrandProdSalesAttrEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseBrandProdSalesAttrEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BaseBrandProdSalesAttrEntity record);
    
    int updateStockAfterOrder(@Param("id")Long id,@Param("stock")Long stock);
    
    int updateAddStockCancleOrder(@Param("id")Long id,@Param("stock")Integer stock);
    
}