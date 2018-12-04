package com.ruirados.service.impl;

import com.ruirados.pojo.OssStatistics;
import com.ruirados.dao.OssStatisticsDao;
import com.ruirados.service.OssStatisticsService;
import com.ruirados.util.NormName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OssStatisticsImpl implements OssStatisticsService {

	private OssStatisticsDao ossStatisticsDao;

	public void setOssStatisticsDao(OssStatisticsDao ossStatisticsDao){
		 this.ossStatisticsDao = ossStatisticsDao;
	}

	public void insert(OssStatistics ossstatistics){
		ossStatisticsDao.insert(ossstatistics);
	}

	public List<OssStatistics> select(OssStatistics ossstatistics){
		return ossStatisticsDao.select(ossstatistics);
	}

	public void update(OssStatistics ossstatistics){
		ossStatisticsDao.update(ossstatistics);
	}

	public void delete(OssStatistics ossstatistics){
		ossStatisticsDao.delete(ossstatistics);
	}

	public List<OssStatistics> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", param);
		 return ossStatisticsDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", param);
		  ossStatisticsDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", param);
		  ossStatisticsDao.deleteByParam(map);
	}

	public List<String> selectByCount(String field, String param, String order) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", param);
		map.put("order", order);
		return ossStatisticsDao.selectByCount(map);
	}

	public List<Integer> selectByHour(String field, String param, String order) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", param);
		map.put("order", order);
		return ossStatisticsDao.selectByHour(map);
	}

}

