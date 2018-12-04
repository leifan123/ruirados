package com.ruirados.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 

public class WebUtils {
	private final static String ContentType_JSON = "application/json";

	public static void outputJson(String content, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType(ContentType_JSON);
		output(content, request, response);
	}
	public  static void output(String content, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			byte[] result = null;
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");
			if (request.getHeader("Accept-Encoding") != null && request.getHeader("Accept-Encoding").toLowerCase()
					.contains("gzip")) {
				GZIPOutputStream gout = null;
				try {
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					gout = new GZIPOutputStream(out);
					gout.write(content.getBytes("UTF-8"));
					gout.close();
					result = out.toByteArray();
					response.setHeader("Content-Encoding", "gzip");
					response.setContentLength(result.length);
				} catch (IOException ex) {
					Logger.getLogger(WebUtils.class.getName()).log(
							Level.SEVERE, null, ex);
				} finally {
					try {
						if (gout != null) {
							gout.close();
						}
					} catch (IOException ex) {
						Logger.getLogger(WebUtils.class.getName()).log(
								Level.SEVERE, null, ex);
					}
				}
				response.getOutputStream().write(result);
			} else {
				response.setCharacterEncoding("UTF-8");
				OutputStream out = response.getOutputStream();
				response.setContentLength(content.getBytes("UTF-8").length);
				out.write(content.getBytes("UTF-8"));
				out.flush();
			}

		} catch (IOException ex) {
			Logger.getLogger(WebUtils.class.getName()).log(Level.SEVERE, null,
					ex);
		}
	}
	
 
}
