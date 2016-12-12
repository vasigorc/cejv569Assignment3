package ca.vgorcinschi.controller;

import ca.vgorcinschi.model.Patient;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import javafx.collections.ObservableList;
import org.slf4j.LoggerFactory;

/**
 *
 * @author vgorcinschi
 */
public abstract class AbstractTabController<R> {

    protected Patient currentPatient;
    protected TabMediator mediator;

    public TabMediator getMediator() {
        return mediator;
    }

    public void setMediator(TabMediator mediator) {
        this.mediator = mediator;
    }
    /**
     * each of the children of the AbstractTabController will have an observable
     * list with listeners needing to be attached to it.
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

    protected int currentMainViewIndex(Predicate<R> predicate) {
        Optional<R> optional = observableList.stream()
                .filter(patient -> predicate.test(patient))
                .findFirst();
        return optional.isPresent() ? observableList.indexOf(optional.get()) : -1;
    }

    public abstract void populateTableView(List<R> list);

    public abstract void bindMainView();
    
    public abstract void notifyListListeners();
    
    public abstract void initializeListeners();
    
    public abstract void onTableRowClickHandler();
    
    public abstract void bindTemporals(R r);
}