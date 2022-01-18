package cz.vse.si.predikceobleceni.model.gui;

import cz.vse.si.predikceobleceni.model.obleceni.CastTela;
import cz.vse.si.predikceobleceni.model.obleceni.Formalni;
import cz.vse.si.predikceobleceni.model.obleceni.Obleceni;
import cz.vse.si.predikceobleceni.model.obleceni.Vrstva;
import cz.vse.si.predikceobleceni.model.utils.Persistence;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public void pridejObleceni(){
        appendArea.setText("");
        if (nazev.getText().equals("") || castTela.getSelectionModel().getSelectedItem() == null || vrstva.getSelectionModel().getSelectedItem() == null || formalnost.getSelectionModel().getSelectedItem() == null){
            appendArea.setText("Chybějící hodnoty");
            return;
        }

        CastTela castTela = getCastTela();
        Vrstva vrstva = getVrstva();

        if ((castTela == CastTela.HLAVA && vrstva != Vrstva.PRVNI) || (castTela == CastTela.BOTY && vrstva != Vrstva.PRVNI )){
            appendArea.setText("Daný kus oblečení může být pouze první vrstvou");
            return;
        }

        Obleceni obleceniKUlozeni = new Obleceni(nazev.getText(),getVrstva(),getCastTela(),minimalniTeplota.getValue(),maximalniTeplota.getValue(), getFormalni());

        //jirka uloží
        Persistence persistence = new Persistence();
        persistence.pridejObleceni(obleceniKUlozeni);

        Stage stage = (Stage) nazev.getScene().getWindow();
        stage.close();
    }


    private Formalni getFormalni(){
        String vybranaFormalnost = formalnost.getSelectionModel().getSelectedItem().toString();
        switch (vybranaFormalnost){
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
    private Vrstva getVrstva(){
        String vybranaVrstva = vrstva.getSelectionModel().getSelectedItem().toString();
        switch (vybranaVrstva){
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
    private CastTela getCastTela(){
        String vybranaCastTela = castTela.getSelectionModel().getSelectedItem().toString();
        switch (vybranaCastTela){
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
