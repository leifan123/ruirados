package com.ruicloud.ruirados;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ruirados.pojo.OssObject;
import com.ruirados.pojo.RspData;

public class testGson {
	

	public static void main(String[] args) {
		RspData rs = new RspData();
		OssObject ooj = new OssObject();
		ooj.setBucketId(1);
		ooj.setBucketName("bucket");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", "bob");
		map.put("age", "16");
		
		System.out.println(map);
		
		Gson gson = new GsonBuilder().create(); 
		map.put("ooj", ooj);
		
		System.out.println(gson.toJson(map));
		System.out.println(gson.toJson(ooj));
		rs.setMsg("成功");
		rs.setData(map);
		System.out.println(gson.toJson(rs));
	}

}


	
