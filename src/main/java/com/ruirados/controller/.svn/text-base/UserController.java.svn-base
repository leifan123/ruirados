/**
 * @author YuLong.Dai
 * @time 2017年3月31日 上午9:59:15
 * TODO
 */
package com.ruirados.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.twonote.rgwadmin4j.RgwAdmin;
import org.twonote.rgwadmin4j.model.CredentialType;
import org.twonote.rgwadmin4j.model.S3Credential;
import org.twonote.rgwadmin4j.model.SubUser;
import org.twonote.rgwadmin4j.model.User;

import com.ruirados.exception.ExcepEnum;
import com.ruirados.pojo.OssArrearage;
import com.ruirados.pojo.OssFlux;
import com.ruirados.pojo.OssOperatelog;
import com.ruirados.pojo.OssUserDomain;
import com.ruirados.pojo.OssUserFlux;
import com.ruirados.pojo.OssZone;
import com.ruirados.pojo.RspData;
import com.ruirados.pojo.UserAccess;
import com.ruirados.pojo.UserCoreAccess;
import com.ruirados.pojo.UserTypeSource;
import com.ruirados.service.GetfreevminformationService;
import com.ruirados.service.OssArrearageService;
import com.ruirados.service.OssFluxService;
import com.ruirados.service.OssOperatelogService;
import com.ruirados.service.OssUserDomainService;
import com.ruirados.service.OssUserFluxService;
import com.ruirados.service.OssZoneService;
import com.ruirados.service.UserAccessService;
import com.ruirados.service.UserCoreAccessService;
import com.ruirados.service.UserTypeSourceService;
import com.ruirados.service.YrAuthlogService;
import com.ruirados.service.YrComperService;
import com.ruirados.util.ApiTool;
import com.ruirados.util.BucketAclUtil;
import com.ruirados.util.Config;
import com.ruirados.util.DesUtils;
import com.ruirados.util.ExptNum;
import com.ruirados.util.FcUtil;
import com.ruirados.util.GetResult;
import com.ruirados.util.GlobalAttr;
import com.ruirados.util.HttpReq;
import com.ruirados.util.JSONUtils;
import com.ruirados.util.ParamIsNull;
import com.ruirados.util.TimeUtil;
import com.ruirados.util.Uuid;
import com.yunrui.pojo.Getfreevminformation;
import com.yunrui.pojo.YrAuthlog;
import com.yunrui.pojo.YrComper;


