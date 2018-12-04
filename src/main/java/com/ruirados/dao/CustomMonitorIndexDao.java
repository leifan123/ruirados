package com.ruirados.dao;

import com.ruirados.pojo.CustomMonitorIndex;

import java.util.List;

import java.util.Map;

public interface CustomMonitorIndexDao {

	public void insert(CustomMonitorIndex custommonitorindex);

	public List<CustomMonitorIndex> select(CustomMonitorIndex custommonitorindex);

	public void update(CustomMonitorIndex custommonitorindex);

	public void delete(CustomMonitorIndex custommonitorindex);

	public List<CustomMonitorIndex> selectByParam(Map<String, String> param);

	public void updateByParam(Map<String, String> params);

	public void deleteByParam(Map<String, String> params);

}

