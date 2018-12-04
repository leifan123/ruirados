package com.ruirados.service.impl;

import com.ruirados.pojo.CustomMonitorIndex;
import com.ruirados.dao.CustomMonitorIndexDao;
import com.ruirados.service.CustomMonitorIndexService;
import com.ruirados.util.NormName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomMonitorIndexImpl implements CustomMonitorIndexService {

	private CustomMonitorIndexDao customMonitorIndexDao;

	public void setCustomMonitorIndexDao(CustomMonitorIndexDao customMonitorIndexDao){
		 this.customMonitorIndexDao = customMonitorIndexDao;
	}

	public void insert(CustomMonitorIndex custommonitorindex){
		customMonitorIndexDao.insert(custommonitorindex);
	}

	public List<CustomMonitorIndex> select(CustomMonitorIndex custommonitorindex){
		return customMonitorIndexDao.select(custommonitorindex);
	}

	public void update(CustomMonitorIndex custommonitorindex){
		customMonitorIndexDao.update(custommonitorindex);
	}

	public void delete(CustomMonitorIndex custommonitorindex){
		customMonitorIndexDao.delete(custommonitorindex);
	}

	public List<CustomMonitorIndex> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", param);
		 return customMonitorIndexDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  customMonitorIndexDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  customMonitorIndexDao.deleteByParam(map);
	}

}

