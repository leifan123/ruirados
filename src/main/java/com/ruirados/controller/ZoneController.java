package com.ruirados.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruirados.pojo.OssZone;
import com.ruirados.pojo.RspData;
import com.ruirados.service.OssZoneService;
import com.ruirados.util.ExptNum;
import com.ruirados.util.JSONUtils;
import com.ruirados.util.MappingConfigura;

@Controller
@RequestMapping(MappingConfigura.ZONE)
public class ZoneController {

	@Autowired
	private OssZoneService OssZoneService;
	private Logger log = Logger.getLogger(ZoneController.class);

	/**
	 * 区域List
	 * 
	 * @return
	 */
	@RequestMapping(value = "zoneList", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String zoneList() {
		RspData rd = new RspData();
		Map<String, Object> map = new HashMap<String, Object>();
		List<OssZone> list = OssZoneService.select(null);
		if (list == null || list.size() == 0) {
			rd.setStatus(ExptNum.FAIL.getCode() + "");
			rd.setMsg(ExptNum.FAIL.getDesc());
			return JSONUtils.createObjectJson(rd);
		}
		map.put("zoneList", list);
		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		rd.setData(map);
		return JSONUtils.createObjectJson(rd);
	}

	/**
	 * 默认区域
	 * 
	 * @return
	 */
	@RequestMapping(value = "defaultZone", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String defaultZone() {
		RspData rd = new RspData();
		Map<String, Object> map = new HashMap<String, Object>();
		List<OssZone> list = OssZoneService.select(null);
		map.put("zoneList", list.get(0));
		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		rd.setData(map);
		return JSONUtils.createObjectJson(rd);
	}

	/**
	 * 获取zoneId
	 * 
	 * @return
	 */
	@RequestMapping(value = "getZoneId", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getZoneId(String zonename) {
		log.debug("zoneName:"+zonename);
		RspData rd = new RspData();
		Map<String, Object> map = new HashMap<String, Object>();
		List<OssZone> list = OssZoneService.select(new OssZone().setZonename(zonename));
		log.debug("list:"+list);
		map.put("zoneId", list.get(0).getZoneid());
		log.debug("zoneId:"+list.get(0).getZoneid());
		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		rd.setData(map);
		return JSONUtils.createObjectJson(rd);
	}
	
	/**
	 * 获取域名
	 * 
	 * @return
	 */
	@RequestMapping(value = "getZoneDomain", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getZoneDomain(String zonename) {
		RspData rd = new RspData();
		Map<String, Object> map = new HashMap<String, Object>();
		List<OssZone> list = OssZoneService.select(new OssZone().setZonename(zonename));
		map.put("zoneDomain", list.get(0).getServerip());
		
		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		rd.setData(map);
		return JSONUtils.createObjectJson(rd);
	}
	
}
