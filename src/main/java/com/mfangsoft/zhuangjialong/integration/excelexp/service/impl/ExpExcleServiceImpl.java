package com.mfangsoft.zhuangjialong.integration.excelexp.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.ognl.Ognl;
import org.apache.ibatis.ognl.OgnlContext;
import org.apache.ibatis.ognl.OgnlException;
import org.apache.ibatis.ognl.enhance.ExpressionAccessor;
import org.apache.ibatis.scripting.xmltags.OgnlClassResolver;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SpringBeantUtils;
import com.mfangsoft.zhuangjialong.integration.excelexp.mapper.BaseExpExcleEntityMapper;
import com.mfangsoft.zhuangjialong.integration.excelexp.model.BaseExpExcleEntity;
import com.mfangsoft.zhuangjialong.integration.excelexp.service.ExpExcleService;
import com.mfangsoft.zhuangjialong.system.mapper.sysConstantEntityMapper;
import com.mfangsoft.zhuangjialong.system.utils.sysConstantEntityUtils;
@Service
public class ExpExcleServiceImpl implements ExpExcleService {

	@Autowired
	private BaseExpExcleEntityMapper expExcleEntityMapper;
	
	
	@Autowired
	private sysConstantEntityMapper sysConstantEntityMapper;
	
	

	
	@Override
	public XSSFWorkbook getHSSFWorkbook(String module, Page<Map<String, Object>> page) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, IOException, OgnlException {
		// TODO Auto-generated method stub
		
		BaseExpExcleEntity baseExpExcleEntity =expExcleEntityMapper.getExpExcelEntity(module);
		
		if(baseExpExcleEntity==null){
			
			return null;
		}
		
		String classString=baseExpExcleEntity.getExport_class();
		
		
		String className=classString.substring(0,classString.lastIndexOf("."));
		
	
		Object obj=SpringBeantUtils.getBean(className);
		
		Method method=obj.getClass().getMethod(classString.substring(classString.lastIndexOf(".")+1, classString.length()), Page.class);
		
		Page<Map<String, Object>> pageInvoke=(Page<Map<String, Object>>) method.invoke(obj, page);
		
		
		List<Map<String, Object>> list=pageInvoke.getData();
		if(list==null){
			
			
			return null;
		}else if(list.size()==0){
			
			
			return null;
		}
		XSSFWorkbook workbook=null;
		if(!baseExpExcleEntity.getIs_user_type()){
			 workbook=this.getHSSFWorkbook(baseExpExcleEntity.getTemplate_name(), "", list, module);
			
		}else{
			 workbook=this.getHSSFWorkbook(baseExpExcleEntity.getTemplate_name(), "_"+UserContext.getCurrentUser().getUser_type()+"", list, module);
			
		}
		
		
		
		return workbook;
	}
	private XSSFWorkbook getHSSFWorkbook(String templateName,String userType,List<Map<String, Object>> list,String module) throws IOException, OgnlException{
		
		String basePath=this.getClass().getResource("/").getPath()+"exporttemp";
		
		
	
		FileInputStream fileInputStream = new FileInputStream(new File(basePath+"/"+templateName+userType+".xlsx")); 
		
		XSSFWorkbook  xssfWorkbook = new XSSFWorkbook(fileInputStream);
		
		XSSFSheet  xssfSheet =xssfWorkbook.getSheetAt(0);
		
		
		XSSFCellStyle setBorder = xssfWorkbook.createCellStyle();
		
		setBorder.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下边框
		setBorder.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
		setBorder.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框
		setBorder.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
		
		int rowNum=2;
		List<String> columList = new ArrayList<>();
		XSSFRow  hssfRow=xssfSheet.getRow(rowNum);
		for(int i=0;i<hssfRow.getLastCellNum();i++){
			columList.add(hssfRow.getCell(i).getStringCellValue());
		}
		Map<String,Object> resultMap = list.get(0);
		rowNum=this.writeToRow(xssfSheet, rowNum, columList, resultMap, module);
		for(int n=1;n<list.size();n++){
			Map<String,Object> map = list.get(n);
			
			XSSFRow row =xssfSheet.createRow(rowNum);
			
			for(int i=0;i<columList.size();i++){
				
				String  ex=columList.get(i);
				
				 String repString= this.getCellValue(ex, map, module);
				 
				 if(ex.equals("#{no}")){
					 repString=(n+1)+"";
				 }
					 
					 XSSFCell  cell=row.createCell(i);
					 cell.setCellValue(repString);
					 cell.setCellStyle(setBorder);
				 
				
				}
			rowNum=rowNum+1;
		}
			
		
		
		//java.io.FileOutputStream outputStream = new FileOutputStream(new File(basePath+"/"+templateName+"_"+userType+"1.xlsx"));
		
		return xssfWorkbook;
		
	}
	private String  getCellValue(String ex,Map<String,Object> resultMap,String module) throws OgnlException{
		String temp =ex;
		String  repString=ex;
		if(StringUtils.isNotEmpty(ex)){
		 for(int j=0;j<getExpressionCount(ex);j++){
			  String exString=getExpressionString(temp);
			  temp=temp.replace(exString, "");
			  String value =this.getValue(resultMap, exString);
			  String dataValue=getDataValueByKey(value,module,exString);
			  
			  if(value==null){
				  repString=repString.replace(exString, "");
				  
			  }else if(dataValue!=null){
				  repString=repString.replace(exString, dataValue);
			  }else{
				  repString=repString.replace(exString, value);
			  }
		  }
		 return repString;
	}
		return null;
	}
	
