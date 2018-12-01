package com.baomidou.springboot.config.jwt;


import com.baomidou.springboot.response.ResponseMessage;
import com.baomidou.springboot.util.JsonUtil;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
* @Description:    jwt过滤器
* @Author:         LiHaitao
* @CreateDate:     2018/8/25 21:47
* @UpdateUser:
* @UpdateDate:
* @UpdateRemark:
* @Version:        1.0.0
*/
public class JwtFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		if("OPTIONS".equals(request.getMethod())){
			chain.doFilter(request, response);
			return;
		}
		String auth = request.getHeader("token");
		if ((auth != null) && (auth.length() > 7)) {
			String HeadStr = auth.substring(0, 6).toLowerCase();
			if (HeadStr.compareTo("bearer") == 0) {
				auth = auth.substring(7, auth.length());
				System.out.println(auth);
				if (JwtHelper.parseJWT(auth) != null) {
					chain.doFilter(request, response);
					return;
				}
			}
		}
		response.setCharacterEncoding("UTF-8");    
		response.setContentType("application/json; charset=utf-8");   
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		/**
		 * 这里要设置转换的对象信息
		 **/
		response.getWriter().write(JsonUtil.getJsonFromObject(new ResponseMessage<>()));
        return;  
	}

}
