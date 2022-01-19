package cz.vse.si.predikceobleceni.model.gui;

import cz.vse.si.predikceobleceni.model.svet.Casoprostor;
import cz.vse.si.predikceobleceni.model.utils.Persistence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class SmazatLokalituController {
    @FXML
    private ListView<Casoprostor> lokalityListView;
    @FXML
    private Button smazatButton;

    int currentId = Integer.MIN_VALUE;

    @FXML
    private void initialize() {
        nacistListView();
    }

    public void zpracujKliknutiMysi(MouseEvent mouseEvent) {
        if (lokalityListView.equals(mouseEvent.getSource())) {
            Casoprostor lokalita = lokalityListView.getSelectionModel().getSelectedItem();

            currentId = lokalita.getId();
        }

        smazatButton.setDisable(false);
    }

    public void smazatLokalitu() {
        Persistence persistence = new Persistence();
        persistence.odeberLokalituPodleId(currentId);

        nacistListView();
    }

    private void nacistListView() {
        Persistence persistence = new Persistence();
        ArrayList<Casoprostor> lokality = persistence.getLokality();

        ObservableList<Casoprostor> lokalityObservableList = FXCollections.observableArrayList(lokality);
        lokalityListView.setItems(lokalityObservableList);
    }
}
