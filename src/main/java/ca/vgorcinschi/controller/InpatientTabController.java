package ca.vgorcinschi.controller;

import ca.vgorcinschi.model.Inpatient;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author vgorcinschi
 */
@Component
public class InpatientTabController extends AbstractTabController <Inpatient> implements Command {

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
}