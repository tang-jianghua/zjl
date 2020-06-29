package com.mfangsoft.zhuangjialong.integration.brand.mapper;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.brand.model.HotClassifyProductEntity;
@WriterRepository
public interface HotClassifyProductEntityMapper {
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
    int insert(HotClassifyProductEntity record);

    int insertSelective(HotClassifyProductEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    HotClassifyProductEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HotClassifyProductEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(HotClassifyProductEntity record);
}