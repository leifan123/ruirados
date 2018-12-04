/**
 * NewHeight.com Inc.
 * Copyright (c) 2008-2010 All Rights Reserved.
 */
package com.ruirados.service;

import java.util.HashMap;
import java.util.Map;
import com.ruirados.pojo.BasicRuiradosCredentials;
import com.ruirados.pojo.OssBucket;
import com.ruirados.pojo.OssZone;
import com.ruirados.pojo.RspData;
import com.ruirados.utils.HttpReq;
import com.ruirados.utils.JSONUtils;

import net.sf.json.JSONObject;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author yunrui006
 * @version $Id: BucketService.java, v 0.1 2018年6月4日 上午10:09:33 yunrui006 Exp $
 */
public class BucketService {
	
	String cephPath=com.ruirados.utils.Prop.getInstance().getPropertiesByPro("ceph.properties", "ceph.path");
	
	/**
	 * 新建Bucket
	 * 
	 * @param session
	 * @param bucketName
	 *            bucket名称
	 * @param accessrights
	 *            bucket权限: 1 私有读写 , 2 公有读私有写, 3 公有读写, 4 自定义权限
	 * @param zoneId
	 *            区域id
	 * @return
	 */
	public String createBucket(OssZone ossZone, OssBucket ossBucket){
		
			Map<String, String> param_map = new HashMap<String, String>();
			param_map.put("zonename", ossZone.getZonename());
			RspData zoneId = HttpReq.getIbMsg("zone","getZoneId.do", param_map);
			
			Map<String,Object> code = (Map<String,Object>)zoneId.getData();
			
			Map<String, String> bucket_map = new HashMap<String, String>();
			bucket_map.put("bucketName", ossBucket.getName());
			bucket_map.put("accessrights", ossBucket.getAccessrights() + "");
			bucket_map.put("zoneId", (String) code.get("zoneId"));
			
			String createBucket = HttpReq.sendPost(cephPath + "bucket/createBucket.do", JSONObject.fromObject(bucket_map).toString());
			Map<String, Object> map=new HashMap<String, Object>();
			try {
				map=JSONUtils.getMapObjectByJson(createBucket);
			} catch (Exception e) {
				return  "返回格式错误！" + createBucket;
			}
		return (String) map.get("data");
	}
	
	/**
	 * 获取该用户下所有的容器信息
	 * zoneId 区域编号
	 * @return
	 */
	public String getBuckets(OssZone ossZone){
		Map<String, String> param_map = new HashMap<String, String>();
		param_map.put("zonename", ossZone.getZonename());
		RspData zoneId = HttpReq.getIbMsg("zone","getZoneId.do", param_map);
		
		Map<String,Object> code = (Map<String,Object>)zoneId.getData();
		
		
		Map<String, String> bucket_map = new HashMap<String, String>();
		bucket_map.put("zoneId", (String) code.get("zoneId"));
		
		String createBucket = HttpReq.sendPost(cephPath + "bucket/getBuckets.do", JSONObject.fromObject(bucket_map).toString());
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			map=JSONUtils.getMapObjectByJson(createBucket);
		} catch (Exception e) {
			return  "返回格式错误！" + createBucket;
		}
	return (String) map.get("data");
	}
	
	/**
	 * 删除Bucket
	 * 
	 * @param session
	 * @param bucketName
	 *            空间名称
	 * @return
	 */
	public String deleteByBucketName(OssZone ossZone, OssBucket ossBucket){
		Map<String, String> param_map = new HashMap<String, String>();
		param_map.put("zonename", ossZone.getZonename());
		RspData zoneId = HttpReq.getIbMsg("zone","getZoneId.do", param_map);
		
		Map<String,Object> code = (Map<String,Object>)zoneId.getData();
		
		Map<String, String> bucket_map = new HashMap<String, String>();
		bucket_map.put("bucketName", ossBucket.getName());
		bucket_map.put("zoneId", (String) code.get("zoneId"));
		
		String createBucket = HttpReq.sendPost(cephPath + "bucket/deleteByBucketName.do", JSONObject.fromObject(bucket_map).toString());
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			map=JSONUtils.getMapObjectByJson(createBucket);
		} catch (Exception e) {
			return  "返回格式错误！" + createBucket;
		}
	return (String) map.get("data");
	}
	
	/**
	 * 获取Bucket的详细信息
	 * 
	 * @param session
	 * @return
	 */
	public String selectByBucketName(OssZone ossZone, OssBucket ossBucket){
		Map<String, String> param_map = new HashMap<String, String>();
		param_map.put("zonename", ossZone.getZonename());
		RspData zoneId = HttpReq.getIbMsg("zone","getZoneId.do", param_map);
		
		Map<String,Object> code = (Map<String,Object>)zoneId.getData();
		
		Map<String, String> bucket_map = new HashMap<String, String>();
		bucket_map.put("bucketName", ossBucket.getName());
		bucket_map.put("zoneId", (String) code.get("zoneId"));
		
		String createBucket = HttpReq.sendPost(cephPath + "bucket/selectByBucketName.do", JSONObject.fromObject(bucket_map).toString());
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			map=JSONUtils.getMapObjectByJson(createBucket);
		} catch (Exception e) {
			return  "返回格式错误！" + createBucket;
		}
	return (String) map.get("data");
	}
}
