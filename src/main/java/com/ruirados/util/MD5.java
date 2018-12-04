package com.ruirados.util;

/**
 *MD5加密
 *
 */

import java.security.*;
import java.util.LinkedHashMap;
import java.util.Map; 
public final class MD5 {
	
	public final static String MD5(String s){ 
		char hexDigits[] = { 
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 
		'e', 'f'}; 
		try { 
			byte[] strTemp = s.getBytes(); 
			MessageDigest mdTemp = MessageDigest.getInstance("MD5"); 
			mdTemp.update(strTemp); 
			byte[] md = mdTemp.digest(); 
			int j = md.length; 
			char str[] = new char[j * 2]; 
			int k = 0; 
			for (int i = 0; i < j; i++) { 
				byte byte0 = md[i]; 
				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; 
				str[k++] = hexDigits[byte0 & 0xf]; 
			} 
			return new String(str).toUpperCase(); 
		} 
		catch (Exception e){ 
			return null; 
		} 
	}
/*	public static void main(String[] args) {

		Map m = new LinkedHashMap();
 
		m.put("vpcid", "4d8aec68-5ec3-41b9-baa2-9c80c86aa07d ");

		try {
			String url = CloudStackHelper.associateIpAddress(m);
			 
			 System.out.println(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}*/
}
