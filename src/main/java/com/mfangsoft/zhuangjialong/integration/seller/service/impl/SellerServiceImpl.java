package com.mfangsoft.zhuangjialong.integration.seller.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.applogin.mapper.CustomerEntityMapper;
import com.mfangsoft.zhuangjialong.app.constructAppointment.mapper.ConstructAppointmentMapper;
import com.mfangsoft.zhuangjialong.app.constructAppointment.mapper.ConstructStateRelationMapper;
import com.mfangsoft.zhuangjialong.app.constructAppointment.model.ConstructAppointmentModel;
import com.mfangsoft.zhuangjialong.app.constructAppointment.model.ConstructStateRelation;
import com.mfangsoft.zhuangjialong.app.order.mapper.OrderEntityMapper;
import com.mfangsoft.zhuangjialong.app.seller.mapper.SellerConstructInfoEntityMapper;
import com.mfangsoft.zhuangjialong.app.seller.mapper.SellerEntityMapper;
import com.mfangsoft.zhuangjialong.app.seller.model.ConstructModel;
import com.mfangsoft.zhuangjialong.app.seller.model.ConstructServiceType;
import com.mfangsoft.zhuangjialong.app.seller.model.Guild;
import com.mfangsoft.zhuangjialong.app.seller.model.SellerConstructInfoEntity;
import com.mfangsoft.zhuangjialong.app.seller.model.SellerEntity;
import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.MD5Util;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseEntity;
import com.mfangsoft.zhuangjialong.integration.enums.UserType;
import com.mfangsoft.zhuangjialong.integration.order.service.impl.OrderServiceImpl;
import com.mfangsoft.zhuangjialong.integration.partner.mapper.PartnerEntityMapper;
import com.mfangsoft.zhuangjialong.integration.partner.model.PartnerEntity;
import com.mfangsoft.zhuangjialong.integration.seller.model.Order;
import com.mfangsoft.zhuangjialong.integration.seller.service.SellerService;
import com.mfangsoft.zhuangjialong.integration.user.model.UserEntity;

@Service(value = "sellerServiceImpl")
public class SellerServiceImpl implements SellerService {

	@Autowired
	private SellerEntityMapper sellerEntityMapper;
	@Autowired
	ConstructAppointmentMapper constructAppointmentMapper;
	@Autowired
	OrderEntityMapper orderEntityMapper;
	@Autowired
	CustomerEntityMapper customerEntityMapper;
	@Autowired
	SellerConstructInfoEntityMapper sellerConstructInfoEntityMapper;
	@Autowired
	PartnerEntityMapper partnerEntityMapper;
	@Autowired
	ConstructStateRelationMapper constructStateRelationMapper;
	@Autowired
	OrderServiceImpl orderServiceImpl;

	@Override
	public ResponseMessage<String> addSeller(SellerEntity sellerEntity) {

		ResponseMessage<String> message = new ResponseMessage<String>();

		Guild guild = sellerEntityMapper.selectByAccount(sellerEntity.getAccount());
		if (guild != null) {
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage("帐号已被注册");
			return message;
		}

		sellerEntity.setPartner_id(partnerEntityMapper.selectBySysUserId(sellerEntity.getPartner_id()));

		sellerEntity.setCreate_time(new Date());
		sellerEntity.setState(1);
		if (sellerEntity.getPassword() != null) {
			sellerEntity.setPassword(MD5Util.MD5(sellerEntity.getPassword()));
		}

		if (sellerEntityMapper.insertSelective(sellerEntity) > 0) {
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			return message;
		}
		message.setCode(SysConstant.FAILURE_CODE);
		return message;
	}

	@Override
	public Boolean modifySeller(SellerEntity sellerEntity) {
		if (sellerEntity.getPassword() != null) {
			sellerEntity.setPassword(MD5Util.MD5(sellerEntity.getPassword()));
		}

		if (sellerEntityMapper.updateByPrimaryKeySelective(sellerEntity) > 0) {

			return true;
		}

		return false;
	}

	public ResponseMessage<Integer> modifySellerState(SellerEntity sellerEntity) {

		ResponseMessage<Integer> message = new ResponseMessage<Integer>();
		if (sellerEntityMapper.updateSellerState(sellerEntity.getState(), sellerEntity.getId()) > 0) {

			SellerEntity entity = sellerEntityMapper.selectByPrimaryKey(sellerEntity.getId());
			if (entity != null) {
				message.setCode(SysConstant.SUCCESS_CODE);
				message.setMessage(SysConstant.SUCCESS_MSG);
				message.setResult(entity.getState());
				return message;
			}
		}
		message.setCode(SysConstant.FAILURE_CODE);
		message.setMessage(SysConstant.FAILURE_MSG);
		return message;
	}

