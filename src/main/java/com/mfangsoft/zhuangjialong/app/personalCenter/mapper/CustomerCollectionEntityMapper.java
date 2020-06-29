package com.mfangsoft.zhuangjialong.app.personalCenter.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.app.personalCenter.model.CollectBrandModel;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.CustomerCollectionEntity;
import com.mfangsoft.zhuangjialong.app.product.model.ListProductModel;
import com.mfangsoft.zhuangjialong.app.product.model.ProductListModel;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;

@WriterRepository
public interface CustomerCollectionEntityMapper {
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
    int insert(CustomerCollectionEntity record);

    int insertSelective(CustomerCollectionEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    CustomerCollectionEntity selectByPrimaryKey(Integer id);

    /**
     * 查询消费者收藏的产品id
     *
     * @MLTH_generated
     */
    List<Map<String, Object>> selectCollectionProductByCustomerIdForPage(Page<Map<String, Object>> page);
    
    /**
     * 查询消费者收藏的产品 
     *
     * @MLTH_generated
     */
    List<ProductListModel> selectCollectionProductsByCustomerIdForPage(Page<ProductListModel> page);
    
    int updateByPrimaryKeySelective(CustomerCollectionEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(CustomerCollectionEntity record);
    
    /**
     * 通过customer_id,product_id/brand_id修改状态  
     *
     * @MLTH_generated
     */
    int updateByCustomerId(CustomerCollectionEntity record);
    
    /**
     *通过消费者id 和 产品id / 品牌id 查询数据
     *
     * @MLTH_generated
     */
    CustomerCollectionEntity selectIfCollected(CustomerCollectionEntity record);
    
    /**
     *通过消费者id 和 产品id / 品牌id 查询被关注的数据
     *
     * @MLTH_generated
     */
    CustomerCollectionEntity selectIfCollecting(CustomerCollectionEntity record);
    
    /**
     *通过消费者id查询关注品牌
     *
     * @MLTH_generated
     */
    List<CollectBrandModel> selectBrandByCustomerId(Map<String, Object>  param);
    
    
    /**
     *批量取消收藏
     *
     * @MLTH_generated
     */
    int updateByIds(Map<String, Object> param);
}