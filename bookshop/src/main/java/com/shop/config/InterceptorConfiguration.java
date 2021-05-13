package com.shop.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;
//
//@Configuration
//public class InterceptorConfiguration implements WebMvcConfigurer {
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        HandlerInterceptor interceptor = new UserLoginInterceptor();
//
//        List<String> patterns = new ArrayList<>();
//        patterns.add("/bootstrap3/**");
//        patterns.add("/css/**");
//        patterns.add("/js/**");
//        patterns.add("/images/**");
//        patterns.add("/web/register.html");
//        patterns.add("/web/login.html");
//        patterns.add("/users/reg");
//        patterns.add("/users/login");
//
//        registry.addInterceptor(interceptor)
//                .addPathPatterns("/**")
//                .excludePathPatterns(patterns);
//    }
//
//}
