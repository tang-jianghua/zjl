package com.mfangsoft.zhuangjialong.common.utils;

import java.security.MessageDigest;

public class MD5Util {

	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将16进制字符串转换成字节数组
	 * 
	 * @param hex
	 * @return
	 */
	private static final String HEX_NUMS_STR = "0123456789ABCDEF";

	public static byte[] hexStringToByte(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] hexChars = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (HEX_NUMS_STR.indexOf(hexChars[pos]) << 4 | HEX_NUMS_STR.indexOf(hexChars[pos + 1]));
		}
		return result;
	}
	
	 /** 
     * 将指定byte数组转换成16进制字符串 
     * @param b 
     * @return 
     */  
    public static String byteToHexString(byte[] b) {  
        StringBuffer hexString = new StringBuffer();  
        for (int i = 0; i < b.length; i++) {  
            String hex = Integer.toHexString(b[i] & 0xFF);  
            if (hex.length() == 1) {  
                hex = '0' + hex;  
            }  
            hexString.append(hex.toUpperCase());  
        }  
        return hexString.toString();  
    }  
    
/**
 * 可逆加密
 * @param psw
 * @return
 */
	public static String KL(String psw) {
		char[] c = new char[psw.length()];
		for (int i = 0; i < psw.length(); i++) {
			c[i] = (char) (psw.charAt(i) ^ 'm');
		}
		return new String(c);
	}
/**
 * 解密
 * @param psw
 * @return
 */
	public static String LM(String psw) {
		char[] c = new char[psw.length()];
		for (int i = 0; i < psw.length(); i++) {
			c[i] = (char) (psw.charAt(i) ^ 'm');
		}
		return new String(c);
	}
	
	public static void main(String[] args) {
		System.out.println(MD5("111111"));
	}
}
