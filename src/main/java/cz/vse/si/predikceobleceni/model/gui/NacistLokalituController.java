package cz.vse.si.predikceobleceni.model.gui;

import cz.vse.si.predikceobleceni.model.obleceni.Obleceni;
import cz.vse.si.predikceobleceni.model.obleceni.Outfit;
import cz.vse.si.predikceobleceni.model.svet.Casoprostor;
import cz.vse.si.predikceobleceni.model.utils.Kalkulator;
import cz.vse.si.predikceobleceni.model.utils.Persistence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class NacistLokalituController {
    @FXML
    private Button okButton;
    @FXML
    private Button zrusitButton;
    @FXML
    private ListView<Casoprostor> lokace; //string?


    @FXML
    public void zavriOkno() {
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
    public void initialize() {
        nacistListView();
    }

    public void predpovedObleceni() {
        Casoprostor casoprostor = lokace.getSelectionModel().getSelectedItem();
        zavriOkno();
        Outfit outfit = Kalkulator.getInstance().predpovedObleceni(casoprostor);

        zobrazOknoOutfitu(outfit);
    }

    public void zobrazOknoOutfitu(Outfit vygenerovanyOutfit) {
        List<Obleceni> zakladniObleceni = vygenerovanyOutfit.vratVsechnoZakladniObleceni();
        List<Obleceni> alternativniObleceni = vygenerovanyOutfit.vratVsechnoAlternativniObleceni();
        boolean vzitSiDestnik = vygenerovanyOutfit.isDestnik();

        Dialog<ButtonType> dialog = new Dialog<>();

        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());
        // dialog.initOwner(mainGridPane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        Path path = FileSystems.getDefault().getPath("src/main/java/cz/vse/si/predikceobleceni/model/resources/predpovedbleceni.fxml");

        try {
            fxmlLoader.setLocation(new URL("file:" + path.toAbsolutePath()));

            Node content = fxmlLoader.load();

            ListView<Obleceni> zakladniObleceniListView = (ListView<Obleceni>) content.lookup("#zakladniObleceniListView");
            ObservableList<Obleceni> zakladniObleceniObservableList = FXCollections.observableArrayList(zakladniObleceni);
            zakladniObleceniListView.setItems(zakladniObleceniObservableList);

            ListView<Obleceni> alternativniObleceniListView = (ListView<Obleceni>) content.lookup("#alternativniObleceniListView");
            ObservableList<Obleceni> alternativniObleceniObservableList = FXCollections.observableArrayList(alternativniObleceni);
            alternativniObleceniListView.setItems(alternativniObleceniObservableList);

            Label destnikLabel = (Label) content.lookup("#destnikLabel");
            if (vzitSiDestnik) {
                destnikLabel.setText("Pravdepodobnost deste. Vezmi si destnik.");
            } else {
                destnikLabel.setText("Zrejme nebude prset. Nemusis si brat destnik");
            }

            dialog.getDialogPane().setContent(content);
            dialog.setTitle("Doporučené oblečení");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        dialog.showAndWait();
    }

}
