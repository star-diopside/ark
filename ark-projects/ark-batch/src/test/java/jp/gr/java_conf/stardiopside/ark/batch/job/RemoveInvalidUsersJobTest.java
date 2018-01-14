package jp.gr.java_conf.stardiopside.ark.batch.job;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.SortedDataSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

import jp.gr.java_conf.stardiopside.silver.commons.test.support.CommitTransactionDatabaseTestSupport;
import jp.gr.java_conf.stardiopside.silver.commons.test.support.DatabaseTestSupport;
import jp.gr.java_conf.stardiopside.silver.commons.test.util.DataSetUtils;
import jp.gr.java_conf.stardiopside.silver.commons.test.util.TestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class RemoveInvalidUsersJobTest {

    @Inject
    @Named("dbunitDataSource")
    private DataSource dataSource;

    @Inject
    private PlatformTransactionManager transactionManager;

    @Inject
    private JobLauncherTestUtils jobLauncherTestUtils;

    private DatabaseTestSupport databaseTestSupport;
    private Map<Object, Object> replacementObjectMap;

    @Before
    public void before() {
        HashMap<Object, Object> objectMap = new HashMap<>();
        LocalDateTime today = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
        objectMap.put("${TODAY}", Timestamp.valueOf(today));
        objectMap.put("${YESTERDAY}", Timestamp.valueOf(today.minusDays(1)));
        databaseTestSupport = new CommitTransactionDatabaseTestSupport(this, dataSource, transactionManager);
        databaseTestSupport.setCsvDataSet("dataset");
        databaseTestSupport.setReplacementObjectMap(objectMap);
        databaseTestSupport.onSetup();
        replacementObjectMap = objectMap;
    }

    @After
    public void after() {
        databaseTestSupport.onTearDown();
    }

    @Test
    public void testJob() throws Exception {
        JobParametersBuilder jpb = new JobParametersBuilder(jobLauncherTestUtils.getUniqueJobParameters());
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jpb.toJobParameters());
        assertThat(jobExecution.getExitStatus(), is(ExitStatus.COMPLETED));

        IDataSet expectedDataSet = new ReplacementDataSet(DataSetUtils.createCsvDataSet(TestUtils.findTestDataFile(
                this, "expected")), replacementObjectMap, null);
        IDataSet actualDataSet = databaseTestSupport.getConnection().createDataSet(expectedDataSet.getTableNames());
        Assertion.assertEquals(new SortedDataSet(expectedDataSet), new SortedDataSet(actualDataSet));
    }
}
