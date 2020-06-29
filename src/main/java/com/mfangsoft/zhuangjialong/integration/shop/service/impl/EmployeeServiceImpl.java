package com.mfangsoft.zhuangjialong.integration.shop.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.integration.shop.mapper.EmployeeEntityMapper;
import com.mfangsoft.zhuangjialong.integration.shop.model.EmployeeEntity;
import com.mfangsoft.zhuangjialong.integration.shop.model.ShopEntity;
import com.mfangsoft.zhuangjialong.integration.shop.service.EmployeeService;
@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeEntityMapper employeeEntityMapper;
	@Override
	public Boolean addEmployee(EmployeeEntity employeeEntity) {
		// TODO Auto-generated method stub
		ShopEntity entity = (ShopEntity) UserContext.getCurrentUserInfo();
		employeeEntity.setShop_id(entity.getId());
		employeeEntity.setCreate_time(new Date());
		if(employeeEntityMapper.insert(employeeEntity)>0){
			return true;
		}
		
		return false;
	}

	@Override
	public Boolean modifyEmployee(EmployeeEntity employeeEntity) {
		// TODO Auto-generated method stub
		employeeEntity.setUpdate_time(new Date());
		if(employeeEntityMapper.updateByPrimaryKeySelective(employeeEntity)>0){
			return true;
		}
		return false;
	}

	@Override
	public EmployeeEntity getEmployeeById(Integer id) {
		// TODO Auto-generated method stub
		return employeeEntityMapper.selectByPrimaryKey(id);
	}

	@Override
	public Page<EmployeeEntity> getEmployeeForPage(Page<EmployeeEntity> page) {
		// TODO Auto-generated method stub
		
		Map<String,Object> mpa=(Map<String, Object>) page.getParam();
		ShopEntity entity = (ShopEntity) UserContext.getCurrentUserInfo();
		mpa.put("shop_id", entity.getId());
		page.setData(employeeEntityMapper.selectEmployeeListForPage(page));
		return page;
	}

	@Override
	public Boolean removeEmployeeById(Integer id) {
		// TODO Auto-generated method stub
		if(employeeEntityMapper.deleteByPrimaryKey(id)>0){
			return true;
		}
		return false;
	}

}
