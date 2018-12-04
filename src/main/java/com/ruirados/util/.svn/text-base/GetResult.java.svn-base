package com.ruirados.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

public class GetResult {
	public static Integer SUCCESS_STATUS = 1;
	public static Integer ERROR_STATUS = 2;
	public static Integer WARN_STATUS = 10;
	public static Integer ERROR_NO_MONEY = 4;
	public static Integer NOT_LOGIN = 3;
	
	public static void main(String[] args) {

	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> isCorrect(String message) {

		HashMap<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("status", ERROR_STATUS);
		result.put("message", "cloudstack return null!");
		if("".equals(message) || message == null){
			return result;
		}
		
		Gson gson = new Gson();
		HashMap<String, HashMap<String, Object>> jsonObj = gson.fromJson(message,
				new TypeToken<HashMap<String, HashMap<String, Object>>>() {
				}.getType());
		HashMap<String, Object> temp = new HashMap<String, Object>();
		LinkedTreeMap<String, Object> messagetemp = new LinkedTreeMap<String, Object>();
	
		temp = jsonObj.get("queryasyncjobresultresponse");
		Double status = (Double) temp.get("jobstatus");
		status.intValue();
		if (status.intValue() == ERROR_STATUS) {
			result.put("status", ERROR_STATUS);
			messagetemp = (LinkedTreeMap<String, Object>) temp.get("jobresult");
			result.put("message", messagetemp.get("errortext"));
		} else {
			result.put("message", "");
			result.put("status", SUCCESS_STATUS);
		}
		//System.out.println(result);
		return result;
	}

	public static Map<String, Object> containJobId(String json, String rootKey) {

		Gson gson = new Gson();
		HashMap<String, HashMap<String, Object>> jsonObj = gson.fromJson(json,
				new TypeToken<HashMap<String, HashMap<String, Object>>>() {
				}.getType());
		HashMap<String, Object> result = new LinkedHashMap<String, Object>();
		HashMap<String, Object> temp = jsonObj.get(rootKey);
		if (temp.containsKey("errorcode")) {
			result.put("status", ERROR_STATUS);
			result.put("message", temp.get("errortext"));
		} else {
			result.put("status", SUCCESS_STATUS);
			result.put("message", temp.get("jobid"));
		}
		return result;
	}

	public static Map<String, Object> unContainJobId(String json, String rootKey) {
		HashMap<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("status", ERROR_STATUS);
		result.put("message", "cloudstack return null!");
		if("".equals(json) || json == null){
			return result;
		}
		Gson gson = new Gson();
		HashMap<String, HashMap<String, Object>> jsonObj = gson.fromJson(json,
				new TypeToken<HashMap<String, HashMap<String, Object>>>() {
				}.getType());
		HashMap<String, Object> temp = jsonObj.get(rootKey);
		if (temp.containsKey("errorcode")) {
			result.put("status", ERROR_STATUS);
			result.put("message", temp.get("errortext"));
		} else {
			result.put("status", SUCCESS_STATUS);
			result.put("message", json);
		}
		return result;
	}

}
