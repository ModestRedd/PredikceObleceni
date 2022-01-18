package cz.vse.si.predikceobleceni.model.gui;

import cz.vse.si.predikceobleceni.model.obleceni.Obleceni;
import cz.vse.si.predikceobleceni.model.svet.Casoprostor;
import cz.vse.si.predikceobleceni.model.utils.Persistence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.ArrayList;

public class NacistLokalituController {
    @FXML
    private Button okButton;
    @FXML
    private Button zrusitButton;
    @FXML
    private ListView<Casoprostor> lokace; //string?


    @FXML
    public void zavriOkno(){
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    private void nacistListView() {
        Persistence persistence = new Persistence();
        ArrayList<Casoprostor> lokality = persistence.getLokality();

        ObservableList<Casoprostor> obleceniObservableList = FXCollections.observableArrayList(lokality);
        lokace.setItems(obleceniObservableList);
    }
    @FXML
    public void initialize(){
        nacistListView();
    }

}
