package com.mfangsoft.zhuangjialong.app.personalCenter.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class PartnerSendMessageModel {

    private String account;

    private List<String> account_array;
    
    private String title;

    private String content;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public List<String> getAccount_array() {
		return account_array;
	}

	public void setAccount_array(List<String> account_array) {
		this.account_array = account_array;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
    
    

}
