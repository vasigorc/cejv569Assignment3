package ca.vgorcinschi.controller;

import ca.vgorcinschi.model.Patient;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import static javaslang.collection.List.of;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author vgorcinschi
 */
@Component
public class MainController implements TabMediator {

    protected org.slf4j.Logger log
            = LoggerFactory.getLogger(this.getClass().getName());

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
    
    private ReportController reportController;
    //quit button
    @FXML
    Button quitBtn;
    //current patietn (top panel)
    @FXML
    Text currentPatient;

    //THE REPORT POP-UP STUFF
    //stage for the report
    Stage popUp;

    Scene popUpScene;

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
        scaffoldReportStage();
        reportController.setMediator(this);
        initializeListeners();
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
        reportController.setCurrentPatient(patientTabController.getCurrentPatient());
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
        if (firstTime) {
            if (of(getSelectedPatient().getFirstName(), getSelectedPatient().getLastName())
                    .forAll(s -> s != null && !s.isEmpty())) {
                currentPatient.setText(getSelectedPatient().getPatientId() + ": "
                        + getSelectedPatient().getLastName() + ", " + getSelectedPatient().getFirstName());
            } else {
                currentPatient.setText("");
            }
        }
        firstTime = false;       
    }

    private void initializeListeners() {
        //for the header (banner) and the report
        selectedPatientProperty().addListener((arg0, oldValue, newValue) -> {
            //only display something if both the name and first name are set
            if (of(newValue.getFirstName(), newValue.getLastName()).forAll(s -> s != null && !s.isEmpty())) {
                currentPatient.setText(newValue.getPatientId() + ": "
                        + newValue.getLastName() + ", " + newValue.getFirstName());
            } else {
                currentPatient.setText("");
            }
        });
    }

    //below are methods and controls for the pop-up report
    private void scaffoldReportStage() {
        Parent parent;
        FXMLLoader popUpLoader = new FXMLLoader(getClass().getResource("/fxml/Report.fxml"), getResourceBundle());
        reportController = popUpLoader.getController();
        try {
            System.out.println("reportController is null"+(reportController==null));
            parent = popUpLoader.load();
            popUpScene = new Scene(parent);
            popUp.setScene(popUpScene);
        } catch (IOException ex) {
            log.error("Failed to load the pop-up fxml file: " + ex.getLocalizedMessage()+"\n"+ex.getStackTrace());
        }
    }

    public Stage getPopUp() {
        return popUp;
    }

    public void setPopUp(Stage popUp) {
        this.popUp = popUp;
    }

    

    @FXML
    public void openPopUp() {
        popUp.showAndWait();
    }

    @Override
    public void closePopUp() {
        popUp.close();
    }
}
