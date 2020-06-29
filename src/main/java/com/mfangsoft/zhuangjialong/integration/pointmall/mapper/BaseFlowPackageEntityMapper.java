package com.mfangsoft.zhuangjialong.integration.pointmall.mapper;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.pointmall.model.BaseFlowPackageEntity;
@WriterRepository
public interface BaseFlowPackageEntityMapper {
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
    int insert(BaseFlowPackageEntity record);

    int insertSelective(BaseFlowPackageEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BaseFlowPackageEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseFlowPackageEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BaseFlowPackageEntity record);
}