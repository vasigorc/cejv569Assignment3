/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vgorcinschi.dao.repositories;

import ca.vgorcinschi.model.Patient;
import java.time.LocalDateTime;
import java.util.Random;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author vgorcinschi
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientRepositoryTests {

    //this test class' logger
    private final Logger log
            = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PatientRepository repository;

    //target for tests
    private Patient dummy;

    public PatientRepositoryTests() {

    }

    @Before
    public void setUp() {
        dummy = new Patient();
        dummy.setAdmissionDate(LocalDateTime.now().minusMonths(2));
        dummy.setReleaseDate(LocalDateTime.now().minusMonths(1));
        dummy.setDiagnosis("test diagnosis");
        dummy.setFirstName("First");
        dummy.setLastName("Last");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void patientRepoIsNotNull() {
        assertNotNull(repository);
    }

    @Test
    public void findByLastNameTest() {
        assertEquals("There is only one Wayne "
                + "in the dump file", repository.findByLastName("Wayne").size(), 1);
    }

    @Test
    public void findByIdTest() {
        String diagnosis = repository.findById(2).getDiagnosis();
        assertEquals("In the sql dump file patient with id 2 "
                + "should have 'Kidney Stones' (id=2)", "Kidney Stones", diagnosis);
        log.info("findByIdTest completed " + diagnosis);
    }

    @Test(expected = DataAccessException.class)
    public void findByIdWithInvalidIDTest() {
        //pass-in a ridiculously large id number
        repository.findById(2000).getDiagnosis();
    }

    @Test
    public void findAllPatientsTest() {
        assertTrue("5 is the initial batch.", repository.getAll().size() >= 5);
    }

    @Test
    public void addAPatientTest() {
        assertTrue(repository.add(dummy));
    }

    @Test
    public void updatePatientTest() {
        Patient p = repository.findById(6);
        p.setFirstName("Test succeeded");
        assertTrue(repository.update(p));
    }

    @Test
    public void failedUpdatePatientTest() {
        //try to update a patient with no id (equivalent to 0)
        dummy.setPatientId(0);
        assertFalse(repository.update(dummy));
    }

    @Test
    public void deletePatientTest() {
        //random int > 5 (don't want to delete our default values) but less then 15
        //source=http://stackoverflow.com/questions/363681/generating-random-integers-in-a-specific-range
        int id = new Random().nextInt((15 - 5) + 1) + 5;
        assertTrue(repository.delete(id));
    }
    
    @Test
    public void failedDeletePatientTest(){
        assertFalse(repository.delete(0));
    }
}
