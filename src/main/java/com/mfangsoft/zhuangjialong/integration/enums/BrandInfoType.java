package com.mfangsoft.zhuangjialong.integration.enums;

public enum BrandInfoType {
	
COMPANY_PROFILE("公司简介",0),DEVELOPMENT_HISTORY("发展历程",1),ENGINEERING_CASE("工程案例",2),BRAND_HONOR("品牌荣誉",3),STORE_STYLE("店面风采",4);


private String name;
private Integer index;


public String getName() {
	return name;
}


public Integer getIndex() {
	return index;
}


private BrandInfoType(String name,Integer index)
{
	this.index=index;
	this.name= name;
}
	
public String getName(Integer key){
	
	switch (key) {
	case 0:
		return BrandInfoType.COMPANY_PROFILE.name;
	case 1:
		return BrandInfoType.DEVELOPMENT_HISTORY.name;
	case 2:
		return BrandInfoType.ENGINEERING_CASE.name;
	case 3:
		return BrandInfoType.BRAND_HONOR.name;
	case 4:
		return BrandInfoType.STORE_STYLE.name;
	default:
		return null;
	}
	
}
}
