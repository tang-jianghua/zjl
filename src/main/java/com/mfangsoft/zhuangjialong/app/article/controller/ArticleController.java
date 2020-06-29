package com.mfangsoft.zhuangjialong.app.article.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.app.article.mapper.ArticleEvaluationMapper;
import com.mfangsoft.zhuangjialong.app.article.mapper.ArticleLikesMapper;
import com.mfangsoft.zhuangjialong.app.article.model.Album;
import com.mfangsoft.zhuangjialong.app.article.model.AppArticleEvaluationModel;
import com.mfangsoft.zhuangjialong.app.article.model.ArticleDetailModel;
import com.mfangsoft.zhuangjialong.app.article.model.ArticleEntity;
import com.mfangsoft.zhuangjialong.app.article.model.BaseArticleCollectionEntity;
import com.mfangsoft.zhuangjialong.app.article.model.BaseArticleLikesEntity;
import com.mfangsoft.zhuangjialong.app.article.model.BaseMainArticleColumnEntity;
import com.mfangsoft.zhuangjialong.app.article.service.ArticleService;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.JPushUtil;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.core.quartz.Quest;
import com.mfangsoft.zhuangjialong.core.quartz.QuestsManagerBean;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年7月5日
* 
*/
@Controller(value="appArticle")
@RequestMapping("/app")
public class ArticleController {
	
	@Autowired
	private ArticleService articleServiceImpl;
	@Autowired
	private ArticleLikesMapper articleLikesMapper;
	@Autowired
	private ArticleEvaluationMapper articleEvaluationMapper;

