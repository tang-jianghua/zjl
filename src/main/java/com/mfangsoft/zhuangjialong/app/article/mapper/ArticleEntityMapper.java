package com.mfangsoft.zhuangjialong.app.article.mapper;

import java.util.List;

import com.mfangsoft.zhuangjialong.app.article.model.ArticleEntity;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.column.model.ArticleModel;

@WriterRepository
public interface ArticleEntityMapper {
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
    int insert(ArticleEntity record);

    int insertSelective(ArticleEntity record);

   String  selectImageByPrimaryKey(Long id);
    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    ArticleEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleEntity record);

    int updateByPrimaryKeyWithBLOBs(ArticleEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(ArticleEntity record);
    
    /**
     * 查询文章列表
     *
     * @MLTH_generated
     */
    List<ArticleEntity> selectArticlesForPage(Page<ArticleEntity> param);
    
    
    /**
     * 查询文章详情
     *
     * @MLTH_generated
     */
    ArticleEntity selectDetailsByPrimaryKey(Long id);
    
    /**
     * 用于服务端的分页查询
     * @param param
     * @return
     */
    List<ArticleModel> selectArticlesServerForPage(Page<ArticleModel> param);
    
    
    
    List<ArticleEntity> selectArticlesServerByAbumId(Long id);
    
    /*
     * 更新浏览量
     */
    int updateSeenTime(Long id);
    
    /*
     * 更新分享量
     */
    int updateShareNum(Long id);
}