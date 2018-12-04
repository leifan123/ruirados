package com.ruirados.service.impl;

import com.ruirados.pojo.OssActivity;
import com.ruirados.dao.OssActivityDao;
import com.ruirados.service.OssActivityService;
import com.ruirados.util.NormName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OssActivityImpl implements OssActivityService {

	private OssActivityDao ossActivityDao;

	public void setOssActivityDao(OssActivityDao ossActivityDao){
		 this.ossActivityDao = ossActivityDao;
	}

	public void insert(OssActivity ossactivity){
		ossActivityDao.insert(ossactivity);
	}

	public List<OssActivity> select(OssActivity ossactivity){
		return ossActivityDao.select(ossactivity);
	}

	public void update(OssActivity ossactivity){
		ossActivityDao.update(ossactivity);
	}

	public void delete(OssActivity ossactivity){
		ossActivityDao.delete(ossactivity);
	}

	public List<OssActivity> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", NormName.normSql(param));
		 return ossActivityDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  ossActivityDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  ossActivityDao.deleteByParam(map);
	}

}

