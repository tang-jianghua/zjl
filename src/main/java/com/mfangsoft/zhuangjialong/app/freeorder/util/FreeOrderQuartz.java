package com.mfangsoft.zhuangjialong.app.freeorder.util;

import java.sql.Time;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import com.mfangsoft.zhuangjialong.app.applogin.service.impl.CustomerServiceImpl;
import com.mfangsoft.zhuangjialong.common.utils.DateUtils;
import com.mfangsoft.zhuangjialong.common.utils.RedisUtils;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年11月11日
* 
*/

public class FreeOrderQuartz {
	
	public boolean updateFreeOrderTotalNum() throws ParseException {
		Date start_time = DateUtils.parseDate_(CustomerServiceImpl.START_TIME);
		Date end_time = DateUtils.parseDate_(CustomerServiceImpl.END_TIME);
		Date date = new Date();
		  if(date.getTime()>=start_time.getTime() && date.getTime()<=end_time.getTime()){
				
			  String freeOrderTotalNum = RedisUtils.getValue(FreeOrderUtil.FREE_ORDER_TOTAL_NUM);
		String freeOrderNum = RedisUtils.getValue(FreeOrderUtil.FREE_ORDER_NUM);
		if(freeOrderTotalNum==null||freeOrderNum==null){
			return false;
		}
		Long fn=Long.parseLong(freeOrderTotalNum);
		Long fnm=Long.parseLong(freeOrderNum);
		Integer orderNum=null;
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		Time time=new Time(hour, minute, second);
		int t1 = time.compareTo(FreeOrderUtil.FREEORDER_TIME_1);
		int t2 = time.compareTo(FreeOrderUtil.FREEORDER_TIME_2);
		int t3 = time.compareTo(FreeOrderUtil.FREEORDER_TIME_3);
		if(t1>=0&&t2<=0){
			orderNum = FreeOrderUtil.getFirstOrderNum();
			for (int i = 0; i < orderNum; i++) {
				fn+=FreeOrderUtil.getPrice();
			}
		}else if(t2>0&&t3<=0){
			orderNum = FreeOrderUtil.getSecondOrderNum();
			for (int i = 0; i < orderNum; i++) {
				fn+=FreeOrderUtil.getPrice();
			}
		}
		fnm+=orderNum;
		RedisUtils.setValue(FreeOrderUtil.FREE_ORDER_TOTAL_NUM, fn.toString());
		RedisUtils.setValue(FreeOrderUtil.FREE_ORDER_NUM, fnm.toString());
		  }
		return true;
	}
	
	
}
