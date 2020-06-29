package com.mfangsoft.zhuangjialong.common.utils;

public class TokenUtil {

	static long preTime;

	public static TokenUtil ins = new TokenUtil();

	public static TokenUtil getIns() {
		return ins;
	}

	public String getAndIncreaseKey() {

		synchronized (ins) {
			long currentTime = System.currentTimeMillis();
			if (currentTime <= preTime) {
				preTime++;
			}else{
				preTime = currentTime;
			}
		}
		return MD5Util.MD5(Long.toString(preTime));
	}

	public String getToken(String str) {
		return MD5Util.MD5(str);
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				int k = 20;

				@Override
				public void run() {
					while (k > 0) {
						System.out.println(Thread.currentThread().getName()+ "----" + TokenUtil.getIns().getAndIncreaseKey() + "-" + System.currentTimeMillis());
						k++;
					}
				}
			}).start();
		}
	}
}
