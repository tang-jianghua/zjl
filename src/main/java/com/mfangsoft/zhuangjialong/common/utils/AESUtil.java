package com.mfangsoft.zhuangjialong.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年9月23日
* 
*/

public class AESUtil {
	/** 
	 * 加密 
	 *  
	 * @param content 需要加密的内容 
	 * @param password  加密密码 
	 * @return 
	 */  
	public static String aesEncrypt(String str, String password) {
		if (str == null || password == null)
			return null;
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE,
					new SecretKeySpec(password.getBytes("utf-8"), "AES"));
			byte[] bytes = cipher.doFinal(str.getBytes("utf-8"));
			return org.apache.commons.codec.binary.Base64.encodeBase64String(bytes);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
    
    /**将二进制转换成16进制 
     * @param buf 
      * @return 
      */   
     public static String parseByte2HexStr(byte buf[]) {   
             StringBuffer sb = new StringBuffer();   
             for (int i = 0; i < buf.length; i++) {   
                     String hex = Integer.toHexString(buf[i] & 0xFF);   
                     if (hex.length() == 1) {   
                             hex = '0' + hex;   
                     }   
                     sb.append(hex.toUpperCase());   
             }   
             return sb.toString();   
     }   
      
     
     
     /**解密 
      * @param content  待解密内容 
      * @param password 解密密钥 
      * @return 
       */   
     public static String aesDecrypt(String str, String password){
 		if (str == null || password == null)
 			return null;
 		try {
 			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
 			cipher.init(Cipher.DECRYPT_MODE,
 					new SecretKeySpec(password.getBytes("utf-8"), "AES"));
 			byte[] bytes = Base64.decodeBase64(str);
 			bytes = cipher.doFinal(bytes);
 			return new String(bytes, "utf-8");
 		} catch (Exception e) {
 			e.printStackTrace();
 			return null;
 		}
 	}	
      
      /**将16进制转换为二进制 
       * @param hexStr 
        * @return 
        */   
       public static byte[] parseHexStr2Byte(String hexStr) {   
               if (hexStr.length() < 1)   
                       return null;   
               byte[] result = new byte[hexStr.length()/2];   
               for (int i = 0;i< hexStr.length()/2; i++) {   
                       int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);   
                       int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);   
                       result[i] = (byte) (high * 16 + low);   
               }   
               return result;   
       }   
	
}
