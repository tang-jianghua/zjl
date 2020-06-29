package com.mfangsoft.zhuangjialong.app.prepay.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.app.prepay.model.ShopPrepayEntity;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
@WriterRepository
public interface ShopPrepayEntityMapper {
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
    int insert(ShopPrepayEntity record);

    int insertSelective(ShopPrepayEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    ShopPrepayEntity selectByPrimaryKey(Integer id);

    List<ShopPrepayEntity> selectByCustomerIdForPCenter(@Param("customer_id")Long customer_id );
    
    List<ShopPrepayEntity> selectByCustomerId(@Param("customer_id")Long customer_id , @Param("shop_id")Long shop_id);
    
    List<ShopPrepayEntity> selectByCustomerIdForCart(@Param("customer_id")Long customer_id , @Param("shop_id")Long shop_id,@Param("usedIds")List<String> usedIds);
    
    int updateByPrimaryKeySelective(ShopPrepayEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(ShopPrepayEntity record);
    
    int updateUnuseStateByPrimaryKey(@Param("id")Integer id);
    
}