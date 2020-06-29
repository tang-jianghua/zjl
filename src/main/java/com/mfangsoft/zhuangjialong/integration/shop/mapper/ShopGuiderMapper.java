package com.mfangsoft.zhuangjialong.integration.shop.mapper;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.shop.model.ShopGuiderModel;

@WriterRepository
public interface ShopGuiderMapper extends BaseShopGuiderEntityMapper{

	/*
	 * 查询店铺导购
	 */
     List<ShopGuiderModel> selectShopGuiderForPage(Page<ShopGuiderModel> page);
     
     /*
      * 根据卖家id修改卖家状态
      */
    int updateCertificationStateBySellerId(Map<String, Object> map);
    
    /*
     * 查询导购认证信息
     */
    ShopGuiderModel selectCertificationInfoBySellerId(Long id);
    
    /*
     * 用过卖家id获取店铺导购认证状态
     */
    int selectCertificationStateBySellerId(Long id);
}
