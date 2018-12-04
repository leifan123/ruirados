package com.ruirados.service;

import com.ruirados.pojo.OssStatistics;

import java.util.List;

public interface OssStatisticsService {

	public void insert(OssStatistics ossstatistics);

	public List<OssStatistics> select(OssStatistics ossstatistics);

	public void update(OssStatistics ossstatistics);

	public void delete(OssStatistics ossstatistics);

	public List<OssStatistics> selectByParam(String field,String param);

	public void updateByParam(String param);

	public void deleteByParam(String param);
	
	public List<String> selectByCount(String field,String param, String order);
	
	public List<Integer> selectByHour(String field,String param, String order);
}

