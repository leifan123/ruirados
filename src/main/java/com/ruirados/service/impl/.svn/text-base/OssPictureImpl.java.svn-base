package com.ruirados.service.impl;

import com.ruirados.pojo.OssPicture;
import com.ruirados.dao.OssPictureDao;
import com.ruirados.service.OssPictureService;
import com.ruirados.util.NormName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OssPictureImpl implements OssPictureService {

	private OssPictureDao ossPictureDao;

	public void setOssPictureDao(OssPictureDao ossPictureDao){
		 this.ossPictureDao = ossPictureDao;
	}

	public void insert(OssPicture osspicture){
		ossPictureDao.insert(osspicture);
	}

	public List<OssPicture> select(OssPicture osspicture){
		return ossPictureDao.select(osspicture);
	}

	public void update(OssPicture osspicture){
		ossPictureDao.update(osspicture);
	}

	public void delete(OssPicture osspicture){
		ossPictureDao.delete(osspicture);
	}

	public List<OssPicture> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", NormName.normSql(param));
		 return ossPictureDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  ossPictureDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  ossPictureDao.deleteByParam(map);
	}

}

