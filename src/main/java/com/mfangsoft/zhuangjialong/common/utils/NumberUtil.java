package com.mfangsoft.zhuangjialong.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;

public class NumberUtil {

	/**
	 * 生成6位随机数验证码
	 * 去掉 0 1 等容易混淆的
	 * @return
	 */
	public final static String SixNumber() {
		String[] list = { "2", "3", "4", "5", "6", "7", "8", "9" };
		Random random = new Random();
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < 6; i++) {
			str.append(list[random.nextInt(8)]);
		}
		return str.toString();
	}

	/**
	 * 生成4位随机数验证码
	 * 
	 * @return
	 */
	public final static String fourNumberAll() {
		String[] list = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		Random random = new Random();
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			str.append(list[random.nextInt(8)]);
		}
		return str.toString();
	}

	// 1467285733838
	static long preTime;

	public static NumberUtil ins = new NumberUtil();

	public static NumberUtil getIns() {
		return ins;
	}

	/**
	 * 生成订单号
	 * 
	 * @return
	 */
	public String code() {
		String rand = "";
		synchronized (ins) {
			long currentTime = System.currentTimeMillis();
			if (currentTime <= preTime) {
				preTime++;
			} else {
				preTime = currentTime;
			}
			rand = fourNumberAll();
		}
		return Long.toString(preTime) + rand;
	}

	public static void main(String[] args) {
//		for (int i = 0; i < 10; i++) {
//			new Thread(new Runnable() {
//				int k = 20;
//
//				@Override
//				public void run() {
//					while (k > 0) {
//						System.out.println(Thread.currentThread().getName() + "----" + NumberUtil.getIns().code() + "-"
//								+ System.currentTimeMillis());
//						k++;
//					}
//				}
//			}).start();
//		}
		System.out.println(round(2343.30054, 2 , BigDecimal.ROUND_HALF_UP));
		double f = 111231.50005D;
		DecimalFormat df = new DecimalFormat("#.00");  
       
		System.out.println(round2(null)); 
        
	}
	
	  /**
	   * 提供精确的小数位四舍五入处理
	   * @param v 需要四舍五入的数字
	   * @param scale 小数点后保留几位
	   * @param round_mode 指定的舍入模式
	   * ROUND_HALF_DOWN 
	   * ROUND_HALF_UP
	   * @return 四舍五入后的结果
	   */
	  public static double round(double v, int scale, int round_mode)
	  {
	     if(scale<0)
	     {
	         throw new IllegalArgumentException("The scale must be a positive integer or zero");
	     }
	     BigDecimal b = new BigDecimal(Double.toString(v));
	     return b.setScale(scale, round_mode).doubleValue();
	  }
	  
	  public static String round2(Double v) {  
		  
		  if(v == null){
			  return "0.00";
		  }
	      return String.format("%.2f", v);
      }  
}

