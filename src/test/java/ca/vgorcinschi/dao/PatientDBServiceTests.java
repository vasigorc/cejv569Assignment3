package ca.vgorcinschi.dao;

import ca.vgorcinschi.dao.repositories.PatientRepository;
import ca.vgorcinschi.model.Medication;
import ca.vgorcinschi.model.Patient;
import static java.math.BigDecimal.valueOf;
import java.time.LocalDateTime;
import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import static org.junit.Assert.*;
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

    @Autowired
    private PatientRepository patientRepository;

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

    @Test
    public void insertPatientTest() {
        assertTrue(service.savePatient(patient));
    }

    @Test
    public void updatePatientTest() {
        //generating a "custom" random last name to retrieve it back
        String checkName = RandomStringUtils.randomAlphabetic(8);
        patient.setLastName(checkName);
        service.savePatient(patient);
        Patient justInserted = patientRepository.findByLastName(checkName).get(0);
        justInserted.setLastName("I have just been updated!");
        assertTrue(justInserted.getPatientId() > 0 && service.savePatient(justInserted));
    }

    @Test
    public void addDetailRecordTest() {
        //creating a sample detail record
        Medication medication = new Medication();
        int id = new Random().nextInt((5 - 1) + 1) + 1;
        medication.setPatientId(id);
        medication.setDateOfMedication(LocalDateTime.now().minusDays(22));
        medication.setMed("Medication");
        medication.setUnitCost(valueOf(15));
        medication.setUnits(valueOf(5));
        assertTrue(service.addDetailRecord(medication));
    }
}
