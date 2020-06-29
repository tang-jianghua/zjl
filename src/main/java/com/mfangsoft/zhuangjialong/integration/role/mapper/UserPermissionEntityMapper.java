package com.mfangsoft.zhuangjialong.integration.role.mapper;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.role.model.UserPermissionEntity;
@WriterRepository
public interface UserPermissionEntityMapper {
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
    int insert(UserPermissionEntity record);

    int insertSelective(UserPermissionEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    UserPermissionEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserPermissionEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(UserPermissionEntity record);
    
    
    List<Long> selectPermissionByUserId(Long userId);
    
    
    int deleteUserPermission(Map<String,Long> map);
}