package com.mfangsoft.zhuangjialong.integration.brand.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.junit.runners.Parameterized.Parameters;
import org.w3c.dom.ls.LSInput;

import com.mfangsoft.zhuangjialong.app.brand.model.Brand;
import com.mfangsoft.zhuangjialong.app.brand.model.BrandEnModel;
import com.mfangsoft.zhuangjialong.app.brand.model.BrandModel;
import com.mfangsoft.zhuangjialong.app.brand.model.BrandScore;
import com.mfangsoft.zhuangjialong.app.product.model.ListProductModel;
import com.mfangsoft.zhuangjialong.app.product.model.ProductListModel;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseEntity;
@WriterRepository
public interface BrandEntityMapper {
	
    /**
     * 查询品牌首页产品
     *
     * @MLTH_generated
     */
	List<ProductListModel> selectBrandMainProducts(Long brand_id);
	
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
    int insert(BrandEntity record);

    int insertSelective(BrandEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BrandEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BrandEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BrandEntity record);
    
    /**
     * 获取首页热门品牌列表
     *
     * @MLTH_generated
     */
    List<BrandEntity> selectMainRegionBrand(BrandEntity param);
    
    
    /**
     * 根据品牌id获取品牌id+图片
     *
     * @MLTH_generated
     */
    Brand selectIdAndImage(Long id);
    
    /**
     * 获取热门品牌列表
     *
     * @MLTH_generated
     */
    List<BrandEntity> selectRegionBrand(BrandEntity param);
    /**
     * 通过用户查询品牌信息
     * @param userId
     * @return
     */
    BrandEntity getBrandEntityByUserId(Long userId);
    
    /**
     * 根据品类获取当地品牌列表
     *
     * @MLTH_generated
     */
    List<BrandModel> selectRegionBrandByClassId(Map<String, Object> map);
    
    /**
     * 根据品类获取当地品牌列表
     *
     * @MLTH_generated
     */
    List<BrandEnModel> selectBrandEnModelsByClassId(Map<String, Object> map);
    
    List<Map<String,Object>> queryBrandForPage(Page<Map<String,Object>> page);
    
    /**
     * 通过品牌id获取质量总分、服务总分、评论总条数
     *
     * @MLTH_generated
     */
    BrandScore selectTotalScoreByBrandId(Map<String , Long> map);
    
    /**
     * 通过品牌id和消费者id判断是否关注 （大于0 关注）
     *
     * @MLTH_generated
     */
    Integer selectBrandWhetherCollected(Map<String , Long> map);
    
    /**
     * 通过品牌id获取品牌名称+logo+粉丝数
     *
     * @MLTH_generated
     */
     BrandModel  selectBrandHead(Map<String , Long> map);
     
     /**
      * 通过品牌id获取品类id
      *
      * @MLTH_generated
      */
     Long selectClassIdByBrandId(Long id);
     
     /**
      * 查询品牌全部产品
      *
      * @MLTH_generated
      */
     List<ProductListModel> selectBrandAllProductsForPage(Page<ProductListModel> page);
     
     /**
      * 通过品牌id获取热线电话
      *
      * @MLTH_generated
      */
     String selectHotLineByBrandId(Map<String, Object> param);
     
     
     /**
      * 获取所有品牌服务的所有区域
      *
      * @MLTH_generated
      */
     List<String>  selectAllRegionCode();
     
     
     
     BrandEntity selectBrandNameByUserId(Long userId);
     
     
     List<BrandEntity> selectBrandName();

     
     List<Map<String,Object>> selectBrandNameByPartner(@Param("partner_id") Long partner_id);
     

     List<Map<String, Object>> selectBrandByIDs(@Param("idList") List<String> idList);
     
     /**
      * 通过品牌id获取名称
      *
      * @MLTH_generated
      */
     String selectBrandNameById(Long brand_id);
      /*
       * 通过品牌id查找品牌的关注数量
       */
     Integer selectBrandCollectNum(Long brang_id);
     
     
     List<BrandEntity> getEnterpriseByBuildId(Long id);
     
     /*
      * 根据城市合伙人和品类确定合伙人下的该品类的所有品牌
      */
     List<Map<String, Object>> selectBrandByPartnerIdAndClassId(Map<String, Long> param);
     
     /*
      * 根据区域编码和品类获取所有品牌
      */
     List<Brand> selectBrandByRegionCodeAndClassIds(Map<String, Object> map);
     
     /*
      * 查询品牌上架条件
      */
     com.mfangsoft.zhuangjialong.integration.brand.model.BrandModel selectBrandStateConditions(Long brand_id);
     
     /*
      * 查询申请下家的品牌
      */
     List<Map<String, Object>> getApplyOffStateBrandsForPage(Page<Map<String, Object>> page);
}