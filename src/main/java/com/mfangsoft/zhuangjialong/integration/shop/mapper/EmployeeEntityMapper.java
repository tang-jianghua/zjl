package com.mfangsoft.zhuangjialong.integration.shop.mapper;

import java.util.List;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.shop.model.EmployeeEntity;
@WriterRepository
public interface EmployeeEntityMapper {
    /**
     * 通过主键删除  
     *
     * @MLTH_generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入数据  
     *
     * @MLTH_generated
     */
    int insert(EmployeeEntity record);

    int insertSelective(EmployeeEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    EmployeeEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EmployeeEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(EmployeeEntity record);
    
    
    List<EmployeeEntity>  getEmployeeList(Long id);
    
    
    
    List<EmployeeEntity> selectEmployeeListForPage(Page<EmployeeEntity> page);
}