package com.baomidou.springboot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
* @Description:    Shiro工具类
* @Author:         LiHaitao
* @CreateDate:     2018/8/25 20:48
* @UpdateUser:
* @UpdateDate:
* @UpdateRemark:
* @Version:        1.0.0
*/
public class ShiroUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(ShiroUtils.class);
	
	
	/**
	 * 是否是Ajax请求
	 * @param request
	 * @return
	 */
	public static boolean isAjax(ServletRequest request){
		return "XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"));
	}
	
	/**
	 * 获得用户远程地址
	 */
	public static String getRemoteAddr(HttpServletRequest request) {
		String remoteAddr = request.getHeader("X-Real-IP");
		if (remoteAddr.equals("")||null==remoteAddr) {
			remoteAddr = request.getHeader("X-Forwarded-For");
		}
		return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
	}
	
	/**
	 * response 输出JSON
	 * @param response
	 * @param resultMap
	 * @throws IOException
	 */
	public static void writeJson(ServletResponse response, Map<String, Object> resultMap){
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			out.print(JsonUtil.getJsonFromObject(resultMap));
		} catch (Exception e) {
			logger.error("输出JSON异常:", e);
		}finally{
			if(null != out){
				out.flush();
				out.close();
			}
		}
	}
}
