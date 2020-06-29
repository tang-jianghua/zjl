package com.mfangsoft.zhuangjialong.integration.enterprise.mapper;

import java.util.List;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.BuildMaterialsEntity;
@WriterRepository
public interface BuildMaterialsEntityMapper {
    /**
     * 通过主键删除  
     *
     * @MLTH_generated
     */
    int deleteByPrimaryKey(Long ID);

    /**
     * 插入数据  
     *
     * @MLTH_generated
     */
    int insert(BuildMaterialsEntity record);

    int insertSelective(BuildMaterialsEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BuildMaterialsEntity selectByPrimaryKey(Long ID);

    int updateByPrimaryKeySelective(BuildMaterialsEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BuildMaterialsEntity record);
    
    List<BuildMaterialsEntity>  getBuildMaterialsList();
    
}