package com.ruirados.service.impl;

import com.ruirados.pojo.OssPrice;
import com.ruirados.dao.OssPriceDao;
import com.ruirados.service.OssPriceService;
import com.ruirados.util.NormName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OssPriceImpl implements OssPriceService {

	private OssPriceDao ossPriceDao;

	public void setOssPriceDao(OssPriceDao ossPriceDao){
		 this.ossPriceDao = ossPriceDao;
	}

	public void insert(OssPrice ossprice){
		ossPriceDao.insert(ossprice);
	}

	public List<OssPrice> select(OssPrice ossprice){
		return ossPriceDao.select(ossprice);
	}

	public void update(OssPrice ossprice){
		ossPriceDao.update(ossprice);
	}

	public void delete(OssPrice ossprice){
		ossPriceDao.delete(ossprice);
	}

	public List<OssPrice> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", NormName.normSql(param));
		 return ossPriceDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  ossPriceDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  ossPriceDao.deleteByParam(map);
	}

}