	@RequestMapping(value = "/queryarticles", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Album> queryArticles(@RequestBody Page<ArticleEntity> param){
		ResponseMessage<Album> responseMessage=new ResponseMessage<>();
		responseMessage.setCode("0");
		responseMessage.setMessage("获取成功");
		responseMessage.setResult(articleServiceImpl.queryAlbum(param));
		return responseMessage;
	}

	@RequestMapping(value = "/getarticleimage", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> queryArticleImage(@RequestBody ArticleEntity param){
		ResponseMessage<String> responseMessage=new ResponseMessage<>();
		responseMessage.setCode("0");
		responseMessage.setMessage("获取成功");
		responseMessage.setResult(articleServiceImpl.queryImage(param.getId()));
		return responseMessage;
	}
	
	@RequestMapping(value = "/queryarticledetails", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<ArticleEntity> queryArticleDetails(@RequestBody ArticleEntity param){
		ResponseMessage<ArticleEntity> responseMessage=new ResponseMessage<>();
		responseMessage.setCode("0");
		responseMessage.setMessage("获取成功");
		responseMessage.setResult(articleServiceImpl.queryArticleDetails(param));
		return responseMessage;
	}
	
	/*
	 * 查询首页文章栏目
	 */
	@RequestMapping(value="/queryarticlecolumns",method=RequestMethod.POST)
	@CrossOrigin
	@ResponseBody
	public ResponseMessage<List<BaseMainArticleColumnEntity>> queryArticleColumns(){
		
		ResponseMessage<List<BaseMainArticleColumnEntity>> message = new ResponseMessage<>();
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			message.setResult(articleServiceImpl.selectArticleColumns());
		return message;
	}
	
	/*
	 * 查询文章评论
	 */
	@RequestMapping(value="/queryapparticleevaluation",method=RequestMethod.POST)
	@CrossOrigin
	@ResponseBody
	public ResponseMessage<Page<AppArticleEvaluationModel>> queryAppArticleEvaluation(@RequestBody Page<AppArticleEvaluationModel> param){
		
		ResponseMessage<Page<AppArticleEvaluationModel>> message = new ResponseMessage<>();
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			message.setResult(articleServiceImpl.queryAppArticleEvaluationForPage(param));
		return message;
	}
	
	/*
	 * 查询文章底部详情
	 */
	@RequestMapping(value="/queryarticledetailsurplus",method=RequestMethod.POST)
	@CrossOrigin
	@ResponseBody
	public ResponseMessage<ArticleDetailModel> queryArticleColumnsSurplus(@RequestBody ArticleDetailModel detailModel){
		
		ResponseMessage<ArticleDetailModel> message = new ResponseMessage<>();
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			message.setResult(articleServiceImpl.queryArticleDetailSurplus(detailModel));
		return message;
	}
	
	/*
	 * 添加点赞或踩状态
	 */
	@RequestMapping(value="/addlikesrecord",method=RequestMethod.POST)
	@CrossOrigin
	@ResponseBody
	public ResponseMessage<Integer> addLikesRecord(@RequestBody BaseArticleLikesEntity param){
		 
		ResponseMessage<Integer> message = new ResponseMessage<>();
		if(articleServiceImpl.addLikesRecord(param)){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		message.setResult(articleLikesMapper.selectArticleLikesNum(param.getArticle_id()));
		return message;
	}
	
	/*
	 * 取消点赞或踩状态
	 */
	@RequestMapping(value="/removelikesrecord",method=RequestMethod.POST)
	@CrossOrigin
	@ResponseBody
	public ResponseMessage<Integer> removeLikesRecord(@RequestBody BaseArticleLikesEntity param){
		 
		ResponseMessage<Integer> message = new ResponseMessage<>();
		if(articleServiceImpl.removeLikesRecord(param)){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		message.setResult(articleLikesMapper.selectArticleLikesNum(param.getArticle_id()));
		return message;
	}
	
	
	/*
	 * 添加文章收藏
	 */
	@RequestMapping(value="/addarticlecollection",method=RequestMethod.POST)
	@CrossOrigin
	@ResponseBody
	public ResponseMessage<String> addArticleCollection(@RequestBody BaseArticleCollectionEntity param){
		 
		ResponseMessage<String> message = new ResponseMessage<>();
		if(articleServiceImpl.addArticleCollection(param)){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
	}
	
	/*
	 * 取消收藏
	 */
	@RequestMapping(value="/canclearticlecollection",method=RequestMethod.POST)
	@CrossOrigin
	@ResponseBody
	public ResponseMessage<String> cancleArticleCollection(@RequestBody ArticleDetailModel param){
		 
		ResponseMessage<String> message = new ResponseMessage<>();
		if(articleServiceImpl.modifyArticleCollection(param)){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
	}
	
	/*
	 * 修改评论点赞
	 */
	@RequestMapping(value="/modifyarticleevaluation",method=RequestMethod.POST)
	@CrossOrigin
	@ResponseBody
	public ResponseMessage<Integer> modifyArticleEvaluation(@RequestBody AppArticleEvaluationModel param){
		 
		ResponseMessage<Integer> message = new ResponseMessage<>();
		if(articleServiceImpl.modifyArticleEvaluation(param)){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		message.setResult(articleEvaluationMapper.selectLikeNum(param.getId()));
		return message;
	}
	
	/*
	 * 查询文章收藏
	 */
	@RequestMapping(value="/queryarticlecollection",method=RequestMethod.POST)
	@CrossOrigin
	@ResponseBody
	public ResponseMessage< Page<ArticleEntity>> queryArticleCollection(@RequestBody  Page<ArticleEntity> param){
		 
		ResponseMessage< Page<ArticleEntity>> message = new ResponseMessage<>();
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			message.setResult(articleServiceImpl.queryArticleCollectionForPage(param));
		return message;
	}
	
	/*
	 * 添加评论
	 */
	@RequestMapping(value="/updateShareNum",method=RequestMethod.POST)
	@CrossOrigin
	@ResponseBody
	public ResponseMessage<String> updateShareNum(@RequestBody ArticleEntity param){
		 
		ResponseMessage<String> message = new ResponseMessage<>();
		if(articleServiceImpl.updateShareNum(param)){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
	}
	
	/*
	 * 更新文章分享量
	 */
	@RequestMapping(value="/addarticleevaluation",method=RequestMethod.POST)
	@CrossOrigin
	@ResponseBody
	public ResponseMessage<String> addArticleEvaluation(@RequestBody AppArticleEvaluationModel param){
		 
		ResponseMessage<String> message = new ResponseMessage<>();
		if(articleServiceImpl.addArticleEvaluation(param)){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
	}
	
}
