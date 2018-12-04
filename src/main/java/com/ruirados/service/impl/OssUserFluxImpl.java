package com.ruirados.service.impl;

import com.ruirados.pojo.OssUserFlux;
import com.ruirados.dao.OssUserFluxDao;
import com.ruirados.service.OssUserFluxService;
import com.ruirados.util.NormName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OssUserFluxImpl implements OssUserFluxService {

	private OssUserFluxDao ossUserFluxDao;

	public void setOssUserFluxDao(OssUserFluxDao ossUserFluxDao){
		 this.ossUserFluxDao = ossUserFluxDao;
	}

	public void insert(OssUserFlux ossuserflux){
		ossUserFluxDao.insert(ossuserflux);
	}

	public List<OssUserFlux> select(OssUserFlux ossuserflux){
		return ossUserFluxDao.select(ossuserflux);
	}

	public void update(OssUserFlux ossuserflux){
		ossUserFluxDao.update(ossuserflux);
	}

	public void delete(OssUserFlux ossuserflux){
		ossUserFluxDao.delete(ossuserflux);
	}

	public List<OssUserFlux> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", param);
		 return ossUserFluxDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", param);
		  ossUserFluxDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", param);
		  ossUserFluxDao.deleteByParam(map);
	}

}

