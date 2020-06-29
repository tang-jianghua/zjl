package com.mfangsoft.zhuangjialong.integration.question.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.helpfeedback.mapper.HelpFeedBackEntityMapper;
import com.mfangsoft.zhuangjialong.app.helpfeedback.model.HelpFeedBackEntity;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.core.exception.ServiceException;
import com.mfangsoft.zhuangjialong.integration.coupons.mapper.BrandCouponsEntityMapper;
import com.mfangsoft.zhuangjialong.integration.coupons.model.BrandCouponsEntity;
import com.mfangsoft.zhuangjialong.integration.question.service.FeedbackService;
@Service
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
	private  HelpFeedBackEntityMapper backEntityMapper;
	
	private  BrandCouponsEntityMapper brandCouponsEntityMapper;
	@Override
	public Page<Map<String,Object>> getHelpFeedBackForPage(Page<Map<String,Object>> page) {
		// TODO Auto-generated method stub
		
		page.setData(backEntityMapper.getHelpFeedBackForPage(page));
		return page;
	}

	@Override
	public Boolean sendRedBag(Integer id,BrandCouponsEntity brandCouponsEntity) throws ServiceException {
		// TODO Auto-generated method stub
		
		HelpFeedBackEntity backEntity=backEntityMapper.selectByPrimaryKey(id);
		//brandCouponsEntity.setCustomer_id(backEntity.getFeedbacker_id());
		brandCouponsEntityMapper.insert(brandCouponsEntity);
		return true;
	}

	@Override
	public Boolean sendCoupons(Integer id,BrandCouponsEntity brandCouponsEntity) throws ServiceException {
		// TODO Auto-generated method stub
		HelpFeedBackEntity backEntity=backEntityMapper.selectByPrimaryKey(id);
		//brandCouponsEntity.setCustomer_id(backEntity.getFeedbacker_id());
		brandCouponsEntityMapper.insert(brandCouponsEntity);
		return true;
	}

}
