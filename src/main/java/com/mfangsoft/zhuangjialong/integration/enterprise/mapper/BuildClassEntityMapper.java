package com.mfangsoft.zhuangjialong.integration.enterprise.mapper;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.app.DIYdesign.model.ClassBrandModel;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.BuildClassEntity;

@WriterRepository
public interface BuildClassEntityMapper {
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
    int insert(BuildClassEntity record);

    int insertSelective(BuildClassEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BuildClassEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BuildClassEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BuildClassEntity record);
    
    /**
     * 通过区域编码获取品类列表
     *
     * @MLTH_generated
     */
    List<BuildClassEntity> selectClassesByRegion(Map<String, String> map);
    
    /**
     * 通过区域编码获取diy品类列表
     *
     * @MLTH_generated
     */
    List<ClassBrandModel> selectDIYClassesByRegion(Map<String, String> map);
    /**
     * 查询所有品类
     * @return
     */
    List<BuildClassEntity> geBuildClassEntities();
    
    /**
     * 查询所有品类
     * @return
     */
    List<BuildClassEntity> selectAllClasses();
    
    
    
    List<BuildClassEntity> geBuildClassEntitiesBybuildId(Long build_id);
    
    
   List<Map<String,Object>> getClassifyList();
}