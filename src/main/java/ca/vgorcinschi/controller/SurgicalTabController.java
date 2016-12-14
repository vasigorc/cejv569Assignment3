package ca.vgorcinschi.controller;

import ca.vgorcinschi.controller.helpers.CurrencyBigDecimalConverter;
import ca.vgorcinschi.dao.PatientDBService;
import ca.vgorcinschi.model.Patient;
import ca.vgorcinschi.model.Surgical;
import static ca.vgorcinschi.model.Surgical.defaultSurgical;
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
import javafx.scene.control.TextArea;
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
public class SurgicalTabController extends AbstractTabController<Surgical> implements Command {

    //wired DAO
    @Autowired
    PatientDBService service;

    //bean that copies object properties
    @Autowired
    DozerMapper dozerMapper;

    private Surgical currentSurgical;

    public Surgical getCurrentSurgical() {
        return currentSurgical;
    }

    public void setCurrentSurgical(Surgical currentSurgical) {
        this.currentSurgical = currentSurgical;
    }

    @FXML
    TableView<Surgical> surgicalDataTable;
    //data table columns
    @FXML
    TableColumn<Surgical, Number> idColumn;
    @FXML
    TableColumn<Surgical, LocalDateTime> surgicalDateColumn;
    @FXML
    TableColumn<Surgical, String> surgeryColumn;
    @FXML
    TableColumn<Surgical, BigDecimal> roomFeeColumn;
    @FXML
    TableColumn<Surgical, BigDecimal> surgeonFeeColumn;
    @FXML
    TableColumn<Surgical, BigDecimal> suppliesColumn;
    /**
     * Initializes the controller class.
     */
    //Main view bindings
    @FXML
    TextField mvSurgicalID;
    @FXML
    TextArea mvSurgery;
    @FXML
    TextField mvRoomFee;
    @FXML
    TextField mvSurgeonFee;
    @FXML
    TextField mvSuppliesFee;
    @FXML
    JFXDatePicker mvSurgicalDate;
    @FXML
    JFXDatePicker mvSurgicalTime;
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
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue()
                .idProperty());
        surgicalDateColumn.setCellValueFactory(cellData -> cellData.getValue()
                .dateOfSurgery());
        surgicalDateColumn.setCellFactory(TextFieldTableCell.forTableColumn(
                new LocalDateTimeStringConverter(ofLocalizedDateTime(MEDIUM, SHORT),
                        ofLocalizedDateTime(MEDIUM, MEDIUM))));
        surgeryColumn.setCellValueFactory(cellData -> cellData.getValue()
                .surgeryProperty());
        roomFeeColumn.setCellValueFactory(cellData -> cellData.getValue()
                .roomFeeProperty());
        surgeonFeeColumn.setCellValueFactory(cellData -> cellData.getValue()
                .surgeonFeeProperty());
        suppliesColumn.setCellValueFactory(cellData -> cellData.getValue()
                .suppliesProperty());
        javaslang.collection.List.of(roomFeeColumn, suppliesColumn, surgeonFeeColumn)
                .forEach(c -> c.setCellFactory(TextFieldTableCell.forTableColumn(
                new CurrencyBigDecimalConverter(Locale.CANADA_FRENCH))));
        initializeListeners();
    }

    @Override
    public void execute() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    public void newSurgical() {
        //TODO
    }

    @FXML
    public void deleteSurgical() {
        //TODO
    }

    @FXML
    public void saveSurgical() {
        //TODO
    }

    @FXML
    public void rewindSurgical() {
        //TODO
    }

    @FXML
    public void forwardSurgical() {
        //TODO
    }

    @Override
    public void populateTableView(List<Surgical> list) {
        observableList = FXCollections.observableArrayList(list);
        surgicalDataTable.setItems(observableList);
        notifyListListeners();
        bindMainView();
    }

    @Override
    public void bindMainView() {
        //a new medication or the one that is chosen
        Surgical surgical = (getCurrentSurgical()== null) ? defaultSurgical(getCurrentPatient().getPatientId())
                : getCurrentSurgical();
        //disable the delete, forward and rewind buttons if we create a new medication
        invokeBoolMethod(javaslang.collection.List.of(
                Tuple.of("setDisable", mvDeleteBtn),
                Tuple.of("setDisable", mvForward),
                Tuple.of("setDisable", mvRewind)
        ), surgical.getId() == 0);
        mvSurgicalID.textProperty().bind(surgical.idProperty().asString());
        Bindings.bindBidirectional(mvSurgery.textProperty(), surgical.surgeryProperty());
        mvRoomFee.textProperty().bindBidirectional(surgical.roomFeeProperty(), new BigDecimalStringConverter());
        mvSuppliesFee.textProperty().bindBidirectional(surgical.suppliesProperty(), new BigDecimalStringConverter());
        mvSurgeonFee.textProperty().bindBidirectional(surgical.surgeonFeeProperty(), new BigDecimalStringConverter());
        bindTemporals(surgical);
    }

    @Override
    public void notifyListListeners() {
         if (!observableList.isEmpty()) {//only if there isn't an arrayindexoutofbound
            currentSurgical = dozerMapper.dozer().map(observableList.get(0), Surgical.class);
        } else {
            //if the patient doesn't have an inpatient, set the current to a new one
            currentSurgical = dozerMapper.dozer().map(defaultSurgical(currentPatient.getPatientId()), Surgical.class);
        }
    }

    @Override
    public void initializeListeners() {
        //set the observables for elements that have min and max length constraints        
        javaslang.collection.List<Tuple3<TextInputControl, Integer, OptionalInt>> minMaxSizes
                = of(Tuple.of(mvSurgery, 10, OptionalInt.of(1)),
                        Tuple.of(mvRoomFee, 7, OptionalInt.empty()),
                        Tuple.of(mvSuppliesFee, 7, OptionalInt.empty()),
                        Tuple.of(mvSurgeonFee, 7, OptionalInt.empty()));
        minMaxSizes.forEach(CommonUtil::addTextLimiter);
        //save button should only appear if all the fields meet the criteria
        mvSaveBtn.disableProperty().bind(Bindings.or(mvSurgery.textProperty().isEmpty(),
                mvRoomFee.textProperty().isEmpty()).or(mvSuppliesFee.textProperty().isEmpty())
                .or(mvSurgeonFee.textProperty().isEmpty()));
        //only double fields
        of(mvRoomFee, mvSuppliesFee, mvSurgeonFee).forEach(CommonUtil::doubleListener);
        onTableRowClickHandler();
    }

    @Override
    public void onTableRowClickHandler() {
        //set a row factory for the table view
        surgicalDataTable.setRowFactory(table -> {
            //a row of records
            TableRow<Surgical> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    Surgical clickedRow = row.getItem();
                    //set new current patient and bind it to the main view
                    setCurrentSurgical(dozerMapper.dozer().map(clickedRow, Surgical.class));
                    bindMainView();
                }
            });
            return row;
        });
    }

    @Override
    public void bindTemporals(Surgical r) {
        //composite bindings for the inpatient date
        if (r.getDateOfSurgery()== null) {//unfortunatelly we cannot bind to null
            r.setDateOfSurgery(LocalDateTime.now(ZoneId.systemDefault()));
        }
        ObjectProperty<LocalDate> surgicalDate = new SimpleObjectProperty<>(r.getDateOfSurgery().toLocalDate());
        surgicalDate.addListener((arg0, oldValue, newValue) -> {
            r.setDateOfSurgery(LocalDateTime.of(newValue, r.getDateOfSurgery().toLocalTime()));
        });
        ObjectProperty<LocalTime> surgicalTime = new SimpleObjectProperty<>(r.getDateOfSurgery().toLocalTime());
        surgicalTime.addListener((arg0, oldValue, newValue) -> {
            r.setDateOfSurgery(LocalDateTime.of(r.getDateOfSurgery().toLocalDate(), newValue));
        });
        Bindings.bindBidirectional(mvSurgicalDate.valueProperty(), surgicalDate);
        Bindings.bindBidirectional(mvSurgicalTime.timeProperty(), surgicalTime);
    }

    @Override
    public void setCurrentPatient(Patient currentPatient) {
        super.setCurrentPatient(currentPatient); //To change body of generated methods, choose Tools | Templates.
        populateTableView(getCurrentPatient().getSurgicals());
    }
}
