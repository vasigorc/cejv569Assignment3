package ca.vgorcinschi.controller;

import ca.vgorcinschi.model.Medication;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author vgorcinschi
 */
@Component
public class MedicationTabController extends AbstractTabController <Medication> implements Command {

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
    public void populateTableView(List<Medication> list) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void bindMainView() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}