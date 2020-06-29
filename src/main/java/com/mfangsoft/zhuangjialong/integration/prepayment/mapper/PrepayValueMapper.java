package com.mfangsoft.zhuangjialong.integration.prepayment.mapper;

import java.util.List;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.prepayment.model.PrepayValue;

@WriterRepository
public interface PrepayValueMapper {
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
    int insert(PrepayValue record);

    int insertSelective(PrepayValue record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    PrepayValue selectByPrimaryKey(Integer id);
    
    /**
     * 查询所有金额 
     *
     * @MLTH_generated
     */
    List<PrepayValue> selectAll();

    int updateByPrimaryKeySelective(PrepayValue record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(PrepayValue record);
}