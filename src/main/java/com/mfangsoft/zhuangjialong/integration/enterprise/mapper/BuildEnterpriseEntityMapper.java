package com.mfangsoft.zhuangjialong.integration.enterprise.mapper;

import java.util.List;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.BuildEnterpriseEntity;
@WriterRepository
public interface BuildEnterpriseEntityMapper {
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
    int insert(BuildEnterpriseEntity record);

    int insertSelective(BuildEnterpriseEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BuildEnterpriseEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BuildEnterpriseEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BuildEnterpriseEntity record);
    
    List<BuildEnterpriseEntity> getBuildEnterpriseList();
    
    
    
    List<BuildEnterpriseEntity> getBuildEnterpriseListByclassId(Long class_id);
}