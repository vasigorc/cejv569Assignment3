package ca.vgorcinschi.controller;

import ca.vgorcinschi.IntegrationTestConfig;
import ca.vgorcinschi.PatientDbApplicationTests;
import ca.vgorcinschi.dao.PatientDBService;
import ca.vgorcinschi.model.Patient;
import java.util.List;
import javafx.scene.control.TableView;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author vgorcinschi
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntegrationTestConfig.class)
public class PatientTabControllerTest extends PatientDbApplicationTests{
    
    @Autowired
    PatientDBService dBService;
    
    PatientTabController controller;
    
    public PatientTabControllerTest() {
        controller = new PatientTabController();
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void populateTableViewTest(){
        List<Patient> patients = dBService.allPatients().get();
        controller.patientDataTable = new TableView<>();
        controller.populateTableView(patients);
        assertTrue(controller.patientDataTable.getItems().size()>1);
    }
}
