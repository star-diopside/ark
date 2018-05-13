package jp.gr.java_conf.stardiopside.ark.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jp.gr.java_conf.stardiopside.ark.service.UserService;
import jp.gr.java_conf.stardiopside.ark.web.interceptor.DualLoginCheckInterceptor;
import jp.gr.java_conf.stardiopside.ark.web.interceptor.KaptchaSessionClearInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(dualLoginCheckInterceptor()).excludePathPatterns("/webjars/**", "/static/**",
                "/authentication/**", "/anonymous/**");
        registry.addInterceptor(kaptchaSessionClearInterceptor()).addPathPatterns("/authentication/**", "/home/**");
    }

    @Override
    public Validator getValidator() {
        return validator();
    }

    @Bean
    public HandlerInterceptor dualLoginCheckInterceptor() {
        return new DualLoginCheckInterceptor(userService);
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
