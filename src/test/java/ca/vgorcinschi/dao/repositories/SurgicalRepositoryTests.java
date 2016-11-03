package ca.vgorcinschi.dao.repositories;

import ca.vgorcinschi.model.Surgical;
import static java.math.BigDecimal.*;
import java.time.LocalDateTime;
import java.util.Random;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
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
@SpringBootTest
public class SurgicalRepositoryTests {

    //this test class' logger
    private final Logger log
            = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private SurgicalRepository repository;

    //target for tests
    private Surgical surgical;

    //random used in the setUp() method. It is cheaper to reuse one Random object
    private final Random random;

    public SurgicalRepositoryTests() {
        random = new Random(System.currentTimeMillis());
    }

    @Before
    public void setUp() {
        surgical = new Surgical();
        //assign to a random patient with id 1-5
        //source=http://stackoverflow.com/questions/363681/generating-random-integers-in-a-specific-range
        int id = random.nextInt((5 - 1) + 1) + 1;
        surgical.setPatientId(id);
        surgical.setRoomFee(valueOf(10));
        surgical.setSupplies(valueOf(5));
        surgical.setDateOfSurgery(LocalDateTime.now().minusWeeks(2));
        surgical.setSurgery("A surgery");
        surgical.setSurgeonFee(valueOf(7));
    }

    @Test
    public void surgicalRepoIsNotNull() {
        assertNotNull(repository);
    }
    
    //only associating the surgical to the first 20 patients (by their ids)
    @Test
    public void addSurgicalTest() {
        assertTrue(repository.add(surgical));
    }
    
    @Test
    public void updateSurgicalTest(){
        /*
         We don't have and we don't need a method for retrieving an 
         inpatient by id or name. We will manually 'hard' set the id
         to an existing id in the db and try to update it.
         */
        surgical.setId(6);
        assertTrue(repository.update(surgical));
    }
    
    //skipping the test to not delete too many
    @Test
    public void deleteInpatientTest() {
        /*
            Instead of deleting actual records (which we don't have too many)
            we will expect false from a ridiculously high id
        */
        assertFalse(repository.delete(12500));
    }
}