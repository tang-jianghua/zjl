package com.mfangsoft.zhuangjialong.integration.series.service;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.Tree;
import com.mfangsoft.zhuangjialong.integration.series.model.BrandSeriesEntity;
import com.mfangsoft.zhuangjialong.integration.series.model.EntSeriesEntity;

public interface BrandSeriesService {
	
	
	/**
	 * 通过品牌ID查询品牌系列
	 * @param brand_id
	 * @return
	 */
	List<BrandSeriesEntity> getBrandSeriesEntityList(Long brand_id);
	
	
	/**
	 * 通过 分类ID查询品牌系列
	 * @param brand_id
	 * @return
	 */
	List<BrandSeriesEntity> getBrandSeriesListByClassify(Map<String,Object> map);
	
	/**
	 * 通过 分类ID查询企业系列
	 * @param brand_id
	 * @return
	 */
	List<EntSeriesEntity> getEntSeriesListByClassify(Map<String,Object> map);
	
	
	/**
	 *  添加品牌系列
	 * @param brandSeriesEntity
	 * @return
	 */
    Boolean  addBrandSeries(BrandSeriesEntity brandSeriesEntity);
    
    /**
     * id获取品牌系列
     * @param id
     * @return
     */
    BrandSeriesEntity getBrandSeriesById(Long id);
    
    
    /**
     * 修改品牌系列
     * @param brandSeriesEntity
     * @return
     */
    Boolean  modifyBrandSeries(BrandSeriesEntity brandSeriesEntity);
    
    
    
    /**
     * 删除品牌系列
     * @param brandSeriesEntity
     * @return
     */
    Boolean  removeBrandSeries(Long  id);
    
    
    /**
     * 删除企业系列
     * @param brandSeriesEntity
     * @return
     */
    Boolean  removeEntSeries(Long  id);
    
    
    
    Page<BrandSeriesEntity>  queryBrandSeriesForPage(Page<BrandSeriesEntity> page);
    
    /**
     * 查询品牌分类系列
     * @return
     */
    List<Map<String,Object>> getClassifySeries();
    
    
    /**
     * 查询品牌分类系列
     * @return
     */
    List<Tree> getClassifySeriesForTree();
    
    
    /**
     * 查询企业分类系列tree
     * @return
     */
    List<Tree> getEntClassifySeriesForTree();
    

    /**
     * 查询品牌分类系列
     * @return
     */
    List<Map<String,Object>> getEntClassifySeries();
    
    /**
     * 添加企业系列
     * @param entSeriesEntity
     * @return
     */
    Boolean  addEntSeries(EntSeriesEntity entSeriesEntity);
    
    
    /**
     * 修改企业系列
     * @param entSeriesEntity
     * @return
     */
    Boolean  modifyEntSeries(EntSeriesEntity entSeriesEntity);
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}
