package com.mfangsoft.zhuangjialong.integration.series.mapper;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.integration.series.model.BrandSeriesEntity;

public interface BrandSeriesEntityMapper {
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
    int insert(BrandSeriesEntity record);

    int insertSelective(BrandSeriesEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BrandSeriesEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BrandSeriesEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BrandSeriesEntity record);
    
    List<BrandSeriesEntity> getBrandSeriesEntityList(Long brand_id);
    
    
    
    List<BrandSeriesEntity> queryBrandSeriesForPage(Page<BrandSeriesEntity> page);
    
    
    List<Map<String,Object>> getClassifySeries(Map<String,Object> map);
    
    
    List<BrandSeriesEntity>   getBrandSeriesListByClassify(Map<String,Object> map);
}