package com.ruirados.filter;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ruirados.exception.ExcepEnum;
import com.ruirados.util.ApiTool;
import com.ruirados.util.BodyReaderHttpServletRequestWrapper;
import com.ruirados.util.DesUtils;
import com.ruirados.util.FcUtil;
import com.ruirados.util.GlobalAttr;
import com.ruirados.util.JSONUtils;
import com.ruirados.util.WebUtils;

@Component
public class ApiPermissionControlFilter implements Filter {

	
	private Logger log = Logger.getLogger(UserPermissionControlFilter.class);
  
	public void init(FilterConfig arg0) throws ServletException {
	 
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		String servletPath = httpServletRequest.getServletPath();
		String urlStr;
  	
		String queryString = httpServletRequest.getQueryString();
		if (queryString != null && !queryString.isEmpty()) {
			urlStr = servletPath + "?" + queryString;
		} else {
			urlStr = servletPath;
		}
		log.debug("requst---->"+urlStr);
		Map<String, String> paramMap = null;
 
		/*httpServletResponse.setContentType("textml;charset=UTF-8");
		httpServletResponse.setHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("Origin"));
		httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		httpServletResponse.setHeader("Access-Control-Max-Age", "0");
		httpServletResponse.setHeader("Access-Control-Allow-Headers",
				"Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");
		httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
		httpServletResponse.setHeader("XDomainRequestAllowed", "1");*/
		try {
					if(urlStr.startsWith("/user/getToken.json") || urlStr.startsWith("/user/login.json") || urlStr.startsWith("/flux/addCapati.json") 
							|| urlStr.startsWith("/system/synUserAccess.json")){
							
						HttpServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(httpServletRequest);
						try{
							paramMap =  ApiTool.getBodyString(requestWrapper);
						}	catch (Exception e) {
							
						}
						request = requestWrapper;
						
						chain.doFilter(request, response);
						return;
						
					}else{
						
						String token = "";
						
						if ("POST".equalsIgnoreCase(httpServletRequest.getMethod())) {
 
							HttpServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(httpServletRequest);
				 
							
							paramMap =  ApiTool.getBodyString(requestWrapper);
							
							token = paramMap.get("token");

							// 必须加上
							request = requestWrapper;

						} else if ("GET".equalsIgnoreCase(httpServletRequest.getMethod())) {
							 token = httpServletRequest.getParameter("token");
						}
						
 
						//校验密钥是否为空
						if(FcUtil.checkIsNull(token)){
							
							Map<String, Object> result_map = FcUtil.getResult(ExcepEnum.ERROR_SY_ISNOTNULL.VAL);
							WebUtils.outputJson(JSONUtils.createObjectJson(result_map), httpServletRequest, httpServletResponse);
							return;
						}
						
						//校验secret合法性
						String  token_code =""; 
						try {
							token_code = DesUtils.getInstance().decrypt(token);
							
							String[] secretArr = token_code.split("#");
							
							//判断是否超时
							if( (new Date().getTime()/1000)  -  Long.valueOf(secretArr[0]) > 2 * 3600 ){
								
								Map<String, Object> result_map = FcUtil.getResult(ExcepEnum.ERROR_CM_MYCS.VAL);
								WebUtils.outputJson(JSONUtils.createObjectJson(result_map), httpServletRequest, httpServletResponse);
								return;
							}
						 
							Map<String, String> userTokenMap = GlobalAttr.getInstance().getUserTokenMap();
							if(!token.equals(userTokenMap.get(secretArr[1]))){
								Map<String, Object> result_map = FcUtil.getResult(ExcepEnum.ERROR_CM_MYSB.VAL);
								WebUtils.outputJson(JSONUtils.createObjectJson(result_map), httpServletRequest, httpServletResponse);
								return;
							}
						 
						} catch (Exception e) {
							log.error(e);
							// TODO: handle exception
							e.printStackTrace();
							Map<String, Object> result_map = FcUtil.getResult(ExcepEnum.ERROR_CM_MYSB.VAL);
							WebUtils.outputJson(JSONUtils.createObjectJson(result_map), httpServletRequest, httpServletResponse);
							return;
						}
						
				 
					chain.doFilter(request, response);
					return;
						  
		 }
			   
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e);
			e.printStackTrace();
			Map<String, Object> result_map = FcUtil.getResult(ExcepEnum.ERROR_CM_MYSB.VAL);
			WebUtils.outputJson(JSONUtils.createObjectJson(result_map), httpServletRequest, httpServletResponse);
			return;
			
		}
	}

	public void destroy() {

	}
}
