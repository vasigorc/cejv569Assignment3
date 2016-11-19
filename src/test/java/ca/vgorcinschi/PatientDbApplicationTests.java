package ca.vgorcinschi;

import javafx.embed.swing.JFXPanel;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ComponentScan(excludeFilters = {
        @ComponentScan.Filter(classes = IntegrationTestConfig.class, type = FilterType.ASSIGNABLE_TYPE)
})
@SpringBootTest(classes = App.class)
public class PatientDbApplicationTests {

    @Rule
    public JavaFxRuntimeAvailable javaFxRuntimeAvailable = new JavaFxRuntimeAvailable();

    /**
     * DataSource is configured in the src/main/resources/application.yml
     */
    @Autowired
    private DataSource dataSource;

    /**
     * We don't even have to configure JdbcTemplate as a Bean. In Spring Boot
     * it is defined in the DataSourceAutoConfiguration class
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeClass
    public static void bootstrapJavaFx(){
        //implicitly initializes JavaFX Subsystem
        // see http://stackoverflow.com/questions/14025718/javafx-toolkit-not-initialized-when-trying-to-play-an-mp3-file-through-mediap
        new JFXPanel();
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void dataSourceIsNotNullTest() {
        assertNotNull(dataSource);
    }

    @Test
    public void jdbcTemplateIsNotNull() {
        assertNotNull(jdbcTemplate);
    }
}
