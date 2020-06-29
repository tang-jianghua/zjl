package com.mfangsoft.zhuangjialong.integration.enterprise.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.BaseEnterpriseTwoEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.BuildClassEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseInfoEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseOneEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseTwoEntity;

public interface EnterpriseService {
	
	/**
	 * 添加企业信息
	 * @param enterpriseEntity
	 * @return
	 */
	Boolean  addEnterprise(EnterpriseEntity enterpriseEntity);
	
	EnterpriseInfoEntity getEnterpriseProfilesByUserId(Integer type);
	
	List<EnterpriseInfoEntity> getEnterpriseDevelopmentByUserId(Integer type);
	
	/**
	 * 通Id获取企业信息
	 * @param id
	 * @return
	 */
	EnterpriseEntity  getEnterpriseById(Long id);

	/**
	 * 修改企业
	 * @param enterpriseEntity
	 * @return
	 */
	Boolean  modifyEnterprise(EnterpriseEntity enterpriseEntity);
	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	Page<Map<String,Object>>  getEnterpriseForPage(Page<Map<String,Object>> page);
	/**
	 * 开启关闭企业用户
	 * @param id
	 * @param state
	 * @return
	 */
	Boolean closeOrOpenEnterprise(Long id,Integer state);
	
	/**
	 * 获取当前用户企业信息
	 * @param userId
	 * @return
	 */
	EnterpriseEntity  getEnterpriseEntity(Long userId);
	
	
	List<EnterpriseEntity> getEnterpriseName();
	
	/**
	 * 添加企业信息
	 * @param enterpriseEntity
	 * @return
	 */
	Boolean  addEnterpriseInfo(EnterpriseInfoEntity enterpriseEntity);
	
	/**
	 * 通过id查询企业信息
	 * @param id
	 * @return
	 */
	EnterpriseInfoEntity  getEnterpriseInfoById(Long id);
	
	/**
	 * 修改企业信息
	 * @param enterpriseEntity
	 * @return
	 */
	Boolean  modifyEnterpriseInfo(EnterpriseInfoEntity enterpriseEntity);
	
	
	List<BuildClassEntity>  getBuildClassEntities();
	
	
	
	List<Map<String,Object>> getEnterpriseInfoList(Map<String,Object> map);
	
	/**
	 * 添加企业二级
	 * @param enterpriseOneEntity
	 * @return
	 */
	Boolean addEnterpriseInfoOne(EnterpriseOneEntity enterpriseOneEntity);
	
	

	/**
	 * 添加企业二级
	 * @param enterpriseTwoEntity
	 * @return
	 */
	Boolean addEnterpriseInfoTwo(List<EnterpriseTwoEntity>  enterpriseTwoEntity);
	
	
	
	/**
	 * 修改企业一级
	 * @param enterpriseOneEntity
	 * @return
	 */
	Boolean  modifyEnterpriseInfoOne(EnterpriseOneEntity enterpriseOneEntity);
	
	
	/**
	 * 通过id查询企业二级
	 * @param id
	 * @return
	 */
	BaseEnterpriseTwoEntity  getEnterpriseInfoTwo(Long id);
	
	
	/**
	 * 通过Id查询企业一级
	 * @param id
	 * @return
	 */
	EnterpriseOneEntity  getEnterpriseInfoOne(Long id);
	
	
	/**
	 * 修改企业二级
	 * @param enterpriseTwoEntity
	 * @return
	 */
	Boolean  modifyEnterpriseInfoTwo(EnterpriseTwoEntity enterpriseTwoEntity);
	
	
	
	
	List<EnterpriseEntity>  getEnterpriseBrandName();
	
	
	/**
	 * 修改企业
	 * @param enterpriseEntity
	 * @return
	 */
	Boolean  modifyBackEnterprise(EnterpriseEntity enterpriseEntity);
	
	
	
	List<Map<String,Object>> getEnterpriseInfoTwoByOneId(Long id);
	
	
	
	
	Boolean removeEnterpriseInfoOne(Long id);
	
	
	Boolean removeEnterpriseInfoTwo(List<Long> id);
	
	
	
	
	
	

}
