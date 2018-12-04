package com.ruirados.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruirados.dao.OssBucketDao;
import com.ruirados.pojo.OssBucket;
import com.ruirados.service.OssBucketService;
import com.ruirados.util.NormName;

public class OssBucketImpl implements OssBucketService {

	private OssBucketDao ossBucketDao;

	public void setOssBucketDao(OssBucketDao ossBucketDao){
		 this.ossBucketDao = ossBucketDao;
	}

	public int insert(OssBucket ossbucket){
		return ossBucketDao.insert(ossbucket);
	}

	public List<OssBucket> select(OssBucket ossbucket){
		return ossBucketDao.select(ossbucket);
	}

	public void update(OssBucket ossbucket){
		ossBucketDao.update(ossbucket);
	}

	public int delete(OssBucket ossbucket){
		return ossBucketDao.delete(ossbucket);
	}

	public List<OssBucket> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", NormName.normSql(param));
		 return ossBucketDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", param);
		 ossBucketDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  ossBucketDao.deleteByParam(map);
	}

	public int selectCount(String field, String param) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", NormName.normSql(param));
		return ossBucketDao.selectCount(map);
	}

}

