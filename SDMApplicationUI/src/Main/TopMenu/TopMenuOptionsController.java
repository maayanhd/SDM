package Main.TopMenu;

import Main.SDMAppMainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;

public class TopMenuOptionsController {
    @FXML private ProgressBar uploadXMLFileProgressBar;
    @FXML private Button uploadXMLButton;
    @FXML private Label loadprogressStatusLabel;
    @FXML private ImageView backroundImage;

    @FXML SDMAppMainController mainController;
    // Sub components

    public void setMainController(SDMAppMainController mainController){
        this.mainController = mainController;
    }


    public void uploadXMLButtonAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select XML file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(mainController.getStage());
        if (selectedFile == null) {
            return;
        }

        String absolutePath = selectedFile.getAbsolutePath();
        selectedFileProperty.set(absolutePath);
        isFileSelected.set(true);
    }
}
