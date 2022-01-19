package cz.vse.si.predikceobleceni.controller;

import cz.vse.si.predikceobleceni.model.Obleceni;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class PredpovedObleceniController {
    @FXML
    private Label destnikLabel;
    @FXML
    private ListView<Obleceni> zakladniObleceniListView;
    @FXML
    private ListView<Obleceni> alternativniObleceniListView;
    @FXML
    private Button buttonOk;

    public void handleOkButton() {
        Stage stage = (Stage) buttonOk.getScene().getWindow();
        stage.close();
    }
}
