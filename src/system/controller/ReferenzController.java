package system.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;


public class ReferenzController {

    @FXML
    TextField textFieldDatei;

    public void chooseFile(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wählen Sie ihre Datei aus!");
        File file = fileChooser.showOpenDialog(((Button)mouseEvent.getSource()).getScene().getWindow());
        if(file != null) {
            textFieldDatei.setText(file.getAbsolutePath());
        }
    }
}
