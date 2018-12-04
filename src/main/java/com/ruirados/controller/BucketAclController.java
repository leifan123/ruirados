package com.ruirados.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.CanonicalGrantee;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.ruirados.pojo.OssAcl;
import com.ruirados.pojo.OssBucket;
import com.ruirados.pojo.OssObject;
import com.ruirados.pojo.OssOperatelog;
import com.ruirados.pojo.RspData;
import com.ruirados.service.OssAclService;
import com.ruirados.service.OssBucketService;
import com.ruirados.service.OssObjectService;
import com.ruirados.service.OssOperatelogService;
import com.ruirados.service.UserCoreAccessService;
import com.ruirados.util.ApiTool;
import com.ruirados.util.BucketAclUtil;
import com.ruirados.util.Config;
import com.ruirados.util.ExptNum;
import com.ruirados.util.GetResult;
import com.ruirados.util.JSONUtils;
import com.ruirados.util.MappingConfigura;
import com.ruirados.util.ParamIsNull;
import com.ruirados.util.Uuid;
import com.yunrui.pojo.YrComper;

@Controller
@RequestMapping(MappingConfigura.BUCKET_ACL)
public class BucketAclController {

	@Autowired
	private OssOperatelogService operatelogService;

	@Autowired
	private OssAclService OssAclService;

	@Autowired
	private OssBucketService OssBucketService;

	@Autowired
	private OssObjectService OssObjectService;
	@Autowired
	UserCoreAccessService userCoreAccessService;

	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式

	private AccessControlList bucketAcc = new AccessControlList();

	private AccessControlList objectAcc = new AccessControlList();

	private AccessControlList acl = new AccessControlList();
	
