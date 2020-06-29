package com.mfangsoft.zhuangjialong.integration.permission.mapper;

import java.util.List;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.permission.model.PermissionEntity;

@WriterRepository
public interface PermissionEntityMapper {
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
    int insert(PermissionEntity record);

    int insertSelective(PermissionEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    PermissionEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PermissionEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(PermissionEntity record);
    
    List<PermissionEntity> selectAll();
    
    
    List<PermissionEntity> getPermissionByUserId(Long userId);
    
    
    int deletePermissionByRoleId(Long role_id);
    
}