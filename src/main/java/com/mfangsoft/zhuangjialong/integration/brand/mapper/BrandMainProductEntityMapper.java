package com.mfangsoft.zhuangjialong.integration.brand.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandMainProductEntity;
@WriterRepository
public interface BrandMainProductEntityMapper {
    /**
     * 通过主键删除  
     *
     * @MLTH_generated
     */
    int deleteByPrimaryKey(Long id);
    /**
     * 批量删除首页单品设定
     * @param brandMainProductIDList
     * @return
     */
    int batchDeleteBrandMainProductByIDs(@Param("brandMainProductIDList")List<Long> brandMainProductIDList);
    /**
     * 插入数据  
     *
     * @MLTH_generated
     */
    int insert(BrandMainProductEntity record);

    int insertSelective(BrandMainProductEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    BrandMainProductEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BrandMainProductEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(BrandMainProductEntity record);
    
    /**
     * 通过品牌id获取首页产品id  
     *
     * @MLTH_generated
     */
    List<Long> selectProductIdByBrandId(Long brand_id);
    /**
     * 品牌管理员后台-品牌管理-界面装修-首页单品设定列表分页显示
     */
    List<Map<String, Object>> findBrandMainProductListPage(Page<Map<String, Object>> page);
}