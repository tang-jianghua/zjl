package com.mfangsoft.zhuangjialong.integration.role.mapper;

import java.util.List;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.role.model.RoleEntity;
@WriterRepository
public interface RoleEntityMapper {
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
    int insert(RoleEntity record);

    int insertSelective(RoleEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    RoleEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RoleEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(RoleEntity record);
    
    
    List<RoleEntity>  getRoleListForPage(Page<RoleEntity> page);
}