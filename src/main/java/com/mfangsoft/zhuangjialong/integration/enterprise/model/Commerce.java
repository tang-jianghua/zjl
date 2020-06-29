package com.mfangsoft.zhuangjialong.integration.enterprise.model;

public class Commerce implements java.io.Serializable{
	
	
	/**
	 * 营业执照号
	 */
	private String biz_num;
	
	/**
	 * 营业执图
	 */
	private String biz_url;	
	/**
	 * 开户许可证号
	 */
	private String open_account;	
	/**
	 * 开户许可证图
	 */
	private String open_account_url;
	
	private String tax_num;
	
	public String getTax_num() {
		return tax_num;
	}
	public void setTax_num(String tax_num) {
		this.tax_num = tax_num;
	}
	public String getTax_num_url() {
		return tax_num_url;
	}
	public void setTax_num_url(String tax_num_url) {
		this.tax_num_url = tax_num_url;
	}
	private String tax_num_url;

	
	
	public String getBiz_num() {
		return biz_num;
	}
	public void setBiz_num(String biz_num) {
		this.biz_num = biz_num;
	}
	public String getBiz_url() {
		return biz_url;
	}
	public void setBiz_url(String biz_url) {
		this.biz_url = biz_url;
	}
	public String getOpen_account() {
		return open_account;
	}
	public void setOpen_account(String open_account) {
		this.open_account = open_account;
	}
	public String getOpen_account_url() {
		return open_account_url;
	}
	public void setOpen_account_url(String open_account_url) {
		this.open_account_url = open_account_url;
	}	
	
	
	
	

}
