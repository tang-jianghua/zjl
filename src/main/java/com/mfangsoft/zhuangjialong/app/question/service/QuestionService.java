package com.mfangsoft.zhuangjialong.app.question.service;

import java.util.List;

import com.mfangsoft.zhuangjialong.integration.question.model.QuestionEntity;
import com.mfangsoft.zhuangjialong.integration.question.model.QuestionTypeEntity;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年6月12日
* 
*/
public interface QuestionService {

	 /**
    * 获取所有问题类型
    *
    * @MLTH_generated
    */
   public List<QuestionTypeEntity> selectQuestionsInTypes();
   
   /**
    * 通过主键获取问题详情  
    *
    * @MLTH_generated
    */
   public String selectDetailsByPrimaryKey(QuestionEntity param);
}
