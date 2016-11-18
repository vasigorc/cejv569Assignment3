package ca.vgorcinschi;

import javafx.embed.swing.JFXPanel;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.concurrent.atomic.AtomicBoolean;

public class JavaFxRuntimeAvailable implements TestRule {

    private static final AtomicBoolean JAVA_FX_STARTED = new AtomicBoolean(false);

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {

                if (!JAVA_FX_STARTED.get()) {
                    initializeJavaFxRuntime();
                    JAVA_FX_STARTED.set(true);
                }

                base.evaluate();
            }
        };
    }

    private void initializeJavaFxRuntime() {
        //implicitly initializes JavaFX Subsystem
        // see http://stackoverflow.com/questions/14025718/javafx-toolkit-not-initialized-when-trying-to-play-an-mp3-file-through-mediap
        new JFXPanel();
    }
}
