package com.mfangsoft.zhuangjialong.integration.column.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.app.article.model.AbumEntity;
import com.mfangsoft.zhuangjialong.app.article.model.ArticleEntity;
import com.mfangsoft.zhuangjialong.app.article.model.BaseMainArticleColumnEntity;
import com.mfangsoft.zhuangjialong.app.headline.model.HeadLineEntity;
import com.mfangsoft.zhuangjialong.app.jumptoweb.model.BasePath;
import com.mfangsoft.zhuangjialong.app.main.model.CaseEntity;
import com.mfangsoft.zhuangjialong.app.main.model.ColumnImg;
import com.mfangsoft.zhuangjialong.app.main.model.MainBannerEntity;
import com.mfangsoft.zhuangjialong.app.main.service.MainService;
import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.column.model.ArticleEvaluationModel;
import com.mfangsoft.zhuangjialong.integration.column.model.ArticleEvaluationReplyModel;
import com.mfangsoft.zhuangjialong.integration.column.model.ArticleModel;
import com.mfangsoft.zhuangjialong.integration.column.model.BaseAdvertisement;
import com.mfangsoft.zhuangjialong.integration.column.service.AdvertisementService;
import com.mfangsoft.zhuangjialong.integration.column.service.ArticleEvaluationService;
import com.mfangsoft.zhuangjialong.integration.column.service.BannerService;
import com.mfangsoft.zhuangjialong.integration.column.service.HeadLineService;
import com.mfangsoft.zhuangjialong.integration.column.service.MainArticleColumnService;
import com.mfangsoft.zhuangjialong.integration.enums.UserType;
import com.mfangsoft.zhuangjialong.integration.user.model.UserEntity;

@Controller
@RequestMapping("/server")
public class ColumnController {
	
    @Autowired
	private ArticleEvaluationService  articleEvaluationServiceImpl;
    
	@Autowired
	private MainArticleColumnService mainArticleColumnServiceImpl;
	
	@Autowired
	private HeadLineService headLineService;
	
	@Autowired
	private BannerService bannerService;
	@Autowired
	private AdvertisementService advertisementServiceImpl;
	
	@Autowired 
	
	private  MainService mainService;
	
	private final static Integer column_img=1;
	
	
	private final static Integer tag_img=2;
	
