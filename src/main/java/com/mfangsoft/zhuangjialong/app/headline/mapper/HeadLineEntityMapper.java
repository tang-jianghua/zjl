package com.mfangsoft.zhuangjialong.app.headline.mapper;

import java.util.List;

import com.mfangsoft.zhuangjialong.app.headline.model.HeadLineEntity;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
@WriterRepository
public interface HeadLineEntityMapper {
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
    int insert(HeadLineEntity record);

    int insertSelective(HeadLineEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    HeadLineEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HeadLineEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(HeadLineEntity record);
    
  /**
   * 通过状态获取头条列表  
   *
   * @MLTH_generated
   */
  List<HeadLineEntity> selectByState(Integer state);
  
  
  
  List<HeadLineEntity> selectHeadLineListForPage(Page<HeadLineEntity> page);
}