package com.mfangsoft.zhuangjialong.app.applogin.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.app.applogin.model.CustomerEntity;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
@WriterRepository
public interface CustomerEntityMapper {
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
    int insert(CustomerEntity record);

    int insertSelective(CustomerEntity record);
    
    List<CustomerEntity> selectGuildCustomerList(@Param("seller_id")Long seller_id);
    
    int selectInvitCount(@Param("customer_id") Long customer_id);

    List<CustomerEntity> selectGuildCustomerListPage(Page<CustomerEntity> param);
    
    int selectGuildCustomerCount(@Param("exshopper_id") Long exshopper_id);
    
    int selectUnReadGuildCustomerCount(@Param("exshopper_id") Long exshopper_id);
    
    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    CustomerEntity selectByPrimaryKey(Long id);

    CustomerEntity queryreceivemessage(@Param("customer_id")Long customer_id);
    
    int updateByPrimaryKeySelective(CustomerEntity record);

    /**
     * 通过账号查询数据  
     *
     * @MLTH_generated
     */
    CustomerEntity selectByAccount(String account);
    
/*    *//**
     * 通过id查询消费者喜好 
     *
     * @MLTH_generated
     *//*
    Map<String, Object> selectFavorByPrimaryKey(Page<ListProductModel> page);*/
    
    
    List<Map<String,Object>> queryCustomerForPage(Page<Map<String,Object>> page);
    
    void updatereceivemessage(CustomerEntity record);
    
    Map<String,Object> selectCustomerById(Long customer_id);
    
    int selectExshopperIdCount(Long id);
    
    List<Map<String,Object>> queryCustomerBackForPage(Page<Map<String,Object>> page);
    /**
     * 通过昵称查询数据  
     *
     */
    CustomerEntity selectByName(String name);
    /*
     * 查询消费者在某段时间内的邀请数
     */
    List<CustomerEntity> selectInvitCountByDate(Map<String, Object> map);
    
    List<CustomerEntity> selectAllcustomer();
}