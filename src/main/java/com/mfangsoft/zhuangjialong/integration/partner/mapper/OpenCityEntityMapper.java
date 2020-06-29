package com.mfangsoft.zhuangjialong.integration.partner.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.partner.model.CustomerSMSModel;
import com.mfangsoft.zhuangjialong.integration.partner.model.OpenCityEntity;
@WriterRepository
public interface OpenCityEntityMapper {
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
    int insert(OpenCityEntity record);

    int insertSelective(OpenCityEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    OpenCityEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OpenCityEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(OpenCityEntity record);
    
    
    List<Map<String,Object>>  queryOpenCity();
    
    List<CustomerSMSModel> selectUnEndOrderCustomerByPartnerId(Long partner_idl);
    
    
    
    List<Integer> queryOpenCtiyByPartnerId(@Param("partner_id")Long partner_id,@Param("state")Integer state);
}