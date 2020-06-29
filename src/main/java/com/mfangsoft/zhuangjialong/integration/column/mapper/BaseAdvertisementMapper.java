package com.mfangsoft.zhuangjialong.integration.column.mapper;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.column.model.BaseAdvertisement;

@WriterRepository
public interface BaseAdvertisementMapper {
    /**
     * 通过主键删除  
     *
     * @MLTH_generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入数据  
     *
     * @MLTH_generated
     */
    int insert(BaseAdvertisement record);

    int insertSelective(BaseAdvertisement record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BaseAdvertisement selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BaseAdvertisement record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BaseAdvertisement record);
}