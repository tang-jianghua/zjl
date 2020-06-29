package com.mfangsoft.zhuangjialong.integration.newproduct.mapper;

import java.util.List;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;

@WriterRepository
public interface BrandProdImgEntityMapper extends  BaseBrandProdImgEntityMapper{

    /**
     * 通过主键查询产品轮播图
     *
     * @MLTH_generated
     */
    List<String> selectImgByProductId(Long product_id);
    
    /**
     * 通过主键查询产品案例图
     *
     * @MLTH_generated
     */
    List<String> selectCaseImgByProductId(Long product_id);
    
    
    /*
     * 通过产品id删除图片
     */
    int deleteByProductId(Long product_id);
}
