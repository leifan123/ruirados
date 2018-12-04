package com.ruirados.dao;

import com.ruirados.pojo.Test1;

import java.util.List;

import java.util.Map;

public interface Test1Dao {

	public void insert(Test1 test1);

	public List<Test1> select(Test1 test1);

	public void update(Test1 test1);

	public void delete(Test1 test1);

	public List<Test1> selectByParam(Map<String, String> param);

	public void updateByParam(Map<String, String> params);

	public void deleteByParam(Map<String, String> params);

}

