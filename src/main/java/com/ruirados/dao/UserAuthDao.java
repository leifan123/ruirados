package com.ruirados.dao;

import com.ruirados.pojo.UserAuth;

import java.util.List;

import java.util.Map;

public interface UserAuthDao {

	public void insert(UserAuth userauth);

	public List<UserAuth> select(UserAuth userauth);

	public void update(UserAuth userauth);

	public void delete(UserAuth userauth);

	public List<UserAuth> selectByParam(Map<String, String> param);

	public void updateByParam(Map<String, String> params);

	public void deleteByParam(Map<String, String> params);

}

