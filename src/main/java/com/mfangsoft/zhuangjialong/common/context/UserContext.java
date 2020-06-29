package com.mfangsoft.zhuangjialong.common.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.common.utils.UserSessionUtil;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseEntity;
import com.mfangsoft.zhuangjialong.integration.enums.UserType;
import com.mfangsoft.zhuangjialong.integration.partner.model.PartnerEntity;
import com.mfangsoft.zhuangjialong.integration.permission.model.PermissionEntity;
import com.mfangsoft.zhuangjialong.integration.user.model.UserEntity;

public class UserContext {
	
	private final static Logger log = LoggerFactory.getLogger(UserContext.class);
	
	public  final static String USER_PERMISSION="user_permission";
	
	
	public  final static String CURRENT_USER_INFO="current_user_info";
	
	public  final static String PARENT_USER_INFO="PARENT_USER_INFO";
	
	
	public static UserEntity getCurrentUser(){
		
		
		
		return (UserEntity) UserSessionUtil.getUserSession().getAttribute(SysConstant.USER_INFO);
		
	}
	
	public static  Long  getCurrentUserId()
	{
		
		UserEntity userEntity=(UserEntity) UserSessionUtil.getUserSession().getAttribute(SysConstant.USER_INFO);
		
		return userEntity.getId();
		
	}
	
	public static List<PermissionEntity>  getCurrentPermission(){
		
		return (List<PermissionEntity>) UserSessionUtil.getUserSession().getAttribute(USER_PERMISSION);
		
		
	}
	
	
	public static Object getCurrentUserInfo()
	{
		return UserSessionUtil.getUserSession().getAttribute(CURRENT_USER_INFO);
	}
	
	public static Object getParnetUserInfo(){
		
		return UserSessionUtil.getUserSession().getAttribute(PARENT_USER_INFO);
	}
	
	
	public static <T> Page<T>  popupPageParam(Page<T> page,String flag){
		
		
		if (!UserContext.getCurrentUser().getAccount().equals("admin")) {

			if (page.getParam() != null) {

				Map<String, Object> map = (Map<String, Object>) page.getParam();
				popupMap(map,flag);

			} else {

				Map<String, Object> map = new HashMap<>();

				popupMap(map,flag);

				page.setParam(map);

			}
		}
		return page;
	}

	private static void popupMap(Map<String, Object> map,String flag) {
		if (UserContext.getCurrentUser().getUser_type() == UserType.ENTERPRISE.getIndex()) {

			
			if(flag.equals("partner")){
				EnterpriseEntity enterpriseEntity = (EnterpriseEntity) UserContext.getCurrentUserInfo();
				
				map.put("currentUser", enterpriseEntity.getId());
			}else if(flag.equals("brand")){
				
                EnterpriseEntity enterpriseEntity = (EnterpriseEntity) UserContext.getCurrentUserInfo();
				
				map.put("brandp", "select p.id  from t_biz_enterprise e,t_biz_partner p where  e.id =p.enterprise_id and e.id="+enterpriseEntity.getId());
			}else if(flag.equals("shop")){
				
				 EnterpriseEntity enterpriseEntity = (EnterpriseEntity) UserContext.getCurrentUserInfo();
				map.put("shopb", "select  brand.id from  t_biz_brand brand where brand.citypartner_id in (select vi.partner_id  from   v_personal_info  vi where vi.enterprise_id="+enterpriseEntity.getId()+" group by vi.partner_id)");
			}
		} else if (UserContext.getCurrentUser().getUser_type() == UserType.PARTNER.getIndex()) {

			PartnerEntity partnerEntity = (PartnerEntity) UserContext.getCurrentUserInfo();
            if(flag.equals("brand")){
            	
            	map.put("currentUser", partnerEntity.getId());
            	
            }else if(flag.equals("shop")){
            	
            	map.put("shopb", "select b.id  from  t_biz_brand b, t_biz_partner p  where  b.citypartner_id = p.id  and p.id ="+partnerEntity.getId());
             }
            
		} else if (UserContext.getCurrentUser().getUser_type() == UserType.BRAND.getIndex()) {

			BrandEntity brandEntity = (BrandEntity) UserContext.getCurrentUserInfo();

			map.put("currentUser", brandEntity.getId());

		}
	}

}
