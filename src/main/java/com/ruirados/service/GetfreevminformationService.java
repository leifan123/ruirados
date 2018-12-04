package com.ruirados.service;

import com.yunrui.pojo.Getfreevminformation;

import java.util.List;

public interface GetfreevminformationService {

	public void insert(Getfreevminformation getfreevminformation);

	public List<Getfreevminformation> select(Getfreevminformation getfreevminformation);

	public void update(Getfreevminformation getfreevminformation);

	public void delete(Getfreevminformation getfreevminformation);

	public List<Getfreevminformation> selectByParam(String field,String param);

	public void updateByParam(String param);

	public void deleteByParam(String param);
	public List<Getfreevminformation> selectBySql(String sql);
}

