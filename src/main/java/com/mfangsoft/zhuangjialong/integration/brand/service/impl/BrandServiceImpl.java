package com.mfangsoft.zhuangjialong.integration.brand.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.PatternEditor;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.newproductcore.mapper.SuperCore;
import com.mfangsoft.zhuangjialong.app.newproductcore.service.ProductCoreService;
import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.MD5Util;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.common.utils.UserSessionUtil;
import com.mfangsoft.zhuangjialong.integration.bank.mapper.BankEntityMapper;
import com.mfangsoft.zhuangjialong.integration.brand.mapper.BaseBrandStateApplyEntityMapper;
import com.mfangsoft.zhuangjialong.integration.brand.mapper.BrandBannerEntityMapper;
import com.mfangsoft.zhuangjialong.integration.brand.mapper.BrandEntityMapper;
import com.mfangsoft.zhuangjialong.integration.brand.mapper.BrandMainProductEntityMapper;
import com.mfangsoft.zhuangjialong.integration.brand.model.BaseBrandStateApplyEntity;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandBannerEntity;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandModel;
import com.mfangsoft.zhuangjialong.integration.brand.service.BrandService;
import com.mfangsoft.zhuangjialong.integration.commerce.mapper.CommerceEntityMapper;
import com.mfangsoft.zhuangjialong.integration.commerce.model.CommerceEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.mapper.BuildClassEntityMapper;
import com.mfangsoft.zhuangjialong.integration.enterprise.mapper.BuildEnterpriseEntityMapper;
import com.mfangsoft.zhuangjialong.integration.enterprise.mapper.EnterpriseEntityMapper;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.BuildClassEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.BuildEnterpriseEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseEntity;
import com.mfangsoft.zhuangjialong.integration.enums.UserState;
import com.mfangsoft.zhuangjialong.integration.enums.UserType;
import com.mfangsoft.zhuangjialong.integration.introduce.mapper.IntroduceProductEntityMapper;
import com.mfangsoft.zhuangjialong.integration.partner.mapper.PartnerEntityMapper;
import com.mfangsoft.zhuangjialong.integration.partner.model.PartnerEntity;
import com.mfangsoft.zhuangjialong.integration.partner.service.PartnerService;
import com.mfangsoft.zhuangjialong.integration.permission.mapper.PermissionEntityMapper;
import com.mfangsoft.zhuangjialong.integration.role.mapper.UserRoleEntityMapper;
import com.mfangsoft.zhuangjialong.integration.role.model.UserRoleEntity;
import com.mfangsoft.zhuangjialong.integration.user.mapper.UserEntityMapper;
import com.mfangsoft.zhuangjialong.integration.user.model.UserEntity;
@Service(value="brandservice")
public class BrandServiceImpl extends SuperCore implements  BrandService {

	 private final static  Long BRAND_ROLE=73L;
	 
	 private final static int APPLY_STATE_ON=1;//品牌申请有效状态
	 private final static int STATE_ON=1;//品牌启用状态
	 private final static int STATE_OFF=0;//品牌禁用状态
	 private final static int STATE_OFFING=2;//品牌下线审核状态

	 public final static int APPLY_RESULT_FALSE = 0;//申请失败
	 public final static int APPLY_RESULT_SUCCESS = 1;//申请成功
	 public final static int APPLY_RESULT_INTRO = 2;//有推荐产品
	 public final static String APPLY_INTRO_MSG = "取消推荐产品才可以申请下架。";//有推荐产品
	@Autowired
	private BrandEntityMapper brandEntityMapper;
	
	@Autowired
	private BankEntityMapper bankEntityMapper;
	
	@Autowired
	private CommerceEntityMapper  commerceEntityMapper;
	
	@Autowired
	private UserEntityMapper userEntityMapper;
	
	@Autowired
	private UserRoleEntityMapper userRoleEntityMapper;

	@Autowired
	private BaseBrandStateApplyEntityMapper brandStateApplyEntityMapper;
	
	
	@Autowired
	private EnterpriseEntityMapper enterpriseEntityMapper;
	
	@Autowired
	private  BuildEnterpriseEntityMapper buildEnterpriseEntityMapper;
	
	@Autowired
	private  BuildClassEntityMapper buildClassEntityMapper;
	
	@Autowired
	private PartnerService partnerService;
	
	@Autowired
	private ProductCoreService productCoreServiceImpl;
	
