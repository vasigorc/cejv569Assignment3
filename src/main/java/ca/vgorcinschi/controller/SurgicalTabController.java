package ca.vgorcinschi.controller;

import ca.vgorcinschi.model.Surgical;
import java.util.List;
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
    public void initialize() {
        // TODO
    }    

    @Override
    public void execute() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }   

    @Override
    public void populateTableView(List<Surgical> list) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}