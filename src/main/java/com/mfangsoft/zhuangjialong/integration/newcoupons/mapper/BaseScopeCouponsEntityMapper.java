package com.mfangsoft.zhuangjialong.integration.newcoupons.mapper;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.newcoupons.model.BaseScopeCouponsEntity;
@WriterRepository
public interface BaseScopeCouponsEntityMapper {
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
    int insert(BaseScopeCouponsEntity record);

    int insertSelective(BaseScopeCouponsEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BaseScopeCouponsEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseScopeCouponsEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BaseScopeCouponsEntity record);
}