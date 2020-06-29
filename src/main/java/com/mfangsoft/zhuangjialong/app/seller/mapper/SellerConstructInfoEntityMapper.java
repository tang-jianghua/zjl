package com.mfangsoft.zhuangjialong.app.seller.mapper;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.app.seller.model.SellerConstructInfoEntity;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
@WriterRepository
public interface SellerConstructInfoEntityMapper {
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
    int insert(SellerConstructInfoEntity record);

    int insertSelective(SellerConstructInfoEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    SellerConstructInfoEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SellerConstructInfoEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(SellerConstructInfoEntity record);
    
    SellerConstructInfoEntity selectStateBySellerId(@Param("seller_id") Long seller_id);
    
    Integer modifyworkingstate(@Param("break_off_state") Integer break_off_state , @Param("seller_id") Long seller_id);
    
    Integer modifyVerifystate(@Param("certification_state") Integer certification_state , @Param("seller_id") Long seller_id, @Param("info") String info);
}