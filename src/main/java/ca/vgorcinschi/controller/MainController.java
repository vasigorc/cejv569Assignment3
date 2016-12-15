package ca.vgorcinschi.controller;

import ca.vgorcinschi.model.Patient;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import static javaslang.collection.List.of;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author vgorcinschi
 */
@Component
public class MainController implements TabMediator {

    @FXML
    private ResourceBundle resources;
    @FXML
    private PatientTabController patientTabController;
    @FXML
    private InpatientTabController inpatientTabController;
    @FXML
    private MedicationTabController medicationTabController;
    @FXML
    private SurgicalTabController surgicalTabController;
    //quit button
    @FXML
    Button quitBtn;
    //current patietn (top panel)
    @FXML
    Text currentPatient;
    
    //controle l'affichage de l'entete la premiere fois
    private boolean firstTime = true;
    //the object property of the current patient that will be used by the header
    public final ObjectProperty<Patient> selectedPatient = new SimpleObjectProperty();

    /**
     * Initializes the controller class.
     */
    @FXML
    private void initialize() {
        //plug-in the mediator to all tabs
        patientTabController.setMediator(this);
        medicationTabController.setMediator(this);
        inpatientTabController.setMediator(this);
        surgicalTabController.setMediator(this);
        updatePatient();
    }

    //quite app
    @FXML
    public void quit() {
        Platform.exit();
    }

    @Override
    public void updatePatient() {
        //update the header in the upper banner based on the selected patient property
        rebindSelectedPatient(patientTabController.getCurrentPatient());
        inpatientTabController.setCurrentPatient(patientTabController.getCurrentPatient());
        medicationTabController.setCurrentPatient(patientTabController.getCurrentPatient());
        surgicalTabController.setCurrentPatient(patientTabController.getCurrentPatient());
    }

    @Override
    public void reloadPatient() {
        patientTabController.updateTable(null);
        updatePatient();
    }

    @Override
    public ResourceBundle getResourceBundle() {
        return resources;
    }

    public final Patient getSelectedPatient() {
        return selectedPatient.get();
    }

    public final void setSelectedPatient(Patient selectedPatient) {
        this.selectedPatient.set(selectedPatient);
    }

    public final ObjectProperty<Patient> selectedPatientProperty() {
        return selectedPatient;
    }

    private void rebindSelectedPatient(Patient currentPatient1) {
        selectedPatient.set(currentPatient1);
        if(firstTime){
            if (of(getSelectedPatient().getFirstName(), getSelectedPatient().getLastName())
                    .forAll(s -> s != null && !s.isEmpty())) {
                currentPatient.setText(getSelectedPatient().getPatientId() + ": "
                        + getSelectedPatient().getLastName() + ", " + getSelectedPatient().getFirstName());
            } else {
                currentPatient.setText("");
            }
        }
        selectedPatientProperty().addListener((arg0, oldValue, newValue) -> {
            //only display something if both the name and first name are set
            if (of(newValue.getFirstName(), newValue.getLastName()).forAll(s -> s != null && !s.isEmpty())) {
                currentPatient.setText(newValue.getPatientId() + ": "
                        + newValue.getLastName() + ", " + newValue.getFirstName());
            } else {
                currentPatient.setText("");
            }
        });
        firstTime = false;
    }
}