	Logger log = Logger.getLogger(getClass());

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
	@RequestMapping(value = "createCustomAcl", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String createCustomAcl(HttpServletRequest request) throws Exception {

		YrComper yr = ApiTool.getUserMsg(request);
		String companyId = yr.getCompanyid();

		Map<String, Object> maps = ApiTool.getBodyObject(request);

		OssOperatelog operatelog = BucketAclUtil.setLog(request, "权限", "添加自定义权限", maps.toString(), (String) maps.get("zoneId"));
		
		String bucketName = (String) maps.get("bucketName");
		bucketName = bucketName.toLowerCase();
		String zoneId = (String) maps.get("zoneId");
		String userAuths = (String) maps.get("userAuths");
		List<String> customPermission = (List<String>) maps.get("customPermission");
		String objectNames = (String) maps.get("objectNames");
		String isOperation = (String) maps.get("isOperation");
		String isReferer = (String) maps.get("isReferer");
		String refereIp = (String) maps.get("refereIp");
		String bucketId = (String) maps.get("bucketId");

		RspData rd = new RspData();
		// 参数完整性判断
		if (!ParamIsNull.isNull(bucketName, userAuths, zoneId, customPermission, objectNames, isOperation, isReferer,
				bucketId)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			operatelog.setOperatestatus(2);
			operatelog.setErrormessage(Config.REQUEST_Param_IS_NULL);
			// 保存系统日志
			operatelogService.insert(operatelog);
			return JSONUtils.createObjectJson(rd);
		}

		// 是否通过白名单
		String ip = BucketAclUtil.getIpAddress(request);
		OssBucket bucketsRe = new OssBucket();
		bucketsRe.setName(bucketName);
		List<OssBucket> bucketRe = OssBucketService.select(bucketsRe);

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
				List<OssAcl> bucketAcls = OssAclService.select(bucketAclRe);

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

		// 清除不是自定义之前的权限数据库
		List<OssBucket> listOssBucket = OssBucketService.select(new OssBucket().setId(Integer.parseInt(bucketId)));
		int accessrights = listOssBucket.get(0).getAccessrights();
		if (accessrights != 4) {
			OssAclService.delete(new OssAcl().setBucketId(listOssBucket.get(0).getId()));
		}

		OssAcl bucketAcl = new OssAcl();
		Map<String, Object> map = new HashMap<String, Object>();

		AmazonS3 conn = BucketAclUtil.getConnByCache(companyId, zoneId, 0, true);

		bucketAcl.setCode(Uuid.getGenerateCode());
		bucketAcl.setCompanyid(companyId);
		bucketAcl.setBucketId(listOssBucket.get(0).getId());
		bucketAcl.setZoneid(zoneId);
		// 添加是否开启白名单
		bucketAcl.setIseffectrefip(Integer.parseInt(isReferer));
		// 添加白名单地址：由Java处理
		bucketAcl.setRefererip(refereIp);
		bucketAcl.setResource(objectNames);

		// 移除所有用户的权限(ceph)
		try {
			this.bucketAcc.grantPermission(GroupGrantee.AllUsers, Permission.FullControl);
			this.objectAcc.grantPermission(GroupGrantee.AllUsers, Permission.FullControl);
			this.bucketAcc.setOwner(conn.getS3AccountOwner());
			this.objectAcc.setOwner(conn.getS3AccountOwner());
			conn.setBucketAcl(bucketName, this.bucketAcc);
			ObjectListing listObjects = conn.listObjects(bucketName);
			List<S3ObjectSummary> sums = listObjects.getObjectSummaries();
			for (S3ObjectSummary s3 : sums) {
				try{
					conn.setObjectAcl(bucketName, s3.getKey(), this.objectAcc);
				}	catch (Exception e) {
					log.error(e);

				}
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}

		// 添加用户授权
		bucketAcl.setUserauthorization(userAuths);
		bucketAcl.setVersion(0);
		bucketAcl.setIsdisplay(1);
		bucketAcl.setState(1);


		// 给某个用户授权某个文件的权限
		if (customPermission != null || customPermission.size() > 0) {
			// customPermission = customPermission + ",";
			// String[] customPermissions = customPermission.split(",");
			// 自定义权限的开关
			boolean putO = false;
			boolean getO = false;
			boolean deleteO = false;
			boolean listB = false;
			boolean deleteB = false;
			// 设置权限
			for (int i = 0; i < customPermission.size(); i++) {
				if ("PutObject".toLowerCase().equals(customPermission.get(i).toLowerCase())) {
					bucketAcl.setPutobject(1);
					putO = true;
				}
				if ("GetObject".toLowerCase().equals(customPermission.get(i).toLowerCase())) {
					bucketAcl.setGetobject(1);
					getO = true;
				}
				if ("DeleteObject".toLowerCase().equals(customPermission.get(i).toLowerCase())) {
					bucketAcl.setDeleteobject(1);
					deleteO = true;
				}
				if ("ListBucket".toLowerCase().equals(customPermission.get(i).toLowerCase())) {
					bucketAcl.setListbucket(1);
					listB = true;
				}
				if ("DeleteBucket".toLowerCase().equals(customPermission.get(i).toLowerCase())) {
					bucketAcl.setDeletebucket(1);
					deleteB = true;
				}
				while (i == customPermission.size() - 1) {
					if (putO == false) {
						bucketAcl.setPutobject(0);
					}
					if (getO == false) {
						bucketAcl.setGetobject(0);
					}
					if (deleteO == false) {
						bucketAcl.setDeleteobject(0);
					}
					if (listB == false) {
						bucketAcl.setListbucket(0);
					}
					if (deleteB == false) {
						bucketAcl.setDeletebucket(0);
					}

					try {
						AccessControlList aclObject = new AccessControlList();
						if (userAuths.contains(",")) {
							for (String userId : userAuths.split(",")) {
								try{
									if (putO || deleteO || listB) {
										aclObject.grantPermission(new CanonicalGrantee(userId), Permission.Write);
									} else if (getO || deleteB) {
										aclObject.grantPermission(new CanonicalGrantee(userId), Permission.Read);
									} else if ((putO || deleteO && getO) || (listB && deleteB)) {
										aclObject.grantPermission(new CanonicalGrantee(userId), Permission.FullControl);
									}
								}	catch (Exception e) {
									
								}
							}
						} else {
							if (putO || deleteO || listB) {
								aclObject.grantPermission(GroupGrantee.AllUsers, Permission.Write);
							} else if (getO || deleteB) {
								aclObject.grantPermission(GroupGrantee.AllUsers, Permission.Read);
							} else if ((putO || deleteO && getO) || (listB && deleteB)) {
								aclObject.grantPermission(GroupGrantee.AllUsers, Permission.FullControl);
							}
						}
						aclObject.grantPermission(new CanonicalGrantee(companyId), Permission.FullControl);
						aclObject.setOwner(conn.getS3AccountOwner());
						for (String object : objectNames.split(",")) {
							OssObject ossobject = new OssObject();
							ossobject.setBucketName(bucketName);
							ossobject.setFilesrc(objectNames);
							List<OssObject> select = OssObjectService.select(ossobject);
							try{
								if(null == select  || select.size() == 0){
									
								}else{
									// 设置Object权限
									if (putO || deleteO || getO) {
										conn.setObjectAcl(bucketName, object, aclObject);
									} else {
										conn.setBucketAcl(bucketName, aclObject);
										break;
									}
								}
							}	catch (Exception e) {
								log.error(e);
							}
						}
					} catch (Exception e) {
						log.error(e);

					}
					i++;
				}
			}
		}
		// 格式化当前时间
		Date date = new Date();
		String dateStr = df.format(date);
		date = df.parse(dateStr);

		// 是否为可操作
		bucketAcl.setIseffectres(Integer.parseInt(isOperation));
		bucketAcl.setCreatetime(date);

		OssAclService.insert(bucketAcl);

		OssBucketService.updateByParam("accessrights=4, lastTime='" + dateStr + "' where name='" + bucketName + "'");

		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		operatelog.setOperatestatus(1);
		// 保存系统日志
		operatelogService.insert(operatelog);
		return JSONUtils.createObjectJson(rd);
	}

	/**
	 * 根据Code查询自定义权限
	 * 
	 * @param zoneId
	 * @param bucketId
	 * @return
	 */
	@RequestMapping(value = "selectFromCode", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String selectFromBucketId(HttpServletRequest request) throws Exception {

		Map<String, Object> maps = ApiTool.getBodyObject(request);

		OssOperatelog operatelog = BucketAclUtil.setLog(request, "权限", "根据Code查询自定义权限", maps.toString(), (String) maps.get("zoneId"));

		String bucketName = (String) maps.get("bucketName");
		String code = (String) maps.get("code");

		RspData rd = new RspData();
		Map<String, Object> map = new HashMap<String, Object>();
		// 参数完整性判断
		if (!ParamIsNull.isNull(code)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			operatelog.setOperatestatus(2);
			operatelog.setErrormessage(Config.REQUEST_Param_IS_NULL);
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
		List<OssBucket> bucketRe = OssBucketService.select(bucketsRe);

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
				List<OssAcl> bucketAcls = OssAclService.select(bucketAclRe);

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

		OssAcl ossAcl = new OssAcl();
		ossAcl.setCode(code);

		List<OssAcl> listOssAcl = OssAclService.select(ossAcl);
		map.put("listOssAcl", listOssAcl);

		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		operatelog.setOperatestatus(1);
		// 保存系统日志
		operatelogService.insert(operatelog);
		rd.setData(map);
		return JSONUtils.createObjectJson(rd);
	}

	/**
	 * 修改自定义权限
	 * 
	 * @param code
	 * @param userAuths
	 * @param customPermission
	 * @param objectNames
	 * @param isOperation
	 * @param isReferer
	 * @param refereIp
	 * @param bucketId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "updateFromCode", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String updateFromBucketId(HttpServletRequest request) throws Exception {

		Map<String, Object> maps = ApiTool.getBodyObject(request);

		OssOperatelog operatelog = BucketAclUtil.setLog(request, "权限", "修改自定义权限", maps.toString(), (String) maps.get("zoneId"));

		String bucketName = (String) maps.get("bucketName");
		String code = (String) maps.get("code");
		String userAuths = (String) maps.get("userAuths");
		List<String> customPermission = (List<String>) maps.get("customPermission");
		String objectNames = (String) maps.get("objectNames");
		String isOperation = (String) maps.get("isOperation");
		String isReferer = (String) maps.get("isReferer");
		String refereIp = (String) maps.get("refereIp");

		RspData rd = new RspData();
		OssAcl ossAcl = new OssAcl();
		// 参数完整性判断
		if (!ParamIsNull.isNull(code, userAuths, customPermission, objectNames, isOperation, isReferer)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			operatelog.setOperatestatus(2);
			operatelog.setErrormessage(Config.REQUEST_Param_IS_NULL);
			// 保存系统日志
			operatelogService.insert(operatelog);
			return JSONUtils.createObjectJson(rd);
		}

		// 是否通过白名单
		YrComper yr = ApiTool.getUserMsg(request);
		String companyId = yr.getCompanyid();
		AmazonS3 conn = BucketAclUtil.getConnByCache(companyId, (String) maps.get("zoneId"), 0, true);
		String ip = BucketAclUtil.getIpAddress(request);
		OssBucket bucketsRe = new OssBucket();
		bucketsRe.setName(bucketName);
		List<OssBucket> bucketRe = OssBucketService.select(bucketsRe);

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
				List<OssAcl> bucketAcls = OssAclService.select(bucketAclRe);

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

		ossAcl.setCode(code);
		// 添加是否开启白名单
		ossAcl.setIseffectrefip(Integer.parseInt(isReferer));
		// 添加用户授权
		ossAcl.setUserauthorization(userAuths);
		// 是否为可操作
		ossAcl.setIseffectres(Integer.parseInt(isOperation));
		// 添加白名单地址：由Java处理
		ossAcl.setRefererip(refereIp);
		ossAcl.setResource(objectNames);
		
		// 给某个用户授权某个文件的权限
		if (customPermission != null || customPermission.size() > 0) {
			// customPermission = customPermission + ",";
			// String[] customPermissions = customPermission.split(",");
			// 自定义权限的开关
			boolean putO = false;
			boolean getO = false;
			boolean deleteO = false;
			boolean listB = false;
			boolean deleteB = false;
			// 设置权限
			for (int i = 0; i < customPermission.size(); i++) {
				if ("PutObject".toLowerCase().equals(customPermission.get(i).toLowerCase())) {
					ossAcl.setPutobject(1);
					putO = true;
				}
				if ("GetObject".toLowerCase().equals(customPermission.get(i).toLowerCase())) {
					ossAcl.setGetobject(1);
					getO = true;
				}
				if ("DeleteObject".toLowerCase().equals(customPermission.get(i).toLowerCase())) {
					ossAcl.setDeleteobject(1);
					deleteO = true;
				}
				if ("ListBucket".toLowerCase().equals(customPermission.get(i).toLowerCase())) {
					ossAcl.setListbucket(1);
					listB = true;
				}
				if ("DeleteBucket".toLowerCase().equals(customPermission.get(i).toLowerCase())) {
					ossAcl.setDeletebucket(1);
					deleteB = true;
				}
				while (i == customPermission.size() - 1) {
					if (putO == false) {
						ossAcl.setPutobject(0);
					}
					if (getO == false) {
						ossAcl.setGetobject(0);
					}
					if (deleteO == false) {
						ossAcl.setDeleteobject(0);
					}
					if (listB == false) {
						ossAcl.setListbucket(0);
					}
					if (deleteB == false) {
						ossAcl.setDeletebucket(0);
					}

					try {
						AccessControlList aclObject = new AccessControlList();
						if (userAuths.contains(",")) {
							for (String userId : userAuths.split(",")) {
								if (putO || deleteO || listB) {
									aclObject.grantPermission(new CanonicalGrantee(userId), Permission.Write);
								} else if (getO || deleteB) {
									aclObject.grantPermission(new CanonicalGrantee(userId), Permission.Read);
								} else if ((putO || deleteO && getO) || (listB && deleteB)) {
									aclObject.grantPermission(new CanonicalGrantee(userId), Permission.FullControl);
								}
							}
						} else {
							if (putO || deleteO || listB) {
								aclObject.grantPermission(GroupGrantee.AllUsers, Permission.Write);
							} else if (getO || deleteB) {
								aclObject.grantPermission(GroupGrantee.AllUsers, Permission.Read);
							} else if ((putO || deleteO && getO) || (listB && deleteB)) {
								aclObject.grantPermission(GroupGrantee.AllUsers, Permission.FullControl);
							}
						}
						aclObject.grantPermission(new CanonicalGrantee(companyId), Permission.FullControl);
						aclObject.setOwner(conn.getS3AccountOwner());
						for (String object : objectNames.split(",")) {
							OssObject ossobject = new OssObject();
							ossobject.setBucketName(bucketName);
							ossobject.setFilesrc(objectNames);
							List<OssObject> select = OssObjectService.select(ossobject);
							try{
								if(select == null || select.size() == 0){
									
								}else{
									// 设置Object权限
									if (putO || deleteO || getO) {
										conn.setObjectAcl(bucketName, object, aclObject);
									} else {
										conn.setBucketAcl(bucketName, aclObject);
										break;
									}
								}
							}	catch (Exception e) {
								log.error(e);
							}
						}
					} catch (Exception e) {
						log.error(e);

					}
					i++;
				}
			}
		}

		OssAclService.update(ossAcl);
		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		operatelog.setOperatestatus(1);
		// 保存系统日志
		operatelogService.insert(operatelog);
		return JSONUtils.createObjectJson(rd);
	}

	/**
	 * 根据Code删除自定义权限
	 * 
	 * @param zoneId
	 * @param bucketId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "deleteFromBucketId", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String deleteFromBucketId(HttpServletRequest request) throws Exception {

		Map<String, String> maps = ApiTool.getBodyString(request);

		OssOperatelog operatelog = BucketAclUtil.setLog(request, "权限", "根据Code删除自定义权限", maps.toString(), (String) maps.get("zoneId"));

		String bucketName = (String) maps.get("bucketName");
		String code = (String) maps.get("code");
		String userAuths = (String) maps.get("userAuths");
		String objectNames = (String) maps.get("objectNames");

		RspData rd = new RspData();
		// 参数完整性判断
		if (!ParamIsNull.isNull(code)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			operatelog.setOperatestatus(2);
			operatelog.setErrormessage(Config.REQUEST_Param_IS_NULL);
			// 保存系统日志
			operatelogService.insert(operatelog);
			return JSONUtils.createObjectJson(rd);
		}

		// 是否通过白名单
		YrComper yr = ApiTool.getUserMsg(request);
		String companyId = yr.getCompanyid();

		AmazonS3 conn = BucketAclUtil.getConnByCache(companyId, (String) maps.get("zoneId"), 0, true);

		String ip = BucketAclUtil.getIpAddress(request);
		OssBucket bucketsRe = new OssBucket();
		bucketsRe.setName(bucketName);
		List<OssBucket> bucketRe = OssBucketService.select(bucketsRe);

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
				List<OssAcl> bucketAcls = OssAclService.select(bucketAclRe);

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
		try {
			AccessControlList aclObject = new AccessControlList();
			if (userAuths.contains(",")) {
				for (String userId : userAuths.split(",")) {
					aclObject.grantPermission(new CanonicalGrantee(userId), Permission.FullControl);
				}
			} else {
				aclObject.grantPermission(GroupGrantee.AllUsers, Permission.FullControl);
			}
			aclObject.grantPermission(new CanonicalGrantee(companyId), Permission.FullControl);
			aclObject.setOwner(conn.getS3AccountOwner());
			for (String object : objectNames.split(",")) {
				try {
					conn.setObjectAcl(bucketName, object, aclObject);
				} catch (Exception e) {
					log.error(e);

				}
				try {
					conn.setBucketAcl(object, aclObject);
				} catch (Exception e) {
					log.error(e);

				}
			}
		} catch (Exception e) {
			log.error(e);
		}
		OssAcl ossAcl = new OssAcl();
		ossAcl.setCode(code);
		OssAclService.delete(ossAcl);

		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		operatelog.setOperatestatus(1);
		// 保存系统日志
		operatelogService.insert(operatelog);
		return JSONUtils.createObjectJson(rd);
	}

	/**
	 * 权限切换
	 * 
	 * @param request
	 * @param zoneId
	 * @param accessrights
	 * @param bucketId
	 * @param bucketName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "aclCut", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String aclCut(HttpServletRequest request) throws Exception {

		Map<String, String> maps = ApiTool.getBodyString(request);
		YrComper yr = ApiTool.getUserMsg(request);
		String companyId = yr.getCompanyid();

		OssOperatelog operatelog = BucketAclUtil.setLog(request, "权限", "权限切换", maps.toString(), (String) maps.get("zoneId"));

		String zoneId = (String) maps.get("zoneId");
		String accessrights = (String) maps.get("accessrights");
		String bucketId = (String) maps.get("bucketId");
		String bucketName = (String) maps.get("bucketName");

		RspData rd = new RspData();
		// Map<String, Object> map = BucketAclUtil.get(request,
		// userCoreAccessService, zoneId);
		AmazonS3 conn = BucketAclUtil.getConnByCache(companyId, zoneId, 0, true);

		// 参数完整性判断
		if (!ParamIsNull.isNull(zoneId, accessrights)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			operatelog.setOperatestatus(2);
			operatelog.setErrormessage(Config.REQUEST_Param_IS_NULL);
			// 保存系统日志
			operatelogService.insert(operatelog);
			return JSONUtils.createObjectJson(rd);
		}

		// 是否通过白名单

		String ip = BucketAclUtil.getIpAddress(request);
		OssBucket bucketsRe = new OssBucket();
		bucketsRe.setName(bucketName);
		List<OssBucket> bucketRe = OssBucketService.select(bucketsRe);

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
				List<OssAcl> bucketAcls = OssAclService.select(bucketAclRe);

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

		OssAclService.delete(new OssAcl().setBucketId(Integer.parseInt(bucketId)));

		// 移除所有用户的权限(ceph)
		try {
			this.bucketAcc.grantPermission(new CanonicalGrantee(companyId), Permission.FullControl);
			this.objectAcc.grantPermission(new CanonicalGrantee(companyId), Permission.FullControl);
			this.bucketAcc.setOwner(conn.getS3AccountOwner());
			this.objectAcc.setOwner(conn.getS3AccountOwner());
			conn.setBucketAcl(bucketName, this.bucketAcc);
			ObjectListing listObjects = conn.listObjects(bucketName);
			List<S3ObjectSummary> sums = listObjects.getObjectSummaries();
			for (S3ObjectSummary s3 : sums) {
				try{
					conn.setObjectAcl(bucketName, s3.getKey(), this.objectAcc);
				}	catch (Exception e) {
					log.error(e);
				}
			}
		} catch (Exception e) {

			log.error(e);		

			e.printStackTrace();

		}

		AccessControlList aclAcp = new AccessControlList();
		if (accessrights.equals("1")) {
			// new CanonicalGrantee(companyId)
			aclAcp.grantPermission(new CanonicalGrantee(companyId), Permission.FullControl);
		} else if (accessrights.equals("2")) {
			aclAcp.grantPermission(new CanonicalGrantee(companyId), Permission.Write);
			aclAcp.grantPermission(GroupGrantee.AllUsers, Permission.Read);
		} else {
			aclAcp.grantPermission(GroupGrantee.AllUsers, Permission.FullControl);
		}

		aclAcp.setOwner(conn.getS3AccountOwner());
		// 设置Bucket权限
		conn.setBucketAcl(bucketName, aclAcp);

		// 格式化当前时间
		Date date = new Date();
		String dateStr = df.format(date);
		date = df.parse(dateStr);

		Map<String, Integer> param = BucketAclUtil.objectAcl(Integer.parseInt(accessrights));
		OssAcl bucketAcl = new OssAcl();
		bucketAcl.setCode(Uuid.getGenerateCode());
		bucketAcl.setCompanyid(companyId);
		bucketAcl.setBucketId(Integer.parseInt(bucketId));
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

		OssAclService.insert(bucketAcl);

		OssBucketService.updateByParam(
				"accessrights= " + accessrights + ", lastTime='" + dateStr + "' where name='" + bucketName + "'");

		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		operatelog.setOperatestatus(1);
		// 保存系统日志
		operatelogService.insert(operatelog);
		return JSONUtils.createObjectJson(rd);
	}

	/**
	 * 获取所有自定义权限
	 * 
	 * @param zoneId
	 * @param bucketId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "selectAclAll", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String selectAclAll(HttpServletRequest request) throws Exception {

		Map<String, String> maps = ApiTool.getBodyString(request);

		OssOperatelog operatelog = BucketAclUtil.setLog(request, "权限", "获取所有自定义权限", maps.toString(), (String) maps.get("zoneId"));

		String bucketName = (String) maps.get("bucketName");
		String zoneId = (String) maps.get("zoneId");
		String bucketId = (String) maps.get("bucketId");

		RspData rd = new RspData();
		Map<String, Object> map = new HashMap<String, Object>();
		// 参数完整性判断
		if (!ParamIsNull.isNull(zoneId, bucketId)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			operatelog.setOperatestatus(2);
			operatelog.setErrormessage(Config.REQUEST_Param_IS_NULL);
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
		List<OssBucket> bucketRe = OssBucketService.select(bucketsRe);

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
				List<OssAcl> bucketAcls = OssAclService.select(bucketAclRe);

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

		OssAcl ossAcl = new OssAcl();
		ossAcl.setBucketId(Integer.parseInt(bucketId));
		ossAcl.setZoneid(zoneId);

		List<OssAcl> list = OssAclService.select(ossAcl);

		if (list == null || list.size() == 0) {
			rd.setStatus(ExptNum.EMPTY.getCode() + "");
			rd.setMsg(ExptNum.EMPTY.getDesc());
			operatelog.setOperatestatus(2);
			operatelog.setErrormessage(ExptNum.EMPTY.getDesc());
			// 保存系统日志
			operatelogService.insert(operatelog);
			return JSONUtils.createObjectJson(rd);
		}

		map.put("list", list);
		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		operatelog.setOperatestatus(1);
		// 保存系统日志
		operatelogService.insert(operatelog);
		rd.setData(map);
		return JSONUtils.createObjectJson(rd);
	}
}
