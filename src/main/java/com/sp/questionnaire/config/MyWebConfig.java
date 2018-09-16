package com.sp.questionnaire.config;


import com.sp.questionnaire.config.session.MySessionListener;
import org.apache.catalina.SessionListener;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/*
 * Author: Seven
 * Email : cpwu@foxmail.com
 * 2018-09-13 Thursday 14:08
 */

@Configuration
public class MyWebConfig extends WebMvcConfigurerAdapter {
    /**
     * 添加拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AdminInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
    /**
     * 允许跨域访问
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                //.allowedOrigins("http://192.168.1.100:8080")
                .allowedMethods("*")
                .maxAge(36000);
    }


    //注册session监听器;
    @Bean
    public ServletListenerRegistrationBean<MySessionListener> servletListenerRegistrationBean() {
        ServletListenerRegistrationBean<MySessionListener> slrBean = new ServletListenerRegistrationBean<>();
        slrBean.setListener(new MySessionListener());
        return slrBean;
    }


}
