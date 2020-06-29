package com.mfangsoft.zhuangjialong.integration.constructor.model;

import java.util.List;

public class Constructor {
	
	
	private  String name;	
	private  List<Integer> Regions_id;	
	private  String  unit_name;
	private  String phone_num;	
	
	private Authentication auth;
	
	
	public Authentication getAuth() {
		return auth;
	}
	public void setAuth(Authentication auth) {
		this.auth = auth;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Integer> getRegions_id() {
		return Regions_id;
	}
	public void setRegions_id(List<Integer> regions_id) {
		Regions_id = regions_id;
	}
	public String getUnit_name() {
		return unit_name;
	}
	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
	}
	public String getPhone_num() {
		return phone_num;
	}
	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}
	
	


}
