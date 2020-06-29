package com.mfangsoft.zhuangjialong.app.article.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.article.mapper.AbumEntityMapper;
import com.mfangsoft.zhuangjialong.app.article.mapper.ArticleCollectionMapper;
import com.mfangsoft.zhuangjialong.app.article.mapper.ArticleEntityMapper;
import com.mfangsoft.zhuangjialong.app.article.mapper.ArticleEvaluationMapper;
import com.mfangsoft.zhuangjialong.app.article.mapper.ArticleLikesMapper;
import com.mfangsoft.zhuangjialong.app.article.mapper.MainArticleColumnMapper;
import com.mfangsoft.zhuangjialong.app.article.model.Album;
import com.mfangsoft.zhuangjialong.app.article.model.AppArticleEvaluationModel;
import com.mfangsoft.zhuangjialong.app.article.model.ArticleDetailModel;
import com.mfangsoft.zhuangjialong.app.article.model.ArticleEntity;
import com.mfangsoft.zhuangjialong.app.article.model.BaseArticleCollectionEntity;
import com.mfangsoft.zhuangjialong.app.article.model.BaseArticleEvaluationEntity;
import com.mfangsoft.zhuangjialong.app.article.model.BaseArticleLikesEntity;
import com.mfangsoft.zhuangjialong.app.article.model.BaseMainArticleColumnEntity;
import com.mfangsoft.zhuangjialong.app.article.service.ArticleService;
import com.mfangsoft.zhuangjialong.app.main.mapper.ColumnImgMapper;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.EmojiUtil;
import com.mfangsoft.zhuangjialong.core.quartz.Quest;
import com.mfangsoft.zhuangjialong.core.quartz.QuestsManagerBean;
import com.mfangsoft.zhuangjialong.integration.sensitivewords.util.SensitiveUtil;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年7月5日
* 
*/

@Service
public class ArticleServiceImpl implements ArticleService{
    
	@Autowired
	private ArticleEntityMapper articleEntityMapper;
	@Autowired
	private AbumEntityMapper abumEntityMapper;
	@Autowired
	private MainArticleColumnMapper mainArticleColumnMapper;
	@Autowired
	private ArticleLikesMapper articleLikesMapper;
	@Autowired
	private ArticleCollectionMapper articleCollectionMapper;
	@Autowired
	private ArticleEvaluationMapper articleEvaluationMapper;
	@Override
	public Album queryAlbum(Page<ArticleEntity> param) {
		Map<String, Integer> map=(Map<String, Integer>)param.getParam();
		Integer album_id = map.get("album_id");
		if(album_id==null){
			return null;
		}
		Album album = abumEntityMapper.selectNameAndImgByPrimaryKey((long)album_id);
		List<ArticleEntity> articles = articleEntityMapper.selectArticlesForPage(param);
		param.setParam(null);
		param.setData(articles);
		album.setAtricles(param);
		return album;
	}


	@Override
	public ArticleEntity queryArticleDetails(ArticleEntity param) {
      QuestsManagerBean.addQuest(new Quest() {
			
			@Override
			public boolean execute() {
				int i = articleEntityMapper.updateSeenTime(param.getId());
				return i>0;
			}
			
			@Override
			public boolean delete() {
				return true;
			}
			
			@Override
			public boolean condition() {
				return true;
			}
		});
		return articleEntityMapper.selectDetailsByPrimaryKey(param.getId());
	}


	@Override
	public String queryImage(Long id) {
		return articleEntityMapper.selectImageByPrimaryKey(id);
	}
	
	@Override
	public List<BaseMainArticleColumnEntity> selectArticleColumns() {
		 List<BaseMainArticleColumnEntity> list = mainArticleColumnMapper.selectArticleColumns();
		 if(list.size()>0){
			 for (BaseMainArticleColumnEntity baseMainArticleColumnEntity : list) {
				 if(baseMainArticleColumnEntity.getLink_type()==null || baseMainArticleColumnEntity.getData_param() ==null){
					 continue;
				 }
				 if(baseMainArticleColumnEntity.getLink_type()==7) {
					ArticleEntity articleEntity = articleEntityMapper.selectByPrimaryKey(Long.valueOf(baseMainArticleColumnEntity.getData_param()));
					baseMainArticleColumnEntity.setAlbum_id(articleEntity.getAbum_id());
				}
			}
		 }
		 return list;
	}


