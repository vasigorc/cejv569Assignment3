/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vgorcinschi.util;

import ca.vgorcinschi.model.Patient;
import static ca.vgorcinschi.model.Patient.DEFAULT_PATIENT;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.function.Function;
import java.util.function.Predicate;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TextInputControl;
import javaslang.Tuple2;
import javaslang.collection.List;

/**
 * Common utility methods and functional interfaces
 *
 * @author vgorcinschi
 */
public class CommonUtil {

    /*
     function to transform LocalDateTime into JDBC acceptable Timestamp
     */
    public static Function<LocalDateTime, Timestamp> localToSql = l -> {
        if (l != null) {
            //do the conversion
            return new Timestamp(l.getYear() - 1900, l.getMonthValue(), l.getDayOfMonth(),
                    l.getHour(), l.getMinute(), l.getSecond(), l.getNano());
        }
        //garbage-in - garbage-out
        return null;
    };

    //set JavaFX TextField characters limit
    public static void addTextLimiter(final TextInputControl tf, final int maxLength) {
        tf.textProperty().addListener((ObservableValue<? extends String> ov, String t, String t1) -> {
            if (tf.getText().length() > maxLength) {
                String s = tf.getText().substring(0, maxLength);
                tf.setText(s);
            }
        });
    }

    public static Predicate<Patient> isDefault = (Patient p) -> {
        return p.equals(DEFAULT_PATIENT);
    };

    //here the string 2 is the method
    public static void invokeBoolMethod(List<Tuple2<String, Node>> list,
            boolean test) {
        //get method of node from string
        //invoke it with the bool method
        list.forEach(tuple -> tuple._2.setDisable(test));
    }
}
