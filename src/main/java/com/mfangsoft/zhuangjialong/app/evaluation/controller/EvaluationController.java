package com.mfangsoft.zhuangjialong.app.evaluation.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.app.evaluation.model.EvaluationEntity;
import com.mfangsoft.zhuangjialong.app.evaluation.model.EvaluationImageEntity;
import com.mfangsoft.zhuangjialong.app.evaluation.model.EvaluationModel;
import com.mfangsoft.zhuangjialong.app.evaluation.model.EvaluationNumModel;
import com.mfangsoft.zhuangjialong.app.evaluation.service.EvaluationService;
import com.mfangsoft.zhuangjialong.app.order.model.OrderProduct;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.ImgUtil;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;

/**
 * @description：
 * 
 * @author：Jianghua.Tang @date：2016年6月24日
 * 
 */

@Controller
@RequestMapping("/app")
public class EvaluationController {

	@Autowired
	private EvaluationService evaluationServiceImpl;

	@RequestMapping(value = "/addevaluation", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<EvaluationEntity> addEvaluation(@RequestBody EvaluationEntity param,
			HttpServletRequest request) {
		ResponseMessage<EvaluationEntity> message = new ResponseMessage<>();
		evaluationServiceImpl.addEvaluation(param);
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		return message;
	}

	@RequestMapping(value = "/getevaluationnum", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<EvaluationNumModel> getEvaluationNum(@RequestBody EvaluationEntity param) {
		ResponseMessage<EvaluationNumModel> message = new ResponseMessage<>();
		message.setCode("0");
		message.setMessage("success");
		message.setResult(evaluationServiceImpl.selectEvaluationNumByProductId(param));
		return message;
	}

	@RequestMapping(value = "/getevaluation", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Page<EvaluationModel>> getEvaluation(@RequestBody Page<EvaluationModel> param) {
		ResponseMessage<Page<EvaluationModel>> message = new ResponseMessage<>();
		message.setCode("0");
		message.setMessage("success");
		message.setResult(evaluationServiceImpl.getSqlEvaluationByProductIdForPage(param));
		return message;
	}
/**
 * 获取一件订单产品的信息
 * @param param
 * @return
 */
	@RequestMapping(value = "/getonproductforevaluation", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<OrderProduct> getonproductforevaluation(@RequestBody EvaluationEntity param) {
		ResponseMessage<OrderProduct> message = new ResponseMessage<>();
		message.setCode("0");
		message.setMessage("success");
		message.setResult(evaluationServiceImpl.getonproductforevaluation(param));
		return message;
	}
}
