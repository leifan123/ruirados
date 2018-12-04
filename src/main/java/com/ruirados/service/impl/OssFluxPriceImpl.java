package com.ruirados.service.impl;

import com.ruirados.pojo.OssFluxPrice;
import com.ruirados.dao.OssFluxPriceDao;
import com.ruirados.service.OssFluxPriceService;
import com.ruirados.util.NormName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OssFluxPriceImpl implements OssFluxPriceService {

	private OssFluxPriceDao ossFluxPriceDao;

	public void setOssFluxPriceDao(OssFluxPriceDao ossFluxPriceDao){
		 this.ossFluxPriceDao = ossFluxPriceDao;
	}

	public void insert(OssFluxPrice ossfluxprice){
		ossFluxPriceDao.insert(ossfluxprice);
	}

	public List<OssFluxPrice> select(OssFluxPrice ossfluxprice){
		return ossFluxPriceDao.select(ossfluxprice);
	}

	public void update(OssFluxPrice ossfluxprice){
		ossFluxPriceDao.update(ossfluxprice);
	}

	public void delete(OssFluxPrice ossfluxprice){
		ossFluxPriceDao.delete(ossfluxprice);
	}

	public List<OssFluxPrice> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", NormName.normSql(param));
		 return ossFluxPriceDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  ossFluxPriceDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  ossFluxPriceDao.deleteByParam(map);
	}

}

