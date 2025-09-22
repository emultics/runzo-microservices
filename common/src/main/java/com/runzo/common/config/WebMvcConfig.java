package com.runzo.common.config;

import com.runzo.common.exceptions.handler.GlobalExceptionHandler;
import com.runzo.common.logger.CorrelationIdFilter;
import com.runzo.common.logger.LoggingInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@ConditionalOnWebApplication
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public LoggingInterceptor loggingInterceptor() {
        return new LoggingInterceptor();
    }

    @Bean
    public GlobalExceptionHandler globalExceptionHandler(){
        return new GlobalExceptionHandler();
    }

    @Bean
    @ConditionalOnMissingBean(name = "correlationIdFilter")
    public FilterRegistrationBean<CorrelationIdFilter> correlationIdFilter() {
        FilterRegistrationBean<CorrelationIdFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CorrelationIdFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1); // Must run before everything else
        return registrationBean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor())
                .addPathPatterns("/**");
    }
}