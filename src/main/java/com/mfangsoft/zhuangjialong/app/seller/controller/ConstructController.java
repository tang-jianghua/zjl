package com.mfangsoft.zhuangjialong.app.seller.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.mfangsoft.zhuangjialong.app.applogin.model.Customer;
import com.mfangsoft.zhuangjialong.app.applogin.model.CustomerEntity;
import com.mfangsoft.zhuangjialong.app.constructAppointment.model.ConstructAppointmentModel;
import com.mfangsoft.zhuangjialong.app.seller.mapper.SellerEntityMapper;
import com.mfangsoft.zhuangjialong.app.seller.model.ConstructModel;
import com.mfangsoft.zhuangjialong.app.seller.model.ConstructServiceType;
import com.mfangsoft.zhuangjialong.app.seller.model.Guild;
import com.mfangsoft.zhuangjialong.app.seller.model.SellerBalanceApplyEntity;
import com.mfangsoft.zhuangjialong.app.seller.model.SellerConstructInfoEntity;
import com.mfangsoft.zhuangjialong.app.seller.model.SellerEntity;
import com.mfangsoft.zhuangjialong.app.seller.service.ConstructService;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.RedisUtils;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.region.model.RegionEntity;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年7月4日
* 
*/
@Controller(value="appConstruct")
@RequestMapping("/app")
public class ConstructController {
	
	
	@Autowired
	private ConstructService constructServiceImpl;
	