	/**
	 * 
	 * link_type 1:banner,2:栏目图标 3：标签栏图标 4：广告图设置
	 * param_type  
	 * @param map {param:"",link_type:"",param_type:"",img_url:""}
	 * @return
	 */
	@RequestMapping(value="/setbanner",method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<String> setBanner(@RequestBody MainBannerEntity mainBannerEntity){
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		try {
			mainBannerEntity.setUser_id(UserContext.getCurrentUserId());
			bannerService.addBanner(mainBannerEntity);
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} catch (Exception e) {
			
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			// TODO: handle exception
		}
		return message;
	}
	
	/**
	 * 
	 * link_type 1:banner,2:栏目图标 3：标签栏图标 4：广告图设置
	 * param_type  
	 * @param map {param:"",link_type:"",param_type:"",img_url:""}
	 * @return
	 */
	@RequestMapping(value="/modfiybanner",method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<String> modfiyBanner(@RequestBody MainBannerEntity mainBannerEntity){
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		try {
			bannerService.modfiyBanner(mainBannerEntity);
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} catch (Exception e) {
			
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			// TODO: handle exception
		}
		return message;
	}
	
	/**
	 * 
	 * link_type 1:banner,2:栏目图标 3：标签栏图标 4：广告图设置
	 * param_type  
	 * @param map {param:"",link_type:"",param_type:"",img_url:""}
	 * @return
	 */
	@RequestMapping(value="/getbannerlist",method=RequestMethod.GET)
	@ResponseBody
	public List<MainBannerEntity> getBannerList(){
		UserEntity entity =UserContext.getCurrentUser();
		Map<String, Object> map =new HashMap<>();
		if (entity.getUser_type().intValue()==UserType.PARTNER.getIndex().intValue()){
			
			
			
			
			
		    map.put("user_id", entity.getId());
		    map.put("user_type", UserContext.getCurrentUser().getUser_type());
		    return bannerService.getAdminMainBannerList(map);
		    
		}else{
			
			map.put("user_type", UserContext.getCurrentUser().getUser_type());
			return bannerService.getAdminMainBannerList(map);
		    	
		    }
			
		
		//return new ArrayList<MainBannerEntity>();
	}
	/**
	 * 
	 * link_type 1:banner,2:栏目图标 3：标签栏图标 4：广告图设置
	 * param_type  
	 * @param map {param:"",link_type:"",param_type:"",img_url:""}
	 * @return
	 */
	@RequestMapping(value="/getbanneradlist",method=RequestMethod.GET)
	@ResponseBody
	public List<MainBannerEntity> getBanneradList(){
		
		return bannerService.selectMainBanneradList();
	}
	
	/**
	 * 
	 * 删除banner
	 * param_type  
	 * @param map {param:"",link_type:"",param_type:"",img_url:""}
	 * @return
	 */
	@RequestMapping(value="/removemainbanner/{id}",method=RequestMethod.GET)
	@ResponseBody
	public ResponseMessage<String> removeMainBanner(@PathVariable Long id){
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		try {
			bannerService.removeBanner(id);
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} catch (Exception e) {
			
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			// TODO: handle exception
		}
		return message;
	}
	
	/**
	 * 新增头条
	 * @param headLineEntity
	 * @return
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/createheadline",method=RequestMethod.POST)
	public ResponseMessage<String> createHeadLine(@RequestBody HeadLineEntity headLineEntity){
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		if(headLineService.addHeadLine(headLineEntity)){
			
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		
		return message;
		
	}
	
	/**
	 * 修改头条
	 * @param headLineEntity
	 * @return
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/modfiyheadline",method=RequestMethod.POST)
	public ResponseMessage<String> modfiyHeadLine(@RequestBody HeadLineEntity headLineEntity){
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		if(headLineService.modfiyHeadLine(headLineEntity)){
			
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		
		return message;
		
	}
	
	/**
	 * 获取头条
	 * @param headLineEntity
	 * @return
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/getheadline/{id}",method=RequestMethod.GET)
	public ResponseMessage<HeadLineEntity> getHeadLineByid(@PathVariable Long id){
		
		ResponseMessage<HeadLineEntity> message = new ResponseMessage<HeadLineEntity>();
		message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			message.setResult(headLineService.getHeadLineByid(id));
		
		return message;
		
	}
	
	/**
	 * 删除头条
	 * @param id
	 * @return
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/removeheadline/{id}",method=RequestMethod.GET)
	public ResponseMessage<String> removeHeadLine(@PathVariable Long id){
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		if(headLineService.removeHeadLine(id)){
			
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		
		return message;
		
	}
	/**
	 * 头条列表
	 * @param page
	 * @return
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/queryheadline",method=RequestMethod.POST)
	public Page<HeadLineEntity> queryHeadLine(@RequestBody Page<HeadLineEntity> page){
		
		return headLineService.selectHeadLineListForPage(page);
		
	}
	
	/**
	 * 添加专辑
	 * @param abumEntity
	 * @return
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/addabum",method=RequestMethod.POST)
    public ResponseMessage<String> addAbum(@RequestBody AbumEntity abumEntity){
    	ResponseMessage<String> message = new ResponseMessage<String>();
    	abumEntity.setUser_id(UserContext.getCurrentUserId());
		if(headLineService.addAbum(abumEntity)){
			
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		
		return message;
    }
	/**
	 * 修改专辑
	 * @param abumEntity
	 * @return
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/modfiyabum",method=RequestMethod.POST)
    public ResponseMessage<String> modfiyAbum(@RequestBody AbumEntity abumEntity){
    	ResponseMessage<String> message = new ResponseMessage<String>();
    	abumEntity.setUser_id(UserContext.getCurrentUserId());
		if(headLineService.modfiyAbum(abumEntity)){
			
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		
		return message;
    }
	
	/**
	 * 查询单个专辑
	 * @param abumEntity
	 * @return
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/getabumbyid/{id}",method=RequestMethod.GET)
    public ResponseMessage<AbumEntity> getAbumByid(@PathVariable Long id){
    	ResponseMessage<AbumEntity> message = new ResponseMessage<AbumEntity>();
    	
    	message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(headLineService.getAbumByid(id));
		return message;
    }
	
	/**
	 * 删除专辑
	 * @param abumEntity
	 * @return
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/removeabum/{id}",method=RequestMethod.GET)
    public ResponseMessage<String> removeAbum(@PathVariable Long id){
    	ResponseMessage<String> message = new ResponseMessage<String>();
    	
    	try {
    		headLineService.removeAbum(id);
    		message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} catch (Exception e) {
			// TODO: handle exception
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
    }
	
	/**
	 * 查询专辑列表
	 * @param page
	 * @return
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/queryabum",method=RequestMethod.POST)
	public Page<Map<String, Object>> queryAbumForPage(@RequestBody Page<Map<String, Object>> page){
		
		return headLineService.selectAbumForPage(page);
	}
	
	
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/queryabumlist",method=RequestMethod.POST)
	public List<AbumEntity> queryAbumList(){
		
		return headLineService.queryAdminAbumList();
	}
	
	
	
	
	/**
	 * 添加文章内容
	 * @param articleEntity
	 * @return
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/createheadcontent",method=RequestMethod.POST)
	public ResponseMessage<String> createHeadContent(@RequestBody ArticleEntity articleEntity){
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		if(headLineService.addHeadContent(articleEntity)){
			
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		
		return message;
		
	}
	
	/**
	 * 修改图文内容
	 * @param articleEntity
	 * @return
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/modfiyheadcontent",method=RequestMethod.POST)
	public ResponseMessage<String> modfiyHeadContent(@RequestBody ArticleEntity articleEntity){
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		if(headLineService.modfiyHeadContent(articleEntity)){
			
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		
		return message;
		
	}
	
	/**
	 * 删除图文
	 * @param id
	 * @return
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/removeheadcontent/{id}",method=RequestMethod.GET)
	public ResponseMessage<String> removeHeadContent(@PathVariable Long id){
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		if(headLineService.removeHeadContent(id)){
			
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		
		return message;
		
	}
	/**
	 * 查询单个图文
	 * @param id
	 * @return
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/getheadcontentbyid/{id}",method=RequestMethod.GET)
	public ResponseMessage<ArticleEntity> getHeadcontentById(@PathVariable Long id){
		
		ResponseMessage<ArticleEntity> message = new ResponseMessage<ArticleEntity>();
		
			
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			message.setResult(headLineService.getHeadcontentById(id));
	
		return message;
		
	}
	/**
	 * 查询图文分页
	 * @param page
	 * @return
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/queryheadcontent",method=RequestMethod.POST)
	public Page<ArticleModel> queryHeadContent(@RequestBody Page<ArticleModel> page){
		
		return headLineService.selectHeadContentListForPage(page);
		
	}
	/**
	 * 修改栏目图标
	 * @param columnImg
	 * @return
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/modfiycolumn",method=RequestMethod.POST)
	public ResponseMessage<String> createColumnImage(@RequestBody ColumnImg columnImg){
		
		columnImg.setImg_type(columnImg.COLUMN_IMG_TYPE);
		ResponseMessage<String> message = new ResponseMessage<>();
		if(headLineService.modfiyColumnImage(columnImg)){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
	}
	
	
	
	/**
	 * 查询栏目图标
	 * @param columnImg
	 * @return
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/querycolumn",method=RequestMethod.GET)
	public ResponseMessage<List<ColumnImg>> queryColumnImage(){
		
		ResponseMessage<List<ColumnImg>> message = new ResponseMessage<>();
		
		message.setResult(mainService.selectAllByImgType(ColumnImg.COLUMN_IMG_TYPE));
		
		return message;
	}
	
	/**
	 * 查询栏目图标
	 * @param columnImg
	 * @return
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/querycolumnselection",method=RequestMethod.GET)
	public ResponseMessage<List<BasePath>> queryColumnSelection(){
		
		ResponseMessage<List<BasePath>> message = new ResponseMessage<>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(mainService.selectMainCoulmn());
		return message;
	}
	
	/**
	 * 修改标签图标
	 * @param columnImg
	 * @return
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/modfiytagimg",method=RequestMethod.POST)
	public ResponseMessage<String> createTagImage(@RequestBody ColumnImg columnImg){
		
		columnImg.setImg_type(columnImg.TAG_IMG_TYPE);
		ResponseMessage<String> message = new ResponseMessage<>();
		if(headLineService.modfiyColumnImage(columnImg)){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
	}
	
	/**
	 * 查询标签图标
	 * @param columnImg
	 * @return
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/querytagimg",method=RequestMethod.GET)
	public ResponseMessage<List<ColumnImg>> queryTagImage(){
		
		ResponseMessage<List<ColumnImg>> message = new ResponseMessage<>();
		
		message.setResult(mainService.selectAllByImgType(ColumnImg.TAG_IMG_TYPE));
		
		return message;
	}
	/**
	 * 修改龙头条
	 * @param columnImg
	 * @return
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/modfiytopimg",method=RequestMethod.POST)
	public ResponseMessage<String> modfiyTopImage(@RequestBody ColumnImg columnImg){
		
		columnImg.setImg_type(columnImg.TOP_IMG_TYPE);
		ResponseMessage<String> message = new ResponseMessage<>();
		if(headLineService.modfiyColumnImage(columnImg)){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
	}
	/**
	 * 修改龙头条
	 * @param columnImg
	 * @return
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/modfiycasetopimg",method=RequestMethod.POST)
	public ResponseMessage<String> modfiyCaseImage(@RequestBody ColumnImg columnImg){
		
		columnImg.setImg_type(columnImg.CASE_IMG_TYPE);
		ResponseMessage<String> message = new ResponseMessage<>();
		if(headLineService.modfiyColumnImage(columnImg)){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
	}
	/**
	 * 查询龙头条
	 * @return
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/querytopimg",method=RequestMethod.GET)
	public ResponseMessage<List<ColumnImg>> queryTopImage(){
		
		ResponseMessage<List<ColumnImg>> message = new ResponseMessage<>();
		
		message.setResult(mainService.selectAllByImgType(ColumnImg.TOP_IMG_TYPE));
		
		return message;
	}
	
	/**
	 * 查询案例头图
	 * @return
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/querycasetopimg",method=RequestMethod.GET)
	public ResponseMessage<List<ColumnImg>> queryCaseTopImage(){
		
		ResponseMessage<List<ColumnImg>> message = new ResponseMessage<>();
		
		message.setResult(mainService.selectAllByImgType(ColumnImg.CASE_IMG_TYPE));
		
		return message;
	}
	/**
	 * 创建案例
	 * @param caseEntity
	 * @return
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/createcase",method=RequestMethod.POST)
	public ResponseMessage<String> createCase(@RequestBody CaseEntity caseEntity){
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		if(headLineService.addCase(caseEntity)){
			
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		
		return message;
		
	}
	
	/**
	 * 修改案例
	 * @param caseEntity
	 * @return
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/modfiycase",method=RequestMethod.POST)
	public ResponseMessage<String> modfiycase(@RequestBody CaseEntity caseEntity){
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		if(headLineService.modfiyCase(caseEntity)){
			
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		
		return message;
		
	}
	
	/**
	 * 获取单个案例
	 * @param caseEntity
	 * @return
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/getcaseid/{id}",method=RequestMethod.GET)
	public ResponseMessage<CaseEntity> getCaseById(@PathVariable Long id){
		
		ResponseMessage<CaseEntity> message = new ResponseMessage<CaseEntity>();
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			message.setResult(headLineService.getCaseById(id));
	
		return message;
		
	}
	
	/**
	 * 删除案例
	 * @param id
	 * @return
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/removecase/{id}",method=RequestMethod.GET)
	public ResponseMessage<String> removeCase(@PathVariable Long id){
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		if(headLineService.removeCase(id)){
			
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}else{
			
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		
		return message;
		
	}
	/**
	 * 查询案例
	 * @param page
	 * @return
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(value="/querycase",method=RequestMethod.POST)
	public Page<CaseEntity> createCase(@RequestBody Page<CaseEntity> page){
		
		return headLineService.selectCaseListForPage(page);
		
	}
	
	/*
	 * 设置首页文章栏目
	 */
	@RequestMapping(value="/setarticlecolumn",method=RequestMethod.POST)
	@CrossOrigin
	@ResponseBody
	public ResponseMessage<String> setArticleColumn(@RequestBody BaseMainArticleColumnEntity articleColumnEntity){
		
		ResponseMessage<String> message = new ResponseMessage<>();
		
			if(mainArticleColumnServiceImpl.addArticleColumn(articleColumnEntity)){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} else {
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
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
			message.setResult(mainArticleColumnServiceImpl.selectArticleColumns());
		return message;
	}

	/*
	 * 修改首页文章栏目
	 */
	@RequestMapping(value="/modifyarticlecolumns",method=RequestMethod.POST)
	@CrossOrigin
	@ResponseBody
	public ResponseMessage<String> modifyArticleColumns(@RequestBody BaseMainArticleColumnEntity articleColumnEntity){
		
		ResponseMessage<String> message = new ResponseMessage<>();
		if(mainArticleColumnServiceImpl.modifyArticleColumn(articleColumnEntity)){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} else {
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
	}
	
	/*
	 * 删除首页文章栏目
	 */
	@RequestMapping(value="/deletearticlecolumns/{id}",method=RequestMethod.POST)
	@CrossOrigin
	@ResponseBody
	public ResponseMessage<String> deleteArticleColumns(@PathVariable int id){
		
		ResponseMessage<String> message = new ResponseMessage<>();
		if(mainArticleColumnServiceImpl.removeArticleColumn(id)){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} else {
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
	}
	
	
	/*
	 * 查询文章一级评论
	 */
	@RequestMapping(value="/queryarticleevaluations",method=RequestMethod.POST)
	@CrossOrigin
	@ResponseBody
	public Page<ArticleEvaluationModel> queryArticleEvaluations(@RequestBody Page<ArticleEvaluationModel> param){
		
		return	articleEvaluationServiceImpl.queryArticleEvaluations(param);
	}
	
	/*
	 * 查询文章二级评论
	 */
	@RequestMapping(value="/queryarticleevaluation/{id}",method=RequestMethod.POST)
	@CrossOrigin
	@ResponseBody
	public ResponseMessage<ArticleEvaluationReplyModel> queryArticleEvaluation(@PathVariable int id){
		
		ResponseMessage<ArticleEvaluationReplyModel> message = new ResponseMessage<>();
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			message.setResult(articleEvaluationServiceImpl.queryArticleEvaluation(id));
		return message;
	}
	
	/*
	 * 查询文章评论
	 */
	@RequestMapping(value="/modifyarticleevaluation",method=RequestMethod.POST)
	@CrossOrigin
	@ResponseBody
	public ResponseMessage<String> modifyArticleEvaluation(@RequestBody ArticleEvaluationModel param){
		
		ResponseMessage<String> message = new ResponseMessage<>();
		if(articleEvaluationServiceImpl.modifyArticleEvaluatoin(param)){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} else {
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
	}
	
	/*
	 * 查询广告图
	 */
	@RequestMapping(value="/getAdvertisements",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<BaseAdvertisement>> getAdvertisements(){
		
		ResponseMessage<List<BaseAdvertisement>> message = new ResponseMessage<>();
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			message.setResult(advertisementServiceImpl.getAds());
		return message;
	}
	
	/*
	 * 查询广告图
	 */
	@RequestMapping(value="/modifyAdvertisement",method=RequestMethod.POST)
	@CrossOrigin
	@ResponseBody
	public ResponseMessage<String> modifyAdvertisement(@RequestBody BaseAdvertisement baseAdvertisement){
		
		ResponseMessage<String> message = new ResponseMessage<>();
		if(advertisementServiceImpl.modifyAd(baseAdvertisement)){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} else {
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
	}
	
	/*
	 * 添加广告图
	 */
	@RequestMapping(value="/addAdvertisement",method=RequestMethod.POST)
	@CrossOrigin
	@ResponseBody
	public ResponseMessage<String> addAdvertisement(@RequestBody BaseAdvertisement baseAdvertisement){
		
		ResponseMessage<String> message = new ResponseMessage<>();
		if(advertisementServiceImpl.addAd(baseAdvertisement)){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} else {
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
	}
	
	/*
	 * 删除广告图
	 */
	@RequestMapping(value="/deleteAdvertisement",method=RequestMethod.POST)
	@CrossOrigin
	@ResponseBody
	public ResponseMessage<String> deleteAdvertisement(@RequestBody BaseAdvertisement baseAdvertisement){
		
		ResponseMessage<String> message = new ResponseMessage<>();
		if(advertisementServiceImpl.deleteAd(baseAdvertisement)){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} else {
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
	}
}
