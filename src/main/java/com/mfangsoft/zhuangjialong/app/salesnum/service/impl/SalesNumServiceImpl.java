package com.mfangsoft.zhuangjialong.app.salesnum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.newproductcore.service.ProductCoreService;
import com.mfangsoft.zhuangjialong.app.salesnum.mapper.SalesNumEntityMapper;
import com.mfangsoft.zhuangjialong.app.salesnum.model.SalesNumEntity;
import com.mfangsoft.zhuangjialong.app.salesnum.service.SalesNumService;
import com.mfangsoft.zhuangjialong.common.utils.JPushUtil;
import com.mfangsoft.zhuangjialong.core.quartz.Quest;
import com.mfangsoft.zhuangjialong.core.quartz.QuestsManagerBean;

@Service
public class SalesNumServiceImpl implements SalesNumService{

	@Autowired
	SalesNumEntityMapper salesNumEntityMapper;
	@Autowired
	ProductCoreService productCoreServiceImpl;
	
	
	@Override
	public void updateSalesNum() {
		salesNumEntityMapper.UpdateSalesNum();
	}

	
	@Override
	public Boolean updateSaleNum(Long product_id, Long sale_num) {
		SalesNumEntity salesNumEntity = salesNumEntityMapper.selectByProductId(product_id);
		
		if(salesNumEntity!=null){
			salesNumEntity.setSales_num(salesNumEntity.getSales_num()+sale_num);
			QuestsManagerBean.addQuest(new Quest() {
				@Override
				public boolean execute() {
					salesNumEntityMapper.updateByPrimaryKey(salesNumEntity);
					productCoreServiceImpl.updateSalesNumDocsByProductId(product_id,salesNumEntity.getSales_num().intValue());
					return true;
				}
				
				@Override
				public boolean condition() {
					return true;
				}
				
				@Override
				public boolean delete() {
					return true;
				}
			});
		}else{
			SalesNumEntity entity = new SalesNumEntity();
			entity.setProduct_id(product_id);
			entity.setSales_num(sale_num);
			QuestsManagerBean.addQuest(new Quest() {
				@Override
				public boolean execute() {
					salesNumEntityMapper.insertSelective(entity);
					productCoreServiceImpl.updateSalesNumDocsByProductId(product_id,sale_num.intValue());
					return true;
				}
				
				@Override
				public boolean condition() {
					return true;
				}
				
				@Override
				public boolean delete() {
					return true;
				}
			});
		}
		return true;
	}


	
}
