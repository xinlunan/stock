package com.xu.util;

import com.google.gson.Gson;

public class JsonFormat {
	/**
	 * 将json字符串转换为java对象
	 * 
	 * @param jsonStr
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T jsonToJava(String jsonStr, T obj) {
		Gson gson = new Gson();
		obj = (T) gson.fromJson(jsonStr, obj.getClass());
		return obj;
	}
}