	/**
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/queryconstructlist", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Page<ConstructModel>> queryConstructList(@RequestBody Page<ConstructModel> param){
		ResponseMessage<Page<ConstructModel>> responseMessage=new ResponseMessage<>();
		responseMessage.setCode("0");
		responseMessage.setMessage("获取成功");
		responseMessage.setResult(constructServiceImpl.queryConstructs(param));
		return responseMessage;
	}
	
	
	@RequestMapping(value = "/queryconstructdetails", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<ConstructModel> queryConstructDetails(@RequestBody ConstructModel param){
		ResponseMessage<ConstructModel> responseMessage=new ResponseMessage<>();
		responseMessage.setCode("0");
		responseMessage.setMessage("获取成功");
		responseMessage.setResult(constructServiceImpl.queryConstructDetails(param));
		return responseMessage;
	}
	
	@RequestMapping(value = "/constructverify", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage constructverify(@RequestBody SellerConstructInfoEntity param){
		ResponseMessage responseMessage=new ResponseMessage<>();
		
		if(constructServiceImpl.constructverify(param)){
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			return responseMessage;
		}else{
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
			return responseMessage;
		}
	}
	
	@RequestMapping(value = "/queryservicetype", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<ConstructServiceType>> queryservicetype(){
		ResponseMessage<List<ConstructServiceType>> responseMessage=new ResponseMessage<>();
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			responseMessage.setResult(constructServiceImpl.queryservicetype());
			return responseMessage;
	}
	
	@RequestMapping(value = "/queryverifystate", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Integer> queryverifystate(@RequestBody ConstructModel param){
		ResponseMessage<Integer> responseMessage=new ResponseMessage<>();
		SellerConstructInfoEntity state = constructServiceImpl.selectStateBySellerId(param.getId());
		if(state != null){
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(state.getInfo());
			responseMessage.setResult(state.getCertification_state());
		}else{
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
		}
		return responseMessage;
	}
	
	@RequestMapping(value = "/modifyworkingstate", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Integer> modifyworkingstate(@RequestBody ConstructModel param){
		ResponseMessage<Integer> responseMessage=new ResponseMessage<>();
		if(constructServiceImpl.modifyworkingstate(param.getBreak_off_state(), param.getId())){
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			return responseMessage;
		}else{
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
			return responseMessage;
		}
	}
	
	/**
	 * 施工订单列表
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/queryconstructlistbyid", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Page<ConstructAppointmentModel>> queryworklistbyid(@RequestBody Page<ConstructAppointmentModel> param){
		ResponseMessage<Page<ConstructAppointmentModel>> responseMessage=new ResponseMessage<>();
		responseMessage.setCode("0");
		responseMessage.setMessage("获取成功");
		responseMessage.setResult(constructServiceImpl.selectAppointmentOfConstructerForPage(param));
		param.setParam(null);
		return responseMessage;
	}
	@RequestMapping(value = "/constructchangestate", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage workerchangestate(@RequestBody ConstructAppointmentModel param){
		ResponseMessage responseMessage=new ResponseMessage<>();
		if(constructServiceImpl.workerchangestate(param)){
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			return responseMessage;
		}
		responseMessage.setCode(SysConstant.FAILURE_CODE);
		responseMessage.setMessage(SysConstant.FAILURE_MSG);
		return responseMessage;
	}
	
	@RequestMapping(value = "/queryconstructdetailbyid", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<ConstructModel> queryworkerdetailbyid(@RequestBody ConstructModel param){
		ResponseMessage<ConstructModel> responseMessage=new ResponseMessage<ConstructModel>();
		ConstructModel m = constructServiceImpl.queryworkerdetailbyid(param);
		if(m!=null){
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			responseMessage.setResult(m);
			return responseMessage;
		}
		responseMessage.setCode(SysConstant.FAILURE_CODE);
		responseMessage.setMessage(SysConstant.FAILURE_MSG);
		return responseMessage;
	}
	
	@RequestMapping(value = "/boundzhifubao", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<ConstructModel> boundzhifubao(@RequestBody ConstructModel param){
		return constructServiceImpl.boundzhifubao(param);
	}
	
	@RequestMapping(value = "/drawandverifymoney", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<SellerBalanceApplyEntity> drawandverifymoney(@RequestBody SellerBalanceApplyEntity param){
		
		return constructServiceImpl.drawandverifymoney(param);
		
	}
	
	@RequestMapping(value = "/sellerinvitefriend", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Guild> sellerinvitefriend(@RequestBody Guild g) {
		ResponseMessage<Guild> responseMessage = new ResponseMessage<Guild>();
		
		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		responseMessage.setResult(constructServiceImpl.selectInvitCount(g));
		return responseMessage;
	}
	
	@RequestMapping(value = "/queryserviceprovencelist", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<RegionEntity>> queryserviceprovencelist(){
		ResponseMessage<List<RegionEntity>> responseMessage=new ResponseMessage<>();
		responseMessage.setCode("0");
		responseMessage.setMessage("获取成功");
		responseMessage.setResult(constructServiceImpl.queryserviceprovencelist());
		return responseMessage;
	}
	
	@RequestMapping(value = "/queryservicecitybyprovencecode", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<RegionEntity>> queryServiceCityByProvenceCode(@RequestBody RegionEntity param){
		ResponseMessage<List<RegionEntity>> responseMessage=new ResponseMessage<>();
		responseMessage.setCode("0");
		responseMessage.setMessage("获取成功");
		responseMessage.setResult(constructServiceImpl.queryserviceCityByprovence(param.getCode()));
		return responseMessage;
	}
	@RequestMapping(value = "/queryservicecountrybycitycode", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<RegionEntity>> queryserviceCountryByCity(@RequestBody RegionEntity param){
		ResponseMessage<List<RegionEntity>> responseMessage=new ResponseMessage<>();
		responseMessage.setCode("0");
		responseMessage.setMessage("获取成功");
		responseMessage.setResult(constructServiceImpl.queryserviceCountryByCity(param.getCode()));
		return responseMessage;
	}
	/**
	 * 施工员修改我的信息
	 * @param constructModel
	 * @return
	 */
	@RequestMapping(value = "/modifyconstructorinfo", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Integer> modifyConstructorInfo(@RequestBody ConstructModel constructModel){
		ResponseMessage<Integer> responseMessage=new ResponseMessage<Integer>();
 		if(constructServiceImpl.modifyConstructorInfo(constructModel)){
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			return responseMessage;
		}else{
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
			return responseMessage;
		}
	}
	
	
	/**
	 * 施工员查询施工暂停状态
	 * @param constructModel
	 * @return
	 */
	@RequestMapping(value = "/modifyconstructorbreakoffstate", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Integer> modifyConstructorbreakoffstate(@RequestBody ConstructModel constructModel){
		ResponseMessage<Integer> responseMessage=new ResponseMessage<Integer>();
		Integer off_state = constructServiceImpl.modifyConstructorbreakoffstate(constructModel);
		if(off_state != null){
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			responseMessage.setResult(off_state);
			return responseMessage;
		}else{
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
			return responseMessage;
		}
	}
	
}
