package com.cz.txl.util;

import java.security.MessageDigest;
import sun.misc.BASE64Encoder;

public class EncryptUtil {
	//该方法将传入的无加密文本，使用md5算法进行加密
	public static String encryptMd5(String password){
		String result = "";
		try {
			//消息摘要
			MessageDigest md5 =
					MessageDigest.getInstance("MD5");

			byte[] temp =
					md5.digest(password.getBytes("utf-8"));

			BASE64Encoder base64en = new BASE64Encoder();

			result = base64en.encode(temp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
