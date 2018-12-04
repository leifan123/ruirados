package com.ruirados.dao;

import com.ruirados.pojo.OssSyslog;

import java.util.List;

import java.util.Map;

public interface OssSyslogDao {

	public void insert(OssSyslog osssyslog);

	public List<OssSyslog> select(OssSyslog osssyslog);

	public void update(OssSyslog osssyslog);

	public void delete(OssSyslog osssyslog);

	public List<OssSyslog> selectByParam(Map<String, String> param);

	public void updateByParam(Map<String, String> params);

	public void deleteByParam(Map<String, String> params);

}

