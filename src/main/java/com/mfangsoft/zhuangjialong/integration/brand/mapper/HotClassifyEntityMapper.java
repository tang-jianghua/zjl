package com.mfangsoft.zhuangjialong.integration.brand.mapper;

import java.util.List;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.brand.model.HotClassifyEntity;
@WriterRepository
public interface HotClassifyEntityMapper {
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
    int insert(HotClassifyEntity record);

    int insertSelective(HotClassifyEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    HotClassifyEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HotClassifyEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(HotClassifyEntity record);
    
    List<HotClassifyEntity>  queryHotClassify(Long brand_id);
}