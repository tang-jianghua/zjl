package com.mfangsoft.zhuangjialong.app.seller.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.applogin.mapper.CustomerEntityMapper;
import com.mfangsoft.zhuangjialong.app.constructAppointment.mapper.ConstructAppointmentMapper;
import com.mfangsoft.zhuangjialong.app.constructAppointment.mapper.ConstructStateRelationMapper;
import com.mfangsoft.zhuangjialong.app.constructAppointment.model.ConstructAppointmentModel;
import com.mfangsoft.zhuangjialong.app.constructAppointment.model.ConstructStateRelation;
import com.mfangsoft.zhuangjialong.app.personalCenter.mapper.CustomerPointEntityMapper;
import com.mfangsoft.zhuangjialong.app.personalCenter.mapper.MessageEntityMapper;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.CustomerPointEntity;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.CustomerPointType;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.MessageEntity;
import com.mfangsoft.zhuangjialong.app.seller.mapper.SellerBalanceApplyEntityMapper;
import com.mfangsoft.zhuangjialong.app.seller.mapper.SellerConstructInfoEntityMapper;
import com.mfangsoft.zhuangjialong.app.seller.mapper.SellerEntityMapper;
import com.mfangsoft.zhuangjialong.app.seller.model.ConstructModel;
import com.mfangsoft.zhuangjialong.app.seller.model.ConstructServiceType;
import com.mfangsoft.zhuangjialong.app.seller.model.Guild;
import com.mfangsoft.zhuangjialong.app.seller.model.SellerBalanceApplyEntity;
import com.mfangsoft.zhuangjialong.app.seller.model.SellerConstructInfoEntity;
import com.mfangsoft.zhuangjialong.app.seller.model.SellerEntity;
import com.mfangsoft.zhuangjialong.app.seller.service.ConstructService;
import com.mfangsoft.zhuangjialong.common.message.service.impl.SendMessageServiceImpl;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.EncrUtil;
import com.mfangsoft.zhuangjialong.common.utils.MessageConstant;
import com.mfangsoft.zhuangjialong.common.utils.NumberUtil;
import com.mfangsoft.zhuangjialong.common.utils.RedisUtils;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.region.mapper.RegionEntityMapper;
import com.mfangsoft.zhuangjialong.integration.region.model.RegionEntity;
import com.mfangsoft.zhuangjialong.integration.region.service.RegionService;

/**
 * @description：
 * 
 * @author：Jianghua.Tang @date：2016年7月4日
 * 
 */
@Service
public class ConstructServiceImpl implements ConstructService {

	@Autowired
	private SellerEntityMapper sellerEntityMapper;
	@Autowired
	private RegionService regionServiceImpl;
	@Autowired
	ConstructAppointmentMapper constructAppointmentMapper;
	@Autowired
	ConstructStateRelationMapper constructStateRelationMapper;
	@Autowired
	SellerConstructInfoEntityMapper sellerConstructInfoEntityMapper;
	@Autowired
	SellerBalanceApplyEntityMapper sellerBalanceApplyEntityMapper;
	@Autowired
	CustomerEntityMapper customerEntityMapper;
	@Autowired
	RegionEntityMapper regionEntityMapper;
	@Autowired
	CustomerPointEntityMapper customerPointEntityMapper;
	@Autowired
	SendMessageServiceImpl sendMessageServiceImpl;
	@Autowired
	MessageEntityMapper messageEntityMapper;
	
