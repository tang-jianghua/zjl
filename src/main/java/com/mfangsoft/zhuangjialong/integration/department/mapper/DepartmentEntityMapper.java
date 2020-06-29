package com.mfangsoft.zhuangjialong.integration.department.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.department.model.DepartmentEntity;
@WriterRepository
public interface DepartmentEntityMapper {
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
    int insert(DepartmentEntity record);

    int insertSelective(DepartmentEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    DepartmentEntity selectByPrimaryKey(@Param("id")Long id);

    int updateByPrimaryKeySelective(DepartmentEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(DepartmentEntity record);
    
    
    List<DepartmentEntity> selectAll(Map<String,Object> map);
    
    
    List<DepartmentEntity> getDepForCombox(@Param("parent_id") Long parent_id,@Param("user_type") Long role_id);
    
    
    List<DepartmentEntity> getPositionForCombox(Long id);
    
    
    
    
    
}