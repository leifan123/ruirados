package com.ruirados.utils;

public class NormName {

	public static String normSql(String sql){

		if(sql == null || sql.isEmpty()){

			return null;

		}

		String sqlTemp = sql.toLowerCase();

		int n = sqlTemp.indexOf("set");

		if(n != -1){

			return sql.substring(n+3, sql.length());

		}

		int i = sqlTemp.indexOf("where");

		if(i != -1){

			sql = sql.substring(i+5, sql.length());

		}

		return sql;

	}

}

