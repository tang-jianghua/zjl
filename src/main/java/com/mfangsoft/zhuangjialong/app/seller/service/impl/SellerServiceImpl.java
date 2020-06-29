package com.mfangsoft.zhuangjialong.app.seller.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.seller.mapper.SellerEntityMapper;
import com.mfangsoft.zhuangjialong.app.seller.model.Guild;
import com.mfangsoft.zhuangjialong.app.seller.model.SellerEntity;
import com.mfangsoft.zhuangjialong.app.seller.model.SellerInfoEntity;
import com.mfangsoft.zhuangjialong.app.seller.service.SellerService;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.EncrUtil;
import com.mfangsoft.zhuangjialong.common.utils.MD5Util;
import com.mfangsoft.zhuangjialong.common.utils.RedisUtils;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.enums.SellerType;
import com.mfangsoft.zhuangjialong.integration.enums.ShopGuiderCertificationState;
import com.mfangsoft.zhuangjialong.integration.shop.mapper.ShopGuiderMapper;
import com.mfangsoft.zhuangjialong.integration.shop.model.BaseShopGuiderEntity;
import com.mfangsoft.zhuangjialong.integration.shop.model.ShopGuiderModel;

@Service(value="appSeller")
public class SellerServiceImpl implements SellerService{
	@Autowired
	SellerEntityMapper sellerEntityMapper;
	@Autowired
	ShopGuiderMapper shopGuiderMapper;
	@Override
	public ResponseMessage<String> register(SellerEntity seller) {
		ResponseMessage<String> message = new ResponseMessage<>();
		
		if(StringUtils.isEmpty(seller.getAccount())){
			message.setCode("1");
			message.setMessage("帐号不能为空");
			return message;
		}
		if(StringUtils.isEmpty(seller.getPassword())){
			message.setCode("1");
			message.setMessage("密码不能为空");
			return message;
		}
		
		String vcode = RedisUtils.getValue(seller.getAccount());
		if(StringUtils.isEmpty(vcode)){
			message.setCode("1");
			message.setMessage("未获取验证码或验证码已过期");
			return message;
		}
		if (!seller.getvCode().equals(vcode)) {
			message.setCode("1");
			message.setMessage("验证码输入错误");
			return message;
		}
		if (sellerEntityMapper.selectByAccount(seller.getAccount()) != null) {
			message.setCode("1");
			message.setMessage("账号已被注册");
			RedisUtils.delValue(seller.getAccount());
			return message;
		}
		try{
			
			String invite_code_test = seller.getInvite_code();
			String invite_id_test =  EncrUtil.decrypt(invite_code_test.substring(1, invite_code_test.length()));
			Long expand_id = sellerEntityMapper.selectByPrimaryKey(Long.parseLong(invite_id_test)).getId();
			if(expand_id == null){
				message.setCode("1");
				message.setMessage("邀请的推广员不存在");
				return message;
			}
		}catch(Exception e){
			message.setCode("1");
			message.setMessage("邀请码不对");
			return message;
		}
		
		Boolean insertSuccessFlag = true;
		SellerEntity sellerEntity = new SellerEntity();
		sellerEntity.setAccount(seller.getAccount());
		sellerEntity.setPassword(MD5Util.MD5(seller.getPassword()));
		sellerEntity.setSeller_type(seller.getSeller_type());
		sellerEntity.setPhone(seller.getAccount());
		sellerEntity.setCreate_time(new Date());
		sellerEntity.setState(1);
		sellerEntity.setName(seller.getName());
		
		String invite_code = seller.getInvite_code();
		sellerEntity.setInvite_code(invite_code);
		String invite_id = EncrUtil.decrypt(invite_code.substring(1, invite_code.length()));
		//取到邀请人ID
		Long expand_id = sellerEntityMapper.selectByPrimaryKey(Long.parseLong(invite_id)).getId();
		Long partner_id = sellerEntityMapper.selectPartnerBySellerId(expand_id);//查询诚实合伙人id
		sellerEntity.setPartner_id(partner_id);
		if (sellerEntityMapper.insertSelective(sellerEntity) == 0)
			insertSuccessFlag = false;
		SellerInfoEntity sellerInfo = new SellerInfoEntity();
		sellerInfo.setSeller_id(sellerEntity.getId());
		sellerInfo.setExpand_id(expand_id);
		
		sellerInfo.setCommission_rate(SysConstant.Seller_rate);
		if (sellerEntityMapper.insertSellerInfo(sellerInfo)==0) 
			insertSuccessFlag = false;
		if(seller.getSeller_type()==SellerType.ShopGuider.getIndex()){
			BaseShopGuiderEntity baseShopGuiderEntity = new BaseShopGuiderEntity();
			baseShopGuiderEntity.setCertification_state(ShopGuiderCertificationState.NoCommit.getIndex());
			baseShopGuiderEntity.setSeller_info_id(sellerInfo.getId());
			baseShopGuiderEntity.setIsgenerate(0);
			if	(shopGuiderMapper.insertSelective(baseShopGuiderEntity)==0)
				insertSuccessFlag = false;
		}
		if (insertSuccessFlag == true){
			message.setCode("0");
			message.setMessage("注册成功");
			RedisUtils.delValue(sellerEntity.getAccount());
			return message;
		} else {
			message.setCode("1");
			message.setMessage("注册失败");
			return message;
		}
	}
	public static void main(String[] args) {
		System.out.println(MD5Util.MD5("123456"));
	}

}
