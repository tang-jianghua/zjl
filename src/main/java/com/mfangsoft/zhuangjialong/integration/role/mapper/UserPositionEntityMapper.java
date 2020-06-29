package com.mfangsoft.zhuangjialong.integration.role.mapper;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.role.model.UserPositionEntity;
@WriterRepository
public interface UserPositionEntityMapper {
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
    int insert(UserPositionEntity record);

    int insertSelective(UserPositionEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    UserPositionEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserPositionEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(UserPositionEntity record);
    
    
    
    List<Long> selectUserByPositionId(Long positionId);
    
    
    int deleteUserPosition(Map<String,Long> map);
}