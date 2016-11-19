/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vgorcinschi.dao.repositories;

import ca.vgorcinschi.IntegrationTestConfig;
import ca.vgorcinschi.model.Inpatient;
import static java.math.BigDecimal.*;
import static java.time.LocalDateTime.*;
import java.time.Month;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author vgorcinschi
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntegrationTestConfig.class)
public class InpatientRepositoryTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private InpatientRepository repository;

    //target for tests
    private Inpatient inpatient;

    //random used in the setUp() method. It is cheaper to reuse one Random object
    private final Random random;

    public InpatientRepositoryTests() {
        random = new Random(System.currentTimeMillis());
    }

    @Before
    public void setUp() {
        inpatient = new Inpatient();
        //assign to a random patient with id 1-20
        //source=http://stackoverflow.com/questions/363681/generating-random-integers-in-a-specific-range
        int id = random.nextInt((5 - 1) + 1) + 1;
        inpatient.setPatientId(id);
        inpatient.setDailyRate(valueOf(10));
        inpatient.setDateOfStay(of(2016, Month.MARCH, 15, 10, 00));
        inpatient.setRoomNumber("C7");
        inpatient.setServices(valueOf(15));
        inpatient.setSupplies(valueOf(5));
    }

    @After
    public void tearDown() {
    }

    @Test
    public void inpatientRepoIsNotNull() {
        assertNotNull(repository);
    }

    //Ignoring for now - too many created. 
    @Test
    //@Ignore
    public void addInpatientTest() {
        assertTrue(repository.add(inpatient));
    }

    @Test
    public void updateInpatientTest() {
        /*
         We don't have and we don't need a method for retrieving an 
         inpatient by id or name. We will manually 'hard' set the id
         to an existing id in the db and try to update it.
         */
        inpatient.setId(6);
        assertTrue(repository.update(inpatient));
    }

    //skipping the test to not delete too many
    @Test
    @Ignore
    public void deleteInpatientTest() {
        /*
         random int > 5 (don't want to delete our default values) but less then 15
         source=http://stackoverflow.com/questions/363681/generating-random-integers-in-a-specific-range
         */
        int id = random.nextInt((22 - 5) + 1) + 5;
        assertTrue(repository.delete(id));
    }

    @Test
    public void batchUpdateInpatienstTest() {
        /*
         The test lies in creating a list of inpatients with existing ids
         (note that) id of the patient isn't important and passing it to
         the repository's method.
         */
        List<Inpatient> inpatients = IntStream.rangeClosed(1, 5).boxed()
                .map((Integer i) -> {
                    inpatient.setId(i);
                    return inpatient;
                }).collect(Collectors.toList());
        //theoretically our test should return true
        assertTrue(repository.updateBatch(inpatients));
    }

    @Test
    public void getPatientDetailsTest() {
        List<Inpatient> inpatients = repository.getPatientDetails(3);
        assertTrue("It should be more then 1.", inpatients.size() > 1
                && inpatients.stream().allMatch((Inpatient i) -> {
                    return i.getPatientId() == 3;
                }));
    }
    
    @Test
    public void failedGetSurgicalDetailsTest() {
        //pass-in a ridiculously large number
        assertThat(repository.getPatientDetails(10000).isEmpty(), is(true));
    }
}
