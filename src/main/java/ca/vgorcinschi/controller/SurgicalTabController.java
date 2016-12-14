package ca.vgorcinschi.controller;

import ca.vgorcinschi.model.Surgical;
import java.util.List;
import javafx.fxml.FXML;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author vgorcinschi
 */
@Component
public class SurgicalTabController extends AbstractTabController <Surgical> implements Command {

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {
        // TODO
    }    

    @Override
    public void execute() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @FXML
    public void newSurgical(){
        //TODO
    }
    
    @FXML
    public void deleteSurgical(){
        //TODO
    }
    
    @FXML
    public void saveSurgical(){
        //TODO
    }
    
    @FXML
    public void rewindSurgical(){
        //TODO
    }
    
    @FXML
    public void forwardSurgical(){
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