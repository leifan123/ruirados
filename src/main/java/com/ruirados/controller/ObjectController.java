package com.ruirados.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadResult;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PartETag;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.UploadPartRequest;
import com.amazonaws.services.s3.model.UploadPartResult;
import com.ruirados.pojo.Dictionary;
import com.ruirados.pojo.OssBucket;
import com.ruirados.pojo.OssObject;
import com.ruirados.pojo.OssOperatelog;
import com.ruirados.pojo.OssUserDomain;
import com.ruirados.pojo.RspData;
import com.ruirados.pojo.UserTypeSource;
import com.ruirados.service.OssAclService;
import com.ruirados.service.OssBucketService;
import com.ruirados.service.OssObjectService;
import com.ruirados.service.OssOperatelogService;
import com.ruirados.service.OssUserDomainService;
import com.ruirados.service.UserCoreAccessService;
import com.ruirados.service.UserTypeSourceService;
import com.ruirados.util.ApiTool;
import com.ruirados.util.BucketAclUtil;
import com.ruirados.util.ExptNum;
import com.ruirados.util.JSONUtils;
import com.ruirados.util.MappingConfigura;
import com.ruirados.util.ParamIsNull;
import com.ruirados.util.Prop;
import com.ruirados.util.Uuid;

@Controller
@RequestMapping(MappingConfigura.OBJECT)
public class ObjectController {

	@Autowired
	private OssOperatelogService operatelogService;
	@Autowired
	private OssObjectService ossObjectService;
	@Autowired
	private OssBucketService ossBucketService;
	@Autowired
	private OssAclService ossAclService;
	@Autowired
	private UserCoreAccessService userCoreAccessService;
	@Autowired
	private OssUserDomainService ossUserDomainService;
	@Autowired
	private UserTypeSourceService userTypeSourceService;

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式

	private Logger log = Logger.getLogger(getClass());

	// 新建文件夹
	@RequestMapping(value = "/createObject", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String createFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> pama = ApiTool.getBodyString(request);

		String fileName = pama.get("fileName");
		String bucketName = pama.get("bucketName");
		String zoneId = pama.get("zoneId");
		String dirId = pama.get("dirId");
		RspData rspData = new RspData();

		OssOperatelog operatelog = new OssOperatelog();
		operatelog.setOperatetarget("Object");
		operatelog.setOperatetype("新建文件夹");

		Map<String, String> maps = new HashMap<String, String>();
		maps.remove("mac");
		maps.put("Bucket名", bucketName);
		maps.put("空间ID", zoneId);
		maps.put("文件夹ID", dirId);
		maps.put("文件夹名", fileName);

		operatelog.setOperatedes(maps.toString());
		operatelog.setZoneid((String) maps.get("zoneId"));

		// 设置公司ID
		operatelog.setCompanyid(ApiTool.getUserMsg(request).getCompanyid());
		// 设置IP地址
		operatelog.setOperatorip(BucketAclUtil.getIpAddress(request));

		// 系统当前时间
		Date date1 = new Date();
		String str = dateFormat.format(date1);
		operatelog.setOperatortime(dateFormat.parse(str));
		// 判断参数是否完整

		if (!ParamIsNull.isNull(bucketName, zoneId, fileName)) {
			rspData.setStatus(ExptNum.PARAM_NO_ALL.getCode() + "");
			rspData.setMsg(ExptNum.PARAM_NO_ALL.getDesc());
			operatelog.setOperatestatus(2);
			operatelog.setErrormessage(ExptNum.PARAM_NO_ALL.getDesc());
			// 保存系统日志
			operatelogService.insert(operatelog);
			return JSONUtils.createObjectJson(rspData);
		}
		// 检查字符长度是否符合要求
		if (fileName.length() > 24) {
			rspData.setStatus(ExptNum.TOO_MANY_WORDS.getCode() + "");
			rspData.setMsg(ExptNum.TOO_MANY_WORDS.getDesc());
			operatelog.setOperatestatus(2);
			operatelog.setErrormessage(ExptNum.TOO_MANY_WORDS.getDesc());
			// 保存系统日志
			operatelogService.insert(operatelog);
			return JSONUtils.createObjectJson(rspData);
		}
		// 判断是否有权限
		int aclb = new BucketAclUtil().checkListBucketAcl(request, bucketName, "PutObject", userCoreAccessService,
				ossAclService, ossBucketService);
		if (aclb == 0) {
			rspData.setStatus(ExptNum.NO_JURISDICTION.getCode() + "");
			rspData.setMsg(ExptNum.NO_JURISDICTION.getDesc());
			operatelog.setOperatestatus(2);
			operatelog.setErrormessage(ExptNum.NO_JURISDICTION.getDesc());
			// 保存系统日志
			operatelogService.insert(operatelog);
			return JSONUtils.createObjectJson(rspData);
		}

		// 获取链接

		String companyId = ApiTool.getUserMsg(request).getCompanyid();
		AmazonS3 conn = BucketAclUtil.getConnByCache(companyId, zoneId, 0, true);
		


		// 将对象大小设置为0
		ByteArrayInputStream in = new ByteArrayInputStream(new byte[0]);
		ObjectMetadata objectMeta = new ObjectMetadata();
		objectMeta.setContentLength(0);

		try {
			OssObject ossObjectrepeat = new OssObject();

			if (dirId == null || dirId.equals("")) {
				ossObjectrepeat.setFilename(fileName);
				ossObjectrepeat.setFId(0);
			} else {
				ossObjectrepeat.setFilename(fileName);
				ossObjectrepeat.setFId(Integer.parseInt(dirId));
			}
			ossObjectrepeat.setCompanyid(companyId);
			ossObjectrepeat.setZoneid(zoneId);
			ossObjectrepeat.setBucketName(bucketName);
			List<OssObject> list = ossObjectService.select(ossObjectrepeat);
			
			if (!list.isEmpty()) {
				rspData.setStatus(ExptNum.FILE_NAME_REPEAT.getCode() + "");
				rspData.setMsg(ExptNum.FILE_NAME_REPEAT.getDesc());
				operatelog.setOperatestatus(2);
				operatelog.setErrormessage(ExptNum.FILE_NAME_REPEAT.getDesc());
			} else {
				conn.putObject(bucketName, fileName, in, new ObjectMetadata());
				// 设置文件路径
				OssObject ossObjectsrc = new OssObject();

				String src = null;
				if (dirId == null || dirId.equals("")) {
					src = null;
				} else {
					src = ossObjectService.select(ossObjectsrc.setId(Integer.parseInt(dirId))).get(0).getFilesrc();
				}
				String fileSrc = null;
				if (src == null) {
					fileSrc = fileName;
				} else {
					fileSrc = src + "/" + fileName;
				}
			

				OssObject ossObject = new OssObject();

				// 设置编码
				String code = Uuid.getGenerateCode();
				// 添加参数

				ossObject.setFilename(fileName);
				ossObject.setBucketName(bucketName);
				ossObject.setCompanyid(companyId);
				ossObject.setCreatetime(dateFormat.parse(str));// 新建文件时间
				ossObject.setLasttime(dateFormat.parse(str));
				ossObject.setIsfile(1);
				ossObject.setFilesrc(fileSrc);
				ossObject.setCode(code);
				ossObject.setFilesize(0.0);
				ossObject.setZoneid(zoneId);
				if (dirId != null && !"".equals(dirId)) {
					ossObject.setFId(Integer.parseInt(dirId));
				} else {
					ossObject.setFId(0);
				}
				ossObjectService.insert(ossObject);
				rspData.setStatus(ExptNum.SUCCESS.getCode() + "");
				rspData.setMsg(ExptNum.SUCCESS.getDesc());
				operatelog.setOperatestatus(1);

			}
		} catch (Exception e) {
			
			log.error(e);
			rspData.setStatus(ExptNum.FAIL.getCode() + "");
			rspData.setMsg(ExptNum.FAIL.getDesc() + e.getMessage());

			operatelog.setOperatestatus(2);
			operatelog.setErrormessage(ExptNum.FAIL.getDesc());
			// 保存系统日志
			operatelogService.insert(operatelog);
			return JSONUtils.createObjectJson(rspData);

		} finally {
			try {
				in.close();
			} catch (IOException e) {
				log.error(e);
				
			}
		}
		Map<String, Object> resData = splitParentDir(dirId);
		rspData.setData(resData);

		// 保存系统日志
		operatelogService.insert(operatelog);
		return JSONUtils.createObjectJson(rspData);

	}

