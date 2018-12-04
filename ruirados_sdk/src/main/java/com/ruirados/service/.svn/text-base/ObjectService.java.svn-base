package com.ruirados.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.ruirados.pojo.OssBucket;
import com.ruirados.pojo.OssObject;
import com.ruirados.pojo.OssZone;
import com.ruirados.pojo.RspData;
import com.ruirados.utils.FcUtil;
import com.ruirados.utils.HttpReq;
import com.ruirados.utils.JSONUtils;
import com.ruirados.utils.uploadfileUtils;

import net.sf.json.JSONObject;

public class ObjectService {
	
	String cephPath=com.ruirados.utils.Prop.getInstance().getPropertiesByPro("ceph.properties", "ceph.path");
    //创建文件夹
	public String createObject(OssZone ossZone,OssObject ossObject){
		
		Map<String, String> param_map = new HashMap<String, String>();
		param_map.put("zonename", ossZone.getZonename());
		RspData zoneId = HttpReq.getIbMsg("zone","getZoneId.do", param_map);
		
		
		
		Map<String,Object> result = (Map<String,Object>)zoneId.getData();
		
		System.out.println("zoneId --> " + (String) result.get("zoneId"));

		Map<String, String> objectMap = new HashMap<String, String>();
		objectMap.put("bucketName", ossObject.getBucketName());
		objectMap.put("fileName", ossObject.getFilename());
		objectMap.put("zoneId",(String) result.get("zoneId"));
		objectMap.put("dirId",ossObject.getId()+"");
		
		System.out.println("参数："+JSONObject.fromObject(objectMap).toString());
		String createObject = HttpReq.sendPost(cephPath+"object/createObject.do",JSONObject.fromObject(objectMap).toString() );
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			map=JSONUtils.getMapObjectByJson(createObject);
		} catch (Exception e) {
			return "返回格式错误";
		}
		
		return JSONUtils.createObjectJson(map);
	}
	//删除文件
	public String deleteFile(OssZone ossZone,OssObject ossObject){

		Map<String, String> param_map = new HashMap<String, String>();
		param_map.put("zonename", ossZone.getZonename());
		RspData zoneId = HttpReq.getIbMsg("zone","getZoneId.do", param_map);
		
		System.out.println("开始获取访问结果");
		
		Map<String,Object> result = (Map<String,Object>)zoneId.getData();
		System.out.println("result："+result);
		System.out.println("zoneId --> " + (String) result.get("zoneId"));

		Map<String, String> objectMap = new HashMap<String, String>();
		objectMap.put("bucketName", ossObject.getBucketName());
		objectMap.put("fileName", ossObject.getFilename());
		objectMap.put("zoneId",(String) result.get("zoneId"));
		objectMap.put("dirId",ossObject.getId()+"");
		
		
		String deleteObject = HttpReq.sendPost(cephPath+"object/deleteObject.do",JSONObject.fromObject(objectMap).toString() );
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			map=JSONUtils.getMapObjectByJson(deleteObject);
		} catch (Exception e) {
			return "返回格式错误";
		}
		
		return JSONUtils.createObjectJson(map);
	}
	
	//查看文件
	public String list(OssZone ossZone,OssObject ossObject){

		Map<String, String> param_map = new HashMap<String, String>();
		param_map.put("zonename", ossZone.getZonename());
		RspData zoneId = HttpReq.getIbMsg("zone","getZoneId.do", param_map);

		Map<String,Object> result = (Map<String,Object>)zoneId.getData();
		System.out.println("result："+result);
		System.out.println("zoneId --> " + (String) result.get("zoneId"));

		Map<String, String> objectMap = new HashMap<String, String>();
		objectMap.put("bucketName", ossObject.getBucketName());
		objectMap.put("fileName", ossObject.getFilename());
		objectMap.put("zoneId",(String) result.get("zoneId"));
		objectMap.put("dirId",ossObject.getId()+"");
		
		
		String listObject = HttpReq.sendPost(cephPath+"object/deleteObject.do",JSONObject.fromObject(objectMap).toString() );
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			map=JSONUtils.getMapObjectByJson(listObject);
		} catch (Exception e) {
			return "返回格式错误";
		}
		
		return JSONUtils.createObjectJson(map);
	}

    //上传
	public String uploadObject(String buckectName,String dirId,String zoneName,String fileSrc){
		Map<String, String> param_map = new HashMap<String, String>();
		param_map.put("zonename", zoneName);
		RspData zoneId = HttpReq.getIbMsg("zone","getZoneId.do", param_map);

		Map<String,Object> result = (Map<String,Object>)zoneId.getData();
		
		System.out.println("result："+result);
		System.out.println("zoneId --> " + (String) result.get("zoneId"));
		String zoneid = (String) result.get("zoneId");

		uploadfileUtils up = new uploadfileUtils();
		String url = cephPath+"object/uploadObject.do";
		String resultMasage = null;
		try {
			String masage = up.upload(url,fileSrc, buckectName,zoneid, dirId);
			resultMasage = masage;
		} catch (Exception e) {
			return "返回格式错误";
		}
		
		return resultMasage;
	}

	//获取外链
	public String geturl(OssZone ossZone,OssObject ossObject , String fileSrc,String timelimit){
		
		Map<String, String> param_map = new HashMap<String, String>();
		param_map.put("zonename", ossZone.getZonename());
		RspData zoneId = HttpReq.getIbMsg("zone","getZoneId.do", param_map);

		Map<String,Object> result = (Map<String,Object>)zoneId.getData();
		System.out.println("result："+result);
		System.out.println("zoneId --> " + (String) result.get("zoneId"));

		Map<String, String> objectMap = new HashMap<String, String>();
		objectMap.put("bucketName", ossObject.getBucketName());
		objectMap.put("fileSrc", fileSrc);
		objectMap.put("zoneId",(String) result.get("zoneId"));
		objectMap.put("timelimit",timelimit);
		
		
		String listObject = HttpReq.sendPost(cephPath+"object/geturl.do",JSONObject.fromObject(objectMap).toString() );
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			map=JSONUtils.getMapObjectByJson(listObject);
		} catch (Exception e) {
			return "返回格式错误";
		}
		
		return JSONUtils.createObjectJson(map);
		
	}
}