	public Map<String, Object> getOneSeller(SellerEntity sellerEntity) {

		return sellerEntityMapper.selectByPrimaryKeyForBack(sellerEntity.getId());
	}

	public Page<Map<String, Object>> getTuiguangSellerForPage(Page<Map<String, Object>> page) {

		Map<String, Object> map;

		if (page.getParam() != null) {
			map = (Map<String, Object>) page.getParam();
		} else {
			map = new HashMap<>();
			page.setParam(map);
		}

		UserEntity u = UserContext.getCurrentUser();
		if (u.getUser_type() == UserType.PARTNER.getIndex()) {

			PartnerEntity p = (PartnerEntity) UserContext.getCurrentUserInfo();
			map.put("partner_id", p.getId() + "");

		} else if (u.getUser_type() == UserType.ENTERPRISE.getIndex()) {
			EnterpriseEntity enterpriseEntity = (EnterpriseEntity) UserContext.getCurrentUserInfo();

			map.put("enterprise_id", enterpriseEntity.getId());

		}
		page.setData(sellerEntityMapper.getTuiguangSellerForPage(page));
		return page;
	}

	@Override
	public Page<Map<String, Object>> getSellerForPage(Page<Map<String, Object>> page) {

		Map<String, Object> map;

		if (page.getParam() != null) {
			map = (Map<String, Object>) page.getParam();
		} else {
			map = new HashMap<>();
			page.setParam(map);
		}

		UserEntity u = UserContext.getCurrentUser();
		if (u.getUser_type() == UserType.PARTNER.getIndex()) {

			PartnerEntity p = (PartnerEntity) UserContext.getCurrentUserInfo();
			map.put("partner_id", p.getId() + "");

		} else if (u.getUser_type() == UserType.ENTERPRISE.getIndex()) {
			EnterpriseEntity enterpriseEntity = (EnterpriseEntity) UserContext.getCurrentUserInfo();

			map.put("enterprise_id", enterpriseEntity.getId());

		}
		page.setData(sellerEntityMapper.getSeller23ForPage(page));

		return page;
	}

	@Override
	public Page<Map<String, Object>> selectConstructAppointmentBackForPage(Page<Map<String, Object>> param) {
		param.setData(constructAppointmentMapper.selectConstructAppointmentBackForPage(param));
		return param;
	}

	public Page<Order> selectOrderListBackForPage(Page<Order> page) {

		Map<String, Object> map;

		if (page.getParam() != null) {
			map = (Map<String, Object>) page.getParam();
		} else {
			map = new HashMap<>();
			page.setParam(map);
		}

		UserEntity u = UserContext.getCurrentUser();
		 if (u.getUser_type() == UserType.ENTERPRISE.getIndex()) {
			EnterpriseEntity enterpriseEntity = (EnterpriseEntity) UserContext.getCurrentUserInfo();

			map.put("enterprise_id", enterpriseEntity.getId());

		}else if (u.getUser_type() == UserType.BRAND.getIndex()) {
			BrandEntity brandEntity = (BrandEntity) UserContext.getCurrentUserInfo();

			map.put("brand_id", brandEntity.getId());

		}else if (u.getUser_type() == UserType.PARTNER.getIndex()) {
			
			PartnerEntity p = (PartnerEntity) UserContext.getCurrentUserInfo();
			map.put("partner_id", p.getId() + "");
			
		}
		
		List<Order> list = orderEntityMapper.getOrderListBackForPage(page);
		
		for(int i=0;i<list.size();i++){
			Order order = list.get(i);
			String s = orderServiceImpl.getOrderPrefer(order.getId());
			order.setCash_info(s);
			
		}
		
		page.setData(list);
		return page;
	}

