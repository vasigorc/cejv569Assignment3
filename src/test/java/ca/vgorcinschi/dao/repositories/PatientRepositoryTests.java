/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vgorcinschi.dao.repositories;

import ca.vgorcinschi.model.Patient;
import java.time.LocalDateTime;
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

    public PatientRepositoryTests() {
    }

    @Before
    public void setUp() {
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
    public void findAllPatientsTest(){
        assertTrue("5 is the initial batch.",repository.getAll().size()>=5);
    }
    
    @Test
    public void addAPatient(){
        Patient p = new Patient();
        p.setAdmissionDate(LocalDateTime.now().minusMonths(2));
        p.setReleaseDate(LocalDateTime.now().minusMonths(1));
        p.setDiagnosis("test diagnosis");
        p.setFirstName("First");
        p.setLastName("Last");
        assertTrue(repository.add(p));
    }
}
