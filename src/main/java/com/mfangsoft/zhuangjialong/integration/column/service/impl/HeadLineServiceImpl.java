package com.mfangsoft.zhuangjialong.integration.column.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.article.mapper.AbumEntityMapper;
import com.mfangsoft.zhuangjialong.app.article.mapper.ArticleCollectionMapper;
import com.mfangsoft.zhuangjialong.app.article.mapper.ArticleEntityMapper;
import com.mfangsoft.zhuangjialong.app.article.mapper.ArticleEvaluationMapper;
import com.mfangsoft.zhuangjialong.app.article.mapper.ArticleLikesMapper;
import com.mfangsoft.zhuangjialong.app.article.model.AbumEntity;
import com.mfangsoft.zhuangjialong.app.article.model.ArticleEntity;
import com.mfangsoft.zhuangjialong.app.headline.mapper.HeadLineEntityMapper;
import com.mfangsoft.zhuangjialong.app.headline.model.HeadLineEntity;
import com.mfangsoft.zhuangjialong.app.main.mapper.CaseEntityMapper;
import com.mfangsoft.zhuangjialong.app.main.mapper.ColumnImgMapper;
import com.mfangsoft.zhuangjialong.app.main.model.CaseEntity;
import com.mfangsoft.zhuangjialong.app.main.model.ColumnImg;
import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.integration.column.model.ArticleModel;
import com.mfangsoft.zhuangjialong.integration.column.service.HeadLineService;
import com.mfangsoft.zhuangjialong.integration.enums.UserType;
import com.mfangsoft.zhuangjialong.integration.user.model.UserEntity;

@Service
public class HeadLineServiceImpl implements HeadLineService {

	@Autowired
	private HeadLineEntityMapper headLineEntityMapper;
	@Autowired
	private ArticleEntityMapper articleEntityMapper;
	@Autowired
	private ColumnImgMapper columnImgMapper;
	@Autowired
	private CaseEntityMapper caseEntityMapper;
	@Autowired
	private AbumEntityMapper abumEntityMapper;
	@Autowired
	private ArticleCollectionMapper articleCollectionMapper;
	@Autowired
	private ArticleLikesMapper articleLikesMapper;
	@Autowired
	private ArticleEvaluationMapper articleEvaluationMapper;

