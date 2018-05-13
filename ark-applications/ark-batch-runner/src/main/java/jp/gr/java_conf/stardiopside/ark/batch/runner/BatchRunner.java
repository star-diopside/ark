package jp.gr.java_conf.stardiopside.ark.batch.runner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import jp.gr.java_conf.stardiopside.ark.core.config.AppConfig;

@SpringBootApplication
@Import(AppConfig.class)
public class BatchRunner {

    public static void main(String[] args) {
        SpringApplication.run(BatchRunner.class, args);
    }
}
