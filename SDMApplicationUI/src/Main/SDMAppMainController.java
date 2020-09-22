package Main;

import Main.TopMenu.TopMenuOptionsController;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SDMAppMainController {
    @FXML private BorderPane sdmAppRoot;
    @FXML private VBox topMenuOptions;


    @FXML private TopMenuOptionsController topMenuOptionsController;
    // more sub components
    // TODO: 20/09/2020 add more sub components

    @FXML
    public void initialize(){
        if(topMenuOptionsController != null){
            topMenuOptionsController.setMainController(this);
        }
    }

    public Stage getStage() {
        return (Stage)sdmAppRoot.getScene().getWindow();
    }
}
