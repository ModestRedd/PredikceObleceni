package cz.vse.si.predikceobleceni.model.gui;

import cz.vse.si.predikceobleceni.model.obleceni.Obleceni;
import cz.vse.si.predikceobleceni.model.utils.Persistence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class SmazatObleceniController {
    @FXML
    private ListView<Obleceni> obleceniListView;
    @FXML
    private Button smazatButton;

    int currentId = Integer.MIN_VALUE;

    public void zpracujKliknutiMysi(MouseEvent mouseEvent) {
        if (obleceniListView.equals(mouseEvent.getSource())) {
            Obleceni obleceni = obleceniListView.getSelectionModel().getSelectedItem();

            currentId = obleceni.getId();
        }

        smazatButton.setDisable(false);
    }

    public void smazatObleceni() {
        Persistence persistence = new Persistence();

        persistence.odeberObleceniPodleId(currentId);

        nacistListView();
    }

    private void nacistListView() {
        Persistence persistence = new Persistence();
        ArrayList<Obleceni> obleceni = persistence.getAllObleceni();

        ObservableList<Obleceni> obleceniObservableList = FXCollections.observableArrayList(obleceni);
        obleceniListView.setItems(obleceniObservableList);
    }
}
