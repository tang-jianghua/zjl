package com.mfangsoft.zhuangjialong.app.constant.mapper;

import com.mfangsoft.zhuangjialong.app.constant.model.AppConstantEntity;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
@WriterRepository
public interface AppConstantEntityMapper {
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
    int insert(AppConstantEntity record);

    int insertSelective(AppConstantEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    AppConstantEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AppConstantEntity record);

    int updateByPrimaryKeyWithBLOBs(AppConstantEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(AppConstantEntity record);
    
    /*
     * 根据key获取值
     */
    AppConstantEntity selectByKey(String key);
}