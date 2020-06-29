package com.mfangsoft.zhuangjialong.integration.newproduct.mapper;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BaseBrandProdImgEntity;
@WriterRepository
public interface BaseBrandProdImgEntityMapper {
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
    int insert(BaseBrandProdImgEntity record);

    int insertSelective(BaseBrandProdImgEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BaseBrandProdImgEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseBrandProdImgEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BaseBrandProdImgEntity record);
}