package com.mfangsoft.zhuangjialong.app.applogin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.app.applogin.model.CustomerAddress;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
@WriterRepository
public interface CustomerAddressMapper {
    /**
     * 通过主键删除  
     *
     * @MLTH_generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 通过主键用户id删除  唯一地址
     *
     * @MLTH_generated
     */
    int deleteByPrimaryKeyAndCustomerId(@Param("id") Long id, @Param("customer_id") Long customer_id);
    
    /**
     * 插入数据  
     *
     * @MLTH_generated
     */
    int insert(CustomerAddress record);

    int insertSelective(CustomerAddress record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    CustomerAddress selectByPrimaryKey(@Param("id") Long id);
    
    /**
     * 通过用户id查询地址列表
     *
     * @MLTH_generated
     */
    List<CustomerAddress> selectByCustomerId(Long customer_id);

    List<CustomerAddress> selectByCustomerIdAndRegionCode(@Param("customer_id")Long customer_id,@Param("region_code")String region_code);
    
    int updateByPrimaryKeySelective(CustomerAddress record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(CustomerAddress record);
    
    int updateStateByCustomerId(CustomerAddress record);
}