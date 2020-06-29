package com.mfangsoft.zhuangjialong.integration.enums;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2017年1月12日
* 
*/

public enum ShopGuiderCertificationState {

	
	NoCommit("未提交",0),Checking("审核中",1),Pass("已通过",2),NoPass("未通过",3);
	
	private String name;
	
	private Integer index;
	
	private ShopGuiderCertificationState(String name,Integer index){
		
		this.name = name;
        this.index = index;
	}
	
	
	public String getName()
	{
		return this.name;
	}
	
	public Integer getIndex()
	{
		
		return this.index;
	}
}
