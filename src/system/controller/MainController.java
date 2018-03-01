package system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import system.Main;
import system.model.Database;
import system.model.Documents;

import java.io.IOException;


public class MainController {
    @FXML
    public TableView tableView;
    @FXML
    public TableColumn IDCol;
    @FXML
    public Button buttonPlusDbGui;
    @FXML
    public Button buttonMinusDbGui;
    @FXML
    public TextField textFieldId;
    @FXML
    public TextField textFieldTitle;
    @FXML
    public TextField textFieldAuthor;
    @FXML
    public TextField textFieldKeywords;
    @FXML
    public TextField textFieldReference;
    @FXML
    public Button openKeywords;

    private Database database;
    private ObservableList<Documents> model;

    @FXML
    public void init() {
        System.out.println("Nahui");
    }

    public void openKeywordsAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(Main.class.getResource("system/view/GUI2.fxml"));
            Stage stage = new Stage();
            stage.initOwner(((TextArea)actionEvent.getSource()).getScene().getWindow());
            stage.setTitle("Keywords Configure");
            stage.setScene(new Scene(root, 500, 250));
            stage.show();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
