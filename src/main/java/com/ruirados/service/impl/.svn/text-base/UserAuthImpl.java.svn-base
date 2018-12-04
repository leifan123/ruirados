package com.ruirados.service.impl;

import com.ruirados.pojo.UserAuth;
import com.ruirados.dao.UserAuthDao;
import com.ruirados.service.UserAuthService;
import com.ruirados.util.NormName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserAuthImpl implements UserAuthService {

	private UserAuthDao userAuthDao;

	public void setUserAuthDao(UserAuthDao userAuthDao){
		 this.userAuthDao = userAuthDao;
	}

	public void insert(UserAuth userauth){
		userAuthDao.insert(userauth);
	}

	public List<UserAuth> select(UserAuth userauth){
		return userAuthDao.select(userauth);
	}

	public void update(UserAuth userauth){
		userAuthDao.update(userauth);
	}

	public void delete(UserAuth userauth){
		userAuthDao.delete(userauth);
	}

	public List<UserAuth> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", NormName.normSql(param));
		 return userAuthDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  userAuthDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  userAuthDao.deleteByParam(map);
	}

}

