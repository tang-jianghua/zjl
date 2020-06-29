package com.mfangsoft.zhuangjialong.app.personalCenter.mapper;


import com.mfangsoft.zhuangjialong.app.personalCenter.model.Point;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.PointEntity;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
@WriterRepository
public interface PointEntityMapper {
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
    int insert(PointEntity record);

    int insertSelective(PointEntity record);
    
    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    PointEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PointEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(PointEntity record);
    
    /**
     * 通过消费者id获取数据  
     *
     * @MLTH_generated
     */
    PointEntity selectByCustomerId(Long customer_id);
    
}