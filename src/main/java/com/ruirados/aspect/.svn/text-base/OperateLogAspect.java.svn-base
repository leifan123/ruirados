package com.ruirados.aspect;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ruirados.annotation.OperateLog;
import com.ruirados.pojo.OssOperatelog;
import com.ruirados.service.OssOperatelogService;
import com.ruirados.util.BucketAclUtil;
import com.ruirados.util.HttpContextUtils;

//@Aspect
//@Component
public class OperateLogAspect {
	@Autowired
	private OssOperatelogService operatelogService;

	@Pointcut("@annotation(com.ruirados.annotation.OperateLog)")
	public void operatelogPointCut() {
	}

	@Around("operatelogPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		// 执行方法
		Object result = point.proceed();
		// 保存日志
		int id = saveLog(point);
		String[] results = result.toString().split(",");
		if(Integer.parseInt(results[0].split(":")[1].replaceAll("\"", "")) == 1){
			operatelogService.updateByParam("operateStatus=1" + " where id=" + id);
		}else{
			operatelogService.updateByParam("operateStatus=2" + ",errorMessage='" + results[1].split(":")[1] +  "' where id=" + id);
		}
		return result;
	}

	private int saveLog(ProceedingJoinPoint joinPoint) throws Exception {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		OssOperatelog operatelog = new OssOperatelog();
		OperateLog syslog = method.getAnnotation(OperateLog.class);
		if (syslog != null) {
			String[] values = syslog.value().split(",");
			operatelog.setOperatetarget(values[0]);
			operatelog.setOperatetype(values[1]);
		}
		
		// 请求的方法名
//		String className = joinPoint.getTarget().getClass().getName();
//		String methodName = signature.getName();
		
		@SuppressWarnings("unchecked")
		Map<String, Object> maps = (Map<String, Object>) joinPoint.getArgs()[1];
		maps.remove("mac");
		maps = BucketAclUtil.toEh(maps);
		operatelog.setOperatedes(maps.toString());
		
		operatelog.setZoneid((String) maps.get("zoneId"));
		
		// 获取request
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		//设置公司ID
		operatelog.setCompanyid((String) request.getSession().getAttribute("username"));
		// 设置IP地址
		operatelog.setOperatorip(BucketAclUtil.getIpAddress(request));
		
		// 系统当前时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式
		Date date = new Date();
		String str = df.format(date);
		operatelog.setOperatortime(df.parse(str));
		
		// 保存系统日志
		operatelogService.insert(operatelog);
		
		return operatelog.getId();
	}
}
