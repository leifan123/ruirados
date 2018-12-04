package com.ruirados.service.impl;

import com.ruirados.pojo.UserAccess;
import com.ruirados.dao.UserAccessDao;
import com.ruirados.service.UserAccessService;
import com.ruirados.util.NormName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserAccessImpl implements UserAccessService {

	private UserAccessDao userAccessDao;

	public void setUserAccessDao(UserAccessDao userAccessDao){
		 this.userAccessDao = userAccessDao;
	}

	public void insert(UserAccess useraccess){
		userAccessDao.insert(useraccess);
	}

	public List<UserAccess> select(UserAccess useraccess){
		return userAccessDao.select(useraccess);
	}

	public void update(UserAccess useraccess){
		userAccessDao.update(useraccess);
	}

	public void delete(UserAccess useraccess){
		userAccessDao.delete(useraccess);
	}

	public List<UserAccess> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", NormName.normSql(param));
		 return userAccessDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", param);
		  userAccessDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", param);
		  userAccessDao.deleteByParam(map);
	}

	/** 
	 * @param param
	 * @return
	 * @see com.ruirados.service.UserAccessService#countByParam(java.lang.String)
	 */
	public Integer countByParam(String param) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", param);
		return userAccessDao.countByParam(map);
	}

}

