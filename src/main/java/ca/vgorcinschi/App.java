package ca.vgorcinschi;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App extends AbstractJavaFxApplicationSupport {

    /**
     * Note that this is configured in application.properties
     */
    @Value("${app.ui.title:Example App}")//
    private String windowTitle;

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle(windowTitle);
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        Parent loadScreen = (Parent) myLoader.load();

        stage.setScene(new Scene(loadScreen));
        //make the stage to feet your screen
        stage.setMaximized(true);
        stage.setResizable(true);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launchApp(App.class, args);
    }
}