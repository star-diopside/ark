package jp.gr.java_conf.star_diopside.ark.web.application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import jp.gr.java_conf.star_diopside.ark.web.interceptor.DualLoginCheckInterceptor;
import jp.gr.java_conf.star_diopside.ark.web.interceptor.KaptchaSessionClearInterceptor;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private MessageSource messageSource;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(dualLoginCheckInterceptor()).excludePathPatterns("/webjars/**", "/static/**",
                "/authentication/**", "/anonymous/**");
        registry.addInterceptor(kaptchaSessionClearInterceptor()).excludePathPatterns("/webjars/**", "/static/**");
    }

    @Override
    public Validator getValidator() {
        return validator();
    }

    @Bean
    public HandlerInterceptor dualLoginCheckInterceptor() {
        return new DualLoginCheckInterceptor();
    }

    @Bean
    public HandlerInterceptor kaptchaSessionClearInterceptor() {
        return new KaptchaSessionClearInterceptor();
    }

    @Bean
    public Validator validator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource);
        return validator;
    }
}
