package com.mfangsoft.zhuangjialong.integration.partner.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.MD5Util;
import com.mfangsoft.zhuangjialong.integration.bank.mapper.BankEntityMapper;
import com.mfangsoft.zhuangjialong.integration.commerce.mapper.CommerceEntityMapper;
import com.mfangsoft.zhuangjialong.integration.enterprise.mapper.BuildClassEntityMapper;
import com.mfangsoft.zhuangjialong.integration.enterprise.mapper.BuildEnterpriseEntityMapper;
import com.mfangsoft.zhuangjialong.integration.enterprise.mapper.EnterpriseEntityMapper;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.BuildEnterpriseEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.service.EnterpriseService;
import com.mfangsoft.zhuangjialong.integration.enums.UserState;
import com.mfangsoft.zhuangjialong.integration.enums.UserType;
import com.mfangsoft.zhuangjialong.integration.partner.mapper.PartnerEntityMapper;
import com.mfangsoft.zhuangjialong.integration.partner.model.PartnerEntity;
import com.mfangsoft.zhuangjialong.integration.partner.model.PartnerModle;
import com.mfangsoft.zhuangjialong.integration.partner.service.PartnerService;
import com.mfangsoft.zhuangjialong.integration.role.mapper.UserRoleEntityMapper;
import com.mfangsoft.zhuangjialong.integration.role.model.UserRoleEntity;
import com.mfangsoft.zhuangjialong.integration.user.mapper.UserEntityMapper;
import com.mfangsoft.zhuangjialong.integration.user.model.UserEntity;
@Service
public class PartnerServiceImpl implements PartnerService{
	
	@Autowired
	private PartnerEntityMapper partnerEntityMapper;
	@Autowired
	private BankEntityMapper bankEntityMapper;
	@Autowired
	private CommerceEntityMapper commerceEntityMapper;
	@Autowired
	private UserEntityMapper userEntityMapper;
	
	@Autowired
	private UserRoleEntityMapper userRoleEntityMapper;
	
	
	@Autowired
	private EnterpriseEntityMapper enterpriseEntityMapper;
	
	@Autowired
	private BuildEnterpriseEntityMapper buildEnterpriseEntityMapper;
	
	
	@Autowired
	private EnterpriseService enterpriseService;
	
	
	private final static Long PARTNER_ROLE=  72L;

	@Override
	public Boolean addPartner(PartnerEntity partnerEntity) {
		// TODO Auto-generated method stub
		
		
		bankEntityMapper.insert(partnerEntity.getBankInfo());
		
		commerceEntityMapper.insert(partnerEntity.getCommerceInfo());
		UserEntity user = createPanterUser(partnerEntity);
		
		setPartner(partnerEntity, user);
		
		createPartnerUserRole(user);
		
		
		if(partnerEntityMapper.insertSelective(partnerEntity)>0){
			return true;
		}
		return false;
	}

	private void setPartner(PartnerEntity partnerEntity, UserEntity user) {
		partnerEntity.setSys_user_id(user.getId());
		
		partnerEntity.setBank_id(partnerEntity.getBankInfo().getId());
		
		partnerEntity.setCommerce_id(partnerEntity.getCommerceInfo().getId());
		
		EnterpriseEntity enterpriseEntity=(EnterpriseEntity) UserContext.getCurrentUserInfo();
		
		partnerEntity.setEnterprise_id(enterpriseEntity.getId());
		
		partnerEntity.setCreater(UserContext.getCurrentUserId());
		
		partnerEntity.setCreate_time(new Date());
	}

	private void createPartnerUserRole(UserEntity user) {
		UserRoleEntity userRoleEntity = new UserRoleEntity();
		
		userRoleEntity.setRole_id(PARTNER_ROLE );
		
		userRoleEntity.setUser_id(user.getId());
		
		userRoleEntityMapper.insertSelective(userRoleEntity);
	}

