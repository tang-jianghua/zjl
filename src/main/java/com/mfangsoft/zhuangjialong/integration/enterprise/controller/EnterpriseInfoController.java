package com.mfangsoft.zhuangjialong.integration.enterprise.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.BaseEnterpriseTwoEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseInfoEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseOneEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseTwoEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.service.EnterpriseService;

@Controller
@RequestMapping("/server")
public class EnterpriseInfoController {

	@Autowired
	private EnterpriseService enterpriseService;

	
	/**
	 * 添加企业简介
	 * @param enterpriseInfoEntity
	 * @return
	 */
	@RequestMapping(value = "/addprofiles", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<String> addProfiles(@RequestBody EnterpriseInfoEntity enterpriseInfoEntity) {

		ResponseMessage<String> message = new ResponseMessage<>();

		enterpriseInfoEntity.setType(enterpriseInfoEntity.ENTERPRISE_PROFILES);

		try {

			enterpriseService.addEnterpriseInfo(enterpriseInfoEntity);
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);

		} catch (Exception e) {
			// TODO: handle exception

			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}

		return message;
	}
	
	
	/**
	 * 获取简介
	 * @param enterpriseInfoEntity
	 * @return
	 */
	@RequestMapping(value = "/getprofiles", method = RequestMethod.GET)
	@ResponseBody
	public ResponseMessage<EnterpriseInfoEntity> getProfiles() {

		ResponseMessage<EnterpriseInfoEntity> message = new ResponseMessage<>();
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			message.setResult(enterpriseService.getEnterpriseProfilesByUserId(EnterpriseInfoEntity.ENTERPRISE_PROFILES));
		return message;
	}
	
	
	/**
	 * 获取发展历程
	 * @param enterpriseInfoEntity
	 * @return
	 */
	@RequestMapping(value = "/getdevelopment", method = RequestMethod.GET)
	@ResponseBody
	public ResponseMessage<EnterpriseInfoEntity> getDevelopment() {

		ResponseMessage<EnterpriseInfoEntity> message = new ResponseMessage<>();
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			message.setResult(enterpriseService.getEnterpriseProfilesByUserId(EnterpriseInfoEntity.ENTERPRISE_DEVELOPMENT));
		return message;
	}
	/**
	 * 添加发展历程
	 * @param enterpriseInfoEntity
	 * @return
	 */
	@RequestMapping(value = "/adddevelopment", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<String> addDevelopment(@RequestBody EnterpriseInfoEntity enterpriseInfoEntity) {

		ResponseMessage<String> message = new ResponseMessage<>();

		enterpriseInfoEntity.setType(enterpriseInfoEntity.ENTERPRISE_DEVELOPMENT);

		try {

			enterpriseService.addEnterpriseInfo(enterpriseInfoEntity);
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);

		} catch (Exception e) {
			// TODO: handle exception

			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}

		return message;
	}

	/**
	 * 修改企业简介
	 * @param enterpriseInfoEntity
	 * @return
	 */
	@RequestMapping(value = "/modifyprofiles", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<String> modifyProfiles(@RequestBody EnterpriseInfoEntity enterpriseInfoEntity) {

		ResponseMessage<String> message = new ResponseMessage<>();

		enterpriseInfoEntity.setType(enterpriseInfoEntity.ENTERPRISE_PROFILES);

		try {

			enterpriseService.modifyEnterpriseInfo(enterpriseInfoEntity);
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);

		} catch (Exception e) {
			// TODO: handle exception

			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}

		return message;

	}
	/**
	 * 修改发展历程
	 * @param enterpriseInfoEntity
	 * @return
	 */
	@RequestMapping(value = "/modifydevelopment", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<String> modifyDevelopment(@RequestBody EnterpriseInfoEntity enterpriseInfoEntity) {

		ResponseMessage<String> message = new ResponseMessage<>();

		enterpriseInfoEntity.setType(enterpriseInfoEntity.ENTERPRISE_DEVELOPMENT);

		try {

			enterpriseService.modifyEnterpriseInfo(enterpriseInfoEntity);
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);

		} catch (Exception e) {
			// TODO: handle exception

			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}

		return message;

	}
	/**
	 * 获取单个企业信息，简介、发展历程
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getenterpriseinfobyid/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseMessage<EnterpriseInfoEntity> getEnterpriseInfo(@PathVariable Long id) {

		ResponseMessage<EnterpriseInfoEntity> message = new ResponseMessage<>();

		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(enterpriseService.getEnterpriseInfoById(id));
		return message;

	}
	/**
	 * 添加 2：工程案例；3：品牌荣誉；4：店面风采 分类
	 * @param enterpriseOneEntity
	 * @return
	 */
	@RequestMapping(value = "/addenterpriseinfoone", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<String> addEnterpriseInfoOne(@RequestBody EnterpriseOneEntity enterpriseOneEntity) {

		ResponseMessage<String> message = new ResponseMessage<>();

		try {

			enterpriseService.addEnterpriseInfoOne(enterpriseOneEntity);
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);

		} catch (Exception e) {
			// TODO: handle exception

			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}

		return message;

	}

