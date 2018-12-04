package com.ruirados.service;

import com.ruirados.pojo.UserTypeSource;

import java.util.List;

public interface UserTypeSourceService {

	public void insert(UserTypeSource usertypesource);

	public List<UserTypeSource> select(UserTypeSource usertypesource);

	public void update(UserTypeSource usertypesource);

	public void delete(UserTypeSource usertypesource);

	public List<UserTypeSource> selectByParam(String field,String param);

	public void updateByParam(String param);

	public void deleteByParam(String param);

}