	@Override
	public Page<ConstructModel> queryConstructs(Page<ConstructModel> param) {
		Map<String, String> map =(Map<String, String>)param.getParam();
		String string = map.get("search_content");
		StringBuffer sql = new StringBuffer();
		if(string != ""){
			sql.append("AND (");
			String[] split = string.split(" ");
			for (int i = 0; i < split.length; i++) {
				if(!"".equals(split[i])){
				if(i<split.length-1){
				sql.append("ci.team_name LIKE '%"+split[i]+"%' OR ");
				}else{
					sql.append("ci.team_name LIKE '%"+split[i]+"%'  )");
				}
				}
			}
			map.put("sql", sql.toString());
		}
		param.setParam(map);
		List<ConstructModel> constructs = sellerEntityMapper.selectConstructByNameAndServiceTypeForPage(param);
		for (int i = 0; i < constructs.size(); i++) {
			String sr = constructs.get(i).getService_region();
			if(sr != null){
				String[] rc = sr.split(",");
				StringBuffer sb = new StringBuffer();
				sb.append(regionEntityMapper.selectreginName(rc[0])+regionEntityMapper.selectreginName(rc[1])+":");
				for (int j = 2; j < rc.length; j++) {
					if(j<rc.length-1){
						sb.append(regionEntityMapper.selectreginName(rc[j]) + "、 ");
					}else{
						sb.append(regionEntityMapper.selectreginName(rc[j]) );
					}
				}
				constructs.get(i).setService_region(sb.toString());
				}
		}
		param.setParam(null);
		param.setData(constructs);
		return param;
	}

	@Override
	public ConstructModel queryConstructDetails(ConstructModel param) {
		ConstructModel constructModel = sellerEntityMapper.selectConstructDetailsById(param.getId());
		String sr = constructModel.getService_region();
		if(sr != null){
		String[] rc = sr.split(",");
		StringBuffer sb = new StringBuffer();
		sb.append(regionEntityMapper.selectreginName(rc[0])+regionEntityMapper.selectreginName(rc[1])+":");
		for (int j = 2; j < rc.length; j++) {
			if(j<rc.length-1){
				sb.append(regionEntityMapper.selectreginName(rc[j]) + "、");
			}else{
				sb.append(regionEntityMapper.selectreginName(rc[j]) );
			}
		}
		constructModel.setService_region(sb.toString());
		}
		return constructModel;
	}

