package ca.vgorcinschi.controller;

import ca.vgorcinschi.dao.PatientDBService;
import ca.vgorcinschi.model.Patient;
import java.util.List;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author vgorcinschi
 */
public abstract class AbstractTabController <R> {

    protected Patient currentPatient;
    protected TabMediator mediator;
    protected PatientDBService service;

    private final org.slf4j.Logger log
            = LoggerFactory.getLogger(this.getClass().getName());

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
    
    public abstract void populateTableView(List<R> list);
}