	// 删除文件

	@RequestMapping(value = "/deleteObject", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String deleteFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> pama = ApiTool.getBodyString(request);
		String fileName = pama.get("fileName");
		String bucketName = pama.get("bucketName");
		String zoneId = pama.get("zoneId");
		String dirId = pama.get("dirId");

		RspData rspData = new RspData();

		// 记录日志
		OssOperatelog operatelog = new OssOperatelog();
		operatelog.setOperatetarget("Object");
		operatelog.setOperatetype("删除文件");

		Map<String, String> maps = new HashMap<String, String>();
		maps.remove("mac");
		maps.put("Bucket名", bucketName);
		maps.put("空间ID", zoneId);
		maps.put("文件夹ID", dirId);
		maps.put("文件夹名", fileName);
		operatelog.setOperatedes(maps.toString());
		operatelog.setZoneid((String) maps.get("zoneId"));
		// 设置公司ID
		operatelog.setCompanyid(ApiTool.getUserMsg(request).getCompanyid());
		// 设置IP地址
		operatelog.setOperatorip(BucketAclUtil.getIpAddress(request));
		// 系统当前时间
		Date date1 = new Date();
		String str = dateFormat.format(date1);
		operatelog.setOperatortime(dateFormat.parse(str));

		// 判断参数是否完整
		if (!ParamIsNull.isNull(bucketName, zoneId, fileName, dirId)) {
			rspData.setStatus(ExptNum.PARAM_NO_ALL.getCode() + "");
			rspData.setMsg(ExptNum.PARAM_NO_ALL.getDesc());
			operatelog.setOperatestatus(2);
			operatelog.setErrormessage(ExptNum.PARAM_NO_ALL.getDesc());
			operatelogService.insert(operatelog);
			return JSONUtils.createObjectJson(rspData);
		}

		// 判断是否有权限
		int aclb = new BucketAclUtil().checkListBucketAcl(request, bucketName, "DeleteObject", userCoreAccessService,
				ossAclService, ossBucketService);
		if (aclb == 0) {
			rspData.setStatus(ExptNum.NO_JURISDICTION.getCode() + "");
			rspData.setMsg(ExptNum.NO_JURISDICTION.getDesc());
			operatelog.setOperatestatus(2);
			operatelog.setErrormessage(ExptNum.NO_JURISDICTION.getDesc());
			operatelogService.insert(operatelog);
			return JSONUtils.createObjectJson(rspData);
		}

		String companyId = ApiTool.getUserMsg(request).getCompanyid();
		AmazonS3 conn = BucketAclUtil.getConnByCache(companyId, zoneId, 0, false);

		OssObject oo = new OssObject();
		List<OssObject> list = ossObjectService.select(oo.setFId(Integer.parseInt(dirId)));
		
		if (list.isEmpty()) {
			// 删除服务器文件
			conn.deleteObject(bucketName, fileName);

			// 更新父文件夹的操作时间

			List<OssObject> s = ossObjectService.selectByParam("", "where id =" + Integer.parseInt(dirId));
			OssObject ooUpdate = new OssObject();
			ooUpdate.setId(s.get(0).getFId());
			ooUpdate.setLasttime(dateFormat.parse(str));
			ossObjectService.update(ooUpdate);
			// 删除数据库的文件1
			
			OssObject ooSelect = new OssObject();
			
			Double ReducedSize = 0.0;
			List<OssObject> buckeylist =  ossObjectService.selectByParam("", " id="+Integer.parseInt(dirId));
			if(!buckeylist.isEmpty()){
				ReducedSize  = buckeylist .get(0).getFilesize();
			}
			
			ossObjectService.delete(ooSelect.setId(Integer.parseInt(dirId)));
			Double capacity = 0.0;
			List<OssBucket> bucketlist = ossBucketService.selectByParam("", " companyId='" +companyId+"'"+" and zoneId='"+zoneId+"'"+" and name='"+bucketName+"'");
			if(!bucketlist.isEmpty()){
				capacity = bucketlist.get(0).getCapacity();
			}
			double nowCapacity = capacity -  ReducedSize;
			ossBucketService.updateByParam(" capacity = "+nowCapacity+" where companyId='" +companyId+"'"+" and zoneId='"+zoneId+"'"+" and name='"+bucketName+"'");
			operatelog.setOperatestatus(1);
			rspData.setStatus(ExptNum.SUCCESS.getCode() + "");
			rspData.setMsg(ExptNum.SUCCESS.getDesc());
		} else {
			rspData.setStatus(ExptNum.IT_HAS_SONFILE.getCode() + "");
			rspData.setMsg(ExptNum.IT_HAS_SONFILE.getDesc());
			operatelog.setOperatestatus(2);
			operatelog.setErrormessage(ExptNum.IT_HAS_SONFILE.getDesc());

		}
		// 添加日志
		operatelogService.insert(operatelog);
		return JSONUtils.createObjectJson(rspData);

	}

