package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;

public class SDMApplicationMain extends Application {
    final String MAIN_APP_FXML_RESOURCE = "SDMAppMain.fxml";
    @Override
    public void start(Stage primaryStage) throws Exception {

        //CSSFX.start();
        
        FXMLLoader loader = new FXMLLoader();

        // load main fxml
        URL mainFXML = getClass().getResource(MAIN_APP_FXML_RESOURCE);
        loader.setLocation(mainFXML);
        BorderPane root = loader.load();
        //Parent root = loader.load();

       // wire up controllers

        //set stage
        primaryStage.setTitle("SDM- Ma'ayan and sons .corp");
        Scene scene = new Scene(root, 1050, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}