	@Autowired
	private IntroduceProductEntityMapper introduceProductEntityMapper;
	
	
	@Override
	public Boolean addBrand(BrandEntity brandEntity) {
		// TODO Auto-generated method stub
		bankEntityMapper.insert(brandEntity.getBankEntity());
		
		commerceEntityMapper.insert(brandEntity.getCommerceEntity());
		
		brandEntity.setBand_info_id(brandEntity.getBankEntity().getId());
		
		brandEntity.setCommerce_id(brandEntity.getCommerceEntity().getId());
		
		UserEntity userEntity = brandEntity.getUserEntity();
		
		userEntity.setCreate_time(new Date());
		
		
		userEntity.setState(UserState.OPEN.getIndex());
		
		
		userEntity.setUser_type(UserType.BRAND.getIndex());
		
		
		userEntity.setPwd(MD5Util.MD5(userEntity.getPwd()));
		
		userEntity.setPhone_num(brandEntity.getPhone_num());
		
		userEntity.setUser(brandEntity.getPrincipal());
		
		userEntityMapper.insertSelective(brandEntity.getUserEntity());
		
		
		createUserRole(userEntity,brandEntity);
		brandEntity.setSys_user_id(userEntity.getId());
		
		brandEntity.setCreater(UserContext.getCurrentUserId()+"");
		
		brandEntity.setCreate_time(new Date());
		
		PartnerEntity partnerEntity= (PartnerEntity) UserContext.getCurrentUserInfo();
		brandEntity.setCitypartner_id(partnerEntity.getId());
		 if(brandEntityMapper.insertSelective(brandEntity)>0){
			 return true;
		 }
		
		return false;
	}

	private void createUserRole(UserEntity userEntity,BrandEntity brandEntity) {
		
		PartnerEntity partnerEntity= (PartnerEntity) UserContext.getCurrentUserInfo();
		
		EnterpriseEntity enterpriseEntity=enterpriseEntityMapper.selectByPrimaryKey(brandEntity.getEnterprise_id());
		
		
		UserRoleEntity roleEntity = new UserRoleEntity();
		
		//roleEntity.setRole_id(new Long(SysConstant.brandmap.get(enterpriseEntity.getClass_id()+"").toString()));
		
		roleEntity.setRole_id(BRAND_ROLE);
		
		roleEntity.setUser_id(userEntity.getId());
		
		userRoleEntityMapper.insertSelective(roleEntity);
	}

	@Override
	public BrandEntity getBrandById(Long id) {
		// TODO Auto-generated method stub
		
		
		BrandEntity brandEntity= brandEntityMapper.selectByPrimaryKey(id);
		
		brandEntity.setPartnerEntity(partnerService.getPartnerById(brandEntity.getCitypartner_id()));
		
		BuildEnterpriseEntity buildEnterpriseEntity=buildEnterpriseEntityMapper.selectByPrimaryKey(brandEntity.getEnterprise_id());
				buildEnterpriseEntity.setBuildClassEntity(buildClassEntityMapper.selectByPrimaryKey(buildEnterpriseEntity.getClass_id()));
		
		brandEntity.setBuildEnterpriseEntity(buildEnterpriseEntity);
		return brandEntity;
	}

	@Override
	public Integer getbrandIdintifyType() {
		UserEntity u = UserContext.getCurrentUser();
		if (u.getUser_type() == UserType.BRAND.getIndex()) {
			BrandEntity b = (BrandEntity) UserContext.getCurrentUserInfo();
			return b.getBrand_idintify_type();
		} else{
			return -1;
		}
	}
	
	@Override
	public Boolean modifyBrand(BrandEntity brandEntity) {
		// TODO Auto-generated method stub
		BrandEntity entity =brandEntityMapper.selectByPrimaryKey(brandEntity.getId());
		
		brandEntity.getBankEntity().setId(entity.getBand_info_id());
		
		
		bankEntityMapper.updateByPrimaryKeySelective(brandEntity.getBankEntity());
		
		
		brandEntity.getCommerceEntity().setId(entity.getCommerce_id());
		
		EnterpriseEntity enterpriseEntity=enterpriseEntityMapper.selectByPrimaryKey(entity.getEnterprise_id());
		
		
		
		
		commerceEntityMapper.updateByPrimaryKeySelective(brandEntity.getCommerceEntity());
		
		
		
		brandEntity.getUserEntity().setId(entity.getSys_user_id());
		///permissionEntityMapper.deletePermissionByRoleId(brandEntity.getUserEntity().getId());
		//this.createUserRole(brandEntity.getUserEntity(), brandEntity);
		userEntityMapper.updateByPrimaryKeySelective(brandEntity.getUserEntity());
		
		brandEntity.setUpdate_time(new Date());
		
		if(brandEntityMapper.updateByPrimaryKeySelective(brandEntity)>0){
			return true;
		}
		
		return false;
	}

	@Override
	public Page<Map<String,Object>> queryBrandForPage(Page<Map<String,Object>> page) {
		// TODO Auto-generated method stub
		
		UserContext.popupPageParam(page,"brand");
		
		page.setData(brandEntityMapper.queryBrandForPage(page));
		return page;
	}

	@Override
	public Boolean modifyopenOrCloseBrand(Long id, Integer state) {
		// TODO Auto-generated method stub
		
		BrandEntity  brandEntity=brandEntityMapper.selectByPrimaryKey(id);
		
		UserEntity userEntity =brandEntity.getUserEntity();
		
		userEntity.setId(brandEntity.getSys_user_id());
		userEntity.setState(state);
		userEntityMapper.updateByPrimaryKeySelective(userEntity);
		
		return true;
	}

