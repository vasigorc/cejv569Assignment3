package ca.vgorcinschi.dao;

import ca.vgorcinschi.dao.repositories.MedicationRepository;
import ca.vgorcinschi.dao.repositories.PatientRepository;
import ca.vgorcinschi.model.Medication;
import ca.vgorcinschi.model.Patient;
import static java.math.BigDecimal.valueOf;
import java.time.LocalDateTime;
import java.util.List;
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

    @Autowired
    private MedicationRepository medicationRepository;

    //targets for tests
    private Patient patient;
    private Medication medication;

    public PatientDBServiceTests() {
    }

    @Before
    public void setUp() {
        //for patietn
        patient = new Patient();
        patient.setAdmissionDate(LocalDateTime.now().minusMonths(2));
        patient.setReleaseDate(LocalDateTime.now().minusMonths(1));
        patient.setDiagnosis("test diagnosis");
        patient.setFirstName("First");
        patient.setLastName("Last");

        //for medication
        medication = new Medication();
        int id = new Random().nextInt((5 - 1) + 1) + 1;
        medication.setPatientId(id);
        medication.setDateOfMedication(LocalDateTime.now().minusDays(22));
        medication.setMed("Medication");
        medication.setUnitCost(valueOf(15));
        medication.setUnits(valueOf(5));
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
        assertTrue(service.saveDetailRecord(medication));
    }

    @Test
    public void updateDetailRecordTest() {
        //provided there are some medication records for patient # 3
        Medication aMedication = medicationRepository.getPatientDetails(3).get(0);
        assert aMedication.getId() > 0;
        aMedication.setUnitCost(valueOf(23));
        assertTrue(service.saveDetailRecord(aMedication));
    }

    @Test
    public void deleteDetailRecordTest() {
        /**
         * test only worked for me because this select count(ID), PATIENTID from
         * HOSPITALDB.MEDICATION group by PATIENTID; returned 9 for patient # 4.
         * Switch the number to fit your figures.
         */
        List<Medication> meds = medicationRepository.getPatientDetails(4);
        //if we don't have a marge - skip the test
        org.junit.Assume.assumeTrue(meds.size() > 2);
        assertTrue(service.deleteDetailRecord(meds.get(0)));
    }
}