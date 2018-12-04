package com.ruirados.utils;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MyJSON {
	
	public static Gson gson = new Gson();
	public static String toJson(Object src){
		return gson.toJson(src);
	}
	
	public static Object toCustom(String json,TypeToken<?> typeToken){
		return gson.fromJson(json,typeToken.getType());
	}
}
