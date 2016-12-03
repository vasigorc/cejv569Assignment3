package ca.vgorcinschi.components;

import javafx.scene.control.TextField;

/**
 *
 * @author vgorcinschi
 */
public class NumberField extends TextField {

    public NumberField() {
    }

    public NumberField(String string) {
        super(string);
    }

    @Override
    public void replaceSelection(String string) {
        super.replaceSelection(string); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void replaceText(int i, int i1, String string) {
        if (string.matches("[0-9]") || string.isEmpty()) {
            super.replaceText(i, i1, string); //To change body of generated methods, choose Tools | Templates.
        }
    }
}
