package com.baomidou.springboot.config.shiro;

import com.baomidou.springboot.auth.entity.User;
import com.baomidou.springboot.util.ShiroUtils;
import lombok.Data;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
/**
* @Description:    session控制过滤器
* @Author:         LiHaitao
* @CreateDate:     2018/8/25 20:56
* @UpdateUser:
* @UpdateDate:
* @UpdateRemark:
* @Version:        1.0.0
*/
@Data
public class KickoutSessionControlFilter extends AccessControlFilter {

	private String kickoutUrl; //踢出后到的地址
    private boolean kickoutAfter = false; //踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
    private int maxSession = 1; //同一个帐号最大会话数 默认1

    private SessionManager sessionManager;
    private Cache<String, Deque<Serializable>> cache;



    public void setSessionManager(SessionManager sessionManager){
        this.sessionManager = sessionManager;
    }
    //设置Cache的key的前缀
    public void setCacheManager(CacheManager cacheManager){
        this.cache = cacheManager.getCache("shiro-kickout-session");
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)throws Exception{
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response)throws Exception{
        Subject subject = getSubject(request, response);
        if(!subject.isAuthenticated() && !subject.isRemembered()) {
            //如果没有登录，直接进行之后的流程
            return true;
        }
        Session session = subject.getSession();
        User user = (User) subject.getPrincipal();
        String username = user.getName();
        Serializable sessionId = session.getId();
        //读取缓存 没有就存入
        Deque<Serializable> deque = cache.get(username);
        
        if(deque == null) {
            deque = new LinkedList<Serializable>();
            cache.put(username, deque);
        }
        
        //如果队列里没有此sessionId，且用户没有被踢出；放入队列
        if(!deque.contains(sessionId) && session.getAttribute("kickout") == null) {
            //将sessionId存入队列
        	deque.push(sessionId);
        }

        //如果队列里的sessionId数超出最大会话数，开始踢人
        while(deque.size() > maxSession) {
            Serializable kickoutSessionId = null;
            if(kickoutAfter) { //如果踢出后者
                kickoutSessionId = deque.removeFirst();
            } else { //否则踢出前者
                kickoutSessionId = deque.removeLast();
            }
            try {
            	//获取被踢出的sessionId的session对象
                Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
                if(kickoutSession != null) {
                    //设置会话的kickout属性表示踢出了
                    kickoutSession.setAttribute("kickout", true);
                }
            } catch (Exception e) {//ignore exception
            }
        }

        //如果被踢出了，直接退出，重定向到踢出后的地址
        if ((Boolean)session.getAttribute("kickout")!=null) {
            //会话被踢出了
            try {
            	//退出登录
                subject.logout();
            } catch (Exception e) { //ignore
            }
            Map<String, Object> result = new HashMap<String, Object>();
			//判断是不是Ajax请求
			if (ShiroUtils.isAjax(request) ) {
				result.put("status", "403");
				result.put("message", "您已经被踢出，请重新登录！");
				ShiroUtils.writeJson(response, result);
			}else
			{
				WebUtils.getSavedRequest(request);
				//再重定向到登录页面
				WebUtils.issueRedirect(request, response, kickoutUrl);
			}
            return false;
        }
        return true;
    }
}

