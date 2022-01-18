package cz.vse.si.predikceobleceni.model.gui;

import cz.vse.si.predikceobleceni.model.obleceni.CastTela;
import cz.vse.si.predikceobleceni.model.obleceni.Formalni;
import cz.vse.si.predikceobleceni.model.obleceni.Vrstva;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpravitObleceniController {
    @FXML
    private TextField nazev;
    @FXML
    private ComboBox castTela;
    @FXML
    private ComboBox vrstva;
    @FXML
    private Spinner<Integer> minimalniTeplota;
    @FXML
    private Spinner<Integer> maximalniTeplota;
    @FXML
    private ComboBox formalnost;

    public void ulozitObleceni(ActionEvent actionEvent) {
    }

    private Formalni getFormalni() {
        String vybranaFormalnost = formalnost.getSelectionModel().getSelectedItem().toString();
        switch (vybranaFormalnost) {
            case "neformalní":
                return Formalni.MALO;
            case "středně":
                return Formalni.STREDNE;
            case "formalní":
                return Formalni.HODNE;
            default:
                return null;
        }
    }

    private Vrstva getVrstva() {
        String vybranaVrstva = vrstva.getSelectionModel().getSelectedItem().toString();
        switch (vybranaVrstva) {
            case "první":
                return Vrstva.PRVNI;
            case "druhá":
                return Vrstva.DRUHA;
            case "třetí":
                return Vrstva.TRETI;
            default:
                return null;
        }
    }

    private CastTela getCastTela() {
        String vybranaCastTela = castTela.getSelectionModel().getSelectedItem().toString();
        switch (vybranaCastTela) {
            case "čepice":
                return CastTela.HLAVA;
            case "tělo":
                return CastTela.TELO;
            case "nohy":
                return CastTela.NOHY;
            case "boty":
                return CastTela.BOTY;
            default:
                return null;
        }
    }

    public void zavriOkno() {
        Stage stage = (Stage) nazev.getScene().getWindow();
        stage.close();
    }
}
