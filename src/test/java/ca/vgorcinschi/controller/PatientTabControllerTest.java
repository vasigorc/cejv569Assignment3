package ca.vgorcinschi.controller;

import ca.vgorcinschi.IntegrationTestConfig;
import ca.vgorcinschi.PatientDbApplicationTests;
import ca.vgorcinschi.dao.PatientDBService;
import ca.vgorcinschi.model.Patient;
import ca.vgorcinschi.util.DozerMapper;
import java.util.ArrayList;
import java.util.List;
import org.dozer.DozerBeanMapper;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author vgorcinschi
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntegrationTestConfig.class)
public class PatientTabControllerTest extends PatientDbApplicationTests {

    @Autowired
    PatientDBService dBService;
    
    @Autowired
    DozerMapper dozerMapper;

    PatientTabController controller;

    DozerBeanMapper mapper;

    public PatientTabControllerTest() {
        controller = new PatientTabController();
        List<String> mappingFiles = new ArrayList();
        mappingFiles.add("dozerJdk8Converters.xml");
        mapper = new DozerBeanMapper();
        mapper.setMappingFiles(mappingFiles);
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void dozerTest() {
        Patient p = dBService.findById(1).get();
        Assume.assumeTrue(p != null);
        Patient copy = mapper.map(p, Patient.class);
        assertEquals(p, copy);
    }

    @Test
    public void dozerBeanTest(){
        Patient p = dBService.findById(1).get();
        Assume.assumeTrue(p != null);
        Patient copy = dozerMapper.dozer().map(p, Patient.class);
        assertEquals(p, copy);
    }
}
