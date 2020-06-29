package com.mfangsoft.zhuangjialong.app.charge.service;

import com.mfangsoft.zhuangjialong.app.charge.model.ChargeBackEntity;
import com.mfangsoft.zhuangjialong.app.charge.model.ChargeBatchEntity;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年10月28日
* 
*/

public interface ChargeService {

	/*
	 * 单个手机充话费
	 */
	public boolean addChargeBatch(int charge_package,String order_id,String mobile);
	
	
	/*
	 * 充值状态推送
	 */
	public String addChargeBack(ChargeBackEntity chargeBackEntity);
	
	
	
	
}
