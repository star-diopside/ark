package jp.gr.java_conf.stardiopside.ark.web.config;

import java.util.Collections;

import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.NoRollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.google.code.kaptcha.servlet.KaptchaServlet;

import jp.gr.java_conf.stardiopside.ark.web.servlet.LoggingConfigFilter;

@Configuration
public class ServletConfig {

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public FilterRegistrationBean<LoggingConfigFilter> loggingConfigFilter(SecurityProperties properties) {
        FilterRegistrationBean<LoggingConfigFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new LoggingConfigFilter());
        bean.setOrder(properties.getFilter().getOrder() - 1);
        return bean;
    }

    @Bean
    public ServletRegistrationBean<KaptchaServlet> kaptchaServlet() {
        return new ServletRegistrationBean<>(new KaptchaServlet(), "/captcha.jpg");
    }

    @Bean
    public MethodInterceptor authenticateTransactionInterceptor() {
        MatchAlwaysTransactionAttributeSource tas = new MatchAlwaysTransactionAttributeSource();
        RuleBasedTransactionAttribute ta = new RuleBasedTransactionAttribute();
        ta.setName("*");
        ta.setRollbackRules(Collections.singletonList(new NoRollbackRuleAttribute(AuthenticationException.class)));
        tas.setTransactionAttribute(ta);
        return new TransactionInterceptor(transactionManager, tas);
    }

    @Bean
    public PointcutAdvisor authenticateTransactionAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("target(" + DaoAuthenticationProvider.class.getName() + ") && execution(* "
                + AuthenticationProvider.class.getName() + ".authenticate(" + Authentication.class.getName() + "))");
        return new DefaultPointcutAdvisor(pointcut, authenticateTransactionInterceptor());
    }
}
