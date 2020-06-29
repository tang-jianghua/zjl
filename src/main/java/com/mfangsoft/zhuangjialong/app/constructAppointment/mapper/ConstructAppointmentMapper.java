package com.mfangsoft.zhuangjialong.app.constructAppointment.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.app.constructAppointment.model.ConstructAppointment;
import com.mfangsoft.zhuangjialong.app.constructAppointment.model.ConstructAppointmentModel;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
@WriterRepository
public interface ConstructAppointmentMapper {
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
    int insert(ConstructAppointment record);

    int insertSelective(ConstructAppointment record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    ConstructAppointment selectByPrimaryKey(Long id);

    ConstructAppointmentModel selectInfoById(Long id);
    
    int updateByPrimaryKeySelective(ConstructAppointment record);
    
    int updateconstructdata(@Param("id") Long id, @Param("construct_data") Double construct_data);
    
    List<Map<String,String>> selectEvaluationsForPage(Page<Map<String,String>> param);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(ConstructAppointment record);
    
    /**
     * 查询某消费者的施工列表 
     *
     * @TOP_generated
     */
    List<ConstructAppointmentModel> selectConstructAppointmentByCustomerIdForPage(Page<ConstructAppointmentModel> param);
    
    List<ConstructAppointmentModel>  selectAppointmentOfConstructerForPage(Page<ConstructAppointmentModel> param);
    
    /**
     * 后台个人中心查询一个用户的施工详情
     * @param param
     * @return
     */
    ConstructAppointmentModel selectConstructerAppointmentAndTraceingBackByAppopintId(Long id);
    
    int selectAppointmentCountByConstructId(Long id);
    
    int selectUnReadAppointmentCountByConstructId(Long id);
    
    List<ConstructAppointmentModel> selectConstructAppointmentBySellerIdForPage(Page<ConstructAppointmentModel> param);
    /**
     * 开发者查询施工预约
     * @param param
     * @return
     */
    List<Map<String,Object>> selectConstructAppointmentBackForPage(Page<Map<String,Object>> param);
    /**
     * 消费者查询施工评价
     * @param param
     * @return
     */
     List<ConstructAppointment> selectEvaluationOfConstructerForPage(Page<ConstructAppointment> param);
     
     /**
      * 消费者查询施工评价图片
      * @param param
      * @return
      */
     List<String> selectEvaluationImages(Long appointment);
     
     
    int  addEvaImgs(ConstructAppointment param);
}