package ca.vgorcinschi.controller;

import java.util.ResourceBundle;

/**
 * mediator part of the mediator pattern
 *
 * @author vgorcinschi
 */
public interface TabMediator {

    public void updatePatient();

    public void reloadPatient();
    
    public void closePopUp();

    public ResourceBundle getResourceBundle();
}
