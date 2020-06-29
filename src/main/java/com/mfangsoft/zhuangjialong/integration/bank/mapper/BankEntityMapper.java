package com.mfangsoft.zhuangjialong.integration.bank.mapper;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.bank.model.BankEntity;
@WriterRepository
public interface BankEntityMapper {
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
    int insert(BankEntity record);

    int insertSelective(BankEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BankEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BankEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BankEntity record);
}