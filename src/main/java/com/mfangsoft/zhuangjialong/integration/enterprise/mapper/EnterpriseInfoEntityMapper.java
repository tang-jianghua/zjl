package com.mfangsoft.zhuangjialong.integration.enterprise.mapper;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.app.brand.model.BrandModel;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseInfoEntity;
@WriterRepository
public interface EnterpriseInfoEntityMapper {
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
    int insert(EnterpriseInfoEntity record);

    int insertSelective(EnterpriseInfoEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    EnterpriseInfoEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(EnterpriseInfoEntity record);

    int updateByPrimaryKeyWithBLOBs(EnterpriseInfoEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(EnterpriseInfoEntity record);

    /**
     * 通过企业id获取企业信息（type传值 0：公司简介；1：发展历程；2：工程案例；3：品牌荣誉；4：店面风采）
     *
     * @MLTH_generated
     */
    String selectEnterpriseHtmlByBrandId(BrandModel param);
    
    
    EnterpriseInfoEntity getEnterpriseProfilesByUserId(Map<String,Object> map);
    
    List<EnterpriseInfoEntity> getEnterpriseDevelopmentByUserId(Map<String,Object> map);
    
}