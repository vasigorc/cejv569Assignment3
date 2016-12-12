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
import static java.time.format.DateTimeFormatter.ofLocalizedDateTime;
import static java.time.format.FormatStyle.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;
import javafx.util.converter.LocalDateTimeStringConverter;
import javaslang.Tuple;
import static javaslang.collection.List.of;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static ca.vgorcinschi.util.CommonUtil.invokeBoolMethod;
import static java.util.Optional.*;
import java.util.OptionalInt;
import javafx.scene.Node;
import javaslang.Tuple4;

/**
 * FXML Controller class
 *
 * @author vgorcinschi
 */
@Component
public class PatientTabController extends AbstractTabController<Patient> implements Command {

    //wired DAO
    @Autowired
    private PatientDBService service;

    //bean that copies object properties
    @Autowired
    private DozerMapper dozerMapper;

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

    @FXML
    Button mvDeleteBtn;

    @FXML
    Button mvSaveBtn;

    @FXML
    Button mvRewind;

    @FXML
    Button mvForward;

    @Override
    public void execute() {
        getMediator().updatePatient();
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
        //format the localdatetimes to be more user friendly
        admissionDateColumn.setCellFactory(TextFieldTableCell.forTableColumn(
                new LocalDateTimeStringConverter(ofLocalizedDateTime(MEDIUM, SHORT),
                        ofLocalizedDateTime(MEDIUM, MEDIUM))));
        releaseDateColumn.setCellValueFactory(cellData -> cellData.getValue()
                .releaseDateProperty());
        releaseDateColumn.setCellFactory(TextFieldTableCell.forTableColumn(
                new LocalDateTimeStringConverter(ofLocalizedDateTime(MEDIUM, SHORT),
                        ofLocalizedDateTime(MEDIUM, MEDIUM))));
        //on our init() we will load all patients
        populateTableView(service.allPatients().orElse(new LinkedList<>()));
        initializeListeners();
    }

    public PatientDBService getService() {
        return service;
    }

