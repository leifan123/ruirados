package com.ruirados.service.impl;

import com.ruirados.pojo.UserCoreAccess;
import com.ruirados.dao.UserCoreAccessDao;
import com.ruirados.service.UserCoreAccessService;
import com.ruirados.util.NormName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserCoreAccessImpl implements UserCoreAccessService {

	private UserCoreAccessDao userCoreAccessDao;

	public void setUserCoreAccessDao(UserCoreAccessDao userCoreAccessDao){
		 this.userCoreAccessDao = userCoreAccessDao;
	}

	public void insert(UserCoreAccess usercoreaccess){
		userCoreAccessDao.insert(usercoreaccess);
	}

	public List<UserCoreAccess> select(UserCoreAccess usercoreaccess){
		return userCoreAccessDao.select(usercoreaccess);
	}

	public void update(UserCoreAccess usercoreaccess){
		userCoreAccessDao.update(usercoreaccess);
	}
	
	public void updateByCompanyId(UserCoreAccess usercoreaccess){
		userCoreAccessDao.updateByCompanyId(usercoreaccess);
	}

	public void delete(UserCoreAccess usercoreaccess){
		userCoreAccessDao.delete(usercoreaccess);
	}

	public List<UserCoreAccess> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", NormName.normSql(param));
		 return userCoreAccessDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", param);
		  userCoreAccessDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  userCoreAccessDao.deleteByParam(map);
	}
	
	public int selectCountByparam(String field,String param){	
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", NormName.normSql(param));
		 return userCoreAccessDao.selectCountByparam(map);
	};

	

}