	// 查看文件夹(文件)

	@RequestMapping(value = "/listObject", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listObject(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> pama = ApiTool.getBodyString(request);
		String bucketname = pama.get("bucketName");
		String dirId = pama.get("dirId");
		String fileNameS = pama.get("fileName");
		String zoneId = pama.get("zoneId");
		RspData rspData = new RspData();
		
		
		
		// 记录日志
		OssOperatelog operatelog = new OssOperatelog();
		operatelog.setOperatetarget("Object");
		operatelog.setOperatetype("查看文件文件");
		Map<String, String> maps = new HashMap<String, String>();
		maps.remove("mac");
		maps.put("Bucket名", bucketname);
		maps.put("空间ID", zoneId);
		maps.put("文件夹ID", dirId);
		maps.put("文件夹名", fileNameS);
		operatelog.setOperatedes(maps.toString());
		operatelog.setZoneid((String) maps.get("zoneId"));
		// 设置公司ID
		operatelog.setCompanyid(ApiTool.getUserMsg(request).getCompanyid());
		// 设置IP地址
		operatelog.setOperatorip(BucketAclUtil.getIpAddress(request));
		// 系统当前时间
		Date date1 = new Date();
		String str = dateFormat.format(date1);
		operatelog.setOperatortime(dateFormat.parse(str));

		// 获取连接
		String companyId = ApiTool.getUserMsg(request).getCompanyid();
		// 判断是否有权限
		log.debug("-*------------"+companyId+"-*------------");
		int aclb = new BucketAclUtil().checkListBucketAcl(request, bucketname, "GetObject", userCoreAccessService,
				ossAclService, ossBucketService);
		if (aclb == 0) {
			rspData.setStatus(ExptNum.NO_JURISDICTION.getCode() + "");
			rspData.setMsg(ExptNum.NO_JURISDICTION.getDesc());
			operatelog.setOperatestatus(2);
			operatelog.setErrormessage(ExptNum.NO_JURISDICTION.getDesc());
			operatelogService.insert(operatelog);
			return JSONUtils.createObjectJson(rspData);
		}

		// 判断是不是搜索文件
		if (fileNameS != null) {
			
			if (!ParamIsNull.isNull(fileNameS, zoneId, bucketname)) {
				rspData.setStatus(ExptNum.PARAM_NO_ALL.getCode() + "");
				rspData.setMsg(ExptNum.PARAM_NO_ALL.getDesc());
				operatelog.setOperatestatus(2);
				operatelog.setErrormessage(ExptNum.PARAM_NO_ALL.getDesc());
				operatelogService.insert(operatelog);
				return JSONUtils.createObjectJson(rspData);
			}
			List<OssObject> list = ossObjectService.selectByParam("", "companyId=" + "'" + companyId + "'"
					+ " and bucket_name=" + "'" + bucketname + "'" + " and fileName like " + "'%" + fileNameS + "%'");

			Map<String, Object> data = new HashMap<String, Object>();
			data.put("data", list);
			rspData.setStatus(ExptNum.SUCCESS.getCode() + "");
			rspData.setMsg(ExptNum.SUCCESS.getDesc());

			// 返回目录信息
			if (dirId != null) {
				OssObject objParam = new OssObject();
				objParam.setId(Integer.parseInt(dirId));
				// 返回的当前目录信息
				OssObject currentDir = ossObjectService.select(objParam).get(0);
				String currentDirStr = currentDir.getFilesrc();
				String[] splitedDir = currentDirStr.split("/");
				int length = splitedDir.length;
				List<Dictionary> dirPathList = new ArrayList<Dictionary>();
				String path = "";
				for (int i = 0; i < length; i++) {
					OssObject dirParam = new OssObject();
					path = path + splitedDir[i];
					dirParam.setFilesrc(path);
					OssObject PathObj = ossObjectService.select(dirParam).get(0);
					dirPathList.add(new Dictionary(splitedDir[i], PathObj.getId()));
					path = path + "/";
				}
				data.put("dirList", dirPathList);
				data.put("currentDir", new Dictionary(currentDir.getFilesrc(), Integer.parseInt(dirId)));
			} else {
				data.put("dirList", new ArrayList<Object>());
				data.put("currentDir", new Dictionary("", 0));
			}

			rspData.setData(data);
			operatelog.setOperatestatus(1);
			operatelogService.insert(operatelog);
			return JSONUtils.createObjectJson(rspData);

		} else {
			// 判断显示文件夹的参数是否满足
		
			if (!ParamIsNull.isNull(bucketname, zoneId)) {
				rspData.setStatus(ExptNum.PARAM_NO_ALL.getCode() + "");
				rspData.setMsg(ExptNum.PARAM_NO_ALL.getDesc());
				operatelog.setOperatestatus(2);
				operatelog.setErrormessage(ExptNum.PARAM_NO_ALL.getDesc());
				operatelogService.insert(operatelog);
				return JSONUtils.createObjectJson(rspData);
			}

			OssObject ooselect = new OssObject();
			ooselect.setBucketName(bucketname);
			if (dirId == null || "".equals(dirId) || "null".equals(dirId)) {
				ooselect.setFId(0);
				
			} else {
				ooselect.setFId(Integer.parseInt(dirId));
			}
			ooselect.setCompanyid(companyId);
			ooselect.setZoneid(zoneId);
			List<OssObject> ossObjects = ossObjectService.select(ooselect);
			
			for (OssObject ossObject : ossObjects) {
				String fileName = ossObject.getFilesrc();
				String condition = "fileSrc LIKE " + " '" + fileName + "/%" + "'" + "AND bucket_name=" + "'"
						+ bucketname + "'" + " and companyId=" + "'" + companyId + "'" + " and zoneId=" + "'" + zoneId
						+ "'";
				Double size = ossObjectService.selectByfileName(
						"(CASE  WHEN SUM(fileSize)  IS NULL THEN 0 ELSE SUM(fileSize) END ) AS sum1 ", condition);
				if (ossObject.getIsfile() == 1) {
					ossObject.setFilesize(size);
				}

			}
			Map<String, Object> resData = splitParentDir(dirId);
			rspData.setStatus(ExptNum.SUCCESS.getCode() + "");
			rspData.setMsg(ExptNum.SUCCESS.getDesc());
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("data", ossObjects);

			data.put("dirMeta", resData);

			// 日志记录

			operatelog.setOperatestatus(1);
			operatelogService.insert(operatelog);

			rspData.setData(data);
			return JSONUtils.createObjectJson(rspData);

		}

	}

	// 下载文件
	@RequestMapping(value = "/downloadsObject", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String downloadsObject(HttpServletRequest request, HttpServletResponse response, String bucketName,String zoneId,String dirId ) throws Exception {

		RspData rspData = new RspData();
		if (!ParamIsNull.isNull(bucketName, zoneId, dirId)) {
			rspData.setStatus(ExptNum.PARAM_NO_ALL.getCode() + "");
			rspData.setMsg(ExptNum.PARAM_NO_ALL.getDesc());
			return JSONUtils.createObjectJson(rspData);
		}

		// 获取连接
		String companyId = ApiTool.getUserMsg(request).getCompanyid();
		AmazonS3 conn = BucketAclUtil.getConnByCache(companyId, zoneId, 0, false);

		try {
			List<OssObject> list = ossObjectService.select(new OssObject().setId(Integer.parseInt(dirId)));

			String relName = list.get(0).getFilesrc();
			String dowloadName = list.get(0).getFilename();

	
			S3Object object = conn.getObject(bucketName, relName);

			InputStream in = object.getObjectContent();
			  int length = 0; 
			  while (length == 0) { 
				  length = in.available(); 
			  } 			  
			  log.debug("长度："+length);
			dowloadName = URLEncoder.encode(dowloadName, "UTF-8"); 
			    //设置输出长度
			   response.setContentType("application/force-download");// 设置强制下载不打开
               response.addHeader("Content-Disposition",
                      "attachment;fileName=" + dowloadName);// 设置文件名       
              byte[] buffer = new byte[1024];
              BufferedInputStream bis = null;
              try {
                  bis = new BufferedInputStream(in);
                  OutputStream os = response.getOutputStream();
                  int i = bis.read(buffer);
                  while (i != -1) {
                      os.write(buffer, 0, i);
                      i = bis.read(buffer);
                  }
              } catch (Exception e) {
            	  log.error(e);
                  
              } finally {
                  if (bis != null) {
                      try {
                          bis.close();
                      } catch (IOException e) {
                    	  log.error(e);
                          
                      }
                  }
                  if (in != null) {
                      try {
                          in.close();
                      } catch (IOException e) {
                    	  log.error(e);
                        
                      }
                  }
              }
			
			Date date = new Date();
			// 获取时间设置格式
			String dateStr = dateFormat.format(date);
			date = dateFormat.parse(dateStr);
			OssObject ossObject = new OssObject();
			ossObject.setLasttime(date);
			ossObject.setId(Integer.parseInt(dirId));
			
			ossObjectService.update(ossObject);
			Map<String,Object> data =new HashMap<String,Object>();
			data.put("fileName", dowloadName);
			rspData.setData(data);
			rspData.setStatus(ExptNum.SUCCESS.getCode() + "");
			rspData.setMsg(ExptNum.SUCCESS.getDesc());

		} catch (Exception e) {
			log.error(e);
			
			rspData.setStatus(ExptNum.FAIL.getCode() + "");
			rspData.setMsg(ExptNum.FAIL.getDesc());
		}
		
		return JSONUtils.createObjectJson(rspData);

	}

	// 获取外链
	@RequestMapping(value = "/geturl", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getSrc(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> pama = ApiTool.getBodyString(request);
		String bucketName = pama.get("bucketName");
		String zoneId = pama.get("zoneId");
		String timelimit = pama.get("timelimit");
		String fileSrc = pama.get("fileSrc");
		String protocol = pama.get("protocol");

		// 记录日志
		OssOperatelog operatelog = new OssOperatelog();
		operatelog.setOperatetarget("Object");
		operatelog.setOperatetype("获取外链");
		Map<String, String> maps = new HashMap<String, String>();
		maps.remove("mac");
		maps.put("Bucket名", bucketName);
		maps.put("空间ID", zoneId);
		maps.put("时间限制", timelimit);
		maps.put("文件夹名", fileSrc);
		operatelog.setOperatedes(maps.toString());
		operatelog.setZoneid((String) maps.get("zoneId"));
		// 设置公司ID
		operatelog.setCompanyid(ApiTool.getUserMsg(request).getCompanyid());
		// 设置IP地址
		operatelog.setOperatorip(BucketAclUtil.getIpAddress(request));
		// 系统当前时间
		Date date1 = new Date();
		String str = dateFormat.format(date1);
		operatelog.setOperatortime(dateFormat.parse(str));

		
		RspData rspData = new RspData();
		// 判断参数完整性
		if (!ParamIsNull.isNull(bucketName, zoneId, timelimit, fileSrc)) {
			rspData.setStatus(ExptNum.PARAM_NO_ALL.getCode() + "");
			rspData.setMsg(ExptNum.PARAM_NO_ALL.getDesc());
			operatelog.setOperatestatus(2);
			operatelog.setErrormessage(ExptNum.PARAM_NO_ALL.getDesc());
			operatelogService.insert(operatelog);
			return JSONUtils.createObjectJson(rspData);
		}
		// 判断是否有权限
		int aclb = new BucketAclUtil().checkListBucketAcl(request, bucketName, "GetObject", userCoreAccessService,
				ossAclService, ossBucketService);
		if (aclb == 0) {
			rspData.setStatus(ExptNum.NO_JURISDICTION.getCode() + "");
			rspData.setMsg(ExptNum.NO_JURISDICTION.getDesc());
			operatelog.setOperatestatus(2);
			operatelog.setErrormessage(ExptNum.NO_JURISDICTION.getDesc());
			operatelogService.insert(operatelog);
			return JSONUtils.createObjectJson(rspData);
		}

		String companyId = ApiTool.getUserMsg(request).getCompanyid();

		if (protocol == "" || protocol == null) {
			protocol = "https";
		}
		userCoreAccessService.updateByParam("protocol='" + protocol + "' where companyId='" + companyId + "'");

		AmazonS3 conn = BucketAclUtil.getConnByCache(companyId, zoneId, 1, true);

		OssUserDomain userDomain = new OssUserDomain();
		userDomain.setCompanyid(companyId);
		userDomain.setZoneid(zoneId);
		List<OssUserDomain> list = ossUserDomainService.select(userDomain);
		if (list.size() > 0) {
			if (list.get(0).getCephcustomdomain() == null || "".equals(list.get(0).getCephcustomdomain())) {

			} else {
				conn.setEndpoint(list.get(0).getCephcustomdomain());
			}
		}

		int timelimits = Integer.parseInt(timelimit);
		Calendar expirationCalendar = Calendar.getInstance();
		
		URL url = null;
		if (timelimits == 1) {
			expirationCalendar.add(Calendar.DAY_OF_YEAR, 1);
			url = conn.generatePresignedUrl(bucketName, fileSrc, expirationCalendar.getTime());
		}
		if (timelimits == 2) {
			expirationCalendar.add(Calendar.DAY_OF_YEAR, 7);
			url = conn.generatePresignedUrl(bucketName, fileSrc, expirationCalendar.getTime());

		} 
		if(timelimits ==3) {
			expirationCalendar.add(Calendar.DAY_OF_YEAR, 999999999);
			url = conn.generatePresignedUrl(bucketName, fileSrc, expirationCalendar.getTime());
		}
		//如果是0 则下载文件
		if(timelimits ==0){
			expirationCalendar.add(Calendar.HOUR_OF_DAY, 1);
			url = conn.generatePresignedUrl(bucketName, fileSrc, expirationCalendar.getTime());
		}

		rspData.setStatus(ExptNum.SUCCESS.getCode() + "");
		rspData.setMsg(ExptNum.SUCCESS.getDesc());
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("data", url);
		operatelog.setOperatestatus(1);
		operatelogService.insert(operatelog);
		rspData.setData(data);
		return JSONUtils.createObjectJson(rspData);

	}

	// 上传文件
	@RequestMapping(value = "/uploadObject", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String uploadObject(HttpServletRequest request, HttpServletResponse response, String bucketName,
			String zoneId, String dirId, MultipartFile uploadFile) throws Exception {
		

		OssOperatelog operatelog = new OssOperatelog();
		operatelog.setOperatetarget("Object");
		operatelog.setOperatetype("上传文件");

		Map<String, String> maps = new HashMap<String, String>();
		maps.remove("mac");
		maps.put("Bucket名", bucketName);
		maps.put("空间ID", zoneId);
		maps.put("文件夹ID", dirId);
		maps.put("文件名", uploadFile.getName());
		operatelog.setOperatedes(maps.toString());

		operatelog.setZoneid((String) maps.get("zoneId"));

		// 设置公司ID
		operatelog.setCompanyid(ApiTool.getUserMsg(request).getCompanyid());
		// 设置IP地址
		operatelog.setOperatorip(BucketAclUtil.getIpAddress(request));

		// 系统当前时间
		
		Date date1 = new Date();
		String str = dateFormat.format(date1);
		operatelog.setOperatortime(dateFormat.parse(str));
		RspData rspData = new RspData();
		if (!ParamIsNull.isNull(bucketName, zoneId, uploadFile)) {
			rspData.setStatus(ExptNum.PARAM_NO_ALL.getCode() + "");
			rspData.setMsg(ExptNum.PARAM_NO_ALL.getDesc());
			operatelog.setOperatestatus(2);
			operatelog.setErrormessage(ExptNum.PARAM_NO_ALL.getDesc());
			// 保存系统日志
			operatelogService.insert(operatelog);
			return JSONUtils.createObjectJson(rspData);
		}
		
		String originalFilename = uploadFile.getOriginalFilename();
		// 判断是否有权限
		int aclb = new BucketAclUtil().checkListBucketAcl(request, bucketName, "PutObject", userCoreAccessService,
				ossAclService, ossBucketService);
		if (aclb == 0) {
			rspData.setStatus(ExptNum.NO_JURISDICTION.getCode() + "");
			rspData.setMsg(ExptNum.NO_JURISDICTION.getDesc());
			operatelog.setOperatestatus(2);
			operatelog.setErrormessage(ExptNum.NO_JURISDICTION.getDesc());
			// 保存系统日志
			operatelogService.insert(operatelog);
			return JSONUtils.createObjectJson(rspData);
		}
		
		// 获取连接
		String companyId = ApiTool.getUserMsg(request).getCompanyid();
		AmazonS3 conn = BucketAclUtil.getConnByCache(companyId, zoneId, 0, true);
		
		//检查配额是否已满
		List<UserTypeSource> userTypeSource = userTypeSourceService.selectByParam("", " companyId='"+companyId+"'");
		Double usedSize = ossObjectService.selectByfileName(
				"(CASE  WHEN SUM(fileSize)  IS NULL THEN 0 ELSE SUM(fileSize) END ) AS sum1 ",
				"companyId='" + companyId + "' and zoneId ='" + zoneId + "'")/(1024*1024);
		Integer maxOssStore = 0;
		try {
			maxOssStore = userTypeSource.get(0).getMaxossstore();
		} catch (Exception e) {
			maxOssStore = 20;
		}
		if(maxOssStore <= usedSize){
			rspData.setStatus(ExptNum.QUOTA_IS_FULL.getCode() + "");
			rspData.setMsg(ExptNum.QUOTA_IS_FULL.getDesc());
			operatelog.setOperatestatus(2);
			operatelog.setErrormessage(ExptNum.QUOTA_IS_FULL.getDesc());
			// 保存系统日志
			operatelogService.insert(operatelog);
			return JSONUtils.createObjectJson(rspData);
			
		}
		
		// 设置文件路径
		OssObject ossObjectsrc = new OssObject();
		String src = null;
		String fileSrc = null;
		if (dirId == null || "null".equals(dirId) || "".equals(dirId)) {
			src = originalFilename;
			fileSrc = originalFilename;
		} else {
			src = ossObjectService.select(ossObjectsrc.setId(Integer.parseInt(dirId))).get(0).getFilesrc();
			fileSrc = src + "/" + originalFilename;
		}

		OssObject ossObjectrepeat = new OssObject();
		ossObjectrepeat.setFilename(originalFilename);

		if (dirId == null || "null".equals(dirId) || "".equals(dirId)) {
			ossObjectrepeat.setFId(0);
		} else {
			ossObjectrepeat.setFId(Integer.parseInt(dirId));
		}
		ossObjectrepeat.setBucketName(bucketName);
		ossObjectrepeat.setZoneid(zoneId);
		ossObjectrepeat.setCompanyid(companyId);
		List<OssObject> list = ossObjectService.select(ossObjectrepeat);
		

		if (!list.isEmpty()) {
			try {
				
				multipartUpload(bucketName, uploadFile, conn, fileSrc);
				
			} catch (IOException e) {
				log.error(e);
				operatelog.setOperatestatus(2);
				operatelog.setErrormessage(ExptNum.UPLOAD_FILE_FAIL.getDesc());
				// 保存系统日志
				operatelogService.insert(operatelog);
				rspData.setMsg(ExptNum.UPLOAD_FILE_FAIL.getDesc());
				rspData.setStatus(ExptNum.UPLOAD_FILE_FAIL.getCode() + "");
				//log.error(e);
				return JSONUtils.createObjectJson(rspData);
			}
			rspData.setStatus(ExptNum.SUCCESS.getCode() + "");
			rspData.setMsg(ExptNum.SUCCESS.getDesc());
		} else {
		
			try {
				multipartUpload(bucketName, uploadFile, conn, fileSrc);
			} catch (IOException e) {
				log.error(e);
				operatelog.setOperatestatus(2);
				operatelog.setErrormessage(ExptNum.UPLOAD_FILE_FAIL.getDesc());
				// 保存系统日志
				operatelogService.insert(operatelog);
				rspData.setMsg(ExptNum.UPLOAD_FILE_FAIL.getDesc());
				rspData.setStatus(ExptNum.UPLOAD_FILE_FAIL.getCode() + "");
				log.error(e);
				return JSONUtils.createObjectJson(rspData);
			}
			
			

		   

			// 上传时间
			Date nowDate = new Date();
			// 获取时间设置格式
			String dateStr = dateFormat.format(nowDate);
			nowDate = dateFormat.parse(dateStr);
			// 获取object大小
			double size = (uploadFile.getSize() / 1024.0);// byte换算成kb

			OssObject ossObject = new OssObject();
			String code = Uuid.getGenerateCode();
			// 添加参数
			
			
			int one = originalFilename.lastIndexOf(".");//图片分类：1 图片 ，2 视频 ，0普通
			String suffix = originalFilename.substring((one+1),originalFilename.length());

	  		
	  		String[] video = {"mp4","flv","avi","rm","rmvb","wmv"};
	  		String[] pic = {"bmp","gif","jfif","jpe","jpeg","jpg","png","ico",  "0"};
	  		int filetype = 0; 
 
	  		for(int i = 0;i<video.length;i++){
	  			if(suffix.equals(video[i])){
	  				
	  				filetype = 2;
	  				break;
	  			}
	  		}
	  		
	  		for(int i = 0;i<pic.length;i++){
	  			if(suffix.equals(pic[i])){
	  				
	  				filetype = 1;
	  				break;
	  			}
	  		}
	  		System.err.println("最终文件类型："+filetype);
			ossObject.setFiletype(filetype);
			ossObject.setFilename(originalFilename);
			ossObject.setBucketName(bucketName);
			ossObject.setCompanyid(companyId);
			ossObject.setCreatetime(dateFormat.parse(str));// 上传文件时间
			ossObject.setIsfile(0);
			ossObject.setCode(code);
			if (dirId == null || "null".equals(dirId) || "".equals(dirId)) {
				ossObject.setFId(0);
			} else {
				ossObject.setFId(Integer.parseInt(dirId));
			}
			ossObject.setLasttime(dateFormat.parse(str));
			ossObject.setFilesize(size);
			ossObject.setFilesrc(fileSrc);
			ossObject.setZoneid(zoneId);
			ossObjectService.insert(ossObject);
			
			
			Double capacity = 0.0;
			List<OssBucket> bucketlist = ossBucketService.selectByParam("", " companyId='" +companyId+"'"+" and zoneId='"+zoneId+"'"+" and name='"+bucketName+"'");
			if(!bucketlist.isEmpty()){
				capacity = bucketlist.get(0).getCapacity();
			}

			double nowCapacity = capacity +  size;
			log.debug("上传  原大小："+capacity+"现大小："+nowCapacity);
			ossBucketService.updateByParam(" capacity = "+nowCapacity+" where companyId='" +companyId+"'"+" and zoneId='"+zoneId+"'"+" and name='"+bucketName+"'");
			rspData.setStatus(ExptNum.SUCCESS.getCode() + "");
			rspData.setMsg(ExptNum.SUCCESS.getDesc());
		}
		operatelog.setOperatestatus(ExptNum.SUCCESS.getCode());
		// 保存系统日志
		operatelogService.insert(operatelog);
		return JSONUtils.createObjectJson(rspData);
	}



	// 获取用户总文件大小
	@RequestMapping(value = "/getAllSize", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getBig(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> pama = ApiTool.getBodyString(request);
		String bucketName = pama.get("bucketName");
		// 记录日志
		OssOperatelog operatelog = new OssOperatelog();
		operatelog.setOperatetarget("Object");
		operatelog.setOperatetype("获取用户文件大小");
		Map<String, String> maps = new HashMap<String, String>();
		maps.remove("mac");
		operatelog.setOperatedes(maps.toString());
		operatelog.setZoneid((String) maps.get("zoneId"));
		// 设置公司ID
		operatelog.setCompanyid(ApiTool.getUserMsg(request).getCompanyid());
		// 设置IP地址
		operatelog.setOperatorip(BucketAclUtil.getIpAddress(request));
		// 系统当前时间
		Date date1 = new Date();
		String str = dateFormat.format(date1);
		operatelog.setOperatortime(dateFormat.parse(str));
		
		Calendar c = Calendar.getInstance();
        //过去一月
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        String startTime = dateFormat.format(m);
        Date date = new Date();
        String endTime = dateFormat.format(date);
		

		RspData rspData = new RspData();
		String zoneId = pama.get("zoneId");
		String companyId = ApiTool.getUserMsg(request).getCompanyid();
		Double size = null;
		Double sizeMouth = null;
		
		if(!StringUtils.isEmpty(bucketName)){
			List<OssBucket> bucketlist = ossBucketService.selectByParam("",
					"companyId='" + companyId + "' and zoneId ='" + zoneId + "'"+" and name = '"+bucketName+"'");
			if(bucketlist.isEmpty()){
				size = 0.0;
			}else{
				size = bucketlist.get(0).getCapacity();
			}
			sizeMouth = ossObjectService.selectByfileName(
					"(CASE  WHEN SUM(fileSize)  IS NULL THEN 0 ELSE SUM(fileSize) END ) AS sum1 ",
     			"companyId='" + companyId + "' and zoneId ='" + zoneId + "'"+" and bucket_name = '"+bucketName+"' and"+" DATE(createTime) between DATE('" +startTime + "') and DATE('" +  endTime + "')");
			
		}else{
			size = ossObjectService.selectByfileName(
					"(CASE  WHEN SUM(fileSize)  IS NULL THEN 0 ELSE SUM(fileSize) END ) AS sum1 ",
					"companyId='" + companyId + "' and zoneId ='" + zoneId + "'");
		    sizeMouth = ossObjectService.selectByfileName(
					"(CASE  WHEN SUM(fileSize)  IS NULL THEN 0 ELSE SUM(fileSize) END ) AS sum1 ",
					"companyId='" + companyId + "' and zoneId ='" + zoneId + "'"+" and"+" DATE(createTime) between DATE('" + startTime + "') and DATE('" + endTime + "')");
		}
		
		rspData.setStatus(ExptNum.SUCCESS.getCode() + "");
		rspData.setMsg(ExptNum.SUCCESS.getDesc());
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("allsize", size);
		data.put("mouthsize", sizeMouth);
		data.put("startTime", startTime);
		data.put("endTime", endTime);
		
		rspData.setData(data);
		operatelog.setOperatestatus(1);
		operatelogService.insert(operatelog);
		return JSONUtils.createObjectJson(rspData);

	}

	
	
    //执行分片上传的方法体
	private void multipartUpload(String bucketName, MultipartFile uploadFile, AmazonS3 conn, String fileSrc)
			throws IOException {
		List<PartETag> partETags = new ArrayList<PartETag>();

		
		InitiateMultipartUploadRequest initRequest = new InitiateMultipartUploadRequest(bucketName, fileSrc);
		InitiateMultipartUploadResult initResponse = conn.initiateMultipartUpload(initRequest);

		
		long filePosition = 0;
		
		
		long contentLength = uploadFile.getSize();
		String partSizeByConfig = Prop.getInstance().getPropertiesByPro("ruirados.properties", "mutiPart.part.size");
		long partSize = Integer.parseInt(partSizeByConfig) * 1024 * 1024; // Set part size to 10 MB.
		for (int i = 1; filePosition < contentLength; i++) {
			
			
			partSize = Math.min(partSize, (contentLength - filePosition));

			// Create the request to upload a part.
			UploadPartRequest uploadRequest = new UploadPartRequest().withBucketName(bucketName).withKey(fileSrc)
					.withUploadId(initResponse.getUploadId()).withPartNumber(i).withFileOffset(filePosition)
					.withInputStream(uploadFile.getInputStream()).withPartSize(partSize);

			// Upload the part and add the response's ETag to our list.
			UploadPartResult uploadResult = conn.uploadPart(uploadRequest);
			partETags.add(uploadResult.getPartETag());
			filePosition += partSize;
		}

		// Complete the multipart upload.
		CompleteMultipartUploadRequest compRequest = new CompleteMultipartUploadRequest(bucketName, fileSrc,
				initResponse.getUploadId(), partETags);

		conn.completeMultipartUpload(compRequest);
	}
	
	//执行返回目录信息的方法体
	private Map<String, Object> splitParentDir(String dirId) {
		Map<String, Object> resData = new HashMap<String, Object>();
		if (dirId != null) {
			OssObject objParam = new OssObject();
			objParam.setId(Integer.parseInt(dirId));
			// 返回的当前目录信息
			OssObject currentDir = ossObjectService.select(objParam).get(0);
			String currentDirStr = currentDir.getFilesrc();
			String[] splitedDir = currentDirStr.split("/");
			int length = splitedDir.length;
			List<Dictionary> dirPathList = new ArrayList<Dictionary>();
			String path = "";
			for (int i = 0; i < length; i++) {
				OssObject dirParam = new OssObject();
				path = path + splitedDir[i];
				dirParam.setFilesrc(path);
				OssObject PathObj = ossObjectService.select(dirParam).get(0);
				dirPathList.add(new Dictionary(splitedDir[i], PathObj.getId()));
				path = path + "/";
			}
			resData.put("dirList", dirPathList);
			resData.put("currentDir", new Dictionary(currentDir.getFilesrc(), Integer.parseInt(dirId)));
		} else {
			resData.put("dirList", new ArrayList<Object>());
			resData.put("currentDir", new Dictionary("", 0));
		}
		return resData;
	}
	


	
	

}
