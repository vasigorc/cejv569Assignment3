/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vgorcinschi.dao.repositories;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
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
public class PatientRepositoryTests {
    
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
    public void patientRepoIsNotNull(){
        assertNotNull(repository);
    }
    
    @Test
    public void findByLastNameTest(){
        assertEquals("There is only one Wayne "
                + "in the dump file", repository.findByLastName("Wayne").size(), 1);
    }
}