	public Page<Map<String, Object>> selectOrderListBackForExcelPage(Page<Map<String, Object>> page) {

		Map<String, Object> map;

		if (page.getParam() != null) {
			map = (Map<String, Object>) page.getParam();
		} else {
			map = new HashMap<>();
			page.setParam(map);
		}

		UserEntity u = UserContext.getCurrentUser();
		if (u.getUser_type() == UserType.ENTERPRISE.getIndex()) {
			EnterpriseEntity enterpriseEntity = (EnterpriseEntity) UserContext.getCurrentUserInfo();

			map.put("enterprise_id", enterpriseEntity.getId());

		} else if (u.getUser_type() == UserType.BRAND.getIndex()) {
			BrandEntity brandEntity = (BrandEntity) UserContext.getCurrentUserInfo();

			map.put("brand_id", brandEntity.getId());

		} else if (u.getUser_type() == UserType.PARTNER.getIndex()) {

			PartnerEntity p = (PartnerEntity) UserContext.getCurrentUserInfo();
			map.put("partner_id", p.getId() + "");

		}

		List<Map<String, Object>> list = orderEntityMapper.getOrderListBackForExcelPage(page);
		
		for(int i=0;i<list.size();i++){
			String s = orderServiceImpl.getOrderPrefer(new Long(list.get(i).get("id").toString()));
			list.get(i).put("cash_info",s);
			
		}
		page.setData(list);
		return page;
	}

	public ConstructAppointmentModel selectConstructerAppointmentAndTraceingBackByAppopintId(
			ConstructAppointmentModel param) {

		return constructAppointmentMapper.selectConstructerAppointmentAndTraceingBackByAppopintId(param.getId());
	}

	public ConstructModel queryconstructdetailbackbyid(ConstructModel param) {

		ConstructModel constructModel = sellerEntityMapper.queryworkerdetailbackbyid(param.getId());

		UserEntity u = UserContext.getCurrentUser();
		if (u.getUser_type() == UserType.PARTNER.getIndex()) {

			PartnerEntity p = (PartnerEntity) UserContext.getCurrentUserInfo();
			constructModel.setPhone_num(p.getPhone_num());

		}
		return constructModel;
	}

	@Override
	public Page<Map<String, Object>> queryCustomerBackForPage(Page<Map<String, Object>> page) {

		page.setData(customerEntityMapper.queryCustomerBackForPage(page));
		return page;
	}

	public Boolean saveconstructdetailback(ConstructModel param) {

		Long id = sellerEntityMapper.queryConstructInfoId(param.getId());
		if (id == null)
			return false;

		SellerConstructInfoEntity entity = new SellerConstructInfoEntity();
		entity.setId(id);
		entity.setTeam_name(param.getTeam_name());
		entity.setConstruct_about(param.getConstruct_about());
		entity.setIdcard_back(param.getIdcard_back());
		entity.setIdcard_front(param.getIdcard_front());
		entity.setIdcard_hold(param.getIdcard_hold());
		entity.setQualification_one(param.getQualification_one());
		entity.setQualification_three(param.getQualification_three());
		entity.setQualification_two(param.getQualification_two());
		entity.setService_code(param.getService_code());
		entity.setUnit_price(param.getUnit_price());
		entity.setUnit_name(param.getUnit_name());
		entity.setService_region(param.getService_region());

		sellerConstructInfoEntityMapper.updateByPrimaryKeySelective(entity);

		if (StringUtils.isNotEmpty(param.getAli_account())) {
			sellerEntityMapper.boundzhifubao(param.getId(), param.getAli_account());
		}

		SellerEntity sellerEntity = new SellerEntity();
		sellerEntity.setId(param.getId());
		sellerEntity.setName(param.getName());
		sellerEntity.setPhone(param.getPhone());
		sellerEntity.setHead_img(param.getHead_img());

		sellerEntityMapper.updateByPrimaryKeySelective(sellerEntity);

		return true;
	}

	public List<ConstructServiceType> queryservicetypeback() {
		return sellerEntityMapper.queryservicetype();
	}

	public boolean modifyVerifystate(ConstructModel param) {
		Long partner_id = sellerEntityMapper.selectPartIdBysellerId(param.getId());

		if (partner_id == null) {
			return false;
		}
		UserEntity u = UserContext.getCurrentUser();
		if (u.getUser_type() == UserType.PARTNER.getIndex()) {
			PartnerEntity p = (PartnerEntity) UserContext.getCurrentUserInfo();
			if (!p.getId().equals(partner_id.longValue())) {
				return false;
			}
		} else {
			return false;
		}

		Integer result = sellerConstructInfoEntityMapper.modifyVerifystate(param.getCertification_state(),
				param.getId(), param.getInfo());
		return ((result != null && result > 0) ? true : false);
	}

	public List<ConstructStateRelation> getconstructstate() {
		return constructStateRelationMapper.selectAllState();
	}

	public Page<Map<String, String>> getEvaluationsForPage(Page<Map<String, String>> param) {

		param.setData(constructAppointmentMapper.selectEvaluationsForPage(param));

		return param;
	}

	
}
