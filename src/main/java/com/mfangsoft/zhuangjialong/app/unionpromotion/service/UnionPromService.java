package com.mfangsoft.zhuangjialong.app.unionpromotion.service;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.app.product.model.ListProductModel;
import com.mfangsoft.zhuangjialong.app.product.model.ProductListModel;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.integration.promotion.model.UnionCustomer;
import com.mfangsoft.zhuangjialong.integration.promotion.model.UnionPromotion;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年7月21日
* 
*/

public interface UnionPromService {

	
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
    UnionPromotion selectUnionDetails(UnionCustomer param);
	
    /**
     * 获取现金券详情
     *
     * @MLTH_generated
     */
    UnionPromotion selectCashDetails(UnionCustomer param);
    
    /**
     * 添加现金券
     *
     * @MLTH_generated
     */
    boolean addCash(UnionCustomer param);
    
    /**
     * 获取品牌联盟产品
     *
     * @MLTH_generated
     */
    Page<Map<String, Object>> getUnionProducts(Page<Map<String, Object>> page);
    /**
     * 获取品牌联盟产品
     *
     * @MLTH_generated
     */
    Page<Map<String, Object>> queryunionproducts(Page<Map<String, Object>> page);
    /**
     * 获取品牌联盟产品
     *
     * @MLTH_generated
     */
    Page<ProductListModel> getSqlUnionProducts(Page<ProductListModel> page);
}
