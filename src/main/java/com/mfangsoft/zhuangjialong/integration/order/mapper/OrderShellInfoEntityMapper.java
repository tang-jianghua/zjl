package com.mfangsoft.zhuangjialong.integration.order.mapper;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.order.model.OrderShellInfoEntity;
@WriterRepository
public interface OrderShellInfoEntityMapper {
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
    int insert(OrderShellInfoEntity record);

    int insertSelective(OrderShellInfoEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    OrderShellInfoEntity selectByPrimaryKey(Integer id);
    
    OrderShellInfoEntity selectData();

    OrderShellInfoEntity selectWholeData();
    
    /**
     * 已修改，去掉where
     */
    int updateByPrimaryKeySelective(OrderShellInfoEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(OrderShellInfoEntity record);
}