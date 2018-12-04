package com.ruirados.controller;

import java.text.SimpleDateFormat;
import java.util.Collections;
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

import com.ruirados.pojo.OssOperatelog;
import com.ruirados.pojo.PageNum;
import com.ruirados.pojo.RspData;
import com.ruirados.service.OssOperatelogService;
import com.ruirados.util.ApiTool;
import com.ruirados.util.Config;
import com.ruirados.util.ExptNum;
import com.ruirados.util.GetResult;
import com.ruirados.util.JSONUtils;
import com.ruirados.util.MappingConfigura;
import com.ruirados.util.ParamIsNull;

@Controller
@RequestMapping(MappingConfigura.OPERATE_LOG)
public class OperateLogController {
	
	@Autowired
	private OssOperatelogService operatelogService;
	
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
	private Logger log = Logger.getLogger(OperateLogController.class);
	
	
	
	/**
	 * 查询当前用户的操作日志
	 * 
	 * @param request
	 * @param maps
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "selectLogs", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String selectLogs(HttpServletRequest request) throws Exception {

		request.getSession().setAttribute("time", "1");

		Map<String, String> maps = ApiTool.getBodyString(request);

		String companyId = ApiTool.getUserMsg(request).getCompanyid();

		String pages = (String) maps.get("pageNum");
		
		String pageSu = (String) maps.get("pageSum");
		
		String operateTarget = (String)maps.get("operateTarget");
		
		RspData rd = new RspData();
		
		// 参数完整性判断
		if (!ParamIsNull.isNull(pages, pageSu)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rd);
		}
		
		int pageNum = Integer.parseInt(pages);
		int pageSum = Integer.parseInt(pageSu);
		
		int limitStart = (pageNum-1) *(pageSum);
		int pageList = 0;
		List<OssOperatelog> list = null;
		if(operateTarget == null){
			pageList = operatelogService.selectCount("count(*)", "companyId='" + companyId + "'");
			list = operatelogService.selectByParam(null, "companyId='"
					+ request.getSession().getAttribute("username").toString() + "'" +" order by operatortime desc "+ " limit " + limitStart + "," + pageSum);
		}	else{
			pageList = operatelogService.selectCount("count(*)", "companyId='" + companyId + "' and operateTarget = '" + operateTarget + "'");
			list = operatelogService.selectByParam(null, "companyId='"
					+ request.getSession().getAttribute("username").toString() + "' and operateTarget = '" + operateTarget +"' order by operatortime desc "+ " limit " + limitStart + "," + pageSum);
		}

		PageNum page = new PageNum();
		page.setPage(pageNum + 1);
		page.setPageSum(pageSum);

		int sum = 0;
		if (pageList % pageSum == 0) {
			sum = pageList / pageSum;
		} else {
			sum = pageList / pageSum + 1;
		}

		page.setPageCount(sum);
		page.setSumCount(pageList);

		OssOperatelog operatelog = new OssOperatelog();
		operatelog.setCompanyid(request.getSession().getAttribute("username").toString());
		
		operatelog.setCompanyid(companyId);

		Map<String, Object> map = new HashMap<String, Object>();
		if (list.size() == 0) {
			map.put("logs", "无数据！");
		} else {
			map.put("logs", list);
			map.put("page", page);
		}

		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		rd.setData(map);
		return JSONUtils.createObjectJson(rd);
	}
	
	/**
	 * 查询某个时间段用户的操作日志
	 * @param request
	 * @param maps
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "selectLogFromTime", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String selectLogFromTime(HttpServletRequest request) throws Exception{
		
		Map<String, String> maps = ApiTool.getBodyString(request);
		
		request.getSession().setAttribute("time", "2");
		
		String startTime = (String)maps.get("startTime");
		String endTime = (String)maps.get("endTime");
		
		String str = 	(String) request.getSession().getAttribute("time");
		if(null == startTime || "".equals(startTime)){
			if("2".equals(str)){
				startTime =  (String) request.getSession().getAttribute("startTime");
				endTime = (String) request.getSession().getAttribute("endTime");
			}
		}	else{
			request.getSession().setAttribute("startTime", startTime);
			request.getSession().setAttribute("endTime", endTime);
		}
		
		String pages = (String) maps.get("pageNum");
		
		String pageSu = (String) maps.get("pageSum");
		
		String operateTarget = (String)maps.get("operateTarget");
		
		RspData rd = new RspData();
		
		// 参数完整性判断
		if (!ParamIsNull.isNull(pages, pageSu)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rd);
		}
		
		int pageNum = Integer.parseInt(pages);
		log.debug("pageNum ---> " + pageNum);
		int pageSum = Integer.parseInt(pageSu);
		log.debug("pageSum ---> " + pageSum);
		
		String companyId = ApiTool.getUserMsg(request).getCompanyid();
		int limitstart = (pageNum-1) *(pageSum);
		int pageList = 0;
		List<OssOperatelog> list = null;
		if(operateTarget == null){
			pageList = operatelogService.selectCount("count(*)", "companyId='" + request.getSession().getAttribute("username").toString() + "' and"
					+" DATE(operatorTime) between DATE('" + endTime + "') and DATE('" + startTime + "')");
			list = operatelogService.selectByParam(null, "companyId='" + companyId + "' and"
					+" DATE(operatorTime) between DATE('" + endTime + "') and DATE('" + startTime + "') limit " + limitstart + "," + pageSum);
		}	else{
			pageList = operatelogService.selectCount("count(*)", "companyId='" + request.getSession().getAttribute("username").toString() + "' and"
					+" DATE(operatorTime) between DATE('" + endTime + "') and DATE('" + startTime + "') and operateTarget = '" + operateTarget + "'");
			list = operatelogService.selectByParam(null, "companyId='" + companyId + "' and"
					+" DATE(operatorTime) between DATE('" + endTime + "') and DATE('" + startTime + "') and operateTarget = '" + operateTarget + "'  limit " + limitstart + "," + pageSum);
		}
		
		log.debug("近一天总条数："+pageList);
		PageNum page = new PageNum();
		page.setPage(pageNum + 1);
		page.setPageSum(pageSum);
		
		int sum = 0;
		if(pageList % pageSum == 0){
			sum = pageList/pageSum;
		}	else{
			sum = pageList/pageSum + 1;
		}
		
		page.setPageCount(sum);
		page.setSumCount(pageList);
		log.debug("总页数："+sum);
		OssOperatelog operatelog = new OssOperatelog();
		
		operatelog.setCompanyid(companyId);
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(list.size() == 0){
			map.put("logs", "无数据");
		}else{
			map.put("logs", list);
			map.put("page", page);
		}
		
		log.debug(list.toArray());
		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		rd.setData(map);
		return JSONUtils.createObjectJson(rd);
	}
	
	/**
	 * 获取操作对象
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getoperateTarget", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getoperateTarget(HttpServletRequest request){
		String companyId = ApiTool.getUserMsg(request).getCompanyid();
		RspData rd = new RspData();
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> selectOperateTarget = operatelogService.selectOperateTarget("distinct(operateTarget)", "companyId = '" + companyId + "'");
		selectOperateTarget.removeAll(Collections.singleton(null));
		map.put("data",selectOperateTarget);
		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		rd.setData(map);
		return JSONUtils.createObjectJson(rd);
	}
}
