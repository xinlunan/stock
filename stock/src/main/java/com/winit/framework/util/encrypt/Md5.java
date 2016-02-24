package com.winit.framework.util.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {
	public static String encrypt(String text) {
		StringBuffer buf = new StringBuffer("");
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(text.getBytes());
			byte b[] = md.digest();

			int i;

			
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}


		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}
		
		return buf.toString();
	}

	public static void main(String[] s) {
		System.out.println(encrypt("fsdafeg"));
	}

}
