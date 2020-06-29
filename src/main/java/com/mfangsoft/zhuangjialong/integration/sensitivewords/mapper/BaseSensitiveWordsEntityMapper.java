package com.mfangsoft.zhuangjialong.integration.sensitivewords.mapper;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.sensitivewords.model.BaseSensitiveWordsEntity;
@WriterRepository
public interface BaseSensitiveWordsEntityMapper {
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
    int insert(BaseSensitiveWordsEntity record);

    int insertSelective(BaseSensitiveWordsEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BaseSensitiveWordsEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BaseSensitiveWordsEntity record);

    int updateByPrimaryKeyWithBLOBs(BaseSensitiveWordsEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BaseSensitiveWordsEntity record);
}