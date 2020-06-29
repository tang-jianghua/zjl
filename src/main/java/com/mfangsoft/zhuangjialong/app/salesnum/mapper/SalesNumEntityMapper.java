package com.mfangsoft.zhuangjialong.app.salesnum.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.app.salesnum.model.SalesNumEntity;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;

@WriterRepository
public interface SalesNumEntityMapper {
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
    int insert(SalesNumEntity record);

    int insertSelective(SalesNumEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    SalesNumEntity selectByPrimaryKey(Long id);
    
    /*
     * 查询所有产品销量
     */
    List<SalesNumEntity> selectAllSalesNum();

    int updateByPrimaryKeySelective(SalesNumEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(SalesNumEntity record);
    /*
     * 增加（0-100）的随机数
     */
    int UpdateSalesNum();
    
    int updateForCustmer(@Param("num")int num, @Param("product_id")Long product_id);
    
    /*
     *根据产品id查询 
     */
    SalesNumEntity selectByProductId(Long product_id);
    
}