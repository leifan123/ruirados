package com.ruirados.dao;

import com.ruirados.pojo.UserCoreAccess;

import java.util.List;

import java.util.Map;

public interface UserCoreAccessDao {

	public void insert(UserCoreAccess usercoreaccess);

	public List<UserCoreAccess> select(UserCoreAccess usercoreaccess);

	public void update(UserCoreAccess usercoreaccess);

	public void updateByCompanyId(UserCoreAccess usercoreaccess);
	
	public void delete(UserCoreAccess usercoreaccess);

	public List<UserCoreAccess> selectByParam(Map<String, String> param);

	public void updateByParam(Map<String, String> params);

	public void deleteByParam(Map<String, String> params);

	public int selectCountByparam(Map<String, String> map);

}

