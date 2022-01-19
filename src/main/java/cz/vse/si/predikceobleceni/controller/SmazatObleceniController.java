package cz.vse.si.predikceobleceni.controller;

import cz.vse.si.predikceobleceni.model.Obleceni;
import cz.vse.si.predikceobleceni.utils.Persistence;
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
            if (obleceni == null){
                return;
            }
            currentId = obleceni.getId();
        }

        smazatButton.setDisable(false);
    }

    public void smazatObleceni() {
        //Persistence persistence = new Persistence();
        Persistence.getInstance().odeberObleceniPodleId(currentId);

        nacistListView();
    }

    private void nacistListView() {
        //Persistence persistence = new Persistence();
        ArrayList<Obleceni> obleceni = Persistence.getInstance().getAllObleceni();

        ObservableList<Obleceni> obleceniObservableList = FXCollections.observableArrayList(obleceni);
        obleceniListView.setItems(obleceniObservableList);
    }
}
