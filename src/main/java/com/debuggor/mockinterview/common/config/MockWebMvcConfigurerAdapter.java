package com.debuggor.mockinterview.common.config;

import com.debuggor.mockinterview.common.interceptor.AdminHandlerInterceptor;
import com.debuggor.mockinterview.common.interceptor.UserHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MockWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/admin").setViewName("admin/main");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
     /*   registry.addInterceptor(new AdminHandlerInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/login", "/admin/main", "/admin");*/
        registry.addInterceptor(new UserHandlerInterceptor())
                .addPathPatterns("/finder/toUpdate", "/finder/update", "/finder/updateHeadUrl", "/finder/judgeOldPwd", "/finder/updatePassword", "/finder/messageInterview")
                .addPathPatterns("/finder/deleteMessage/*", "/finder/deleteMessageAll/*", "/finder/posts", "/finder/deletePost/*")
                .addPathPatterns("/pay/**", "/follower/follow", "/follower/unfollow")
                .addPathPatterns("/interview/interview/*", "/interview/confirmOrder", "/interview/evaluate/*")
                .addPathPatterns("/interview/evaluateAction", "/interview/finder/*", "/interview/orderEnd")
                .addPathPatterns("/interviewer/toUpdate", "/interviewer/update", "/interviewer/updateHeadUrl", "/interviewer/judgeOldPwd", "/interviewer/updatePassword")
                .addPathPatterns("/interviewer/deleteMessageAll/*", "/interviewer/posts", "/interviewer/deletePost/*", "/interviewer/messageInterview", "/interviewer/deleteMessage/*")
                .addPathPatterns("/forum/add", "/forum/action", "/forum/updatePost/*", "/forum/update", "/forum/insertComment");
    }
}
