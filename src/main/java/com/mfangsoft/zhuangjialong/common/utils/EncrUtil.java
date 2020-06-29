package com.mfangsoft.zhuangjialong.common.utils;

import java.math.BigInteger;

/**
 * 可逆加密算法
 * @author Administrator
 *
 */
public class EncrUtil {
	private static final int RADIX = 36;  
    private static final String SEED = "1556862388778025535336";  
  
    public static final String encrypt(String value) {  
        if (value == null)  
            return "";  
        if (value.length() == 0)  
            return "";  
  
        BigInteger bi_passwd = new BigInteger(value.getBytes());  
  
        BigInteger bi_r0 = new BigInteger(SEED);  
        BigInteger bi_r1 = bi_r0.xor(bi_passwd);  
  
        return bi_r1.toString(RADIX);  
    }  
  
    public static final String decrypt(String encrypted) {  
        if (encrypted == null)  
            return "";  
        if (encrypted.length() == 0)  
            return "";  
  
        BigInteger bi_confuse = new BigInteger(SEED);  
  
        try {  
            BigInteger bi_r1 = new BigInteger(encrypted, RADIX);  
            BigInteger bi_r0 = bi_r1.xor(bi_confuse);  
  
            return new String(bi_r0.toByteArray());  
        } catch (Exception e) {  
            return "";  
        }  
    }  
  
    public static void main(String args[]){  
        String str = "12364086362";
        String en = "C" + encrypt(str);
        String de = decrypt(en.substring(1));
            System.out.println(en);  
            System.out.println(de);  
        }  
}