	/**
	 * 添加二级  2：工程案例；3：品牌荣誉；4：店面风采
	 * @param enterpriseTwoEntity
	 * @return
	 */
	@RequestMapping(value = "/addenterpriseinfotwo", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<String> addEnterpriseInfoTwo(@RequestBody List<EnterpriseTwoEntity> enterpriseTwoEntity) {

		ResponseMessage<String> message = new ResponseMessage<>();

		try {

			enterpriseService.addEnterpriseInfoTwo(enterpriseTwoEntity);
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);

		} catch (Exception e) {
			// TODO: handle exception

			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}

		return message;

	}
	/**
	 * 修改分类（2：工程案例；3：品牌荣誉；4：店面风采）
	 * @param enterpriseOneEntity
	 * @return
	 */
	@RequestMapping(value = "/modifyenterpriseinfoone", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<String> modifyEnterpriseInfoOne(@RequestBody EnterpriseOneEntity enterpriseOneEntity) {

		ResponseMessage<String> message = new ResponseMessage<>();

		try {

			enterpriseService.modifyEnterpriseInfoOne(enterpriseOneEntity);
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);

		} catch (Exception e) {
			// TODO: handle exception

			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}

		return message;

	}

	/**
	 * /**
	 * 修改分类（2：工程案例；3：品牌荣誉；4：店面风采）
	 * 
	 *
	 * @param enterpriseTwoEntity
	 * @return
	 */
	@RequestMapping(value = "/modifyenterpriseinfoTwo", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<String> modifyEnterpriseInfoTwo(EnterpriseTwoEntity enterpriseTwoEntity) {

		ResponseMessage<String> message = new ResponseMessage<>();

		try {

			enterpriseService.modifyEnterpriseInfoTwo(enterpriseTwoEntity);
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);

		} catch (Exception e) {
			// TODO: handle exception

			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}

		return message;
	}
	
	
	/**
	 * 获取一级分类
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getenterpriseinfoone/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<EnterpriseOneEntity> getEnterpriseInfoOne(@PathVariable Long id) {

		ResponseMessage<EnterpriseOneEntity> message = new ResponseMessage<>();
		
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			message.setResult(enterpriseService.getEnterpriseInfoOne(id));


		return message;

	}

	/**
	 * 获取二级内容
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getenterpriseinfoTwo/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<BaseEnterpriseTwoEntity> getEnterpriseInfoTwo(@PathVariable Long id) {

		ResponseMessage<BaseEnterpriseTwoEntity> message = new ResponseMessage<>();

			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			message.setResult(enterpriseService.getEnterpriseInfoTwo(id));
		
		return message;
	}

	
	
	
	/**
	 * 通过type 获取集合
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/getEnterpriseInfoOne/{type}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseMessage<List<Map<String, Object>>> getEnterpriseInfoOne(@PathVariable Integer type ) {

		ResponseMessage<List<Map<String, Object>>> message = new ResponseMessage<>();
        Map<String,Object> map = new HashMap<>();
		map.put("enterprise_id", ((EnterpriseEntity)UserContext.getCurrentUserInfo()).getId());
		map.put("type", type);
		message.setResult(enterpriseService.getEnterpriseInfoList(map));
		
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		return message;
	}
	
	/**
	 * 通过OneId 获取集合
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/getEnterpriseInfoTow/{oneId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseMessage<List<Map<String, Object>>> getEnterpriseInfoTowByOneId(@PathVariable Long oneId ) {

		ResponseMessage<List<Map<String, Object>>> message = new ResponseMessage<>();
      
		message.setResult(enterpriseService.getEnterpriseInfoTwoByOneId(oneId));
		
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		return message;
	}
	
	/**
	 * 分类级联删除
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/removeEnterpriseInfoOne/{oneId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseMessage<String> removeEnterpriseInfoTowByOneId(@PathVariable Long oneId ) {

		ResponseMessage<String> message = new ResponseMessage<>();
		enterpriseService.removeEnterpriseInfoOne(oneId);
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		return message;
	}
	
	
	/**
	 * 批量删除二级
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/removeEnterpriseInfoTwo", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<String> removeEnterpriseInfoOne(@RequestBody List<Long> list) {

		ResponseMessage<String> message = new ResponseMessage<>();
		enterpriseService.removeEnterpriseInfoTwo(list);
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		return message;
	}
	
	

}
