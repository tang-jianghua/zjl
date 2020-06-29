package com.mfangsoft.zhuangjialong.app.applogin.mapper;

import com.mfangsoft.zhuangjialong.app.applogin.model.BaseSellerLogEntity;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;

@WriterRepository
public interface BaseSellerLogEntityMapper {
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
    int insert(BaseSellerLogEntity record);

    int insertSelective(BaseSellerLogEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BaseSellerLogEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseSellerLogEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BaseSellerLogEntity record);
    
    /*
     * 通过卖家id查询最后一次登录的日志 
     *
     */
    BaseSellerLogEntity selectLastLoginBySellerId(Long seller_id);
}