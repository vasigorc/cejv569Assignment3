/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vgorcinschi.dao.repositories;

import ca.vgorcinschi.App;
import ca.vgorcinschi.IntegrationTestConfig;
import ca.vgorcinschi.model.Medication;
import static java.math.BigDecimal.valueOf;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test class to test the jdbc implementation for SurgicalRepository
 *
 * @author v_gorcin
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
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

    @Test
    public void deleteMedicationTest() {
        /*
         Instead of deleting actual records (which we don't have too many)
         we will expect false from a ridiculously high id
         */
        assertFalse(repository.delete(12500));
    }

    @Test
    public void batchUpdateMedicationTest() {
        /*
         The test lies in creating a list of inpatients with existing ids
         (note that) id of the patient isn't important and passing it to
         the repository's method.
         */
        List<Medication> medications = IntStream.rangeClosed(1, 5).boxed()
                .map((Integer i) -> {
                    medication.setId(i);
                    return medication;
                }).collect(Collectors.toList());
        //theoretically our test should return true
        assertTrue(repository.updateBatch(medications));
    }

    @Test
    public void getSurgicalDetailsTest() {
        //I couldn't come up with a better test than looking up an patient 
        //with 1+ surgicals
        List<Medication> medications = repository.getPatientDetails(2);
        assertTrue("It should be more then 1.", medications.size() > 1
                && medications.stream().allMatch((Medication m)
                        -> m.getPatientId() == 2
                ));
    }
}
