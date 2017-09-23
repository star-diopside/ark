package jp.gr.java_conf.star_diopside.ark.web.application.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.code.kaptcha.servlet.KaptchaServlet;

import jp.gr.java_conf.star_diopside.ark.web.servlet.LoggingConfigFilter;

@Configuration
public class ServletConfig {

    @Bean
    public FilterRegistrationBean loggingConfigFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new LoggingConfigFilter());
        bean.setOrder(1);
        return bean;
    }

    @Bean
    public ServletRegistrationBean kaptchaServlet() {
        return new ServletRegistrationBean(new KaptchaServlet(), "/captcha.jpg");
    }
}
