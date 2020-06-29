package com.mfangsoft.zhuangjialong.integration.brand.mapper;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.brand.model.BaseBrandStateApplyEntity;

@WriterRepository
public interface BaseBrandStateApplyEntityMapper {
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
    int insert(BaseBrandStateApplyEntity record);

    int insertSelective(BaseBrandStateApplyEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BaseBrandStateApplyEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BaseBrandStateApplyEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BaseBrandStateApplyEntity record);
}