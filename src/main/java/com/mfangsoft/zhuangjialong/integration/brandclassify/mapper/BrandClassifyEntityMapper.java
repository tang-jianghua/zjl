package com.mfangsoft.zhuangjialong.integration.brandclassify.mapper;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.brandclassify.model.BrandClassifyEntity;
@WriterRepository
public interface BrandClassifyEntityMapper {
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
    int insert(BrandClassifyEntity record);

    int insertSelective(BrandClassifyEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BrandClassifyEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BrandClassifyEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BrandClassifyEntity record);
    
    
    List<BrandClassifyEntity> queryBrandClassifiesForPage(Page<BrandClassifyEntity> page);
    
    
    List<BrandClassifyEntity>  queryBrandClassifies(Map<String,Object> map);
    
    /**
     * 通过品牌id获取品牌分类及系列  
     *
     * @MLTH_generated
     */
    List<BrandClassifyEntity> selectBrandClassifyAndSeries(Long brand_id);
}