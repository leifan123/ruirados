package com.ruirados.service.impl;

import com.ruirados.pojo.UserTypeSource;
import com.ruirados.dao.UserTypeSourceDao;
import com.ruirados.service.UserTypeSourceService;
import com.ruirados.util.NormName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserTypeSourceImpl implements UserTypeSourceService {

	private UserTypeSourceDao userTypeSourceDao;

	public void setUserTypeSourceDao(UserTypeSourceDao userTypeSourceDao){
		 this.userTypeSourceDao = userTypeSourceDao;
	}

	public void insert(UserTypeSource usertypesource){
		userTypeSourceDao.insert(usertypesource);
	}

	public List<UserTypeSource> select(UserTypeSource usertypesource){
		return userTypeSourceDao.select(usertypesource);
	}

	public void update(UserTypeSource usertypesource){
		userTypeSourceDao.update(usertypesource);
	}

	public void delete(UserTypeSource usertypesource){
		userTypeSourceDao.delete(usertypesource);
	}

	public List<UserTypeSource> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", NormName.normSql(param));
		 return userTypeSourceDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  userTypeSourceDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  userTypeSourceDao.deleteByParam(map);
	}

}

