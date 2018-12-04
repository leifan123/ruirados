package com.ruirados.service;

import com.ruirados.pojo.Test1;

import java.util.List;

public interface Test1Service {

	public void insert(Test1 test1);

	public List<Test1> select(Test1 test1);

	public void update(Test1 test1);

	public void delete(Test1 test1);

	public List<Test1> selectByParam(String field,String param);

	public void updateByParam(String param);

	public void deleteByParam(String param);

}