	private UserEntity createPanterUser(PartnerEntity partnerEntity) {
		UserEntity user= partnerEntity.getUserEntity();
		
		user.setPhone_num(partnerEntity.getPhone_num());
		
		user.setCreate_time(new Date());
		
		user.setState(UserState.OPEN.getIndex());
		
		user.setUser(partnerEntity.getPrincipal());
		user.setPwd(MD5Util.MD5(user.getPwd()));
		
		user.setUser_type(UserType.PARTNER.getIndex());
		
		userEntityMapper.insertSelective(user);
		return user;
	}

	@Override
	public Boolean modifyPartner(PartnerEntity partnerEntity) {
		// TODO Auto-generated method stub
		
		PartnerEntity p =partnerEntityMapper.selectByPrimaryKey(partnerEntity.getId());
		
		
		partnerEntity.getBankInfo().setId(p.getBank_id());
		
		partnerEntity.getCommerceInfo().setId(p.getCommerce_id());
		
		bankEntityMapper.updateByPrimaryKey(partnerEntity.getBankInfo());
		
		commerceEntityMapper.updateByPrimaryKeySelective(partnerEntity.getCommerceInfo());
		
		UserEntity user=userEntityMapper.selectByPrimaryKey(p.getSys_user_id());
		
		
		user.setUser(partnerEntity.getPrincipal());
		
		user.setPhone_num(partnerEntity.getPhone_num());
		
		
		user.setAccount(partnerEntity.getUserEntity().getAccount());
		
		userEntityMapper.updateByPrimaryKeySelective(user);
		
		partnerEntity.setUpdate_time(new Date());
		
		partnerEntity.setUpdater(UserContext.getCurrentUserId());
		
		if(partnerEntityMapper.updateByPrimaryKeySelective(partnerEntity)>0){
			return true;
		}
		return false;
	}

	@Override
	public PartnerEntity getPartnerById(Long id) {
		// TODO Auto-generated method stub
		
		PartnerEntity partnerEntity=partnerEntityMapper.selectAllByPrimaryKey(id);
		
		partnerEntity.setEnterpriseEntity(enterpriseService.getEnterpriseById(partnerEntity.getEnterprise_id()));
		
		return partnerEntity;
	}

	@Override
	public Page<Map<String,Object>> queryPartnerForPage(Page<Map<String,Object>> page) {
		// TODO Auto-generated method stub
		UserContext.popupPageParam(page,"partner");
		page.setData(partnerEntityMapper.queryPartnerForPage(page));
		return page;
	}

	@Override
	public Boolean modifyopenOrClosePartner(Long id, Integer state) {
		// TODO Auto-generated method stub
		PartnerEntity partnerEntity=partnerEntityMapper.selectByPrimaryKey(id);
		
		
		UserEntity  userEntity= userEntityMapper.selectByPrimaryKey(partnerEntity.getSys_user_id());
		userEntity.setState(state);
		
		userEntityMapper.updateByPrimaryKey(userEntity);
		
		return true;
	}

	@Override
	public PartnerEntity getPartnerEntity(Long userId) {
		// TODO Auto-generated method stub
		return partnerEntityMapper.getPartnerEntity(userId);
	}

	@Override
	public List<PartnerEntity> getPartnerName() {
		// TODO Auto-generated method stub
		return partnerEntityMapper.getPartnerName();
	}

	@Override
	public Boolean modifyBackPartner(PartnerEntity partnerEntity) {
		// TODO Auto-generated method stub
		if(partnerEntityMapper.updateByPrimaryKeySelective(partnerEntity)>0){
			
			
			PartnerEntity entity =partnerEntityMapper.selectByPrimaryKey(partnerEntity.getId());
			
			UserEntity userEntity=userEntityMapper.selectByPrimaryKey(entity.getSys_user_id());
			
			UserEntity userEntityUpdate = new UserEntity();
			
			userEntityUpdate.setUser(partnerEntity.getPrincipal());
			userEntityUpdate.setId(userEntity.getId());
			userEntityMapper.updateByPrimaryKeySelective(userEntityUpdate);
			return true;
		}
		return false;
	}

	public  List<PartnerModle>  selectAllPartner(){
		return partnerEntityMapper.selectAllPartner();
	}

}
