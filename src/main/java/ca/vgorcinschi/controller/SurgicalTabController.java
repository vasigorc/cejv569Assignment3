package ca.vgorcinschi.controller;

import ca.vgorcinschi.dao.PatientDBService;
import ca.vgorcinschi.model.Surgical;
import ca.vgorcinschi.util.DozerMapper;
import com.jfoenix.controls.JFXDatePicker;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    TextField mvSurgery;
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
        // TODO
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onTableRowClickHandler() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void bindTemporals(Surgical r) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
