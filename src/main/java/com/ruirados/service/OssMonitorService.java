package com.ruirados.service;

import com.ruirados.pojo.OssMonitor;

import java.util.List;
import java.util.Map;

public interface OssMonitorService {

	public void insert(OssMonitor ossmonitor);

	public List<OssMonitor> select(OssMonitor ossmonitor);

	public void update(OssMonitor ossmonitor);

	public void delete(OssMonitor ossmonitor);

	public List<OssMonitor> selectByParam(String field,String param, String order);

	public void updateByParam(String param);

	public void deleteByParam(String param);

	public List<String> selectByCount(String field,String param, String order);
	
	public List<Integer> selectByHour(String field,String param, String order);
}

