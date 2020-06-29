package com.mfangsoft.zhuangjialong.integration.role.mapper;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.role.model.RolePermissionEntity;
@WriterRepository
public interface RolePermissionEntityMapper {
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
    int insert(RolePermissionEntity record);

    int insertSelective(RolePermissionEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    RolePermissionEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RolePermissionEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(RolePermissionEntity record);
    
    
    List<Long> selectPermissionByRoleId(Long roleId);
    
    
    int deleteRolePermission(Map<String,Long> map);
}