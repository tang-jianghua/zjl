package com.mfangsoft.zhuangjialong.integration.excelexp.model;

import java.io.Serializable;
import java.util.Map;

import com.mfangsoft.zhuangjialong.common.model.Page;

public class ExportModel implements Serializable {
	
	
	
	private String module_code;
	
	private Page<Map<String, Object>> page;

	public String getModule_code() {
		return module_code;
	}

	public void setModule_code(String module_code) {
		this.module_code = module_code;
	}

	public Page<Map<String, Object>> getPage() {
		return page;
	}

	public void setPage(Page<Map<String, Object>> page) {
		this.page = page;
	}
	
	

}
