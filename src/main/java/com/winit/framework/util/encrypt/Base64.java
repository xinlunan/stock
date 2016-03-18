package com.winit.framework.util.encrypt;

import java.io.IOException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64 {

	@SuppressWarnings("restriction")
	public static String encrypt(String s) {
		
		return new BASE64Encoder().encode(s.getBytes());
	}
	
	@SuppressWarnings("restriction")
	public static String decrypt(String s)  {
		
		if(s==null || s.isEmpty()) return s;
		
		try {
			byte[] inputData = (new BASE64Decoder()).decodeBuffer(s);
			return new String(inputData);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}
