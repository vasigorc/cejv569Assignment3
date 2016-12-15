/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vgorcinschi.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.OptionalInt;
import java.util.function.Function;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javaslang.Tuple2;
import javaslang.Tuple3;
import javaslang.collection.List;
import javaslang.control.Try;
import org.slf4j.LoggerFactory;

/**
 * Common utility methods and functional interfaces
 *
 * @author vgorcinschi
 */
public class CommonUtil {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(CommonUtil.class.getName());

    /**
     * It is kind of the cache that will keep references to the methods by their
     * classes and method names. This will allow us to not recreate Method
     * objects everytime we need to hide/disable a JavaFX Node
     */
    public static Map<Class<?>, Map<String, Method>> methods;

    //bootstrap the methods map
    static {
        methods = new HashMap<>();
    }

    //method that will populate and return the required methods
    public static Method findOrCreateMethod(Class<? extends Node> type, String methodName, Class<?> parameterType) throws NoSuchMethodException {
        /**
         * check if there is a class in the type's hierarchy that has the
         * methodName cf:
         * https://docs.oracle.com/javase/tutorial/java/generics/erasure.html
         */
        Class<?> clazz = type;
        while (clazz != null) {
            Try<Method> tryMethod = loadMethod(clazz, methodName, parameterType);
            if (tryMethod.isSuccess()) {
                //add class to the cache (if needed)
                methods.putIfAbsent(clazz, new HashMap<>());
                //add method to the cache (if needed)
                methods.get(clazz).putIfAbsent(methodName, tryMethod.get());
                return methods.get(clazz).get(methodName);
            } else {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchMethodException("Method " + methodName + " does not belong to any "
                + "class in the hierarchy of " + type.getSimpleName());
    }

    private static Try<Method> loadMethod(Class<?> type, String methodName, Class<?> parameterType) {
        return Try.of(() -> type.getDeclaredMethod(methodName, parameterType));
    }

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

    public static void doubleListener(final TextInputControl tf) {
        tf.textProperty().addListener((ObservableValue<? extends String> ov, String t, String t1) -> {
            //if number is not a decimal -> clear the field
            Try<Double> attempt = Try.of(() -> Double.parseDouble(tf.getText()));
            if (!attempt.isSuccess()) {
                Platform.runLater(() -> tf.clear());
            }
        });
    }

    //set JavaFX TextField characters limit
    public static void addTextLimiter(final TextInputControl tf, final int maxLength) {
        addTextLimiter(tf, maxLength, OptionalInt.empty());
    }

    public static void addTextLimiter(final TextInputControl tf, final int maxLength, OptionalInt minLength) {
        tf.textProperty().addListener((ObservableValue<? extends String> ov, String t, String t1) -> {
            if (tf.getText().length() > maxLength) {
                String s = tf.getText().substring(0, maxLength);
                tf.setText(s);
            }
            if (minLength.isPresent()) {
                if (tf.getText().length() < minLength.getAsInt()) {
                    tf.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.DOTTED, new CornerRadii(5), new BorderWidths(2))));
                } else {
                    tf.setBorder(Border.EMPTY);
                }
            }
        });
    }

    //handy tuple implementation
    public static void addTextLimiter(Tuple3<TextInputControl, Integer, OptionalInt> t) {
        addTextLimiter(t._1, t._2, t._3);
    }

    //here the string 2 is the method
    public static void invokeBoolMethod(List<Tuple2<String, Node>> list,
            boolean test) {
        //get method of node from string and invoke it with the bool method
        list.forEach((Tuple2<String, Node> tuple) -> {
            try {
                Method method = findOrCreateMethod(tuple._2.getClass(), tuple._1, boolean.class);
                method.invoke(tuple._2, test);
            } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                log.error("Couldn't invoke a boolean method: " + ex.getMessage() + ", " + ex.getClass().getSimpleName());
            }
        });
    }
}
