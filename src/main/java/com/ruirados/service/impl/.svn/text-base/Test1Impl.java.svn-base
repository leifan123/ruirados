package com.ruirados.service.impl;

import com.ruirados.pojo.Test1;
import com.ruirados.dao.Test1Dao;
import com.ruirados.service.Test1Service;
import com.ruirados.util.NormName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test1Impl implements Test1Service {

	private Test1Dao test1Dao;

	public void setTest1Dao(Test1Dao test1Dao){
		 this.test1Dao = test1Dao;
	}

	public void insert(Test1 test1){
		test1Dao.insert(test1);
	}

	public List<Test1> select(Test1 test1){
		return test1Dao.select(test1);
	}

	public void update(Test1 test1){
		test1Dao.update(test1);
	}

	public void delete(Test1 test1){
		test1Dao.delete(test1);
	}

	public List<Test1> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", NormName.normSql(param));
		 return test1Dao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  test1Dao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  test1Dao.deleteByParam(map);
	}

}