	@Override
	public Boolean addHeadLine(HeadLineEntity headLineEntity) {
		// TODO Auto-generated method stub

		
		headLineEntity.setCreater(UserContext.getCurrentUserId()+"");
		if (headLineEntityMapper.insertSelective(headLineEntity) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Boolean modfiyHeadLine(HeadLineEntity headLineEntity) {
		// TODO Auto-generated method stub

		headLineEntity.setEdit_time(new Date());
		if (headLineEntityMapper.updateByPrimaryKeySelective(headLineEntity) > 0) {
			return true;
		}
		return false;

	}

	@Override
	public Boolean removeHeadLine(Long id) {
		// TODO Auto-generated method stub
		if (headLineEntityMapper.deleteByPrimaryKey(id) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Page<HeadLineEntity> selectHeadLineListForPage(Page<HeadLineEntity> page) {
		// TODO Auto-generated method stub

		page.setData(headLineEntityMapper.selectHeadLineListForPage(page));

		return page;

	}

	@Override
	public Boolean addHeadContent(ArticleEntity articleEntity) {
		// TODO Auto-generated method stub

		if (articleEntityMapper.insertSelective(articleEntity) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Boolean modfiyHeadContent(ArticleEntity articleEntity) {
		// TODO Auto-generated method stub
		articleEntity.setUpdate_time(new Date());
		if (articleEntityMapper.updateByPrimaryKeySelective(articleEntity)>0) {

			return true;
		}
		return false;
	}

	@Override
	public Boolean removeHeadContent(Long id) {
		// TODO Auto-generated method stub
		if (articleEntityMapper.deleteByPrimaryKey(id) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Page<ArticleModel> selectHeadContentListForPage(Page<ArticleModel> page) {
		// TODO Auto-generated method stub
		//不同后台用户看到自己添加的图文列表
		Map<String, Object> map = new HashMap<>();
		UserEntity entity= UserContext.getCurrentUser();
		if (page.getParam() != null){
			if(entity.getUser_type().intValue()==UserType.PARTNER.getIndex().intValue()){
				
				
				map = (Map<String, Object>) page.getParam();
				map.put("user_type", UserContext.getCurrentUser().getUser_type());
				map.put("user_id", entity.getId());
				
			}else{
				
				map = (Map<String, Object>) page.getParam();
				map.put("user_type", UserContext.getCurrentUser().getUser_type());
				//map.put("user_id", entity.getId());
			}
			
			
			
		}
		page.setParam(map);
		List<ArticleModel> articles = articleEntityMapper.selectArticlesServerForPage(page);
		for (ArticleModel articleModel : articles) {
			articleModel.setCollection_num(articleCollectionMapper.selectArticleCollectionNum(articleModel.getId()));
			articleModel.setLikes_num(articleLikesMapper.selectArticleLikesNum(articleModel.getId()));
			articleModel.setEvaluation_num(articleEvaluationMapper.selectArticleEvaluationNum(articleModel.getId()));
		}
		page.setData(articles);
		return page;
	}

	@Override
	public Boolean modfiyColumnImage(ColumnImg columnImg) {
		// TODO Auto-generated method stub

		if (columnImgMapper.updateByPrimaryKeySelective(columnImg) > 0) {

			return true;
		}
		return false;
	}

	@Override
	public Boolean addCase(CaseEntity caseEntity) {
		// TODO Auto-generated method stub
		
		caseEntity.setCreate_time(new Date());
		caseEntity.setState(1);
		if (caseEntityMapper.insertSelective(caseEntity) > 0) {

			return true;
		}
		return false;
	}

	@Override
	public Boolean modfiyCase(CaseEntity caseEntity) {
		// TODO Auto-generated method stub
		if (caseEntityMapper.updateByPrimaryKeySelective(caseEntity) > 0) {

			return true;
		}
		return false;
	}

	@Override
	public Boolean removeCase(Long id) {
		// TODO Auto-generated method stub
		if (caseEntityMapper.deleteByPrimaryKey(id) > 0) {

			return true;
		}
		return false;
	}

	@Override
	public Page<CaseEntity> selectCaseListForPage(Page<CaseEntity> page) {
		// TODO Auto-generated method stub
		page.setData(caseEntityMapper.selectCaseListForPage(page));
		return page;
	}

	@Override
	public Boolean addAbum(AbumEntity abumEntity) {
		// TODO Auto-generated method stub
		
		abumEntity.setCreate_time(new Date());
		if(abumEntityMapper.insertSelective(abumEntity)>0){
			return true;
		}
		return false;
	}

	@Override
	public Page<Map<String, Object>> selectAbumForPage(Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		UserEntity entity= UserContext.getCurrentUser();
		if (page.getParam() != null){
			if(entity.getUser_type().intValue()==UserType.PARTNER.getIndex().intValue()){
				
				
				map = (Map<String, Object>) page.getParam();
				map.put("user_type", UserContext.getCurrentUser().getUser_type());
				map.put("user_id", entity.getId());
				
			}else{
				
				map = (Map<String, Object>) page.getParam();
				map.put("user_type", UserContext.getCurrentUser().getUser_type());
				//map.put("user_id", entity.getId());
			}
			
			
			
		}
		page.setParam(map);
		page.setData(abumEntityMapper.selectAbumForPage(page));
		return page;
	}
	
	public List<AbumEntity> queryAbumList()
	{
		
		return abumEntityMapper.queryAbumList();
	}
	@Override
	public List<AbumEntity> queryAdminAbumList() {
		
		return abumEntityMapper.queryAdminAbumList(UserContext.getCurrentUserId());
	}
	
	@Override
	public ArticleEntity getHeadcontentById(Long id) {
		// TODO Auto-generated method stub
		
		ArticleEntity  articleEntity=articleEntityMapper.selectByPrimaryKey(id);
		
		AbumEntity abumEntity=abumEntityMapper.selectByPrimaryKey(articleEntity.getAbum_id());
		
		articleEntity.setAbum_name(abumEntity.getName());
		
		return articleEntity;
	}

	@Override
	public HeadLineEntity getHeadLineByid(Long id) {
		// TODO Auto-generated method stub
		return headLineEntityMapper.selectByPrimaryKey(id);
	}

	@Override
	public Boolean modfiyAbum(AbumEntity abumEntity) {
		// TODO Auto-generated method stub
		abumEntity.setUpdate_time(new Date());
		if(abumEntityMapper.updateByPrimaryKeySelective(abumEntity)>0){
			return true;
		}
		return false;
	}

	@Override
	public Boolean removeAbum(Long id) {
		// TODO Auto-generated method stub
		abumEntityMapper.deleteByPrimaryKey(id);
		List<ArticleEntity> list=articleEntityMapper.selectArticlesServerByAbumId(id);
		
		for (ArticleEntity articleEntity : list) {
			articleEntityMapper.deleteByPrimaryKey(articleEntity.getId());
		}
		
		return true;
	}

	@Override
	public AbumEntity getAbumByid(Long id) {
		// TODO Auto-generated method stub
		return abumEntityMapper.selectByPrimaryKey(id);
	}

	@Override
	public CaseEntity getCaseById(Long id) {
		// TODO Auto-generated method stub
		return caseEntityMapper.selectByPrimaryKey(id);
	}

}
