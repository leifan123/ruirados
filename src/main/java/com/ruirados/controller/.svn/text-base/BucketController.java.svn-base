package com.ruirados.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CanonicalGrantee;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.ruirados.bean.DestroyBucketBean;
import com.ruirados.pojo.OssAcl;
import com.ruirados.pojo.OssBucket;
import com.ruirados.pojo.OssObject;
import com.ruirados.pojo.OssOperatelog;
import com.ruirados.pojo.OssZone;
import com.ruirados.pojo.RspData;
import com.ruirados.pojo.UserCoreAccess;
import com.ruirados.pojo.UserTypeSource;
import com.ruirados.service.OssAclService;
import com.ruirados.service.OssBucketService;
import com.ruirados.service.OssObjectService;
import com.ruirados.service.OssOperatelogService;
import com.ruirados.service.OssZoneService;
import com.ruirados.service.UserCoreAccessService;
import com.ruirados.service.UserTypeSourceService;
import com.ruirados.threads.SynBucketCapacityRunnable;
import com.ruirados.util.ApiTool;
import com.ruirados.util.BucketAclUtil;
import com.ruirados.util.Config;
import com.ruirados.util.ExptNum;
import com.ruirados.util.GetResult;
import com.ruirados.util.GlobalAttr;
import com.ruirados.util.JSONUtils;
import com.ruirados.util.MappingConfigura;
import com.ruirados.util.ParamIsNull;
import com.ruirados.util.Prop;
import com.ruirados.util.RuiradosUtil;
import com.ruirados.util.Uuid;
import com.yunrui.pojo.YrComper;


@Controller
@RequestMapping(MappingConfigura.BUCKET)
public class BucketController {

	@Autowired
	private OssOperatelogService operatelogService;
	@Autowired
	private UserTypeSourceService userTypeSourceService;
	@Autowired
	private OssAclService ossAclService;
	@Autowired
	private OssBucketService ossBucketService;
	@Autowired
	private OssObjectService ossObjectService;
	@Autowired
	private UserCoreAccessService userCoreAccessService;
	@Autowired
	private OssZoneService ossZoneService;
	//private Logger 

	public static AWSCredentials awsCredentials;

	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式

	private AccessControlList acl = new AccessControlList();
	Logger log = Logger.getLogger(getClass());
	

