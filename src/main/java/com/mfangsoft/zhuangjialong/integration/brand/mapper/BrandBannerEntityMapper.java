package com.mfangsoft.zhuangjialong.integration.brand.mapper;

import java.util.List;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandBannerEntity;

@WriterRepository
public interface BrandBannerEntityMapper {
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
    int insert(BrandBannerEntity record);

    int insertSelective(BrandBannerEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BrandBannerEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BrandBannerEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BrandBannerEntity record);
    
    /**
     * 通过品牌id获取banner数组
     *
     * @MLTH_generated
     */
    List<BrandBannerEntity> selectByBrandId(Long brand_id);
    
    
    List<BrandBannerEntity> queryBrandBanner(Long brand_id);
}