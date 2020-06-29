package com.mfangsoft.zhuangjialong.integration.entnewproduct.mapper;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.entnewproduct.model.BaseEntProdImgEntity;
@WriterRepository
public interface BaseEntProdImgEntityMapper {
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
    int insert(BaseEntProdImgEntity record);

    int insertSelective(BaseEntProdImgEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BaseEntProdImgEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseEntProdImgEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BaseEntProdImgEntity record);
}