package jp.gr.java_conf.stardiopside.ark.data.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import jp.gr.java_conf.stardiopside.ark.data.domain.SecurityContextAuditorAware;

@Configuration
@EntityScan(basePackages = { "jp.gr.java_conf.stardiopside.ark.data.entity",
        "org.springframework.data.jpa.convert.threeten" })
@EnableJpaRepositories(basePackages = "jp.gr.java_conf.stardiopside.ark.data.repository")
@EnableJpaAuditing
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return new SecurityContextAuditorAware();
    }
}
