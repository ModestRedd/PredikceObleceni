package cz.vse.si.predikceobleceni.model.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class OaplikaciController {
    @FXML
    private Button okButton;

    @FXML
    public void zavriOkno() {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

}
