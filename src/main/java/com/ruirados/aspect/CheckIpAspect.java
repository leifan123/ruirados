package com.ruirados.aspect;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import com.ruirados.annotation.CheckIp;
import com.ruirados.pojo.OssAcl;
import com.ruirados.pojo.OssBucket;
import com.ruirados.pojo.RspData;
import com.ruirados.service.OssAclService;
import com.ruirados.service.OssBucketService;
import com.ruirados.util.ApiTool;
import com.ruirados.util.BucketAclUtil;
import com.ruirados.util.ExptNum;
import com.ruirados.util.HttpContextUtils;
import com.ruirados.util.JSONUtils;
import com.yunrui.pojo.YrComper;

//@Aspect
//@Component
public class CheckIpAspect {
	
	@Autowired
	private OssAclService OssAclService;

	@Autowired
	private OssBucketService OssBucketService;
	private Logger log = Logger.getLogger(CheckIpAspect.class);
	@Pointcut("@annotation(com.ruirados.annotation.CheckIp)")
	public void CheckIpPointCut() {
	}

	@Around("CheckIpPointCut()")
	public Object check(ProceedingJoinPoint point) throws Throwable, IOException {
		log.debug("进入切面 ---");
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		CheckIp CheckIp = method.getAnnotation(CheckIp.class);
		if (CheckIp != null) {
			// 注解上的描述
			log.debug(CheckIp.value());
		}

		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();

		@SuppressWarnings("unchecked")
		Map<String, String> maps = (Map<String, String>) point.getArgs()[1];
		RspData rd = new RspData();

		String bucketName = (String) maps.get("bucketName");
		YrComper yr = ApiTool.getUserMsg(request);
		String companyId = yr.getCompanyid();

		log.debug("bucketName --->" + bucketName);
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
				Object result = point.proceed();
				return result;
			}

			log.debug(bucketRe.size());

			OssAcl bucketAclRe = new OssAcl();

			for (OssBucket bu : bucketRe) {
				bucketAclRe.setBucketId(bu.getId());
			}
			List<OssAcl> bucketAcls = OssAclService.select(bucketAclRe);

			log.debug("bucketAcls = " + bucketAcls);
			int isEffectRefIp = 0;
			List<String> list = new ArrayList<String>();
			for (OssAcl buAcl : bucketAcls) {
				String str = buAcl.getRefererip();
				if (str != null && str.contains(";")) {
					String[] ips = str.split(";");
					for (String ipss : ips) {
						list.add(ipss);
					}
				} else {
					list.add(str);
				}
				log.debug("白名单地址 --->" + list.toString());
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
			Object result = point.proceed();
			return result;
		}
	}
}
