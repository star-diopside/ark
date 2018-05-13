package jp.gr.java_conf.stardiopside.ark;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchDataSourceInitializer;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;

@SpringBootApplication
@EnableConfigurationProperties(BatchProperties.class)
public class DbSetup {

    public static void main(String[] args) {
        SpringApplication.run(DbSetup.class, args);
    }

    @Bean
    public BatchDataSourceInitializer batchDataSourceInitializer(DataSource dataSource, ResourceLoader resourceLoader,
            BatchProperties properties) {
        return new BatchDataSourceInitializer(dataSource, resourceLoader, properties);
    }
}
