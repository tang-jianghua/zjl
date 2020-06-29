package com.mfangsoft.zhuangjialong.common.utils;

import java.util.Date;

import org.apache.commons.beanutils.Converter;

public class DateConvert implements Converter {

	@Override
	public Object convert(Class type, Object value) {
		// TODO Auto-generated method stub
		
		if(value==null){
			return null;
		}
		 if(value instanceof Date){  
	            return value;  
	        }  
		 if(value instanceof String){  
	            return value;  
	        }  
		 
		return null;
	}

}
