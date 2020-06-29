package com.mfangsoft.zhuangjialong.integration.column.service;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.app.article.model.AbumEntity;
import com.mfangsoft.zhuangjialong.app.article.model.ArticleEntity;
import com.mfangsoft.zhuangjialong.app.headline.model.HeadLineEntity;
import com.mfangsoft.zhuangjialong.app.main.model.CaseEntity;
import com.mfangsoft.zhuangjialong.app.main.model.ColumnImg;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.integration.column.model.ArticleModel;

public interface HeadLineService {
	
	
	
	Boolean addHeadLine(HeadLineEntity headLineEntity);
	
	
	Boolean modfiyHeadLine(HeadLineEntity headLineEntity);
	
	
	HeadLineEntity getHeadLineByid(Long id);
	
	
	Boolean removeHeadLine(Long id);
	
	
	Page<HeadLineEntity>    selectHeadLineListForPage(Page<HeadLineEntity> page);
	
	
	
	
	Boolean addHeadContent(ArticleEntity headLineEntity);
	
	
	Boolean modfiyHeadContent(ArticleEntity headLineEntity);
	
	
	
	Boolean removeHeadContent(Long id);
	
	
	Page<ArticleModel>    selectHeadContentListForPage(Page<ArticleModel> page);
	
	
	Boolean modfiyColumnImage(ColumnImg columnImg);
	
	
	Boolean addAbum(AbumEntity abumEntity);
	
	
	Boolean modfiyAbum(AbumEntity abumEntity);
	
	
	Boolean removeAbum(Long id);
	
	
	AbumEntity getAbumByid(Long id);
	
	Page<Map<String, Object>> selectAbumForPage(Page<Map<String, Object>> page);
	
	
	List<AbumEntity> queryAbumList();
	
	List<AbumEntity> queryAdminAbumList();
	
	
	/**
	 * 添加案例
	 * @param caseEntity
	 * @return
	 */
	Boolean addCase(CaseEntity caseEntity);
	
	/**
	 * 修改案例
	 * @param caseEntity
	 * @return
	 */
	Boolean modfiyCase(CaseEntity caseEntity);
	
	
	/**
	 * 删除案例
	 * @param id
	 * @return
	 */
	Boolean removeCase(Long id);
	
	/**
	 * 案例分页查询
	 */
	Page<CaseEntity>    selectCaseListForPage(Page<CaseEntity> page);
	
	
	ArticleEntity getHeadcontentById(Long id);
	
	/**
	 * 获取单个案例
	 * @param id
	 * @return
	 */
	CaseEntity getCaseById(Long id);
	
	
	
	
	

}