    @Override
    public void populateTableView(List<Patient> list) {
        observableList = FXCollections.observableArrayList(list);
        patientDataTable.setItems(observableList);
        notifyListListeners();//now current patient is set
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
    public void newPatient() {
        setCurrentPatient(dozerMapper.dozer().map(DEFAULT_PATIENT, Patient.class));
        bindMainView();
    }

    @FXML
    public void deletePatient() {
        if (service.deletePatient(currentPatient.getPatientId())) {
            //if deleted -> reload the view
            updateTable(null);
        }
    }

    @FXML
    public void savePatient() {
        if (service.savePatient(currentPatient)) {
            updateTable(null);
        }
    }

    @FXML
    public void rewindPatient() {
        int currentIndex = currentMainViewIndex(patient -> currentPatient.getPatientId() == patient.getPatientId());
        if (currentIndex > 0) {
            setCurrentPatient(dozerMapper.dozer().map(observableList.get(currentIndex - 1), Patient.class));
            bindMainView();
        }
        //en-/disable the rewind button based on the current index
        invokeBoolMethod(javaslang.collection.List.of(
                Tuple.of("setDisable", mvRewind)
        ), currentIndex <= 0);
    }

    @FXML
    public void forwardPatient() {
        int currentIndex = currentMainViewIndex(patient -> currentPatient.getPatientId() == patient.getPatientId());
        if (currentIndex < observableList.size()) {
            setCurrentPatient(dozerMapper.dozer().map(observableList.get(currentIndex + 1), Patient.class));
            bindMainView();
        }
        //en-/disable the rewind button based on the current index
        invokeBoolMethod(javaslang.collection.List.of(
                Tuple.of("setDisable", mvRewind)
        ), currentIndex >= observableList.size());
    }

    @Override
    public void initializeListeners() {
        //the "of" factorymethod for immutable collections will only become available
        //from JDK 9 - meanwhile we can use JavaSlang
        //set the observable for each element that has ONLY max constraints
        of(Tuple.of(mvPatientDiagnosis, 100)).forEach(tuple -> CommonUtil.addTextLimiter(tuple._1(), tuple._2()));
        javaslang.collection.List<Tuple4<TextInputControl, Integer, OptionalInt, javaslang.collection.List<Node>>> minMaxSizes
                = of(Tuple.of(mvPatientLastName, 20, OptionalInt.of(2), of(mvSaveBtn)),
                        Tuple.of(mvPatientFirstName, 15, OptionalInt.of(1), of(mvSaveBtn)));
        //and for each of the minMaxSizes
        minMaxSizes.forEach(CommonUtil::addTextLimiter);
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
    public void bindMainView() {
        //if curent patient is null bind with the default patient and disable the delete button
        Patient picked = (getCurrentPatient() == null) ? DEFAULT_PATIENT : getCurrentPatient();
        //disable the delete, forward and rewindbuttons if we create a new patient
        invokeBoolMethod(javaslang.collection.List.of(
                Tuple.of("setDisable", mvDeleteBtn),
                Tuple.of("setDisable", mvForward),
                Tuple.of("setDisable", mvRewind)
        ), picked.equals(DEFAULT_PATIENT));
        //set all properties
        mvPatientID.textProperty().bind(picked.patientIdProperty().asString());
        Bindings.bindBidirectional(mvPatientLastName.textProperty(), picked.lastNameProperty());
        Bindings.bindBidirectional(mvPatientFirstName.textProperty(), picked.firstNameProperty());
        Bindings.bindBidirectional(mvPatientDiagnosis.textProperty(), picked.diagnosisProperty());
        bindTemporals(picked);
    }

    @Override
    public void bindTemporals(Patient r) {
        //composite bindings for admission date
        ObjectProperty<LocalDate> admDate = new SimpleObjectProperty<>(r.getAdmissionDate().toLocalDate());
        admDate.addListener((arg0, oldValue, newValue) -> {
            r.setAdmissionDate(LocalDateTime.of(newValue, r.getAdmissionDate().toLocalTime()));
        });
        ObjectProperty<LocalTime> admTime = new SimpleObjectProperty<>(r.getAdmissionDate().toLocalTime());
        admTime.addListener((arg0, oldValue, newValue) -> {
            r.setAdmissionDate(LocalDateTime.of(r.getAdmissionDate().toLocalDate(), newValue));
        });
        Bindings.bindBidirectional(mvPatientAdmDate.valueProperty(), admDate);
        Bindings.bindBidirectional(mvPatientAdmTime.timeProperty(), admTime);
        //composite bindings for release date
        ObjectProperty<LocalDate> relDate = new SimpleObjectProperty<>(r.getReleaseDate().toLocalDate());
        relDate.addListener((arg0, oldValue, newValue) -> {
            r.setReleaseDate(LocalDateTime.of(newValue, r.getReleaseDate().toLocalTime()));
        });
        ObjectProperty<LocalTime> relTime = new SimpleObjectProperty<>(r.getReleaseDate().toLocalTime());
        relTime.addListener((arg0, oldValue, newValue) -> {
            r.setReleaseDate(LocalDateTime.of(r.getReleaseDate().toLocalDate(), newValue));
        });
        Bindings.bindBidirectional(mvPatientRelDate.valueProperty(), relDate);
        Bindings.bindBidirectional(mvPatientRelTime.timeProperty(), relTime);
    }

    @Override
    public void onTableRowClickHandler() {
        //set a row factory for the table view
        patientDataTable.setRowFactory(table -> {
            //a row of records
            TableRow<Patient> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    Patient clickedRow = row.getItem();
                    //set new current patient and bind it to the main view
                    setCurrentPatient(dozerMapper.dozer().map(clickedRow, Patient.class));
                    execute();
                    bindMainView();
                }
            });
            return row;
        });
    }

    @Override
    public void notifyListListeners() {
        /**
         * since
         * https://docs.oracle.com/javafx/2/api/javafx/collections/ListChangeListener.Change.html
         * is failry useless, we're binding the events manually
         */
        if (!observableList.isEmpty()) {//only if there isn't an arrayindexoutofbound
            //do not reset the main view if the current patient is in the list
            //especially useful for detail tabs
            Optional<Patient> optional = empty();
            if (currentPatient != null) {
                optional = observableList.stream()
                        .filter(p -> p.getPatientId() == currentPatient.getPatientId()).findFirst();
            }
            if (optional.isPresent()) {
                setCurrentPatient(dozerMapper.dozer().map(optional.get(), Patient.class));
            } else {
                setCurrentPatient(dozerMapper.dozer().map(observableList.get(0), Patient.class));
            }
        }
        mvRewind.setDisable(observableList.isEmpty());
        mvForward.setDisable(observableList.isEmpty());
    }

    @Override
    public void setCurrentPatient(Patient currentPatient) {
        super.setCurrentPatient(currentPatient);
        if (getMediator() != null) {//all tabs are initialized and are ready to listen to changes
            execute();
        }
    }
}
