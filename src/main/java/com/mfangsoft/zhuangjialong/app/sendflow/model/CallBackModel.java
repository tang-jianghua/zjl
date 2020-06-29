package com.mfangsoft.zhuangjialong.app.sendflow.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年9月23日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class CallBackModel {
      
	
	private String batchNo;  //批次号 
	
	private Integer successCount;  //成功数量 
	
	private Integer failCount; //失败数量 
	
	private List<CallBackMobile> errorlist;  //号码列表 如没有失败 errorlist 为空 

	/**
	 * @return the batchNo
	 */
	public String getBatchNo() {
		return batchNo;
	}

	/**
	 * @param batchNo the batchNo to set
	 */
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}


	/**
	 * @return the successCount
	 */
	public Integer getSuccessCount() {
		return successCount;
	}

	/**
	 * @param successCount the successCount to set
	 */
	public void setSuccessCount(Integer successCount) {
		this.successCount = successCount;
	}

	/**
	 * @return the failCount
	 */
	public Integer getFailCount() {
		return failCount;
	}

	/**
	 * @param failCount the failCount to set
	 */
	public void setFailCount(Integer failCount) {
		this.failCount = failCount;
	}

	/**
	 * @return the errorlist
	 */
	public List<CallBackMobile> getErrorlist() {
		return errorlist;
	}

	/**
	 * @param errorlist the errorlist to set
	 */
	public void setErrorlist(List<CallBackMobile> errorlist) {
		this.errorlist = errorlist;
	}
	
	
}
