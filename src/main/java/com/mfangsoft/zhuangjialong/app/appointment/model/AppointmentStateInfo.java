package com.mfangsoft.zhuangjialong.app.appointment.model;

import java.text.ParseException;

import com.mfangsoft.zhuangjialong.common.utils.DateUtils;

public class AppointmentStateInfo implements Comparable{
	private String state;
	private String time;
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public int compareTo(Object o) {
		AppointmentStateInfo appointmentStateInfo = (AppointmentStateInfo)o;
		if(this.time != null && appointmentStateInfo != null && appointmentStateInfo.getTime() != null){
			try {
				return (int)(DateUtils.parseDate_(this.time).getTime() - DateUtils.parseDate_(appointmentStateInfo.getTime()).getTime()) > 0? -1 : 1;
			} catch (ParseException e) {
				e.printStackTrace();
				return 0;
			}
		}
		return 0;
	}
}
