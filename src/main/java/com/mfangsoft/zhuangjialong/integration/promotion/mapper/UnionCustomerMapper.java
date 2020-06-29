package com.mfangsoft.zhuangjialong.integration.promotion.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.promotion.model.UnionCustomer;
import com.mfangsoft.zhuangjialong.integration.promotion.model.UnionPromotion;
import com.mfangsoft.zhuangjialong.integration.promotion.model.UnionPromotionParam;
@WriterRepository
public interface UnionCustomerMapper {
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
    int insert(UnionCustomer record);

    int insertSelective(UnionCustomer record);
    
    UnionCustomer selectByEntity(UnionCustomer record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    UnionCustomer selectByPrimaryKey(Integer id);
    
    UnionCustomer selectBase(@Param("customer_id") Long customer_id, @Param("promotion_id")Integer promotion_id);
    
    int selectHaveCashForCart(@Param("customer_id") Long customer_id, @Param("brand_id")Long brand_id);

    List<UnionPromotion> selectHaveCashListForCart(@Param("customer_id") Long customer_id, @Param("productIdList")Long[] productIdList, @Param("r_List")List<String> r_List);
    
    UnionPromotion selectCanUseCashListForCartOrderPriceByCustomerId(@Param("customer_id") Long customer_id,@Param("product_id") Long product_id);
    
    UnionPromotion selectCanUseCashOneForCartOrderPrice(@Param("customer_id") Long customer_id, @Param("brand_id")Long brand_id);
    
    List<UnionPromotionParam> selectListForCartByCustomerAndPromIdNew(@Param("customer_id") Long customer_id, @Param("promotionIdList")Long[] promotionIdList);
    
    List<UnionPromotion> selectListForCartByCustomerAndPromId(@Param("customer_id") Long customer_id, @Param("promotionIdList")Long[] promotionIdList);
    
    int updateByPrimaryKeySelective(UnionCustomer record);
    
    UnionPromotion selectOneProductByProductId(@Param("product_id") Long product_id);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(UnionCustomer record);
    
    int updateUnuseStateByPromotionId(@Param("customer_id")Long customer_id, @Param("promotion_id")Integer promotion_id);

    /**
     * 查询是否购买过现金券  
     *
     * @MLTH_generated
     */
    int selectWitherHasCash(UnionCustomer record);
    
    int updateByPromotion_Id(UnionCustomer record );
}