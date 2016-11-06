/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vgorcinschi.dao.repositories;

import ca.vgorcinschi.model.Medication;
import static java.math.BigDecimal.valueOf;
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
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test class to test the jdbc implementation for SurgicalRepository
 *
 * @author v_gorcin
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MedicationRepositoryTests {

    //this test class' logger
    private final Logger log
            = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private MedicationRepository repository;

    //target for tests
    private Medication medication;

    //random used in the setUp() method. It is cheaper to reuse one Random object
    private final Random random;

    public MedicationRepositoryTests() {
        random = new Random(System.currentTimeMillis());
    }

    @Before
    public void setUp() {
        medication = new Medication();
        //assign to a random patient with id 1-5
        //source=http://stackoverflow.com/questions/363681/generating-random-integers-in-a-specific-range
        int id = random.nextInt((5 - 1) + 1) + 1;
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
    public void medicationRepoIsNotNull() {
        assertNotNull(repository);
    }

    //only associating the surgical to the first 20 patients (by their ids)
    @Test
    public void addMedicationTest() {
        assertTrue(repository.add(medication));
    }

    @Test
    public void failedAddMedicationTest() {
        //this patient id doesn't exist, so we should be getting an error
        medication.setPatientId(10000);
        assertFalse(repository.add(medication));
    }
    
    @Test
    public void updateMedicationTest() {
        /*
         We don't have and we don't need a method for retrieving a
         medication by id or name. We will manually 'hard' set the id
         to an existing id in the db and try to update it.
         */
        medication.setId(6);
        assertTrue(repository.update(medication));
    }
}