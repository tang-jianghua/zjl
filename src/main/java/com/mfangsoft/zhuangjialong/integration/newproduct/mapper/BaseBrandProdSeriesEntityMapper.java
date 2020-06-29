package com.mfangsoft.zhuangjialong.integration.newproduct.mapper;

import java.util.List;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BaseBrandProdSeriesEntity;
@WriterRepository
public interface BaseBrandProdSeriesEntityMapper {
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
    int insert(BaseBrandProdSeriesEntity record);

    int insertSelective(BaseBrandProdSeriesEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BaseBrandProdSeriesEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseBrandProdSeriesEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BaseBrandProdSeriesEntity record);
    
    
    int deleteProdSeriesByProductId(Long product_id); 
    
    
    List<BaseBrandProdSeriesEntity> selectBySeriesId(Long seriesId);
    
    
    
}