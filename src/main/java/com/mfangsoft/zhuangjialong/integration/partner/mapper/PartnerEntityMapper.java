package com.mfangsoft.zhuangjialong.integration.partner.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.app.buildClass.model.BuildClass;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.partner.model.PartnerEntity;
import com.mfangsoft.zhuangjialong.integration.partner.model.PartnerModle;
@WriterRepository
public interface PartnerEntityMapper {
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
    int insert(PartnerEntity record);

    int insertSelective(PartnerEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    PartnerEntity selectByPrimaryKey(Long id);
    
    List<PartnerModle> selectAllPartner();
    
    long selectBySysUserId(@Param("sys_user_id") Long sys_user_id);
    
    PartnerEntity selectAllByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PartnerEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(PartnerEntity record);
    
    
    List<Map<String,Object>> queryPartnerForPage(Page<Map<String,Object>> page);
    
    
    PartnerEntity getPartnerEntity(Long userId);
    
    
    
    List<PartnerEntity> getPartnerName();
    
    /*
     * 查询该城市合伙人下的品类
     */
    List<BuildClass> selectClassByPartnerId(Long partner_id);
}