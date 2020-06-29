package com.mfangsoft.zhuangjialong.app.freeorder.util;

import java.sql.Time;
import java.util.Random;

import com.mfangsoft.zhuangjialong.common.utils.RedisUtils;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年11月11日
* 
*/

public class FreeOrderUtil {
      
	public final static int MAX_FREEORDER_PRICE=4800;//单价最高值
	public final static int MIN_FREEORDER_PRICE=900;//单价最低值
	public final static int MAX_FREEORDER_FIRSTORDERNUM=8;//09:00:00-17:59:59人数最高值
	public final static int MIN_FREEORDER_FIRSTORDERNUM=1;//09:00:00-17:59:59人数最低值
	public final static int MAX_FREEORDER_SECONDORDERNUM=5;//17:59:59-23:59:59人数最高值
	public final static int MIN_FREEORDER_SECONDORDERNUM=1;//17:59:59-23:59:59人数最低值
	public final static Time FREEORDER_TIME_1=new Time(9, 0, 0);
	public final static Time FREEORDER_TIME_2=new Time(17,59,59);
	public final static Time FREEORDER_TIME_3=new Time(23,59,59);
	public final static String FREE_ORDER_TOTAL_NUM="FREE_ORDER_TOTAL_NUM";
	public final static String FREE_ORDER_NUM="FREE_ORDER_NUM";
	public final static String FREE_ORDER_INI_TOTAL_NUM="347";
	public final static String FREE_ORDER_INI_NUM="1";
	/*
	 * 获取单价
	 */
	public static int getPrice(){
		Random random = new Random();
		return random.nextInt(MAX_FREEORDER_PRICE)%(MAX_FREEORDER_PRICE-MIN_FREEORDER_PRICE+1) + MIN_FREEORDER_PRICE;
	}
	/*
	 * 获取9:00-18:00人数
	 */
	public static int getFirstOrderNum(){
		Random random = new Random();
		return random.nextInt(MAX_FREEORDER_FIRSTORDERNUM)%(MAX_FREEORDER_FIRSTORDERNUM-MIN_FREEORDER_FIRSTORDERNUM+1) + MIN_FREEORDER_FIRSTORDERNUM;
	}
	/*
	 * 获取18:00-24:00人数
	 */
	public static int getSecondOrderNum(){
        Random random = new Random();
        return random.nextInt(MAX_FREEORDER_SECONDORDERNUM)%(MAX_FREEORDER_SECONDORDERNUM-MIN_FREEORDER_SECONDORDERNUM+1) + MIN_FREEORDER_SECONDORDERNUM;
	}
	
	
	public static void main(String[] args) {
		System.out.println(getPrice());
		System.out.println(getFirstOrderNum());
		System.out.println(getSecondOrderNum());
	}
}
