package com.mfangsoft.zhuangjialong.integration.enterprise.mapper;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseEntity;
@WriterRepository
public interface EnterpriseEntityMapper {
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
    int insert(EnterpriseEntity record);

    int insertSelective(EnterpriseEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    EnterpriseEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(EnterpriseEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(EnterpriseEntity record);
    
    
   List<Map<String,Object>> getEnterpriseForPage(Page<Map<String,Object>> page);
   
   
   
   List<EnterpriseEntity>   getEnterpriseBrandName();
   
   EnterpriseEntity getEnterpriseEntity(Long userId);
   
   List<EnterpriseEntity> getEnterpriseName();
   
   
   Map<String,Object> selectEntById(Map<String,Object> map);
   
   
   List<EnterpriseEntity> getEnterpriseByBuildId(Long id);
}