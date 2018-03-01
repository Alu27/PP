package system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import system.model.Database;
import system.model.Keywords;

import java.util.Optional;

public class KeywordController {
    @FXML
    public ListView<Keywords> listView;
    @FXML
    public Button buttonPlusListView;
    @FXML
    public Button buttonMinusListView;
    @FXML
    public Button firstButtonCancel;
    @FXML
    public Button firstButtonOK;

    private ObservableList<Keywords> model;
    private Database database;

    public void init() {
        // set data model
        database = new Database();
        model = FXCollections.observableArrayList( database.getKeyword() );
    }

    public void updateBtns() {
        buttonMinusListView.setDisable(!(listView
                .getSelectionModel().getSelectedItems().size() > 0));
        buttonPlusListView.setDisable(!(listView
                .getSelectionModel().getSelectedItems().size() > 0));
    }

    public void secondHandlePlusAction(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog("New Item");
        dialog.setContentText("neuer Eintrag:");
        dialog.setTitle("Hinzufügen");
        dialog.setHeaderText("Hier kann ein Schlagwort hinzugefügt werden");
        Optional<String> result = dialog.showAndWait(); // wait for user input
//        if( result.isPresent() ) {
//            // FIXME: HIER WAR ARTEM DAS GEHT NICHT MEHR WEIL WEGEN IST SO LOMBOK IST SCHEIßE
//            Item item = new Item(result.get().toString());
//            item = database.addItem(item); // get Item with new id
//            model.add(item);
//        }
    }

    public void secondHandleMinusAction(ActionEvent actionEvent) {
        // important: delete from database first
        database.deleteKeyword(listView.getSelectionModel().getSelectedItem());
        model.remove(listView.getSelectionModel().getSelectedIndex());
    }

    @FXML
    public void FirstButtonCancelAction(ActionEvent actionEvent) {
        Stage stage = (Stage) firstButtonCancel.getScene().getWindow();
        stage.close();
    }
}
