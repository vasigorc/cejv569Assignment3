package ca.vgorcinschi.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author vgorcinschi
 */
@Component
public class MainController extends TabPane implements TabMediator {
    
    @FXML private PatientTabController patientTabController;
    @FXML private InpatientTabController inpatientTabController;
    @FXML private MedicationTabController medicationTabController;
    @FXML private SurgicalTabController surgicalTabController;
    /**
     * Initializes the controller class.
     */
    @FXML private void initialize() {
        // TODO
    }    

    @Override
    public void updatePatient() {
        inpatientTabController.setCurrentPatient(patientTabController.getCurrentPatient());
        medicationTabController.setCurrentPatient(patientTabController.getCurrentPatient());
        surgicalTabController.setCurrentPatient(patientTabController.getCurrentPatient());
    }

    @Override
    public void reloadPatient() {
        patientTabController.getService().savePatient(patientTabController.getCurrentPatient());
        updatePatient();
    }    
}