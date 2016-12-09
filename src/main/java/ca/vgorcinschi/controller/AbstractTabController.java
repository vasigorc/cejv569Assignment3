package ca.vgorcinschi.controller;

import ca.vgorcinschi.model.Patient;
import java.util.List;
import javafx.collections.ObservableList;
import org.slf4j.LoggerFactory;

/**
 *
 * @author vgorcinschi
 */
public abstract class AbstractTabController <R> {

    protected Patient currentPatient;
    protected TabMediator mediator;
    /**
     * each of the children of the AbstractTabController
     * will have an observable list with listeners needing to 
     * be attached to it.
     */
    protected ObservableList<R> observableList;

    protected org.slf4j.Logger log
            = LoggerFactory.getLogger(this.getClass().getName());

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
    
    public abstract void bindMainView();
}
