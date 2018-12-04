package com.ruirados.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.BucketCrossOriginConfiguration;
import com.amazonaws.services.s3.model.CORSRule;
import com.ruirados.pojo.OssCors;
import com.ruirados.pojo.OssOperatelog;
import com.ruirados.pojo.RspData;
import com.ruirados.service.OssCorsService;
import com.ruirados.service.OssOperatelogService;
import com.ruirados.util.ApiTool;
import com.ruirados.util.BucketAclUtil;
import com.ruirados.util.Config;
import com.ruirados.util.ExptNum;
import com.ruirados.util.GetResult;
import com.ruirados.util.JSONUtils;
import com.ruirados.util.MappingConfigura;
import com.ruirados.util.ParamIsNull;
import com.ruirados.util.Uuid;

@Controller
@RequestMapping(MappingConfigura.CORS)
public class CorsController {
	
	@Autowired
	private OssCorsService OssCorsService;
	
	@Autowired
	private OssOperatelogService operatelogService;
	
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式
	
	@RequestMapping(value = "addCors", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String addCors(HttpServletRequest request) throws Exception{
		Map<String, Object> maps = ApiTool.getBodyObject(request);
		
		String zoneId = (String) maps.get("zoneId");
		
		OssOperatelog operatelog = BucketAclUtil.setLog(request, "Cors", "新增Cors", maps.toString(), zoneId);
		
		String bucketId = (String) maps.get("bucketid");
		String bucketName = (String) maps.get("bucketName");
		String orgins = (String) maps.get("orgins");
		List<String> methods = (List<String>) maps.get("methods");
		String allowsHeaders = (String) maps.get("allowsHeaders");
		String ExposeHeaders = (String) maps.get("ExposeHeaders");
		String maxAgeS = (String) maps.get("maxAge");
		String companyId = ApiTool.getUserMsg(request).getCompanyid();
		
		RspData rd = new RspData();
		
		// 参数完整性判断
		if (!ParamIsNull.isNull(bucketId, bucketName, orgins, methods, allowsHeaders, ExposeHeaders, maxAgeS, zoneId)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			operatelog.setOperatestatus(2);
			operatelog.setErrormessage(Config.REQUEST_Param_IS_NULL);
			// 保存系统日志
			operatelogService.insert(operatelog);
			return JSONUtils.createObjectJson(rd);
		}
		
		int bucketid = Integer.parseInt(bucketId);
		int maxAge = Integer.parseInt(maxAgeS);
		
		//一个空间下有且最多有 十条规则
		List listCors = OssCorsService.select(new OssCors().setBucketid(bucketid));
		if(listCors.size() >= 10){
			rd.setStatus(ExptNum.OVERFULFIL.getCode() + "");
			rd.setMsg(ExptNum.OVERFULFIL.getDesc());
			operatelog.setOperatestatus(2);
			operatelog.setErrormessage(ExptNum.OVERFULFIL.getDesc());
			// 保存系统日志
			operatelogService.insert(operatelog);
			return JSONUtils.createObjectJson(rd);
		}
		
		AmazonS3 conn = BucketAclUtil.getConnByCache(companyId, zoneId, 0, true);
		
		BucketCrossOriginConfiguration configuration = new BucketCrossOriginConfiguration();

		configuration = conn.getBucketCrossOriginConfiguration(bucketName);
		
		// 创建CORS 规则
		List<CORSRule.AllowedMethods> rule1AM = new ArrayList<CORSRule.AllowedMethods>();
		
		for(String cors : methods){
			if("Put".equals(cors))
				rule1AM.add(CORSRule.AllowedMethods.PUT);
			if("Delete".equals(cors))
				rule1AM.add(CORSRule.AllowedMethods.DELETE);
			if("Get".equals(cors))
				rule1AM.add(CORSRule.AllowedMethods.GET);
			if("Post".equals(cors))
				rule1AM.add(CORSRule.AllowedMethods.POST);
			if("Head".equals(cors))
				rule1AM.add(CORSRule.AllowedMethods.HEAD);
		}
		
		List<String> list = new ArrayList<String>();
		if(allowsHeaders.contains(",")){
			String[] str1 = allowsHeaders.split(",");
			for(String header : str1){
				list.add(header);
			}
		}	else{
			list.add(allowsHeaders);
		}
		
		List<CORSRule> rules = null;
		BucketCrossOriginConfiguration configuration1= new BucketCrossOriginConfiguration();
		if(configuration == null){
			rules = new ArrayList<CORSRule>();
		} else{
			rules = configuration.getRules();
		}
		
		CORSRule rule1 = null;
		if(orgins.contains(",")){
			String[] strs = orgins.split(",");
			for(int i = 0; i < strs.length; i++){
				rule1 = new CORSRule().withId("CORS" + Uuid.getGenerateCode().substring(0, 5)).withAllowedMethods(rule1AM)
						.withAllowedOrigins(Arrays.asList(new String[] { strs[i] })).withMaxAgeSeconds(maxAge).withAllowedHeaders(list)
						.withExposedHeaders(Arrays.asList(new String[] { ExposeHeaders }));
				rules.add(rule1);
			}
		}	else{
			rule1 = new CORSRule().withId("CORS" + Uuid.getGenerateCode().substring(0, 5)).withAllowedMethods(rule1AM)
					.withAllowedOrigins(Arrays.asList(new String[] { orgins })).withMaxAgeSeconds(maxAge).withAllowedHeaders(list)
					.withExposedHeaders(Arrays.asList(new String[] { ExposeHeaders }));
			
			rules.add(rule1);
		}
		
		if(configuration == null){
			// 将规则添加到新的CORS配置中
			configuration1.setRules(rules);

			// 将配置添加到bucket中
			conn.setBucketCrossOriginConfiguration(bucketName, configuration1);
		}	else{
			// 将规则添加到新的CORS配置中
			configuration.setRules(rules);

			// 将配置添加到bucket中
			conn.setBucketCrossOriginConfiguration(bucketName, configuration);
		}
		
		OssCors ossCors = new OssCors();
		ossCors.setCode(Uuid.getGenerateCode());
		ossCors.setCorsid(rule1.getId());
		ossCors.setBucketid(bucketid);
		ossCors.setOrgins(orgins);
		ossCors.setMethods(methods.toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\"", ""));
		ossCors.setAllowsheaders(allowsHeaders);
		ossCors.setExposeheaders(ExposeHeaders);
		ossCors.setMaxage(maxAge);
		ossCors.setVersion(0);
		ossCors.setIsdisplay(1);
		ossCors.setState(1);
		
		// 格式化当前时间
		Date date = new Date();
		String dateStr = df.format(date);
		date = df.parse(dateStr);
		
		ossCors.setCreatetime(date);
		
		OssCorsService.insert(ossCors);
		
		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		operatelog.setOperatestatus(1);
		// 保存系统日志
		operatelogService.insert(operatelog);
		return JSONUtils.createObjectJson(rd);
	}
	
