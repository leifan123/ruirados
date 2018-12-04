package com.ruirados.service;

import java.util.HashMap;
import java.util.Map;

import com.ruirados.pojo.BasicRuiradosCredentials;
import com.ruirados.pojo.OssZone;
import com.ruirados.utils.HttpReq;
import com.ruirados.utils.JSONUtils;

import net.sf.json.JSONObject;

public class creatConnService {
	
	String cephPath=com.ruirados.utils.Prop.getInstance().getPropertiesByPro("ceph.properties", "ceph.path");
	
	/**
	 * 创建Ceph连接
	 * @param Credentials
	 * @param ossZone
	 * @return
	 */
	public String creatConnect(BasicRuiradosCredentials Credentials, OssZone ossZone){
		
		Map<String, String> bucket_map = new HashMap<String, String>();
		bucket_map.put("access_keys", Credentials.getAccess_keys());
		bucket_map.put("secret_keys", Credentials.getSecret_keys());
		bucket_map.put("zoneName", ossZone.getZonename());
		
		String createUserAcess = HttpReq.sendGet(cephPath + "user/creatConnect.do", JSONObject.fromObject(bucket_map).toString());
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			map=JSONUtils.getMapObjectByJson(createUserAcess);
		} catch (Exception e) {
			return  "返回格式错误！";
		}
	return (String) map.get("data");
	}
}
