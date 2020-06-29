package com.mfangsoft.zhuangjialong.integration.series.mapper;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.series.model.EntSeriesEntity;
@WriterRepository
public interface EntSeriesEntityMapper {
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
    int insert(EntSeriesEntity record);

    int insertSelective(EntSeriesEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    EntSeriesEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(EntSeriesEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(EntSeriesEntity record);
    
    
    List<Map<String,Object>> getClassifySeries(Map<String,Object> map);
    
    List<EntSeriesEntity>  getEntSeriesListByClassify(Map<String,Object> map);
}