package com.ruirados.util;

import java.util.Map;

public class QueryConfig {
	/**   
	 * <p>Title: QueryConfig.java  </p>
	 * <p>Description: 缓存查询结果集 </p>
	 * <p>Company: </p>
	 * @author yr004   
	 * @date 2018年4月12日 上午9:51:57    
	 */
	private Map<String, String> queryConfig;  
    private static QueryConfig instance = null;  
  
    public static synchronized QueryConfig getInstance() {  
        if(instance == null) {  
            instance = new QueryConfig();  
        }  
        return instance;  
    }  
  
    //清楚对象中缓存的内容  
    public void clearQueryConfig() {  
        queryConfig = null;  
    }  
  
    public Map<String, String> getQueryConfig() {  
        return queryConfig;  
    }  
  
    public void setQueryConfig(Map<String, String> queryConfig) {  
        this.queryConfig = queryConfig;  
    }  
}
