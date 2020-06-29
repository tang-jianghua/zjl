package com.mfangsoft.zhuangjialong.app.sendflow.utils;

import java.util.Random;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年9月24日
* 
*/

public class FlowPackageUtil {
	final static Long CUCC20=9L;//联通20M流量包产品id
	final static Long CUCC50=11L;//联通50M流量包产品id
	final static Long CTCC10=8L;//移动电信10M流量包产品id
	final static Long CTCC30=10L;//移动电信30M流量包产品id
	final static Long CTCC50=11L;//电信50M流量包产品id
	final static Long CMCC10=8L;//移动电信10M流量包产品id
	final static Long CMCC30=10L;//移动电信30M流量包产品id
	final static Long[] CUCC = {CUCC20,CUCC20,CUCC20,CUCC20,CUCC20,CUCC20,CUCC20,CUCC20,CUCC20,CUCC50};
	final static Long[] CTCC = {CTCC10,CTCC10,CTCC10,CTCC10,CTCC10,CTCC10,CTCC10,CTCC10,CTCC10,CTCC10,CTCC10,CTCC10,CTCC10,CTCC10,CTCC10,CTCC30,CTCC30,CTCC30,CTCC30,CTCC50};
	final static Long[] CMCC = {CMCC10,CMCC10,CMCC10,CMCC10,CMCC10,CMCC10,CMCC10,CMCC10,CMCC10,CMCC10,CMCC10,CMCC10,CMCC10,CMCC10,CMCC10,CMCC30,CMCC30,CMCC30,CMCC30,CTCC30};
	
	public static Long getCucc(){
		Random random = new Random();
		return CUCC[random.nextInt(CUCC.length)];
	}
	public static Long getCtcc(){
		Random random = new Random();
		return CTCC[random.nextInt(CTCC.length)];
	}
	public static Long getCmcc(){
		Random random = new Random();
		return CMCC[random.nextInt(CMCC.length)];
	}
	public static void main(String[] args) {
		System.out.println(CUCC.length);
		System.out.println(getCucc());
	}
}
