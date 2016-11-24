package ca.vgorcinschi.controller;

import ca.vgorcinschi.dao.PatientDBService;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author vgorcinschi
 */
@Component
public class PatientTabController extends AbstractTabController implements Command {
    
    @Override
    public void execute() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Initializes the controller class.
     */
    private void initialize() {
        // TODO
    }

    public PatientDBService getService() {
        return service;
    }
}