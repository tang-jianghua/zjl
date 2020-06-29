package com.mfangsoft.zhuangjialong.app.evaluation.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.app.evaluation.model.EvaluationEntity;
import com.mfangsoft.zhuangjialong.app.evaluation.model.EvaluationModel;
import com.mfangsoft.zhuangjialong.app.evaluation.model.EvaluationNumModel;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;

@WriterRepository
public interface EvaluationEntityMapper {
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
    int insert(EvaluationEntity record);

    long insertSelective(EvaluationEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    EvaluationEntity selectByPrimaryKey(Long id);

    List<EvaluationEntity> selectListByOrderId(@Param("order_id") Long order_id);
    
    int updateByPrimaryKeySelective(EvaluationEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(EvaluationEntity record);
    

    /**
     * 通过产品id获取评价数
     *
     * @MLTH_generated
     */
    EvaluationNumModel selectEvaluationNumByProductId(Long product_id);
    
    /**
     * 通过产品id获取总评价数
     *
     * @MLTH_generated
     */
    Long selectAllEvaluationNumByProductId(Long product_id);
    
    
    List<Map<String,Object>> selectEvaluationForPage(Page<Map<String, Object>> page);
    
    
    int insertreply(Map<String,Object> map);
    
    /**
     * 通过订单 id获取评论列表
     *
     * @MLTH_generated
     */
    List<EvaluationEntity> selectEvaluationListByOrderCode(String code);
    
    /**
     * 获取产品详情页的评论
     *
     * @MLTH_generated
     */
    EvaluationModel  selectOneGoodByProductId(Long product_id);
    
    /**
     * 获取产品的评论
     *
     * @MLTH_generated
     */
    List<EvaluationModel> selectEvaluationByProductIdForPage(Page<EvaluationModel> page);
}