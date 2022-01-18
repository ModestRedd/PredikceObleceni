package cz.vse.si.predikceobleceni.model.gui;

import cz.vse.si.predikceobleceni.model.obleceni.Obleceni;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class PredpovedObleceniController {
    @FXML
    private Label destnikLabel;
    @FXML
    private ListView<Obleceni> zakladniObleceniListView;
    @FXML
    private ListView<Obleceni> alternativniObleceniListView;

    public void handleOkButton() {

    }
}
