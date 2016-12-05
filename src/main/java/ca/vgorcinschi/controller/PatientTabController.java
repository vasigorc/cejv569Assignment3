package ca.vgorcinschi.controller;

import ca.vgorcinschi.dao.PatientDBService;
import ca.vgorcinschi.model.Patient;
import static ca.vgorcinschi.model.Patient.DEFAULT_PATIENT;
import ca.vgorcinschi.util.CommonUtil;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author vgorcinschi
 */
@Component
public class PatientTabController extends AbstractTabController<Patient> implements Command {

    //wired DAO
    @Autowired
    PatientDBService service;

    //filter fields
    @FXML
    TextField nameFilter;

    @FXML
    TextField idFilter;

    //refresh button in the filter section
    @FXML
    Button refreshButton;

    //error message for filters - invisible by default
    @FXML
    Text filterErrorText;

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

    /**
     * Main view bindings: these are the GUI controls that handle the new or
     * picked patient's data. "mv" prefix stands for Main View
     */
    @FXML
    TextField mvPatientID;

    @FXML
    TextField mvPatientLastName;

    @FXML
    TextField mvPatientFirstName;

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
        initializeListeners();
    }

    public PatientDBService getService() {
        return service;
    }

    @Override
    public void populateTableView(List<Patient> list) {
        ObservableList<Patient> observableList = FXCollections.observableArrayList(list);
        patientDataTable.setItems(observableList);
        //put the first patient to out main view
        setCurrentPatient(observableList.get(0));
        bindMainView();
    }

    @FXML
    public void updateTable(ActionEvent e) {
        List<Patient> result = new LinkedList<>();
        //starting with the most precise
        if (!idFilter.getText().isEmpty()) {
            Optional<Patient> optional = service.findById(Integer.parseInt(idFilter.getText()));
            if (optional.isPresent()) {
                result.add(optional.get());
            }
        } else if (!nameFilter.getText().isEmpty()) {
            Optional<List<Patient>> optional = service.findByName(nameFilter.getText());
            if (optional.isPresent()) {
                result.addAll(optional.get());
            }
        } else {
            //if db is empty -> empty the view also
            result = service.allPatients().orElse(new LinkedList<>());
        }
        populateTableView(result);
    }

    private void initializeListeners() {
        //the "of" factorymethod for immutable collections will only become available
        //from JDK 9 - meanwhile we can use JavaSlang
        javaslang.collection.List<TextField> maxTwenties
                = javaslang.collection.List.of(mvPatientLastName, mvPatientFirstName);
        //set the observable for each of the maxTwenties
        maxTwenties.forEach(field -> CommonUtil.addTextLimiter(field, 20));
        //for the name filter
        nameFilter.textProperty().addListener((arg0, oldValue, newValue) -> {
            //we can only search by name or id, not both!
            if (!nameFilter.getText().isEmpty()) {
                idFilter.setDisable(true);
            } else {
                idFilter.setDisable(false);
            }
            //disallow DB query on 20+ characters
            if (nameFilter.getText().length() > 20) {
                refreshButton.setDisable(true);
                filterErrorText.setText(mediator.getResourceBundle().getString("stringTooLong"));
                filterErrorText.setVisible(true);

            } else {
                refreshButton.setDisable(false);
                filterErrorText.setVisible(false);
            }
        });

        //for the id filter
        idFilter.textProperty().addListener((arg0, oldValue, newValue) -> {
            if (!idFilter.getText().isEmpty()) {
                nameFilter.setDisable(true);
            } else {
                nameFilter.setDisable(false);
            }

            //disallow DB query on 4+ ints
            if (idFilter.getText().length() > 4) {
                refreshButton.setDisable(true);
                filterErrorText.setText(mediator.getResourceBundle().getString("intTooLong"));
                filterErrorText.setVisible(true);

            } else {
                refreshButton.setDisable(false);
                filterErrorText.setVisible(false);
            }
        });
    }

    @Override
    public void bindMainView() {        //if curent patient is null bind with the default patient
        Patient picked = (getCurrentPatient() == null) ? DEFAULT_PATIENT : getCurrentPatient();
        //set all properties
        mvPatientID.textProperty().bind(picked.patientIdProperty().asString());
        Bindings.bindBidirectional(mvPatientLastName.textProperty(), picked.lastNameProperty());
        Bindings.bindBidirectional(mvPatientFirstName.textProperty(), picked.firstNameProperty());
    }
}
