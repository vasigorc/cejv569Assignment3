package ca.vgorcinschi;

import javax.sql.DataSource;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientDbApplicationTests {

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
    
	@Test
	public void contextLoads() {
	}
        
        @Test
        public void dataSourceIsNotNullTest(){
            assertNotNull(dataSource);
        }

        @Test
        public void jdbcTemplateIsNotNull(){
            assertNotNull(jdbcTemplate);
        }
}
