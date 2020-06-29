package com.mfangsoft.zhuangjialong.integration.enterprise.mapper;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.app.brand.model.BrandModel;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseOneEntity;
@WriterRepository
public interface EnterpriseOneEntityMapper {
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
    int insert(EnterpriseOneEntity record);

    int insertSelective(EnterpriseOneEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    EnterpriseOneEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(EnterpriseOneEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(EnterpriseOneEntity record);
    
    
    List<Map<String,Object>> getEnterpriseOneByEntIdList(Map<String,Object> map);
    
    /*
     * 通过品牌id获取一级分类
     */
    List<EnterpriseOneEntity> selectEnterpriseOneByBrandId(BrandModel brandModel);
}