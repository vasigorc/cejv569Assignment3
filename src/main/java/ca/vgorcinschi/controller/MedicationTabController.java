package ca.vgorcinschi.controller;

import ca.vgorcinschi.components.DoubleField;
import ca.vgorcinschi.controller.helpers.CurrencyBigDecimalConverter;
import ca.vgorcinschi.dao.PatientDBService;
import ca.vgorcinschi.model.Medication;
import static ca.vgorcinschi.model.Medication.defaultMedication;
import ca.vgorcinschi.model.Patient;
import ca.vgorcinschi.util.CommonUtil;
import static ca.vgorcinschi.util.CommonUtil.*;
import ca.vgorcinschi.util.DozerMapper;
import com.jfoenix.controls.JFXDatePicker;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import static java.time.format.DateTimeFormatter.ofLocalizedDateTime;
import static java.time.format.FormatStyle.*;
import java.util.List;
import java.util.Locale;
import java.util.OptionalInt;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.util.converter.BigDecimalStringConverter;
import javafx.util.converter.LocalDateTimeStringConverter;
import javaslang.Tuple;
import javaslang.Tuple4;
import static javaslang.collection.List.of;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author vgorcinschi
 */
@Component
public class MedicationTabController extends AbstractTabController<Medication> implements Command {

    //wired DAO
    @Autowired
    PatientDBService service;

    //bean that copies object properties
    @Autowired
    DozerMapper dozerMapper;
    //property that is used to manipulate the main view
    private Medication currentMedication;

    public Medication getCurrentMedication() {
        return currentMedication;
    }

    public void setCurrentMedication(Medication currentMedication) {
        this.currentMedication = currentMedication;
    }

    @FXML
    TableView<Medication> medicationDataTable;
    //data table columns
    @FXML
    TableColumn<Medication, Number> idColumn;
    @FXML
    TableColumn<Medication, LocalDateTime> dateOfMedicationColumn;
    @FXML
    TableColumn<Medication, String> medNameColumn;
    @FXML
    TableColumn<Medication, BigDecimal> unitCostColumn;
    @FXML
    TableColumn<Medication, BigDecimal> unitsColumn;

    /**
     * Main view bindings: these are the GUI controls that handle the new or
     * picked patient's data. "mv" prefix stands for Main View
     */
    @FXML
    TextField mvMedicationID;

    @FXML
    TextField mvMedName;

    @FXML
    DoubleField mvMedUnitCost;

    @FXML
    DoubleField mvUnits;

    @FXML
    JFXDatePicker mvMedicationDate;

    @FXML
    JFXDatePicker mvMedicationTime;

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

    @FXML
    private void initialize() {
        //link table columns to patient class properties
        idColumn.setCellValueFactory(cellData -> cellData.getValue()
                .idProperty());
        dateOfMedicationColumn.setCellValueFactory(cellData -> cellData.getValue()
                .dateOfMedicationProperty());
        dateOfMedicationColumn.setCellFactory(TextFieldTableCell.forTableColumn(
                new LocalDateTimeStringConverter(ofLocalizedDateTime(MEDIUM, SHORT),
                        ofLocalizedDateTime(MEDIUM, MEDIUM))));
        medNameColumn.setCellValueFactory(cellData -> cellData.getValue()
                .medProperty());
        unitCostColumn.setCellValueFactory(cellData -> cellData.getValue()
                .unitCostProperty());
        unitCostColumn.setCellFactory(TextFieldTableCell.forTableColumn(
                new CurrencyBigDecimalConverter(Locale.CANADA_FRENCH)));
        unitsColumn.setCellValueFactory(cellData -> cellData.getValue()
                .unitsProperty());
        initializeListeners();
    }

    @FXML
    public void newMedication() {
        setCurrentMedication(dozerMapper.dozer().map(defaultMedication(currentPatient.getPatientId()), Medication.class));
        bindMainView();
    }

    @FXML
    public void saveMedication() {
        if (service.saveDetailRecord(currentMedication)) {
            execute();
        }
    }

    @FXML
    public void deleteMedication() {
        if (service.deleteDetailRecord(currentMedication)) {
            execute();
        }
    }

    @FXML
    public void rewindMedication() {
        int currentIndex = currentMainViewIndex(medication -> currentMedication.getId() == medication.getId());
        if (currentIndex > 0) {
            setCurrentMedication(dozerMapper.dozer().map(observableList.get(currentIndex - 1), Medication.class));
            bindMainView();
        }
        //en-/disable the rewind button based on the current index
        invokeBoolMethod(javaslang.collection.List.of(
                Tuple.of("setDisable", mvRewind)
        ), currentIndex <= 0);
    }

