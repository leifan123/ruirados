package com.ruirados.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.twonote.rgwadmin4j.RgwAdmin;
import org.twonote.rgwadmin4j.RgwAdminBuilder;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.model.Permission;
import com.ruirados.pojo.OssAcl;
import com.ruirados.pojo.OssBucket;
import com.ruirados.pojo.OssOperatelog;
import com.ruirados.pojo.OssZone;
import com.ruirados.pojo.RspData;
import com.ruirados.service.OssAclService;
import com.ruirados.service.OssBucketService;
import com.ruirados.service.UserCoreAccessService;
import com.yunrui.pojo.YrComper;

public class BucketAclUtil {

	public static AmazonS3 getConnByCache(String companyId,String zoneId,int protocol,Boolean isInner){
		
		AmazonS3 conn = AmazonCache.getAmazonConn(companyId, protocol).getAmazonConn();
		
		OssZone zone = GlobalAttr.getInstance().getZoneMap().get(zoneId);
		conn.setS3ClientOptions(S3ClientOptions.builder().setPathStyleAccess(true).build());
		if(!isInner) {
			conn.setEndpoint(zone.getInnerserverip());
		} else {
			conn.setEndpoint(zone.getServerip());
		}
		
		return conn;
	}
	
	public static RgwAdmin getRgwAdmin(String accessKey, String secretKey, String adminEndpoint){
	        return new RgwAdminBuilder().accessKey(accessKey).secretKey(secretKey).endpoint(adminEndpoint)
	                .build();
	}
	
	/**
	 * 根据用户选择对应权限
	 * @param accessrights	1 ：私有读写， 2：公有读私有写，3：公有读写
	 * @return
	 */
	public static Map<String, Integer> objectAcl(int accessrights){
		Map<String, Integer> map = new HashMap<String, Integer>(); 
		
		String putObject = "putObject";
		String getObject = "getObject";
		String deleteObject = "deleteObject";
		String listBucket = "listBucket";
		String deleteBucket = "deleteBucket";
		/*
		 * 对除自己外其他用户
		 * 0:	能有此操作
		 * 1：不能有此操作
		 */
		if(accessrights == 1){
			map.put(putObject, 0);
			map.put(getObject, 0);	
			map.put(deleteObject, 0);
			map.put(listBucket, 0);
			map.put(deleteBucket, 0);
			return	map;
		}	else if(accessrights == 2){
			map.put(putObject, 0);
			map.put(getObject, 1);
			map.put(deleteObject, 0);
			map.put(listBucket, 0);
			map.put(deleteBucket, 0);
			return	map;
		}	else if(accessrights == 3){
			map.put(putObject, 1);
			map.put(getObject, 1);
			map.put(deleteObject, 1);
			map.put(listBucket, 1);
			map.put(deleteBucket, 1);
			return	map;
		}
		return map;
	}
	
	/**
	 * 获得Permission权限（自定义权限对Object）
	 * @param customPermission
	 * @return
	 */
	public static Permission getPermissionFromObject(String[] customPermission){
		boolean putO = false;
		boolean getO = false;
		boolean deleteO = false;
		for(int i=0; i<customPermission.length; i++){
			if("PutObject".equals(customPermission[i])){
				putO = true;
			}
			if("GetObject".equals(customPermission[i])){
				getO = true;
			}
			if("DeleteObject".equals(customPermission[i])){
				deleteO = true;
			}
			while(i == customPermission.length-1){
				if(putO&& deleteO&& getO){
					return Permission.FullControl;
				}	else if(putO&& deleteO&& getO == false){
					return Permission.WriteAcp;
				}	else if(getO&& putO|| deleteO){
					return Permission.WriteAcp;
				}	else if(getO&& putO == false && deleteO == false ){
					return Permission.ReadAcp;
				}
			}
		}
		return Permission.ReadAcp;
	}
	
	/**
	 * 获得Permission权限（自定义权限对Bucket）
	 * @param customPermission
	 * @return
	 */
	public static Permission getPermissionFromBucket(String[] customPermission){
		boolean listB = false;
		boolean deleteB = false;
		for(int i=0; i<customPermission.length; i++){
			if("ListBucket".equals(customPermission[i])){
				listB = true;
			}
			if("DeleteBucket".equals(customPermission[i])){
				deleteB = true;
			}
			while(i == customPermission.length-1){
				if(listB == true && deleteB == true){
					return Permission.FullControl;
				}	else if(listB&& deleteB == false){
					return Permission.Read;
				}	else if(deleteB&& listB == false){
					return Permission.Write;
				}
			}
		}
		return Permission.Read;
	}
	
