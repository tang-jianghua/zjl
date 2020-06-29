package com.mfangsoft.zhuangjialong.integration.order.mapper;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.order.model.OrderShellListEntity;
@WriterRepository
public interface OrderShellListEntityMapper {
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
    int insert(OrderShellListEntity record);

    Long insertSelective(OrderShellListEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    OrderShellListEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderShellListEntity record);

    int updateByPrimaryKeyWithBLOBs(OrderShellListEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(OrderShellListEntity record);
    
    List<Map<String,Object>> queryShellOrderListByPage(Page<Map<String,Object>> page);
    
    Map<String,Object> queryOneShellOrderById(Long id);
}