	/*
	 * public static void main(String[] args) { String accessKey =
	 * "7RZBFITUNL53C1Y819SU"; String secretKey = "leifen"; String endpoint =
	 * "bj1vnc.xrcloud.net:80"; AWSCredentials credentials = new
	 * BasicAWSCredentials(accessKey, secretKey); ClientConfiguration
	 * clientConfig = new ClientConfiguration();
	 * clientConfig.setSignerOverride("S3SignerType");
	 * clientConfig.setProtocol(Protocol.HTTPS); AmazonS3 conn = new
	 * AmazonS3Client(credentials,clientConfig);
	 * conn.setS3ClientOptions(S3ClientOptions.builder().setPathStyleAccess(true
	 * ).build()); conn.setEndpoint(endpoint); //######################test
	 * System.out.println("list buckets"); List<Bucket> buckets =
	 * conn.listBuckets();
	 * 
	 * for (Bucket b : buckets) { System.out.println(b.getName()); }
	 * com.amazonaws.services.s3.model.Owner owner = conn.getS3AccountOwner();
	 * System.out.println("conn.createBucket(bucketName);ownerName:" +
	 * owner.getDisplayName() + "ownerid:" + owner.getId());
	 * //######################end test }
	 */

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
	@RequestMapping(value = "createBucket", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String creatBucket(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		

		Map<String, String> maps = ApiTool.getBodyString(request);

		OssOperatelog operatelog = BucketAclUtil.setLog(request, "Bucket", "新增Bucket", maps.toString(), (String) maps.get("zoneId"));

		String bucketName = (String) maps.get("bucketName");
		String companyId = ApiTool.getUserMsg(request).getCompanyid();
		bucketName = bucketName.toLowerCase() + companyId;
		String accessrights = (String) maps.get("accessrights");
		String zoneId = (String) maps.get("zoneId");
		

		RspData rd = new RspData();
		// 参数完整性判断
		if (!ParamIsNull.isNull(bucketName, accessrights, zoneId)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			operatelog.setOperatestatus(2);
			operatelog.setErrormessage(Config.REQUEST_Param_IS_NULL);
			// 保存系统日志
			operatelogService.insert(operatelog);
			return JSONUtils.createObjectJson(rd);
		}
		
		List<UserTypeSource> userTypeSource = userTypeSourceService.selectByParam("", " companyId='"+companyId+"'");
		int bucketNum = ossBucketService.selectCount("count(*)"," companyId='"+companyId+"'");
		
		int maxBucketNum = 0;
		try {
			maxBucketNum  = userTypeSource.get(0).getMaxossbucket();
		} catch (Exception e) {
			maxBucketNum = 5;
		}
		if(maxBucketNum  <= bucketNum){
			rd.setStatus(ExptNum.QUOTA_IS_FULL.getCode()+"");
			rd.setMsg(ExptNum.QUOTA_IS_FULL.getDesc());
			operatelog.setOperatestatus(2);
			operatelog.setErrormessage(ExptNum.QUOTA_IS_FULL.getDesc());
			// 保存系统日志
			operatelogService.insert(operatelog);
			return JSONUtils.createObjectJson(rd);
		}

		AmazonS3 conn = BucketAclUtil.getConnByCache(companyId, zoneId, 0, true);

		try {
			conn.createBucket(bucketName);
		} catch (SdkClientException e) {
			log.error(e);
			e.printStackTrace();
			rd.setStatus(ExptNum.UNKNOWN_ERROR.getCode() + "");
			rd.setMsg(ExptNum.UNKNOWN_ERROR.getDesc());
			operatelog.setOperatestatus(2);
			operatelog.setErrormessage(ExptNum.USED.getDesc());
			// 保存系统日志
			operatelogService.insert(operatelog);
			return JSONUtils.createObjectJson(rd);
		}

		if(accessrights.equals("1")){
			this.acl.grantPermission(new CanonicalGrantee(companyId), Permission.FullControl);
		}else if(accessrights.equals("2")){
			this.acl.grantPermission(new CanonicalGrantee(companyId), Permission.Write);
			this.acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
		}else{
			this.acl.grantPermission(GroupGrantee.AllUsers, Permission.FullControl);
		}
		
		this.acl.setOwner(conn.getS3AccountOwner());
		// 设置Bucket权限
		conn.setBucketAcl(bucketName, this.acl);

		OssBucket bucketOld = new OssBucket();
		bucketOld.setName(bucketName);
		bucketOld.setZoneid(zoneId);
		List<OssBucket> buckets = ossBucketService.select(bucketOld);
		if (buckets.size() > 0) {
			rd.setStatus(ExptNum.USED.getCode() + "");
			rd.setMsg(ExptNum.USED.getDesc());
			operatelog.setOperatestatus(2);
			operatelog.setErrormessage(ExptNum.USED.getDesc());
			// 保存系统日志
			operatelogService.insert(operatelog);
			return JSONUtils.createObjectJson(rd);
		}

		// 格式化当前时间
		Date date = new Date();
		String dateStr = df.format(date);
		date = df.parse(dateStr);

		// 添加信息到数据库
		OssBucket bucket = new OssBucket();
		bucket.setCode(Uuid.getGenerateCode());
		bucket.setCompanyid(companyId);
		bucket.setName(bucketName);
		bucket.setZoneid(zoneId);
		bucket.setVersion(0);
		bucket.setIsdisplay(1);
		bucket.setState(1);
		bucket.setAccessrights(Integer.parseInt(accessrights));
		bucket.setCreatetime(date);
		ossBucketService.insert(bucket);

		// 得到对应的权限
		Map<String, Integer> param = BucketAclUtil.objectAcl(Integer.parseInt(accessrights));
		OssAcl bucketAcl = new OssAcl();
		bucketAcl.setCode(Uuid.getGenerateCode());
		bucketAcl.setCompanyid(companyId);
		bucketAcl.setBucketId(bucket.getId());
		bucketAcl.setVersion(0);
		bucketAcl.setIsdisplay(1);
		bucketAcl.setZoneid(zoneId);
		bucketAcl.setUserauthorization("*");
		bucketAcl.setPutobject(param.get("putObject"));
		bucketAcl.setGetobject(param.get("getObject"));
		bucketAcl.setDeleteobject(param.get("deleteObject"));
		bucketAcl.setListbucket(param.get("listBucket"));
		bucketAcl.setDeletebucket(param.get("deleteBucket"));
		bucketAcl.setIseffectres(1);
		bucketAcl.setIseffectrefip(0);
		bucketAcl.setState(1);
		bucketAcl.setCreatetime(date);

		ossAclService.insert(bucketAcl);

		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		operatelog.setOperatestatus(1);
		// 保存系统日志
		operatelogService.insert(operatelog);
		return JSONUtils.createObjectJson(rd);
	}

	/**
	 * 获取该用户下所有的容器信息 zoneId 区域编号
	 * 
	 * @return
	 */
	@RequestMapping(value = "getBuckets", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getBuckets(HttpServletRequest request) throws Exception {

		Map<String, String> maps = ApiTool.getBodyString(request);

		OssOperatelog operatelog = BucketAclUtil.setLog(request, "Bucket", "获取获取该用户下Bucket", maps.toString(), (String) maps.get("zoneId"));

		String zoneId = (String) maps.get("zoneId");

		RspData rd = new RspData();

		// 是否有权限
		int aclb = new BucketAclUtil().checkListBucketAcl(request, null, "ListBucket", userCoreAccessService,
				ossAclService, ossBucketService);
		if (aclb == 0) {
			rd.setStatus(ExptNum.NO_JURISDICTION.getCode() + "");
			rd.setMsg(ExptNum.NO_JURISDICTION.getDesc());
			operatelog.setOperatestatus(2);
			operatelog.setErrormessage(ExptNum.NO_JURISDICTION.getDesc());
			// 保存系统日志
			operatelogService.insert(operatelog);
			return JSONUtils.createObjectJson(rd);
		}

		if (!ParamIsNull.isNull(zoneId)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			operatelog.setOperatestatus(2);
			operatelog.setErrormessage(Config.REQUEST_Param_IS_NULL);
			// 保存系统日志
			operatelogService.insert(operatelog);
			return JSONUtils.createObjectJson(rd);
		}

		YrComper yr = ApiTool.getUserMsg(request);
		String companyId = yr.getCompanyid();

		OssBucket bucket = new OssBucket();
		bucket.setCompanyid(companyId);
		bucket.setZoneid(zoneId);
		// 获取所有的bucket
		List<OssBucket> buckets = ossBucketService.select(bucket);

		Map<String, Object> mapBucket = new HashMap<String, Object>();
		mapBucket.put("bucket", buckets);
		OssAcl bucketAcl = new OssAcl();
		bucketAcl.setZoneid(zoneId);

		List<UserCoreAccess> select = userCoreAccessService.select(new UserCoreAccess().setCompanyid(companyId));
		mapBucket.put("protocol", select.get(0).getProtocol());
		
		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		operatelog.setOperatestatus(1);
		// 保存系统日志
		operatelogService.insert(operatelog);
		rd.setData(mapBucket);
		return JSONUtils.createObjectJson(rd);
	}

	/**
	 * 删除Bucket
	 * 
	 * @param session
	 * @param bucketName
	 *            空间名称
	 * @return
	 */
	@RequestMapping(value = "deleteByBucketName", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String deleteByBucketName(HttpServletRequest request) throws Exception {

		Map<String, String> maps = ApiTool.getBodyString(request);

		OssOperatelog operatelog = BucketAclUtil.setLog(request, "Bucket", "删除Bucket", maps.toString(), (String) maps.get("zoneId"));

		String bucketName = (String) maps.get("bucketName");
		bucketName = bucketName.toLowerCase();
		String zoneId = (String) maps.get("zoneId");

		RspData rd = new RspData();

		// 判断是否有权限
		int aclb = new BucketAclUtil().checkListBucketAcl(request, bucketName, "DeleteBucket", userCoreAccessService,
				ossAclService, ossBucketService);
		if (aclb == 0) {
			rd.setStatus(ExptNum.NO_JURISDICTION.getCode() + "");
			rd.setMsg(ExptNum.NO_JURISDICTION.getDesc());
			operatelog.setOperatestatus(2);
			operatelog.setErrormessage(ExptNum.NO_JURISDICTION.getDesc());
			// 保存系统日志
			operatelogService.insert(operatelog);
			return JSONUtils.createObjectJson(rd);
		}

		// 是否通过白名单
		YrComper yr = ApiTool.getUserMsg(request);
		String companyId = yr.getCompanyid();
		String ip = BucketAclUtil.getIpAddress(request);
		OssBucket bucketsRe = new OssBucket();
		bucketsRe.setName(bucketName);
		List<OssBucket> bucketRe = ossBucketService.select(bucketsRe);

		if (bucketRe.size() == 0) {
			rd.setStatus(ExptNum.EMPTY.getCode() + "");
			rd.setMsg(ExptNum.EMPTY.getDesc());
			return JSONUtils.createObjectJson(rd);
		} else {
			// 如果是用户本身，就直接通过
			if (companyId.equals(bucketRe.get(0).getCompanyid())) {

			} else {

				OssAcl bucketAclRe = new OssAcl();

				for (OssBucket bu : bucketRe) {
					bucketAclRe.setBucketId(bu.getId());
				}
				List<OssAcl> bucketAcls = ossAclService.select(bucketAclRe);
				int isEffectRefIp = 0;
				List<String> list = new ArrayList<String>();
				for (OssAcl buAcl : bucketAcls) {
					String str1 = buAcl.getRefererip();
					if (str1 != null && str1.contains(";")) {
						String[] ips = str1.split(";");
						for (String ipss : ips) {
							list.add(ipss);
						}
					} else {
						list.add(str1);
					}
					isEffectRefIp = buAcl.getIseffectrefip();
				}

				if (isEffectRefIp == 1) {
					boolean refererBo = false;
					if (list != null || list.size() > 0) {
						for (String refererIp : list) {
							if (ip.equals(refererIp)) {
								refererBo = true;
							}
						}
					}
					if (refererBo == false) {
						rd.setStatus(ExptNum.IPNOACL.getCode() + "");
						rd.setMsg(ExptNum.IPNOACL.getDesc());
						return JSONUtils.createObjectJson(rd);
					}
				}
			}
		}

		// 参数完整性判断
		if (!ParamIsNull.isNull(bucketName, zoneId)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			operatelog.setOperatestatus(2);
			operatelog.setErrormessage(Config.REQUEST_Param_IS_NULL);
			// 保存系统日志
			operatelogService.insert(operatelog);
			return JSONUtils.createObjectJson(rd);
		}

		// 需保证该bucket下没有文件或者文件夹
		OssObject ossobject = new OssObject();
		ossobject.setBucketName(bucketName);
		ossobject.setZoneid(zoneId);
		List<OssObject> object = ossObjectService.select(ossobject);
		if (object != null && object.size() > 0) {
			rd.setStatus(ExptNum.DFAIL.getCode() + "");
			rd.setMsg(ExptNum.DFAIL.getDesc());
			operatelog.setOperatestatus(2);
			operatelog.setErrormessage(ExptNum.DFAIL.getDesc());
			// 保存系统日志
			operatelogService.insert(operatelog);
			return JSONUtils.createObjectJson(rd);
		}

		OssBucket bucket = new OssBucket();
		bucket.setName(bucketName);
		AmazonS3 conn = BucketAclUtil.getConnByCache(companyId, zoneId, 0, true);

		try {
			conn.deleteBucket(bucketName);
		} catch (AmazonS3Exception e) {
			e.printStackTrace();
			e.getMessage();
			log.error(e);
			rd.setStatus(ExptNum.NO_BUCKETNAME.getCode() + "");
			rd.setMsg(ExptNum.NO_BUCKETNAME.getDesc());
			operatelog.setOperatestatus(2);
			operatelog.setErrormessage(ExptNum.NO_BUCKETNAME.getDesc());
			// 保存系统日志
			operatelogService.insert(operatelog);
			return JSONUtils.createObjectJson(rd);
		}

		int bucketId = ossBucketService.select(bucket).get(0).getId();
		ossBucketService.delete(bucket);
		OssAcl bucketAcl = new OssAcl();
		bucketAcl.setBucketId(bucketId);

		ossAclService.delete(bucketAcl);

		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		operatelog.setOperatestatus(1);
		// 保存系统日志
		operatelogService.insert(operatelog);
		return JSONUtils.createObjectJson(rd);
	}

	/**
	 * 根据Bucket名获取信息
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "selectByBucketName", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String selectByBucketName(HttpServletRequest request) throws Exception {

		Map<String, String> maps = ApiTool.getBodyString(request);

		OssOperatelog operatelog = BucketAclUtil.setLog(request, "Bucket", "根据Bucket名获取信息", maps.toString(), (String) maps.get("zoneId"));

		String bucketName = (String) maps.get("bucketName");
		bucketName = bucketName.toLowerCase();
		String zoneId = (String) maps.get("zoneId");

		RspData rd = new RspData();

		// 是否通过白名单
		YrComper yr = ApiTool.getUserMsg(request);
		String companyId = yr.getCompanyid();
		String ip = BucketAclUtil.getIpAddress(request);
		OssBucket bucketsRe = new OssBucket();
		bucketsRe.setName(bucketName);
		List<OssBucket> bucketRe = ossBucketService.select(bucketsRe);

		if (bucketRe.size() == 0) {
			rd.setStatus(ExptNum.EMPTY.getCode() + "");
			rd.setMsg(ExptNum.EMPTY.getDesc());
			return JSONUtils.createObjectJson(rd);
		} else {
			// 如果是用户本身，就直接通过
			if (companyId.equals(bucketRe.get(0).getCompanyid())) {

			} else {

				OssAcl bucketAclRe = new OssAcl();

				for (OssBucket bu : bucketRe) {
					bucketAclRe.setBucketId(bu.getId());
				}
				List<OssAcl> bucketAcls = ossAclService.select(bucketAclRe);

				int isEffectRefIp = 0;
				List<String> list = new ArrayList<String>();
				for (OssAcl buAcl : bucketAcls) {
					String str1 = buAcl.getRefererip();
					if (str1 != null && str1.contains(";")) {
						String[] ips = str1.split(";");
						for (String ipss : ips) {
							list.add(ipss);
						}
					} else {
						list.add(str1);
					}
					isEffectRefIp = buAcl.getIseffectrefip();
				}

				if (isEffectRefIp == 1) {
					boolean refererBo = false;
					if (list != null || list.size() > 0) {
						for (String refererIp : list) {
							if (ip.equals(refererIp)) {
								refererBo = true;
							}
						}
					}
					if (refererBo == false) {
						rd.setStatus(ExptNum.IPNOACL.getCode() + "");
						rd.setMsg(ExptNum.IPNOACL.getDesc());
						return JSONUtils.createObjectJson(rd);
					}
				}
			}
		}

		// 参数完整性判断
		if (!ParamIsNull.isNull(bucketName, zoneId)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			operatelog.setOperatestatus(2);
			operatelog.setErrormessage(Config.REQUEST_Param_IS_NULL);
			// 保存系统日志
			operatelogService.insert(operatelog);
			return JSONUtils.createObjectJson(rd);
		}

		OssBucket bucket = new OssBucket();
		bucket.setName(bucketName);
		bucket.setZoneid(zoneId);
		Map<String, Object> map = new HashMap<String, Object>();

		// 获取Bucket的详细信息
		List<OssBucket> buckets = ossBucketService.select(bucket);
		OssAcl bucketAcl = new OssAcl();
		bucketAcl.setZoneid(zoneId);
		for (OssBucket bk : buckets) {
			map.put("code", bk.getCode());
			map.put("companyid", bk.getCompanyid());
			map.put("bucketName", bk.getName());
			map.put("bucketIsDisplay", bk.getIsdisplay());
			map.put("accessrights", bk.getAccessrights());
			map.put("zoneid", bk.getZoneid());
			map.put("state", bk.getState());
			map.put("createtime", bk.getCreatetime());
			map.put("bucketLastTime", bk.getLasttime());
			map.put("bucketRemark", bk.getRemark());
			bucketAcl.setBucketId(bk.getId());
		}

		// 获取当前Bucket的权限列表
		List<OssAcl> ossAcl = ossAclService.select(bucketAcl);
		for (OssAcl acl : ossAcl) {
			map.put("aclIsDisplay", acl.getIsdisplay());
			map.put("aclUserAuthorization", acl.getUserauthorization());
			map.put("aclPutObject", acl.getPutobject());
			map.put("aclGetObject", acl.getGetobject());
			map.put("aclDeleteObject", acl.getDeleteobject());
			map.put("aclListBucket", acl.getListbucket());
			map.put("aclDeleteBucket", acl.getDeletebucket());
			map.put("aclResource", acl.getResource());
			map.put("aclIsEffectRes", acl.getIseffectres());
			map.put("aclRefererIp", acl.getRefererip());
			map.put("aclIsEffectRefIp", acl.getIseffectrefip());
			map.put("aclState", acl.getState());
			map.put("aclCreateTime", acl.getCreatetime());
			map.put("aclRemark", acl.getRemark());
		}

		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		operatelog.setOperatestatus(1);
		// 保存系统日志
		operatelogService.insert(operatelog);
		rd.setData(map);
		return JSONUtils.createObjectJson(rd);
	}
	
	
	/**
	 * 强制删除bucket
	 * @throws Exception 
	 */
	@RequestMapping(value = "forceDestroyBucket", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String forceDestroyBucket(HttpServletRequest request) throws Exception{
		RspData rspData = new RspData();
		YrComper yrComper = ApiTool.getUserMsg(request);
		Map<String, String> maps = ApiTool.getBodyString(request);
		OssOperatelog operatelog = BucketAclUtil.setLog(request, "Bucket", "根据Bucket名获取信息", maps.toString(), (String) maps.get("zoneId"));
		
		String bucketName = maps.get("bucketName");
		String zoneId = maps.get("zoneId");
		
		if(!ParamIsNull.isNull(bucketName, zoneId)){
			operatelog.setOperatestatus(0);
			operatelog.setErrormessage(ExptNum.PARAM_NO_ALL.getDesc());
			operatelogService.insert(operatelog);
			rspData.setStatus(ExptNum.PARAM_NO_ALL.getCode() +"");
			rspData.setMsg(ExptNum.PARAM_NO_ALL.getDesc());
			return JSONUtils.createObjectJson(rspData);
		}
		
		OssObject delObjectParam = new OssObject();
		delObjectParam.setBucketName(bucketName);
		ossObjectService.delete(delObjectParam);
		
		OssBucket delBucketParam = new OssBucket();
		delBucketParam.setName(bucketName);
		ossBucketService.delete(delBucketParam);
		
		ConcurrentLinkedQueue<DestroyBucketBean> destroyBucketqueue = GlobalAttr.getInstance().getDestroyBucketqueue();
		destroyBucketqueue.add(new DestroyBucketBean(bucketName, yrComper.getCompanyid(), zoneId));
		rspData.setStatus(ExptNum.SUCCESS.getCode() + "");
		rspData.setMsg(ExptNum.SUCCESS.getDesc());
		
		operatelog.setOperatestatus(1);
		operatelogService.insert(operatelog);
		return JSONUtils.createObjectJson(rspData);
	}

	/**
	 * 同步bucket已使用容量
	 * @throws IOException 
	 */
	@RequestMapping(value = "synBucketCapacity", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String synBucketCapacity() {
		int threadNum = Integer
				.valueOf(Prop.getInstance().getPropertiesByPro("ruirados.properties", "destroyBucket.thread.num"));
		Map<String, OssZone> zoneMap = GlobalAttr.getInstance().getZoneMap();
		Collection<OssZone> zoneList = zoneMap.values();
		//遍历区域
		for (OssZone zone : zoneList) {
			String zoneId = zone.getZoneid();
			List<OssBucket> bucketList = ossBucketService.selectByParam("name", "zoneId = '" + zoneId + "'");
			//将bucketname 放入队列
			ConcurrentLinkedQueue<String> bucketNameQueue = new ConcurrentLinkedQueue<String>();
			for (OssBucket ossBucket : bucketList) {
				bucketNameQueue.offer(ossBucket.getName());
			}
			ExecutorService threadPool = Executors.newFixedThreadPool(threadNum);
			for (int i = 0; i < threadNum; i++) {
				log.debug("正在同步AccessKey");
				threadPool.submit(
						new SynBucketCapacityRunnable(ossBucketService, ossObjectService, bucketNameQueue, zoneId));
			}
			threadPool.shutdown();
		}

		RspData rspData = new RspData();
		rspData.setStatus(ExptNum.SUCCESS.getCode() + "");
		rspData.setMsg(ExptNum.SUCCESS.getDesc());
		return JSONUtils.createObjectJson(rspData);
	}
	
	/**
	 * 根据companyID 同步
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "ruiradosSynchronization", produces = "application/json; charset=utf-8")
	@ResponseBody
	public  String ruiradosSynchronization(HttpServletRequest  request) throws Exception{
		
//		Map<String, String> maps = ApiTool.getBodyString(request);
		RspData rd = new RspData();
		
		String companyId =  ApiTool.getUserMsg(request).getCompanyid();
		
		// 参数完整性判断
		if (!ParamIsNull.isNull(companyId)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rd);
		}
		
		List<UserCoreAccess> userCoreList = userCoreAccessService.select(new UserCoreAccess().setCompanyid(companyId));
		
		UserCoreAccess currentUser = userCoreList.get(0);
		
		// 获取数据库所有的Bucket
		List<OssBucket> bucketSum = ossBucketService.selectByParam(null, "companyid = '" + companyId + "'");
//				Queue<OssBucket> bucketQueue = new LinkedList<OssBucket>();
//				for (OssBucket bucket : bucketSum) {
//					bucketQueue.offer(bucket);
//				}

		List<OssZone> ossZoneList = ossZoneService.select(null);

		// 获取数据库所有的Object
		List<OssObject> objectSum = ossObjectService.selectByParam(null, "companyid = '" + companyId + "'");

		// 获取数据库所有的文件夹
		List<OssObject> sumDirList = ossObjectService.selectByParam(null, "companyid = '" + companyId + "' and isfile = 1");
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式
		// 格式化当前时间
		Date date = new Date();
		String dateStr = df.format(date);
		try {
			date = df.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		for (OssZone ossZone : ossZoneList) {
			// 获取Ceph连接
			AmazonS3 conn;

			AccessControlList acl = new AccessControlList();

			conn = RuiradosUtil.getConn(currentUser.getCephaccesskeyid(), currentUser.getCephaccesskeysecret(),
					ossZone.getServerip());
			
			// 得到当前主用户下的所有Bucket
			try {
				List<Bucket> buckets = conn.listBuckets();
				for (Bucket b : buckets) {
					String connBucketName = b.getName();
					int numB = 0;
					for (OssBucket bucket : bucketSum) {

						// 和数据库进行比较,有就移除，进行下轮比较
						if (connBucketName.equals(bucket.getName())) {
							bucketSum.remove(bucket);
							break;
						}

						// 如果没有就添加Bucket和当前权限，并清空Bucket权限
						if (!connBucketName.equals(bucket.getName()) && numB == bucketSum.size() - 1) {
							// 添加信息到数据库
							OssBucket bucket1 = new OssBucket();
							bucket1.setCode(Uuid.getGenerateCode());
							bucket1.setCompanyid(currentUser.getCompanyid());
							bucket1.setName(connBucketName);
							bucket1.setZoneid(ossZone.getZoneid());
							bucket1.setVersion(0);
							bucket1.setIsdisplay(1);
							bucket1.setState(1);
							int accessrights = RuiradosUtil.getAccessrights(conn.getBucketAcl(connBucketName)
									.getGrantsAsList().get(0).getPermission().toString());
							bucket1.setAccessrights(accessrights);
							bucket1.setCreatetime(date);
							ossBucketService.insert(bucket1);
							
							int bucketid = bucket1.getId();
//							acl.grantPermission(GroupGrantee.AllUsers, Permission.FullControl);
//							acl.setOwner(conn.getS3AccountOwner());
//							// 设置Bucket权限
//							conn.setBucketAcl(connBucketName, acl);

							// 得到对应的权限
							Map<String, Integer> param = RuiradosUtil.objectAcl(accessrights);
							OssAcl bucketAcl = new OssAcl();
							bucketAcl.setCode(Uuid.getGenerateCode());
							bucketAcl.setCompanyid(currentUser.getCompanyid());
							bucketAcl.setBucketId(bucketid);
							bucketAcl.setVersion(0);
							bucketAcl.setIsdisplay(1);
							bucketAcl.setZoneid(ossZone.getZoneid());
							bucketAcl.setUserauthorization("*");
							bucketAcl.setPutobject(param.get("putObject"));
							bucketAcl.setGetobject(param.get("getObject"));
							bucketAcl.setDeleteobject(param.get("deleteObject"));
							bucketAcl.setListbucket(param.get("listBucket"));
							bucketAcl.setDeletebucket(param.get("deleteBucket"));
							bucketAcl.setIseffectres(1);
							bucketAcl.setIseffectrefip(0);
							bucketAcl.setState(1);
							bucketAcl.setCreatetime(date);
							ossAclService.insert(bucketAcl);

							break;
						}
						numB++;
					}

					// 获取当前Bucket下的所有Object
					ObjectListing objs = conn.listObjects(connBucketName);
					List<S3ObjectSummary> sums = objs.getObjectSummaries();

					for (S3ObjectSummary s3 : sums) {
						int num = 0;
						for (OssObject object : objectSum) {

							// 和数据库进行比较，有就移除，并进行下轮比较
							if (object.getFilesrc().equals(s3.getKey())) {
								objectSum.remove(object);
								break;
							}

							// 如果没有就添加
							if (!object.getFilesrc().equals(s3.getKey()) && num == objectSum.size() - 1) {
								OssObject ossObject = new OssObject();
								OssObject f_Object = new OssObject();
								ossObject.setCode(Uuid.getGenerateCode());
								ossObject.setBucketName(connBucketName);
								ossObject.setCompanyid(currentUser.getCompanyid());
								ossObject.setFilesize((double) s3.getSize() / 1024);
								ossObject.setFilename(s3.getKey().substring(s3.getKey().lastIndexOf("/") + 1));
								ossObject.setFilesrc(s3.getKey());
								ossObject.setZoneid(ossZone.getZoneid());

								// 如果当前Object包含 / ，说明有父类关系，且父类必定是文件夹
								if (s3.getKey().contains("/")) {
									int m = 0;
									for (String str : s3.getKey().substring(0, s3.getKey().lastIndexOf("/"))
											.split("/")) {

										// 查看数据库中是否有当前文件夹，当m为第一级时说明当前文件夹没父类
										if (m == 0) {
											for (int u = 0; u < sumDirList.size(); u++) {
												if (sumDirList.get(u).getFilename().equals(str)) {
													sumDirList.remove(sumDirList.get(u));
													break;
												}
												if (!sumDirList.get(u).getFilename().equals(str)
														&& u == sumDirList.size() - 1) {
													OssObject now_Object = new OssObject();
													// 添加当前文件夹进数据库
													now_Object.setCode(Uuid.getGenerateCode());
													now_Object.setBucketName(connBucketName);
													now_Object.setCompanyid(currentUser.getCompanyid());
													now_Object.setFilesize(0.00);
													now_Object.setFilename(s3.getKey().split("/")[m]);
													now_Object.setFilesrc(s3.getKey().split("/")[m]+"/");
													now_Object.setZoneid(ossZone.getZoneid());
													now_Object.setIsfile(1);
													now_Object.setCreatetime(date);
													now_Object.setFId(0);
													ossObjectService.insert(now_Object);
													break;
												}
											}
										}

										if (m != 0) {
											for (int u = 0; u < sumDirList.size(); u++) {
												// 查看数据库中当前父节点的信息
												List<OssObject> f_ids = ossObjectService.select(
														new OssObject().setFilename(s3.getKey().split("/")[m - 1]));

												// 获取父节点的信息
												List<OssObject> object_id = ossObjectService.select(
														new OssObject().setFilename(s3.getKey().split("/")[m - 1]));

												if (sumDirList.get(u).getFilename().equals(str)) {

													// 查看数据库中父节点信息是否为当前文件夹的上级
													if (f_ids.get(0).getFilename().equals(s3.getKey().split("/")[m])) {
														sumDirList.remove(sumDirList.get(u));
														// 如果父节点信息一致就设置为父节点id
														break;
													} else {
														// 如果不一致，就获取Ceph里当前文件的上级，并取出最后添加进去的id
														int k = 0;
														if (object_id.size() > 1) {
															for (int j = 0; j < object_id.size(); j++) {
																if (j != object_id.size() - 1) {
																	int ago = object_id.get(j).getId();
																	int after = object_id.get(j + 1).getId();
																	if (ago < after) {
																		k = after;
																	}
																}
															}
														}

														// 获取上级fileName
														String strName = "";
														for (OssObject object3 : object_id) {
															if (object3.getId() == k) {
																strName = object3.getFilename();
															}
														}

														// 添加当前文件夹进数据库
														f_Object.setCode(Uuid.getGenerateCode());
														f_Object.setBucketName(connBucketName);
														f_Object.setCompanyid(currentUser.getCompanyid());
														f_Object.setFilesize(0.00);
														f_Object.setFilename(str);
														f_Object.setFilesrc(strName + "/" + str +"/");
														f_Object.setZoneid(ossZone.getZoneid());
														f_Object.setIsfile(1);
														f_Object.setCreatetime(date);
														f_Object.setFId(k);
														ossObjectService.insert(f_Object);
														break;
													}
												}
												if (!sumDirList.get(u).getFilename().equals(str)
														&& u == sumDirList.size() - 1) {
													int k = 0;
													if (object_id.size() > 1) {
														for (int j = 0; j < object_id.size(); j++) {
															if (j != object_id.size() - 1) {
																int ago = object_id.get(j).getId();
																int after = object_id.get(j + 1).getId();
																if (ago < after) {
																	k = after;
																}
															}
														}
													}

													// 获取上级fileName
													String strName = "";
													for (OssObject object3 : object_id) {
														if (object3.getId() == k) {
															strName = object3.getFilename();
														}
													}

													// 添加当前文件夹进数据库
													f_Object.setCode(Uuid.getGenerateCode());
													f_Object.setBucketName(connBucketName);
													f_Object.setCompanyid(currentUser.getCompanyid());
													f_Object.setFilesize(0.00);
													f_Object.setFilename(str);
													f_Object.setFilesrc(strName + "/" + str +"/");
													f_Object.setZoneid(ossZone.getZoneid());
													f_Object.setIsfile(1);
													f_Object.setCreatetime(date);
													f_Object.setFId(k);
													ossObjectService.insert(f_Object);
													break;
												}
											}
										}
										m++;
									}
								}
								// 判断当前文件是文件夹还是文件
								if (s3.getSize() == 0) {

								} else {
									ossObject.setIsfile(0);
									if (s3.getKey().contains("/")) {
										List<OssObject> select = null;
										if (s3.getKey().split("/").length >= 3) {
											select = ossObjectService.select(new OssObject()
													.setFilename(s3.getKey().substring(0, s3.getKey().lastIndexOf("/"))
															.substring(s3.getKey()
																	.substring(0,
																			s3.getKey().lastIndexOf("/"))
																	.lastIndexOf("/") + 1)));
										} else {
											select = ossObjectService.select(new OssObject().setFilename(
													s3.getKey().substring(0, s3.getKey().lastIndexOf("/"))));
										}
										ossObject.setFId(select.get(0).getId());
									} else {
										ossObject.setFId(0);
									}
									ossObject.setCreatetime(date);
									// 添加当前文件进数据库
									ossObjectService.insert(ossObject);
									break;
								}
							}
							num++;
						}
					}
				}
			} catch (SdkClientException e) {
				// TODO: handle exception
			}
			
		}
		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		return JSONUtils.createObjectJson(rd);
	}
}
