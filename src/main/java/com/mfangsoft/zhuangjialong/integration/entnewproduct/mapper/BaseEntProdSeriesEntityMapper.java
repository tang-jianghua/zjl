package com.mfangsoft.zhuangjialong.integration.entnewproduct.mapper;

import java.util.List;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.entnewproduct.model.BaseEntProdSeriesEntity;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BaseBrandProdSeriesEntity;
@WriterRepository
public interface BaseEntProdSeriesEntityMapper {
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
    int insert(BaseEntProdSeriesEntity record);

    int insertSelective(BaseEntProdSeriesEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BaseEntProdSeriesEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseEntProdSeriesEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BaseEntProdSeriesEntity record);
    
    int deleteProdSeriesByProductId(Long product_id); 
    
    List<BaseEntProdSeriesEntity> selectBySeriesId(Long seriesId);
    
}