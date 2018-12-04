package com.ruirados.dao;

import com.ruirados.pojo.UserAccess;

import java.util.List;

import java.util.Map;

public interface UserAccessDao {

	public void insert(UserAccess useraccess);

	public List<UserAccess> select(UserAccess useraccess);

	public void update(UserAccess useraccess);

	public void delete(UserAccess useraccess);

	public List<UserAccess> selectByParam(Map<String, String> param);

	public void updateByParam(Map<String, String> params);

	public void deleteByParam(Map<String, String> params);
	
	public Integer countByParam(Map<String, String> params);

}

