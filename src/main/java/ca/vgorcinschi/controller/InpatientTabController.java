package ca.vgorcinschi.controller;

import ca.vgorcinschi.dao.PatientDBService;
import ca.vgorcinschi.model.Inpatient;
import ca.vgorcinschi.util.DozerMapper;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author vgorcinschi
 */
@Component
public class InpatientTabController extends AbstractTabController <Inpatient> implements Command {

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
    
    /**
     * Initializes the controller class.
     */
    public void initialize() {
        // TODO
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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