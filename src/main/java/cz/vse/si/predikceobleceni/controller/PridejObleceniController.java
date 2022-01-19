package cz.vse.si.predikceobleceni.controller;

import cz.vse.si.predikceobleceni.model.CastTela;
import cz.vse.si.predikceobleceni.model.Formalni;
import cz.vse.si.predikceobleceni.model.Obleceni;
import cz.vse.si.predikceobleceni.model.Vrstva;
import cz.vse.si.predikceobleceni.utils.Persistence;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class PridejObleceniController {
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
    @FXML
    private Label appendArea;
    @FXML
    private Button pridat;


    @FXML
    private void initialize() {
        pridat.setDisable(true);
    }

    public void pridejObleceni() {
        appendArea.setText("");
        if (nazev.getText().equals("") || castTela.getSelectionModel().getSelectedItem() == null || vrstva.getSelectionModel().getSelectedItem() == null || formalnost.getSelectionModel().getSelectedItem() == null) {
            appendArea.setText("Chybějící hodnoty");
            return;
        }

        CastTela castTela = getCastTela();
        Vrstva vrstva = getVrstva();

        if ((castTela == CastTela.HLAVA && vrstva != Vrstva.PRVNI) || (castTela == CastTela.BOTY && vrstva != Vrstva.PRVNI)) {
            appendArea.setText("Daný kus oblečení může být pouze první vrstvou");
            return;
        }

        if (minimalniTeplota.getValue() > maximalniTeplota.getValue()) {
            appendArea.setText("Problémy v teplotách");
            return;
        }

        Obleceni obleceniKUlozeni = new Obleceni(nazev.getText(), getVrstva(), getCastTela(), minimalniTeplota.getValue(), maximalniTeplota.getValue(), getFormalni());

        Persistence persistence = new Persistence();
        persistence.pridejObleceni(obleceniKUlozeni);

        Stage stage = (Stage) nazev.getScene().getWindow();
        stage.close();
    }


    private Formalni getFormalni() {
        String vybranaFormalnost = formalnost.getSelectionModel().getSelectedItem().toString();
        switch (vybranaFormalnost) {
            case "neformální":
                return Formalni.MALO;
            case "středně":
                return Formalni.STREDNE;
            case "formální":
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
            case "hlava":
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

    public void zkontrolujVsechnyUdaje() {
        appendArea.setText("");
        pridat.setDisable(false);
        if (nazev.getText().equals("") || castTela.getSelectionModel().getSelectedItem() == null || vrstva.getSelectionModel().getSelectedItem() == null || formalnost.getSelectionModel().getSelectedItem() == null) {
            pridat.setDisable(true);
        }

    }
}
