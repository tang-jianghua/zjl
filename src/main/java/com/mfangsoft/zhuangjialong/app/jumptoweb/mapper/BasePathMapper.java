package com.mfangsoft.zhuangjialong.app.jumptoweb.mapper;

import java.util.List;

import com.mfangsoft.zhuangjialong.app.jumptoweb.model.BasePath;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;

@WriterRepository
public interface BasePathMapper {
    /**
     * 通过主键删除  
     *
     * @MLTH_generated
     */
    int deleteByPrimaryKey(Integer type);

    /**
     * 插入数据  
     *
     * @MLTH_generated
     */
    int insert(BasePath record);

    int insertSelective(BasePath record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    String selectByPrimaryKey(Integer type);

    int updateByPrimaryKeySelective(BasePath record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BasePath record);
    
    /*
     * 根据分类查询
     */
    List<BasePath> selectByFlagType(Integer flag_type);
}