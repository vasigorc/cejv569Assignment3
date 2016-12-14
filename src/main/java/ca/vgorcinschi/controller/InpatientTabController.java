package ca.vgorcinschi.controller;

import ca.vgorcinschi.controller.helpers.CurrencyBigDecimalConverter;
import ca.vgorcinschi.dao.PatientDBService;
import ca.vgorcinschi.model.Inpatient;
import static ca.vgorcinschi.model.Inpatient.defaultInpatient;
import ca.vgorcinschi.model.Patient;
import ca.vgorcinschi.util.CommonUtil;
import static ca.vgorcinschi.util.CommonUtil.invokeBoolMethod;
import ca.vgorcinschi.util.DozerMapper;
import com.jfoenix.controls.JFXDatePicker;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import static java.time.format.DateTimeFormatter.ofLocalizedDateTime;
import static java.time.format.FormatStyle.MEDIUM;
import static java.time.format.FormatStyle.SHORT;
import java.util.List;
import java.util.Locale;
import java.util.OptionalInt;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
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
import javaslang.Tuple3;
import static javaslang.collection.List.of;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author vgorcinschi
 */
@Component
public class InpatientTabController extends AbstractTabController<Inpatient> implements Command {

    //wired DAO
    @Autowired
    PatientDBService service;

    //bean that copies object properties
    @Autowired
    DozerMapper dozerMapper;
    
    private Inpatient currentInpatient;
    
    public Inpatient getCurrentInpatient() {
        return currentInpatient;
    }
    
    public void setCurrentInpatient(Inpatient currentInpatient) {
        this.currentInpatient = currentInpatient;
    }
    
    @FXML
    TableView<Inpatient> inpatientDataTable;
    //data table columns
    @FXML
    TableColumn<Inpatient, Number> idColumn;
    @FXML
    TableColumn<Inpatient, LocalDateTime> dateOfInpatientColumn;
    @FXML
    TableColumn<Inpatient, String> roomNumberColumn;
    @FXML
    TableColumn<Inpatient, BigDecimal> dailyRateColumn;
    @FXML
    TableColumn<Inpatient, BigDecimal> suppliesColumn;
    @FXML
    TableColumn<Inpatient, BigDecimal> servicesColumn;

    //Main view bindings
    @FXML
    TextField mvInpatientID;
    
    @FXML
    TextField mvRoomNumber;
    
    @FXML
    TextField mvDailyRate;
    
    @FXML
    TextField mvSupplies;
    
    @FXML
    TextField mvServices;
    
    @FXML
    JFXDatePicker mvDateOfInpatient;
    
    @FXML
    JFXDatePicker mvTimeOfInpatient;
    
    @FXML
    Button mvAddBtn;
    
    @FXML
    Button mvDeleteBtn;
    
    @FXML
    Button mvInpSaveBtn;
    
    @FXML
    Button mvRewind;
    
