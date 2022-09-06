package com.fastcampus.MyWeb1.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle
            (HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        HttpSession session = request.getSession(false);
        if(session==null || session.getAttribute("id")==null){
            UriComponentsBuilder temp = UriComponentsBuilder.newInstance();
            temp.queryParam("toURL",request.getServletPath());
            if(request.getQueryString()!=null){
                temp.queryParam(request.getQueryString());
            }

            response.sendRedirect(request.getContextPath()+"/login"+temp.build());
            return false;
        }

        return true;
    }
}
