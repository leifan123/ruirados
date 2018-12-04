package com.ruirados.service.impl;

import com.ruirados.pojo.OssUsedMonitor;
import com.ruirados.dao.OssUsedMonitorDao;
import com.ruirados.service.OssUsedMonitorService;
import com.ruirados.util.NormName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OssUsedMonitorImpl implements OssUsedMonitorService {

	private OssUsedMonitorDao ossUsedMonitorDao;

	public void setOssUsedMonitorDao(OssUsedMonitorDao ossUsedMonitorDao){
		 this.ossUsedMonitorDao = ossUsedMonitorDao;
	}

	public void insert(OssUsedMonitor ossusedmonitor){
		ossUsedMonitorDao.insert(ossusedmonitor);
	}

	public List<OssUsedMonitor> select(OssUsedMonitor ossusedmonitor){
		return ossUsedMonitorDao.select(ossusedmonitor);
	}

	public void update(OssUsedMonitor ossusedmonitor){
		ossUsedMonitorDao.update(ossusedmonitor);
	}

	public void delete(OssUsedMonitor ossusedmonitor){
		ossUsedMonitorDao.delete(ossusedmonitor);
	}

	public List<OssUsedMonitor> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", NormName.normSql(param));
		 return ossUsedMonitorDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  ossUsedMonitorDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  ossUsedMonitorDao.deleteByParam(map);
	}

}

