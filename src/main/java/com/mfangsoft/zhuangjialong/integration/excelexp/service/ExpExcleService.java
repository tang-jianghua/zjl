package com.mfangsoft.zhuangjialong.integration.excelexp.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.ibatis.ognl.OgnlException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.integration.excelexp.model.BaseExpExcleEntity;

public interface ExpExcleService {
	
	
	
	
	
	XSSFWorkbook  getHSSFWorkbook(String module,Page<Map<String,Object>> page) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException,IOException,OgnlException;
	
	BaseExpExcleEntity  getExpExcelEntity(String module_code); 
	
	

}
