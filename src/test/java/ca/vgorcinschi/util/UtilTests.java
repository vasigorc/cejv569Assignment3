package ca.vgorcinschi.util;

import java.lang.reflect.Method;
import javafx.scene.control.Button;
import static org.hamcrest.Matchers.instanceOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.LoggerFactory;

/**
 *
 * @author vgorcinschi
 */
public class UtilTests {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(CommonUtil.class.getName());

    public UtilTests() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void findOrCreateMethodTest() {
        try {
            assertThat(CommonUtil.findOrCreateMethod(Button.class, "setDisable", boolean.class), instanceOf(Method.class));
        } catch (NoSuchMethodException ex) {
            log.error("Couldn't invoke a boolean method: " + ex.getMessage() + ", " + ex.getClass().getSimpleName());
        }
    }
}
