package cz.vse.si.predikceobleceni.model.gui;

import cz.vse.si.predikceobleceni.model.obleceni.CastTela;
import cz.vse.si.predikceobleceni.model.obleceni.Formalni;
import cz.vse.si.predikceobleceni.model.obleceni.Obleceni;
import cz.vse.si.predikceobleceni.model.obleceni.Vrstva;
import cz.vse.si.predikceobleceni.model.utils.Persistence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.SQLOutput;
import java.util.ArrayList;

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
    @FXML
    private ListView<Obleceni> obleceniListView;
    @FXML
    private Button ulozitButton;
    @FXML
    private Label appendArea;

    int currentId = Integer.MIN_VALUE;
    CastTela currentCastTela;
    Vrstva currentVrstva;

    private void nacistListView() {
        Persistence persistence = new Persistence();
        ArrayList<Obleceni> obleceni = persistence.getAllObleceni();

        ObservableList<Obleceni> obleceniObservableList = FXCollections.observableArrayList(obleceni);
        obleceniListView.setItems(obleceniObservableList);
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

    private Vrstva getVrstva(String vybranaVrstva) {
        //String vybranaVrstva = vrstva.getSelectionModel().getSelectedItem().toString();
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

    private CastTela getCastTela(String vybranaCastTela) {
        //String vybranaCastTela = castTela.getSelectionModel().getSelectedItem().toString();
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

    public void zpracujKliknutiMysi(MouseEvent mouseEvent) {
        if (obleceniListView.equals(mouseEvent.getSource())) {
            appendArea.setText("");

            Obleceni obleceni = obleceniListView.getSelectionModel().getSelectedItem();

            currentId = obleceni.getId();

            nazev.setText(obleceni.getNazev());

            currentCastTela = obleceni.getCastTela();
            castTela.setValue(determineCastTela(currentCastTela));

            currentVrstva = obleceni.getVrstva();
            vrstva.setValue(determineVrstva(currentVrstva));

            minimalniTeplota.getValueFactory().setValue(obleceni.getMinimalniTeplota());
            maximalniTeplota.getValueFactory().setValue(obleceni.getMaximalniTeplota());

            formalnost.setValue(determineFormalnost(obleceni.getFormalni()));
        }

        ulozitButton.setDisable(false);
    }

    private String determineCastTela(CastTela castTela) {
        switch (castTela) {
            case HLAVA:
                return "hlava";
            case TELO:
                return "tělo";
            case NOHY:
                return "nohy";
            case BOTY:
                return "boty";
        }

        return null;
    }

    private String determineFormalnost(Formalni formalni) {
        switch (formalni) {
            case MALO:
                return "neformalní";
            case STREDNE:
                return "stredne";
            case HODNE:
                return "formalní";
        }

        return null;
    }

    private String determineVrstva(Vrstva vrstva) {
        switch (vrstva) {
            case PRVNI:
                return "první";
            case DRUHA:
                return "druhá";
            case TRETI:
                return "třetí";
        }

        return null;
    }

    public void upravObleceni() {
        appendArea.setText("");
        if (nazev.getText().equals("") || this.castTela.getSelectionModel().getSelectedItem() == null || vrstva.getSelectionModel().getSelectedItem() == null || formalnost.getSelectionModel().getSelectedItem() == null) {
            appendArea.setText("Chybějící hodnoty");
            return;
        }

        CastTela castTelaKZapisu = getCastTela(castTela.getValue().toString());
        Vrstva vrstvaKZapisu = getVrstva(vrstva.getValue().toString());

        if ((castTelaKZapisu == CastTela.HLAVA && vrstvaKZapisu != Vrstva.PRVNI) || (castTelaKZapisu == CastTela.BOTY && vrstvaKZapisu != Vrstva.PRVNI)) {
            appendArea.setText("Daný kus oblečení může být pouze první vrstvou");
            return;
        }

        /*
        System.out.println(castTelaKZapisu);
        System.out.println(vrstvaKZapisu);
        */

        Obleceni obleceniKUlozeni = new Obleceni(nazev.getText(), vrstvaKZapisu, castTelaKZapisu, minimalniTeplota.getValue(), maximalniTeplota.getValue(), getFormalni());
        obleceniKUlozeni.setId(currentId);

        Persistence persistence = new Persistence();
        persistence.pridejObleceni(obleceniKUlozeni);

        nacistListView();
    }
}
