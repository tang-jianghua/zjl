package com.mfangsoft.zhuangjialong.app.sendflow.service;

import com.mfangsoft.zhuangjialong.app.sendflow.model.CallBackModel;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年9月23日
* 
*/

public interface SendFlowService {
	//成功
	final static  Integer SUCCESS=1;
	//余额不足
	final static  Integer FAILURE=2;
	//余额不足
	final static  Integer NOT_ENOUGH=3;
	
	
	/*
	 * 发送流量
	 * 
	 * param:
	 *phone  要发送的 手机号,只发一个号码：13800000001。发多个号码：13800000001,13800000002,...N 。使用半角逗号分隔。
	 *taskNo 
	 *
	 */
	public  Integer InsertSendFlow(String mobiles,String taskNo,String ctcc,String cucc,String cmcc);
  	
	/*
	 * 流量回调
	 * 
	 * param:
	 *phone  要发送的 手机号,只发一个号码：13800000001。发多个号码：13800000001,13800000002,...N 。使用半角逗号分隔。
	 *taskNo 
	 *
	 */
	public  String InsertFlowBack(CallBackModel backModel );

	

}
