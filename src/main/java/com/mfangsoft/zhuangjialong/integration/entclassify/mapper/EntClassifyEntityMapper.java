package com.mfangsoft.zhuangjialong.integration.entclassify.mapper;

import java.util.List;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.entclassify.model.EntClassifyEntity;
@WriterRepository
public interface EntClassifyEntityMapper {
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
    int insert(EntClassifyEntity record);

    int insertSelective(EntClassifyEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    EntClassifyEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(EntClassifyEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(EntClassifyEntity record);
    
    
    
    List<EntClassifyEntity>  queryEntClassify(Long ent_id);
    
    
    List<Long> queryent_id(Long brand_id);
}