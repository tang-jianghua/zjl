package com.mfangsoft.zhuangjialong.integration.order.mapper;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.order.model.OrderShellStateRelationEntity;
@WriterRepository
public interface OrderShellStateRelationEntityMapper {
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
    int insert(OrderShellStateRelationEntity record);

    int insertSelective(OrderShellStateRelationEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    OrderShellStateRelationEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderShellStateRelationEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(OrderShellStateRelationEntity record);
}