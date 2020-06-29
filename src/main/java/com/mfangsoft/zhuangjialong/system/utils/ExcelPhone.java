package com.mfangsoft.zhuangjialong.system.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mfangsoft.zhuangjialong.common.utils.HttpClientUtil;

public class ExcelPhone {
	
	
	public static void main(String[] args) throws IOException {
		
		
		java.io.FileInputStream o= new FileInputStream(new File("E:\\c\\ExcelFile_2016-11-07T11_19_30.xlsx"));
		
		XSSFWorkbook  workbook = new XSSFWorkbook(o);
		
		
		XSSFSheet s=workbook.getSheetAt(0);
		
		for(int i=1;i<s.getLastRowNum();i++){
			
		    XSSFRow row	=s.getRow(i);
		    
		    XSSFCell xssfCell=row.getCell(3);
		   String sys=xssfCell.getStringCellValue();
		   
		   
		   URI uri=null;
		try {
			uri = new URI("http://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel="+sys);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   String str=HttpClientUtil.sendGetForJson(uri);
		   
		   ObjectMapper  mapper  = new ObjectMapper();
		   
		   if(str.indexOf("mts")!=-1){
		   
		   
		  str= str.replaceAll(" ", "");
		  
		  str=str.replaceAll("_", "");
		  
		  str=str.replaceAll("GetZoneResult", "");
		  
		  str= str.replaceAll("=", "");
		   
		  str=str.replaceAll("'", "\"");
		  
		 // str=str.replaceAll("{", "{\"");
		  str=str.replaceAll(":", "\":");
		  str=str.replaceAll(",", ",\"");
		  
		  str=str.replaceAll("mts", "\"mts");
		  
		  
		  str=str.replaceAll ("\r\n", "");
		  
		  str=str.replaceAll("  ", "");
		  
		  str=str.replaceAll("	", "");
		  System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+str);
		  Map<String,Object> map =mapper.readValue(str, Map.class);
		  
		  if(map.get("catName")!=null&&map.get("province")!=null){
			  XSSFCell  se =row.createCell(9);
			   se.setCellValue(map.get("province").toString()+map.get("catName").toString());
			    
		  
		  }
		  
		 
		   System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+str);
		   }
		}
		
		java.io.FileOutputStream out= new FileOutputStream(new File("E:\\c\\ExcelFile_2016-11-07T11_19_31.xlsx")); 
		   
		   workbook.write(out);
		
		
	}

}
