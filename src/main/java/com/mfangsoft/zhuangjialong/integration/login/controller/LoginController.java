package com.mfangsoft.zhuangjialong.integration.login.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.kaptcha.Producer;
import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.MD5Util;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.common.utils.UserSessionUtil;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;
import com.mfangsoft.zhuangjialong.integration.brand.service.BrandService;
import com.mfangsoft.zhuangjialong.integration.enterprise.mapper.BuildClassEntityMapper;
import com.mfangsoft.zhuangjialong.integration.enterprise.mapper.BuildEnterpriseEntityMapper;
import com.mfangsoft.zhuangjialong.integration.enterprise.mapper.EnterpriseEntityMapper;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.BuildClassEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.BuildEnterpriseEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.service.EnterpriseService;
import com.mfangsoft.zhuangjialong.integration.enums.UserState;
import com.mfangsoft.zhuangjialong.integration.enums.UserType;
import com.mfangsoft.zhuangjialong.integration.partner.model.PartnerEntity;
import com.mfangsoft.zhuangjialong.integration.partner.service.PartnerService;
import com.mfangsoft.zhuangjialong.integration.permission.model.PermissionEntity;
import com.mfangsoft.zhuangjialong.integration.permission.service.PermissionService;
import com.mfangsoft.zhuangjialong.integration.role.model.RoleEntity;
import com.mfangsoft.zhuangjialong.integration.role.model.UserRoleEntity;
import com.mfangsoft.zhuangjialong.integration.role.service.RoleService;
import com.mfangsoft.zhuangjialong.integration.shop.model.ShopEntity;
import com.mfangsoft.zhuangjialong.integration.shop.service.ShopService;
import com.mfangsoft.zhuangjialong.integration.user.model.UserEntity;
import com.mfangsoft.zhuangjialong.integration.user.service.UserService;
@Controller
public class LoginController {
	
	
	  @Autowired  
      private Producer captchaProducer = null;   
	  
	  @Autowired
	  
	  private UserService userService;
	  
	  
	  @Autowired
	  private  PermissionService permissionService;
	  
	  
	  @Autowired
	  private EnterpriseEntityMapper enterpriseEntityMapper;
	  
	  @Autowired
	  private EnterpriseService enterpriseService;
	  
	  @Autowired
	  private PartnerService partnerService;
	  
	  @Autowired
	  private BrandService brandService;
	  
	  @Autowired
	  private RoleService roleService;
	  
	  @Autowired
	  private BuildEnterpriseEntityMapper buildEnterpriseEntityMapper;
	  
	  @Autowired
	  private BuildClassEntityMapper buildClassEntityMapper;
	  
	  @Autowired
	  private ShopService shopService;
	  
	  
	  private final static boolean IS_V_CODE= true;
	  
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public void  login(UserEntity user,HttpSession session,HttpServletResponse response,HttpServletRequest request) throws IOException{
		
				
		UserEntity  userEntity=userService.getUserByAccount(user.getAccount());
		
		
		UserRoleEntity userRoleEntity=roleService.selectUserRoleByUserId(userEntity.getId());
		RoleEntity roleEntity=roleService.getRoleById(userRoleEntity.getRole_id());
		
		userEntity.setRoleEntity(roleEntity);
		if(userEntity!=null){
			session.setAttribute(UserContext.USER_PERMISSION, permissionService.getPermissionByUserId(userEntity.getId()));
			
			this.setUserInfo(session, userEntity);
			//UserSessionUtil.remove();
			
			UserSessionUtil.setUserSession(session);
			response.sendRedirect("views/dashboard/index.html");
			
			
		}else{
			
			response.sendRedirect("login");
		}
	}
	
	
	
