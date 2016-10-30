/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vgorcinschi.dao.repositories;

import ca.vgorcinschi.model.Inpatient;
import static java.math.BigDecimal.*;
import static java.time.LocalDateTime.*;
import java.time.Month;
import java.util.Random;
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
@SpringBootTest
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
        int id = random.nextInt((16 - 1) + 1) + 1;
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
    
    @Test
    public void addInpatientTest() {
        assertTrue(repository.add(inpatient));
    }
}
