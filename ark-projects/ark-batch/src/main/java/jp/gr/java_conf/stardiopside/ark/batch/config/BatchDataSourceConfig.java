package jp.gr.java_conf.stardiopside.ark.batch.config;

import javax.sql.DataSource;

import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.batch.BatchDataSourceInitializer;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

@Configuration
public class BatchDataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.job-datasource")
    public DataSourceProperties jobDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "jobDataSource")
    @ConditionalOnProperty(prefix = "spring.job-datasource", name = "url")
    public DataSource jobDataSource() {
        return jobDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean(name = "jobDataSource", destroyMethod = "")
    @ConditionalOnProperty(prefix = "spring.job-datasource", name = "jndi-name")
    public DataSource jndiJobDataSource() {
        return new JndiDataSourceLookup().getDataSource(jobDataSourceProperties().getJndiName());
    }

    @Bean
    public ResourcelessTransactionManager resourcelessTransactionManager() {
        return new ResourcelessTransactionManager();
    }

    @Bean
    public BatchDataSourceInitializer batchDataSourceInitializer(@Qualifier("jobDataSource") DataSource jobDataSource,
            ResourceLoader resourceLoader, BatchProperties properties) {
        return new BatchDataSourceInitializer(jobDataSource, resourceLoader, properties);
    }
}
