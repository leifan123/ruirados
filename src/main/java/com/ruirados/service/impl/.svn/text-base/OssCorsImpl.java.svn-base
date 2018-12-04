package com.ruirados.service.impl;

import com.ruirados.pojo.OssCors;
import com.ruirados.dao.OssCorsDao;
import com.ruirados.service.OssCorsService;
import com.ruirados.util.NormName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OssCorsImpl implements OssCorsService {

	private OssCorsDao ossCorsDao;

	public void setOssCorsDao(OssCorsDao ossCorsDao){
		 this.ossCorsDao = ossCorsDao;
	}

	public void insert(OssCors osscors){
		ossCorsDao.insert(osscors);
	}

	public List<OssCors> select(OssCors osscors){
		return ossCorsDao.select(osscors);
	}

	public void update(OssCors osscors){
		ossCorsDao.update(osscors);
	}

	public void delete(OssCors osscors){
		ossCorsDao.delete(osscors);
	}

	public List<OssCors> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", NormName.normSql(param));
		 return ossCorsDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", param);
		  ossCorsDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  ossCorsDao.deleteByParam(map);
	}

}