	/**
	 * 获取真实的IP地址
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String unknown = "unknown";
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 查看是否有权限
	 * @param request
	 * @param bucketName
	 * @param acl
	 * @return
	 */
	public int checkListBucketAcl(HttpServletRequest request, String bucketName, String acl, UserCoreAccessService userCoreAccessService, OssAclService OssAclService, OssBucketService OssBucketService){
		RspData rd = new RspData();
		
		YrComper yr = ApiTool.getUserMsg(request);
		
		String companyId = yr.getCompanyid();
		
		List<OssBucket> listOssBucket;
		
		if(bucketName == null){
			return 3;
		}	else{
			listOssBucket = OssBucketService.selectByParam(null, " name='" + bucketName + "'");
			String bucketCompanyId = listOssBucket.get(0).getCompanyid();
			if(companyId.equals(bucketCompanyId)){
				return 3;
			}
		}
		
		
		
		List<OssAcl> listOssAcl = OssAclService.select(new OssAcl().setBucketId(listOssBucket.get(0).getId()));
		int aclb = 0;
		if ("ListBucket".equals(acl)) {
			for(OssAcl ossAcl : listOssAcl){
				aclb = ossAcl.getListbucket();
				if(aclb == 1){
					break;
				}
			}
		}
		else if ("DeleteBucket".equals(acl)) {
			for(OssAcl ossAcl : listOssAcl){
				aclb = ossAcl.getDeletebucket();
				if(aclb == 1){
					break;
				}
			}
		}
		else if ("PutObject".equals(acl)) {
			for(OssAcl ossAcl : listOssAcl){
				aclb = ossAcl.getPutobject();
				if(aclb == 1){
					break;
				}
			}
		}
		else if ("GetObject".equals(acl)) {
			for(OssAcl ossAcl : listOssAcl){
				aclb = ossAcl.getGetobject();
				if(aclb == 1){
					break;
				}
			}
		}
		else if ("DeleteObject".equals(acl)) {
			for(OssAcl ossAcl : listOssAcl){
				aclb = ossAcl.getDeleteobject();
				if(aclb == 1){
					break;
				}
			}
		}
		return aclb;
	}
	
	public static Map<String, Object> toEh(Map<String, Object> maps){
		if((String) maps.get("bucketName") != null){
			maps.put("空间名", (String) maps.get("bucketName"));
			maps.remove("bucketName");
		} 
		else if((String) maps.get("zoneId") != null){
			maps.put("区域ID", (String) maps.get("zoneId"));
			maps.remove("zoneId");
		}
		else if((String) maps.get("userAuths") != null){
			maps.put("用户授权", (String) maps.get("userAuths"));
			maps.remove("userAuths");
		}
		else if((List<String>) maps.get("customPermission") != null){
			maps.put("对象和Object权限", (List<String>) maps.get("customPermission"));
			maps.remove("customPermission");
		}
		else if((String) maps.get("objectNames") != null){
			maps.put("资源路径", (String) maps.get("objectNames"));
			maps.remove("objectNames");
		}
		else if((String) maps.get("isOperation") != null){
			maps.put("操作权限", (String) maps.get("isOperation"));
			maps.remove("isOperation");
		}
		else if((String) maps.get("isReferer") != null){
			maps.put("是否开启白名单", (String) maps.get("isReferer"));
			maps.remove("isReferer");
		}
		else if((String) maps.get("refereIp") != null){
			maps.put("白名单Ip", (String) maps.get("refereIp"));
			maps.remove("refereIp");
		}
		else if((String) maps.get("bucketId") != null){
			maps.put("空间ID", (String) maps.get("bucketId"));
			maps.remove("bucketId");
		}
		else if((String) maps.get("code") != null){
			maps.put("code编号", (String) maps.get("code"));
			maps.remove("code");
		}
		else if((String) maps.get("accessrights") != null){
			maps.put("权限类型", (String) maps.get("accessrights"));
			maps.remove("accessrights");
		}
		else if((String) maps.get("fileName") != null){
			maps.put("文件夹名", (String) maps.get("fileName"));
			maps.remove("fileName");
		}
		else if((String) maps.get("dirId") != null){
			maps.put("文件夹ID", (String) maps.get("dirId"));
			maps.remove("dirId");
		}
		else if((String) maps.get("status") != null){
			maps.put("状态", (String) maps.get("status"));
			maps.remove("status");
		}
		else if((String) maps.get("zoneName") != null){
			maps.put("空间名", (String) maps.get("zoneName"));
			maps.remove("zoneName");
		}else if((String) maps.get("zoneId") != null){
			maps.put("区域ID", (String) maps.get("bucketName"));
			maps.remove("zoneId");
		}
		else if((String) maps.get("zoneId") != null){
			maps.put("区域ID", (String) maps.get("bucketName"));
			maps.remove("zoneId");
		}
		return maps;
	}
	
