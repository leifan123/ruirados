package com.ruirados.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruirados.pojo.OssAcl;
import com.ruirados.pojo.OssBucket;
import com.ruirados.pojo.OssZone;
import com.ruirados.pojo.RspData;
import com.ruirados.utils.HttpReq;
import com.ruirados.utils.JSONUtils;

import net.sf.json.JSONObject;

public class AclService {
	
	String cephPath=com.ruirados.utils.Prop.getInstance().getPropertiesByPro("ceph.properties", "ceph.path");
	
	/**
	 * 添加自定义权限
	 * 
	 * @param request
	 * @param bucketName
	 *            bucket名称
	 * @param zoneId
	 *            区域编号
	 * @param bucketId
	 *            bucketId
	 * @param userAuths
	 *            用户授权
	 * @param customPermission
	 *            对象和Object权限
	 * @param objectNames
	 *            资源路径
	 * @param isOperation
	 *            操作权限 0 不可操作 1可操作
	 * @param isReferer
	 *            是否开启白名单 0否 1是
	 * @param refereIp
	 *            白名单Ip
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */
	public String createCustomAcl(OssAcl ossAcl,OssZone ossZone, OssBucket ossBucket){
		List<String> list = new ArrayList<String>();
		if(ossAcl.getGetobject() == 1){
			list.add("GetObject");
		}
		if(ossAcl.getListbucket() == 1){
			list.add("ListBucket");
		}
		if(ossAcl.getDeletebucket() == 1){
			list.add("DeleteBucket");
		}
		if(ossAcl.getDeleteobject() == 1){
			list.add("DeleteObject");
		}
		if(ossAcl.getPutobject() == 1){
			list.add("PutObject");
		}
		
		List<String> customPermission = list;
		
		Map<String, String> param_map = new HashMap<String, String>();
		param_map.put("zonename", ossZone.getZonename());
		RspData zoneId = HttpReq.getIbMsg("zone","getZoneId.do", param_map);
		
		Map<String,Object> code = (Map<String,Object>)zoneId.getData();
		
		Map<String, Object> bucket_map = new HashMap<String, Object>();
		bucket_map.put("bucketName", ossBucket.getName());
		bucket_map.put("userAuths", ossAcl.getUserauthorization());
		bucket_map.put("customPermission", customPermission);
		bucket_map.put("objectNames", ossAcl.getResource());
		bucket_map.put("isOperation", ossAcl.getIseffectres() + "");
		bucket_map.put("isReferer", ossAcl.getIseffectrefip() + "");
		bucket_map.put("refereIp", ossAcl.getRefererip());
		bucket_map.put("bucketId", ossBucket.getId() + "");
		bucket_map.put("zoneId", (String)code.get("zoneId"));
		
		String createBucket = HttpReq.sendPost(cephPath + "bucketAcl/createCustomAcl.do", JSONObject.fromObject(bucket_map).toString());
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			map=JSONUtils.getMapObjectByJson(createBucket);
		} catch (Exception e) {
			return  "返回格式错误！" + createBucket;
		}
	return (String) map.get("data");
	}
	
	/**
	 * 根据Code查询自定义权限
	 * @param zoneId
	 * @param bucketId
	 * @return
	 */
	public String selectFromBucketId(OssAcl ossAcl,OssBucket ossBucket){
		Map<String, Object> bucket_map = new HashMap<String, Object>();
		bucket_map.put("bucketName", ossBucket.getName());
		bucket_map.put("code", ossAcl.getCode());
		
		String createBucket = HttpReq.sendPost(cephPath + "bucketAcl/selectFromCode.do", JSONObject.fromObject(bucket_map).toString());
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			map=JSONUtils.getMapObjectByJson(createBucket);
		} catch (Exception e) {
			return  "返回格式错误！" + createBucket;
		}
	return (String) map.get("data");
	}
	
	public String updateFromBucketId(OssAcl ossAcl,OssBucket ossBucket){
		List<String> list = new ArrayList<String>();
		list.add(ossAcl.getGetobject() + "");
		list.add(ossAcl.getListbucket() + "");
		list.add(ossAcl.getDeletebucket() + "");
		list.add(ossAcl.getDeleteobject() + "");
		list.add(ossAcl.getPutobject() + "");
		
		List<String> customPermission = list;
		
		Map<String, Object> bucket_map = new HashMap<String, Object>();
		bucket_map.put("bucketName", ossBucket.getName());
		bucket_map.put("userAuths", ossAcl.getUserauthorization());
		bucket_map.put("customPermission", customPermission);
		bucket_map.put("objectNames", ossAcl.getResource());
		bucket_map.put("isOperation", ossAcl.getIseffectres() + "");
		bucket_map.put("isReferer", ossAcl.getIseffectrefip() + "");
		bucket_map.put("refereIp", ossAcl.getRefererip());
		bucket_map.put("code", ossAcl.getCode());
		
		String createBucket = HttpReq.sendPost(cephPath + "bucketAcl/updateFromBucketId.do", JSONObject.fromObject(bucket_map).toString());
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			map=JSONUtils.getMapObjectByJson(createBucket);
		} catch (Exception e) {
			return  "返回格式错误！" + createBucket;
		}
	return (String) map.get("data");
	}
	
	/**
	 * 根据Code删除自定义权限
	 * @param zoneId
	 * @param bucketId
	 * @return
	 */
	public String deleteFromBucketId(OssAcl ossAcl,OssBucket ossBucket){
		Map<String, Object> bucket_map = new HashMap<String, Object>();
		bucket_map.put("bucketName", ossBucket.getName());
		bucket_map.put("code", ossAcl.getCode());
		
		String createBucket = HttpReq.sendPost(cephPath + "bucketAcl/deleteFromBucketId.do", JSONObject.fromObject(bucket_map).toString());
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			map=JSONUtils.getMapObjectByJson(createBucket);
		} catch (Exception e) {
			return  "返回格式错误！" + createBucket;
		}
	return (String) map.get("data");
	}
	
	/**
	 * 权限切换
	 * @param request
	 * @param zoneId
	 * @param accessrights
	 * @param bucketId
	 * @param bucketName
	 * @return
	 * @throws Exception
	 */
	public String aclCut(OssZone ossZone, OssBucket ossBucket){
		Map<String, String> param_map = new HashMap<String, String>();
		param_map.put("zonename", ossZone.getZonename());
		RspData zoneId = HttpReq.getIbMsg("zone","getZoneId.do", param_map);
		
		Map<String,Object> code = (Map<String,Object>)zoneId.getData();
		
		Map<String, Object> bucket_map = new HashMap<String, Object>();
		bucket_map.put("bucketName", ossBucket.getName());
		bucket_map.put("zoneId", (String) code.get("zoneId"));
		bucket_map.put("accessrights", ossBucket.getAccessrights() + "");
		bucket_map.put("bucketId", ossBucket.getId() + "");
		
		String createBucket = HttpReq.sendPost(cephPath + "bucketAcl/aclCut.do", JSONObject.fromObject(bucket_map).toString());
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
	public String selectAclAll(OssZone ossZone, OssBucket ossBucket){
		Map<String, String> param_map = new HashMap<String, String>();
		param_map.put("zonename", ossZone.getZonename());
		RspData zoneId = HttpReq.getIbMsg("zone","getZoneId.do", param_map);
		
		Map<String,Object> code = (Map<String,Object>)zoneId.getData();
		
		Map<String, Object> bucket_map = new HashMap<String, Object>();
		bucket_map.put("bucketName", ossBucket.getName());
		bucket_map.put("zoneId", (String) code.get("zoneId"));
		bucket_map.put("bucketId", ossBucket.getId() + "");
		
		String createBucket = HttpReq.sendPost(cephPath + "bucketAcl/selectAclAll.do", JSONObject.fromObject(bucket_map).toString());
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			map=JSONUtils.getMapObjectByJson(createBucket);
		} catch (Exception e) {
			return  "返回格式错误！" + createBucket;
		}
	return (String) map.get("data");
	}
}
