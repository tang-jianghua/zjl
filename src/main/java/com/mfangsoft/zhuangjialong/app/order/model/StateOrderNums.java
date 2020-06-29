package com.mfangsoft.zhuangjialong.app.order.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class StateOrderNums {

	private int  needpayNum;
	private int  needDeliveryNum;
	private int  needReciveNum;
	private int  needCommentNum;
	private int  successNum;
	private int  cancleNum;
	private int  deletedNum;
	
	private int  pointNum;;
	
	public int getPointNum() {
		return pointNum;
	}
	public void setPointNum(int pointNum) {
		this.pointNum = pointNum;
	}
	public Integer getNeedpayNum() {
		return needpayNum;
	}
	public void setNeedpayNum(Integer needpayNum) {
		this.needpayNum = needpayNum;
	}
	public Integer getNeedDeliveryNum() {
		return needDeliveryNum;
	}
	public void setNeedDeliveryNum(Integer needDeliveryNum) {
		this.needDeliveryNum = needDeliveryNum;
	}
	public Integer getNeedReciveNum() {
		return needReciveNum;
	}
	public void setNeedReciveNum(Integer needReciveNum) {
		this.needReciveNum = needReciveNum;
	}
	public Integer getNeedCommentNum() {
		return needCommentNum;
	}
	public void setNeedCommentNum(Integer needCommentNum) {
		this.needCommentNum = needCommentNum;
	}
	public Integer getSuccessNum() {
		return successNum;
	}
	public void setSuccessNum(Integer successNum) {
		this.successNum = successNum;
	}
	public Integer getCancleNum() {
		return cancleNum;
	}
	public void setCancleNum(Integer cancleNum) {
		this.cancleNum = cancleNum;
	}
	public Integer getDeletedNum() {
		return deletedNum;
	}
	public void setDeletedNum(Integer deletedNum) {
		this.deletedNum = deletedNum;
	}
	
}
