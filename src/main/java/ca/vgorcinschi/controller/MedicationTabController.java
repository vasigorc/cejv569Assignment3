package ca.vgorcinschi.controller;

import ca.vgorcinschi.controller.helpers.CurrencyBigDecimalConverter;
import ca.vgorcinschi.dao.PatientDBService;
import ca.vgorcinschi.model.Medication;
import ca.vgorcinschi.model.Patient;
import ca.vgorcinschi.util.DozerMapper;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static java.time.format.DateTimeFormatter.ofLocalizedDateTime;
import static java.time.format.FormatStyle.MEDIUM;
import static java.time.format.FormatStyle.SHORT;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.LocalDateTimeStringConverter;
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
     * Initializes the controller class.
     */
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
        //load the medication (if exist) for current patient (if exists)
        if (getCurrentPatient() != null) {
            populateTableView(getCurrentPatient().getMedications());
        } else {
            populateTableView(new ArrayList<>());
        }
    }

    @Override
    public void execute() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void populateTableView(List<Medication> list) {
        observableList = FXCollections.observableArrayList(list);
        medicationDataTable.setItems(observableList);
        notifyListListeners();
        //bindMainView() when implemented
    }

    @Override
    public void bindMainView() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void notifyListListeners() {
        if (!observableList.isEmpty()) {//only if there isn't an arrayindexoutofbound
            currentMedication = dozerMapper.dozer().map(observableList.get(0), Medication.class);
        }
    }

    @Override
    public void setCurrentPatient(Patient currentPatient) {
        super.setCurrentPatient(currentPatient);
        populateTableView(getCurrentPatient().getMedications());
    }
}
