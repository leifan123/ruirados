package com.ruirados.service;

import java.util.HashMap;
import java.util.Map;

import com.ruirados.pojo.RspData;
import com.ruirados.utils.HttpReq;
import com.ruirados.utils.JSONUtils;

import net.sf.json.JSONObject;

public class ZoneService {
	String cephPath=com.ruirados.utils.Prop.getInstance().getPropertiesByPro("ceph.properties", "ceph.path");
	
	//获取区域列表
	public String zoneList(){
		String zoneList = HttpReq.sendGet(cephPath + "zone/zoneList.do", null);
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			map=JSONUtils.getMapObjectByJson(zoneList);
		} catch (Exception e) {
			return "返回格式错误";
		}
		
		return JSONUtils.createObjectJson(map);
	}
	
	//默认区域
	public String defaultZone(){
		String defaultZone = HttpReq.sendGet(cephPath + "zone/zoneList.do", null);
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			map=JSONUtils.getMapObjectByJson(defaultZone);
		} catch (Exception e) {
			return "返回格式错误";
		}
		
		return JSONUtils.createObjectJson(map);
	}

}
