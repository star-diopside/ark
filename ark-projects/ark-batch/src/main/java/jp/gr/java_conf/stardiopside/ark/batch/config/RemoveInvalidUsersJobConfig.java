package jp.gr.java_conf.stardiopside.ark.batch.config;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import jp.gr.java_conf.stardiopside.ark.batch.item.InvalidUserFilter;
import jp.gr.java_conf.stardiopside.ark.batch.item.RemoveUserWriter;
import jp.gr.java_conf.stardiopside.ark.batch.item.UserVersionCheckProcessor;
import jp.gr.java_conf.stardiopside.ark.data.entity.User;
import jp.gr.java_conf.stardiopside.ark.data.repository.UserRepository;
import jp.gr.java_conf.stardiopside.ark.service.UserService;
import jp.gr.java_conf.stardiopside.silver.commons.batch.support.TemporaryFileJobListener;

@Configuration
public class RemoveInvalidUsersJobConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Bean
    public Job removeInvalidUsersJob(@Qualifier("extractStep") Step extractStep,
            @Qualifier("removeStep") Step removeStep) {
        return jobBuilderFactory.get("removeInvalidUsersJob").listener(temporaryFileJobListener()).start(extractStep)
                .next(removeStep).build();
    }

    @Bean
    protected JobExecutionListener temporaryFileJobListener() {
        TemporaryFileJobListener listener = new TemporaryFileJobListener();
        listener.setKey("tempFile");
        return listener;
    }

    @Bean
    protected Step extractStep(@Qualifier("extractStepWriter") ItemWriter<User> extractStepWriter) {
        return stepBuilderFactory.get("extractStep").<User, User>chunk(10).reader(extractStepReader())
                .processor(extractStepProcessor()).writer(extractStepWriter).build();
    }

    @Bean
    @StepScope
    protected JpaPagingItemReader<User> extractStepReader() {
        JpaPagingItemReader<User> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString("select u from User u order by u.userId asc");
        return reader;
    }

    @Bean
    @StepScope
    protected InvalidUserFilter extractStepProcessor() {
        return new InvalidUserFilter(userService);
    }

    @Bean
    @StepScope
    protected FlatFileItemWriter<User> extractStepWriter(@Value("#{jobExecutionContext['tempFile']}") String tempFile) {
        BeanWrapperFieldExtractor<User> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[] { "userId", "version" });
        fieldExtractor.afterPropertiesSet();

        DelimitedLineAggregator<User> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setFieldExtractor(fieldExtractor);

        FlatFileItemWriter<User> writer = new FlatFileItemWriter<>();
        writer.setResource(new FileSystemResource(tempFile));
        writer.setEncoding("UTF-8");
        writer.setLineAggregator(lineAggregator);

        return writer;
    }

    @Bean
    protected Step removeStep(@Qualifier("removeStepReader") ItemReader<User> removeStepReader) {
        return stepBuilderFactory.get("removeStep").<User, User>chunk(10).reader(removeStepReader)
                .processor(removeStepProcessor()).writer(removeStepWriter()).build();
    }

    @Bean
    @StepScope
    protected FlatFileItemReader<User> removeStepReader(@Value("#{jobExecutionContext['tempFile']}") String tempFile)
            throws Exception {
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("userId", "version");
        tokenizer.afterPropertiesSet();

        BeanWrapperFieldSetMapper<User> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(User.class);
        fieldSetMapper.afterPropertiesSet();

        DefaultLineMapper<User> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        lineMapper.afterPropertiesSet();

        FlatFileItemReader<User> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource(tempFile));
        reader.setEncoding("UTF-8");
        reader.setLineMapper(lineMapper);

        return reader;
    }

    @Bean
    @StepScope
    protected UserVersionCheckProcessor removeStepProcessor() {
        return new UserVersionCheckProcessor(userRepository);
    }

    @Bean
    @StepScope
    protected RemoveUserWriter removeStepWriter() {
        return new RemoveUserWriter(userService);
    }
}
