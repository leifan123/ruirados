package com.ruirados.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruirados.dao.OssMonitorDao;
import com.ruirados.pojo.OssMonitor;
import com.ruirados.service.OssMonitorService;

public class OssMonitorImpl implements OssMonitorService {

	private OssMonitorDao ossMonitorDao;

	public void setOssMonitorDao(OssMonitorDao ossMonitorDao){
		 this.ossMonitorDao = ossMonitorDao;
	}

	public void insert(OssMonitor ossmonitor){
		ossMonitorDao.insert(ossmonitor);
	}

	public List<OssMonitor> select(OssMonitor ossmonitor){
		return ossMonitorDao.select(ossmonitor);
	}

	public void update(OssMonitor ossmonitor){
		ossMonitorDao.update(ossmonitor);
	}

	public void delete(OssMonitor ossmonitor){
		ossMonitorDao.delete(ossmonitor);
	}

	public List<OssMonitor> selectByParam(String field,String param, String order){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", param);
		map.put("order", order);
		return ossMonitorDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", param);
		  ossMonitorDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", param);
		  ossMonitorDao.deleteByParam(map);
	}

	public List<String> selectByCount(String field, String param, String order) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", param);
		map.put("order", order);
		return ossMonitorDao.selectByCount(map);
	}

	public List<Integer> selectByHour(String field, String param, String order) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", param);
		map.put("order", order);
		return ossMonitorDao.selectByHour(map);
	}

}

