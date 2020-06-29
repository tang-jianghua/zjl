package com.mfangsoft.zhuangjialong.common.utils;

import java.util.ResourceBundle;


public class PropUtis {
	
	private static ResourceBundle RESOURCE_BUNDLE=null;
	
	 static {  RESOURCE_BUNDLE = ResourceBundle.getBundle("sys");
	 
	 }
	
	
	public static  String  getValue(String key){
		
		return RESOURCE_BUNDLE.getString(key);
	}
	
	
	public static void main(String[] args) {
		
		PropUtis.getValue("file.path");
	}

}
