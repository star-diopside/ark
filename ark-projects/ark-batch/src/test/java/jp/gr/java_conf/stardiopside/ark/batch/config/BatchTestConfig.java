package jp.gr.java_conf.stardiopside.ark.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import jp.gr.java_conf.stardiopside.ark.test.config.TestConfig;

@TestConfiguration
@Import(TestConfig.class)
public class BatchTestConfig {

    @Bean
    public JobLauncherTestUtils removeInvalidUsersJobLauncherTestUtils() {
        return new JobLauncherTestUtils() {
            @Override
            @Autowired
            public void setJob(@Qualifier("removeInvalidUsersJob") Job job) {
                super.setJob(job);
            }
        };
    }
}
