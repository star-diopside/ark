package jp.gr.java_conf.stardiopside.ark.core.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Jsr330ScopeMetadataResolver;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import jp.gr.java_conf.stardiopside.ark.core.interceptor.CustomLoggingInterceptor;

@Configuration
@ComponentScan(basePackages = "jp.gr.java_conf.stardiopside.ark", scopeResolver = Jsr330ScopeMetadataResolver.class, excludeFilters = {
        @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
        @Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
public class AppConfig {

    @Bean
    public MessageSourceAccessor messages(MessageSource messageSource) {
        return new MessageSourceAccessor(messageSource);
    }

    @Bean
    public MethodInterceptor loggingInterceptor() {
        return new CustomLoggingInterceptor();
    }

    @Bean
    public PointcutAdvisor loggingAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("(execution(* jp.gr.java_conf.stardiopside.ark..*(..))"
                + " && !execution(* jp.gr.java_conf.stardiopside.ark..config..*(..)))"
                + " || execution(* jp.gr.java_conf.stardiopside.silver.commons..*(..))"
                + " || target(" + JpaRepository.class.getName() + ")");
        return new DefaultPointcutAdvisor(pointcut, loggingInterceptor());
    }

    @Bean
    @Primary
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "dataSource")
    @ConditionalOnProperty(prefix = "spring.datasource", name = "url")
    @Primary
    public DataSource dataSource() {
        return dataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean(name = "dataSource", destroyMethod = "")
    @ConditionalOnProperty(prefix = "spring.datasource", name = "jndi-name")
    @Primary
    public DataSource jndiDataSource() {
        return new JndiDataSourceLookup().getDataSource(dataSourceProperties().getJndiName());
    }

    @Bean
    @Primary
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
