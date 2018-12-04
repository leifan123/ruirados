package com.ruirados.dao;

import com.yunrui.pojo.Getfreevminformation;

import java.util.List;

import java.util.Map;

public interface GetfreevminformationDao {

	public void insert(Getfreevminformation getfreevminformation);

	public List<Getfreevminformation> select(Getfreevminformation getfreevminformation);

	public void update(Getfreevminformation getfreevminformation);

	public void delete(Getfreevminformation getfreevminformation);

	public List<Getfreevminformation> selectByParam(Map<String, String> param);

	public void updateByParam(Map<String, String> params);

	public void deleteByParam(Map<String, String> params);
	
	public List<Getfreevminformation> selectBySql(Map<String, String> param);
}

