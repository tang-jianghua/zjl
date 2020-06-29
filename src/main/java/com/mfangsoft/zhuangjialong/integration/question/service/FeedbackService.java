package com.mfangsoft.zhuangjialong.integration.question.service;

import java.util.Map;

import com.mfangsoft.zhuangjialong.app.helpfeedback.model.HelpFeedBackEntity;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.coupons.model.BrandCouponsEntity;
@WriterRepository
public interface FeedbackService {
	
	
	Page<Map<String,Object>> getHelpFeedBackForPage(Page<Map<String,Object>> page);
	
	
    Boolean  sendRedBag(Integer id, BrandCouponsEntity brandCouponsEntity); 
    
    
    Boolean  sendCoupons(Integer id,BrandCouponsEntity brandCouponsEntity); 

}
