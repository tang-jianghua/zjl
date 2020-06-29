package com.mfangsoft.zhuangjialong.integration.promotion.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.promotion.model.UnionProduct;
import com.mfangsoft.zhuangjialong.integration.promotion.model.UnionPromotion;

@WriterRepository
public interface UnionPromotionMapper {
    /**
     * 通过主键删除  
     *
     * @MLTH_generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入数据  
     *
     * @MLTH_generated
     */
    int insert(UnionPromotion record);

    int insertSelective(UnionPromotion record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    Map<String, Object> selectByPrimaryKey(@Param("id") Integer id);

    List<UnionPromotion> selectListByPrimaryKeyS(Long[] idList);
    
    int updateByPrimaryKeySelective(UnionPromotion record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(UnionPromotion record);
    
    
    /**
     * 获取当地联盟活动列表
     *
     * @MLTH_generated
     */
    List<UnionPromotion> selectUnionList(Map<String, String> map);
    
    /**
     * 获取联盟活动详情
     *
     * @MLTH_generated
     */
    UnionPromotion selectUnionDetails(Integer id);
    
    UnionPromotion selectBaseInfoByPrimaryKey(Integer id);
    
    /**
     * 通过产品id获取活动di和名称
     *
     * @MLTH_generated
     */
    UnionPromotion selectPromotionByProductId(Long product_id);
    /**
     * 获取现金券详情
     *
     * @MLTH_generated
     */
    UnionPromotion selectCashDetails(Integer id);
    
    /**
     * 获取消费者个人现金券
     *
     * @MLTH_generated
     */
    List<UnionPromotion> selectCashByCustomerId(Long customer_id);
    
    
  List<Map<String, Object>> queryUnionPromotionForPage(Page<Map<String, Object>> map);
  
  List<Map<String, Object>> selectUnionCon(@Param("ids") String ids);
    
  	/**
  	 * 修改下架 上架状态,0:下架;1:上架 
  	 * unionPromotionID 联盟活动ID
  	 * onOffFlag 0:下架;1:上架
  	 */
  	int updateOnOffFlag(@Param("unionPromotionID")Integer unionPromotionID,@Param("onOffFlag")Integer onOffFlag);
  	
  	int updateSellNumByPrimaryKey(@Param("id")Integer id, @Param("cash_sell_num")Integer cash_sell_num);
  	
  	List<UnionPromotion> selectHaveCashListForCart(@Param("customer_id") Long customer_id, @Param("productIdList")List<Long> productIdList, @Param("r_List")List<String> r_List);
  	
  	UnionPromotion selectHaveCashOfOneProductForCart(@Param("customer_id") Long customer_id, @Param("product_id")Long product_id, @Param("r_List")List<String> r_List);
  	
  	
  	
}