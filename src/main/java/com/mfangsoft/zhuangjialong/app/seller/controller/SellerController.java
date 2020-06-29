package com.mfangsoft.zhuangjialong.app.seller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.app.seller.mapper.SellerEntityMapper;
import com.mfangsoft.zhuangjialong.app.seller.model.ConstructModel;
import com.mfangsoft.zhuangjialong.app.seller.model.SellerEntity;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.RedisUtils;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;

@Controller(value="appSellerController")
@RequestMapping("/app")
public class SellerController {
	@Autowired
	private SellerEntityMapper sellerEntityMapper;
	
	/**
	 * 修改卖家电话
	 * @param seller
	 * {flag}:验证或修改手机号标识，0：验证；1：修改手机号
	 * @return
	 */
	@RequestMapping(value = "/modifysellerphonenumber/{flag}", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Integer> modifyphonenumber(@RequestBody ConstructModel seller,@PathVariable("flag") Integer flag) {
		ResponseMessage<Integer> responseMessage = new ResponseMessage<Integer>();
		if (seller.getVcode().equals(RedisUtils.getValue(seller.getPhone()))) {
			//验证
			if (flag == 0){
				responseMessage.setCode(SysConstant.SUCCESS_CODE);
				responseMessage.setMessage(SysConstant.SUCCESS_MSG);
				return responseMessage;
			}
			
			if (sellerEntityMapper.selectByAccount(seller.getPhone()) != null) {
				responseMessage.setCode(SysConstant.FAILURE_CODE);
				responseMessage.setMessage("此手机号已被注册");
				return responseMessage;
			}
			
			SellerEntity sellerEntity = new SellerEntity();
			sellerEntity.setId(seller.getId());
			sellerEntity.setPhone(seller.getPhone());
			sellerEntity.setAccount(seller.getPhone());
			if (sellerEntityMapper.updateByPrimaryKeySelective(sellerEntity) > 0) {
				responseMessage.setCode(SysConstant.SUCCESS_CODE);
				responseMessage.setMessage(SysConstant.SUCCESS_MSG);
				return responseMessage;
			}
		}
		responseMessage.setCode(SysConstant.FAILURE_CODE);
		responseMessage.setMessage("验证码错误");
		return responseMessage;
	}
	/**
	 * 卖家上传头像
	 * @param seller
	 * @return
	 */
	@RequestMapping(value = "/uploadsellerheadimg", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Integer> uploadSellerHeadImg(@RequestBody ConstructModel seller) {
		ResponseMessage<Integer> responseMessage = new ResponseMessage<Integer>();
		SellerEntity sellerEntity = new SellerEntity();
		sellerEntity.setHead_img(seller.getHead_img());
		sellerEntity.setId(seller.getId());
		if(sellerEntityMapper.updateByPrimaryKeySelective(sellerEntity) > 0){
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			return responseMessage;
		}else{
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
			return responseMessage;
		}
	}
}
