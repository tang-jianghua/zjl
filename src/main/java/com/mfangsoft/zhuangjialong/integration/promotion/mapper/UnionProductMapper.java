package com.mfangsoft.zhuangjialong.integration.promotion.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.app.product.model.ListProductModel;
import com.mfangsoft.zhuangjialong.app.product.model.ProductListModel;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.promotion.model.UnionProduct;
import com.mfangsoft.zhuangjialong.integration.promotion.model.UnionPromotion;
@WriterRepository
public interface UnionProductMapper {
    /**
     * 通过主键删除  
     *
     * @MLTH_generated
     */
    int deleteByPrimaryKey(@Param("idList")List<Integer> idList);
    /**
     * 通过活动id删除活动产品
     * @param promotionID
     * @return
     */
    int deleteByUnionPromotionID(@Param("promotionID") Integer promotionID);
    /**
     * 插入数据  
     *
     * @MLTH_generated
     */
    int insert(UnionProduct record);

    int insertSelective(UnionProduct record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    UnionProduct selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UnionProduct record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(UnionProduct record);
    
    
    /**
     * 获取壁纸品牌的联盟活动产品
     *
     * @MLTH_generated
     */
    List<ListProductModel> selectUnionWallpaperForPage(Page<ListProductModel> page);
    
    /**
     * 获取地板品牌的联盟活动产品
     *
     * @MLTH_generated
     */
    List<ListProductModel> selectUnionFloorForPage(Page<ListProductModel> page);
    
    /**
     * 获取瓷砖品牌的联盟活动产品
     *
     * @MLTH_generated
     */
    List<ListProductModel> selectUnionTileForPage(Page<ListProductModel> page);
    
    /**
     * 获取门品牌的联盟活动产品
     *
     * @MLTH_generated
     */
    List<ListProductModel> selectUnionDoorForPage(Page<ListProductModel> page);
    
    /**
     * 获取涂料品牌的联盟活动产品
     *
     * @MLTH_generated
     */
    List<ListProductModel> selectUnionPaintForPage(Page<ListProductModel> page);
    
    /**
     * 获取某联盟活动的某品牌的所有产品id
     *
     * @MLTH_generated
     */
    List<Long> selectUnionProductIds(Page<Map<String, Object>> page);
    
    /**
     * 获取某联盟活动的某品牌的所有产品
     *
     * @MLTH_generated
     */
    List<ProductListModel> selectUnionProductsForPage(Page<ProductListModel> page);
    
    /**
     * 品牌管理员后台-活动管理-品牌活动-联盟活动-参加活动产品列表分页显示
     */
    List<Map<String, Object>> findUnionProductListPage(Page<Map<String, Object>> page);
    /**
     * 品牌管理员后台-活动管理-品牌活动-联盟活动-参加活动产品列表,不分页
     */
    List<Map<String, Object>> findUnionProductList(Page<Map<String, Object>> page);
    /**
     * 品牌管理员后台-活动管理-品牌活动-联盟活动-设置活动产品列表分页显示未被选择的品牌产品
     */
    List<Map<String, Object>> findNotSelectedBrandProductPage(Page<Map<String, Object>> page);
    /**
     * 品牌管理员后台-活动管理-品牌活动-联盟活动-发布促销活动-选择全部产品时导入品牌产品时过滤
     */
    List<Long> findNotSelectedBrandProductIDList(Map<String, Object> param);
    /**
     * 根据联盟活动id和品牌id删除联盟活动产品表中对应关系记录
     * @param unionPromotionID
     * @param idArray
     * @return
     */
    int deleteByUnionPromotionIDANDBrandIDs(@Param("unionPromotionID")Integer unionPromotionID,@Param("idArray")String[] idArray);
    /**
     * 批量添加
     * @return
     */
    int batchInsert(@Param("upList")List<UnionProduct> upList);
    
    //brand_id start_time end_time
    List<String> selectedExistUnionPromotionBrandProduct(Map<String, Object> map);
}