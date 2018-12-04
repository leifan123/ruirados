package com.ruirados.service.impl;


import com.ruirados.dao.GetfreevminformationDao;
import com.ruirados.service.GetfreevminformationService;
import com.ruirados.util.NormName;
import com.yunrui.pojo.Getfreevminformation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetfreevminformationImpl implements GetfreevminformationService {

	private GetfreevminformationDao getfreevminformationDao;

	public void setGetfreevminformationDao(GetfreevminformationDao getfreevminformationDao){
		 this.getfreevminformationDao = getfreevminformationDao;
	}

	public void insert(Getfreevminformation getfreevminformation){
		getfreevminformationDao.insert(getfreevminformation);
	}

	public List<Getfreevminformation> select(Getfreevminformation getfreevminformation){
		return getfreevminformationDao.select(getfreevminformation);
	}

	public void update(Getfreevminformation getfreevminformation){
		getfreevminformationDao.update(getfreevminformation);
	}

	public void delete(Getfreevminformation getfreevminformation){
		getfreevminformationDao.delete(getfreevminformation);
	}

	public List<Getfreevminformation> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", NormName.normSql(param));
		 return getfreevminformationDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  getfreevminformationDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  getfreevminformationDao.deleteByParam(map);
	}
	public List<Getfreevminformation> selectBySql(String sql) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("sql", sql);
		return getfreevminformationDao.selectBySql(map);
	}
}