	private int writeToRow(XSSFSheet  xssfSheet,int rowNum,List<String> columList,Map<String,Object> resultmap,String module) throws OgnlException{
			XSSFRow  row=xssfSheet.getRow(rowNum);
			for(int i=0;i<columList.size();i++){
				
				String  ex=columList.get(i);
				String temp =ex;
				String  repString=ex;
				
				if(StringUtils.isNotEmpty(ex)){
				 for(int j=0;j<getExpressionCount(ex);j++){
					  String exString=getExpressionString(temp);
					  
					  if(exString.equals("#{no}")){
						  
						  repString=1+"";
					  }else{
						  
						  temp=temp.replace(exString, "");
						  String value =this.getValue(resultmap, exString);
						  String dataValue=getDataValueByKey(value,module,exString);
						  
						  if(value==null){
							  repString=repString.replace(exString, "");
							  
						  }else if(dataValue!=null){
							  repString=repString.replace(exString, dataValue);
						  }else{
							  repString=repString.replace(exString, value);
						  }
					  }
					  
					  
				  }
					row.getCell(i).setCellValue(repString);
				 
				}
				
			}
			
		return rowNum+1;
		
	}
	
	private String getValue(Map<String,Object> map,String ex) throws OgnlException{
		
		Map<Object,Object> obj=(Map<Object, Object>) Ognl.getValue(ex, map);
		
		Object value=obj.keySet().iterator().next();
		
		if(value!=null){
			
			return value.toString();
		}
		
		return null;
	}
	
	private  static int  getExpressionCount(String expr){
		
		int count=0;
	    Pattern p=Pattern.compile("\\#"); 
	        Matcher m=p.matcher(expr); 
	        while(m.find())
	        { 
	        	 count++;
	 	        
	        } 
	        return count;
	}

	
	
	
	private static String  getExpressionString(String expr){
		
		int start=0;
		int end=0;
	    Pattern p=Pattern.compile("\\#"); 
	        Matcher m=p.matcher(expr); 
	        while(m.find())
	        { 
	        	start=m.start();
	 	        
	        } 
	        Pattern pp=Pattern.compile("\\}"); 
	        Matcher mm=pp.matcher(expr); 
	        
	        while(mm.find())
	        { 
	         end=mm.start();
	          //System.out.println(mm.start());
	        } 
	        return expr.substring(start, end+1);
	}
	
	
	private String  getDataValueByKey(String key, String moduleName,String clname){
		
		
		String type =moduleName+"_"+clname.replace("#{", "").replace("}", "");
		
		String value=sysConstantEntityUtils.getValue(key, type);
		
		if(value!=null){
			
			
			return  value;
		}else{
			return null;
		}
		
	}
	
	  public static void main(String[] args) throws IOException, OgnlException {
		
		  String x="#{max_price}-#{min_price}";
		  
		  String temp = x;
		 
		  
		  for(int i=0;i<getExpressionCount(x);i++){
			  
			  String ex=getExpressionString(temp);
			  
			  temp=temp.replace(ex, "");
			  
			  System.out.println(ex);
		  }
		  
		  
		  
		  
	}
	@Override
	public BaseExpExcleEntity getExpExcelEntity(String module_code) {
		// TODO Auto-generated method stub
		return expExcleEntityMapper.getExpExcelEntity(module_code);
	}
}
