package com.ruirados.service.impl;

import com.ruirados.pojo.OssFlux;
import com.ruirados.dao.OssFluxDao;
import com.ruirados.service.OssFluxService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OssFluxImpl implements OssFluxService {

	private OssFluxDao ossFluxDao;

	public void setOssFluxDao(OssFluxDao ossFluxDao){
		 this.ossFluxDao = ossFluxDao;
	}

	public void insert(OssFlux ossflux){
		ossFluxDao.insert(ossflux);
	}

	public List<OssFlux> select(OssFlux ossflux){
		return ossFluxDao.select(ossflux);
	}

	public void update(OssFlux ossflux){
		ossFluxDao.update(ossflux);
	}

	public void delete(OssFlux ossflux){
		ossFluxDao.delete(ossflux);
	}

	public List<OssFlux> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", param);
		 return ossFluxDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", param);
		  ossFluxDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", param);
		  ossFluxDao.deleteByParam(map);
	}

}

