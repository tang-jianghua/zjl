package com.mfangsoft.zhuangjialong.integration.brandclassify.service;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.core.exception.ServiceException;
import com.mfangsoft.zhuangjialong.integration.brandclassify.model.BrandClassifyEntity;

public interface BrandClassifyService {
	
	
	Boolean  addBrandClassify(BrandClassifyEntity brandClassifyEntity) throws ServiceException;
	
	
	Boolean  modifyBrandClassify(BrandClassifyEntity brandClassifyEntity) throws ServiceException;
	
	
	Boolean removeBrandClassify(Long id) throws ServiceException;
	
	
	Page<BrandClassifyEntity> queryBrandClassifiesForPage(Page<BrandClassifyEntity> page) throws ServiceException;
	
	
	List<BrandClassifyEntity> queryBrandClassifies() throws ServiceException;
	
	
	BrandClassifyEntity getBrandClassifyById(Long id) throws ServiceException;

}