	/**
	 * 查询施工列表
	 */
	@Override
	public Page<ConstructAppointmentModel> selectAppointmentOfConstructerForPage(
			Page<ConstructAppointmentModel> param) {
		try {
			List<ConstructAppointmentModel> list = constructAppointmentMapper.selectAppointmentOfConstructerForPage(param);
			if(list != null){
				for(ConstructAppointmentModel c : list){
					if(c.getConstruct_data() != null){
						DecimalFormat df = new DecimalFormat( "0.00");
						String p = df.format(c.getConstruct_data());
//						double p = NumberUtil.round(c.getConstruct_data() < 0 ? 0 : c.getConstruct_data(), 2, BigDecimal.ROUND_HALF_DOWN);
						c.setConstruct_data_str(p);
						c.setConstruct_data(null);
					}
				}
				param.setData(list);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return param;
	}
public static void main(String[] args) {
	System.out.println(NumberUtil.round(200.20255D < 0 ? 0 : 200.20255D, 2, BigDecimal.ROUND_HALF_DOWN));
}
	/**
	 * 改变施工状态
	 */
	@Override
	public boolean workerchangestate(ConstructAppointmentModel param) {

		
		if (param.getConstruct_data() != null) {
			Double money = NumberUtil.round(param.getConstruct_data() < 0 ? 0 : param.getConstruct_data(), 2, BigDecimal.ROUND_HALF_DOWN);
			constructAppointmentMapper.updateconstructdata(param.getId(), money);
		}
		// ConstructStateRelation c =
		// constructStateRelationMapper.selectByAppointmentId(param.getId());
		// // 状态只能想大数方向改变
		// if (c != null && Integer.parseInt(c.getState_code()) >
		// Integer.parseInt(param.getState_code())) {
		// return false;
		// } else {
		
		if(param.getState_code().equals("1002")){
			ConstructAppointmentModel constructAppointment = constructAppointmentMapper.selectInfoById(param.getId());
			
			List<CustomerPointType> list = customerPointEntityMapper.selectPointType();
			CustomerPointType result = null;
			for(CustomerPointType c : list){
				if(c.getType().equals(SysConstant.Construct_Point_Id)){
					result = c;
				}
			}
			
			CustomerPointEntity customerPointEntity = new CustomerPointEntity();
			customerPointEntity.setCustomer_id(constructAppointment.getCustomer_id());
			customerPointEntity.setName(constructAppointment.getTeam_name());
			customerPointEntity.setTime(new Date());
			customerPointEntity.setType(SysConstant.Construct_Point_Id);
			customerPointEntity.setPoint(result.getPoint());
			customerPointEntityMapper.insertSelective(customerPointEntity);
			
			MessageEntity messageEntity = new MessageEntity();
			messageEntity.setCustomer_id(constructAppointment.getCustomer_id());
			messageEntity.setType_id(MessageConstant.PointShigong);
			messageEntity.setParams(result.getPoint().toString());
			messageEntity.setTime(new Date());
			messageEntityMapper.insertSelective(messageEntity);
			
			sendMessageServiceImpl.sendMessage(constructAppointment.getCustomer_id(), result.getPoint());
		}
		
		ConstructStateRelation r = new ConstructStateRelation();
		r.setConstruct_appointment_id(param.getId());
		r.setCreate_time(new Date());
		r.setState_code(param.getState_code());
		if (constructStateRelationMapper.insertSelective(r) > 0) {
			return true;
		} else {
			return false;
		}
		// }

	}

	/**
	 * 查询施工主页信息
	 */
	@Override
	public ConstructModel queryworkerdetailbyid(ConstructModel param) {
		double draw_money = 0D;
		Double sum = sellerEntityMapper.guilderCanDrawedMoneySum(param.getId());
		Double drawed = sellerEntityMapper.guilderDrawedMoney(param.getId());
		if (sum != null) {
			double d = drawed == null ? 0 : drawed.doubleValue();
			draw_money = sum.doubleValue() - d;
		}
		ConstructModel constructModel = sellerEntityMapper.queryworkerdetailbyid(param.getId());
		constructModel.setDraw_money(draw_money);
		return constructModel;
	}

	@Override
	public boolean constructverify(SellerConstructInfoEntity record) {
		
		record.setCertification_state(2);
		sellerConstructInfoEntityMapper.insertSelective(record);
		if (record.getId() > 0) {
			if (sellerEntityMapper.updateConstructInfoIdBySellerId(record.getSeller_id(), record.getId()) > 0) {
				return true;
			}
		}
		return false;
	}

	public List<ConstructServiceType> queryservicetype() {
		
		return sellerEntityMapper.queryservicetype();
		
	}

	public SellerConstructInfoEntity selectStateBySellerId(Long seller_id) {
		return sellerConstructInfoEntityMapper.selectStateBySellerId(seller_id);
	}

	public boolean modifyworkingstate(Integer break_off_state, Long seller_id) {
		Integer result = sellerConstructInfoEntityMapper.modifyworkingstate(break_off_state, seller_id);
		return ((result != null && result > 0) ? true : false);
	}

	public ResponseMessage<ConstructModel> boundzhifubao(ConstructModel param) {
		ResponseMessage<ConstructModel> responseMessage = new ResponseMessage<ConstructModel>();
		String vcode = RedisUtils.getValue(param.getAccount());

		if (StringUtils.isEmpty(param.getVcode())) {
			responseMessage.setCode("1");
			responseMessage.setMessage("验证码不能为空");
			return responseMessage;
		}
		if (StringUtils.isEmpty(vcode)) {
			responseMessage.setCode("1");
			responseMessage.setMessage("未获取验证码或验证码已过期");
			return responseMessage;
		}
		if (!param.getVcode().equals(vcode)) {
			responseMessage.setCode("1");
			responseMessage.setMessage("验证码错误");
			return responseMessage;
		}
		if (sellerEntityMapper.boundzhifubao(param.getId(), param.getAli_account()) > 0) {
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			return responseMessage;
		}
		responseMessage.setCode(SysConstant.FAILURE_CODE);
		responseMessage.setMessage(SysConstant.FAILURE_MSG);
		return responseMessage;
	}

	public ResponseMessage<SellerBalanceApplyEntity> drawandverifymoney(SellerBalanceApplyEntity param) {

		ResponseMessage<SellerBalanceApplyEntity> responseMessage = new ResponseMessage<SellerBalanceApplyEntity>();

		responseMessage.setCode(SysConstant.FAILURE_CODE);
		responseMessage.setMessage(SysConstant.FAILURE_MSG);

		if (param.getAmount() <= 0)
			return responseMessage;
		if (param.getVcode() == null) {
			return responseMessage;
		}
		if (param.getPhone() == null) {
			return responseMessage;
		}

		SellerEntity sellerEntity = sellerEntityMapper.selectByPrimaryKey(param.getSeller_id());
		if (sellerEntity != null && !sellerEntity.getAccount().equals(param.getPhone())) {
			return responseMessage;
		}

		if (!param.getVcode().equals(RedisUtils.getValue(sellerEntity.getAccount()))) {
			return responseMessage;
		}

		if (sellerBalanceApplyEntityMapper.selectBySellerId(param.getSeller_id()) != null) {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.APPLY_NOT_DEAL);
			return responseMessage;// 有未处理的申请，返回
		}
		Double sum = sellerEntityMapper.guilderCanDrawedMoneySum(param.getSeller_id());
		Double drawed = sellerEntityMapper.guilderDrawedMoney(param.getSeller_id());
		if (sum != null) {
			double d = drawed == null ? 0 : drawed.doubleValue();
			double d2 = sum.doubleValue() - d;
			if (d2 >= param.getAmount()) {
				sellerBalanceApplyEntityMapper.insertSelective(param);
				if (param.getId() > 0) {
					responseMessage.setCode(SysConstant.SUCCESS_CODE);
					responseMessage.setMessage(SysConstant.SUCCESS_MSG);
					return responseMessage;
				}
			}
		}
		responseMessage.setCode(SysConstant.FAILURE_CODE);
		responseMessage.setMessage(SysConstant.MONEY_NOT_ENOUGH);
		return responseMessage;
	}

	public Guild selectInvitCount(Guild g) {
		String invite_code = "D" + EncrUtil.encrypt(g.getId().toString());
		Guild result = new Guild();
		result.setInvite_code(invite_code);
		result.setInvite_count(customerEntityMapper.selectExshopperIdCount(g.getId()));
		return result;
	}

	public List<RegionEntity> queryserviceprovencelist() {
		return regionEntityMapper.selectServiceRegion();
	}

	public List<RegionEntity> queryserviceCityByprovence(String code) {
		return regionEntityMapper.queryserviceCityByprovence(code);
	}

	public List<RegionEntity> queryserviceCountryByCity(String code) {
		return regionEntityMapper.queryserviceCountryByCity(code);
	}

	/**
	 * 施工员在我的界面修改施工简介
	 */
	public boolean modifyConstructorInfo(ConstructModel constructModel) {
		Integer result = 0;
		if (constructModel == null)
			return false;
		Long id = sellerEntityMapper.queryConstructInfoId(constructModel.getId());
		if (id == null)
			return false;

		SellerConstructInfoEntity sellerConstructInfoEntity = new SellerConstructInfoEntity();

		// app传入施工简介内容
		String constructAbout = constructModel.getConstruct_about();
		sellerConstructInfoEntity.setId(id);
		if (!StringUtils.isBlank(constructAbout)) {
			sellerConstructInfoEntity.setConstruct_about(constructAbout);
		}
		if (!StringUtils.isBlank(constructModel.getService_region())) {
			sellerConstructInfoEntity.setService_region(constructModel.getService_region());
		}
		if (constructModel.getUnit_price() != null) {
			sellerConstructInfoEntity.setUnit_price(constructModel.getUnit_price());
		}
		if (constructModel.getUnit_name() != null) {
			sellerConstructInfoEntity.setUnit_name(constructModel.getUnit_name());
		}
		result = sellerConstructInfoEntityMapper.updateByPrimaryKeySelective(sellerConstructInfoEntity);
		return ((result != null && result > 0) ? true : false);
	}

	/**
	 * 施工员 暂停状态
	 */
	public Integer modifyConstructorbreakoffstate(ConstructModel constructModel) {
		return sellerEntityMapper.queryConstructBreakOffState(constructModel.getId());
	}
}
