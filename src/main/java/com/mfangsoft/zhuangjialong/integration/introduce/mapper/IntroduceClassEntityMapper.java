package com.mfangsoft.zhuangjialong.integration.introduce.mapper;

import java.util.List;


import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.app.main.model.IntroduceProductModel;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.introduce.model.IntroduceClassEntity;
import com.mfangsoft.zhuangjialong.integration.introduce.model.PartnerClass;
@WriterRepository
public interface IntroduceClassEntityMapper {
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
    int insert(IntroduceClassEntity record);

    int insertSelective(IntroduceClassEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    IntroduceClassEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(IntroduceClassEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(IntroduceClassEntity record);
    
    List<IntroduceClassEntity> selectByPartner(Long partner_id);
    
    List<PartnerClass> selectPartnerClass(Long partner_id);
    
    IntroduceClassEntity  selectclassByPartnerClassId(@Param("partner_id")Long partner_id, @Param("class_id")Long class_id);
    
    /*
     * 查询推荐产品
     */
    List<IntroduceProductModel> selectClassForAppForPage(Page<IntroduceProductModel> page);
}