/**
 * @author YuLong.Dai
 * @time 2017年3月31日 上午9:59:15
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
	private Logger log = Logger.getLogger(UserController.class);
	@Autowired
	private OssOperatelogService operatelogService;
	@Autowired
	private UserTypeSourceService userTypeSourceService;
	
	@Autowired
	private YrComperService yrComperService;
	@Autowired
	private UserCoreAccessService userCoreAccessService;
	@Autowired
	private UserAccessService userAccessSerivce;
	@Autowired
	private OssZoneService ossZoneService;
	@Autowired
	private OssOperatelogService ossOperatelogService;
	@Autowired
	private OssUserDomainService ossUserDomainService;
	@Autowired
	private GetfreevminformationService getfreevminformationService;
	@Autowired
	private YrAuthlogService yrAuthlogService;
	@Autowired
	private OssFluxService ossFluxService;
	@Autowired
	private OssUserFluxService ossUserFluxService;
	
	@Autowired
	private OssArrearageService arrearageService;
	
	public Boolean judgeApiSerect(String companyId, String secret){
		List<UserCoreAccess> userCoreAccessList = userCoreAccessService.selectByParam(null, "apiaccesskeysecret = '"+secret+"'");
		if(userCoreAccessList.isEmpty()){
			return false;
		} else {
			UserCoreAccess userCoreAccess = userCoreAccessList.get(0);
			if(companyId.equals(userCoreAccess.getCompanyid())){
				return true;
			}
		}
		List<UserAccess> userAccessList = userAccessSerivce.selectByParam(null, "accesskeysecret = '" + secret + "'");
		if(userAccessList.isEmpty()){
			return false;
		}
		for(UserAccess userAccess : userAccessList){
			if(companyId.equals(userAccess.getCompanyid())){
				return true;
			}
		}
		
		return false;
	}

	/**
	 * @Title: GetUserInfo @Description: 获取用户信息 @author YaNan.Guan @param
	 *         request @param response @return @return String @throws
	 */
	@RequestMapping(value = "/getUserInfo", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String GetUserInfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result_map = new HashMap<String, Object>();
		YrComper comper = ApiTool.getUserMsg(request);
		
		List<Getfreevminformation> list_getfree = this.getfreevminformationService.selectBySql(
				"select  a.id,case when b.companyType is null then 0 else b.companyType  end as companyType from activity as a LEFT JOIN getfreevminformation as b on a.id=b.activityNum and companyId='"
						+ comper.getCompanyid() + "'");

		YrComper comPerinfo = new YrComper();
		comPerinfo.setRealname(comper.getRealname());
		comPerinfo.setActivityInfo(list_getfree);
		String telephone = comper.getPhone();

		if (telephone == null || telephone.isEmpty()) {
		} else {
			comPerinfo.setPhone(telephone.substring(0, 3) + "****" + telephone.substring(7, 11));
		}
		comPerinfo.setLoginname(comper.getLoginname());
		comPerinfo.setAvatar(comper.getAvatar());
		comPerinfo.setCountry(comper.getCountry());
		comPerinfo.setProvince(comper.getProvince());
		comPerinfo.setCity(comper.getCity());
		comPerinfo.setAddress(comper.getAddress());
		comPerinfo.setIsdirector(comper.getIsdirector());
		comPerinfo.setPersonalauth(comper.getPersonalauth());
		comPerinfo.setCompanyauth(comper.getCompanyauth());
		comPerinfo.setEmailauth(comper.getEmailauth());
		comPerinfo.setDefaultzoneid(comper.getDefaultzoneid());
		comPerinfo.setDefaultzonename(comper.getDefaultzonename());
		comPerinfo.setCompanyid(comper.getCompanyid());
		result_map.put("status", GetResult.SUCCESS_STATUS);
		result_map.put("message", Config.REQUEST_SUCCESS);
		result_map.put("result", comPerinfo);

		List<YrAuthlog> yrAuthList = yrAuthlogService.select(new YrAuthlog().setCompanyid(comper.getCompanyid()));

		YrAuthlog yrAuth = null;

		try {

			if (!yrAuthList.isEmpty()) {
				yrAuth = yrAuthList.get(0);
				String ps = yrAuth.getPersonalnumber();
				String phone = comper.getPhone();
				if (phone != null && !phone.isEmpty() && phone.length() >= 11) {
					yrAuth.setPhone(phone.substring(0, 3) + "****" + phone.substring(7, 11));
				}
				if (ps != null && !ps.isEmpty()) {
					yrAuth.setPersonalnumber(ps.substring(0, 6) + "********" + ps.substring(14, ps.length()));
				}
			}

		} catch (Exception e) {
			log.error(e);
			// TODO: handle exception
		}

		result_map.put("authInfo", yrAuth);

		return JSONUtils.createObjectJson(result_map);
	}
	/**
	 * 获取用户token
	 * 
	 * @param request
	 * @param response
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value = "/getToken", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getToken(HttpServletRequest request, HttpServletResponse response, String companyId, String secret)
			throws Exception {

		// String companyId = ApiTool.getYrComer(request);
		
		UserCoreAccess queryParam = new UserCoreAccess();
		queryParam.setApiaccesskeysecret(secret);
		List<UserCoreAccess> userAccessList = userCoreAccessService.select(queryParam);
		// 判空
		if (FcUtil.checkIsNull(companyId) || FcUtil.checkIsNull(secret)) {

			Map<String, Object> result_map = FcUtil.getResult(ExcepEnum.ERROR_SY_ISNOTNULL.VAL);
			return JSONUtils.createObjectJson(result_map);
		}
/*
		// 用户不存在
		if (userAccessList.size() == 0) {
			Map<String, Object> result_map = FcUtil.getResult(ExcepEnum.ERROR_CM_NOUSER.VAL);

			return JSONUtils.createObjectJson(result_map);

		}*/
		// 密钥错误
		if (!judgeApiSerect(companyId, secret)) {

			Map<String, Object> result_map = FcUtil.getResult(ExcepEnum.ERROR_CM_MYSB.VAL);

			return JSONUtils.createObjectJson(result_map);
		}
		String token_md = (new Date().getTime() / 1000) + "#" + companyId;
		String token = DesUtils.getInstance().encrypt(token_md);

		Map<String, String> userTokenMap = GlobalAttr.getInstance().getUserTokenMap();

		userTokenMap.put(companyId, token);

		Map<String, Object> result_map = FcUtil.getResult(ExcepEnum.SUCCESS.VAL);

		result_map.put("token", token);

		return JSONUtils.createObjectJson(result_map);

	}

	/**
	 * 创建对象存储用户
	 * 
	 * @param request
	 * @param response
	 * @param companyId
	 * @param zoneid
	 * @return
	 */
	@RequestMapping(value = "/createUserAcess", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String createUserAcess(HttpServletRequest request,HttpServletResponse response) 
			throws Exception {
		YrComper yr = ApiTool.getUserMsg(request);
		String companyId = yr.getCompanyid();

		OssOperatelog operatelog = new OssOperatelog();
		operatelog.setOperatetarget("用户");
		operatelog.setOperatetype("创建对象存储用户");
		operatelog.setOperatedes("创建对象存储用户");

//		operatelog.setZoneid((String) maps.get(yr.getz);

		// 设置公司ID
		operatelog.setCompanyid(ApiTool.getUserMsg(request).getCompanyid());
		// 设置IP地址
		operatelog.setOperatorip(BucketAclUtil.getIpAddress(request));
		Map<String, String> reqParam = ApiTool.getBodyString(request);

		OssOperatelog ossOperatelog = new OssOperatelog();
		ossOperatelog.setCompanyid(companyId);
		ossOperatelog.setOperatorip(BucketAclUtil.getIpAddress(request));

		// 系统当前时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式
		Date date1 = new Date();
		String str = df.format(date1);
		operatelog.setOperatortime(df.parse(str));

		List<UserTypeSource> userTypeSource = userTypeSourceService.selectByParam("", " companyId='" + companyId + "'");
		int accesskeyNum = userAccessSerivce.selectByParam("count(id) as id", " companyId='" + companyId + "'").get(0).getId();
		
		int maxAccessKeyNum = 0;
		try {
			maxAccessKeyNum = userTypeSource.get(0).getMaxossaccesskey();
		} catch (Exception e) {
			maxAccessKeyNum = 5;
		}
		if (maxAccessKeyNum <= accesskeyNum) {
			ossOperatelog.setOperatestatus(2);
			ossOperatelog.setErrormessage(ExptNum.QUOTA_IS_FULL.getDesc());
			ossOperatelogService.insert(ossOperatelog);
			RspData rspData = new RspData();
			rspData.setStatus(ExptNum.QUOTA_IS_FULL.getCode() + "");
			rspData.setMsg(ExptNum.QUOTA_IS_FULL.getDesc());
			return JSONUtils.createObjectJson(rspData);

		}

		// 判断用户是否存在user_core_access
		List<UserCoreAccess> userCoreAccessList = userCoreAccessService.selectByParam(null,
				"companyid='" + companyId + "'");
		Date now = new Date();

		Map<String, RgwAdmin> rgwAdminMap = GlobalAttr.getInstance().getRgwAdminMap();
		RgwAdmin rgwAdmin = null;
		// 确保密钥只插入一次
		boolean isFirstAccess = true;
		boolean isFirstCoreAccess = true;
		Integer maxSize = 0;
		try {
			maxSize = userTypeSource.get(0).getMaxossstore();
		} catch (Exception e) {
			maxSize = 20;
		}
		
		if (StringUtils.isEmpty(userCoreAccessList.get(0).getCephaccesskeysecret())) {
			String coreAccessKey = "";
			String coreSecretKey = "";
			for (Entry<String, RgwAdmin> rgwAdminEntry : rgwAdminMap.entrySet()) {
				rgwAdmin = rgwAdminEntry.getValue();

				if (isFirstCoreAccess) {
					Map<String, String> params = new HashMap<String, String>();
					params.put("user-caps", "usage=read, write");
					User user = rgwAdmin.createUser(companyId, params);
					List<S3Credential> credentialList = user.getS3Credentials();

					for (S3Credential credential : credentialList) {
						if(companyId.equals(credential.getUserId())) {
							coreAccessKey = credential.getAccessKey();
							coreSecretKey = credential.getSecretKey();
							break;
						}
					}
					rgwAdmin.setUserQuota(companyId, -1, 1024 * 1024 * maxSize);
					isFirstCoreAccess = false;
				} else {
					Map<String, String> params = new HashMap<String, String>();
					params.put("user-caps", "usage=read, write");
					params.put("access-key", coreAccessKey);
					params.put("secret-key", coreSecretKey);
					rgwAdmin.createUser(companyId, params);
					rgwAdmin.setUserQuota(companyId, -1, 1024 * 1024 * maxSize);
				}

			}
			UserCoreAccess userCoreAccess = new UserCoreAccess();
			userCoreAccess.setCompanyid(companyId);
			userCoreAccess.setCephaccesskeyid(coreAccessKey);
			userCoreAccess.setCephaccesskeysecret(coreSecretKey);
			userCoreAccess.setStatus(1);
			userCoreAccess.setCreatetime(now);
			userCoreAccess.setUserid(companyId);
			userCoreAccessService.updateByCompanyId(userCoreAccess);
		}
		
		String accessKey = "";
		String secretKey = "";
//		Integer userAccessNum = userAccessSerivce.countByParam("companyId='" + companyId + "'");
//		String subuuid = companyId + "_" + (userAccessNum + 1);
		String subuuid = TimeUtil.getTime();
		for (Entry<String, RgwAdmin> rgwAdminEntry : rgwAdminMap.entrySet()) {
			rgwAdmin = rgwAdminEntry.getValue();
			if(isFirstAccess) {
				SubUser createSsubUser = rgwAdmin.createSubUser(companyId, subuuid, SubUser.Permission.FULL,
						CredentialType.S3);
				List<S3Credential> credentialList = rgwAdmin.createS3CredentialForSubUser(companyId, subuuid);
				S3Credential credential = credentialList.get(0);
				accessKey = credential.getAccessKey();
				secretKey = credential.getSecretKey();
				isFirstAccess = false;
			} else {
				rgwAdmin.createSubUser(companyId, subuuid, SubUser.Permission.FULL,
						CredentialType.S3);
				rgwAdmin.createS3CredentialForSubUser(companyId, subuuid, accessKey, secretKey);
			}
			
		}
		UserAccess userAccess = new UserAccess();
		userAccess.setUserid(subuuid);
		userAccess.setAccesskeyid(accessKey);
		userAccess.setAccesskeysecret(secretKey);
		userAccess.setCompanyid(companyId);
		userAccess.setCreatetime(now);
		userAccess.setStatus(1);
		userAccess.setIsdisplay(1);
		userAccessSerivce.insert(userAccess);
		

		ossOperatelog.setOperatestatus(1);
		ossOperatelogService.insert(ossOperatelog);

		RspData rspData = new RspData();
		rspData.setStatus(ExptNum.SUCCESS.getCode() + "");
		rspData.setMsg(ExptNum.SUCCESS.getDesc());
		operatelog.setOperatestatus(1);
		// 保存系统日志
		operatelogService.insert(operatelog);
		return JSONUtils.createObjectJson(rspData);
	}

	/**
	 * 删除 accessKey
	 * 
	 * @param accessKeyID
	 * @return
	 */
	@RequestMapping(value = "/deleteUserAcess", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String deleteUserAcess(HttpServletRequest request) throws Exception {
		
		Map<String, String> maps = ApiTool.getBodyString(request);
		
		OssOperatelog operatelog = new OssOperatelog();
		operatelog.setOperatetarget("用户");
		operatelog.setOperatetype("删除 accessKey");
		operatelog.setOperatedes(maps.toString());
		
		operatelog.setZoneid((String) maps.get("zoneId"));
		
		//设置公司ID
		operatelog.setCompanyid(ApiTool.getUserMsg(request).getCompanyid());
		// 设置IP地址
		operatelog.setOperatorip(BucketAclUtil.getIpAddress(request));

		// 系统当前时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式
		Date date1 = new Date();
		String str = df.format(date1);
		operatelog.setOperatortime(df.parse(str));
		YrComper yr = ApiTool.getUserMsg(request);
		
		Map<String, String> reqParam = ApiTool.getBodyString(request);
		String accessKeyID = (String) reqParam.get("accessKeyID");
//		String zoneId = (String) reqParam.get("zoneId");
		
		if(!ParamIsNull.isNull(accessKeyID)){
			RspData rspData = new RspData();
			rspData.setStatus(ExptNum.PARAM_NO_ALL.getCode() + "");
			rspData.setMsg(ExptNum.PARAM_NO_ALL.getDesc());
			operatelog.setOperatestatus(2);
			operatelog.setOperatedes(ExptNum.PARAM_NO_ALL.getDesc());
			// 保存系统日志
			operatelogService.insert(operatelog);
			return JSONUtils.createObjectJson(rspData);
		}
		List<UserAccess> userAccessList = userAccessSerivce.selectByParam(null, "accesskeyid = '" + accessKeyID + "'");
		if(userAccessList.isEmpty()){
			RspData rspData = new RspData();
			rspData.setStatus(ExptNum.NO_ACCESSSECRET.getCode() + "");
			rspData.setMsg(ExptNum.NO_ACCESSSECRET.getDesc());
			operatelog.setOperatestatus(2);
			operatelog.setOperatedes(ExptNum.NO_ACCESSSECRET.getDesc());
			// 保存系统日志
			operatelogService.insert(operatelog);
			return JSONUtils.createObjectJson(rspData);
		}
		
		UserAccess deleteParam = new UserAccess();
		deleteParam.setAccesskeyid(accessKeyID);
		userAccessSerivce.delete(deleteParam);
		
		Map<String, RgwAdmin> rgwAdminMap = GlobalAttr.getInstance().getRgwAdminMap();
		for(Map.Entry<String, RgwAdmin> rgwAdminEntry : rgwAdminMap.entrySet()){
			RgwAdmin rgwAdmin = rgwAdminEntry.getValue();
			String uuid = userAccessList.get(0).getCompanyid();
			String subUuid = userAccessList.get(0).getUserid();
			rgwAdmin.removeSubUser(uuid, subUuid);
		}
		
		RspData rspData = new RspData();
		rspData.setStatus(ExptNum.SUCCESS.getCode() + "");
		rspData.setMsg(ExptNum.SUCCESS.getDesc());
		operatelog.setOperatestatus(1);
		// 保存系统日志
		operatelogService.insert(operatelog);
		return JSONUtils.createObjectJson(rspData);
	}

	/**
	 * 状态修改
	 * 
	 * @param accessKeyID
	 * @param status
	 *            状态
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/stateUserAcess", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String stateUserAcess(HttpServletRequest request) throws Exception {
		
		Map<String, String> maps = ApiTool.getBodyString(request);
		
		OssOperatelog operatelog = new OssOperatelog();
		operatelog.setOperatetarget("用户");
		operatelog.setOperatetype("状态修改UserAcess");
		operatelog.setOperatedes(maps.toString());
		
		operatelog.setZoneid((String) maps.get("zoneId"));
		
		//设置公司ID
		operatelog.setCompanyid(ApiTool.getUserMsg(request).getCompanyid());
		// 设置IP地址
		operatelog.setOperatorip(BucketAclUtil.getIpAddress(request));

		// 系统当前时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式
		Date date1 = new Date();
		String str = df.format(date1);
		operatelog.setOperatortime(df.parse(str));
		
		Map<String, String> reqParam = ApiTool.getBodyString(request);
		String accessKeyID = (String) reqParam.get("accessKeyID");
		String status = (String) reqParam.get("status");
//		String zoneId = (String) reqParam.get("zoneId");
		YrComper userMsg = ApiTool.getUserMsg(request);
		String companyId = userMsg.getCompanyid();
		OssOperatelog ossOperatelog = new OssOperatelog();
		ossOperatelog.setCompanyid(companyId);
		ossOperatelog.setOperatorip(BucketAclUtil.getIpAddress(request));
		ossOperatelog.setOperatetarget("用户");
		if("1".equals(status)){
			ossOperatelog.setOperatetype("禁用accecc");
			ossOperatelog.setOperatedes("禁用accecc");
		} else {
			ossOperatelog.setOperatetype("启用accecc");
			ossOperatelog.setOperatedes("用户accecc");
		}
		if(!ParamIsNull.isNull(accessKeyID, status)){
			ossOperatelog.setOperatestatus(2);
			ossOperatelog.setErrormessage("参数不完整");
			ossOperatelogService.insert(ossOperatelog);
			RspData rspData = new RspData();
			rspData.setStatus(ExptNum.PARAM_NO_ALL.getCode() + "");
			rspData.setMsg(ExptNum.PARAM_NO_ALL.getDesc());
			return JSONUtils.createObjectJson(rspData);
		}
		
		
//		String companyId = (String) request.getSession().getAttribute("username");
		String fooUserid = companyId;
		
		UserAccess selectParam = new UserAccess();
		selectParam.setAccesskeyid(accessKeyID);
//		UserAccess userAccess = userAccessSerivce.select(selectParam).get(0);
		List<UserAccess> userAccessList = userAccessSerivce.selectByParam(null, "accesskeyid = '" + accessKeyID + "'");
		if(userAccessList.isEmpty()){
			RspData rspData = new RspData();
			rspData.setStatus(ExptNum.NO_USER_ACCESS.getCode() + "");
			rspData.setMsg(ExptNum.NO_USER_ACCESS.getDesc());
			operatelog.setOperatestatus(2);
			operatelog.setOperatedes(ExptNum.NO_USER_ACCESS.getDesc());
			// 保存系统日志
			operatelogService.insert(operatelog);
			return JSONUtils.createObjectJson(rspData);
		}
		UserAccess userAccess = userAccessList.get(0);
		String subUserId = userAccess.getUserid(); 
		RspData rspData = new RspData();
		Map<String, RgwAdmin> rgwAdminMap = GlobalAttr.getInstance().getRgwAdminMap();
		RgwAdmin rgwAdmin =null;
		if ("1".equals(status)) {
			userAccessSerivce.updateByParam("status = 0 " + "where accessKeyID='" + accessKeyID + "'");
		} else {
			userAccessSerivce.updateByParam("status = 1 " + "where accessKeyID='" + accessKeyID + "'");
		}
		for(Map.Entry<String, RgwAdmin> rgwAdminEntry : rgwAdminMap.entrySet()){
			rgwAdmin = rgwAdminEntry.getValue();
			if ("1".equals(status)) {
				rgwAdmin.setSubUserPermission(fooUserid, subUserId, SubUser.Permission.NONE);
			} else {
				rgwAdmin.setSubUserPermission(fooUserid, subUserId, SubUser.Permission.FULL);
			}
		}
		ossOperatelog.setOperatestatus(1);
		ossOperatelogService.insert(ossOperatelog);

		rspData.setStatus(ExptNum.SUCCESS.getCode() + "");
		rspData.setMsg(ExptNum.SUCCESS.getDesc());
		operatelog.setOperatestatus(1);
		// 保存系统日志
		operatelogService.insert(operatelog);
		return JSONUtils.createObjectJson(rspData);
	}

	/**
	 * 查看密钥
	 * 
	 * @param accessKeyID
	 * @return
	 */
	@RequestMapping(value = "/showUserAcess", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String showUserAcess(HttpServletRequest request) throws Exception {

		Map<String, String> maps = ApiTool.getBodyString(request);
		
		OssOperatelog operatelog = new OssOperatelog();
		operatelog.setOperatetarget("用户");
		operatelog.setOperatetype("查看密钥");
		operatelog.setOperatedes(maps.toString());
		
		operatelog.setZoneid((String) maps.get("zoneId"));
		
		//设置公司ID
		operatelog.setCompanyid(ApiTool.getUserMsg(request).getCompanyid());
		// 设置IP地址
		operatelog.setOperatorip(BucketAclUtil.getIpAddress(request));

		// 系统当前时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式
		Date date1 = new Date();
		String str = df.format(date1);
		operatelog.setOperatortime(df.parse(str));
		
		String accessKeyID = (String) maps.get("accessKeyID");
		RspData rspData = new RspData();
		
		if(!ParamIsNull.isNull(accessKeyID)){
			operatelog.setOperatestatus(2);
			operatelog.setErrormessage(ExptNum.PARAM_NO_ALL.getCode() + "");
			operatelogService.insert(operatelog);
			rspData.setStatus(ExptNum.PARAM_NO_ALL.getCode() + "");
			rspData.setMsg(ExptNum.PARAM_NO_ALL.getDesc());
			return JSONUtils.createObjectJson(rspData);
		}
		List<UserAccess> list = userAccessSerivce.selectByParam("accessKeySecret",
				"where accessKeyID='" + accessKeyID + "'");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accessKeySecret", list.get(0).getAccesskeysecret());
		rspData.setStatus(ExptNum.SUCCESS.getCode() + "");
		rspData.setMsg(ExptNum.SUCCESS.getDesc());
		operatelog.setOperatestatus(1);
		// 保存系统日志
		operatelogService.insert(operatelog);
		rspData.setData(map);
		return JSONUtils.createObjectJson(rspData);
	}

	/**
	 * 查看信息
	 * 
	 * @param accessKeyID
	 * @return
	 */
	@RequestMapping(value = "/showUserAcessAll", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String showUserAcessAll(HttpServletRequest request) {
		// 获得当前用户的companyId
		String companyId = ApiTool.getUserMsg(request).getCompanyid();
		UserAccess user = new UserAccess();
		user.setCompanyid(companyId);
		RspData rspData = new RspData();
		List<UserAccess> list = userAccessSerivce.select(user);
		if (list.isEmpty()) {
			rspData.setStatus(ExptNum.NO_ACCESSSECRET.getCode() + "");
			rspData.setMsg(ExptNum.NO_ACCESSSECRET.getDesc());
			return JSONUtils.createObjectJson(rspData);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("UserAccess", list);
		rspData.setStatus(ExptNum.SUCCESS.getCode() + "");
		rspData.setMsg(ExptNum.SUCCESS.getDesc());
		rspData.setData(map);
		return JSONUtils.createObjectJson(rspData);
	}
	
	/**
	 * 获取域名
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCustom", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getCustom(HttpServletRequest request) throws Exception{
		
		Map<String, String> maps = ApiTool.getBodyString(request);
		String zoneId = (String)maps.get("zoneId");
		RspData rspData = new RspData();
		
		if(!ParamIsNull.isNull(zoneId)){
			rspData.setStatus(ExptNum.PARAM_NO_ALL.getCode() + "");
			rspData.setMsg(ExptNum.PARAM_NO_ALL.getDesc());
			return JSONUtils.createObjectJson(rspData);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 获得当前用户的companyId
		String companyId = ApiTool.getUserMsg(request).getCompanyid();
		List<OssUserDomain> selectByParam = ossUserDomainService.selectByParam(null, "companyId='" + companyId + "' and zoneId='" + zoneId + "'");
		if(selectByParam.size() == 0){
			map.put("custom", "");
		} else{
			map.put("custom", selectByParam.get(0).getCephcustomdomain());
		}
		
		List<OssZone> select = ossZoneService.select(new OssZone().setZoneid(zoneId));
		map.put("default", select.get(0).getServerip());
		
		rspData.setStatus(ExptNum.SUCCESS.getCode() + "");
		rspData.setMsg(ExptNum.SUCCESS.getDesc());
		rspData.setData(map);
		return JSONUtils.createObjectJson(rspData);
		
	}

	/**
	 * 修改域名
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateCustom", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String updateCustom(HttpServletRequest request) throws Exception{
		
		Map<String, String> maps = ApiTool.getBodyString(request);
		String zoneId = (String)maps.get("zoneId");
		RspData rspData = new RspData();
		
		OssOperatelog operatelog = new OssOperatelog();
		operatelog.setOperatetarget("用户");
		operatelog.setOperatetype("修改域名");
		operatelog.setOperatedes(maps.toString());
		
		operatelog.setZoneid((String) maps.get("zoneId"));
		
		//设置公司ID
		operatelog.setCompanyid(ApiTool.getUserMsg(request).getCompanyid());
		// 设置IP地址
		operatelog.setOperatorip(BucketAclUtil.getIpAddress(request));

		// 系统当前时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式
		Date date1 = new Date();
		String str = df.format(date1);
		operatelog.setOperatortime(df.parse(str));
		
		String cephCustomDomain = (String)maps.get("cephCustomDomain");
		/*if(!ParamIsNull.isNull(cephCustomDomain)){
			operatelog.setOperatestatus(2);
			operatelog.setErrormessage(ExptNum.PARAM_NO_ALL.getCode() + "");
			operatelogService.insert(operatelog);
			rspData.setStatus(ExptNum.PARAM_NO_ALL.getCode() + "");
			rspData.setMsg(ExptNum.PARAM_NO_ALL.getDesc());
			return JSONUtils.createObjectJson(rspData);
		}*/
		// 获得当前用户的companyId
		String companyId = ApiTool.getUserMsg(request).getCompanyid();
		
		List<OssUserDomain> selectByParam = ossUserDomainService.selectByParam(null, "companyId='" + companyId + "' and zoneId='" + zoneId + "'");
		if(selectByParam.size() == 0){
			OssUserDomain ossuserdomain = new OssUserDomain();
			ossuserdomain.setCode(Uuid.getGenerateCode());
			ossuserdomain.setCompanyid(companyId);
			ossuserdomain.setZoneid(zoneId);
			ossuserdomain.setCephcustomdomain(cephCustomDomain);
			ossuserdomain.setVersion(0);
			ossuserdomain.setIsdisplay(1);
			ossuserdomain.setState(1);
			ossuserdomain.setCreatetime(date1);
			ossUserDomainService.insert(ossuserdomain);
		} else{
			ossUserDomainService.updateByParam("cephCustomDomain='" + cephCustomDomain + "' where companyId='" + companyId + "' and zoneId='" + zoneId + "'");
		}
		
		operatelog.setOperatestatus(1);
		operatelogService.insert(operatelog);
		rspData.setStatus(ExptNum.SUCCESS.getCode() + "");
		rspData.setMsg(ExptNum.SUCCESS.getDesc());
		return JSONUtils.createObjectJson(rspData);
		
	}
	
	/**
	 * @Title: logout @Description: 退出登录 @author YaNan.Guan @param
	 *         request @param response @return @throws IOException @return
	 *         String @throws
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("status", ExptNum.SUCCESS.getCode());
		result.put("msg", ExptNum.SUCCESS.getDesc());
		return JSONUtils.createObjectJson(result);
	}
	
	/**
	 * 获取ceph
	 * 
	 * @param access_keys
	 * @param secret_keys
	 * @return
	 */
	/*@OperateLog("用户,获取ceph")
	@RequestMapping(value = "/creatConnect", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String creatConnect(HttpServletRequest request, @RequestBody Map<String, Object> maps) throws Exception {
		String access_keys = (String) maps.get("access_keys");
		String secret_keys = (String) maps.get("secret_keys");
		String zoneName = (String) maps.get("zoneName");

		RspData rd = new RspData();

		// 参数完整性判断
		if (!ParamIsNull.isNull(access_keys, secret_keys)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rd);
		}

		request.getSession().setAttribute("access_keys", access_keys);
		request.getSession().setAttribute("secret_keys", secret_keys);

		AWSCredentials credentials = new BasicAWSCredentials(access_keys, secret_keys);
		ClientConfiguration clientConfig = new ClientConfiguration();
		clientConfig.setSignerOverride("S3SignerType");
		clientConfig.setProtocol(Protocol.HTTPS);
		AmazonS3 conn = new AmazonS3ClientSerialize(credentials, clientConfig);

		List<OssZone> list = ossZoneService.select(new OssZone().setZonename(zoneName));

		OssZone zone = GlobalAttr.getInstance().getZoneMap().get(list.get(0).getZoneid());

		conn.setEndpoint(zone.getServerip() + ":" + zone.getServerport());

		request.getSession().setAttribute("conn", conn);

		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		return JSONUtils.createObjectJson(rd);
	}*/
	
	/**
	 * 欠费详情
	 * @return
	 */
	@RequestMapping(value = "/getArrerage", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getArrerage(HttpServletRequest request){
		RspData rspData = new RspData();
		String companyid = ApiTool.getUserMsg(request).getCompanyid();
		OssArrearage ossarrearage = new OssArrearage();
		ossarrearage.setCompanyid(companyid);
		ossarrearage.setState(1);
		Map<String, Object> map = new HashMap<String, Object>();
		List<OssArrearage> arrearage = arrearageService.select(ossarrearage);
		if(arrearage.size() == 0){
			rspData.setStatus(ExptNum.SUCCESS.getCode() + "");
			rspData.setMsg(ExptNum.SUCCESS.getDesc());
			return JSONUtils.createObjectJson(rspData);
		}else{
			
			int totalPrice = Integer.parseInt(arrearage.get(arrearage.size() -1).getCapacityPrice())+Integer.parseInt(arrearage.get(arrearage.size() -1).getTimesPrice())+Integer.parseInt(arrearage.get(arrearage.size() -1).getTrafficPrice());
			map.put("arrearage", arrearage.get(arrearage.size() -1));
			map.put("totalPrice", totalPrice);
			rspData.setStatus(ExptNum.ARREARAGE.getCode() + "");
			rspData.setMsg(ExptNum.ARREARAGE.getDesc());
			rspData.setData(map);
			return JSONUtils.createObjectJson(rspData);
		}
		
	}
	
	
	
	/**
	 *同步
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/synUserAccessKey", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String synUserAccessKey(HttpServletRequest request) throws Exception{
		RspData rspData = new RspData();
		String companyId = ApiTool.getUserMsg(request).getCompanyid();


		Map<String, RgwAdmin> rgwAdminMap = GlobalAttr.getInstance().getRgwAdminMap();
		RgwAdmin rgwAdmin = rgwAdminMap.values().iterator().next();
		Optional<User> userInfo = rgwAdmin.getUserInfo(companyId);
		if (userInfo.isPresent()) {
			User user = userInfo.get(); 
			List<S3Credential> s3Credentials = user.getS3Credentials();
			String userCoreAccessKey = "";
			String userCoreSecretkey = "";
			String subUserAccessKey = "";
			String subUserSecretKey = "";
			List<UserAccess> userAccessList = userAccessSerivce.select(new UserAccess().setCompanyid(companyId));
			for (UserAccess subUser : userAccessList) {
				for (S3Credential s3c : s3Credentials) {
					if (companyId.equals(s3c.getUserId())) {
						userCoreAccessKey = s3c.getAccessKey();
						userCoreSecretkey = s3c.getSecretKey();
						userCoreAccessService
								.updateByParam("cephAccessKeyID ='" + userCoreAccessKey + "',cephAccessKeySecret='"
										+ userCoreSecretkey + "' where companyId = '" + companyId + "'");
					}
					if (s3c.getUserId().equals(companyId + ":" + subUser.getUserid())) {
						subUserAccessKey = s3c.getAccessKey();
						subUserSecretKey = s3c.getSecretKey();
						userAccessSerivce.updateByParam("accessKeyID = '" + subUserAccessKey
								+ "' , accessKeySecret = '" + subUserSecretKey + "' where companyId = '" + companyId
								+ "' and userId = '" + subUser.getUserid() + "'");
					}

				}
			}
		}
	
		rspData.setStatus(ExptNum.SUCCESS.getCode() + "");
		rspData.setMsg(ExptNum.SUCCESS.getDesc());
		return JSONUtils.createObjectJson(rspData);
		
	}
	
	
	
}
