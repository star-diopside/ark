package jp.gr.java_conf.star_diopside.ark.data.repository;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.ITable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import jp.gr.java_conf.star_diopside.ark.data.entity.User;
import jp.gr.java_conf.star_diopside.silver.commons.test.support.DatabaseTestSupport;
import jp.gr.java_conf.star_diopside.silver.commons.test.support.SimpleDatabaseTestSupport;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-test.xml" })
@Transactional
@Rollback
public class UserRepositoryTest {

    @Resource(name = "dbunitDataSource")
    private DataSource dataSource;

    @Inject
    private UserRepository userRepository;

    private DatabaseTestSupport databaseTestSupport;

    @Before
    public void before() {
        databaseTestSupport = new SimpleDatabaseTestSupport(this, dataSource);
        databaseTestSupport.setCsvDataSet("dataset");
        databaseTestSupport.onSetup();
    }

    @After
    public void after() {
        databaseTestSupport.onTearDown();
    }

    @Test
    public void testCount() throws Exception {
        long count = userRepository.count();
        long rowCount = databaseTestSupport.getDataSet().getTable("users").getRowCount();
        assertThat(count, is(rowCount));
    }

    @Test
    public void testFindOne() throws Exception {
        User user = userRepository.getOne("user01");
        assertUserEntity(user);
    }

    @Test
    public void testFindAll() throws Exception {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            assertUserEntity(user);
        }
    }

    private void assertUserEntity(User user) throws DataSetException {

        ITable table = databaseTestSupport.getDataSet().getTable("users");
        int count = 0;

        for (int i = 0; i < table.getRowCount(); i++) {
            if (StringUtils.equals(user.getUserId(), (String) table.getValue(i, "user_id"))) {
                count++;
                assertThat(user.getUserId(), is(table.getValue(i, "user_id")));
                assertThat(user.getUsername(), is(table.getValue(i, "username")));
                assertThat(user.getPassword(), is(table.getValue(i, "password")));
                assertThat(user.getPasswordUpdatedAt(), is(toLocalDateTime(table.getValue(i, "password_updated_at"))));
                assertThat(user.isEnabled(), is(Boolean.valueOf((String) table.getValue(i, "enabled"))));
                assertThat(user.isHighGradeRegistry(),
                        is(Boolean.valueOf((String) table.getValue(i, "high_grade_registry"))));
                assertThat(user.getLoginErrorCount(),
                        is(Integer.valueOf((String) table.getValue(i, "login_error_count"))));
                assertThat(user.getLockoutAt(), is(toLocalDateTime(table.getValue(i, "lockout_at"))));
                assertThat(user.getLastLoginAt(), is(toLocalDateTime(table.getValue(i, "last_login_at"))));
                assertThat(user.getLogoutAt(), is(toLocalDateTime(table.getValue(i, "logout_at"))));
                assertThat(user.getCreatedAt(), is(toLocalDateTime(table.getValue(i, "created_at"))));
                assertThat(user.getCreatedUserId(), is(table.getValue(i, "created_user_id")));
                assertThat(user.getUpdatedAt(), is(toLocalDateTime(table.getValue(i, "updated_at"))));
                assertThat(user.getUpdatedUserId(), is(table.getValue(i, "updated_user_id")));
                assertThat(user.getVersion(), is(Integer.valueOf((String) table.getValue(i, "version"))));
            }
        }

        assertThat(count, is(1));
    }

    private static LocalDateTime toLocalDateTime(Object timestampString) {
        return Timestamp.valueOf((String) timestampString).toLocalDateTime();
    }
}
