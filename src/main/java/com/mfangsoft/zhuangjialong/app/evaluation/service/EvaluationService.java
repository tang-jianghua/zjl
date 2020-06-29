package com.mfangsoft.zhuangjialong.app.evaluation.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.mfangsoft.zhuangjialong.app.evaluation.model.EvaluationEntity;
import com.mfangsoft.zhuangjialong.app.evaluation.model.EvaluationModel;
import com.mfangsoft.zhuangjialong.app.evaluation.model.EvaluationNumModel;
import com.mfangsoft.zhuangjialong.app.order.model.OrderProduct;
import com.mfangsoft.zhuangjialong.common.model.Page;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年6月24日
* 
*/

public interface EvaluationService {

    boolean addEvaluation(EvaluationEntity record);
	  /**
     * 通过产品id获取评价数
     *
     * @MLTH_generated
     */
    EvaluationNumModel selectEvaluationNumByProductId(EvaluationEntity param);
    
    
    /**
     * 通过产品id获取评价数
     *
     * @MLTH_generated
     */
    Page<EvaluationModel> getSqlEvaluationByProductIdForPage(Page<EvaluationModel> page);
    
    OrderProduct getonproductforevaluation(EvaluationEntity param);
}
