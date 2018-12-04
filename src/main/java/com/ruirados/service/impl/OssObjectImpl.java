package com.ruirados.service.impl;

import com.ruirados.pojo.OssObject;
import com.ruirados.dao.OssObjectDao;
import com.ruirados.service.OssObjectService;
import com.ruirados.util.NormName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OssObjectImpl implements OssObjectService {

	private OssObjectDao ossObjectDao;

	public void setOssObjectDao(OssObjectDao ossObjectDao){
		 this.ossObjectDao = ossObjectDao;
	}

	public void insert(OssObject ossobject){
		ossObjectDao.insert(ossobject);
	}

	public List<OssObject> select(OssObject ossobject){
		return ossObjectDao.select(ossobject);
	}


	public void update(OssObject ossobject){
		ossObjectDao.update(ossobject);
	}

	public void delete(OssObject ossobject){
		ossObjectDao.delete(ossobject);
	}

	public List<OssObject> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", NormName.normSql(param));
		 return ossObjectDao.selectByParam(map);
	}
	
	public Double selectByfileName(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", param);
		 return ossObjectDao.selectByfileName(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  ossObjectDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  ossObjectDao.deleteByParam(map);
	}



}