	@RequestMapping(value = "deleteCors", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String deleteCors(HttpServletRequest request) throws Exception{
		Map<String, Object> maps = ApiTool.getBodyObject(request);
		
		String zoneId = (String) maps.get("zoneId");
		
		OssOperatelog operatelog = BucketAclUtil.setLog(request, "Cors", "删除Cors", maps.toString(), zoneId);
		
		String corsid = (String) maps.get("corsid");
		String bucketName = (String)maps.get("bucketName");
		
		String companyId = ApiTool.getUserMsg(request).getCompanyid();
		
		RspData rd = new RspData();
		
		// 参数完整性判断
		if (!ParamIsNull.isNull(corsid)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			operatelog.setOperatestatus(2);
			operatelog.setErrormessage(Config.REQUEST_Param_IS_NULL);
			// 保存系统日志
			operatelogService.insert(operatelog);
			return JSONUtils.createObjectJson(rd);
		}
		
		AmazonS3 conn = BucketAclUtil.getConnByCache(companyId, zoneId, 0, true);
		
		BucketCrossOriginConfiguration configuration = new BucketCrossOriginConfiguration();
		
		configuration = conn.getBucketCrossOriginConfiguration(bucketName);
		
		List<CORSRule> rules = configuration.getRules();
		for(int i = 0; i < rules.size(); i++){
			if(rules.get(i).getId().equals(corsid)){
				rules.remove(rules.get(i));
				i++;
			}
		}
		
		if(rules.size() == 0){
			conn.deleteBucketCrossOriginConfiguration(bucketName);
		}else{
			// 将规则添加到新的CORS配置中
			configuration.setRules(rules);

			// 将配置添加到bucket中
			conn.setBucketCrossOriginConfiguration(bucketName, configuration);
		}
		
		OssCorsService.delete(new OssCors().setCorsid(corsid));
		
		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		operatelog.setOperatestatus(1);
		// 保存系统日志
		operatelogService.insert(operatelog);
		return JSONUtils.createObjectJson(rd);	
	}
	
	@RequestMapping(value = "updateCors", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String updateCors(HttpServletRequest request) throws Exception{
		
		Map<String, Object> maps = ApiTool.getBodyObject(request);
		String zoneId = (String) maps.get("zoneId");
		
		OssOperatelog operatelog = BucketAclUtil.setLog(request, "Cors", "修改Cors", maps.toString(), zoneId);
		
		String corsid = (String) maps.get("corsid");
		String bucketName = (String) maps.get("bucketName");
		String orgins = (String) maps.get("orgins");
		List<String> methods = (List<String>) maps.get("methods");
		String allowsHeaders = (String) maps.get("allowsHeaders");
		String ExposeHeaders = (String) maps.get("ExposeHeaders");
		String maxAges = (String) maps.get("maxAge");
		
		String companyId = ApiTool.getUserMsg(request).getCompanyid();
		
		RspData rd = new RspData();
		// 参数完整性判断
		if (!ParamIsNull.isNull(corsid, bucketName, orgins, methods, allowsHeaders, ExposeHeaders, maxAges, zoneId)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			operatelog.setOperatestatus(2);
			operatelog.setErrormessage(Config.REQUEST_Param_IS_NULL);
			// 保存系统日志
			operatelogService.insert(operatelog);
			return JSONUtils.createObjectJson(rd);
		}
		
		int maxAge = Integer.parseInt(maxAges);
		
		List<String> list = new ArrayList<String>();
		if(allowsHeaders.contains(",")){
			String[] str1 = allowsHeaders.split(",");
			for(String header : str1){
				list.add(header);
			}
		}	else{
			list.add(allowsHeaders);
		}
		
		AmazonS3 conn = BucketAclUtil.getConnByCache(companyId, zoneId, 0, true);
		
		BucketCrossOriginConfiguration configuration = new BucketCrossOriginConfiguration();

		configuration = conn.getBucketCrossOriginConfiguration(bucketName);
		
		// 创建CORS 规则
		List<CORSRule.AllowedMethods> rule1AM = new ArrayList<CORSRule.AllowedMethods>();
		
		for(String cors : methods){
			if("Put".equals(cors))
				rule1AM.add(CORSRule.AllowedMethods.PUT);
			if("Delete".equals(cors))
				rule1AM.add(CORSRule.AllowedMethods.DELETE);
			if("Get".equals(cors))
				rule1AM.add(CORSRule.AllowedMethods.GET);
			if("Post".equals(cors))
				rule1AM.add(CORSRule.AllowedMethods.POST);
			if("Head".equals(cors))
				rule1AM.add(CORSRule.AllowedMethods.HEAD);
		}
		
		List<CORSRule> rules = configuration.getRules();
		int num = 0;
		  for(int i = 0; i < rules.size(); i++){
			  if(corsid.equals(rules.get(i).getId())){
				  rules.remove(rules.get(i));
				  num = i;
				  break;
			  }
		  }
		  rules.add(num, new CORSRule().withId(corsid).withAllowedMethods(rule1AM)
					.withAllowedOrigins(Arrays.asList(new String[] { orgins })).withMaxAgeSeconds(maxAge).withAllowedHeaders(list)
					.withExposedHeaders(Arrays.asList(new String[] { ExposeHeaders })));
		  
		// 将规则添加到新的CORS配置中
		configuration.setRules(rules);

		// 将配置添加到bucket中
		conn.setBucketCrossOriginConfiguration(bucketName, configuration);
		
		OssCors ossCors = new OssCors();
		ossCors.setOrgins(orgins);
		ossCors.setMethods(methods.toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\"", ""));
		ossCors.setAllowsheaders(allowsHeaders);
		ossCors.setExposeheaders(ExposeHeaders);
		ossCors.setMaxage(maxAge);
		
		// 格式化当前时间
		Date date = new Date();
		String dateStr = df.format(date);
		date = df.parse(dateStr);
		
		ossCors.setLasttime(date);
		
		OssCorsService.update(ossCors);
		
		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		operatelog.setOperatestatus(1);
		// 保存系统日志
		operatelogService.insert(operatelog);
		return JSONUtils.createObjectJson(rd);
	}
	
	@RequestMapping(value = "selectCorsFromId", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String selectCorsFromId(HttpServletRequest request) throws Exception{
		Map<String, Object> maps = ApiTool.getBodyObject(request);
		
		String Id = (String) maps.get("id");
		
		String zoneId =(String) maps.get("zoneId");
		
		OssOperatelog operatelog = BucketAclUtil.setLog(request, "Cors", "查询Cors", maps.toString(), zoneId);

		RspData rd = new RspData();
		
		// 参数完整性判断
		if (!ParamIsNull.isNull(Id)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			operatelog.setOperatestatus(2);
			operatelog.setErrormessage(Config.REQUEST_Param_IS_NULL);
			// 保存系统日志
			operatelogService.insert(operatelog);
			return JSONUtils.createObjectJson(rd);
		}
		
		int id = Integer.parseInt(Id);
		
		// 系统当前时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式
		Date date1 = new Date();
		String str = df.format(date1);
		operatelog.setOperatortime(df.parse(str));
		
		List<OssCors> list = OssCorsService.select(new OssCors().setId(id));
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("OssCors", list.get(0));
		
		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		operatelog.setOperatestatus(1);
		// 保存系统日志
		operatelogService.insert(operatelog);
		rd.setData(map);
		return JSONUtils.createObjectJson(rd);
	}
	
	@RequestMapping(value = "selectCorsAll", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String selectCorsAll(HttpServletRequest request) throws Exception{
		Map<String, Object> maps = ApiTool.getBodyObject(request);
		String zoneId = (String) maps.get("zoneId");
		
		OssOperatelog operatelog = BucketAclUtil.setLog(request, "Cors", "查询当前bucket所有Cors", maps.toString(), zoneId);
		
		String Id = (String) maps.get("bucketId");
		
		RspData rd = new RspData();
		
		// 参数完整性判断
		if (!ParamIsNull.isNull(Id)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			operatelog.setOperatestatus(2);
			operatelog.setErrormessage(Config.REQUEST_Param_IS_NULL);
			// 保存系统日志
			operatelogService.insert(operatelog);
			return JSONUtils.createObjectJson(rd);
		}
		
		int bucketId = Integer.parseInt(Id);
		
		List<OssCors> list = OssCorsService.select(new OssCors().setBucketid(bucketId));
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("OssCors", list);
		
		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		operatelog.setOperatestatus(1);
		// 保存系统日志
		operatelogService.insert(operatelog);
		rd.setData(map);
		return JSONUtils.createObjectJson(rd);
	}
}
