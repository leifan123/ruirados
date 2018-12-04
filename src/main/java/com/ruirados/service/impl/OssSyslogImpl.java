package com.ruirados.service.impl;

import com.ruirados.pojo.OssSyslog;
import com.ruirados.dao.OssSyslogDao;
import com.ruirados.service.OssSyslogService;
import com.ruirados.util.NormName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OssSyslogImpl implements OssSyslogService {

	private OssSyslogDao ossSyslogDao;

	public void setOssSyslogDao(OssSyslogDao ossSyslogDao){
		 this.ossSyslogDao = ossSyslogDao;
	}

	public void insert(OssSyslog osssyslog){
		ossSyslogDao.insert(osssyslog);
	}

	public List<OssSyslog> select(OssSyslog osssyslog){
		return ossSyslogDao.select(osssyslog);
	}

	public void update(OssSyslog osssyslog){
		ossSyslogDao.update(osssyslog);
	}

	public void delete(OssSyslog osssyslog){
		ossSyslogDao.delete(osssyslog);
	}

	public List<OssSyslog> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", NormName.normSql(param));
		 return ossSyslogDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  ossSyslogDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  ossSyslogDao.deleteByParam(map);
	}

}

