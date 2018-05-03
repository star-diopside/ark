package jp.gr.java_conf.stardiopside.ark.batch.config;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.separator.DefaultRecordSeparatorPolicy;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import jp.gr.java_conf.stardiopside.ark.batch.bean.PostalCodeAddress;
import jp.gr.java_conf.stardiopside.ark.batch.item.PostalCodeConverter;
import jp.gr.java_conf.stardiopside.ark.batch.tasklet.UnarchiveZipFileTasklet;
import jp.gr.java_conf.stardiopside.ark.data.entity.PostalCode;
import jp.gr.java_conf.stardiopside.silver.commons.batch.support.TemporaryDirectoryJobListener;

@Configuration
public class ImportPostalCodeJobConfig {

    private static final String TEMP_DIR_KEY = "postcode.tempDir";

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    @Qualifier("resourcelessTransactionManager")
    private PlatformTransactionManager resourcelessTransactionManager;

    @Bean
    public Job importPostalCodeJob(@Qualifier("unarchiveStep") Step unarchiveStep,
            @Qualifier("importStep") Step importStep) {
        return jobBuilderFactory.get("importPostalCodeJob").listener(temporaryDirectoryJobListener())
                .start(unarchiveStep).next(clearDataStep()).next(importStep).build();
    }

    @Bean
    protected JobExecutionListener temporaryDirectoryJobListener() {
        TemporaryDirectoryJobListener listener = new TemporaryDirectoryJobListener();
        listener.setKey(TEMP_DIR_KEY);
        listener.setPrefix("postcode");
        return listener;
    }

    @Bean
    protected Step unarchiveStep(@Qualifier("unarchiveStepTasklet") Tasklet unarchiveStepTasklet) {
        return stepBuilderFactory.get("unarchiveStep").transactionManager(resourcelessTransactionManager)
                .tasklet(unarchiveStepTasklet).build();
    }

    @Bean
    @StepScope
    protected UnarchiveZipFileTasklet unarchiveStepTasklet(
            @Value("${application.settings.resource.postcode}") Resource resource,
            @Value("#{jobExecutionContext['" + TEMP_DIR_KEY + "']}") String tempDir) {
        UnarchiveZipFileTasklet tasklet = new UnarchiveZipFileTasklet();
        tasklet.setResource(resource);
        tasklet.setCharset("Windows-31J");
        tasklet.setUnarchiveDirectory(tempDir);
        return tasklet;
    }

    @Bean
    protected Step clearDataStep() {
        return stepBuilderFactory.get("clearDataStep").tasklet(clearDataStepTasklet()).build();
    }

    @Bean
    @StepScope
    protected MethodInvokingTaskletAdapter clearDataStepTasklet() {
        MethodInvokingTaskletAdapter tasklet = new MethodInvokingTaskletAdapter();
        tasklet.setTargetObject(jdbcTemplate);
        tasklet.setTargetMethod("execute");
        tasklet.setArguments(
                new Object[] { "TRUNCATE TABLE POSTAL_CODES; SELECT SETVAL('POSTAL_CODES_ID_SEQ', 1, FALSE);" });
        return tasklet;
    }

    @Bean
    protected Step importStep(@Qualifier("importStepReader") ItemReader<PostalCodeAddress> importStepReader) {
        return stepBuilderFactory.get("importStep").<PostalCodeAddress, PostalCode>chunk(100).reader(importStepReader)
                .processor(importStepProcessor()).writer(importStepWriter()).build();
    }

    @Bean
    @StepScope
    protected MultiResourceItemReader<PostalCodeAddress> importStepReader(
            @Value("file:#{jobExecutionContext['" + TEMP_DIR_KEY + "']}/*") Resource[] resources) throws Exception {
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("localGovernmentCode", "oldPostalCode", "postalCode", "kanaPrefectureName",
                "kanaMunicipalityName", "kanaAreaName", "kanjiPrefectureName", "kanjiMunicipalityName", "kanjiAreaName",
                "flag1", "flag2", "flag3", "flag4", "flag5", "flag6");
        tokenizer.afterPropertiesSet();

        BeanWrapperFieldSetMapper<PostalCodeAddress> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(PostalCodeAddress.class);
        fieldSetMapper.afterPropertiesSet();

        DefaultLineMapper<PostalCodeAddress> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        lineMapper.afterPropertiesSet();

        FlatFileItemReader<PostalCodeAddress> reader = new FlatFileItemReader<>();
        reader.setEncoding("Windows-31J");
        reader.setRecordSeparatorPolicy(new DefaultRecordSeparatorPolicy());
        reader.setLineMapper(lineMapper);
        reader.afterPropertiesSet();

        MultiResourceItemReader<PostalCodeAddress> multiReader = new MultiResourceItemReader<>();
        multiReader.setResources(resources);
        multiReader.setDelegate(reader);

        return multiReader;
    }

    @Bean
    @StepScope
    protected PostalCodeConverter importStepProcessor() {
        return new PostalCodeConverter();
    }

    @Bean
    @StepScope
    protected JpaItemWriter<PostalCode> importStepWriter() {
        JpaItemWriter<PostalCode> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
}