	private  void  setUserInfo(HttpSession session,UserEntity userEntity){
		
		if(userEntity.getUser_type().intValue()==UserType.ENTERPRISE.getIndex().intValue()){
			
			EnterpriseEntity enterpriseEntity=enterpriseEntityMapper.getEnterpriseEntity(userEntity.getId());
			session.setAttribute(UserContext.CURRENT_USER_INFO, enterpriseService.getEnterpriseById(enterpriseEntity.getId()));
			
			userEntity.setEnterprise_name(enterpriseEntity.getEnterprise_name());
			
		}else if(userEntity.getUser_type().intValue()==UserType.PARTNER.getIndex().intValue()){
			
			PartnerEntity partnerEntity=partnerService.getPartnerEntity(userEntity.getId());
			
			
			session.setAttribute(UserContext.PARENT_USER_INFO, enterpriseEntityMapper.selectByPrimaryKey(partnerEntity.getEnterprise_id()));
			
			
			session.setAttribute(UserContext.CURRENT_USER_INFO, partnerService.getPartnerById(partnerEntity.getId()));
			
			EnterpriseEntity ent= enterpriseEntityMapper.selectByPrimaryKey(partnerEntity.getEnterprise_id());
			
			Map<String,Object> map = new HashMap<>();
			
			map.put("where", "partner_id");
			map.put("value", partnerEntity.getId());
			
			Map<String,Object> re=enterpriseEntityMapper.selectEntById(map);
			
			userEntity.setEnterprise_name(ent.getEnterprise_name());
			
		}else if(userEntity.getUser_type().intValue()==UserType.BRAND.getIndex().intValue()){
			
			BrandEntity  brandEntity = brandService.selectBrandNameByUserId(userEntity.getId());
			
			BrandEntity  brand=brandService.getBrandById(brandEntity.getId());
			
			session.setAttribute(UserContext.CURRENT_USER_INFO,brand);
			
			userEntity.setEnterprise_name(brand.getPartnerEntity().getEnterpriseEntity().getEnterprise_name());
			
		}else if(userEntity.getUser_type().intValue()==UserType.SHOP.getIndex().intValue()){
			
			ShopEntity shopEntity=shopService.selectshopByUserId(userEntity.getId());
			
			ShopEntity entity =shopService.getShopById(shopEntity.getId());
			userEntity.setEnterprise_name(entity.getBrandEntity().getPartnerEntity().getEnterpriseEntity().getEnterprise_name());
			session.setAttribute(UserContext.CURRENT_USER_INFO,entity);
		}
		
		session.setAttribute(SysConstant.USER_INFO, userEntity);
	}
	
	
	@RequestMapping(value="/v_user",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String>  validateUser (UserEntity user,HttpSession session,HttpServletResponse response) throws IOException{
		
		ResponseMessage<String> message = new ResponseMessage<String>();
		
		if(IS_V_CODE){
			
			
			if(session.getAttribute(SysConstant.KAPTCHA_SESSION_KEY)==null){
				
				 message.setCode(SysConstant.FAILURE_CODE);
				 message.setMessage("重新获取验证码");
				 
				 return  message;
			}
			
			if(user.getV_code().equals(session.getAttribute(SysConstant.KAPTCHA_SESSION_KEY).toString())){
				 return this.v_user(user, message);
			 }else{
				 
				 message.setCode(SysConstant.FAILURE_CODE);
				 message.setMessage("验证码错误");
				 
				 return  message;
			 }
		}else{
			
			return this.v_user(user, message);
		}
		
		
		 
		
		
		
		
	}
	
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	
	@CrossOrigin
	public void login2(HttpSession session,HttpServletResponse response) throws IOException{
		
		
		response.sendRedirect("login.html");
		
		
	}
	private  ResponseMessage<String>   v_user(UserEntity userEntity, ResponseMessage<String> message){
		
		
		
		
		
		UserEntity entity=userService.getUserByAccount(userEntity.getAccount());
		
		
		
		if(entity==null){
			
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage("账号不存在");
			
			return message;
		}
		
		if(entity.getState()==UserState.CLOSE.getIndex()){
			
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage("账号已关闭");
			return message;
		}
		
		
		if(entity.getPwd().equals(MD5Util.MD5(userEntity.getPwd()))){
			
			
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			
			return  message;
			
		}else{
			
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage("密码错误");
			
			return message;
		}
	}
	
	@RequestMapping(value="/getvcode",method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> getVcode(HttpServletResponse response,HttpSession session)
	{
		 
        String code = (String)session.getAttribute(SysConstant.KAPTCHA_SESSION_KEY);  
        response.setDateHeader("Expires", 0);  
          
        // Set standard HTTP/1.1 no-cache headers.  
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");  
          
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).  
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");  
          
        // Set standard HTTP/1.0 no-cache header.  
        response.setHeader("Pragma", "no-cache");  
          
        // return a jpeg  
        response.setContentType("image/jpeg");  
          
        // create the text for the image  
        String capText = captchaProducer.createText();  
          
        // store the text in the session  
        session.setAttribute(SysConstant.KAPTCHA_SESSION_KEY, capText);  
          
        // create the image with the text  
        BufferedImage bi = captchaProducer.createImage(capText);  
        
        ByteArrayOutputStream  arrayOutputStream = new ByteArrayOutputStream();
        
        
        // write the data out  
        try {
			ImageIO.write(bi, "jpg", arrayOutputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        
        byte []  b=arrayOutputStream.toByteArray();
        
        String imageStr=Base64Utils.encodeToString(b);
        ResponseMessage<String>  message=new ResponseMessage<String>();
        
        message.setCode(SysConstant.SUCCESS_CODE);
        message.setMessage(SysConstant.SUCCESS_MSG);
        
        message.setResult(imageStr);
        
        return message;
       
	}
	@RequestMapping(value="/server/getPermissionByUserId",method=RequestMethod.GET)
	@ResponseBody
	public List<PermissionEntity> getPermissionByUserId(HttpSession httpSession){
	
		return UserContext.getCurrentPermission();
		
		
		
	}
	
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	@ResponseBody
	public ResponseMessage<String> logout(HttpSession session){
		
		
		if (session.getAttribute(SysConstant.USER_INFO) != null) {

			session.removeAttribute(SysConstant.USER_INFO);
		}

		if (session.getAttribute(UserContext.CURRENT_USER_INFO) != null) {

			session.removeAttribute(UserContext.CURRENT_USER_INFO);
		}

		if (session.getAttribute(UserContext.PARENT_USER_INFO) != null) {

			session.removeAttribute(UserContext.PARENT_USER_INFO);
		}
		if (session.getAttribute(UserContext.USER_PERMISSION) != null) {

			session.removeAttribute(UserContext.USER_PERMISSION);
		}

		UserSessionUtil.remove();

		ResponseMessage<String> message = new ResponseMessage<>();

		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		return message;
		
	}
	
	
	@RequestMapping(value="/server/getCurrentUser",method=RequestMethod.GET)
	@ResponseBody
	public Object getCurrentUser(HttpSession session){
		
		
		return UserContext.getCurrentUser();
		
	}
	@RequestMapping(value="/server/getCurrentUserInfo",method=RequestMethod.GET)
	@ResponseBody
	public Object getCurrentUserInfo(HttpSession session){
		
		
		return UserContext.getCurrentUserInfo();
		
	}
	
	
}
