package jp.gr.java_conf.star_diopside.ark.web.application.config;

import java.util.Collections;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.NoRollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import jp.gr.java_conf.star_diopside.ark.core.interceptor.CustomLoggingInterceptor;

@Configuration
public class AppConfig {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public MessageSourceAccessor messages() {
        return new MessageSourceAccessor(messageSource);
    }

    @Bean
    public Advice loggingInterceptor() {
        return new CustomLoggingInterceptor();
    }

    @Bean
    public Advisor loggingAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* jp.gr.java_conf.star_diopside.ark..*(..))");
        return new DefaultPointcutAdvisor(pointcut, loggingInterceptor());
    }

    @Bean
    public TransactionInterceptor authenticateTransactionInterceptor() {
        MatchAlwaysTransactionAttributeSource tas = new MatchAlwaysTransactionAttributeSource();
        RuleBasedTransactionAttribute ta = new RuleBasedTransactionAttribute();
        ta.setName("*");
        ta.setRollbackRules(Collections.singletonList(new NoRollbackRuleAttribute(AuthenticationException.class)));
        tas.setTransactionAttribute(ta);
        return new TransactionInterceptor(transactionManager, tas);
    }

    @Bean
    public Advisor authenticateTransactionAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("target(" + DaoAuthenticationProvider.class.getName() + ") && execution(* "
                + AuthenticationProvider.class.getName() + ".authenticate(" + Authentication.class.getName() + "))");
        return new DefaultPointcutAdvisor(pointcut, authenticateTransactionInterceptor());
    }
}
