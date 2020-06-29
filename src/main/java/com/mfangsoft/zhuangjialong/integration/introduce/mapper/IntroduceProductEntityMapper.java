package com.mfangsoft.zhuangjialong.integration.introduce.mapper;

import java.util.List;
import java.util.Map;


import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.introduce.model.IntroduceClass;
import com.mfangsoft.zhuangjialong.integration.introduce.model.IntroduceProductEntity;
@WriterRepository
public interface IntroduceProductEntityMapper {
    /**
     * 通过主键删除  
     *
     * @MLTH_generated
     */
    int deleteByPrimaryKey(Long id);
    
    int deleteByIntroduceId(Long introduce_id);
    
    /**
     * 插入数据  
     *
     * @MLTH_generated
     */
    int insert(IntroduceProductEntity record);

    int insertSelective(IntroduceProductEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    IntroduceProductEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(IntroduceProductEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(IntroduceProductEntity record);
    
    List<IntroduceProductEntity> selectAllByIntroduceId(Long introduce_id);
    
    List<IntroduceClass> selectClassAndProduct(Long partner_id);
    
    /*
     * 根据推荐id获取产品id
     */
    List<Map<String,Object>> selectProductIdsByIntroduceId(Long introduct_id);
    
    /*
     * 获取当地区的推荐产品id
     */
    List<Long>  selectIntroduceProductIds(String region_code);
    
    /*
     * 查询某品牌推荐产品
     */
    int selectCountByBrandId(Long brand_id);
}