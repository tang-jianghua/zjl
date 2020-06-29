package com.mfangsoft.zhuangjialong.app.salesnum.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mfangsoft.zhuangjialong.app.newproductcore.service.ProductCoreService;
import com.mfangsoft.zhuangjialong.app.salesnum.mapper.SalesNumEntityMapper;
import com.mfangsoft.zhuangjialong.app.salesnum.model.SalesNumEntity;
import com.mfangsoft.zhuangjialong.app.salesnum.service.SalesNumService;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年10月18日
* 
*/
public class SalesNumQuartz {
	
	@Autowired
	SalesNumService salesNumServiceImpl;
	@Autowired
	SalesNumEntityMapper salesNumEntityMapper;
	@Autowired
	ProductCoreService productCoreServiceImpl;
	private void updateSalesNum(){
		salesNumServiceImpl.updateSalesNum();
		List<SalesNumEntity> list = salesNumEntityMapper.selectAllSalesNum();
		productCoreServiceImpl.updateSalesNumDocs(list);
	}
	
	
}
