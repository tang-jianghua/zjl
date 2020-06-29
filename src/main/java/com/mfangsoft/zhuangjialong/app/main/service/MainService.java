package com.mfangsoft.zhuangjialong.app.main.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.article.model.ArticleEntity;
import com.mfangsoft.zhuangjialong.app.jumptoweb.model.BasePath;
import com.mfangsoft.zhuangjialong.app.main.model.CaseEntity;
import com.mfangsoft.zhuangjialong.app.main.model.ColumnImg;
import com.mfangsoft.zhuangjialong.app.main.model.IntroduceProductModel;
import com.mfangsoft.zhuangjialong.app.main.model.MainBannerEntity;
import com.mfangsoft.zhuangjialong.app.product.model.ProductListModel;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.core.exception.ServiceException;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;

/**
* @description：首页接口
* @author：Jianghua.Tang 
* @date：2016年6月3日
* 
*/
@Service
public interface MainService {
	
	
	/**
	    * @description：获取首页banner
	    * @param：无
	    */
	public  List<MainBannerEntity> selectMainBanners(String region_code) throws ServiceException;
	
	
	/**
	    * @description：获取首页图标
	    * @param：Integer img_type
	    */
	public List<ColumnImg> selectByImgType(Integer img_type) throws ServiceException;
	
	/**
	    * @description：获取首页栏目图标
	    * @param：Integer img_type
	    */
	public List<ColumnImg> selectMainColumn();
	
	/**
	    * @description：获取首页广告
	    * @param：Integer img_type
	    */
	public MainBannerEntity getAd();
	
	
	 /**
     * 获取头条  
     * @param：Integer state
     */
    public Map<String, Object> selectMainHeadLine() throws ServiceException;
    
    
    /**
     *   通过状态获取案例列表
     *
     * @MLTH_generated
     */
    public List<CaseEntity> selectCaseList(Map<String, String> map) throws ServiceException;
    
    
    /**
     *   案例详情
     *
     * @MLTH_generated
     */
    public ArticleEntity selectForCaseDetails(Long id) throws ServiceException;
	
    /**
     * 获取首页区域品牌列表
     *
     * @MLTH_generated
     */
    public List<BrandEntity> selectMainRegionBrand(BrandEntity param) ;
    
    
    /**
     * 获取首页热门产品列表
     *
     * @MLTH_generated
     */
    public Page<ProductListModel> getSqlMainHotProductForPage(Page<ProductListModel> page) ;
    
    
    /**
     * 获取首页热门产品列表
     *
     * @MLTH_generated
     */
    public Page<IntroduceProductModel> getMainHotProductForPage(Page<IntroduceProductModel> page) ;
    
    
    List<ColumnImg> selectAllByImgType(Integer img_type);
    
    /*
     * 查询首页按钮跳转类型
     */
    List<BasePath> selectMainCoulmn();
}
