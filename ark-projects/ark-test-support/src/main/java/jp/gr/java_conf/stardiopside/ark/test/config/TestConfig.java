package jp.gr.java_conf.stardiopside.ark.test.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

@TestConfiguration
@EnableAutoConfiguration
public class TestConfig {

    @Bean
    public DataSource dbunitDataSource(DataSource dataSource) {
        return new TransactionAwareDataSourceProxy(dataSource);
    }
}
