package com.example.UserManagement.Config;

import com.example.UserManagement.Util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@CrossOrigin
@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        System.out.println("PREHANDLE");
//        System.out.println(request.getRequestURI());
//        System.out.println(!request.getRequestURI().contains("Auth"));
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);

        }

        String auth = request.getHeader("Authorization");
        System.out.println(auth);
       System.out.println(request.getRequestURI());
//        System.out.println();
        if(!(request.getRequestURI().contains("Auth"))){
            System.out.println("heloooo");
            jwtUtils.verify(auth);
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
