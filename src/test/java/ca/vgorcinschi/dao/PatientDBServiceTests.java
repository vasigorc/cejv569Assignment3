package ca.vgorcinschi.dao;

import ca.vgorcinschi.model.Patient;
import java.time.LocalDateTime;
import org.junit.After;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test class to test the jdbc implementation for PatientDBService
 *
 * @author vgorcinschi
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientDBServiceTests {

    @Autowired
    private PatientDBService service;

    //target for tests
    private Patient patient;

    public PatientDBServiceTests() {
    }

    @Before
    public void setUp() {
        patient = new Patient();
        patient.setAdmissionDate(LocalDateTime.now().minusMonths(2));
        patient.setReleaseDate(LocalDateTime.now().minusMonths(1));
        patient.setDiagnosis("test diagnosis");
        patient.setFirstName("First");
        patient.setLastName("Last");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void serviceIsNotNull() {
        assertNotNull(service);
    }
}