	public static OssOperatelog setLog(HttpServletRequest request, String operatetarget, String operatetype, 
			String operatedes, String zoneid) throws Exception{
		OssOperatelog operatelog = new OssOperatelog();
		operatelog.setOperatetarget(operatetarget);
		operatelog.setOperatetype(operatetype);
		operatelog.setOperatedes(operatedes);

		operatelog.setZoneid(zoneid);

		// 设置公司ID
		operatelog.setCompanyid(ApiTool.getUserMsg(request).getCompanyid());
		// 设置IP地址
		operatelog.setOperatorip(BucketAclUtil.getIpAddress(request));

		// 系统当前时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式
		Date date1 = new Date();
		String str = df.format(date1);
		operatelog.setOperatortime(df.parse(str));
		return operatelog;
	}
	
	public static double[] price(boolean flowB, boolean capacityB, String timeType, int capacitySize, int time, int flowSize){
		
		double[] number = new double[2];
		if(flowB == true && capacityB == true){
			number[0] = 0;
			number[1] = 0;
		}else if(flowB == true){
			if(timeType.equals("mouth")){
				number[0] = (0.49 * capacitySize * 0.9 * time);
				number[1] = 0.49 * capacitySize * time;
			}else if(timeType.equals("year")){
				if(time == 1){
					number[0] = (0.49 * capacitySize * 0.83 * 12);
					number[1] = 0.49 * capacitySize * 12;
				} else if(time == 2){
					number[0] = (0.49 * capacitySize * 0.7 * 24);
					number[1] = 0.49 * capacitySize * 24;
				} else if(time == 2){
					number[0] = (0.49 * capacitySize * 0.6 * 36);
					number[1] = 0.49 * capacitySize * 36;
				}
			}
			
		}else if(capacityB == true){
			if(timeType.equals("mouth")){
				number[0] = (flowSize * 0.09 * 0.9 * time);
				number[1] = flowSize * 0.09 * time;
			}else if(timeType.equals("year")){
				if(time == 1){
					number[0] = (flowSize * 0.09 * 0.83 * 12);
					number[1] = flowSize * 0.09 * 12;
				} else if(time == 2){
					number[0] = (flowSize * 0.09 * 0.7 * 24);
					number[1] = flowSize * 0.09 * 24;
				} else if(time == 2){
					number[0] = (flowSize * 0.09 * 0.6 * 36);
					number[1] = flowSize * 0.09 * 36;
				}
			}
		}else{
			if(timeType.equals("mouth")){
				number[0] = (flowSize * 0.09 * 0.9 * time) + (0.49 * capacitySize * 0.9 * time);
				number[1] = (flowSize * 0.09 * time) + (0.49 * capacitySize * time);
			}else if(timeType.equals("year")){
				if(time == 1){
					number[0] = (flowSize * 0.09 * 0.83 * 12) + (0.49 * capacitySize * 0.83 * 12);
					number[1] = (flowSize * 0.09 * 12) + (0.49 * capacitySize * 12);
				} else if(time == 2){
					number[0] = (flowSize * 0.09 * 0.7 * 24) + (0.49 * capacitySize * 0.7 * 24);
					number[1] = (flowSize * 0.09 * 24) + (0.49 * capacitySize * 24);
				} else if(time == 2){
					number[0] = (flowSize * 0.09 * 0.6 * 36) + (0.49 * capacitySize * 0.6 * 36);
					number[1] = (flowSize * 0.09 * 36) + (0.49 * capacitySize * 36);
				}
			}
		}
		
		return number;
	}
	
}
