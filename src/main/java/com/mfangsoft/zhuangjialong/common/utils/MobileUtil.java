package com.mfangsoft.zhuangjialong.common.utils;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年9月23日
* 
*/
public class MobileUtil { 
/** 
 * 判断传入的参数号码为哪家运营商 
 * @param mobile 
 * @return 运营商名称 
 */  
	public final static String ERROR="ERROR";//错误
	public final static String UNKNOW="UNKNOW";//未知运营商
public static String validateMobile(String mobile){  
    if(mobile==null || mobile.trim().length()!=11){  
        return ERROR;        //mobile参数为空或者手机号码长度不为11，错误！  
    }  
    if(mobile.trim().substring(0,3).equals("134") ||  mobile.trim().substring(0,3).equals("135") 
    		|| mobile.trim().substring(0,3).equals("136") || mobile.trim().substring(0,3).equals("137")    
            || mobile.trim().substring(0,3).equals("138") || mobile.trim().substring(0,3).equals("139")
            || mobile.trim().substring(0,3).equals("147") || mobile.trim().substring(0,3).equals("178")
            || mobile.trim().substring(0,3).equals("150") || mobile.trim().substring(0,3).equals("151") 
            || mobile.trim().substring(0,3).equals("152") || mobile.trim().substring(0,3).equals("157") 
            || mobile.trim().substring(0,3).equals("158") || mobile.trim().substring(0,3).equals("159")   
            || mobile.trim().substring(0,3).equals("187") || mobile.trim().substring(0,3).equals("188")
            || mobile.trim().substring(0,3).equals("182") || mobile.trim().substring(0,3).equals("183")
            || mobile.trim().substring(0,3).equals("184") || mobile.trim().substring(0,4).equals("1705")){  
        return   SysConstant.CMCC;//中国移动  
    }  
    if(mobile.trim().substring(0,3).equals("130") || mobile.trim().substring(0,3).equals("131") 
    		|| mobile.trim().substring(0,3).equals("132") || mobile.trim().substring(0,3).equals("156")    
            || mobile.trim().substring(0,3).equals("185") || mobile.trim().substring(0,3).equals("186")
            || mobile.trim().substring(0,3).equals("145") || mobile.trim().substring(0,3).equals("155")
            || mobile.trim().substring(0,3).equals("176") || mobile.trim().substring(0,4).equals("1709")
            || mobile.trim().substring(0,3).equals("1708") || mobile.trim().substring(0,3).equals("1707")){  
      return SysConstant.CUCC;   //中国联通  
    }  
    if(mobile.trim().substring(0,3).equals("180") ||  mobile.trim().substring(0,3).equals("181") 
    		|| mobile.trim().substring(0,3).equals("189") || mobile.trim().substring(0,3).equals("133")
    		|| mobile.trim().substring(0,3).equals("153") || mobile.trim().substring(0,4).equals("1700")
    		|| mobile.trim().substring(0,3).equals("177") || mobile.trim().substring(0,3).equals("173")
    		|| mobile.trim().substring(0,4).equals("1701") || mobile.trim().substring(0,4).equals("1702")
    		){  
       return SysConstant.CTCC;   //中国电信  
    }  
    return UNKNOW;
}  
public static void main(String[] arg){  
    MobileUtil util=new MobileUtil();  
    System.out.println(util.validateMobile("13999889090"));  
    System.out.println(util.validateMobile("13418170986"));  
    System.out.println(util.validateMobile("15392496493"));  
    System.out.println(util.validateMobile("13399889090"));  
    System.out.println(util.validateMobile("erot4543545"));  
    System.out.println(util.validateMobile("13051671528"));  
    System.out.println(util.validateMobile("erot543545"));  
	
}
}  