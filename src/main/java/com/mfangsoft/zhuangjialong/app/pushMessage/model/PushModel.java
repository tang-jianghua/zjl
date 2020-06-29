package com.mfangsoft.zhuangjialong.app.pushMessage.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年9月28日
* 
*/
@JsonInclude
public class PushModel {

	//系统类型
	private String PlatType;
	
	//设备号
	private String mobileNo;
	
	//消息头
	private String title;
	
	//消息内容
	private String content;

	/**
	 * @return the platType
	 */
	public String getPlatType() {
		return PlatType;
	}

	/**
	 * @param platType the platType to set
	 */
	public void setPlatType(String platType) {
		PlatType = platType;
	}

	/**
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * @param mobileNo the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	
	
	
}
