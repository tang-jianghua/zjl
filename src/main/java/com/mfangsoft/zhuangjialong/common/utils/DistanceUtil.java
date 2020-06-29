package com.mfangsoft.zhuangjialong.common.utils;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年6月30日
* 
*/

public class DistanceUtil {
	private static Double latitudea;//纬度
	private static Double longitudea;//经度
	
	private static Double latitudeb;
	private static Double longitudeb;
      public static Double getDistance(String lbsa,String lbsb){
    	  String[] lbsas = lbsa.split(",");
    	  latitudea = Double.valueOf(lbsas[0]);
    	  longitudea = Double.valueOf(lbsas[1]);
    	  
    	  String[] lbsbs = lbsb.split(",");
    	  latitudeb = Double.valueOf(lbsbs[0]);
    	  longitudeb = Double.valueOf(lbsbs[1]);
    	  
    	  Double a, b, R;  
    	    R = 6378137.0; // 地球半径  
    	    latitudea = latitudea * Math.PI / 180.0;  
    	    latitudeb = latitudeb * Math.PI / 180.0;  
    	    a = latitudea - latitudeb;  
    	    b = (longitudea - longitudeb) * Math.PI / 180.0;  
    	    Double d;  
    	    Double sa2, sb2;  
    	    sa2 = Math.sin(a / 2.0);  
    	    sb2 = Math.sin(b / 2.0);  
    	    d = 2*R*Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(latitudea)  
    	                    * Math.cos(latitudeb) * sb2 * sb2));  
    	    
    	    return Math.floor(d/100)/10;
      }
}