    @FXML
    Button mvForward;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue()
                .idProperty());
        dateOfInpatientColumn.setCellValueFactory(cellData -> cellData.getValue()
                .dateOfStayProperty());
        dateOfInpatientColumn.setCellFactory(TextFieldTableCell.forTableColumn(
                new LocalDateTimeStringConverter(ofLocalizedDateTime(MEDIUM, SHORT),
                        ofLocalizedDateTime(MEDIUM, MEDIUM))));
        roomNumberColumn.setCellValueFactory(cellData -> cellData.getValue()
                .roomNumberProperty());
        dailyRateColumn.setCellValueFactory(cellData -> cellData.getValue()
                .dailyRateProperty());
        suppliesColumn.setCellValueFactory(cellData -> cellData.getValue()
                .suppliesProperty());
        servicesColumn.setCellValueFactory(cellData -> cellData.getValue()
                .servicesProperty());
        javaslang.collection.List.of(dailyRateColumn, suppliesColumn, servicesColumn)
                .forEach(c -> c.setCellFactory(TextFieldTableCell.forTableColumn(
                new CurrencyBigDecimalConverter(Locale.CANADA_FRENCH))));
        initializeListeners();
    }
    
    @FXML
    public void newInpatient() {
        setCurrentInpatient(dozerMapper.dozer().map(defaultInpatient(currentPatient.getPatientId()), Inpatient.class));
        bindMainView();
    }
    
    @FXML
    public void deleteInpatient() {
        if (service.deleteDetailRecord(currentInpatient)) {
            execute();
        }
    }
    
    @FXML
    public void saveInpatient() {
        if (service.saveDetailRecord(currentInpatient)) {
            execute();
        }
    }
    
    @FXML
    public void rewindInpatient() {
        int currentIndex = currentMainViewIndex(inpatient -> currentInpatient.getId() == inpatient.getId());
        if (currentIndex > 0) {
            setCurrentInpatient(dozerMapper.dozer().map(observableList.get(currentIndex - 1), Inpatient.class));
            bindMainView();
        }
        //en-/disable the rewind button based on the current index
        invokeBoolMethod(javaslang.collection.List.of(
                Tuple.of("setDisable", mvRewind)
        ), currentIndex <= 0);
    }
    
    @FXML
    public void forwardInpatient() {
        int currentIndex = currentMainViewIndex(patient -> currentInpatient.getId() == patient.getId());
        if (currentIndex < (observableList.size() - 1)) {
            setCurrentInpatient(dozerMapper.dozer().map(observableList.get(currentIndex + 1), Inpatient.class));
            bindMainView();
        }
        //en-/disable the rewind button based on the current index
        invokeBoolMethod(javaslang.collection.List.of(
                Tuple.of("setDisable", mvForward)
        ), currentIndex >= (observableList.size() - 1));
    }
    
    @Override
    public void execute() {
        mediator.reloadPatient();
    }
    
    @Override
    public void populateTableView(List<Inpatient> list) {
        observableList = FXCollections.observableArrayList(list);
        inpatientDataTable.setItems(observableList);
        notifyListListeners();
        bindMainView();
    }
    
    @Override
    public void bindMainView() {
        //a new medication or the one that is chosen
        Inpatient inpatient = (getCurrentInpatient() == null) ? defaultInpatient(getCurrentPatient().getPatientId())
                : getCurrentInpatient();
        //disable the delete, forward and rewind buttons if we create a new medication
        invokeBoolMethod(javaslang.collection.List.of(
                Tuple.of("setDisable", mvDeleteBtn),
                Tuple.of("setDisable", mvForward),
                Tuple.of("setDisable", mvRewind)
        ), inpatient.getId() == 0);
        mvInpatientID.textProperty().bind(inpatient.idProperty().asString());
        Bindings.bindBidirectional(mvRoomNumber.textProperty(), inpatient.roomNumberProperty());
        mvDailyRate.textProperty().bindBidirectional(inpatient.dailyRateProperty(), new BigDecimalStringConverter());
        mvServices.textProperty().bindBidirectional(inpatient.servicesProperty(), new BigDecimalStringConverter());
        mvSupplies.textProperty().bindBidirectional(inpatient.suppliesProperty(), new BigDecimalStringConverter());
        bindTemporals(inpatient);
    }
    
    @Override
    public void notifyListListeners() {
        if (!observableList.isEmpty()) {//only if there isn't an arrayindexoutofbound
            currentInpatient = dozerMapper.dozer().map(observableList.get(0), Inpatient.class);
        } else {
            //if the patient doesn't have an inpatient, set the current to a new one
            currentInpatient = dozerMapper.dozer().map(defaultInpatient(currentPatient.getPatientId()), Inpatient.class);
        }
    }
    
    @Override
    public void initializeListeners() {
        //set the observables for elements that have min and max length constraints        
        javaslang.collection.List<Tuple3<TextInputControl, Integer, OptionalInt>> minMaxSizes
                = of(Tuple.of(mvRoomNumber, 10, OptionalInt.of(1)),
                        Tuple.of(mvServices, 7, OptionalInt.empty()),
                        Tuple.of(mvSupplies, 7, OptionalInt.empty()),
                        Tuple.of(mvDailyRate, 7, OptionalInt.empty()));
        minMaxSizes.forEach(CommonUtil::addTextLimiter);
        //save button should only appear if all the fields meet the criteria
        mvInpSaveBtn.disableProperty().bind(Bindings.or(mvServices.textProperty().isEmpty(),
                mvSupplies.textProperty().isEmpty()).or(mvRoomNumber.textProperty().isEmpty())
                .or(mvDailyRate.textProperty().isEmpty()));
        //only double fields
        of(mvServices, mvSupplies, mvDailyRate).forEach(CommonUtil::doubleListener);
        onTableRowClickHandler();
    }
    
    @Override
    public void onTableRowClickHandler() {
        //set a row factory for the table view
        inpatientDataTable.setRowFactory(table -> {
            //a row of records
            TableRow<Inpatient> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    Inpatient clickedRow = row.getItem();
                    //set new current patient and bind it to the main view
                    setCurrentInpatient(dozerMapper.dozer().map(clickedRow, Inpatient.class));
                    bindMainView();
                }
            });
            return row;
        });
    }
    
    @Override
    public void bindTemporals(Inpatient r) {
        //composite bindings for the inpatient date
        if (r.getDateOfStay() == null) {//unfortunatelly we cannot bind to null
            r.setDateOfStay(LocalDateTime.now(ZoneId.systemDefault()));
        }
        ObjectProperty<LocalDate> inpatientDate = new SimpleObjectProperty<>(r.getDateOfStay().toLocalDate());
        inpatientDate.addListener((arg0, oldValue, newValue) -> {
            r.setDateOfStay(LocalDateTime.of(newValue, r.getDateOfStay().toLocalTime()));
        });
        ObjectProperty<LocalTime> inpatientTime = new SimpleObjectProperty<>(r.getDateOfStay().toLocalTime());
        inpatientTime.addListener((arg0, oldValue, newValue) -> {
            r.setDateOfStay(LocalDateTime.of(r.getDateOfStay().toLocalDate(), newValue));
        });
        Bindings.bindBidirectional(mvDateOfInpatient.valueProperty(), inpatientDate);
        Bindings.bindBidirectional(mvTimeOfInpatient.timeProperty(), inpatientTime);
    }
    
    @Override
    public void setCurrentPatient(Patient currentPatient) {
        super.setCurrentPatient(currentPatient); //To change body of generated methods, choose Tools | Templates.        
        populateTableView(getCurrentPatient().getInpatients());
    }
    
}
