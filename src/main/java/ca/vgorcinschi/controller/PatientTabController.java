package ca.vgorcinschi.controller;

import ca.vgorcinschi.dao.PatientDBService;
import ca.vgorcinschi.model.Patient;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author vgorcinschi
 */
@Component
public class PatientTabController extends AbstractTabController<Patient> implements Command {

    @Autowired
    PatientDBService service;
    
    @FXML
    TextField nameFilter;
    
    @FXML
    TextField idFilter;
    
    @FXML
    TableView<Patient> patientDataTable;
    //data table columns
    @FXML
    TableColumn<Patient, Number> idColumn;
    @FXML
    TableColumn<Patient, String> lastNameColumn;
    @FXML
    TableColumn<Patient, String> firstNameColumn;
    @FXML
    TableColumn<Patient, String> diagnosisColumn;
    @FXML
    TableColumn<Patient, LocalDateTime> admissionDateColumn;
    @FXML
    TableColumn<Patient, LocalDateTime> releaseDateColumn;

    @Override
    public void execute() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Initializes the controller class.
     */
    @FXML
    private void initialize() {
        //link table columns to patient class properties
        idColumn.setCellValueFactory(cellData -> cellData.getValue()
                .patientIdProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue()
                .lastNameProperty());
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue()
                .firstNameProperty());
        diagnosisColumn.setCellValueFactory(cellData -> cellData.getValue()
                .diagnosisProperty());
        admissionDateColumn.setCellValueFactory(cellData -> cellData.getValue()
                .admissionDateProperty());
        releaseDateColumn.setCellValueFactory(cellData -> cellData.getValue()
                .releaseDateProperty());
        //on our init() we will load all patients
        populateTableView(service.allPatients().orElse(new LinkedList<>()));
    }

    public PatientDBService getService() {
        return service;
    }

    @Override
    public void populateTableView(List<Patient> list) {
        ObservableList<Patient> observableList = FXCollections.observableArrayList(list);
        patientDataTable.setItems(observableList);
    }
}
