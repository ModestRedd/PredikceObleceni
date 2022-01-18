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

    public void zpracujKliknutiMysi(MouseEvent mouseEvent) {
        if (obleceniListView.equals(mouseEvent.getSource())) {
            appendArea.setText("");

            Obleceni obleceni = obleceniListView.getSelectionModel().getSelectedItem();

            currentId = obleceni.getId();

            nazev.setText(obleceni.getNazev());

            switch (obleceni.getCastTela()) {
                case HLAVA:
                    castTela.setValue("hlava");
                    break;
                case TELO:
                    castTela.setValue("tělo");
                    break;
                case NOHY:
                    castTela.setValue("nohy");
                    break;
                case BOTY:
                    castTela.setValue("boty");
                    break;
                default:
                    break;
            }

            switch (obleceni.getVrstva()) {
                case PRVNI:
                    vrstva.setValue("první");
                    break;
                case DRUHA:
                    vrstva.setValue("druhá");
                    break;
                case TRETI:
                    vrstva.setValue("třetí");
                    break;
                default:
                    break;
            }

            minimalniTeplota.getValueFactory().setValue(obleceni.getMinimalniTeplota());
            maximalniTeplota.getValueFactory().setValue(obleceni.getMaximalniTeplota());

            switch (obleceni.getFormalni()) {
                case MALO:
                    formalnost.setValue("neformalní");
                    break;
                case STREDNE:
                    formalnost.setValue("stredne");
                    break;
                case HODNE:
                    formalnost.setValue("formalní");
                    break;
                default:
                    break;
            }
        }

        ulozitButton.setDisable(false);
    }

    public void upravObleceni() {
        appendArea.setText("");
        if (nazev.getText().equals("") || this.castTela.getSelectionModel().getSelectedItem() == null || vrstva.getSelectionModel().getSelectedItem() == null || formalnost.getSelectionModel().getSelectedItem() == null) {
            appendArea.setText("Chybějící hodnoty");
            return;
        }

        CastTela castTela = getCastTela();
        Vrstva vrstva = getVrstva();

        if ((castTela == CastTela.HLAVA && vrstva != Vrstva.PRVNI) || (castTela == CastTela.BOTY && vrstva != Vrstva.PRVNI)) {
            appendArea.setText("Daný kus oblečení může být pouze první vrstvou");
            return;
        }

        Obleceni obleceniKUlozeni = new Obleceni(nazev.getText(), getVrstva(), getCastTela(), minimalniTeplota.getValue(), maximalniTeplota.getValue(), getFormalni());
        obleceniKUlozeni.setId(currentId);

        Persistence persistence = new Persistence();
        persistence.pridejObleceni(obleceniKUlozeni);

        nacistListView();
    }
}
