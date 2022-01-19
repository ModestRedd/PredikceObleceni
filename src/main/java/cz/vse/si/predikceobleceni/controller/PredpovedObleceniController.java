package cz.vse.si.predikceobleceni.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PredpovedObleceniController {

    @FXML
    private Button buttonOk;

    public void handleOkButton() {
        Stage stage = (Stage) buttonOk.getScene().getWindow();
        stage.close();
    }
}
