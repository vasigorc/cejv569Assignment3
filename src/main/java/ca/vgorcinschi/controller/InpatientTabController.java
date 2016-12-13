package ca.vgorcinschi.controller;

import ca.vgorcinschi.controller.helpers.CurrencyBigDecimalConverter;
import ca.vgorcinschi.dao.PatientDBService;
import ca.vgorcinschi.model.Inpatient;
import ca.vgorcinschi.util.CommonUtil;
import ca.vgorcinschi.util.DozerMapper;
import com.jfoenix.controls.JFXDatePicker;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static java.time.format.DateTimeFormatter.ofLocalizedDateTime;
import static java.time.format.FormatStyle.MEDIUM;
import static java.time.format.FormatStyle.SHORT;
import java.util.List;
import java.util.Locale;
import java.util.OptionalInt;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.TextFieldTableCell;
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
    TableView<Inpatient> medicationDataTable;
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
    Button mvSaveBtn;

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
        //TODO
    }

    @FXML
    public void deleteInpatient() {
        //TODO
    }

    @FXML
    public void saveInpatient() {
        //TODO
    }
    
    @FXML
    public void rewindInpatient(){
        //TODO
    }
    
    @FXML
    public void forwardInpatient(){
        //TODO
    }

    @Override
    public void execute() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void populateTableView(List<Inpatient> list) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void bindMainView() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void notifyListListeners() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initializeListeners() {
        //set the observables for elements that have min and max length constraints
        javaslang.collection.List<Tuple4<TextInputControl, Integer, OptionalInt, javaslang.collection.List<Node>>> minMaxSizes
                = of(Tuple.of(mvRoomNumber, 10, OptionalInt.of(1), of(mvSaveBtn)),
                        Tuple.of(mvServices, 10, OptionalInt.of(1), of(mvSaveBtn)),
                        Tuple.of(mvSupplies, 10, OptionalInt.of(1), of(mvSaveBtn)),
                        Tuple.of(mvDailyRate, 10, OptionalInt.of(1), of(mvSaveBtn)));
        minMaxSizes.forEach(CommonUtil::addTextLimiter);
        //only double fields
        of(mvServices, mvSupplies, mvDailyRate).forEach(CommonUtil::doubleListener);
    }

    @Override
    public void onTableRowClickHandler() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void bindTemporals(Inpatient r) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
