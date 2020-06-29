package com.mfangsoft.zhuangjialong.integration.enums;

public enum UserState {
	
	OPEN("开启",1), CLOSE("关闭",2);
	
	
	private String name;
	
	private Integer index;
	
	public String getName() {
		return name;
	}




	public Integer getIndex() {
		return index;
	}




	private UserState(String name,Integer index){
		
		this.name = name;
        this.index = index;
	}
	
	
	
	
	private String getName(Integer key){
		
		switch (key) {
		case 1:
			return UserState.OPEN.name;
		case 2:
			return UserState.CLOSE.name;

		default:
			return null;
		}
		
	}
	
	public static void main(String[] args) {
		
		System.out.println(UserState.OPEN.name);
		System.out.println(UserState.OPEN.getName());
		
		System.out.println(UserState.OPEN.getIndex());
		
		
	}
	
}
