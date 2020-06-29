package com.mfangsoft.zhuangjialong.app.helpfeedback.mapper;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.app.helpfeedback.model.HelpFeedBackEntity;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
@WriterRepository
public interface HelpFeedBackEntityMapper {
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
    int insert(HelpFeedBackEntity record);

    int insertSelective(HelpFeedBackEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    HelpFeedBackEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HelpFeedBackEntity record);

    int updateByPrimaryKeyWithBLOBs(HelpFeedBackEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(HelpFeedBackEntity record);
    
    
    List<Map<String,Object>> getHelpFeedBackForPage(Page<Map<String,Object>> page);
}