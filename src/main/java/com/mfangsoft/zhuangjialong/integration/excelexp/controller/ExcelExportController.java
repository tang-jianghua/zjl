package com.mfangsoft.zhuangjialong.integration.excelexp.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.ognl.OgnlException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.integration.excelexp.model.BaseExpExcleEntity;
import com.mfangsoft.zhuangjialong.integration.excelexp.model.ExportModel;
import com.mfangsoft.zhuangjialong.integration.excelexp.service.ExpExcleService;

@Controller
@RequestMapping("/server")
public class ExcelExportController {

	@Autowired
	private ExpExcleService expExcleService;
	
	@RequestMapping(value="/export", method=RequestMethod.POST)
	public void  excelExport(String module_code, String page,HttpServletRequest request,HttpServletResponse response){
		
		
		try {
			
			ExportModel exportModel = new ExportModel();
			
			exportModel.setModule_code(module_code);
			
			
			ObjectMapper objectMapper = new ObjectMapper();
			
			
			Page<Map<String, Object>> pageo=objectMapper.readValue(page, Page.class);
			
			exportModel.setPage(pageo);
			
			XSSFWorkbook workbook =expExcleService.getHSSFWorkbook(exportModel.getModule_code(), exportModel.getPage());
			
			
			
			if(workbook!=null){
			
				ServletOutputStream outputStream =response.getOutputStream();
				BaseExpExcleEntity baseExpExcleEntity=expExcleService.getExpExcelEntity(exportModel.getModule_code());
				response.addHeader("Content-Disposition", "attachment;filename="+ new String((baseExpExcleEntity.getModule_name() + ".xlsx").getBytes(), "iso-8859-1"));
						            //response.addHeader("Content-Length", "" + file.length());
						             response.setContentType("application/ms-excel;charset=UTF-8");
				
				
				workbook.write(outputStream);
				outputStream.close();
				outputStream.flush();
				
			}else{
				
				response.getWriter().print("<script type='text/javascript'>alert('当前导出暂无数据！');window.history.go(-1);</script>");
				//outputStream.print("3333");
			}
			
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OgnlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
