package com.mfangsoft.zhuangjialong.integration.partner.model;

public class PartnerModle {

    private Long id;

    /**
     *   负责人
     */
    private String principal;

    /**
     *   联系方式
     */
    private String phone_num;

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getPhone_num() {
		return phone_num;
	}

	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    
}