	@Override
	public BrandEntity selectBrandNameByUserId(Long userId) {
		// TODO Auto-generated method stub
		return brandEntityMapper.selectBrandNameByUserId(userId);
	}

	@Override
	public List<BrandEntity> selectBrandName() {
		// TODO Auto-generated method stub
		return brandEntityMapper.selectBrandName();
	}

	@Override
	public Boolean modifyBackBrand(BrandEntity brandEntity) {
		// TODO Auto-generated method stub
		if(brandEntityMapper.updateByPrimaryKeySelective(brandEntity)>0){
			
			BrandEntity entity =brandEntityMapper.selectByPrimaryKey(brandEntity.getId());
			
			UserEntity userEntity=userEntityMapper.selectByPrimaryKey(entity.getSys_user_id());
			
			UserEntity userEntityUpdate = new UserEntity();
			
			userEntityUpdate.setUser(brandEntity.getPrincipal());
			userEntityUpdate.setId(userEntity.getId());
			userEntityMapper.updateByPrimaryKeySelective(userEntityUpdate);
			
			return true;
		}
		return false;
	}


	@Override
	public ResponseMessage<String> modifyBrandOnShelf(BrandEntity brand) {
		BrandModel brandModel = brandEntityMapper.selectBrandStateConditions(brand.getId());
		
		StringBuffer msg=new StringBuffer();
		
		msg.append(brandModel.getBanner_num()>0 ? "" : "缺少轮播图，");
		msg.append(brandModel.getMain_num()>0 ? "" : "缺少首页产品，");
		msg.append(brandModel.getProduct_num()>0 ? "" : "缺少线上产品，");
		msg.append(brandModel.getShop_num()>0 ? "" : "缺少线上店铺，");
		ResponseMessage<String> message = new ResponseMessage<String>();
		if(msg.length()!=0){//不能上架
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(msg.append("无法上架。").toString());
		}else{//可以上架
			brand.setState(STATE_ON);
          if(brandEntityMapper.updateByPrimaryKeySelective(brand)>0 && productCoreServiceImpl.updateBrandStateDoc(brand.getId(), STATE_ON)){
        	  message.setCode(SysConstant.SUCCESS_CODE);
        	  message.setMessage(SysConstant.SUCCESS_MSG);
          }else{
          message.setCode(SysConstant.FAILURE_CODE);
          message.setMessage(SysConstant.FAILURE_MSG);
		}
		}
		return message;
	}

	@Override
	public Integer modifyBrandOffingShelf(BaseBrandStateApplyEntity brand) {
		UserEntity u = UserContext.getCurrentUser();
		if(u.getUser_type()==UserType.PARTNER.getIndex()){
			int selectCountByBrandId = introduceProductEntityMapper.selectCountByBrandId(brand.getBrand_id());
			if(selectCountByBrandId>0){
				return APPLY_RESULT_INTRO;
			}
			brand.setState(APPLY_STATE_ON);
			brand.setUser_id(u.getId());
			int istate = brandStateApplyEntityMapper.insertSelective(brand);
			BrandEntity brandEntity = new BrandEntity();
			brandEntity.setId(brand.getBrand_id());
			brandEntity.setState(STATE_OFFING);
			int ibrand = brandEntityMapper.updateByPrimaryKeySelective(brandEntity);
			 boolean icore = productCoreServiceImpl.updateBrandStateDoc(brand.getBrand_id(), STATE_OFFING);
		if(istate>0 && ibrand>0 && icore){
			return APPLY_RESULT_SUCCESS;
		}else if(istate>0 &&( ibrand==0 || !icore)){
			brandStateApplyEntityMapper.deleteByPrimaryKey(brand.getId());
			return APPLY_RESULT_FALSE;
		}
		}
		return APPLY_RESULT_FALSE;
	}

	@Override
	public Boolean modifyBrandOffShelf(BaseBrandStateApplyEntity brand) {
		UserEntity u = UserContext.getCurrentUser();
		if(u.getUser_type()==UserType.PLATFORM.getIndex()){
			int istate = brandStateApplyEntityMapper.updateByPrimaryKeySelective(brand);
			BrandEntity brandEntity = new BrandEntity();
			brandEntity.setId(brand.getBrand_id());
			brandEntity.setState(STATE_OFF);
			int ibrand = brandEntityMapper.updateByPrimaryKeySelective(brandEntity);
			boolean icore = productCoreServiceImpl.updateBrandStateDoc(brand.getBrand_id(), STATE_OFF);
			if(istate>0 && ibrand>0 && icore){
				return true;
			}else if(istate>0 && (ibrand==0 || !icore)){
				brand.setState(APPLY_STATE_ON);
				brandStateApplyEntityMapper.updateByPrimaryKeySelective(brand);
				return false;
			}
		}
		return false;
	}

	@Override
	public Page<Map<String, Object>> getApplyOffStateBrands(Page<Map<String, Object>> page) {
		List<Map<String,Object>> list = brandEntityMapper.getApplyOffStateBrandsForPage(page);
		page.setData(list);
		return page;
	}

}
