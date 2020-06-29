package com.mfangsoft.zhuangjialong.common.utils;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;

public class BeanUtilsExtends extends BeanUtils {

	
	static {
		
		ConvertUtils.register(new DateConvert(), java.util.Date.class);
		
	}
	
	public static void copyProperties(Object dest, Object orig)
	        throws IllegalAccessException, InvocationTargetException {
		BeanUtils.copyProperties(dest, orig);
	    }
	
	
	
	
}
