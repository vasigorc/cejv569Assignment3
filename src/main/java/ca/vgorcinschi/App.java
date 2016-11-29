package ca.vgorcinschi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class App extends Application {

    /**
     * Note that this is configured in application.properties
     */
    @Value("${app.ui.title:Example App}")//
    private String windowTitle;
    private Parent rootNode;
    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() throws Exception {
        applicationContext = SpringApplication.run(App.class);
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        myLoader.setControllerFactory(applicationContext::getBean);
        rootNode = myLoader.load();
    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle(windowTitle);
        stage.setScene(new Scene(rootNode));
        stage.setMaximized(true);
        stage.setResizable(true);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
    
    @Override
	public void stop() throws Exception {
		applicationContext.close();
	}
}
