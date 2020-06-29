package com.mfangsoft.zhuangjialong.system.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.system.model.sysConstantEntity;
@WriterRepository
public interface sysConstantEntityMapper {
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
    int insert(sysConstantEntity record);

    int insertSelective(sysConstantEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    sysConstantEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(sysConstantEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(sysConstantEntity record);
    
    sysConstantEntity getSysConstantByKey(String key);
	
	List<sysConstantEntity> getSysConstantByType(String type);
	
	
	Map<String,Object> getSysConstantByKeyForMap(String key);
	
	Map<String,Object> getSysConstantByKeyAndTypeForMap(@Param("key") String key,@Param("type") String type);
	
	List<sysConstantEntity>  getALLSysConstant();
	
}