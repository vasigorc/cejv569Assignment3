package ca.vgorcinschi.controller;

import ca.vgorcinschi.dao.PatientDBService;
import ca.vgorcinschi.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author vgorcinschi
 */
public abstract class AbstractTabController {

    protected Patient currentPatient;
    protected TabMediator mediator;
    protected PatientDBService service;

    @Autowired
    public AbstractTabController(PatientDBService service) {
        this.service = service;
    }

    public AbstractTabController() {
    }

    public Patient getCurrentPatient() {
        return currentPatient;
    }

    public void setCurrentPatient(Patient currentPatient) {
        this.currentPatient = currentPatient;
    }

    public TabMediator getMediator() {
        return mediator;
    }

    public void setMediator(TabMediator mediator) {
        this.mediator = mediator;
    }
    
    
}
