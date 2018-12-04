package com.ruirados.service.impl;

import com.ruirados.pojo.OssZone;
import com.ruirados.dao.OssZoneDao;
import com.ruirados.service.OssZoneService;
import com.ruirados.util.NormName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OssZoneImpl implements OssZoneService {

	private OssZoneDao ossZoneDao;

	public void setOssZoneDao(OssZoneDao ossZoneDao){
		 this.ossZoneDao = ossZoneDao;
	}

	public void insert(OssZone osszone){
		ossZoneDao.insert(osszone);
	}

	public List<OssZone> select(OssZone osszone){
		return ossZoneDao.select(osszone);
	}

	public void update(OssZone osszone){
		ossZoneDao.update(osszone);
	}

	public void delete(OssZone osszone){
		ossZoneDao.delete(osszone);
	}

	public List<OssZone> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", NormName.normSql(param));
		 return ossZoneDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  ossZoneDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  ossZoneDao.deleteByParam(map);
	}

}

