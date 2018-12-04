package com.ruirados.dao;



import java.util.List;

import java.util.Map;

import com.yunrui.pojo.YrAuthlog;

public interface YrAuthlogDao {

	public void insert(YrAuthlog yrauthlog);

	public List<YrAuthlog> select(YrAuthlog yrauthlog);

	public void update(YrAuthlog yrauthlog);

	public void delete(YrAuthlog yrauthlog);

	public List<YrAuthlog> selectByParam(Map<String, String> param);

	public void updateByParam(Map<String, String> params);

	public void deleteByParam(Map<String, String> params);

}

