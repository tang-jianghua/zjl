package com.mfangsoft.zhuangjialong.integration.role.mapper;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.role.model.UserRoleEntity;
@WriterRepository
public interface UserRoleEntityMapper {
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
    int insert(UserRoleEntity record);

    int insertSelective(UserRoleEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    UserRoleEntity selectByPrimaryKey(Long id);
    
    UserRoleEntity selectUserRoleByUserId(Long id);
    
    UserRoleEntity selectUserRoleByRoleId(Long id);

    int updateByPrimaryKeySelective(UserRoleEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(UserRoleEntity record);
}