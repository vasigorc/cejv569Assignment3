package ca.vgorcinschi;

import ca.vgorcinschi.ui.MainLayout;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App extends AbstractJavaFxApplicationSupport {

    /**
     * Note that this is configured in application.properties
     */
    @Value("${app.ui.title:Example App}")//
    private String windowTitle;

    @Autowired//
    private MainLayout mainLayout;

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle(windowTitle);
        stage.setScene(new Scene(mainLayout));
        stage.setResizable(true);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launchApp(App.class, args);
    }
}
