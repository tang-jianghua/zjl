package com.mfangsoft.zhuangjialong.integration.shop.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.app.shop.model.Shop;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.shop.model.ShopEntity;
@WriterRepository
public interface ShopEntityMapper {
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
    int insert(ShopEntity record);

    int insertSelective(ShopEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    ShopEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ShopEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(ShopEntity record);
    
    /**
     * 通过品牌id获取店铺信息
     *
     * @MLTH_generated
     */
    List<Shop> selectShopInfoByBrandId(ShopEntity param);
    
    /**
     * 通过产品id获取店铺信息
     *
     * @MLTH_generated
     */
    List<Shop> selectShopInfoByProductId(Long product_id);
    
    
    List<Map<String, Object>> getShopForPage(Page<Map<String, Object>> page);
    
    
    List<ShopEntity> getShopNameByBrand(Long brand_id);
    
    List<ShopEntity> getShopName();
    
    ShopEntity  selectshopByUserId(Long userId);
    
    Map<String,Object> getShopEntityById(Long shopId);
    
    /*
     * 根据品牌id获取店铺列表
     */
    List<Shop> selectShopByBrandId(Long brand_id);
    
    List<Long> selectAllShop(@Param("shop_id_list")List<Long> shop_id_list);
    
}