    @FXML
    public void forwardMedication() {
        int currentIndex = currentMainViewIndex(medication -> currentMedication.getId() == medication.getId());
        if (currentIndex < (observableList.size()-1)) {
            setCurrentMedication(dozerMapper.dozer().map(observableList.get(currentIndex + 1), Medication.class));
            bindMainView();
        }
        //en-/disable the rewind button based on the current index
        invokeBoolMethod(javaslang.collection.List.of(
                Tuple.of("setDisable", mvForward)
        ), currentIndex >= (observableList.size()-1));
    }

    @Override
    public void execute() {
        mediator.reloadPatient();
    }

    @Override
    public void populateTableView(List<Medication> list) {
        observableList = FXCollections.observableArrayList(list);
        medicationDataTable.setItems(observableList);
        notifyListListeners();
        bindMainView();
    }

    @Override
    public void bindMainView() {
        //a new medication or the one that is chosen
        Medication medication = (getCurrentMedication() == null) ? defaultMedication(getCurrentPatient().getPatientId())
                : getCurrentMedication();
        //disable the delete, forward and rewind buttons if we create a new medication
        invokeBoolMethod(javaslang.collection.List.of(
                Tuple.of("setDisable", mvDeleteBtn),
                Tuple.of("setDisable", mvForward),
                Tuple.of("setDisable", mvRewind)
        ), medication.getId() == 0);
        //set all properties
        mvMedicationID.textProperty().bind(medication.idProperty().asString());
        Bindings.bindBidirectional(mvMedName.textProperty(), medication.medProperty());
        mvMedUnitCost.textProperty().bindBidirectional(medication.unitCostProperty(), new BigDecimalStringConverter());
        mvUnits.textProperty().bindBidirectional(medication.unitsProperty(), new BigDecimalStringConverter());
        bindTemporals(medication);
    }

    @Override
    public void notifyListListeners() {
        if (!observableList.isEmpty()) {//only if there isn't an arrayindexoutofbound
            currentMedication = dozerMapper.dozer().map(observableList.get(0), Medication.class);
        } else {
            //if the patient doesn't have a medication, set the current to a new one
            currentMedication = dozerMapper.dozer().map(defaultMedication(currentPatient.getPatientId()), Medication.class);
        }
    }

    @Override
    public void setCurrentPatient(Patient currentPatient) {
        super.setCurrentPatient(currentPatient);
        populateTableView(getCurrentPatient().getMedications());
    }

    @Override
    public void initializeListeners() {
        //set the observables for elements that have min and max length constraints
        javaslang.collection.List<Tuple4<TextInputControl, Integer, OptionalInt, javaslang.collection.List<Node>>> minMaxSizes
                = of(Tuple.of(mvMedName, 20, OptionalInt.of(5), of(mvSaveBtn)),
                        Tuple.of(mvMedUnitCost, 10, OptionalInt.of(1), of(mvSaveBtn)),
                        Tuple.of(mvUnits, 3, OptionalInt.of(1), of(mvSaveBtn)));
        minMaxSizes.forEach(CommonUtil::addTextLimiter);
        onTableRowClickHandler();
    }

    @Override
    public void onTableRowClickHandler() {
        //set a row factory for the table view
        medicationDataTable.setRowFactory(table -> {
            //a row of records
            TableRow<Medication> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    Medication clickedRow = row.getItem();
                    //set new current patient and bind it to the main view
                    setCurrentMedication(dozerMapper.dozer().map(clickedRow, Medication.class));
                    bindMainView();
                }
            });
            return row;
        });
    }

    @Override
    public void bindTemporals(Medication r) {
        //composite bindings for medication date
        if (r.getDateOfMedication() == null) {//unfortunatelly we cannot bind to null
            r.setDateOfMedication(LocalDateTime.now(ZoneId.systemDefault()));
        }
        ObjectProperty<LocalDate> admDate = new SimpleObjectProperty<>(r.getDateOfMedication().toLocalDate());
        admDate.addListener((arg0, oldValue, newValue) -> {
            r.setDateOfMedication(LocalDateTime.of(newValue, r.getDateOfMedication().toLocalTime()));
        });
        ObjectProperty<LocalTime> admTime = new SimpleObjectProperty<>(r.getDateOfMedication().toLocalTime());
        admTime.addListener((arg0, oldValue, newValue) -> {
            r.setDateOfMedication(LocalDateTime.of(r.getDateOfMedication().toLocalDate(), newValue));
        });
        Bindings.bindBidirectional(mvMedicationDate.valueProperty(), admDate);
        Bindings.bindBidirectional(mvMedicationTime.timeProperty(), admTime);
    }
}
