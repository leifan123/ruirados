package com.ruirados.dao;

import com.ruirados.pojo.UserTypeSource;

import java.util.List;

import java.util.Map;

public interface UserTypeSourceDao {

	public void insert(UserTypeSource usertypesource);

	public List<UserTypeSource> select(UserTypeSource usertypesource);

	public void update(UserTypeSource usertypesource);

	public void delete(UserTypeSource usertypesource);

	public List<UserTypeSource> selectByParam(Map<String, String> param);

	public void updateByParam(Map<String, String> params);

	public void deleteByParam(Map<String, String> params);

}

