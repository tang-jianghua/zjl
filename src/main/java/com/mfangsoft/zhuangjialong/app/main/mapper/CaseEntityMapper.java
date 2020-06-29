package com.mfangsoft.zhuangjialong.app.main.mapper;

import java.util.List;

import com.mfangsoft.zhuangjialong.app.article.model.ArticleEntity;
import com.mfangsoft.zhuangjialong.app.main.model.CaseEntity;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
@WriterRepository
public interface CaseEntityMapper {
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
    int insert(CaseEntity record);

    int insertSelective(CaseEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    CaseEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CaseEntity record);

    int updateByPrimaryKeyWithBLOBs(CaseEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(CaseEntity record);

    /**
     *   通过主键获取案例详情
     *
     * @MLTH_generated
     */
    List<CaseEntity> selectCaseList(String region_code);
   
    /**
     *   通过id获取案例详情
     *
     * @MLTH_generated
     */
    ArticleEntity selectForCaseDetails(Long id);
    
    
    
    List<CaseEntity> selectCaseListForPage(Page<CaseEntity> page);
}