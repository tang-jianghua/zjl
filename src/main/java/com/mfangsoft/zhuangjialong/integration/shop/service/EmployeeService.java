package com.mfangsoft.zhuangjialong.integration.shop.service;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.integration.shop.model.EmployeeEntity;

public interface EmployeeService {

	
	/**
	 * 添加店铺信息
	 * @param shopEntity
	 * @return
	 */
	Boolean addEmployee(EmployeeEntity shopEntity);
	
	/**
	 * 修改店铺信息
	 * @param shopEntity
	 * @return
	 */
	Boolean modifyEmployee(EmployeeEntity shopEntity);
	
	/**
	 * 通过Id获取店铺信息
	 * @param id
	 * @return
	 */
	EmployeeEntity getEmployeeById(Integer id);
	
	
	/**
	 * 通过Id获取店铺信息
	 * @param id
	 * @return
	 */
	Boolean removeEmployeeById(Integer id);
	
	/**
	 * 分页查询店铺信息
	 * @param page
	 * @return
	 */
	Page<EmployeeEntity> getEmployeeForPage(Page<EmployeeEntity> page);
}
