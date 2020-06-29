package com.mfangsoft.zhuangjialong.integration.appointment.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.mfangsoft.zhuangjialong.common.utils.DateUtils;

public class AppointmentState {

	//1、已提交  2,已受理  3、受理中4、已完成5、已取消 
	
//新	1、已提交 2 已确认 3、受理中 4、已完成 5、已取消  6 无法受理
	
	public static int statecode1 = 1;
	
	public static int statecode2 = 2;
	
	public static int statecode3 = 3;
	
	public static int statecode4 = 4;
	
	public static int statecode5 = 5;
	
	public static int statecode6 = 6;
	
	public static String splitChar = ";";
	
	public static String state1 = "服务信息已上传，等待受理 T";
	
	public static String state2 = "您的预约已确认，由【%2s】店铺为您服务 T" ;
	
	public static String state3 = "您的预约，已分配服务专员【%3s】为您服务，联系电话【%3d】 T";
	
	public static String state4 = "已完成。感谢您的信任，我们会做更好的服务！ T";
	
	public static String state5 = "您的预约服务已取消成功 T";
	
	public static String state6 = "您的预约服务已更换为【%6s】为您服务 T";
	
	
	public static void main(String[] args) {
		System.out.println(state5);
	}
	
}
