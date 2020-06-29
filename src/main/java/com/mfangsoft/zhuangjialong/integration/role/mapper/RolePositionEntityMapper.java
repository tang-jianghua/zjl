package com.mfangsoft.zhuangjialong.integration.role.mapper;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.role.model.RoleEntity;
import com.mfangsoft.zhuangjialong.integration.role.model.RolePositionEntity;
@WriterRepository
public interface RolePositionEntityMapper {
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
    int insert(RolePositionEntity record);

    int insertSelective(RolePositionEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    RolePositionEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RolePositionEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(RolePositionEntity record);
    
    
    
    List<Long> selectPositionByRoleId(Long positionId);
    
    
    int deleteRolePosition(Map<String,Long> map);
    
    
    List<RoleEntity> selectRoleByPositionId(Long positionId);
    
}