package com.mfangsoft.zhuangjialong.integration.enums;

public enum UserType {
	


    PLATFORM("平台商",1), ENTERPRISE("企业",2),PARTNER("城市合伙人",3), BRAND("品牌",4),SHOP("实体店铺",5),BACKSTAGE("后台用户",0);

	
	private String name;
	
	private Integer index;
	
	private UserType(String name,Integer index){
		
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
	
	public String getName(Integer key){
		
		switch (key) {
		case 1:
			return UserType.PLATFORM.name;
		case 2:
			return UserType.ENTERPRISE.name;
		case 3:
			return UserType.PARTNER.name;
		case 4:
			return UserType.BRAND.name;
		case 5:
			return UserType.SHOP.name;
		case 0:
			return UserType.BACKSTAGE.name;

		default:
			return null;
		}
		
	}

}
