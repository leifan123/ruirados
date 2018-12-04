package com.ruirados.dao;

import com.ruirados.pojo.OssMonitor;

import java.util.List;

import java.util.Map;

public interface OssMonitorDao {

	public void insert(OssMonitor ossmonitor);

	public List<OssMonitor> select(OssMonitor ossmonitor);

	public void update(OssMonitor ossmonitor);

	public void delete(OssMonitor ossmonitor);

	public List<OssMonitor> selectByParam(Map<String, String> param);

	public void updateByParam(Map<String, String> params);

	public void deleteByParam(Map<String, String> params);
	
	public List<String> selectByCount(Map<String, String> param);
	
	public List<Integer> selectByHour(Map<String, String> param);
}

