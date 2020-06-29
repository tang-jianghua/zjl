package com.mfangsoft.zhuangjialong.integration.newcoupons.mapper;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.newcoupons.model.BaseCustomerCouponsEntity;
@WriterRepository
public interface BaseCustomerCouponsEntityMapper {
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
    int insert(BaseCustomerCouponsEntity record);

    int insertSelective(BaseCustomerCouponsEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BaseCustomerCouponsEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseCustomerCouponsEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BaseCustomerCouponsEntity record);
}