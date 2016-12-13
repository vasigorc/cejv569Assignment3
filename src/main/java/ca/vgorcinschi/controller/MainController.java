package ca.vgorcinschi.controller;

import java.util.ResourceBundle;
import javafx.fxml.FXML;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author vgorcinschi
 */
@Component
public class MainController implements TabMediator {
    
    @FXML private ResourceBundle resources;
    @FXML private PatientTabController patientTabController;
    @FXML private InpatientTabController inpatientTabController;
    @FXML private MedicationTabController medicationTabController;
    @FXML private SurgicalTabController surgicalTabController;
    /**
     * Initializes the controller class.
     */
    @FXML private void initialize() {
        //plug-in the mediator to all tabs
        patientTabController.setMediator(this);
        medicationTabController.setMediator(this);
        inpatientTabController.setMediator(this);
        updatePatient();
    }    

    @Override
    public void updatePatient() {
        inpatientTabController.setCurrentPatient(patientTabController.getCurrentPatient());
        medicationTabController.setCurrentPatient(patientTabController.getCurrentPatient());
        surgicalTabController.setCurrentPatient(patientTabController.getCurrentPatient());
    }

    @Override
    public void reloadPatient() {
        //patientTabController.getService().savePatient(patientTabController.getCurrentPatient());
        patientTabController.updateTable(null);
        updatePatient();
    }    

    @Override
    public ResourceBundle getResourceBundle() {
        return resources;
    }
}