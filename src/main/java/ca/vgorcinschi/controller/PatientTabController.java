package ca.vgorcinschi.controller;

import ca.vgorcinschi.dao.PatientDBService;
import ca.vgorcinschi.model.Patient;
import static ca.vgorcinschi.model.Patient.DEFAULT_PATIENT;
import ca.vgorcinschi.util.CommonUtil;
import ca.vgorcinschi.util.DozerMapper;
import com.jfoenix.controls.JFXDatePicker;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;
import javaslang.Tuple;
import javaslang.Tuple2;
import static javaslang.collection.List.of;
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
    
    //bean that copies object properties
    @Autowired
    DozerMapper dozerMapper;

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

    @FXML
    TextArea mvPatientDiagnosis;

    @FXML
    JFXDatePicker mvPatientAdmDate;

    @FXML
    JFXDatePicker mvPatientAdmTime;

    @FXML
    JFXDatePicker mvPatientRelTime;

    @FXML
    JFXDatePicker mvPatientRelDate;
    
    @FXML
    Button mvAddBtn;

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
        //put a COPY! of the first patient to out main view
        currentPatient = dozerMapper.dozer().map(observableList.get(0), Patient.class);
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
    
    @FXML
    public void newPatient(){
        currentPatient = dozerMapper.dozer().map(DEFAULT_PATIENT, Patient.class);
        bindMainView();
    }

    private void initializeListeners() {
        //the "of" factorymethod for immutable collections will only become available
        //from JDK 9 - meanwhile we can use JavaSlang
        javaslang.collection.List<Tuple2<TextInputControl, Integer>> maxSizes
                = of(Tuple.of(mvPatientLastName, 20), Tuple.of(mvPatientFirstName, 15),
                        Tuple.of(mvPatientDiagnosis, 100));
        //set the observable for each of the maxSizes
        for (Tuple2<TextInputControl, Integer> tuple : maxSizes) {
            CommonUtil.addTextLimiter(tuple._1(), tuple._2());
        }
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
        onTableRowClickHandler();
    }

    @Override
    public void bindMainView() {        //if curent patient is null bind with the default patient
        Patient picked = (getCurrentPatient() == null) ? DEFAULT_PATIENT : getCurrentPatient();
        //set all properties
        mvPatientID.textProperty().bind(picked.patientIdProperty().asString());
        Bindings.bindBidirectional(mvPatientLastName.textProperty(), picked.lastNameProperty());
        Bindings.bindBidirectional(mvPatientFirstName.textProperty(), picked.firstNameProperty());
        Bindings.bindBidirectional(mvPatientDiagnosis.textProperty(), picked.diagnosisProperty());
        bindTemporals(picked);
    }

    private void bindTemporals(Patient picked) {
        //composite bindings for admission date
        ObjectProperty<LocalDate> admDate = new SimpleObjectProperty<>(picked.getAdmissionDate().toLocalDate());
        admDate.addListener((arg0, oldValue, newValue) -> {
            picked.setAdmissionDate(LocalDateTime.of(newValue, picked.getAdmissionDate().toLocalTime()));
        });
        ObjectProperty<LocalTime> admTime = new SimpleObjectProperty<>(picked.getAdmissionDate().toLocalTime());
        admTime.addListener((arg0, oldValue, newValue) -> {
            picked.setAdmissionDate(LocalDateTime.of(picked.getAdmissionDate().toLocalDate(), newValue));
        });
        Bindings.bindBidirectional(mvPatientAdmDate.valueProperty(), admDate);
        Bindings.bindBidirectional(mvPatientAdmTime.timeProperty(), admTime);
        //composite bindings for release date
        ObjectProperty<LocalDate> relDate = new SimpleObjectProperty<>(picked.getReleaseDate().toLocalDate());
        relDate.addListener((arg0, oldValue, newValue) -> {
            picked.setReleaseDate(LocalDateTime.of(newValue, picked.getReleaseDate().toLocalTime()));
        });
        ObjectProperty<LocalTime> relTime = new SimpleObjectProperty<>(picked.getReleaseDate().toLocalTime());
        relTime.addListener((arg0, oldValue, newValue) -> {
            picked.setReleaseDate(LocalDateTime.of(picked.getReleaseDate().toLocalDate(), newValue));
        });
        Bindings.bindBidirectional(mvPatientRelDate.valueProperty(), admDate);
        Bindings.bindBidirectional(mvPatientRelTime.timeProperty(), admTime);
    }

    private void onTableRowClickHandler() {
        //set a row factory for the table view
        patientDataTable.setRowFactory(table -> {
            //a row of records
            TableRow<Patient> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    Patient clickedRow = row.getItem();
                    //set new current patient and bind it to the main view
                    currentPatient = dozerMapper.dozer().map(clickedRow, Patient.class);
                    bindMainView();
                }
            });
            return row;
        });
    }
}