	@Override
	public Page<AppArticleEvaluationModel> queryAppArticleEvaluationForPage(Page<AppArticleEvaluationModel> page) {
		
		List<AppArticleEvaluationModel> list = articleEvaluationMapper.selectAppArticleEvaluationsForPage(page);
		
		for (AppArticleEvaluationModel appArticleEvaluationModel : list) {
			try {
				appArticleEvaluationModel.setContent(EmojiUtil.resolveToEmoji(appArticleEvaluationModel.getContent()));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<AppArticleEvaluationModel> replies = articleEvaluationMapper.selectAppArticleReplies(appArticleEvaluationModel.getId());
			for (AppArticleEvaluationModel appArticleEvaluationModel2 : replies) {
				try {
					appArticleEvaluationModel2.setContent(EmojiUtil.resolveToEmoji(appArticleEvaluationModel2.getContent()));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			appArticleEvaluationModel.setReplies(replies);
		}
		
		page.setData(list);
		return page;
	}


	@Override
	public ArticleDetailModel queryArticleDetailSurplus(ArticleDetailModel detailModel) {
		ArticleDetailModel articleDetailModel = new ArticleDetailModel();
		if(articleLikesMapper.selectWhetherLike(detailModel)==null){
			articleDetailModel.setLikes_state(false);
			articleDetailModel.setDislikes_state(false);
		}else if(articleLikesMapper.selectWhetherLike(detailModel)==0){
			articleDetailModel.setLikes_state(false);
			articleDetailModel.setDislikes_state(true);
		}else if(articleLikesMapper.selectWhetherLike(detailModel)==1){
			articleDetailModel.setLikes_state(true);
			articleDetailModel.setDislikes_state(false);
		}
		articleDetailModel.setLikes_num(articleLikesMapper.selectArticleLikesNum(detailModel.getArticle_id()));
		articleDetailModel.setCollect_state(articleCollectionMapper.selectWhetherCollect(detailModel));
	//	articleDetailModel.setCollect_num(articleCollectionMapper.selectArticleCollectionNum(Long.valueOf(article_id)));
		List<String> comments = articleEvaluationMapper.selectArticleScrollComments(detailModel.getArticle_id());
		for (int i = 0; i < comments.size(); i++) {
			try {
				String emoji = EmojiUtil.resolveToEmoji(comments.get(i));
				comments.set(i, emoji);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		articleDetailModel.setScrollComments(comments);
		return articleDetailModel;
	}

	public static void main(String[] args) {
		List<String> comments =new ArrayList<>();
		comments.add( "[[%F0%9F%98%82]][[%F0%9F%98%82]][[%F0%9F%98%82]]");
		comments.add( "[[%F0%9F%98%82]][[%F0%9F%98%82]][[%F0%9F%98%82]]");
		comments.add( "[[%F0%9F%98%82]][[%F0%9F%98%82]][[%F0%9F%98%82]]");
		for (String string : comments) {
			try {
				string=EmojiUtil.resolveToEmoji(string);
				System.out.println(string);
			
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(comments.toString());
	}
	
	@Override
	public Boolean removeLikesRecord(BaseArticleLikesEntity articleLikesEntity) {
		if(articleLikesEntity.getCustomer_id()==null){
			return false;
		}
		return articleLikesMapper.deleteByCustomerIdAndArticleId(articleLikesEntity)>0;
	}


	@Override
	public Boolean addLikesRecord(BaseArticleLikesEntity articleLikesEntity) {
		if(articleLikesEntity.getCustomer_id()==null){
			return false;
		}
	   BaseArticleLikesEntity entity = articleLikesMapper.selectLikeRecordByCustomerIdAndArticleId(articleLikesEntity);
		if(entity ==null){
		articleLikesEntity.setCreate_time(new Date());
		return articleLikesMapper.insertSelective(articleLikesEntity)>0;
		}else{
			if(entity.getState()==articleLikesEntity.getState()){
				return true;
			}else{
				entity.setCreate_time(new Date());
				entity.setState(articleLikesEntity.getState());
				return articleLikesMapper.updateByPrimaryKeySelective(entity)>0;
			}
		}
	}

	@Override
	public Boolean addArticleCollection(BaseArticleCollectionEntity articleCollectionEntity) {
		if(articleCollectionEntity.getCustomer_id()==null){
			return false;
		}
		BaseArticleCollectionEntity collectionEntity = articleCollectionMapper.selectCollectRecord(articleCollectionEntity);
		if(collectionEntity!=null){
			collectionEntity.setCreate_time(new Date());
			collectionEntity.setState((byte)1);
			return articleCollectionMapper.updateByPrimaryKeySelective(collectionEntity)>0;
		}else{
			articleCollectionEntity.setCreate_time(new Date());
			articleCollectionEntity.setState((byte)1);
			return articleCollectionMapper.insert(articleCollectionEntity)>0;
		}
		
	}

	@Override
	public Boolean modifyArticleCollection(ArticleDetailModel articleCollectionEntity) {
			if(articleCollectionEntity.getCustomer_id()==null){
				return false;
			}
				return articleCollectionMapper.cancleCollectState(articleCollectionEntity)>0;
	}


	@Override
	public Page<ArticleEntity> queryArticleCollectionForPage(Page<ArticleEntity> param) {
		List<ArticleEntity> list = articleCollectionMapper.selectCollectArticlesForPage(param);
		param.setData(list);
		return param;
	}


	@Override
	public synchronized Boolean modifyArticleEvaluation(AppArticleEvaluationModel appArticleEvaluationModel) {
		return articleEvaluationMapper.updateArticleEvaluationLikesNum(appArticleEvaluationModel.getId())>0;
	}


	@Override
	public Boolean addArticleEvaluation(BaseArticleEvaluationEntity articleEvaluationEntity) {
		if(articleEvaluationEntity.getCustomer_id()==null){
			return false;
		}
		articleEvaluationEntity.setCreate_time(new Date());
		String string = SensitiveUtil.replaceSensitiveWord(articleEvaluationEntity.getContent(), SensitiveUtil.maxMatchType, "*");
		try {
			articleEvaluationEntity.setContent(EmojiUtil.resolveFromEmoji(string));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return	articleEvaluationMapper.insertSelective(articleEvaluationEntity)>0;
	}


	@Override
	public Boolean updateShareNum(ArticleEntity articleEntity) {
	     QuestsManagerBean.addQuest(new Quest() {
				
				@Override
				public boolean execute() {
					int i = articleEntityMapper.updateShareNum(articleEntity.getId());
					return i>0;
				}
				
				@Override
				public boolean delete() {
					return true;
				}
				
				@Override
				public boolean condition() {
					return true;
				}
			});
		return true;
	}


}
