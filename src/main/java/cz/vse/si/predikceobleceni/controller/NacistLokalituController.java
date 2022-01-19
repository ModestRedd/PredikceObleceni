package cz.vse.si.predikceobleceni.controller;

import cz.vse.si.predikceobleceni.model.obleceni.Obleceni;
import cz.vse.si.predikceobleceni.model.obleceni.Outfit;
import cz.vse.si.predikceobleceni.model.svet.Casoprostor;
import cz.vse.si.predikceobleceni.utils.InternetAlert;
import cz.vse.si.predikceobleceni.utils.Kalkulator;
import cz.vse.si.predikceobleceni.utils.Persistence;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
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
    private ListView<Casoprostor> lokace;
    @FXML
    private Label appendArea;


    @FXML
    private void initialize() {
        nacistListView();
    }

    @FXML
    public void zavriOkno() {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    public void zobrazOknoOutfitu(Outfit vygenerovanyOutfit) {
        List<Obleceni> zakladniObleceni = vygenerovanyOutfit.vratVsechnoZakladniObleceni();
        List<Obleceni> alternativniObleceni = vygenerovanyOutfit.vratVsechnoAlternativniObleceni();
        boolean vzitSiDestnik = vygenerovanyOutfit.isDestnik();

        Dialog<ButtonType> dialog = new Dialog<>();

        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());
        dialog.initOwner(okButton.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        Path path = FileSystems.getDefault().getPath("src/main/java/cz/vse/si/predikceobleceni/view/predpovedbleceni.fxml");

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
                destnikLabel.setText("Pravděpodobnost deště. Vezmi si deštník.");
            } else {
                destnikLabel.setText("Zřejmě nebude pršet. Nemusíš si brát deštník.");
            }

            dialog.getDialogPane().setContent(content);
            dialog.setTitle("Doporučené oblečení");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        dialog.showAndWait();
    }

    public void zpracujKliknutiMysi(MouseEvent mouseEvent) {
        Casoprostor casoprostor = lokace.getSelectionModel().getSelectedItem();
        if (casoprostor == null){
            okButton.setDisable(true);
            return;
        }
        if (lokace.equals(mouseEvent.getSource())) {
            okButton.setDisable(false);
        }
    }

    public void predpovedObleceni() {
        Casoprostor casoprostor = lokace.getSelectionModel().getSelectedItem();

        this.zobrazZFXThread("Načítání...");
        Outfit outfit = Kalkulator.getInstance().predpovedObleceni(casoprostor);
        this.zobrazZFXThread("");
        if (outfit == null) {
            InternetAlert.zobrazNoInternetAlert();
            return;
        }
        if (outfit.getCepice() != null) {
            if (outfit.getCepice().getMinimalniTeplota() == Integer.MIN_VALUE) {
                InternetAlert.zobrazMalyRozsahAlert();
                return;
            }
        }
        zobrazOknoOutfitu(outfit);
    }

    private void zobrazZFXThread(String text) {
        Runnable task = () -> Platform.runLater(() -> appendArea.setText(text));
        new Thread(task).start();
    }


    private void nacistListView() {
        ArrayList<Casoprostor> lokality = Persistence.getInstance().getLokality();

        ObservableList<Casoprostor> obleceniObservableList = FXCollections.observableArrayList(lokality);
        lokace.setItems(obleceniObservableList);
    }


}
