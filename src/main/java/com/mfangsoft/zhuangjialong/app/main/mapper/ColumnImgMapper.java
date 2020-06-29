package com.mfangsoft.zhuangjialong.app.main.mapper;

import java.util.List;

import com.mfangsoft.zhuangjialong.app.main.model.ColumnImg;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
@WriterRepository
public interface ColumnImgMapper {
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
    int insert(ColumnImg record);

    int insertSelective(ColumnImg record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    ColumnImg selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ColumnImg record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(ColumnImg record);
    
	  /**
     * 获取栏目图
     *
     * @MLTH_generated
     */
    List<ColumnImg> selectByImgType(Integer img_type);
    
    
    List<ColumnImg> selectAllByImgType(Integer img_type);
}